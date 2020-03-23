package com.verminsnest.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.projectiles.Projectile;
import com.verminsnest.projectiles.Projectiles;

public class Mage extends Playable {
	public Mage(int[] pos) {
		super(pos, 5, 5, 5);
	}

	@Override
	public void init() {
		Texture wFrontSheet = new Texture("textures/characters/mage/Mage-W-Front.png");
		TextureRegion[][] temp = TextureRegion.split(wFrontSheet, 64, 74);
		TextureRegion[] frames = new TextureRegion[temp[0].length];

		for (int i = 0; i < temp[0].length; i++) {
			frames[i] = temp[0][i];
		}
		frontWalkAni = new Animation<TextureRegion>(0.025f * this.speed, frames);

		Texture wBackSheet = new Texture("textures/characters/mage/Mage-W-Back.png");
		temp = TextureRegion.split(wBackSheet, 64, 74);
		frames = new TextureRegion[temp[0].length];

		for (int i = 0; i < temp[0].length; i++) {
			frames[i] = temp[0][i];
		}
		backWalkAni = new Animation<TextureRegion>(0.025f * this.speed, frames);

		Texture wRightSheet = new Texture("textures/characters/mage/Mage-W-Right.png");
		temp = TextureRegion.split(wRightSheet, 64, 74);
		frames = new TextureRegion[temp[0].length];

		for (int i = 0; i < temp[0].length; i++) {
			frames[i] = temp[0][i];
		}
		rightWalkAni = new Animation<TextureRegion>(0.025f * this.speed, frames);

		Texture wleftSheet = new Texture("textures/characters/mage/Mage-W-Left.png");
		temp = TextureRegion.split(wleftSheet, 64, 74);
		frames = new TextureRegion[temp[0].length];

		for (int i = 0; i < temp[0].length; i++) {
			frames[i] = temp[0][i];
		}
		leftWalkAni = new Animation<TextureRegion>(0.025f * this.speed, frames);

		Texture idleSheet = new Texture("textures/characters/mage/Mage-Idle.png");
		temp = TextureRegion.split(idleSheet, 64, 74);
		frames = new TextureRegion[temp[0].length];

		for (int i = 0; i < temp[0].length; i++) {
			frames[i] = temp[0][i];
		}
		idleAni = new Animation<TextureRegion>(0.5f, frames);
		this.setCurrentAni(IDLE);
	}

	@Override
	public TextureRegion getCurrentFrame(float stateTime) {
		return currentAni.getKeyFrame(stateTime, true);
	}

	@Override
	public void attack(float stateTime) {
		if (lastAttack < stateTime - agility/5) {
			Projectile prj = new Projectile(strength,new Texture("textures/characters/mage/FireBall-Shadow.png"), new Texture("textures/characters/mage/FireBall.png"), null, null,
					32, 32, new int[] { pos[0], pos[1] });
			switch (currentDir) {
			case IDLE:
			case W_FRONT:
				prj.setDirection(new int[] { 0, speed * -3 });
				prj.getPosition()[0] += currentAni.getKeyFrame(0).getRegionWidth()/4;
				prj.getPosition()[1] +=currentAni.getKeyFrame(0).getRegionHeight()/4;
				break;
			case W_BACK:
				prj.setDirection(new int[] { 0, speed * 3 });
				prj.getPosition()[0] += currentAni.getKeyFrame(0).getRegionWidth()/4;
				prj.getPosition()[1] +=currentAni.getKeyFrame(0).getRegionHeight();
				break;
			case W_RIGHT:
				prj.setDirection(new int[] { speed * 3, 0 });
				prj.getPosition()[0] += currentAni.getKeyFrame(0).getRegionWidth()/2;
				prj.getPosition()[1] +=currentAni.getKeyFrame(0).getRegionHeight()/4;
				break;
			case W_LEFT:
				prj.setDirection(new int[] { speed * -3, 0 });
				prj.getPosition()[1] +=currentAni.getKeyFrame(0).getRegionHeight()/4;
				break;
			}
			prj.setCurrentAni(Projectile.FLYING);
			Projectiles.getInstance().add(prj);
			lastAttack = stateTime;
		}
	}
}
