package com.verminsnest.entities.particles;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.core.management.ids.Qualifier;
import com.verminsnest.entities.Entity;

public abstract class Particle extends Entity{
	protected Texture texture;
	protected int textureNum;
	private int[] movement;
	protected Random rand;
	
	public Particle(int[] pos, int textureID) {
		super(pos, textureID,Qualifier.RENDER_LAYER_BOT);
		isObstacle = false;
		rand = new Random();
		
		int randomizer = rand.nextInt(4);
		switch(randomizer){
		case 0:
			movement = new int[]{rand.nextInt(6),-rand.nextInt(6)};
			break;
		case 1:
			movement = new int[]{-rand.nextInt(6),rand.nextInt(6)};
			break;
		case 2:
			movement = new int[]{rand.nextInt(6),rand.nextInt(6)};
			break;
		case 3:
			movement = new int[]{-rand.nextInt(6),-rand.nextInt(6)};
			break;
		}
		
		init();
	}

	@Override
	public void setCurrentAni(int animationKey) {
		//Not needed
	}

	@Override
	public void update(float stateTime) {
		internalStateTime += Gdx.graphics.getDeltaTime();
		if(internalStateTime < 0.2){
			rotation += rand.nextInt(12)-rand.nextInt(12);
			RuntimeData.getInstance().getMovmentSystem().move(this, movement);
		}
	}

	@Override
	public TextureRegion getCurrentFrame(float stateTime) {
		return TextureRegion.split(texture, texture.getWidth(), texture.getHeight())[0][0];
	}
}
