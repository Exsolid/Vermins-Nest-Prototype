package com.verminsnest.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Projectile {
	
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
	private int[] position;
	
	public Projectile(int damage,Texture shadow, Texture flyingSheet, Texture hitSheet, Texture castSheet, int width, int height, int[] position){
		this.setDamage(damage);
		this.position = position;
		this.shadow = shadow;
		
		TextureRegion[][] temp = TextureRegion.split(flyingSheet, width, height);
		TextureRegion[] frames = new TextureRegion[temp[0].length];
		
		for(int i = 0; i< temp[0].length; i++){
			frames[i] = temp[0][i];
		}
		flyingAni = new Animation<TextureRegion>(0.015f,frames);
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

	public void updatePosition(){
		position[0] += direction[0];
		position[1] += direction[1];
	}
	
	public int[] getPosition() {
		return position;
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
}
