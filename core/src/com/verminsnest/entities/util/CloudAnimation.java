package com.verminsnest.entities.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.core.management.ids.Qualifier;

public class CloudAnimation extends UtilEntity{
	public CloudAnimation(int[] pos) {
		//Textures are loaded initially
		super(pos, -1,Qualifier.RENDER_LAYER_TOP);
		this.setSize(128, 128);
		isObstacle = false;
	}

	@Override
	public void init() {
		Texture sheet = RuntimeData.getInstance().getTexture("textures/level-sheets/cave/Dirt-Cloud.png");
		TextureRegion[][] temp = TextureRegion.split(sheet, 128, 128);
		TextureRegion[] frames = new TextureRegion[temp[0].length];
		
		for(int i = 0; i< temp[0].length; i++){
			frames[i] = temp[0][i];
		}
		currentAni = new Animation<TextureRegion>(0.125f,frames);
	}

	@Override
	public void setCurrentAni(int animationKey) {
		//Not needed
	}

	@Override
	public void update(float stateTime) {
		internalStateTime += Gdx.graphics.getDeltaTime();
		if(currentAni.isAnimationFinished(internalStateTime)){
			RuntimeData.getInstance().getEntityManager().removeEntity(this);
		}
	}
	
	
	@Override
	public TextureRegion getCurrentFrame(float stateTime) {
		return currentAni.getKeyFrame(internalStateTime, false);
	}
}
