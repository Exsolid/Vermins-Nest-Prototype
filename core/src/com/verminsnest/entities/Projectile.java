package com.verminsnest.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.core.EntityMovementSystem;
import com.verminsnest.singletons.RuntimeData;

public class Projectile extends Entity{
	
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
	
	private int damage;
	private int speed;
	
	private float internalStateTime;
	private float lastStateTime;
	private boolean toReset;
	
	public Projectile(int direction, int agility,int damage,Texture shadow, Texture flyingSheet, Texture hitSheet, Texture castSheet, int width, int height, int[] position, float stateTime){
		super(position, new int[]{TextureRegion.split(flyingSheet, width, height)[0][0].getRegionWidth(),TextureRegion.split(flyingSheet, width, height)[0][0].getRegionHeight()});
		
		//Data
		this.setDamage(damage);
		speed = agility;
		this.direction = direction;		
		
		//Textures
		this.shadow = shadow;
		
		TextureRegion[][] temp = TextureRegion.split(flyingSheet, width, height);
		TextureRegion[] frames = new TextureRegion[temp[0].length];
		
		for(int i = 0; i< temp[0].length; i++){
			frames[i] = temp[0][i];
		}
		flyingAni = new Animation<TextureRegion>(0.85f/agility,frames);
		
		temp = TextureRegion.split(hitSheet, width, height);
		frames = new TextureRegion[temp[0].length];
		
		for(int i = 0; i< temp[0].length; i++){
			frames[i] = temp[0][i];
		}
		hitAni = new Animation<TextureRegion>(0.01f*agility,frames);
		
		temp = TextureRegion.split(castSheet, width, height);
		frames = new TextureRegion[temp[0].length];
		
		for(int i = 0; i< temp[0].length; i++){
			frames[i] = temp[0][i];
		}
		castAni = new Animation<TextureRegion>(0.75f/agility,frames);
		
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
	}
	
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

	public void updatePosition(EntityMovementSystem sys){
		if(state == FLYING){
			if(direction == EAST && !sys.moveRight(this,speed)){
				setCurrentAni(HIT);
			}
			else if(direction == WEST && !sys.moveLeft(this,speed)){
				setCurrentAni(HIT);
			}
			else if(direction == NORTH && !sys.moveTop(this,speed)){
				setCurrentAni(HIT);
			}
			else if(direction == SOUTH && !sys.moveDown(this,speed)){
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
			RuntimeData.getInstance().removeProjectile(this);
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
	
	@Override
	public void dispose() {
		shadow.dispose();
		flyingAni.getKeyFrame(0).getTexture().dispose();
		hitAni.getKeyFrame(0).getTexture().dispose();
		castAni.getKeyFrame(0).getTexture().dispose();
	}
}
