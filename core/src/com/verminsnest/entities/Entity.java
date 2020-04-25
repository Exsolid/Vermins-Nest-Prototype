package com.verminsnest.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.singletons.RuntimeData;

public abstract class Entity {
	protected int textureID = -1;
	protected int[] pos;
	protected int[] size;
	private String id;
	protected Animation<TextureRegion> currentAni;
	protected Texture shadow;
	protected int yShadowOffset;
	
	public Entity(int[] pos, int textureID){
		RuntimeData.getInstance().loadTextures(textureID);
		RuntimeData.getInstance().addEntity(this);
		this.textureID = textureID;
		this.pos = pos;
		this.setId(this.toString());
	}
	
	public abstract void init();
	public abstract void setCurrentAni(int animationKey);
	public abstract void update(float stateTime);
	
	public int[] getPos(){
		return pos;
	}
	
	public int[] getSize(){
		return size;
	}
	
	public int getYShadowOffset(){
		return yShadowOffset;
	}
	
	protected void setSize(int width, int height){
		size = new int[]{width,height};
	}

	public String getId() {
		return id;
	}

	private void setId(String id) {
		this.id = id;
	}
	
	public Texture getShadow(){
		return shadow;
	}
	
	public TextureRegion getCurrentFrame(float stateTime) {
		return currentAni.getKeyFrame(stateTime, true);
	}
	public void dispose(){
		//TODO log textureid -1
		RuntimeData.getInstance().disposeTextures(textureID);
	}
	public int getTextureID(){
		return textureID;
	}
}
