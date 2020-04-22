package com.verminsnest.entities.projectiles;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.entities.Entity;
import com.verminsnest.singletons.RuntimeData;

public abstract class Projectile extends Entity{
	
	public final static int NORTH = 0;
	public final static int WEST = 1;
	public final static int EAST = 2;
	public final static int SOUTH = 3;
	private int direction;
	private int rotation;
	
	protected Animation<TextureRegion> flyingAni;
	protected Animation<TextureRegion> hitAni;
	protected Animation<TextureRegion> castAni;
	public final static int FLYING = 0;
	public final static int HIT = 1;
	public final static int CAST = 2;
	public final static int TODELETE = 3;
	private int state;
	
	protected int damage;
	protected int speed;
	
	private float internalStateTime;
	private float lastStateTime;
	private boolean toReset;
	
	public static String iconPath;
	
	public Projectile(int textureID, int direction, int agility,int damage, int[] position, float stateTime){
		super(position, textureID);
		
		//Data
		this.setDamage(damage);
		speed = agility;
		this.direction = direction;		
		//Texture orientation
		switch(direction){
		case SOUTH:
			rotation = 180;
			break;
		case WEST:
			rotation = 90;
			break;
		case EAST:
			rotation = -90;
			break;
		}
		internalStateTime = 0;
		lastStateTime = stateTime;
		init();
		this.setCurrentAni(CAST);
		this.setSize(currentAni.getKeyFrame(0).getRegionWidth(),currentAni.getKeyFrame(0).getRegionHeight());
	}
	
	public abstract void init();
	
	public TextureRegion getCurrentFrame(float stateTime) {
		if(toReset){
			lastStateTime=stateTime;
			toReset = false;
		}
		internalStateTime = stateTime -lastStateTime;
		if(state == CAST){
			if(currentAni.isAnimationFinished(internalStateTime)){
				setCurrentAni(FLYING);
			}
			return currentAni.getKeyFrame(internalStateTime, false);
		}else if(state == FLYING){
			return currentAni.getKeyFrame(internalStateTime, true);
		}
		else if(state == HIT ){
			if(currentAni.isAnimationFinished(internalStateTime)){
				state = TODELETE;
			}
			return currentAni.getKeyFrame(internalStateTime, false);
		}else if(state == TODELETE){
			return currentAni.getKeyFrame(currentAni.getAnimationDuration(), false);
		}
		return null;
	}
	
	public void setCurrentAni(int aniKey){
		switch(aniKey){
		case FLYING:
			currentAni = flyingAni;
			state = FLYING;
			toReset = true;
			break;
		case HIT:
			currentAni = hitAni;
			state = HIT;
			toReset = true;
			break;
		case CAST:
			currentAni = castAni;
			state = CAST;
			toReset = true;
			break;
		}
	}

	public void update(){
		if(state == FLYING){
			if(direction == EAST && !RuntimeData.getInstance().getMovmentSystem().moveRight(this,speed)){
				setCurrentAni(HIT);
			}
			else if(direction == WEST && !RuntimeData.getInstance().getMovmentSystem().moveLeft(this,speed)){
				setCurrentAni(HIT);
			}
			else if(direction == NORTH && !RuntimeData.getInstance().getMovmentSystem().moveTop(this,speed)){
				setCurrentAni(HIT);
			}
			else if(direction == SOUTH && !RuntimeData.getInstance().getMovmentSystem().moveDown(this,speed)){
				setCurrentAni(HIT);
			}
		}else if(state == HIT){
			if(direction == WEST){
				pos[0] -= speed;
			}else if(direction == EAST){
				pos[0] += speed;
			}else if(direction == SOUTH){
				pos[1] -= speed;
			}else if(direction == NORTH){
				pos[1] += speed;
			}
		}else if(state == TODELETE){
			RuntimeData.getInstance().removeEntity(this);
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
	
	public int getRotation() {
		if(state != CAST){
			return rotation;
		}else{
			return 0;
		}
	}
}
