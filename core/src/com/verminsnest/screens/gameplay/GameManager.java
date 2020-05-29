package com.verminsnest.screens.gameplay;

import com.badlogic.gdx.Gdx;
import com.verminsnest.core.VerminsNest;
import com.verminsnest.screens.VNScreen;
import com.verminsnest.screens.gameplay.menus.LevelMenu;
import com.verminsnest.screens.gameplay.menus.PauseMenu;

public class GameManager extends VNScreen {
	
	// Rendering
	private float timeSinceRender = 0;
	private float updateStep = 1 / 120f;
	private float stateTime;

	//Control blocking
	private long blockTime;
	private boolean controlBlocked;
	private long blockStartTime;
	
	public static final int RUNNING = 0;
	public static final int PAUSEMENU = 1;
	public static final int LEVELMENU = 2;
	private int state;
	
	private Gameplay gameplay;
	private PauseMenu pauseMenu;
	private LevelMenu levelMenu;
	
	public GameManager( VerminsNest game) {
		super(game);
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
			timeSinceRender += Gdx.graphics.getDeltaTime();
			//Cap out rendering cycle to 120 frames/second
			if (timeSinceRender >= updateStep) {
				stateTime += delta;
				timeSinceRender -= updateStep;
				game.getBatch().begin();
				switch(state){
				case RUNNING:
					gameplay.update(stateTime);
					gameplay.render(stateTime);
					break;
				case PAUSEMENU:
					gameplay.render(stateTime);
					pauseMenu.render(stateTime);
					pauseMenu.update(stateTime);
					break;
				case LEVELMENU:
					gameplay.render(stateTime);
					levelMenu.render(stateTime);
					levelMenu.update(stateTime);
					break;
				}
				game.getBatch().end();
				blockTime = System.currentTimeMillis() - blockStartTime;
				if (blockTime > 225) {
					controlBlocked = false;
				}
		}
	}

	public void setState(int state){
		switch(state){
		case RUNNING:
			this.state = RUNNING;
			break;
		case PAUSEMENU:
			pauseMenu.init();
			this.state = PAUSEMENU;
			break;
		case LEVELMENU:
			levelMenu.init();
			this.state = LEVELMENU;
			break;
		}
		this.resetBlocked();
	}
	
	public void resetBlocked(){
		controlBlocked = true;
		blockStartTime = System.currentTimeMillis();
	}
	
	public boolean isControlBlocked(){
		return controlBlocked;
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
		levelMenu.dispose();
		pauseMenu.dispose();
		isDisposed = true;
	}

	@Override
	public void init() {
		gameplay = new Gameplay(game, this);
		pauseMenu = new PauseMenu(game, this);
		levelMenu = new LevelMenu(game, this);
		pauseMenu.init();
		levelMenu.init();
		
		state = RUNNING;
		

		// Controls
		blockTime = 0;
		blockStartTime = System.currentTimeMillis();
		controlBlocked = true;
		isDisposed = false;
	}

	@Override
	public void reload() {
		// TODO Auto-generated method stub
		
	}
}
