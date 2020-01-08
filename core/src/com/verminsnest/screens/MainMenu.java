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
		// Misc
		running = true;

		// Textures
		backgroundImg = new Texture("textures/menus/Background.png");
		backgroundScrollImg = new Texture("textures/menus/MenuScroll.png");

		// Buttons
		blockTime = 0;
		blockStartTime = 0;
		movementBlocked = false;

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
		bManager.setSize(150);
		try {
			bManager.calcMidofBounds(backgroundScrollImg.getWidth(), backgroundScrollImg.getHeight(), new Point(Gdx.graphics.getWidth() / 2 - backgroundScrollImg.getWidth() / 2,
					Gdx.graphics.getHeight() / 2 - backgroundScrollImg.getHeight() / 2));
		} catch (OutOfBounds e) {
			e.printStackTrace();
		}

	}

	@Override
	public void render(float delta) {
		if (running) {
			game.getBatch().begin();
			game.getBatch().draw(backgroundImg, 0, 0);
			game.getBatch().draw(backgroundScrollImg, Gdx.graphics.getWidth() / 2 - backgroundScrollImg.getWidth() / 2,
					Gdx.graphics.getHeight() / 2 - backgroundScrollImg.getHeight() / 2);
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
		bManager.dispose();
		backgroundImg.dispose();
		backgroundScrollImg.dispose();
		running = false;
	}

	public void close() {
		game.dispose();
	}

	// Change to preferences later
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
					break;
				case SETTINGS:
					game.setScreen(new SettingsMenu(game));
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
