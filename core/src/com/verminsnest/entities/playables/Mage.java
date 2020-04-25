package com.verminsnest.entities.playables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.entities.projectiles.Fireball;
import com.verminsnest.entities.projectiles.Projectile;
import com.verminsnest.misc.assets.VNAssetManager;
import com.verminsnest.singletons.RuntimeData;

public class Mage extends Playable {
	public Mage(int[] pos) {
		super(VNAssetManager.GAMEPLAY_MAGE, pos, 7, 7, 7, 50);
		attackIconPath = Fireball.iconPath;
	}

	@Override
	public void init() {
		Texture wFrontSheet = RuntimeData.getInstance().getAsset("textures/characters/mage/Mage-W-Front.png");
		TextureRegion[][] temp = TextureRegion.split(wFrontSheet, 50, 64);
		TextureRegion[] frames = new TextureRegion[temp[0].length];

		for (int i = 0; i < temp[0].length; i++) {
			frames[i] = temp[0][i];
		}
		frontWalkAni = new Animation<TextureRegion>(1f / this.speed, frames);

		Texture wBackSheet = RuntimeData.getInstance().getAsset("textures/characters/mage/Mage-W-Back.png");
		temp = TextureRegion.split(wBackSheet, 50, 64);
		frames = new TextureRegion[temp[0].length];

		for (int i = 0; i < temp[0].length; i++) {
			frames[i] = temp[0][i];
		}
		backWalkAni = new Animation<TextureRegion>(1f / this.speed, frames);

		Texture wRightSheet = RuntimeData.getInstance().getAsset("textures/characters/mage/Mage-W-Right.png");
		temp = TextureRegion.split(wRightSheet, 50, 64);
		frames = new TextureRegion[temp[0].length];

		for (int i = 0; i < temp[0].length; i++) {
			frames[i] = temp[0][i];
		}
		rightWalkAni = new Animation<TextureRegion>(1f / this.speed, frames);

		Texture wleftSheet = RuntimeData.getInstance().getAsset("textures/characters/mage/Mage-W-Left.png");
		temp = TextureRegion.split(wleftSheet, 50, 64);
		frames = new TextureRegion[temp[0].length];

		for (int i = 0; i < temp[0].length; i++) {
			frames[i] = temp[0][i];
		}
		leftWalkAni = new Animation<TextureRegion>(1f / this.speed, frames);

		Texture idleSheet = RuntimeData.getInstance().getAsset("textures/characters/mage/Mage-Idle.png");
		temp = TextureRegion.split(idleSheet, 50, 64);
		frames = new TextureRegion[temp[0].length];

		for (int i = 0; i < temp[0].length; i++) {
			frames[i] = temp[0][i];
		}
		idleAni = new Animation<TextureRegion>(0.5f, frames);
		yShadowOffset = -15;
	}

	@Override
	public TextureRegion getCurrentFrame(float stateTime) {
		return currentAni.getKeyFrame(stateTime, true);
	}

	@Override
	public void attackAction(float stateTime) {
		Projectile prj = null;
		switch (currentDir) {
		case IDLE:
		case W_FRONT:
			prj = new Fireball(Projectile.SOUTH, agility, strength, new int[] { pos[0], pos[1] }, stateTime);
			prj.getPos()[0] += currentAni.getKeyFrame(0).getRegionWidth() / 4;
			prj.getPos()[1] -= prj.getSize()[1];
			break;
		case W_BACK:
			prj = new Fireball(Projectile.NORTH, agility, strength, new int[] { pos[0], pos[1] }, stateTime);
			prj.getPos()[0] += currentAni.getKeyFrame(0).getRegionWidth() / 4;
			prj.getPos()[1] += currentAni.getKeyFrame(0).getRegionWidth() * 1.5;
			break;
		case W_RIGHT:
			prj = new Fireball(Projectile.EAST, agility, strength, new int[] { pos[0], pos[1] }, stateTime);
			prj.getPos()[0] += currentAni.getKeyFrame(0).getRegionWidth() * 1.5;
			prj.getPos()[1] += currentAni.getKeyFrame(0).getRegionHeight() / 4;
			break;
		case W_LEFT:
			prj = new Fireball(Projectile.WEST, agility, strength, new int[] { pos[0], pos[1] }, stateTime);
			prj.getPos()[1] += currentAni.getKeyFrame(0).getRegionHeight() / 4;
			prj.getPos()[0] -= prj.getSize()[0];
			break;
		}
		prj.setFriendly(true);
		prj.setCurrentAni(Projectile.CAST);
		attackCooldown = stateTime;
	}
}
