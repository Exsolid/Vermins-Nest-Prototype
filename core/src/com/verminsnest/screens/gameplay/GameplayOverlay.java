package com.verminsnest.screens.gameplay;

public abstract class GameplayOverlay {
	protected GameManager gameMan;
	
	public GameplayOverlay(GameManager gameMan) {
		this.gameMan = gameMan;
	}
	
	public abstract void render(float delta);
	public abstract void manageControls(float delta);
	public abstract void update(float delta);
	public abstract void dispose();
}
