package com.verminsnest.screens;

import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.verminsnest.exceptions.OutOfBounds;
import com.verminsnest.gamedev.VerminsNest;
import com.verminsnest.mapgen.WorldGen;
import com.verminsnest.misc.assets.VNAssetManager;
import com.verminsnest.misc.gui.Button;
import com.verminsnest.misc.gui.ButtonManager;
import com.verminsnest.singletons.RuntimeData;

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

	public MainMenu(VerminsNest game) {
		this.game = game;
	}

	@Override
	public void show() {
		game.getCamera().position.set(game.getConfig().getResolution()[0]/2,game.getConfig().getResolution()[1]/2,0);
		
		// Misc
		running = true;

		RuntimeData.getInstance().loadTextures(VNAssetManager.MENU);
		
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
		bManager = new ButtonManager(buttonList);
		bManager.setSize(100);
		try {
			bManager.calcMidofBounds(RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getWidth(), RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getHeight(), new Point(game.getConfig().getResolution()[0] / 2 - RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getWidth() / 2,
					game.getConfig().getResolution()[1] / 2 - RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getHeight() / 2));
		} catch (OutOfBounds e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(float delta) {
		if (running) {
			game.setPro();
			game.getBatch().begin();
			game.getBatch().draw(RuntimeData.getInstance().getAsset("textures/general/Background.png"),game.getConfig().getResolution()[0] / 2 - RuntimeData.getInstance().getAsset("textures/general/Background.png").getWidth() / 2,
					game.getConfig().getResolution()[1] / 2 - RuntimeData.getInstance().getAsset("textures/general/Background.png").getHeight() / 2);
			game.getBatch().draw(RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png"), game.getConfig().getResolution()[0] / 2 - RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getWidth() / 2,
					game.getConfig().getResolution()[1] / 2 - RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getHeight() / 2);
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
					RuntimeData.getInstance().loadTextures(VNAssetManager.GAMEPLAY);
					WorldGen gen = new WorldGen(game);
					gen.setData(6, 30, 30, 10,(RuntimeData.getInstance().getAsset("textures/level-sheets/cave/Mountain-Sheet.png")));
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
					RuntimeData.getInstance().dispose();
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
