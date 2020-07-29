package com.verminsnest.entities.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.entities.Entity;

public class Food extends Entity {
	private Texture foodTexture;
	private boolean isPicked;
	
	public Food(int[] pos) {
		super(pos,-1);
		init();
	}

	@Override
	public void init() {
		isPicked = false;
		foodTexture = RuntimeData.getInstance().getAsset("textures/items/Food.png");
		shadow = RuntimeData.getInstance().getAsset("textures/shadows/Shadow-S.png");
		setSize(foodTexture.getWidth(),foodTexture.getHeight());
		yShadowOffset = -8;
	}

	@Override
	public void setCurrentAni(int animationKey) {
		//Not needed
	}
	
	@Override
	public TextureRegion getCurrentFrame(float delta) {
		return TextureRegion.split(foodTexture, foodTexture.getWidth(), foodTexture.getHeight())[0][0];
	}
	
	@Override
	public void update(float delta) {
		//Not neededs
	}

	public boolean isPicked() {
		return isPicked;
	}

	public void setPicked(boolean isPicked) {
		this.isPicked = isPicked;
	}

}
