package com.verminsnest.screens.gameplay;

import com.verminsnest.gamedev.VerminsNest;

public abstract class GameplayScreen {
	protected VerminsNest game;
	protected GameManager gameMan;
	
	public GameplayScreen(VerminsNest game,GameManager gameMan) {

		this.gameMan = gameMan;
		this.game = game;
	}
	
	public abstract void render(float stateTime);
	public abstract void manageControls(float stateTime);
	public abstract void update(float stateTime);
	public abstract void dispose();
}
