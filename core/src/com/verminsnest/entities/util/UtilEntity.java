package com.verminsnest.entities.util;

import com.verminsnest.entities.Entity;

public abstract class UtilEntity extends Entity {
	protected boolean isGrounded;
	public UtilEntity(int[] pos, int textureID) {
		super(pos, textureID);
		init();
	}
	public boolean isGrounded() {
		return isGrounded;
	}
	public void setGrounded(boolean isGrounded) {
		this.isGrounded = isGrounded;
	}
}
