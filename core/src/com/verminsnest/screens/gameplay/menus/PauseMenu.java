package com.verminsnest.screens.gameplay.menus;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.verminsnest.core.VerminsNest;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.misc.gui.ButtonManager;
import com.verminsnest.screens.gameplay.GameManager;
import com.verminsnest.screens.gameplay.GameplayOverlay;

public class PauseMenu extends GameplayOverlay{
	private ButtonManager bManager;
	
	private final static int BACK = 0;
	private final static int QUIT = 1;
	
	public PauseMenu(GameManager gameMan){
		super(gameMan);
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
		RuntimeData.getInstance().getGame().getCamera().update();
		RuntimeData.getInstance().getGame().setPro();
			bManager.setMidOfBounds(new int[]{RuntimeData.getInstance().getTexture("textures/menus/scrolls/VerticalScroll_Small.png").getWidth(), RuntimeData.getInstance().getTexture("textures/menus/scrolls/VerticalScroll_Small.png").getHeight()}, new int[]{(int)(RuntimeData.getInstance().getGame().getCamera().position.x - RuntimeData.getInstance().getTexture("textures/menus/scrolls/VerticalScroll_Small.png").getWidth() / 2),
					(int) (RuntimeData.getInstance().getGame().getCamera().position.y - RuntimeData.getInstance().getTexture("textures/menus/scrolls/VerticalScroll_Small.png").getHeight() / 2)});
	}
	public void render(float stateTime) {
		RuntimeData.getInstance().getGame().getBatch().draw(RuntimeData.getInstance().getTexture("textures/menus/scrolls/VerticalScroll_Small.png"), RuntimeData.getInstance().getGame().getCamera().position.x - RuntimeData.getInstance().getTexture("textures/menus/scrolls/VerticalScroll_Small.png").getWidth() / 2,
				RuntimeData.getInstance().getGame().getCamera().position.y - RuntimeData.getInstance().getTexture("textures/menus/scrolls/VerticalScroll_Small.png").getHeight() / 2);
		bManager.draw(RuntimeData.getInstance().getGame().getBatch());
	}
	@Override
	public void manageControls(float delta) {
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
				RuntimeData.getInstance().getGame().showScreen(VerminsNest.MAINMENU);
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
	}

	@Override
	public void update(float delta) {
		this.manageControls(delta);
	}

}
