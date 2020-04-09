package com.verminsnest.entities.playables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.entities.Entity;

public abstract class Playable extends Entity {
	
	protected Animation<TextureRegion> frontWalkAni;
	protected Animation<TextureRegion> backWalkAni;
	protected Animation<TextureRegion> rightWalkAni;
	protected Animation<TextureRegion> leftWalkAni;
	protected Animation<TextureRegion> idleAni;
	
	public final static int W_FRONT = 0;
	public final static int W_BACK = 1;
	public final static int W_LEFT = 2;
	public final static int W_RIGHT = 3;
	public final static int IDLE = 4;
	protected int currentDir;
	
	
	protected int speed;
	protected int agility;
	protected int strength;
	
	protected float lastAttack;
	
	
	public Playable(int textureID,int[] position, int speed, int dmg, int agi){
		super(position,textureID);
		setSpeed(speed);
		setStrength(dmg);
		setAgility(agi);
		
		shadow = new Texture("textures/characters/Shadow.png");
		
		init();
		this.setCurrentAni(IDLE);
		this.setSize(currentAni.getKeyFrame(0).getRegionWidth(),currentAni.getKeyFrame(0).getRegionHeight());
	}
	
	public abstract void init();
	
	public abstract void attack(float stateTime);

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
	
	public void setCurrentAni(int animationKey) {
		switch (animationKey){
		case W_FRONT:
			currentAni = frontWalkAni;
			currentDir = W_FRONT;
			break;
		case W_BACK:
			currentAni = backWalkAni;
			currentDir = W_BACK;
			break;
		case W_LEFT:
			currentAni = leftWalkAni;
			currentDir = W_LEFT;
			break;
		case W_RIGHT:
			currentAni = rightWalkAni;
			currentDir = W_RIGHT;
			break;
		case IDLE:
			currentAni = idleAni;
			currentDir = IDLE;
			break;
		}
	}
}
