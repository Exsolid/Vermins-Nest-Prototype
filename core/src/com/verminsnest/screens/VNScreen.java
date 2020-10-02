package com.verminsnest.screens;

import com.badlogic.gdx.Screen;

public abstract class VNScreen implements Screen{
	protected boolean isDisposed;
	
	public VNScreen() {
		isDisposed = true;
	}
	
	public boolean isDisposed(){
		return isDisposed;
	}
	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}
	public abstract void init();
	public abstract void reload();
}
