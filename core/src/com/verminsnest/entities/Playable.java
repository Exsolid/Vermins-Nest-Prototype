package com.verminsnest.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Playable {
	
	protected Animation<TextureRegion> frontWalkAni;
	protected Animation<TextureRegion> backWalkAni;
	protected Animation<TextureRegion> rightWalkAni;
	protected Animation<TextureRegion> leftWalkAni;
	protected Animation<TextureRegion> idleAni;
	protected Animation<TextureRegion> currentAni;
	protected Texture shadowTexture;
	
	public final static int W_FRONT = 0;
	public final static int W_BACK = 1;
	public final static int W_LEFT = 2;
	public final static int W_RIGHT = 3;
	public final static int IDLE = 4;
	
	protected int[] pos;	
	protected int speed;
	protected int agility;
	protected int strength;
	
	
	public Playable(int[] position,int speed, int dmg, int agi){
		setSpeed(speed);
		setStrength(dmg);
		setAgility(agi);
		
		setPos(position);
		
		shadowTexture = new Texture("textures/characters/Shadow.png");
		
		init();
	}
	
	public abstract void init();

	public int[] getPos() {
		return pos;
	}

	public void setPos(int[] pos) {
		this.pos = pos;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getAgility() {
		return agility;
	}

	public void setAgility(int agility) {
		this.agility = agility;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public TextureRegion getCurrentFrame(float stateTime) {
		return currentAni.getKeyFrame(stateTime, true);
	}
	
	public Texture getShadow(){
		return shadowTexture;
	}
	
	public void setCurrentAni(int animationKey) {
		switch (animationKey){
		case W_FRONT:
			currentAni = frontWalkAni;
			break;
		case W_BACK:
			currentAni = backWalkAni;
			break;
		case W_LEFT:
			currentAni = leftWalkAni;
			break;
		case W_RIGHT:
			currentAni = rightWalkAni;
			break;
		case IDLE:
			currentAni = idleAni;
			break;
		}
	}
	
}
