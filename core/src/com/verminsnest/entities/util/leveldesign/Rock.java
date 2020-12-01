package com.verminsnest.entities.util.leveldesign;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.core.management.ids.Indentifiers;
import com.verminsnest.core.management.ids.Qualifier;
import com.verminsnest.entities.util.UtilEntity;

public class Rock extends UtilEntity {
	private Random rand;
	public Rock(int[] pos, int sizeID) {
		super(pos, Indentifiers.ASSETMANAGER_ROCKS, sizeID, Qualifier.RENDER_LAYER_MID);
		init();
	}

	@Override
	public void init() {
		rand = new Random();
		textureNum = rand.nextInt(5)+1;
		
		Texture texture = null;
		switch(sizeID){
		case 0:
			switch(textureNum){
			case 1:
				texture = RuntimeData.getInstance().getTexture("textures/level-design/rocks/Rock-Small-1.png");
				shadow = RuntimeData.getInstance().getTexture("textures/shadows/Shadow-M.png");
				yShadowOffset = -10;
				xShadowOffset = -4;
				this.setHitbox(texture.getWidth(), texture.getHeight()/2);
				break;
			case 2:
				texture = RuntimeData.getInstance().getTexture("textures/level-design/rocks/Rock-Small-2.png");
				shadow = RuntimeData.getInstance().getTexture("textures/shadows/Shadow-S.png");
				yShadowOffset = -10;
				xShadowOffset = -2;
				this.setHitbox(texture.getWidth(), (int) (texture.getHeight()*0.7));
				break;
			case 3:
				texture = RuntimeData.getInstance().getTexture("textures/level-design/rocks/Rock-Small-3.png");
				shadow = RuntimeData.getInstance().getTexture("textures/shadows/Shadow-M.png");
				yShadowOffset = -11;
				xShadowOffset = -6;
				this.setHitbox(texture.getWidth(), (int) (texture.getHeight()*0.8));
				break;
			case 4:
				texture = RuntimeData.getInstance().getTexture("textures/level-design/rocks/Rock-Small-4.png");
				shadow = RuntimeData.getInstance().getTexture("textures/shadows/Shadow-M.png");
				yShadowOffset = -11;
				xShadowOffset = -6;
				this.setHitbox(texture.getWidth(), (int) (texture.getHeight()*0.8));
				break;
			case 5:
				texture = RuntimeData.getInstance().getTexture("textures/level-design/rocks/Rock-Small-5.png");
				shadow = RuntimeData.getInstance().getTexture("textures/shadows/Shadow-M.png");
				yShadowOffset = -11;
				xShadowOffset = -5;
				this.setHitbox(texture.getWidth(), (int) (texture.getHeight()*0.7));
				break;
			}
		}
		
		TextureRegion[][] temp = TextureRegion.split(texture, texture.getWidth(), texture.getHeight());
		TextureRegion[] frames = new TextureRegion[temp[0].length];
		
		for(int i = 0; i< temp[0].length; i++){
			frames[i] = temp[0][i];
		}
		currentAni = new Animation<TextureRegion>(0.15f,frames);
		this.setSize(texture.getWidth(), texture.getHeight());
		RuntimeData.getInstance().getMovmentSystem().updateTiles(this);
	}

	@Override
	public void setCurrentAni(int animationKey) {
		// not needed
		
	}

	@Override
	public void update(float delta) {
		internalStateTime += delta;
	}
	
	@Override
	public TextureRegion getCurrentFrame(float delta) {
		return currentAni.getKeyFrame(internalStateTime, false);
	}
	
	@Override
	public void dispose(){
		for(int[] pos: this.getMapPos()){
			RuntimeData.getInstance().getMapData().getData()[pos[0]][pos[1]].editObstacleCount(-1);;
		}
	}
	
	public void persistPos(){
		for(int[] pos: this.getMapPos()){
			RuntimeData.getInstance().getMapData().getData()[pos[0]][pos[1]].editObstacleCount(1);;
		}
	}
}
