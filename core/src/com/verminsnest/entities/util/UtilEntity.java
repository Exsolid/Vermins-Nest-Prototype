package com.verminsnest.entities.util;

import com.verminsnest.entities.Entity;

public abstract class UtilEntity extends Entity {
	protected int sizeID;
	protected int textureNum;
	protected boolean impassible;
	
	public UtilEntity(int[] pos, int textureID, int renderLayer) {
		super(pos, textureID, renderLayer);
		init();
	}
	
	public UtilEntity(int[] pos, int textureID, int sizeID, int renderLayer) {
		super(pos, textureID, renderLayer);
		this.sizeID = sizeID;
		init();
	}
	
	public boolean isImpassible(){
		return impassible;
	}
}
