package com.verminsnest.entities.playables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.entities.projectiles.Fireball;
import com.verminsnest.entities.projectiles.Projectile;
import com.verminsnest.singletons.RuntimeData;

public class Mage extends Playable {
	public Mage(int[] pos) {
		super(pos,new int[]{TextureRegion.split(new Texture("textures/characters/mage/Mage-W-Front.png"), 64, 74)[0][0].getRegionWidth(), TextureRegion.split(new Texture("textures/characters/mage/Mage-W-Front.png"), 64, 74)[0][0].getRegionHeight()}, 7, 7, 7);
	}

	@Override
	public void init() {
		Texture wFrontSheet = new Texture("textures/characters/mage/Mage-W-Front.png");
		TextureRegion[][] temp = TextureRegion.split(wFrontSheet, 64, 74);
		TextureRegion[] frames = new TextureRegion[temp[0].length];

		for (int i = 0; i < temp[0].length; i++) {
			frames[i] = temp[0][i];
		}
		frontWalkAni = new Animation<TextureRegion>(1f / this.speed, frames);

		Texture wBackSheet = new Texture("textures/characters/mage/Mage-W-Back.png");
		temp = TextureRegion.split(wBackSheet, 64, 74);
		frames = new TextureRegion[temp[0].length];

		for (int i = 0; i < temp[0].length; i++) {
			frames[i] = temp[0][i];
		}
		backWalkAni = new Animation<TextureRegion>(1f / this.speed, frames);

		Texture wRightSheet = new Texture("textures/characters/mage/Mage-W-Right.png");
		temp = TextureRegion.split(wRightSheet, 64, 74);
		frames = new TextureRegion[temp[0].length];

		for (int i = 0; i < temp[0].length; i++) {
			frames[i] = temp[0][i];
		}
		rightWalkAni = new Animation<TextureRegion>(1f / this.speed, frames);

		Texture wleftSheet = new Texture("textures/characters/mage/Mage-W-Left.png");
		temp = TextureRegion.split(wleftSheet, 64, 74);
		frames = new TextureRegion[temp[0].length];

		for (int i = 0; i < temp[0].length; i++) {
			frames[i] = temp[0][i];
		}
		leftWalkAni = new Animation<TextureRegion>(1f / this.speed, frames);

		Texture idleSheet = new Texture("textures/characters/mage/Mage-Idle.png");
		temp = TextureRegion.split(idleSheet, 64, 74);
		frames = new TextureRegion[temp[0].length];

		for (int i = 0; i < temp[0].length; i++) {
			frames[i] = temp[0][i];
		}
		idleAni = new Animation<TextureRegion>(0.5f, frames);
	}

	@Override
	public TextureRegion getCurrentFrame(float stateTime) {
		return currentAni.getKeyFrame(stateTime, true);
	}

	@Override
	public void attack(float stateTime) {
		if (lastAttack < stateTime - 1/(agility*0.2)) {
			Projectile prj = null;
			switch (currentDir) {
			case IDLE:
			case W_FRONT:
				prj = new Fireball(Projectile.SOUTH,agility,strength,new int[] { pos[0], pos[1] },stateTime);
				prj.getPos()[0] += currentAni.getKeyFrame(0).getRegionWidth()/4;
				prj.getPos()[1] -= prj.getSize()[1];
				break;
			case W_BACK:
				prj = new Fireball(Projectile.NORTH,agility,strength,new int[] { pos[0], pos[1] },stateTime);
				prj.getPos()[0] += currentAni.getKeyFrame(0).getRegionWidth()/4;
				prj.getPos()[1] += currentAni.getKeyFrame(0).getRegionWidth()*1.5;
				break;
			case W_RIGHT:
				prj = new Fireball(Projectile.EAST,agility,strength,new int[] { pos[0], pos[1] },stateTime);
				prj.getPos()[0] += currentAni.getKeyFrame(0).getRegionWidth()*1.5;
				prj.getPos()[1] += currentAni.getKeyFrame(0).getRegionHeight()/4;
				break;
			case W_LEFT:
				prj = new Fireball(Projectile.WEST,agility,strength,new int[] { pos[0], pos[1] },stateTime);
				prj.getPos()[1] +=currentAni.getKeyFrame(0).getRegionHeight()/4;
				prj.getPos()[0] -= prj.getSize()[0];
				break;
			}
			prj.setCurrentAni(Projectile.CAST);
			RuntimeData.getInstance().addProjectile(prj);
			lastAttack = stateTime;
		}
	}
}
