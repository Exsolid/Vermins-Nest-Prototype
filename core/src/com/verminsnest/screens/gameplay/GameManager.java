package com.verminsnest.screens.gameplay;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.verminsnest.core.VerminsNest;
import com.verminsnest.core.engine.shaders.Shader;
import com.verminsnest.core.management.Indentifiers;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.misc.gui.dialogs.ChoiceDialog;
import com.verminsnest.screens.VNScreen;
import com.verminsnest.screens.gameplay.menus.LevelMenu;
import com.verminsnest.screens.gameplay.menus.PauseMenu;

public class GameManager extends VNScreen {

	// Rendering
	private float timeSinceRender = 0;
	private float updateStep = 1 / 120f;

	// Control blocking
	private long blockTime;
	private boolean controlBlocked;
	private long blockStartTime;

	public static final int RUNNING = 0;
	public static final int PAUSEMENU = 1;
	public static final int LEVELMENU = 2;
	public static final int DIALOG = 3;
	private int state;

	private Gameplay gameplay;
	private PauseMenu pauseMenu;
	private LevelMenu levelMenu;
	private ChoiceDialog dialog;
	
	public GameManager() {
		super();
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		
		if (!RuntimeData.getInstance().isGameOver()) {
			RuntimeData.getInstance().getGame().getBatch().begin();
			switch (state) {
			case RUNNING:
				gameplay.render(delta);
				break;
			case PAUSEMENU:
				gameplay.render(delta);
				pauseMenu.render(delta);
				break;
			case LEVELMENU:
				gameplay.render(delta);
				levelMenu.render(delta);
				break;
			case DIALOG:
				gameplay.render(delta);
				dialog.render(delta);
				break;
			}
			RuntimeData.getInstance().getGame().getBatch().end();
		}
		
		timeSinceRender += Gdx.graphics.getDeltaTime();
		// Cap out rendering cycle to 120 frames/second
		if (timeSinceRender >= updateStep) {
			timeSinceRender -= updateStep;

			blockTime = System.currentTimeMillis() - blockStartTime;
			if (blockTime > 225) {
				controlBlocked = false;
			}

			if (!RuntimeData.getInstance().isGameOver()) {
				switch (state) {
				case RUNNING:
					gameplay.update(delta);
					break;
				case PAUSEMENU:
					pauseMenu.update(delta);
					break;
				case LEVELMENU:
					levelMenu.update(delta);
					break;
				case DIALOG:
					dialog.update(delta);
					if(dialog.getState() != Indentifiers.DIALOG_OPEN){
						setState(RUNNING);
					}
					break;
				}
			} else {
				RuntimeData.getInstance().getGame().showScreen(VerminsNest.MAINMENU);
				RuntimeData.getInstance().setGameOver(false);
			}
		}
	}

	public void setState(int state) {
		switch (state) {
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
		case DIALOG:
			this.state = DIALOG;
			break;
		}
		this.resetBlocked();
	}

	public void resetBlocked() {
		controlBlocked = true;
		blockStartTime = System.currentTimeMillis();
	}

	public boolean isControlBlocked() {
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
		Shader.getInstance().dispose();
		
		gameplay.dispose();
		levelMenu.dispose();
		pauseMenu.dispose();
		
		RuntimeData.getInstance().disposeTextures(Indentifiers.ASSETMANAGER_GAMEPLAY);
		RuntimeData.getInstance().getAudioManager().stopMusic();
		RuntimeData.getInstance().getEntityManager().clearData();
		
		isDisposed = true;
	}

	@Override
	public void init() {
		if (RuntimeData.getInstance().getEntityManager().getCharacter() == null) {
			RuntimeData.getInstance().getEntityManager().createCharacter();
			for (int x = 0; x < RuntimeData.getInstance().getMapData().getData().length; x++) {
				for (int y = 0; y < RuntimeData.getInstance().getMapData().getData()[0].length; y++) {
					if (RuntimeData.getInstance().getMapData().getData()[x][y].isWalkable()) {
						RuntimeData.getInstance().getEntityManager().getCharacter()
								.getPos()[0] = RuntimeData.getInstance().getMapData().getData()[x][y].getxPos();
						RuntimeData.getInstance().getEntityManager().getCharacter()
								.getPos()[1] = RuntimeData.getInstance().getMapData().getData()[x][y].getyPos();
					}
				}
			}
			
			gameplay = new Gameplay(this);
			pauseMenu = new PauseMenu(this);
			levelMenu = new LevelMenu(this);
			pauseMenu.init();
			levelMenu.init();
			ArrayList<String> temp = new ArrayList<String>();
			temp.add("audio/music/Into The Unknown.mp3");
			RuntimeData.getInstance().getAudioManager().playMusicQueue(temp);
		} else {
			//On layers
			for (int x = 0; x < RuntimeData.getInstance().getMapData().getData().length; x++) {
				for (int y = 0; y < RuntimeData.getInstance().getMapData().getData()[0].length; y++) {
					if (RuntimeData.getInstance().getMapData().getData()[x][y].isWalkable()) {
						RuntimeData.getInstance().getEntityManager().getCharacter()
								.getPos()[0] = RuntimeData.getInstance().getMapData().getData()[x][y].getxPos();
						RuntimeData.getInstance().getEntityManager().getCharacter()
								.getPos()[1] = RuntimeData.getInstance().getMapData().getData()[x][y].getyPos();
					}
				}
			}
		}

		state = RUNNING;

		// Controls
		blockTime = 0;
		blockStartTime = System.currentTimeMillis();
		controlBlocked = true;
		isDisposed = false;
	}

	@Override
	public void reload() {
	}
	
	public void setDialog(ChoiceDialog dia){
		if(dia == null){
			dialog.dispose();
			setState(RUNNING);
		}
		else setState(DIALOG);
		dialog = dia;
	}
	
	public ChoiceDialog getDialog(){
		return dialog;
	}
}
