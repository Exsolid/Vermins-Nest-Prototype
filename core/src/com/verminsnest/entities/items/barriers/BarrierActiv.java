package com.verminsnest.entities.items.barriers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.core.management.Indentifiers;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.entities.playables.Playable;;

public class BarrierActiv extends Barrier {
	
	public BarrierActiv() {
		super(Indentifiers.ASSETMANAGER_BARRIER_BLUE, false);
		this.setIconPath("textures/items/barriers/barrier-blue/Barrier-Blue-Icon.png");
		baseCooldown = 20;
	}

	@Override
	public void activate() {
		runtime = 4f;
		internalStateTime = 0;
		if(keeper instanceof Playable)activationHP = ((Playable)keeper).getHealth();
		this.pos = new int[]{keeper.getPos()[0]-this.size[0]/2+keeper.getSize()[0]/2,keeper.getPos()[1]-this.size[1]/2+keeper.getSize()[1]/2};
	}

	@Override
	public void init() {
		Texture castSheet = RuntimeData.getInstance().getAsset("textures/items/barriers/barrier-blue/Barrier-Blue-Cast.png");
		TextureRegion[][] temp = TextureRegion.split(castSheet, 70, 80);
		TextureRegion[] frames = new TextureRegion[temp[0].length];

		for (int i = 0; i < temp[0].length; i++) {
			frames[i] = temp[0][i];
		}
		castAni = new Animation<TextureRegion>(0.075f, frames);
		
		Texture breakSheet = RuntimeData.getInstance().getAsset("textures/items/barriers/barrier-blue/Barrier-Blue-Break.png");
		temp = TextureRegion.split(breakSheet, 70, 80);
		frames = new TextureRegion[temp[0].length];

		for (int i = 0; i < temp[0].length; i++) {
			frames[i] = temp[0][i];
		}
		breakAni = new Animation<TextureRegion>(0.15f, frames);
		
		Texture idleSheet = RuntimeData.getInstance().getAsset("textures/items/barriers/barrier-blue/Barrier-Blue-Idle.png");
		temp = TextureRegion.split(idleSheet, 70, 80);
		frames = new TextureRegion[temp[0].length];

		for (int i = 0; i < temp[0].length; i++) {
			frames[i] = temp[0][i];
		}
		idleAni = new Animation<TextureRegion>(1f, frames);
		setCurrentAni(Indentifiers.STATE_CAST);
	}

	@Override
	public void update(float delta) {
		internalStateTime += delta;
		if(runtime > 0 && state != Indentifiers.STATE_BREAK){
			this.pos = new int[]{keeper.getPos()[0]-this.size[0]/2+keeper.getSize()[0]/2,keeper.getPos()[1]-this.size[1]/2+keeper.getSize()[1]/2};
			switch(state){
			case Indentifiers.STATE_IDLE:
				runtime -= delta;
				if(keeper instanceof Playable){
					((Playable)keeper).setHealth(activationHP);
				}
				if(runtime < 0){
					setCurrentAni(Indentifiers.STATE_BREAK);
					internalStateTime = 0;
				}
				break;
			case Indentifiers.STATE_CAST:
				if(currentAni.isAnimationFinished(internalStateTime)){
					setCurrentAni(Indentifiers.STATE_IDLE);
					internalStateTime = 0;
				}
				if(keeper instanceof Playable && internalStateTime > 0.5f){
					((Playable)keeper).setHealth(activationHP);
				}
				break;
			case Indentifiers.STATE_BREAK:
				break;
			}
		}else if(state == Indentifiers.STATE_BREAK){
			this.pos = new int[]{keeper.getPos()[0]-this.size[0]/2+keeper.getSize()[0]/2,keeper.getPos()[1]-this.size[1]/2+keeper.getSize()[1]/2};
			if(currentAni.isAnimationFinished(internalStateTime)){
				setCurrentAni(Indentifiers.STATE_CAST);
				pos = null;
			}
		}
	}

}
