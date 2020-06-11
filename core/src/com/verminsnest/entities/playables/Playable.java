package com.verminsnest.entities.playables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.verminsnest.core.management.Indentifiers;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.entities.Entity;

public abstract class Playable extends Entity {
	
	protected Animation<TextureRegion> frontWalkAni;
	protected Animation<TextureRegion> backWalkAni;
	protected Animation<TextureRegion> rightWalkAni;
	protected Animation<TextureRegion> leftWalkAni;
	protected Animation<TextureRegion> idleAni;
	
	protected int level;
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
	
	private Vector2 topLeft;
	private Vector2 bottomLeft;
	private Vector2 topRight;
	private Vector2 bottomRight;
	
	public Playable(int textureID,int[] position, int speed, int dmg, int agi, int health){
		super(position,textureID);
		setSpeed(speed);
		setHealth(health);
		setMaxHealth(health);
		setStrength(dmg);
		setAgility(agi);
		
		skillPoints = 2;
		killCount = 0;
		killLimit = 10;
		level = 5;
		
		prevKey = '-';
		currentKey = '-';
		
		shadow = RuntimeData.getInstance().getAsset("textures/characters/Character-Shadow.png");
		
		init();
		this.setCurrentAni(Indentifiers.STATE_IDLE);
		this.setSize(currentAni.getKeyFrame(0).getRegionWidth(),currentAni.getKeyFrame(0).getRegionHeight());
		
		topLeft = new Vector2(RuntimeData.getInstance().getGame().getConfig().getResolution()[0]/2,(RuntimeData.getInstance().getGame().getConfig().getResolution()[1]+25)/2).nor();
		topLeft.setAngle(135);
		bottomLeft = new Vector2(RuntimeData.getInstance().getGame().getConfig().getResolution()[0]/2,(RuntimeData.getInstance().getGame().getConfig().getResolution()[1]+25)/2).nor();
		bottomLeft.setAngle(-135);
		topRight = new Vector2(RuntimeData.getInstance().getGame().getConfig().getResolution()[0]/2,(RuntimeData.getInstance().getGame().getConfig().getResolution()[1]+25)/2).nor();
		topRight.setAngle(45);
		bottomRight = new Vector2(RuntimeData.getInstance().getGame().getConfig().getResolution()[0]/2,(RuntimeData.getInstance().getGame().getConfig().getResolution()[1]+25)/2).nor();
		bottomRight.setAngle(-45);
	}
	
	public void attack(float delta, int direction){
		if (attackCooldown > 1/(agility*0.2)) {
			attackAction(delta, direction);
		}
	}
	
	public abstract void init();
	public abstract void attackAction(float delta, int direction);

	public void update(float delta){
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
			RuntimeData.getInstance().getMovmentSystem().moveTop(this, this.getSpeed(), null);
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
			RuntimeData.getInstance().getMovmentSystem().moveRight(this, this.getSpeed(), null);
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
			RuntimeData.getInstance().getMovmentSystem().moveDown(this, this.getSpeed(), null);
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
			RuntimeData.getInstance().getMovmentSystem().moveLeft(this, this.getSpeed(), null);
			this.setCurrentAni(Indentifiers.STATE_WALK_WEST);
			break;
		case '-':
			this.setCurrentAni(Indentifiers.STATE_IDLE);
			prevKey = '-';
		}		
		
		//Attacking
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			float mouseX =RuntimeData.getInstance().getMousePosInGameWorld().x;
			float mouseY =RuntimeData.getInstance().getMousePosInGameWorld().y;
			Vector2 mouseVector = new Vector2(mouseX-pos[0]+size[0]/2,mouseY-pos[1]+size[1]/2).nor();
			if(mouseVector.y - bottomLeft.y >= 0 && mouseVector.y - topLeft.y <= 0 && mouseX < pos[0]+size[0]/2){
				RuntimeData.getInstance().getEntityManager().getCharacter().attack(delta, Indentifiers.DIRECTION_WEST);
			}else if(mouseVector.y - bottomRight.y >= 0 && mouseVector.y - topRight.y <= 0&& mouseX > pos[0]+size[0]/2){
				RuntimeData.getInstance().getEntityManager().getCharacter().attack(delta, Indentifiers.DIRECTION_EAST);
			}if(mouseVector.x - topRight.x <= 0 && mouseVector.x - topLeft.x >= 0 && mouseY > pos[1]+size[1]/2){
				RuntimeData.getInstance().getEntityManager().getCharacter().attack(delta, Indentifiers.DIRECTION_NORTH);
			}else if(mouseVector.x - bottomRight.x <= 0 && mouseVector.x - bottomLeft.x >= 0 && mouseY < pos[1]+size[1]/2){
				RuntimeData.getInstance().getEntityManager().getCharacter().attack(delta, Indentifiers.DIRECTION_SOUTH);
			}	
		}
		attackCooldown += delta;
		internalStateTime += delta;
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
	
	public float[] getAttackDetails(){
		if(attackCooldown >1/(agility*0.2))return new float[]{(float) (1/(agility*0.2)) ,(float) (1/(agility*0.2))};
		else return new float[]{attackCooldown ,(float) (1/(agility*0.2))};
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
	
	public void updateKills(){
		if(killCount+1 == killLimit){
			killCount = 0;
			killLimit += 5;
			level++;
			skillPoints += 2;
		}else{
			killCount++;
		}
	}
	
	public int[] getLevelData(){
		return new int[]{level, killCount, killLimit};
	}
}
