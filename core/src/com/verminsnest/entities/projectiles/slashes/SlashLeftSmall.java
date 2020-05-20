package com.verminsnest.entities.projectiles.slashes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.core.Indentifiers;
import com.verminsnest.core.singletons.RuntimeData;

public class SlashLeftSmall extends Slash{

	public SlashLeftSmall(int direction, int agility, int damage, int[] position, float stateTime) {
		super(Indentifiers.ASSETMANAGER_SLASH_SMALL, direction, agility, damage, position, stateTime, false);
	}

	@Override
	public void init() {
		//Textures
		Texture flyingSheet = RuntimeData.getInstance().getAsset("textures/projectiles/slash/SlashLeft.png");
		TextureRegion[][] temp = TextureRegion.split(flyingSheet, flyingSheet.getWidth(), flyingSheet.getHeight());
		TextureRegion[] frames = new TextureRegion[temp[0].length];
		
		for(int i = 0; i< temp[0].length; i++){
			frames[i] = temp[0][i];
		}
		rotation += 60;
		rotationSpeed =6.5f;
		flyTime = 0.1f;
		flyingAni = new Animation<TextureRegion>(0.1f,frames);
		castAni =flyingAni;
		hitAni = flyingAni;
	}

}
