package com.verminsnest.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.core.EntityMovementSystem;
import com.verminsnest.singletons.RuntimeData;

public class Projectile extends Entity{
	
	private int[] direction;
	protected Animation<TextureRegion> flyingAni;
	protected Animation<TextureRegion> hitAni;
	protected Animation<TextureRegion> castAni;
	protected Animation<TextureRegion> currentAni;
	protected Texture shadow;
	
	public final static int FLYING = 0;
	public final static int HIT = 1;
	public final static int CAST = 2;
	
	private int damage;
	
	public Projectile(int damage,Texture shadow, Texture flyingSheet, Texture hitSheet, Texture castSheet, int width, int height, int[] position){
		super(position, new int[]{TextureRegion.split(flyingSheet, width, height)[0][0].getRegionWidth(),TextureRegion.split(flyingSheet, width, height)[0][0].getRegionHeight()});
		
		this.setDamage(damage);
		this.shadow = shadow;
		
		TextureRegion[][] temp = TextureRegion.split(flyingSheet, width, height);
		TextureRegion[] frames = new TextureRegion[temp[0].length];
		
		for(int i = 0; i< temp[0].length; i++){
			frames[i] = temp[0][i];
		}
		flyingAni = new Animation<TextureRegion>(0.02f,frames);
	}
	
	public TextureRegion getCurrentFrame(float stateTime) {
		return currentAni.getKeyFrame(stateTime, true);
	}
	
	public Texture getShadow(){
		return shadow;
	}
	
	public void setCurrentAni(int aniKey){
		switch(aniKey){
		case FLYING:
			currentAni = flyingAni;
			break;
		case HIT:
			currentAni = hitAni;
			break;
		case CAST:
			currentAni = castAni;
			break;
		}
	}

	public void updatePosition(EntityMovementSystem sys){
		if(direction[0] > 0){
			if(!sys.moveRight(this,direction[0])){
				RuntimeData.getInstance().removeProjectile(this);
			}
		}
		else if(direction[0] < 0){
			if(!sys.moveLeft(this,direction[0]*-1)){
				RuntimeData.getInstance().removeProjectile(this);
			}
		}
		else if(direction[1] > 0){
			if(!sys.moveTop(this,direction[1])){
				RuntimeData.getInstance().removeProjectile(this);
			}
		}
		else if(direction[1] < 0){
			if(!sys.moveDown(this,direction[1]*-1)){
				RuntimeData.getInstance().removeProjectile(this);
			}
		}
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public void setDirection(int[] dir){
		this.direction = dir;
	}

	@Override
	public void dispose() {
		shadow.dispose();
		flyingAni.getKeyFrame(0).getTexture().dispose();
		//TODO do animations and dispose them
//		hitAni.getKeyFrame(0).getTexture().dispose();
//		castAni.getKeyFrame(0).getTexture().dispose();
	}
}
