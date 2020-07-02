package com.verminsnest.entities.explosions;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.entities.Entity;

public abstract class Explosion extends Entity {

	private int damage;

	public Explosion(int[] pos, int textureID, int damage) {
		super(pos, textureID);
		init();
		setDamage(damage);
		setSize(currentAni.getKeyFrame(0).getRegionWidth(),currentAni.getKeyFrame(0).getRegionHeight());
	}

	@Override
	public void setCurrentAni(int animationKey) {
		//Not required
	}
	
	@Override
	public TextureRegion getCurrentFrame(float delta) {
		return currentAni.getKeyFrame(internalStateTime, false);
	}
	
	@Override
	public void update(float delta) {
		if(internalStateTime == 0){
			RuntimeData.getInstance().getEntityDamageSystem().calculateHit(this);
		}
		internalStateTime += delta;
		if(currentAni.isAnimationFinished(internalStateTime)){
			RuntimeData.getInstance().getEntityManager().removeEntity(this);
		}
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
}
