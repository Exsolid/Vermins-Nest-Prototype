package com.verminsnest.entities.util.puddles;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.entities.util.UtilEntity;

public abstract class Puddle extends UtilEntity {
	
	protected int textureNum;
	protected Random rand;
	
	public Puddle(int[] pos, int textureID, int sizeID) {
		super(pos, textureID, sizeID);
		isGrounded = true;
		isObstacle = false;
	}

	@Override
	public void setCurrentAni(int animationKey) {
		//Not needed
	}
	
	public TextureRegion getCurrentFrame(float delta) {
		return currentAni.getKeyFrame(internalStateTime, false);
	}
	
	@Override
	public void update(float delta) {
	internalStateTime+=delta;
	}

}
