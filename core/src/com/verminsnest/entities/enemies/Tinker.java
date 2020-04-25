package com.verminsnest.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.entities.projectiles.Projectile;
import com.verminsnest.entities.projectiles.slashes.Slash;
import com.verminsnest.entities.projectiles.slashes.SlashLeftSmall;
import com.verminsnest.entities.projectiles.slashes.SlashRightSmall;
import com.verminsnest.misc.assets.VNAssetManager;
import com.verminsnest.singletons.RuntimeData;

public class Tinker extends Enemy {
	
	public Tinker(int[] pos) {
		super(pos, VNAssetManager.GAMEPLAY_TINKER,7,6,5,25);
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
		
		Texture attackFSheet = RuntimeData.getInstance().getAsset("textures/enemies/tinker/Tinker-A-Front.png");
		temp = TextureRegion.split(attackFSheet, 64, 64);
		frames = new TextureRegion[temp[0].length];

		for (int i = 0; i < temp[0].length; i++) {
			frames[i] = temp[0][i];
		}
		frontAttackAni = new Animation<TextureRegion>(0.15f, frames);
		
		Texture attackBSheet = RuntimeData.getInstance().getAsset("textures/enemies/tinker/Tinker-A-Back.png");
		temp = TextureRegion.split(attackBSheet, 64, 64);
		frames = new TextureRegion[temp[0].length];

		for (int i = 0; i < temp[0].length; i++) {
			frames[i] = temp[0][i];
		}
		backAttackAni = new Animation<TextureRegion>(0.15f, frames);
		
		Texture attackLSheet = RuntimeData.getInstance().getAsset("textures/enemies/tinker/Tinker-A-Left.png");
		temp = TextureRegion.split(attackLSheet, 64, 64);
		frames = new TextureRegion[temp[0].length];

		for (int i = 0; i < temp[0].length; i++) {
			frames[i] = temp[0][i];
		}
		leftAttackAni = new Animation<TextureRegion>(0.15f, frames);
	}

	@Override
	protected void chooseAvoidAction(int xDistance, int yDistance, float stateTime) {
		walkRightOf();
		walkLeftOf();
		if(!movedLeftOf && !movedRightOf){
			if(playerAlerted != null)alerted = playerAlerted;
			chooseAgressiveAction(xDistance,yDistance, stateTime);
		}
	}

	@Override
	protected void chooseAgressiveAction(int xDistance, int yDistance, float stateTime) {
		if(Math.abs(xDistance)<10 &&Math.abs(yDistance)<10){
			attack(stateTime);
		}else{
			walkTowards();
		}
	}

	@Override
	protected void attack(float stateTime) {
		if(lastAttack<stateTime-1/(agility*0.2)){
			lastAttack = stateTime;
			Slash temp = null;
			switch(state){
			case W_FRONT:
			case IDLE:
			case A_FRONT:
				temp = new SlashRightSmall(Projectile.SOUTH, 4, strength, new int[]{pos[0]+size[0]/2,pos[1]}, stateTime);
				temp.getPos()[1] -= temp.getSize()[1]+5;
				temp.getPos()[0] -= temp.getSize()[0]+15;
				temp = new SlashLeftSmall(Projectile.SOUTH, 4, strength, new int[]{pos[0]+size[0]/2,pos[1]}, stateTime);
				temp.getPos()[1] -= temp.getSize()[1]+5;
				temp.getPos()[0] += 15;
				setCurrentAni(A_FRONT);
				break;
			case W_BACK:
			case A_BACK:
				temp = new SlashLeftSmall(Projectile.NORTH, 4, strength, new int[]{pos[0]+size[0]/2,pos[1]+this.size[1]+5}, stateTime);
				temp.getPos()[0] -= temp.getSize()[0]+15;
				temp = new SlashRightSmall(Projectile.NORTH, 4, strength, new int[]{pos[0]+size[0]/2,pos[1]+this.size[1]+5}, stateTime);
				temp.getPos()[0] += 15;
				setCurrentAni(A_BACK);
				break;
			case W_LEFT:
			case A_LEFT:
				temp = new SlashRightSmall(Projectile.WEST, 4, strength, new int[]{pos[0],pos[1]+size[1]/2}, stateTime);
				temp.getPos()[0] -= temp.getSize()[1]+10;
				temp.getPos()[1] += temp.getSize()[0];
				temp = new SlashLeftSmall(Projectile.WEST, 4, strength, new int[]{pos[0],pos[1]+size[1]/2}, stateTime);
				temp.getPos()[0] -= temp.getSize()[1]+10;
				temp.getPos()[1] -= temp.getSize()[0];
				setCurrentAni(A_LEFT);
				break;
			case W_RIGHT:
			case A_RIGHT:
				temp = new SlashLeftSmall(Projectile.EAST, 4, strength, new int[]{pos[0]+this.size[0]+5,pos[1]+size[1]/2}, stateTime);
				temp.getPos()[1] += temp.getSize()[0];
				temp = new SlashRightSmall(Projectile.EAST, 4, strength, new int[]{pos[0]+this.size[0]+5,pos[1]+size[1]/2}, stateTime);
				temp.getPos()[1] -= temp.getSize()[0];
				setCurrentAni(A_RIGHT);
				break;
			}
		}
	}
}
