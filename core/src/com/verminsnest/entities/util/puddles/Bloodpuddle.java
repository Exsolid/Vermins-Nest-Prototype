package com.verminsnest.entities.util.puddles;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.core.management.Indentifiers;
import com.verminsnest.core.management.data.RuntimeData;

public class Bloodpuddle extends Puddle{
	public Bloodpuddle(int[] pos, int sizeID) {
		super(pos, Indentifiers.ASSETMANAGER_PUDDLE_BLOOD, sizeID);
		init();
	}

	@Override
	public void init() {
		rand = new Random();
		textureNum = rand.nextInt(2)+1;
		Texture texture = null;
		switch(sizeID){
		case 0:
			switch(textureNum){
			case 1:
				texture = RuntimeData.getInstance().getAsset("textures/puddles/bloodpuddles/Bloodpuddle-Small-1.png");
				break;
			case 2:
				texture = RuntimeData.getInstance().getAsset("textures/puddles/bloodpuddles/Bloodpuddle-Small-2.png");
				break;
			}
			break;
		case 1:
			switch(textureNum){
			case 1:
				texture = RuntimeData.getInstance().getAsset("textures/puddles/bloodpuddles/Bloodpuddle-Big-1.png");
				break;
			case 2:
				texture = RuntimeData.getInstance().getAsset("textures/puddles/bloodpuddles/Bloodpuddle-Big-2.png");
				break;
			}
		}
		
		TextureRegion[][] temp = TextureRegion.split(texture, texture.getHeight(), texture.getHeight());
		TextureRegion[] frames = new TextureRegion[temp[0].length];
		
		for(int i = 0; i< temp[0].length; i++){
			frames[i] = temp[0][i];
		}
		currentAni = new Animation<TextureRegion>(0.15f,frames);
		this.setSize(texture.getHeight(), texture.getHeight());
		rotation = rand.nextInt(360);
	}

}
