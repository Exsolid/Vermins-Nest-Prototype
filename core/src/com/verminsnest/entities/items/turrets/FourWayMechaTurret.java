package com.verminsnest.entities.items.turrets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.core.management.Indentifiers;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.entities.particles.Shell;
import com.verminsnest.entities.playables.Playable;
import com.verminsnest.entities.projectiles.bullets.GreenBullet;

public class FourWayMechaTurret extends Turret {
	int counter;
	public FourWayMechaTurret() {
		super(Indentifiers.ASSETMANAGER_TURRET_MECHA, false);
		baseCooldown = 15f;
		counter= 0;
	}

	@Override
	public void doStuff() {
		if(attackCooldown <= 0) {
			if(counter == 0) {
				attackCooldown = 2f;
				counter = 3;
			}else {
				attackCooldown = 0.20f;
				counter--;
			}
			GreenBullet prj1 =new GreenBullet(Indentifiers.DIRECTION_EAST, (int) (((Playable)keeper).getAgility()*1.5), ((Playable)keeper).getStrength(), new int[] {pos[0]+25+size[0], pos[1]+size[1]/2});
			GreenBullet prj2 =new GreenBullet(Indentifiers.DIRECTION_WEST, (int) (((Playable)keeper).getAgility()*1.5), ((Playable)keeper).getStrength(), new int[] {pos[0]-25, pos[1]+size[1]/2});
			GreenBullet prj3 =new GreenBullet(Indentifiers.DIRECTION_SOUTH, (int) (((Playable)keeper).getAgility()*1.5), ((Playable)keeper).getStrength(), new int[] {pos[0]+size[1]/2, pos[1]});
			GreenBullet prj4 =new GreenBullet(Indentifiers.DIRECTION_NORTH, (int) (((Playable)keeper).getAgility()*1.5), ((Playable)keeper).getStrength(), new int[] {pos[0]+size[1]/2, pos[1]+size[1]});
			prj1.setFriendly(true);
			prj2.setFriendly(true);
			prj3.setFriendly(true);
			prj4.setFriendly(true);
			setCurrentAni(Indentifiers.STATE_ATTACK_EAST);
			//TODO set this to init on bullet init
			new Shell(new int[] {pos[0]+size[0]/2, pos[1]+size[1]/2});
		}
	}

	@Override
	public void init() {
		Texture idleSheet = RuntimeData.getInstance().getTexture("textures/items/turrets/mecha-turret/4-Way-MechaTurret-Idle.png");
		TextureRegion[][] temp = TextureRegion.split(idleSheet, idleSheet.getHeight(), idleSheet.getHeight());
		TextureRegion[] frames = new TextureRegion[temp[0].length];

		for (int i = 0; i < temp[0].length; i++) {
			frames[i] = temp[0][i];
		}
		idleAni = new Animation<TextureRegion>(1f, frames);

		Texture aAllSheet = RuntimeData.getInstance().getTexture("textures/items/turrets/mecha-turret/4-Way-MechaTurret-A-All.png");
		temp = TextureRegion.split(aAllSheet, aAllSheet.getHeight(), aAllSheet.getHeight());
		frames = new TextureRegion[temp[0].length];

		for (int i = 0; i < temp[0].length; i++) {
			frames[i] = temp[0][i];
		}
		attackWestAni = new Animation<TextureRegion>(0.02f, frames);
		attackEastAni = new Animation<TextureRegion>(0.02f, frames);
		attackNorthAni = new Animation<TextureRegion>(0.02f, frames);
		attackSouthAni = new Animation<TextureRegion>(0.02f, frames);
		
		setCurrentAni(Indentifiers.STATE_IDLE);
		this.setIconPath("textures/items/turrets/mecha-turret/4-Way-MechaTurret-Icon.png");
	}

}
