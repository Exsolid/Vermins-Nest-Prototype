package com.verminsnest.entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.core.VNLogger;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.core.management.ids.Indentifiers;

public abstract class Entity {
	protected int textureID = -1;
	protected int[] pos;
	protected int[] hitbox;
	protected int[] size;
	private String id;
	protected Animation<TextureRegion> currentAni;
	protected Texture shadow;
	protected int yShadowOffset;
	protected int xShadowOffset;
	protected boolean isObstacle;
	protected int state;
	protected float rotation;
	protected float internalStateTime;
	protected boolean isForced;
	protected float forceTimer;
	protected int forceDirection;
	protected int renderLayer;
	
	protected boolean caresObstacles;

	private boolean searchAlternativPaths;
	
	public Entity(int[] pos, int textureID, int renderLayer){
		if(textureID != -1){
			if(!RuntimeData.getInstance().areAssetsLoaded(textureID))VNLogger.logErr("Texture for ID " + textureID+ " not loaded", this.getClass());
		}
		this.renderLayer = renderLayer;
		RuntimeData.getInstance().getEntityManager().addEntity(this);
		this.textureID = textureID;
		caresObstacles = true;
		this.pos = pos;
		isObstacle = true;
		this.setId(this.toString());
		rotation = 0;
		internalStateTime = 0;
		id = this.toString();
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

	public int[] getHitbox(){
		return hitbox;
	}
	
	protected void setSize(int width, int height){
		size = new int[]{width,height};
		if(hitbox == null)hitbox= new int[]{width,height};
	}
	
	protected void setHitbox(int width, int height){
		hitbox = new int[]{width,height};
	}
	
	public int getYShadowOffset(){
		return yShadowOffset;
	}
	
	public int getXShadowOffset(){
		return xShadowOffset;
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
		tiles.add(new int[]{((this.getPos()[0]+hitbox[0])- (this.getPos()[0]+hitbox[0]) % 128) / 128,
				(this.getPos()[1]- this.getPos()[1] % 128) / 128});
		//Left top pos
		tiles.add(new int[]{(this.getPos()[0]- this.getPos()[0] % 128) / 128,
				((this.getPos()[1]+hitbox[1])- (this.getPos()[1]+hitbox[1]) % 128) / 128});
		//Right top pos
		tiles.add(new int[]{((this.getPos()[0]+hitbox[0])- (this.getPos()[0]+hitbox[0]) % 128) / 128,
				((this.getPos()[1]+hitbox[1])- (this.getPos()[1]+hitbox[1]) % 128) / 128});
		return tiles;
	}

	public boolean isForced() {
		return isForced;
	}

	public void setForced(boolean isForced, float timer, int forceDirection) {
		this.isForced = isForced;
		this.forceDirection = forceDirection;
		if(isForced)forceTimer = timer;
		else forceTimer = 0;
	}
	
	public int getForceDirection(){
		return forceDirection;
	}

	public int getRenderLayer() {
		return renderLayer;
	}

	public void setRenderLayer(int renderLayer) {
		this.renderLayer = renderLayer;
	}
	
	public boolean caresObstacles(){
		return caresObstacles;
	}
	
	public void dispose(){}

	public boolean isSearchAlternativPaths() {
		return searchAlternativPaths;
	}

	public void setSearchAlternativPaths(boolean searchAlternativPaths) {
		this.searchAlternativPaths = searchAlternativPaths;
	}
}
