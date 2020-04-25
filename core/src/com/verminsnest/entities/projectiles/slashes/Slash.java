package com.verminsnest.entities.projectiles.slashes;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.entities.projectiles.Projectile;
import com.verminsnest.singletons.RuntimeData;

public abstract class Slash extends Projectile {
	
	protected float flyTime;
	protected float rotationSpeed;
	private boolean invert;
	
	public Slash(int textureID, int direction, int agility, int damage, int[] position, float stateTime, boolean invert) {
		super(textureID, direction, agility, damage, position, stateTime);
		this.invert = invert;
		setCurrentAni(FLYING);
	}
	
	@Override
	public TextureRegion getCurrentFrame(float stateTime) {
		return flyingAni.getKeyFrame(0);
	}
	
	@Override
	public void update(float stateTime){
		updateStateTime(stateTime);
		if(internalStateTime > flyTime){
			RuntimeData.getInstance().removeEntity(this);
		}
		switch(direction){
		case Projectile.NORTH:
			if(invert){
				RuntimeData.getInstance().getMovmentSystem().moveLeft(this, this.speed);
				rotation += rotationSpeed;
			}else{
				RuntimeData.getInstance().getMovmentSystem().moveRight(this, this.speed);
				rotation -= rotationSpeed;
			}
			break;
		case Projectile.EAST:
			if(invert){
				RuntimeData.getInstance().getMovmentSystem().moveTop(this, this.speed);
				rotation += rotationSpeed;
			}else{
				RuntimeData.getInstance().getMovmentSystem().moveDown(this, this.speed);
				rotation -= rotationSpeed;
			}
			break;
		case Projectile.SOUTH:
			if(invert){
				RuntimeData.getInstance().getMovmentSystem().moveRight(this, this.speed);
				rotation += rotationSpeed;
			}else{
				rotation -= rotationSpeed;
				RuntimeData.getInstance().getMovmentSystem().moveLeft(this, this.speed);
			}
			break;
		case Projectile.WEST:
			if(invert){
				RuntimeData.getInstance().getMovmentSystem().moveDown(this, this.speed);
				rotation += rotationSpeed;
			}else{
				RuntimeData.getInstance().getMovmentSystem().moveTop(this, this.speed);
				rotation -= rotationSpeed;
			}
			break;
		}
	}
}
