package com.verminsnest.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.entities.Entity;
import com.verminsnest.singletons.RuntimeData;

public abstract class Enemy extends Entity {
	protected Animation<TextureRegion> frontAttackAni;
	protected Animation<TextureRegion> backAttackAni;
	protected Animation<TextureRegion> rightAttackAni;
	protected Animation<TextureRegion> leftAttackAni;
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
	public final static int A_FRONT = 5;
	public final static int A_BACK = 6;
	public final static int A_LEFT = 7;
	public final static int A_RIGHT = 8;
	protected int state;
	
	protected int speed;
	protected int agility;
	protected int strength;
	
	public Enemy(int[] pos, int textureID, int agility, int speed, int strength) {
		super(pos, textureID);
		setSpeed(speed);
		setAgility(agility);
		setStrength(strength);
		
		shadow = new Texture("textures/characters/Shadow.png");
		
		init();
		this.setCurrentAni(IDLE);
		this.setSize(currentAni.getKeyFrame(0).getRegionWidth(),currentAni.getKeyFrame(0).getRegionHeight());
	}
	
	public void update(){
		for(Entity ent: RuntimeData.getInstance().getEntities()){
			if(ent != this){
				if(ent.getPos()[1] > this.getPos()[1]-400 &&ent.getPos()[1] < this.getPos()[1] && ent.getPos()[0] < this.getPos()[0]+this.getSize()[0]&& ent.getPos()[0] > this.getPos()[0]){
					setCurrentAni(W_BACK);
				}else{
					setCurrentAni(IDLE);
				}
			}
		}
	}
	
	@Override
	public void setCurrentAni(int animationKey) {
		switch (animationKey){
		case A_FRONT:
			currentAni = frontWalkAni;
			state = A_FRONT;
			break;
		case A_BACK:
			currentAni = backWalkAni;
			state = A_BACK;
			break;
		case A_LEFT:
			currentAni = leftWalkAni;
			state = A_LEFT;
			break;
		case A_RIGHT:
			currentAni = rightWalkAni;
			state = A_RIGHT;
			break;
		case W_FRONT:
			currentAni = frontWalkAni;
			state = W_FRONT;
			break;
		case W_BACK:
			currentAni = backWalkAni;
			state = W_BACK;
			break;
		case W_LEFT:
			currentAni = leftWalkAni;
			state = W_LEFT;
			break;
		case W_RIGHT:
			currentAni = rightWalkAni;
			state = W_RIGHT;
			break;
		case IDLE:
			currentAni = idleAni;
			state = IDLE;
			break;
		}
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
}
