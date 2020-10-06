package com.verminsnest.entities.projectiles;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.core.management.ids.Indentifiers;
import com.verminsnest.entities.Entity;

public abstract class Projectile extends Entity{
	protected int direction;
	
	protected Animation<TextureRegion> flyingAni;
	protected Animation<TextureRegion> hitAni;
	protected Animation<TextureRegion> castAni;
	
	protected int damage;
	protected int speed;
	
	protected boolean toReset;
	
	public static String iconPath;
	
	private boolean isFriendly;
	
	public Projectile(int textureID, int direction, int agility,int damage, int[] position){
		super(position, textureID);
		
		//Data
		isFriendly = false;
		this.setDamage(damage);
		speed = agility;
		this.direction = direction;		
		//Texture orientation
		switch(direction){
		case Indentifiers.DIRECTION_SOUTH:
			rotation = 180;
			break;
		case Indentifiers.DIRECTION_WEST:
			rotation = 90;
			break;
		case Indentifiers.DIRECTION_EAST:
			rotation = 270;
			break;
		}
		internalStateTime = 0;
		init();
		this.setCurrentAni(Indentifiers.STATE_CAST);
		this.setSize(currentAni.getKeyFrame(0).getRegionWidth(),currentAni.getKeyFrame(0).getRegionHeight());
	}
	
	public abstract void init();
	
	protected void updateStateTime(float delta){
		if(toReset){
			internalStateTime = 0;
			toReset = false;
		}
		internalStateTime += delta;
	}
	
	public TextureRegion getCurrentFrame(float stateTime) {
		if(state == Indentifiers.STATE_CAST){
			if(currentAni.isAnimationFinished(internalStateTime)){
				setCurrentAni(Indentifiers.STATE_FLYING);
			}
			return currentAni.getKeyFrame(internalStateTime, false);
		}else if(state == Indentifiers.STATE_FLYING){
			return currentAni.getKeyFrame(internalStateTime, true);
		}
		else if(state == Indentifiers.STATE_HIT ){
			if(currentAni.isAnimationFinished(internalStateTime)){
				state = Indentifiers.STATE_TODELETE;
			}
			return currentAni.getKeyFrame(internalStateTime, false);
		}else if(state == Indentifiers.STATE_TODELETE){
			return currentAni.getKeyFrame(currentAni.getAnimationDuration(), false);
		}
		return null;
	}
	
	public void setCurrentAni(int aniKey){
		switch(aniKey){
		case Indentifiers.STATE_FLYING:
			currentAni = flyingAni;
			state = Indentifiers.STATE_FLYING;
			toReset = true;
			break;
		case Indentifiers.STATE_HIT:
			currentAni = hitAni;
			state = Indentifiers.STATE_HIT;
			toReset = true;
			break;
		case Indentifiers.STATE_CAST:
			currentAni = castAni;
			state = Indentifiers.STATE_CAST;
			toReset = true;
			break;
		}
	}

	public void update(float stateTime){
		updateStateTime(stateTime);
		if(state == Indentifiers.STATE_FLYING){
			if(direction == Indentifiers.DIRECTION_EAST && !RuntimeData.getInstance().getMovmentSystem().moveRight(this,speed, null)){
				setCurrentAni(Indentifiers.STATE_HIT);
			}
			else if(direction == Indentifiers.DIRECTION_WEST && !RuntimeData.getInstance().getMovmentSystem().moveLeft(this,speed, null)){
				setCurrentAni(Indentifiers.STATE_HIT);
			}
			else if(direction == Indentifiers.DIRECTION_NORTH && !RuntimeData.getInstance().getMovmentSystem().moveTop(this,speed, null)){
				setCurrentAni(Indentifiers.STATE_HIT);
			}
			else if(direction == Indentifiers.DIRECTION_SOUTH && !RuntimeData.getInstance().getMovmentSystem().moveDown(this,speed, null)){
				setCurrentAni(Indentifiers.STATE_HIT);
			}
		}else if(state == Indentifiers.STATE_HIT){
			if(direction == Indentifiers.DIRECTION_WEST){
				pos[0] -= speed;
			}else if(direction == Indentifiers.DIRECTION_EAST){
				pos[0] += speed;
			}else if(direction == Indentifiers.DIRECTION_SOUTH){
				pos[1] -= speed;
			}else if(direction == Indentifiers.DIRECTION_NORTH){
				pos[1] += speed;
			}
		}else if(state == Indentifiers.STATE_TODELETE){
			RuntimeData.getInstance().getEntityManager().removeEntity(this);
		}
	}

	public int getDamage() {
		return damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public int getState(){
		return state;
	}	

	public boolean isFriendly() {
		return isFriendly;
	}

	public void setFriendly(boolean isFriendly) {
		this.isFriendly = isFriendly;
	}
}
