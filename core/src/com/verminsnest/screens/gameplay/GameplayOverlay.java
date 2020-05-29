package com.verminsnest.screens.gameplay;

import com.verminsnest.core.VerminsNest;

public abstract class GameplayOverlay {
	protected VerminsNest game;
	protected GameManager gameMan;
	
	public GameplayOverlay(VerminsNest game,GameManager gameMan) {

		this.gameMan = gameMan;
		this.game = game;
	}
	
	public abstract void render(float stateTime);
	public abstract void manageControls(float stateTime);
	public abstract void update(float stateTime);
	public abstract void dispose();
}
