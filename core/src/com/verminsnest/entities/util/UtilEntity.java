package com.verminsnest.entities.util;

import com.verminsnest.entities.Entity;

public abstract class UtilEntity extends Entity {

	public UtilEntity(int[] pos, int textureID) {
		super(pos, textureID);
		init();
	}
}
