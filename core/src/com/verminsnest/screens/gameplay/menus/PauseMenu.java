package com.verminsnest.screens.gameplay.menus;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.verminsnest.core.Indentifiers;
import com.verminsnest.core.VerminsNest;
import com.verminsnest.core.singletons.RuntimeData;
import com.verminsnest.misc.gui.ButtonManager;
import com.verminsnest.screens.gameplay.GameManager;

public class PauseMenu extends GameplayOverlay{
	private ButtonManager bManager;
	
	private final static int BACK = 0;
	private final static int QUIT = 1;
	
	public PauseMenu(VerminsNest game, GameManager gameMan){
		super(game,gameMan);
		ArrayList<String> backButton = new ArrayList<>();
		backButton.add("SettingsMenu_Back");
		ArrayList<String> quitButton = new ArrayList<>();
		quitButton.add("MainMenu_Quit");
		ArrayList<ArrayList<String>> buttonList = new ArrayList<>();
		
		buttonList.add(backButton);
		buttonList.add(quitButton);
		bManager = new ButtonManager(buttonList, 100, true, "", "", true);
	}
	
	public void init(){
		game.getCamera().update();
		game.setPro();
			bManager.setMidOfBounds(new int[]{RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getWidth(), RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getHeight()}, new int[]{(int)(game.getCamera().position.x - RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getWidth() / 2),
					(int) (game.getCamera().position.y - RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getHeight() / 2)});
	}
	public void render(float stateTime) {
		game.getBatch().begin();
		game.getBatch().draw(RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png"), game.getCamera().position.x - RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getWidth() / 2,
				game.getCamera().position.y - RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getHeight() / 2);
		bManager.draw(game.getBatch());
		game.getBatch().end();
	}
	@Override
	public void manageControls(float stateTime) {
		if(Gdx.input.isKeyPressed(Input.Keys.W) && !gameMan.isControlBlocked()){
			bManager.prev();
			gameMan.resetBlocked();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S) && !gameMan.isControlBlocked()){
			bManager.next();
			gameMan.resetBlocked();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.ENTER) && !gameMan.isControlBlocked()){
			switch(bManager.getIndex()){
			case BACK:
				gameMan.setState(GameManager.RUNNING);
				break;
			case QUIT:
				game.showScreen(VerminsNest.MAINMENU);
				break;
			}
			gameMan.resetBlocked();
		}
		//Unpause
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && !gameMan.isControlBlocked()) {
			gameMan.setState(GameManager.RUNNING);
		}
	}


	@Override
	public void dispose() {
		bManager.dispose();
		RuntimeData.getInstance().disposeTextures(Indentifiers.ASSETMANAGER_GAMEPLAY);;
		RuntimeData.getInstance().clearData();
	}

	@Override
	public void update(float stateTime) {
		this.manageControls(stateTime);
	}

}
