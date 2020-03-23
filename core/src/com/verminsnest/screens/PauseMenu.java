package com.verminsnest.screens;

import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.verminsnest.exceptions.OutOfBounds;
import com.verminsnest.gamedev.VerminsNest;
import com.verminsnest.misc.Button;
import com.verminsnest.misc.ButtonManager;

public class PauseMenu implements Screen {


	private Texture backgroundScrollImg;

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
		bManager = new ButtonManager(buttonList,"MainMenu");
		bManager.setSize(100);
		try {
			bManager.calcMidofBounds(backgroundScrollImg.getWidth(), backgroundScrollImg.getHeight(), new Point((int)(game.getCamera().position.x - backgroundScrollImg.getWidth() / 2),
					(int) (game.getCamera().position.y - backgroundScrollImg.getHeight() / 2)));
		} catch (OutOfBounds e) {
			e.printStackTrace();
		}
	}
	
	public void init(){
		backgroundScrollImg = new Texture("textures/menus/MenuScroll.png");
		
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
			game.getBatch().draw(backgroundScrollImg, game.getCamera().position.x - backgroundScrollImg.getWidth() / 2,
					game.getCamera().position.y - backgroundScrollImg.getHeight() / 2);
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
				this.dispose();
				game.screenMainMenu();
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
		// TODO Auto-generated method stub

	}

}
