package com.verminsnest.entities.explosions;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.core.management.Indentifiers;
import com.verminsnest.core.management.data.RuntimeData;

public class ExplosionSmall extends Explosion {

	public ExplosionSmall(int[] pos, int damage) {
		super(pos, Indentifiers.ASSETMANAGER_EXPLOSION_SMALL, 15);
	}

	@Override
	public void init() {
		Texture exSheet = RuntimeData.getInstance().getAsset("textures/explosions/Explosion-Small.png");
		TextureRegion[][] temp = TextureRegion.split(exSheet, exSheet.getHeight(), exSheet.getHeight());
		TextureRegion[] frames = new TextureRegion[temp[0].length];
		
		for(int i = 0; i< temp[0].length; i++){
			frames[i] = temp[0][i];
		}
		currentAni = new Animation<TextureRegion>(0.07f,frames);
	}
}
