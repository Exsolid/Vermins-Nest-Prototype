package com.verminsnest.entities.enemies;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.core.management.Indentifiers;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.entities.explosions.ExplosionSmall;
import com.verminsnest.entities.playables.Playable;
import com.verminsnest.misc.entities.Death;

public class Flunk extends Enemy {

	private int[] patrolPos;
	
	public Flunk(int[] pos) {
		super(pos, Indentifiers.ASSETMANAGER_FLUNK, 5, 7, 15, 10);
		yShadowOffset = -25;
	}

	@Override
	protected void chooseAvoidAction(int dist, float delta) {
		if(playerAlerted == null){
			playerAlerted = RuntimeData.getInstance().getEntityManager().getCharacter();
		}
		if(!(alerted instanceof Playable)){
			dist = 999;
			alerted = playerAlerted;
		}
		chooseAgressiveAction(dist, delta);
	}

	@Override
	protected void chooseIdleAction(float delta) {
		// Get room
		int[] roomSize = RuntimeData.getInstance().getMapData().getRoomSize();
		int[] roomNum = new int[] { ((pos[0] - 10 * 128) / 128) / roomSize[0],
				((pos[1] - 10 * 128) / 128) / roomSize[1] };
		Random rand = new Random();

		// Get random position in room
		while (patrolPos == null
				|| !RuntimeData.getInstance().getMapData().getData()[patrolPos[0]][patrolPos[1]].isWalkable()) {
			patrolPos = new int[] { (roomNum[0] + 1) * rand.nextInt(roomSize[0]) + 10,
					(roomNum[1] + 1) * rand.nextInt(roomSize[1]) + 10 };
		}
		// Walk to position
		int[] goalPos = new int[] { patrolPos[0] * 128, patrolPos[1] * 128 };
		walkRandomTo(goalPos);
		int[] dif = new int[]{Math.abs(goalPos[0]-pos[0]),Math.abs(goalPos[1]-pos[1])};
		if((dif[0] | dif[1]) < 50)patrolPos = null;
	}

	@Override
	protected void chooseAgressiveAction(int dist, float delta) {
		if(dist<5){
			attack(delta);
		}else{
			walkRandomTo(new int[]{alerted.getPos()[0],alerted.getPos()[1]});
		}
	}

	@Override
	protected void attack(float stateTime) {
		isLastDeath = true;
		isReadyToDig = true;
		new Death(this);
		new ExplosionSmall(new int[]{pos[0]-10,pos[1]-10}, strength);
	}

	@Override
	public void init() {
		Texture exSheet = RuntimeData.getInstance().getAsset("textures/enemies/flunk/Flunk-All.png");
		TextureRegion[][] temp = TextureRegion.split(exSheet, exSheet.getHeight(), exSheet.getHeight());
		TextureRegion[] frames = new TextureRegion[temp[0].length];
		
		for(int i = 0; i< temp[0].length; i++){
			frames[i] = temp[0][i];
		}
		currentAni = new Animation<TextureRegion>(0.5f / this.speed,frames);
		shadow = RuntimeData.getInstance().getAsset("textures/shadows/Shadow-XS.png");
	}
}
