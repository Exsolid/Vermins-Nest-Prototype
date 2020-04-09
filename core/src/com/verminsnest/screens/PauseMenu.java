package com.verminsnest.screens;

import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.verminsnest.exceptions.OutOfBounds;
import com.verminsnest.gamedev.VerminsNest;
import com.verminsnest.misc.assets.VNAssetManager;
import com.verminsnest.misc.gui.Button;
import com.verminsnest.misc.gui.ButtonManager;
import com.verminsnest.singletons.RuntimeData;

public class PauseMenu implements Screen {

	private VerminsNest game;
	private boolean running;
	
	private ButtonManager bManager;
	private boolean movementBlocked;
	private long blockTime;
	private long blockStartTime;
	
	private final static int BACK = 0;
	private final static int QUIT = 1;
	
	public PauseMenu(VerminsNest game){
		this.game = game;
	}
	
	@Override
	public void show() {
		game.getCamera().update();
		game.setPro();
		Button backButton = new Button(game.getConfig().getMessage("SettingsMenu_Back"));
		Button quitButton = new Button(game.getConfig().getMessage("MainMenu_Quit"));
		ArrayList<Button> buttonList = new ArrayList<Button>();
		
		buttonList.add(backButton);
		buttonList.add(quitButton);
		bManager = new ButtonManager(buttonList);
		bManager.setSize(100);
		try {
			bManager.calcMidofBounds(RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getWidth(), RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getHeight(), new Point((int)(game.getCamera().position.x - RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getWidth() / 2),
					(int) (game.getCamera().position.y - RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getHeight() / 2)));
		} catch (OutOfBounds e) {
			e.printStackTrace();
		}
	}
	
	public void init(){
		// Buttons
		blockTime = 0;
		blockStartTime = System.currentTimeMillis();
		movementBlocked = true;
		
		running = false;
	}
	
	@Override
	public void render(float delta) {
		if (running) {
			game.getBatch().begin();
			game.getBatch().draw(RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png"), game.getCamera().position.x - RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getWidth() / 2,
					game.getCamera().position.y - RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getHeight() / 2);
			bManager.draw(game.getBatch());
			game.getBatch().end();
			this.mangageControls();
			game.getCamera().update();
		}
	}

	private void mangageControls() {
		blockTime = System.currentTimeMillis() - blockStartTime;
		if (blockTime > 225) {
			movementBlocked = false;
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.W) && !movementBlocked){
			bManager.prev();
			movementBlocked = true;
			blockStartTime = System.currentTimeMillis();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S) && !movementBlocked){
			bManager.next();
			movementBlocked = true;
			blockStartTime = System.currentTimeMillis();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.ENTER) && !movementBlocked){
			switch(bManager.getIndex()){
			case BACK:
				game.togglePause();
				break;
			case QUIT:
				RuntimeData.getInstance().disposeTextures(VNAssetManager.GAMEPLAY);;
				RuntimeData.getInstance().clearData();
				game.screenMainMenu(this);
				break;
		}
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		running = false;
	}

	@Override
	public void resume() {
		running = true;
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		bManager.dispose();
	}

}
