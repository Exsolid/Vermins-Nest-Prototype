package com.verminsnest.entities.enemies;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.verminsnest.entities.Entity;
import com.verminsnest.entities.playables.Playable;
import com.verminsnest.entities.projectiles.Projectile;
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
	protected int health;
	
	private Vector2 leftVision;
	private Vector2 rightVision;
	protected Entity alerted;
	protected Entity playerAlerted;
	private float timer;
	private int lastDirCount;
	protected boolean movedLeftOf;
	protected boolean movedRightOf;
	
	public Enemy(int[] pos, int textureID, int agility, int speed, int strength, int health) {
		super(pos, textureID);
		setSpeed(speed);
		setHealth(health);
		setAgility(agility);
		setStrength(strength);
		
		shadow = RuntimeData.getInstance().getAsset("textures/enemies/Shadow.png");
		
		init();
		this.setCurrentAni(IDLE);
		this.setSize(currentAni.getKeyFrame(0).getRegionWidth(),currentAni.getKeyFrame(0).getRegionHeight());
		
		leftVision = new Vector2(pos[0]+size[0]/2, pos[1]).nor();
		leftVision.setAngle(-45);
		rightVision = new Vector2(pos[0]+size[0]/2, pos[1]).nor();
		rightVision.setAngle(-135);
 
	}
	
	public void update(){
		updateVision();
		updateAction();
		timer += Gdx.graphics.getDeltaTime();
	}
	protected abstract void chooseAvoidAction(int xDistance, int yDistance);
	protected abstract void chooseAgressiveAction(int xDistance, int yDistance);
	private void updateAction(){
		if(alerted != null && alerted instanceof Playable){
			if(timer < 3){
				chooseAgressiveAction(this.pos[0]-alerted.getPos()[0],this.pos[1]-alerted.getPos()[1]);
			}else{
				playerAlerted = null;
				alerted = null;
				lastDirCount = -1;
				setCurrentAni(IDLE);
			}
		}else if(alerted != null && (alerted instanceof Projectile || alerted instanceof Enemy)){
			if(timer < 1){
				chooseAvoidAction(this.pos[0]-alerted.getPos()[0],this.pos[1]-alerted.getPos()[1]);
			}else{
				if(playerAlerted != null){
					alerted = playerAlerted;
				}else{
					alerted = null;
				}
				lastDirCount = -1;
				setCurrentAni(IDLE);
			}
		}
	}
	
	private void updateVision(){
		int range = 300;
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
						if(!(ent instanceof Enemy)){
							alerted = ent;
							if(ent instanceof Playable){
								playerAlerted = ent;
							}
							timer= 0;
						}
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
						if(!(ent instanceof Enemy)){
							alerted = ent;
							if(ent instanceof Playable){
								playerAlerted = ent;
							}
							timer= 0;
						}
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
						if(!(ent instanceof Enemy)){
							alerted = ent;
							if(ent instanceof Playable){
								playerAlerted = ent;
							}
							timer= 0;
						}
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
						if(!(ent instanceof Enemy)){
								alerted = ent;
							if(ent instanceof Playable){
								playerAlerted = ent;
							}
							timer= 0;
						}
					}
					break;
				}
			}
		}
	}
	
	protected void walkLeftOf(){
		int x = Math.abs(alerted.getPos()[0]-this.pos[0])+1;
		int y = Math.abs(alerted.getPos()[1]-this.pos[1])+1;
			if(!movedLeftOf){
				if(alerted.getPos()[0]>this.getPos()[0] && alerted.getPos()[1]>this.getPos()[1] && x < y&& ((Projectile)alerted).getRotation() == 180){
					 RuntimeData.getInstance().getMovmentSystem().moveLeft(this, speed);
					 setCurrentAni(W_LEFT);
					 lastDirCount = 4;
					 movedLeftOf = true;
				 }else if(alerted.getPos()[0]<this.getPos()[0] && alerted.getPos()[1]<this.getPos()[1] && x < y&& ((Projectile)alerted).getRotation() == 0){
					 RuntimeData.getInstance().getMovmentSystem().moveRight(this, speed);
					 setCurrentAni(W_RIGHT);
					 lastDirCount = 4;
					 movedLeftOf = true;
				 }else if(alerted.getPos()[0]<this.getPos()[0] && alerted.getPos()[1]>this.getPos()[1] && x > y&& ((Projectile)alerted).getRotation() == -90){
					 RuntimeData.getInstance().getMovmentSystem().moveDown(this, speed);
					 setCurrentAni(W_FRONT);
					 lastDirCount = 4;
					 movedLeftOf = true;
				 }else if(alerted.getPos()[0]>this.getPos()[0] && alerted.getPos()[1]<this.getPos()[1] && x > y&& ((Projectile)alerted).getRotation() == 90){
					 RuntimeData.getInstance().getMovmentSystem().moveTop(this, speed);
					 setCurrentAni(W_BACK);
					 lastDirCount = 4;
					 movedLeftOf = true;
				 }
			}else{
				switch (state){
				case IDLE:
				case W_FRONT:
					RuntimeData.getInstance().getMovmentSystem().moveDown(this,speed);
					break;
				case W_BACK:
					 RuntimeData.getInstance().getMovmentSystem().moveTop(this,speed);
					break;
				case W_LEFT:
					 RuntimeData.getInstance().getMovmentSystem().moveLeft(this,speed);
					break;
				case W_RIGHT:
					 RuntimeData.getInstance().getMovmentSystem().moveRight(this,speed);
					break;
				}
				lastDirCount--;
				if(lastDirCount < 0){
					movedLeftOf = false;
			}
		}
	}
	
	protected void walkRightOf(){
		int x = Math.abs(alerted.getPos()[0]-this.pos[0])+1;
		int y = Math.abs(alerted.getPos()[1]-this.pos[1])+1;

		if(alerted instanceof Projectile){
				if(!movedRightOf){
					 if(alerted.getPos()[0]>=this.getPos()[0] && alerted.getPos()[1]>=this.getPos()[1] && x > y && ((Projectile)alerted).getRotation() == 90){
						 RuntimeData.getInstance().getMovmentSystem().moveDown(this, speed);
						 setCurrentAni(W_FRONT);
						 lastDirCount = 3;
						 movedRightOf = true;
					 }else if(alerted.getPos()[0]<=this.getPos()[0] && alerted.getPos()[1]<=this.getPos()[1] && x > y && ((Projectile)alerted).getRotation() == -90){
						 RuntimeData.getInstance().getMovmentSystem().moveTop(this, speed);
						 setCurrentAni(W_BACK);
						 lastDirCount = 3;
						 movedRightOf = true;
					 }else if(alerted.getPos()[0]<=this.getPos()[0] && alerted.getPos()[1]>=this.getPos()[1] && x < y && ((Projectile)alerted).getRotation() == 180){
						 RuntimeData.getInstance().getMovmentSystem().moveRight(this, speed);
						 setCurrentAni(W_RIGHT);
						 lastDirCount = 3;
						 movedRightOf = true;
					 }else if(alerted.getPos()[0]>=this.getPos()[0] && alerted.getPos()[1]<=this.getPos()[1] && x < y&& ((Projectile)alerted).getRotation() == 0){
						 RuntimeData.getInstance().getMovmentSystem().moveLeft(this, speed);
						 setCurrentAni(W_LEFT);
						 lastDirCount = 3;
						 movedRightOf = true;
					 }
				}else{
					switch (state){
					case IDLE:
					case W_FRONT:
						RuntimeData.getInstance().getMovmentSystem().moveDown(this,speed);
						break;
					case W_BACK:
						 RuntimeData.getInstance().getMovmentSystem().moveTop(this,speed);
						break;
					case W_LEFT:
						 RuntimeData.getInstance().getMovmentSystem().moveLeft(this,speed);
						break;
					case W_RIGHT:
						 RuntimeData.getInstance().getMovmentSystem().moveRight(this,speed);
						break;
					}
					lastDirCount--;
					if(lastDirCount < 0){
						movedRightOf = false;
				}
			}
		}
	}
	
	protected void walkTowards(){
		if(lastDirCount < 0){
			Random rand = new Random();
			 int x = Math.abs(alerted.getPos()[0]-this.pos[0])+1;
			 int y = Math.abs(alerted.getPos()[1]-this.pos[1])+1;
			 int randX = rand.nextInt(x)-1;
			 int randY = rand.nextInt(y)-1;
			 if(randX >= randY){
				 if(alerted.getPos()[0]>this.pos[0]){
					 RuntimeData.getInstance().getMovmentSystem().moveRight(this,speed);
					 setCurrentAni(W_RIGHT);
				 }else{
					 RuntimeData.getInstance().getMovmentSystem().moveLeft(this,speed);
					 setCurrentAni(W_LEFT);
				 }
			 }else{
				 if(alerted.getPos()[1]>this.pos[1]){
					 RuntimeData.getInstance().getMovmentSystem().moveTop(this,speed);
					 setCurrentAni(W_BACK);
				 }else{
					 RuntimeData.getInstance().getMovmentSystem().moveDown(this,speed);
					 setCurrentAni(W_FRONT);
				 }
			 }
			 lastDirCount = 4;
		}else{
			switch (state){
			case IDLE:
			case W_FRONT:
				RuntimeData.getInstance().getMovmentSystem().moveDown(this,speed);
				break;
			case W_BACK:
				 RuntimeData.getInstance().getMovmentSystem().moveTop(this,speed);
				break;
			case W_LEFT:
				 RuntimeData.getInstance().getMovmentSystem().moveLeft(this,speed);
				break;
			case W_RIGHT:
				 RuntimeData.getInstance().getMovmentSystem().moveRight(this,speed);
				break;
			}
			lastDirCount--;
			
		}
	}
	
	protected void walkAway(){
		if(lastDirCount < 0){
			Random rand = new Random();
			 int x = Math.abs(alerted.getPos()[0]-this.pos[0])+1;
			 int y = Math.abs(alerted.getPos()[1]-this.pos[1])+1;
			 int randX = rand.nextInt(x)-1;
			 int randY = rand.nextInt(y)-1;
			 if(randX > randY){
				 if(alerted.getPos()[0]>this.pos[0]){
					 RuntimeData.getInstance().getMovmentSystem().moveLeft(this,speed);
					 setCurrentAni(W_LEFT);
				 }else{
					 RuntimeData.getInstance().getMovmentSystem().moveRight(this,speed);
					 setCurrentAni(W_RIGHT);
				 }
			 }else{
				 if(alerted.getPos()[1]>this.pos[1]){
					 RuntimeData.getInstance().getMovmentSystem().moveDown(this,speed);
					 setCurrentAni(W_FRONT);
				 }else{
					 RuntimeData.getInstance().getMovmentSystem().moveTop(this,speed);
					 setCurrentAni(W_BACK);
				 }
			 }   
			 lastDirCount = 4;
		}else{
			switch (state){
			case IDLE:
			case W_FRONT:
				RuntimeData.getInstance().getMovmentSystem().moveDown (this,speed);
				break;
			case W_BACK:
				 RuntimeData.getInstance().getMovmentSystem().moveTop(this,speed);
				break;
			case W_LEFT:
				 RuntimeData.getInstance().getMovmentSystem().moveLeft(this,speed);
				break;
			case W_RIGHT:
				 RuntimeData.getInstance().getMovmentSystem().moveRight(this,speed);
				break;
			}
			lastDirCount--;
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

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
}
