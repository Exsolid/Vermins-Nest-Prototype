package com.verminsnest.entities.util.shop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.core.management.Indentifiers;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.entities.util.UtilEntity;

public class Blanket extends UtilEntity {
	private Texture texture;
	public Blanket(int[] pos) {
		super(pos, Indentifiers.ASSETMANAGER_SHOPKEEPER);
		isGrounded = true;
	}

	@Override
	public void init() {
		texture =RuntimeData.getInstance().getTexture("textures/util/Blanket.png");
		isObstacle = false;
		setSize(texture.getWidth(),texture.getHeight());
	}

	@Override
	public void setCurrentAni(int animationKey) {
		//Not need
	}
	
	@Override
	public TextureRegion getCurrentFrame(float delta) {
		return TextureRegion.split(texture, texture.getWidth(), texture.getHeight())[0][0];
	}
	@Override
	public void update(float delta) {
		//Not needed
	}

}
