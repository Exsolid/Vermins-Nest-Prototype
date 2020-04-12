package com.verminsnest.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
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
	
	protected Vector2 leftVision;
	protected Vector2 rightVision;
	
	public Enemy(int[] pos, int textureID, int agility, int speed, int strength) {
		super(pos, textureID);
		setSpeed(speed);
		setAgility(agility);
		setStrength(strength);
		
		shadow = new Texture("textures/characters/Shadow.png");
		
		init();
		this.setCurrentAni(W_FRONT);
		this.setSize(currentAni.getKeyFrame(0).getRegionWidth(),currentAni.getKeyFrame(0).getRegionHeight());
		
		leftVision = new Vector2(pos[0]+size[0]/2, pos[1]).nor();
		leftVision.setAngle(-45);
		rightVision = new Vector2(pos[0]+size[0]/2, pos[1]).nor();
		rightVision.setAngle(-135);
 
	}
	
	public void update(){
		int range = 300;
		boolean found = false;
		for(int i = 0; i<range; i+=16){

			int yRight = (int) (pos[1]+i*rightVision.y); 
			int xLeft = 0; 
			int xRight = 0;
			int yLeft = 0; 
			int yMax = 0; 
			int xMax = 0; 
			for(Entity ent: RuntimeData.getInstance().getEntities()){
				switch(state){
				case IDLE:
				case W_FRONT:
				case A_FRONT:
					leftVision.setAngle(-45);
					rightVision.setAngle(-135);
					xLeft = (int) (pos[0]+size[0]/2+i*leftVision.x); 
					xRight = (int) (pos[0]+size[0]/2+i*rightVision.x);
					yLeft = (int) (pos[1]+size[1]/2+i*leftVision.y); 
					yMax = (int) (pos[1]+size[1]/2+range*leftVision.y); 
					if((ent.getPos()[0]<xLeft && ent.getPos()[0]>xRight || ent.getPos()[0]+ent.getSize()[0]<xLeft && ent.getPos()[0]+ent.getSize()[0]>xRight)
							&&(ent.getPos()[1]<yLeft && ent.getPos()[1]>yMax || ent.getPos()[1]+ent.getSize()[1]<yLeft && ent.getPos()[1]+ent.getSize()[1]>yMax) && ent != this){
						found = true;
					}
					break;
				case W_LEFT:
				case A_LEFT:
					leftVision.setAngle(-135);
					rightVision.setAngle(135);
					yLeft = (int) (pos[1]+size[1]/2+i*leftVision.y); 
					yRight= (int) (pos[1]+size[1]/2+i*rightVision.y); 
					xLeft = (int) (pos[0]+size[0]/2+i*leftVision.x);
					xMax = (int) (pos[0]+size[0]/2+range*leftVision.x); 
					if((ent.getPos()[1]>yLeft && ent.getPos()[1]<yRight || ent.getPos()[1]+ent.getSize()[1]>yLeft && ent.getPos()[1]+ent.getSize()[1]<yRight)
							&&(ent.getPos()[0]<xLeft && ent.getPos()[0]>xMax || ent.getPos()[0]+ent.getSize()[0]<xLeft && ent.getPos()[0]+ent.getSize()[0]>xMax) && ent != this){
						found = true;
					}
					break;
				case W_RIGHT:
				case A_RIGHT:
					leftVision.setAngle(45);
					rightVision.setAngle(-45);
					yLeft = (int) (pos[1]+size[1]/2+i*leftVision.y); 
					yRight= (int) (pos[1]+size[1]/2+i*rightVision.y); 
					xLeft = (int) (pos[0]+size[0]/2+i*leftVision.x);
					xMax = (int) (pos[0]+size[0]/2+range*leftVision.x); 
					if((ent.getPos()[1]<yLeft && ent.getPos()[1]>yRight || ent.getPos()[1]+ent.getSize()[1]<yLeft && ent.getPos()[1]+ent.getSize()[1]>yRight)
							&&(ent.getPos()[0]>xLeft && ent.getPos()[0]<xMax || ent.getPos()[0]+ent.getSize()[0]>xLeft && ent.getPos()[0]+ent.getSize()[0]<xMax) && ent != this){
						found = true;
					}
					break;
				case A_BACK:
				case W_BACK:
					leftVision.setAngle(135);
					rightVision.setAngle(45);
					xLeft = (int) (pos[0]+size[0]/2+i*leftVision.x); 
					xRight = (int) (pos[0]+size[0]/2+i*rightVision.x);
					yLeft = (int) (pos[1]+size[1]/2+i*leftVision.y); 
					yMax = (int) (pos[1]+size[1]/2+range*leftVision.y); 
					if((ent.getPos()[0]>xLeft && ent.getPos()[0]<xRight || ent.getPos()[0]+ent.getSize()[0]>xLeft && ent.getPos()[0]+ent.getSize()[0]<xRight)
							&&(ent.getPos()[1]>yLeft && ent.getPos()[1]<yMax || ent.getPos()[1]+ent.getSize()[1]>yLeft && ent.getPos()[1]+ent.getSize()[1]<yMax ) && ent != this){
						found = true;
					}
					break;
				}
			}
		}
		//TODO ANN
		if(found){
			System.out.println("FOUND");
		}else{
			System.out.println("NO");
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
