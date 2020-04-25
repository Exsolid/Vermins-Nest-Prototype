package com.verminsnest.entities.playables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.entities.Entity;
import com.verminsnest.singletons.RuntimeData;

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
	protected int health;
	
	protected float attackCooldown;
	protected String attackIconPath;
	
	private char currentKey;
	private char prevKey;
	
	public Playable(int textureID,int[] position, int speed, int dmg, int agi, int health){
		super(position,textureID);
		setSpeed(speed);
		setHealth(health);
		setStrength(dmg);
		setAgility(agi);
		
		prevKey = '-';
		currentKey = '-';
		
		shadow = RuntimeData.getInstance().getAsset("textures/characters/Character-Shadow.png");
		
		init();
		this.setCurrentAni(IDLE);
		this.setSize(currentAni.getKeyFrame(0).getRegionWidth(),currentAni.getKeyFrame(0).getRegionHeight());
	}
	
	public void attack(float stateTime){
		if (attackCooldown < stateTime - 1/(agility*0.2)) {
			attackAction(stateTime);
		}
	}
	
	public abstract void init();
	public abstract void attackAction(float stateTime);

	public void update(float stateTime){
		if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
			prevKey = currentKey;
			currentKey = 'S';
		}
		// D Pressed
		if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
			prevKey = currentKey;
			currentKey = 'D';
		}
		// A Pressed
		if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
			prevKey = currentKey;
			currentKey = 'A';
		}
		// W Pressed
		if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
			prevKey = currentKey;
			currentKey = 'W';
		}
		//Calculates proper movement
		switch (currentKey) {
		case 'W':
			if (!Gdx.input.isKeyPressed(Input.Keys.W)) {
				if (prevKey != '-') {
					currentKey = prevKey;
					prevKey = '-';
				} else {
					currentKey = '-';
				}
			}
			RuntimeData.getInstance().getMovmentSystem().moveTop(this, this.getSpeed());
			this.setCurrentAni(Playable.W_BACK);
			break;
		case 'D':
			if (!Gdx.input.isKeyPressed(Input.Keys.D)) {
				if (prevKey != '-') {
					currentKey = prevKey;
					prevKey = '-';
				} else {
					currentKey = '-';
				}
			}
			RuntimeData.getInstance().getMovmentSystem().moveRight(this, this.getSpeed());
			this.setCurrentAni(Playable.W_RIGHT);
			break;
		case 'S':
			if (!Gdx.input.isKeyPressed(Input.Keys.S)) {
				if (prevKey != '-') {
					currentKey = prevKey;
					prevKey = '-';
				} else {
					currentKey = '-';
				}
			}
			RuntimeData.getInstance().getMovmentSystem().moveDown(this, this.getSpeed());
			this.setCurrentAni(Playable.W_FRONT);
			break;
		case 'A':
			if (!Gdx.input.isKeyPressed(Input.Keys.A)) {
				if (prevKey != '-') {
					currentKey = prevKey;
					prevKey = '-';
				} else {
					currentKey = '-';
				}
			}
			RuntimeData.getInstance().getMovmentSystem().moveLeft(this, this.getSpeed());
			this.setCurrentAni(Playable.W_LEFT);
			break;
		case '-':
			this.setCurrentAni(Playable.IDLE);
			prevKey = '-';
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
	
	@Override
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

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public String getAttackIcon() {
		return attackIconPath;
	}
	
	public float[] getAttackDetails(float stateTime){
		float cd = (float) (attackCooldown+1/(agility*0.2)-stateTime);
		if(cd <0) cd = 0;
		return new float[]{cd ,(float) (1/(agility*0.2))};
	}
}
