package com.verminsnest.screens;

import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.verminsnest.exceptions.OutOfBounds;
import com.verminsnest.gamedev.VerminsNest;
import com.verminsnest.mapgen.WorldGen;
import com.verminsnest.misc.Button;
import com.verminsnest.misc.ButtonManager;

public class MainMenu implements Screen {

	// Misc
	private VerminsNest game;
	private boolean running;

	// Buttons
	private ButtonManager bManager;
	private boolean movementBlocked;
	private long blockTime;
	private long blockStartTime;
	private static final int START = 0;
	private static final int SETTINGS = 1;
	private static final int CREDITS = 2;
	private static final int QUIT = 3;

	// Textures
	private Texture backgroundImg;
	private Texture backgroundScrollImg;

	public MainMenu(VerminsNest game) {
		this.game = game;
	}

	@Override
	public void show() {
		game.getCamera().position.set(game.getConfig().getResolution()[0]/2,game.getConfig().getResolution()[1]/2,0);
		
		// Misc
		running = true;

		// Textures
		backgroundImg = new Texture("textures/menus/Background.png");
		backgroundScrollImg = new Texture("textures/menus/MenuScroll.png");

		// Buttons
		blockTime = 0;
		blockStartTime = System.currentTimeMillis();
		movementBlocked = true;

		Button playButton = new Button(game.getConfig().getMessage("MainMenu_Play"));
		Button settingsButton = new Button(game.getConfig().getMessage("MainMenu_Settings"));
		Button creditsButton = new Button(game.getConfig().getMessage("MainMenu_Credits"));
		Button quitButton = new Button(game.getConfig().getMessage("MainMenu_Quit"));

		ArrayList<Button> buttonList = new ArrayList<Button>();
		buttonList.add(playButton);
		buttonList.add(settingsButton);
		buttonList.add(creditsButton);
		buttonList.add(quitButton);
		bManager = new ButtonManager(buttonList,"MainMenu");
		bManager.setSize(100);
		try {
			bManager.calcMidofBounds(backgroundScrollImg.getWidth(), backgroundScrollImg.getHeight(), new Point(game.getConfig().getResolution()[0] / 2 - backgroundScrollImg.getWidth() / 2,
					game.getConfig().getResolution()[1] / 2 - backgroundScrollImg.getHeight() / 2));
		} catch (OutOfBounds e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(float delta) {
		if (running) {
			game.setPro();
			game.getBatch().begin();
			game.getBatch().draw(backgroundImg,game.getConfig().getResolution()[0] / 2 - backgroundImg.getWidth() / 2,
					game.getConfig().getResolution()[1] / 2 - backgroundImg.getHeight() / 2);
			game.getBatch().draw(backgroundScrollImg, game.getConfig().getResolution()[0] / 2 - backgroundScrollImg.getWidth() / 2,
					game.getConfig().getResolution()[1] / 2 - backgroundScrollImg.getHeight() / 2);
			bManager.draw(game.getBatch());
			game.getBatch().end();
			this.mangageControls();
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
		running = false;
		bManager.dispose();
		backgroundImg.dispose();
		backgroundScrollImg.dispose();
	}

	public void close() {
		game.dispose();
	}

	//TODO Change to preferences later
	private void mangageControls() {

		blockTime = System.currentTimeMillis() - blockStartTime;
		if (blockTime > 225) {
			movementBlocked = false;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			if (!movementBlocked) {
				bManager.next();
				movementBlocked = true;
				blockStartTime = System.currentTimeMillis();
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			if (!movementBlocked) {
				bManager.prev();
				movementBlocked = true;
				blockStartTime = System.currentTimeMillis();
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
			if (!movementBlocked) {
				switch (bManager.getIndex()) {
				case START:
					// World generation
					WorldGen gen = new WorldGen(game);
					gen.setData(6, 30, 30, 10,new Texture("textures/level-sheets/cave/Mountain-Sheet.png"));
					game.screenLoading(LoadingScreen.GAMEMANAGER, this);
					break;
				case SETTINGS:
					game.screenSettings();;
					this.dispose();
					break;
				case CREDITS:
					game.setScreen(new CreditsMenu(game));
					this.dispose();
					break;
				case QUIT:
					this.dispose();
					this.close();
					break;
				}
				movementBlocked = true;
				blockStartTime = System.currentTimeMillis();
			}
		}
	}
}
