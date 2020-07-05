package com.verminsnest.entities.enemies;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.core.management.Indentifiers;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.entities.projectiles.slashes.Slash;
import com.verminsnest.entities.projectiles.slashes.SlashLeftSmall;
import com.verminsnest.entities.projectiles.slashes.SlashRightSmall;

public class Tinker extends Enemy {

	private int[] patrolPos;
	
	public Tinker(int[] pos) {
		super(pos, Indentifiers.ASSETMANAGER_TINKER,7,6,5,25);
	}

	@Override
	public void init() {
		shadow = RuntimeData.getInstance().getAsset("textures/enemies/tinker/Shadow.png");
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
	protected void chooseAvoidAction(int dist, float delta) {
		dodgeRightOf();
		dodgeLeftOf();
		if(!movedLeftOf && !movedRightOf){
			if(playerAlerted == null){
				playerAlerted = RuntimeData.getInstance().getEntityManager().getCharacter();
			}
			alerted = playerAlerted;
			chooseAgressiveAction(dist, delta);
		}
	}

	@Override
	protected void chooseAgressiveAction(int dist, float delta) {
		if(dist<20){
			attack(delta);
		}else{
			walkTowards(new int[]{alerted.getPos()[0],alerted.getPos()[1]});
		}
	}

	@Override
	protected void attack(float delta) {
		if(attackCooldown>1/(agility*0.2)){
			attackCooldown = 0;
			Slash temp = null;
			switch(state){
			case Indentifiers.STATE_WALK_SOUTH:
			case Indentifiers.STATE_IDLE:
			case Indentifiers.STATE_ATTACK_SOUTH:
				temp = new SlashRightSmall(Indentifiers.DIRECTION_SOUTH, 4, strength, new int[]{pos[0]+size[0]/2,pos[1]});
				temp.getPos()[1] -= temp.getSize()[1]+5;
				temp.getPos()[0] -= temp.getSize()[0]+15;
				temp = new SlashLeftSmall(Indentifiers.DIRECTION_SOUTH, 4, strength, new int[]{pos[0]+size[0]/2,pos[1]});
				temp.getPos()[1] -= temp.getSize()[1]+5;
				temp.getPos()[0] += 15;
				setCurrentAni(Indentifiers.STATE_ATTACK_SOUTH);
				break;
			case Indentifiers.STATE_WALK_NORTH:
			case Indentifiers.STATE_ATTACK_NORTH:
				temp = new SlashLeftSmall(Indentifiers.DIRECTION_NORTH, 4, strength, new int[]{pos[0]+size[0]/2,pos[1]+this.size[1]+5});
				temp.getPos()[0] -= temp.getSize()[0]+15;
				temp = new SlashRightSmall(Indentifiers.DIRECTION_NORTH, 4, strength, new int[]{pos[0]+size[0]/2,pos[1]+this.size[1]+5});
				temp.getPos()[0] += 15;
				setCurrentAni(Indentifiers.STATE_ATTACK_NORTH);
				break;
			case Indentifiers.STATE_WALK_WEST:
			case Indentifiers.STATE_ATTACK_WEST:
				temp = new SlashRightSmall(Indentifiers.DIRECTION_WEST, 4, strength, new int[]{pos[0],pos[1]+size[1]/2});
				temp.getPos()[0] -= temp.getSize()[1]+10;
				temp.getPos()[1] += temp.getSize()[0];
				temp = new SlashLeftSmall(Indentifiers.DIRECTION_WEST, 4, strength, new int[]{pos[0],pos[1]+size[1]/2});
				temp.getPos()[0] -= temp.getSize()[1]+10;
				temp.getPos()[1] -= temp.getSize()[0];
				setCurrentAni(Indentifiers.STATE_ATTACK_WEST);
				break;
			case Indentifiers.STATE_WALK_EAST:
			case Indentifiers.STATE_ATTACK_EAST:
				temp = new SlashLeftSmall(Indentifiers.DIRECTION_EAST, 4, strength, new int[]{pos[0]+this.size[0]+5,pos[1]+size[1]/2});
				temp.getPos()[1] += temp.getSize()[0];
				temp = new SlashRightSmall(Indentifiers.DIRECTION_EAST, 4, strength, new int[]{pos[0]+this.size[0]+5,pos[1]+size[1]/2});
				temp.getPos()[1] -= temp.getSize()[0];
				setCurrentAni(Indentifiers.STATE_ATTACK_EAST);
				break;
			}
		}
	}

	@Override
	protected void chooseIdleAction(float delta) {
		//Get room
		int[] roomSize = RuntimeData.getInstance().getMapData().getRoomSize();
		int[] roomNum =new int[] { ((pos[0]-10*128) / 128) / roomSize[0],
				((pos[1]-10*128)/ 128) / roomSize[1]};
		Random rand = new Random();
		
		//Get random position in room
		while(patrolPos == null || !RuntimeData.getInstance().getMapData().getData()[patrolPos[0]][patrolPos[1]].isWalkable()){
			patrolPos = new int[]{(roomNum[0]+1)*rand.nextInt(roomSize[0])+10,(roomNum[1]+1)*rand.nextInt(roomSize[1])+10};
		}
		//Walk to position
		if(!RuntimeData.getInstance().getMovmentSystem().goToPos(this, new int[]{patrolPos[0]*128, patrolPos[1]*128}, (int) (speed*0.6)))patrolPos = null;
	}
}
