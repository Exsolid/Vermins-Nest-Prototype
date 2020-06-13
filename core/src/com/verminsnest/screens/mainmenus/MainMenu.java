package com.verminsnest.screens.mainmenus;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.verminsnest.core.VerminsNest;
import com.verminsnest.core.management.Indentifiers;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.misc.gui.ButtonManager;
import com.verminsnest.screens.VNScreen;
import com.verminsnest.world.generation.map.World;
import com.verminsnest.world.generation.spawning.EnemySpawner;

public class MainMenu extends VNScreen {

	// Buttons
	private ButtonManager bManager;
	private boolean movementBlocked;
	private long blockTime;
	private long blockStartTime;
	private static final int START = 0;
	private static final int SETTINGS = 1;
	private static final int CREDITS = 2;
	private static final int QUIT = 3;

	public MainMenu() {
		super();
	}

	@Override
	public void show() {
		RuntimeData.getInstance().getGame().getCamera().position.set(RuntimeData.getInstance().getGame().getConfig().getResolution()[0] / 2, RuntimeData.getInstance().getGame().getConfig().getResolution()[1] / 2,
				0);
		while(bManager.getIndex() != 0){
			bManager.prev();
		}
		movementBlocked = true;
		blockTime = blockStartTime = System.currentTimeMillis();
	}

	@Override
	public void render(float delta) {
		RuntimeData.getInstance().getGame().setPro();
		RuntimeData.getInstance().getGame().getBatch().begin();
		RuntimeData.getInstance().getGame().getBatch().draw(RuntimeData.getInstance().getAsset("textures/general/Background.png"),
				Gdx.graphics.getWidth() / 2
						- RuntimeData.getInstance().getAsset("textures/general/Background.png").getWidth() / 2,
				Gdx.graphics.getHeight() / 2
						- RuntimeData.getInstance().getAsset("textures/general/Background.png").getHeight() / 2);
		RuntimeData.getInstance().getGame().getBatch().draw(RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png"),
				RuntimeData.getInstance().getGame().getConfig().getResolution()[0] / 2 - RuntimeData.getInstance()
						.getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getWidth() / 2,
				RuntimeData.getInstance().getGame().getConfig().getResolution()[1] / 2 - RuntimeData.getInstance()
						.getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getHeight() / 2);
		bManager.draw(RuntimeData.getInstance().getGame().getBatch());
		RuntimeData.getInstance().getGame().getBatch().end();
		this.mangageControls();
	}

	@Override
	public void resize(int width, int height) {
		bManager.setMidOfBounds(
				new int[] {
						RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png")
								.getWidth(),
						RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png")
								.getHeight() },
				new int[] {
						RuntimeData.getInstance().getGame().getConfig().getResolution()[0] / 2 - RuntimeData.getInstance()
								.getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getWidth() / 2,
						RuntimeData.getInstance().getGame().getConfig().getResolution()[1] / 2 - RuntimeData.getInstance()
								.getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getHeight() / 2 });
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
		isDisposed = true;
	}

	// TODO Change to preferences later
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
		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			if (!movementBlocked) {
				switch (bManager.getIndex()) {
				case START:
					// World generation
					RuntimeData.getInstance().loadTextures(Indentifiers.ASSETMANAGER_GAMEPLAY);
					World gen = new World(RuntimeData.getInstance().getGame());
					gen.setData(3, 20, 20, 10,
							(RuntimeData.getInstance().getAsset("textures/level-sheets/cave/Mountain-Sheet.png")));
					new EnemySpawner(3);
					RuntimeData.getInstance().getGame().showScreen(VerminsNest.LOADGAME);
					break;
				case SETTINGS:
					RuntimeData.getInstance().getGame().showScreen(VerminsNest.SETTINGSMENU);
					break;
				case CREDITS:
					RuntimeData.getInstance().getGame().showScreen(VerminsNest.CREDITSMENU);
					break;
				case QUIT:
					RuntimeData.getInstance().dispose();
					break;
				}
				movementBlocked = true;
				blockStartTime = System.currentTimeMillis();
			}
		}
	}

	@Override
	public void init() {
		// Buttons
		blockTime = 0;
		blockStartTime = System.currentTimeMillis();
		movementBlocked = true;

		ArrayList<String> playButton = new ArrayList<String>();
		playButton.add("MainMenu_Play");
		ArrayList<String> settingsButton = new ArrayList<String>();
		settingsButton.add("MainMenu_Settings");
		ArrayList<String> creditsButton = new ArrayList<String>();
		creditsButton.add("MainMenu_Credits");
		ArrayList<String> quitButton = new ArrayList<String>();
		quitButton.add("MainMenu_Quit");

		ArrayList<ArrayList<String>> buttonList = new ArrayList<>();
		buttonList.add(playButton);
		buttonList.add(settingsButton);
		buttonList.add(creditsButton);
		buttonList.add(quitButton);
		bManager = new ButtonManager(buttonList, 100, true, "", "", true);
		bManager.setMidOfBounds(
				new int[] {
						RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png")
								.getWidth(),
						RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png")
								.getHeight() },
				new int[] {
						RuntimeData.getInstance().getGame().getConfig().getResolution()[0] / 2 - RuntimeData.getInstance()
								.getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getWidth() / 2,
						RuntimeData.getInstance().getGame().getConfig().getResolution()[1] / 2 - RuntimeData.getInstance()
								.getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getHeight() / 2 });
		isDisposed = false;
	}

	@Override
	public void reload() {
		bManager.reload();
	}
}
