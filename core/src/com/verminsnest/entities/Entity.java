package com.verminsnest.entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.core.management.Indentifiers;
import com.verminsnest.core.management.data.RuntimeData;

public abstract class Entity {
	protected int textureID = -1;
	protected int[] pos;
	protected int[] size;
	private String id;
	protected Animation<TextureRegion> currentAni;
	protected Texture shadow;
	protected int yShadowOffset;
	protected boolean isObstacle;
	protected int state;
	protected float rotation;
	protected float internalStateTime;
	public Entity(int[] pos, int textureID){
		if(textureID != -1){
			RuntimeData.getInstance().loadTextures(textureID);
		}
		RuntimeData.getInstance().getEntityManager().addEntity(this);
		this.textureID = textureID;
		this.pos = pos;
		isObstacle = true;
		this.setId(this.toString());
		rotation = 0;
		internalStateTime = 0;
	}
	
	public abstract void init();
	public abstract void setCurrentAni(int animationKey);
	public abstract void update(float delta);
	
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
	
	public TextureRegion getCurrentFrame(float delta) {
		return currentAni.getKeyFrame(internalStateTime, true);
	}
	
	public void dispose(){
		if(textureID != -1){
			RuntimeData.getInstance().disposeTextures(textureID);
		}
	}
	public int getTextureID(){
		return textureID;
	}
	
	public boolean isObstacle(){
		return isObstacle;
	}
	
	public int getState(){
		return state;
	}

	public float getRotation() {
		if(state != Indentifiers.STATE_CAST){
			return rotation;
		}else{
			return 0;
		}
	}
	
	public ArrayList<int[]> getMapPos(){
		ArrayList<int[]> tiles = new ArrayList<>();
		//Left pos
		tiles.add(new int[]{(this.getPos()[0]- this.getPos()[0] % 128) / 128,
				(this.getPos()[1]- this.getPos()[1] % 128) / 128});
		//Right pos
		tiles.add(new int[]{((this.getPos()[0]+size[0])- (this.getPos()[0]+size[0]) % 128) / 128,
				(this.getPos()[1]- this.getPos()[1] % 128) / 128});
		//Left top pos
		tiles.add(new int[]{(this.getPos()[0]- this.getPos()[0] % 128) / 128,
				((this.getPos()[1]+size[1])- (this.getPos()[1]+size[1]) % 128) / 128});
		//Right top pos
		tiles.add(new int[]{((this.getPos()[0]+size[0])- (this.getPos()[0]+size[0]) % 128) / 128,
				((this.getPos()[1]+size[1])- (this.getPos()[1]+size[1]) % 128) / 128});
		return tiles;
	}
}
