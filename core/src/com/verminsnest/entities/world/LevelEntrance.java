package com.verminsnest.entities.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.core.Indentifiers;
import com.verminsnest.core.singletons.RuntimeData;
import com.verminsnest.entities.Entity;

public class LevelEntrance extends Entity {
	
	private Animation<TextureRegion> spawnAni;
	private Texture entrance;

	private float internalStateTime;
	public LevelEntrance(int[] pos) {
		super(pos, -1);
		isObstacle = false;
		init();
	}

	@Override
	public void init() {
		Texture spawnSheet = RuntimeData.getInstance().getAsset("textures/level-sheets/cave/Dirt-Cloud.png");
		TextureRegion[][] temp = TextureRegion.split(spawnSheet, 128, 128);
		TextureRegion[] frames = new TextureRegion[temp[0].length];

		for (int i = 0; i < temp[0].length; i++) {
			frames[i] = temp[0][i];
		}
		spawnAni = new Animation<TextureRegion>(0.8f, frames);
		
		entrance = RuntimeData.getInstance().getAsset("textures/level-sheets/cave/Mountain-Hole.png");
		this.setSize(entrance.getWidth(), entrance.getHeight());
		setCurrentAni(Indentifiers.STATE_SPAWNING);
		internalStateTime = 0;
	}

	@Override
	public void setCurrentAni(int animationKey) {
		switch(animationKey){
		case Indentifiers.STATE_IDLE:
			state =Indentifiers.STATE_IDLE;
			currentAni = null;
			break;
		case Indentifiers.STATE_SPAWNING:
			state = Indentifiers.STATE_SPAWNING;
			currentAni = spawnAni;
			break;
		}
	}

	@Override
	public void update(float stateTime) {
		internalStateTime += Gdx.graphics.getDeltaTime();
		if(currentAni != null && currentAni.isAnimationFinished(internalStateTime)){
			setCurrentAni(Indentifiers.STATE_IDLE);
		}
	}
	
	@Override
	public TextureRegion getCurrentFrame(float stateTime) {
		switch(state){
		case Indentifiers.STATE_IDLE:
			return TextureRegion.split(entrance, entrance.getWidth(), entrance.getHeight())[0][0];
		case Indentifiers.STATE_SPAWNING:
			return currentAni.getKeyFrame(stateTime, true);
		default:
			return null;
		}
	}

}
