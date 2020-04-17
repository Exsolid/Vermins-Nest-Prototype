package com.verminsnest.screens.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.verminsnest.gamedev.VerminsNest;

public class GameManager implements Screen {

	// Game
	private VerminsNest game;
	
	// Rendering
	private float timeSinceRender = 0;
	private float updateStep = 1 / 120f;
	private float stateTime;

	public static final int RUNNING = 0;
	public static final int PAUSEMENU = 1;
	public static final int INVMENU = 2;
	private int state;
	
	private Gameplay gameplay;
	private PauseMenu pausemenu;
	
	public GameManager( VerminsNest game) {
		this.game = game;
	}

	@Override
	public void show() {
		gameplay = new Gameplay(game, this);
		pausemenu = new PauseMenu(game, this);
		state = RUNNING;
	}

	@Override
	public void render(float delta) {
			timeSinceRender += Gdx.graphics.getDeltaTime();
			//Cap out rendering cycle to 120 frames/second
			if (timeSinceRender >= updateStep) {
				stateTime += delta;
				timeSinceRender -= updateStep;
				switch(state){
				case RUNNING:
					gameplay.update(stateTime);
					gameplay.render(stateTime);
					break;
				case PAUSEMENU:
					pausemenu.update(stateTime);
					gameplay.render(stateTime);
					pausemenu.render(stateTime);
					break;
				case INVMENU:
					break;
				}
			
		}
	}

	public void setState(int state){
		switch(state){
		case RUNNING:
			this.state = RUNNING;
			break;
		case PAUSEMENU:
			pausemenu.init();
			this.state = PAUSEMENU;
			break;
		case INVMENU:
			this.state = INVMENU;
			break;
		}
	
	}
	
	@Override
	public void resize(int width, int height) {
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

	@Override
	public void dispose() {
		gameplay.dispose();
		pausemenu.dispose();
	}
}
