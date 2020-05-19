package com.verminsnest.screens;

import com.badlogic.gdx.Screen;
import com.verminsnest.core.VerminsNest;

public abstract class VNScreen implements Screen{
	protected VerminsNest game;
	protected boolean isDisposed;
	
	public VNScreen(VerminsNest game) {
		this.game = game;
		isDisposed = true;
	}
	
	public boolean isDisposed(){
		return isDisposed;
	}
	
	public abstract void init();
	public abstract void reload();
}
