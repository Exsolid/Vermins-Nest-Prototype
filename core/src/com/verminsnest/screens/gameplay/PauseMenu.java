package com.verminsnest.screens.gameplay;

import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.verminsnest.exceptions.OutOfBounds;
import com.verminsnest.gamedev.VerminsNest;
import com.verminsnest.misc.assets.VNAssetManager;
import com.verminsnest.misc.gui.Button;
import com.verminsnest.misc.gui.ButtonManager;
import com.verminsnest.singletons.RuntimeData;

public class PauseMenu extends GameplayScreen{
	private ButtonManager bManager;
	private boolean movementBlocked;
	private long blockTime;
	private long blockStartTime;
	
	private final static int BACK = 0;
	private final static int QUIT = 1;
	
	public PauseMenu(VerminsNest game, GameManager gameMan){
		super(game,gameMan);
		Button backButton = new Button(game.getConfig().getMessage("SettingsMenu_Back"));
		Button quitButton = new Button(game.getConfig().getMessage("MainMenu_Quit"));
		ArrayList<Button> buttonList = new ArrayList<Button>();
		
		buttonList.add(backButton);
		buttonList.add(quitButton);
		bManager = new ButtonManager(buttonList);
		bManager.setSize(100);
	}
	
	public void init(){
		game.getCamera().update();
		game.setPro();
		// Buttons
		blockTime = 0;
		blockStartTime = System.currentTimeMillis();
		movementBlocked = true;
		try {
			bManager.calcMidofBounds(RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getWidth(), RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getHeight(), new Point((int)(game.getCamera().position.x - RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getWidth() / 2),
					(int) (game.getCamera().position.y - RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getHeight() / 2)));
		} catch (OutOfBounds e) {
			e.printStackTrace();
		}
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
				gameMan.setState(GameManager.RUNNING);
				break;
			case QUIT:
				game.screenMainMenu(gameMan);
				break;
		}
		}
	}


	@Override
	public void dispose() {
		bManager.dispose();
		RuntimeData.getInstance().disposeTextures(VNAssetManager.GAMEPLAY);;
		RuntimeData.getInstance().clearData();
	}

	@Override
	public void update(float stateTime) {
		this.manageControls(stateTime);
	}

}
