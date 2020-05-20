package com.verminsnest.entities.playables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.core.Indentifiers;
import com.verminsnest.core.singletons.RuntimeData;
import com.verminsnest.entities.Entity;

public abstract class Playable extends Entity {
	
	protected Animation<TextureRegion> frontWalkAni;
	protected Animation<TextureRegion> backWalkAni;
	protected Animation<TextureRegion> rightWalkAni;
	protected Animation<TextureRegion> leftWalkAni;
	protected Animation<TextureRegion> idleAni;
	
	protected int killCount;
	protected int killLimit;
	protected int skillPoints;
	
	protected int speed;
	protected int agility;
	protected int strength;
	protected int health;
	protected int maxHealth;
	
	protected float attackCooldown;
	protected String attackIconPath;
	
	private char currentKey;
	private char prevKey;
	
	public Playable(int textureID,int[] position, int speed, int dmg, int agi, int health){
		super(position,textureID);
		setSpeed(speed);
		setHealth(health);
		setMaxHealth(health);
		setStrength(dmg);
		setAgility(agi);
		
		skillPoints = 2;
		
		prevKey = '-';
		currentKey = '-';
		
		shadow = RuntimeData.getInstance().getAsset("textures/characters/Character-Shadow.png");
		
		init();
		this.setCurrentAni(Indentifiers.STATE_IDLE);
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
			this.setCurrentAni(Indentifiers.STATE_WALK_NORTH);
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
			this.setCurrentAni(Indentifiers.STATE_WALK_EAST);
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
			this.setCurrentAni(Indentifiers.STATE_WALK_SOUTH);
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
			this.setCurrentAni(Indentifiers.STATE_WALK_WEST);
			break;
		case '-':
			this.setCurrentAni(Indentifiers.STATE_IDLE);
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
		case Indentifiers.STATE_WALK_SOUTH:
			currentAni = frontWalkAni;
			state = Indentifiers.STATE_WALK_SOUTH;
			break;
		case Indentifiers.STATE_WALK_NORTH:
			currentAni = backWalkAni;
			state = Indentifiers.STATE_WALK_NORTH;
			break;
		case Indentifiers.STATE_WALK_WEST:
			currentAni = leftWalkAni;
			state = Indentifiers.STATE_WALK_WEST;
			break;
		case Indentifiers.STATE_WALK_EAST:
			currentAni = rightWalkAni;
			state = Indentifiers.STATE_WALK_EAST;
			break;
		case Indentifiers.STATE_IDLE:
			currentAni = idleAni;
			state = Indentifiers.STATE_IDLE;
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

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
	
	public int getSkillPoints(){
		return skillPoints;
	}
	
	public void setSkilPoints(int skillPoints){
		this.skillPoints = skillPoints;
	}
}
