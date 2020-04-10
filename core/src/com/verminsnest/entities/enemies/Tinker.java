package com.verminsnest.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.misc.assets.VNAssetManager;
import com.verminsnest.singletons.RuntimeData;

public class Tinker extends Enemy {

	public Tinker(int[] pos) {
		super(pos, VNAssetManager.GAMEPLAY_TINKER,5,5,5);
	}

	@Override
	public void init() {
		Texture wFrontSheet = RuntimeData.getInstance().getAsset("textures/enemies/tinker/Tinker-W-Front.png");
		TextureRegion[][] temp = TextureRegion.split(wFrontSheet, 64, 64);
		TextureRegion[] frames = new TextureRegion[temp[0].length];

		for (int i = 0; i < temp[0].length; i++) {
			frames[i] = temp[0][i];
		}
		frontWalkAni = new Animation<TextureRegion>(1f / this.speed, frames);

		Texture wBackSheet = RuntimeData.getInstance().getAsset("textures/enemies/tinker/Tinker-W-Back.png");
		temp = TextureRegion.split(wBackSheet, 64, 64);
		frames = new TextureRegion[temp[0].length];

		for (int i = 0; i < temp[0].length; i++) {
			frames[i] = temp[0][i];
		}
		backWalkAni = new Animation<TextureRegion>(1f / this.speed, frames);

		Texture wRightSheet = RuntimeData.getInstance().getAsset("textures/enemies/tinker/Tinker-W-Right.png");
		temp = TextureRegion.split(wRightSheet, 64, 64);
		frames = new TextureRegion[temp[0].length];

		for (int i = 0; i < temp[0].length; i++) {
			frames[i] = temp[0][i];
		}
		rightWalkAni = new Animation<TextureRegion>(1f / this.speed, frames);

		Texture wleftSheet = RuntimeData.getInstance().getAsset("textures/enemies/tinker/Tinker-W-Left.png");
		temp = TextureRegion.split(wleftSheet, 64, 64);
		frames = new TextureRegion[temp[0].length];

		for (int i = 0; i < temp[0].length; i++) {
			frames[i] = temp[0][i];
		}
		leftWalkAni = new Animation<TextureRegion>(1f / this.speed, frames);

		Texture idleSheet = RuntimeData.getInstance().getAsset("textures/enemies/tinker/Tinker-Idle.png");
		temp = TextureRegion.split(idleSheet, 64, 64);
		frames = new TextureRegion[temp[0].length];

		for (int i = 0; i < temp[0].length; i++) {
			frames[i] = temp[0][i];
		}
		idleAni = new Animation<TextureRegion>(0.5f, frames);
	}
}
