package com.verminsnest.entities.enemies;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.verminsnest.core.Indentifiers;
import com.verminsnest.core.singletons.RuntimeData;
import com.verminsnest.entities.Entity;
import com.verminsnest.entities.playables.Playable;
import com.verminsnest.entities.projectiles.Projectile;

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
	
	protected float lastAttack;
	
	private boolean isLastDeath;
	private boolean isReadyToDig;
	
	public Enemy(int[] pos, int textureID, int agility, int speed, int strength, int health) {
		super(pos, textureID);
		setSpeed(speed);
		setHealth(health);
		setAgility(agility);
		setStrength(strength);
		
		shadow = RuntimeData.getInstance().getAsset("textures/enemies/Shadow.png");
		
		init();
		this.setCurrentAni(Indentifiers.STATE_IDLE);
		this.setSize(currentAni.getKeyFrame(0).getRegionWidth(),currentAni.getKeyFrame(0).getRegionHeight());
		
		leftVision = new Vector2(pos[0]+size[0]/2, pos[1]).nor();
		leftVision.setAngle(-45);
		rightVision = new Vector2(pos[0]+size[0]/2, pos[1]).nor();
		rightVision.setAngle(-135);
 
	}
	
	public void update(float stateTime){
		if(isLastDeath) {
			int[] thisMapPos = new int[2];
			int[] playerMapPos = new int[2];
			int[] goalPos = new int[2];
			
			thisMapPos[0] = (this.getPos()[0]-this.getPos()[0]%128)/128;
			thisMapPos[1] = (this.getPos()[1]-this.getPos()[1]%128)/128;
			
			playerMapPos[0] = (RuntimeData.getInstance().getCharacter().getPos()[0]-RuntimeData.getInstance().getCharacter().getPos()[0]%128)/128;
			playerMapPos[1] = (RuntimeData.getInstance().getCharacter().getPos()[1]-RuntimeData.getInstance().getCharacter().getPos()[1]%128)/128;
			
			goalPos[0] = this.getPos()[0]-this.getPos()[0]%128+128/2-size[0]/2;
			goalPos[1] = this.getPos()[1]-this.getPos()[1]%128+128/2-size[1]/2;
			
			if(playerMapPos[0] == thisMapPos[0] && playerMapPos[1] == thisMapPos[1]) {
				boolean[] dirs = RuntimeData.getInstance().getMapData().getWalkableDirs(thisMapPos);
				if(dirs[0]) {
					RuntimeData.getInstance().getMovmentSystem().moveTop(this, 1);
				}
				if(dirs[1]) {
					RuntimeData.getInstance().getMovmentSystem().moveRight(this, 1);
				}
				if(dirs[2]) {
					RuntimeData.getInstance().getMovmentSystem().moveDown(this, 1);
				}
				if(dirs[3]) {
					RuntimeData.getInstance().getMovmentSystem().moveRight(this, 1);
				}
			}else{
				int[] movement = new int[2];
				if(goalPos[0] > pos[0]) {
					movement[0] = +1;
					movement[1] = 0;
					setCurrentAni(Indentifiers.STATE_WALK_EAST);
				}
				else if(goalPos[0] < pos[0]) {
					movement[0] = -1;
					movement[1] = 0;
					setCurrentAni(Indentifiers.STATE_WALK_WEST);
				}else if(goalPos[1] > pos[1]) {
					movement[1] = +1;
					movement[0] = 0;
					setCurrentAni(Indentifiers.STATE_WALK_NORTH);
				}else if(goalPos[1] < pos[1]) {
					movement[1] = -1;
					movement[0] = 0;
					setCurrentAni(Indentifiers.STATE_WALK_SOUTH);
				}
				
				RuntimeData.getInstance().getMovmentSystem().move(this, movement);
				
				if(goalPos[0] == pos[0] && goalPos[1] == pos[1])isReadyToDig = true;
			}
			
			
		}else {
			updateVision();
			updateAction(stateTime);
		}
		timer += Gdx.graphics.getDeltaTime();
	}
	protected abstract void chooseAvoidAction(int xDistance, int yDistance, float stateTime);
	protected abstract void chooseAgressiveAction(int xDistance, int yDistance, float stateTime);
	protected abstract void attack(float stateTime);
	
	private void updateAction(float stateTime){
		if(alerted != null && alerted instanceof Playable){
			if(timer < 3){
				int xDis = 0;
				int yDis = 0;
				switch(state){
				case Indentifiers.STATE_WALK_SOUTH:
				case Indentifiers.STATE_ATTACK_SOUTH:
				case Indentifiers.STATE_IDLE:
					yDis = alerted.getPos()[1]+alerted.getSize()[1]-this.pos[1];
					if(Math.abs(this.pos[0]- alerted.getPos()[0])<this.getSize()[0]){
						xDis = 0;
					}else{
						xDis =999;
					}
					break;
				case Indentifiers.STATE_WALK_NORTH:
				case Indentifiers.STATE_ATTACK_NORTH:
					yDis = alerted.getPos()[1]-(this.pos[1]+this.size[1]);
					if(Math.abs(this.pos[0]- alerted.getPos()[0])<this.getSize()[0]){
						xDis = 0;
					}else{
						xDis =999;
					}
					break;
				case Indentifiers.STATE_WALK_WEST:
				case Indentifiers.STATE_ATTACK_WEST:
					xDis = alerted.getPos()[0]+alerted.getSize()[0]-this.pos[0];
					if(Math.abs(this.pos[1]- alerted.getPos()[1])<this.getSize()[1]){
						yDis = 0;
					}else{
						yDis =999;
					}
					break;
				case Indentifiers.STATE_WALK_EAST:
				case Indentifiers.STATE_ATTACK_EAST:
					xDis = alerted.getPos()[0]-(this.pos[0]+this.size[0]);
					if(Math.abs(this.pos[1]- alerted.getPos()[1])<this.getSize()[1]){
						yDis = 0;
					}else{
						yDis =999;
					}
					break;
				}
				chooseAgressiveAction(xDis,yDis,stateTime);
			}else{
				playerAlerted = null;
				alerted = null;
				lastDirCount = -1;
				setCurrentAni(Indentifiers.STATE_IDLE);
			}
		}else if(alerted != null && (alerted instanceof Projectile || alerted instanceof Enemy)){
			if(timer < 1){
				int xDis = 0;
				int yDis = 0;
				switch(state){
				case Indentifiers.STATE_WALK_SOUTH:
				case Indentifiers.STATE_ATTACK_SOUTH:				
				case Indentifiers.STATE_WALK_NORTH:
				case Indentifiers.STATE_ATTACK_NORTH:
					yDis = alerted.getPos()[1]+alerted.getSize()[1]-this.pos[1];
					xDis = alerted.getPos()[0]+alerted.getSize()[0]/2- this.pos[0]+alerted.getSize()[0]/2;
					break;
				case Indentifiers.STATE_WALK_WEST:
				case Indentifiers.STATE_ATTACK_WEST:				
				case Indentifiers.STATE_WALK_EAST:
				case Indentifiers.STATE_ATTACK_EAST:
					xDis = alerted.getPos()[0]+alerted.getSize()[0]-this.pos[1];
					yDis = alerted.getPos()[1]+alerted.getSize()[1]/2- this.pos[1]+alerted.getSize()[1]/2;
					break;
				}
				chooseAvoidAction(xDis,yDis,stateTime);
			}else{
				if(playerAlerted != null){
					alerted = playerAlerted;
				}else{
					alerted = null;
				}
				lastDirCount = -1;
				setCurrentAni(Indentifiers.STATE_IDLE);
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
				case Indentifiers.STATE_IDLE:
				case Indentifiers.STATE_WALK_SOUTH:
				case Indentifiers.STATE_ATTACK_SOUTH:
					leftVision.setAngle(-45);
					rightVision.setAngle(-135);
					xLeft = (int) (pos[0]+size[0]/2+i*leftVision.x); 
					xRight = (int) (pos[0]+size[0]/2+i*rightVision.x);
					yLeft = (int) (pos[1]+size[1]/2+i*leftVision.y); 
					yMax = (int) (pos[1]+size[1]/2+range*leftVision.y); 
					if((ent.getPos()[0]<xLeft && ent.getPos()[0]>xRight || ent.getPos()[0]+ent.getSize()[0]<xLeft && ent.getPos()[0]+ent.getSize()[0]>xRight)
							&&(ent.getPos()[1]<yLeft && ent.getPos()[1]>yMax || ent.getPos()[1]+ent.getSize()[1]<yLeft && ent.getPos()[1]+ent.getSize()[1]>yMax) && ent != this){
						if(ent instanceof Projectile || ent instanceof Playable){
							if(ent instanceof Projectile){
								if(((Projectile)ent).isFriendly()){
									alerted = ent;	
									timer= 0;	
								}
							}else{
								alerted = ent;							
								if(ent instanceof Playable){
									playerAlerted = ent;
								}
								timer= 0;
							}
						}
					}
					break;
				case Indentifiers.STATE_WALK_WEST:
				case Indentifiers.STATE_ATTACK_WEST:
					leftVision.setAngle(-135);
					rightVision.setAngle(135);
					yLeft = (int) (pos[1]+size[1]/2+i*leftVision.y); 
					yRight= (int) (pos[1]+size[1]/2+i*rightVision.y); 
					xLeft = (int) (pos[0]+size[0]/2+i*leftVision.x);
					xMax = (int) (pos[0]+size[0]/2+range*leftVision.x); 
					if((ent.getPos()[1]>yLeft && ent.getPos()[1]<yRight || ent.getPos()[1]+ent.getSize()[1]>yLeft && ent.getPos()[1]+ent.getSize()[1]<yRight)
							&&(ent.getPos()[0]<xLeft && ent.getPos()[0]>xMax || ent.getPos()[0]+ent.getSize()[0]<xLeft && ent.getPos()[0]+ent.getSize()[0]>xMax) && ent != this){
						if(ent instanceof Projectile || ent instanceof Playable){
							if(ent instanceof Projectile){
								if(((Projectile)ent).isFriendly()){
									alerted = ent;	
									timer= 0;	
								}
							}else{
								alerted = ent;
								if(ent instanceof Playable){
									playerAlerted = ent;
								}
								timer= 0;
							}
						}
					}
					break;
				case Indentifiers.STATE_WALK_EAST:
				case Indentifiers.STATE_ATTACK_EAST:
					leftVision.setAngle(45);
					rightVision.setAngle(-45);
					yLeft = (int) (pos[1]+size[1]/2+i*leftVision.y); 
					yRight= (int) (pos[1]+size[1]/2+i*rightVision.y); 
					xLeft = (int) (pos[0]+size[0]/2+i*leftVision.x);
					xMax = (int) (pos[0]+size[0]/2+range*leftVision.x); 
					if((ent.getPos()[1]<yLeft && ent.getPos()[1]>yRight || ent.getPos()[1]+ent.getSize()[1]<yLeft && ent.getPos()[1]+ent.getSize()[1]>yRight)
							&&(ent.getPos()[0]>xLeft && ent.getPos()[0]<xMax || ent.getPos()[0]+ent.getSize()[0]>xLeft && ent.getPos()[0]+ent.getSize()[0]<xMax) && ent != this){
						if(ent instanceof Projectile || ent instanceof Playable){
							if(ent instanceof Projectile){
								if(((Projectile)ent).isFriendly()){
									alerted = ent;	
									timer= 0;	
								}
							}else{
								alerted = ent;
								if(ent instanceof Playable){
									playerAlerted = ent;
								}
								timer= 0;
							}
						}
					}
					break;
				case Indentifiers.STATE_ATTACK_NORTH:
				case Indentifiers.STATE_WALK_NORTH:
					leftVision.setAngle(135);
					rightVision.setAngle(45);
					xLeft = (int) (pos[0]+size[0]/2+i*leftVision.x); 
					xRight = (int) (pos[0]+size[0]/2+i*rightVision.x);
					yLeft = (int) (pos[1]+size[1]/2+i*leftVision.y); 
					yMax = (int) (pos[1]+size[1]/2+range*leftVision.y); 
					if((ent.getPos()[0]>xLeft && ent.getPos()[0]<xRight || ent.getPos()[0]+ent.getSize()[0]>xLeft && ent.getPos()[0]+ent.getSize()[0]<xRight)
							&&(ent.getPos()[1]>yLeft && ent.getPos()[1]<yMax || ent.getPos()[1]+ent.getSize()[1]>yLeft && ent.getPos()[1]+ent.getSize()[1]<yMax ) && ent != this){
						if(ent instanceof Projectile || ent instanceof Playable){
							if(ent instanceof Projectile){
								if(((Projectile)ent).isFriendly()){
									alerted = ent;	
									timer= 0;	
								}
							}else{
								alerted = ent;
								if(ent instanceof Playable){
									playerAlerted = ent;
								}
								timer= 0;
							}
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
					 setCurrentAni(Indentifiers.STATE_WALK_WEST);
					 lastDirCount = 4;
					 movedLeftOf = true;
				 }else if(alerted.getPos()[0]<this.getPos()[0] && alerted.getPos()[1]<this.getPos()[1] && x < y&& ((Projectile)alerted).getRotation() == 0){
					 RuntimeData.getInstance().getMovmentSystem().moveRight(this, speed);
					 setCurrentAni(Indentifiers.STATE_WALK_EAST);
					 lastDirCount = 4;
					 movedLeftOf = true;
				 }else if(alerted.getPos()[0]<this.getPos()[0] && alerted.getPos()[1]>this.getPos()[1] && x > y&& ((Projectile)alerted).getRotation() == 270){
					 RuntimeData.getInstance().getMovmentSystem().moveDown(this, speed);
					 setCurrentAni(Indentifiers.STATE_WALK_SOUTH);
					 lastDirCount = 4;
					 movedLeftOf = true;
				 }else if(alerted.getPos()[0]>this.getPos()[0] && alerted.getPos()[1]<this.getPos()[1] && x > y&& ((Projectile)alerted).getRotation() == 90){
					 RuntimeData.getInstance().getMovmentSystem().moveTop(this, speed);
					 setCurrentAni(Indentifiers.STATE_WALK_NORTH);
					 lastDirCount = 4;
					 movedLeftOf = true;
				 }
			}else{
				switch (state){
				case Indentifiers.STATE_IDLE:
				case Indentifiers.STATE_WALK_SOUTH:
					RuntimeData.getInstance().getMovmentSystem().moveDown(this,speed);
					break;
				case Indentifiers.STATE_WALK_NORTH:
					 RuntimeData.getInstance().getMovmentSystem().moveTop(this,speed);
					break;
				case Indentifiers.STATE_WALK_WEST:
					 RuntimeData.getInstance().getMovmentSystem().moveLeft(this,speed);
					break;
				case Indentifiers.STATE_WALK_EAST:
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
						 setCurrentAni(Indentifiers.STATE_WALK_SOUTH);
						 lastDirCount = 3;
						 movedRightOf = true;
					 }else if(alerted.getPos()[0]<=this.getPos()[0] && alerted.getPos()[1]<=this.getPos()[1] && x > y && ((Projectile)alerted).getRotation() == -90){
						 RuntimeData.getInstance().getMovmentSystem().moveTop(this, speed);
						 setCurrentAni(Indentifiers.STATE_WALK_NORTH);
						 lastDirCount = 3;
						 movedRightOf = true;
					 }else if(alerted.getPos()[0]<=this.getPos()[0] && alerted.getPos()[1]>=this.getPos()[1] && x < y && ((Projectile)alerted).getRotation() == 180){
						 RuntimeData.getInstance().getMovmentSystem().moveRight(this, speed);
						 setCurrentAni(Indentifiers.STATE_WALK_EAST);
						 lastDirCount = 3;
						 movedRightOf = true;
					 }else if(alerted.getPos()[0]>=this.getPos()[0] && alerted.getPos()[1]<=this.getPos()[1] && x < y&& ((Projectile)alerted).getRotation() == 0){
						 RuntimeData.getInstance().getMovmentSystem().moveLeft(this, speed);
						 setCurrentAni(Indentifiers.STATE_WALK_WEST);
						 lastDirCount = 3;
						 movedRightOf = true;
					 }
				}else{
					switch (state){
					case Indentifiers.STATE_IDLE:
					case Indentifiers.STATE_WALK_SOUTH:
						RuntimeData.getInstance().getMovmentSystem().moveDown(this,speed);
						break;
					case Indentifiers.STATE_WALK_NORTH:
						 RuntimeData.getInstance().getMovmentSystem().moveTop(this,speed);
						break;
					case Indentifiers.STATE_WALK_WEST:
						 RuntimeData.getInstance().getMovmentSystem().moveLeft(this,speed);
						break;
					case Indentifiers.STATE_WALK_EAST:
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
					 setCurrentAni(Indentifiers.STATE_WALK_EAST);
				 }else{
					 RuntimeData.getInstance().getMovmentSystem().moveLeft(this,speed);
					 setCurrentAni(Indentifiers.STATE_WALK_WEST);
				 }
			 }else{
				 if(alerted.getPos()[1]>this.pos[1]){
					 RuntimeData.getInstance().getMovmentSystem().moveTop(this,speed);
					 setCurrentAni(Indentifiers.STATE_WALK_NORTH);
				 }else{
					 RuntimeData.getInstance().getMovmentSystem().moveDown(this,speed);
					 setCurrentAni(Indentifiers.STATE_WALK_SOUTH);
				 }
			 }
			 lastDirCount = 4;
		}else{
			switch (state){
			case Indentifiers.STATE_IDLE:
			case Indentifiers.STATE_WALK_SOUTH:
				RuntimeData.getInstance().getMovmentSystem().moveDown(this,speed);
				break;
			case Indentifiers.STATE_WALK_NORTH:
				 RuntimeData.getInstance().getMovmentSystem().moveTop(this,speed);
				break;
			case Indentifiers.STATE_WALK_WEST:
				 RuntimeData.getInstance().getMovmentSystem().moveLeft(this,speed);
				break;
			case Indentifiers.STATE_WALK_EAST:
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
					 setCurrentAni(Indentifiers.STATE_WALK_WEST);
				 }else{
					 RuntimeData.getInstance().getMovmentSystem().moveRight(this,speed);
					 setCurrentAni(Indentifiers.STATE_WALK_EAST);
				 }
			 }else{
				 if(alerted.getPos()[1]>this.pos[1]){
					 RuntimeData.getInstance().getMovmentSystem().moveDown(this,speed);
					 setCurrentAni(Indentifiers.STATE_WALK_SOUTH);
				 }else{
					 RuntimeData.getInstance().getMovmentSystem().moveTop(this,speed);
					 setCurrentAni(Indentifiers.STATE_WALK_NORTH);
				 }
			 }   
			 lastDirCount = 4;
		}else{
			switch (state){
			case Indentifiers.STATE_IDLE:
			case Indentifiers.STATE_WALK_SOUTH:
				RuntimeData.getInstance().getMovmentSystem().moveDown (this,speed);
				break;
			case Indentifiers.STATE_WALK_NORTH:
				 RuntimeData.getInstance().getMovmentSystem().moveTop(this,speed);
				break;
			case Indentifiers.STATE_WALK_WEST:
				 RuntimeData.getInstance().getMovmentSystem().moveLeft(this,speed);
				break;
			case Indentifiers.STATE_WALK_EAST:
				 RuntimeData.getInstance().getMovmentSystem().moveRight(this,speed);
				break;
			}
			lastDirCount--;
		}
	}
	
	@Override
	public void setCurrentAni(int animationKey) {
		switch (animationKey){
		case Indentifiers.STATE_ATTACK_SOUTH:
			currentAni = frontAttackAni;
			state = Indentifiers.STATE_ATTACK_SOUTH;
			break;
		case Indentifiers.STATE_ATTACK_NORTH:
			currentAni = backAttackAni;
			state = Indentifiers.STATE_ATTACK_NORTH;
			break;
		case Indentifiers.STATE_ATTACK_WEST:
			currentAni = leftAttackAni;
			state = Indentifiers.STATE_ATTACK_WEST;
			break;
		case Indentifiers.STATE_ATTACK_EAST:
			currentAni = rightWalkAni;
			state = Indentifiers.STATE_ATTACK_EAST;
			break;
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
	
	public void setIsLastDeath(boolean isLast) {
		this.isLastDeath = isLast;
		timer = 0; 
		alerted = null;
		playerAlerted = null;
	}
	
	public boolean isReadyToDig() {
		return isReadyToDig;
	}
}
