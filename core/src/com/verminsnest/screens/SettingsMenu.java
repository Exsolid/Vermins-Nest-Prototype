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

public class SettingsMenu implements Screen {

	// Misc
	private VerminsNest game;
	private boolean running;

	// Textures
	private Texture backgroundImg;
	private Texture menuScrollImg;
	private Texture settingsScrollImg;
	private Point menuScrollPos;
	private Point settingsScrollPos;

	// Buttons
	private long blockTime;
	private boolean movementBlocked;
	private long blockStartTime;

	// Main settings menu
	private final static int GRAPHICS = 0;
	private final static int SOUND = 1;
	private final static int CONTROLS = 2;
	private final static int BACK = 3;
	private ButtonManager settingsMenuManager;

	// Graphics settings menu
	private final static int MODE = 0;
	private final static int RESOLUTION = 1;
	private final static int LANGUAGE = 2;

	private int menuIndex;
	private ButtonManager currentMenuManager;

	public SettingsMenu(VerminsNest game) {
		this.game = game;
	}

	@Override
	public void show() {
		// Misc
		running = true;

		// Textures
		menuScrollImg = new Texture("textures/menus/MenuScroll.png");
		settingsScrollImg = new Texture("textures/menus/SettingsScroll.png");
		backgroundImg = new Texture("textures/menus/Background.png");

		// Buttons
		blockTime = 0;
		blockStartTime = System.currentTimeMillis();
		movementBlocked = true;
		menuIndex = -1;

		// Positions
		menuScrollPos = new Point(
				Gdx.graphics.getWidth() / 2 - (menuScrollImg.getWidth() + settingsScrollImg.getWidth()) / 2,
				Gdx.graphics.getHeight() / 2 - menuScrollImg.getHeight() / 2);
		settingsScrollPos = new Point(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 - menuScrollImg.getHeight() / 2);

		// Main settings menu
		ArrayList<Button> buttonList = new ArrayList<Button>();
		buttonList.add(new Button(game.getConfig().getMessage("SettingsMenu_Graphics")));
		buttonList.add(new Button(game.getConfig().getMessage("SettingsMenu_Sound")));
		buttonList.add(new Button(game.getConfig().getMessage("SettingsMenu_Controls")));
		buttonList.add(new Button(game.getConfig().getMessage("SettingsMenu_Back")));
		settingsMenuManager = new ButtonManager(buttonList, "Settings Menu");
		settingsMenuManager.setSize(150);
		try {
			settingsMenuManager.calcMidofBounds(menuScrollImg.getWidth(), menuScrollImg.getHeight(), menuScrollPos);
		} catch (OutOfBounds e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(float delta) {
		if (running) {
			game.setPro();
			game.getBatch().begin();
			game.getBatch().draw(backgroundImg, game.getConfig().getResolution()[0] / 2 - backgroundImg.getWidth() / 2,
					game.getConfig().getResolution()[1] / 2 - backgroundImg.getHeight() / 2);
			game.getBatch().draw(menuScrollImg, menuScrollPos.x, menuScrollPos.y);
			game.getBatch().draw(settingsScrollImg, settingsScrollPos.x, settingsScrollPos.y);
			settingsMenuManager.draw(game.getBatch());
			if (currentMenuManager != null) {
				currentMenuManager.draw(game.getBatch());
			}

			game.getBatch().end();

			try {
				this.mangageControls();
			} catch (OutOfBounds e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		game.resize(game.getConfig().getResolution()[0], game.getConfig().getResolution()[1]);
		menuScrollPos = new Point(
				Gdx.graphics.getWidth() / 2 - (menuScrollImg.getWidth() + settingsScrollImg.getWidth()) / 2,
				Gdx.graphics.getHeight() / 2 - menuScrollImg.getHeight() / 2);
		settingsScrollPos = new Point(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 - menuScrollImg.getHeight() / 2);
		try {
			settingsMenuManager.calcMidofBounds(menuScrollImg.getWidth(), menuScrollImg.getHeight(), menuScrollPos);
		} catch (OutOfBounds e) {
			e.printStackTrace();
		}
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
		backgroundImg.dispose();
		settingsScrollImg.dispose();
		menuScrollImg.dispose();
		settingsMenuManager.dispose();
		if (currentMenuManager != null)
			currentMenuManager.dispose();
	}

	// Change to preferences later
	private void mangageControls() throws OutOfBounds {

		blockTime = System.currentTimeMillis() - blockStartTime;
		if (blockTime > 225) {
			movementBlocked = false;
		}

		// S Pressed
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			switch (menuIndex) {
			case -1:
				if (!movementBlocked) {
					settingsMenuManager.next();
					movementBlocked = true;
					blockStartTime = System.currentTimeMillis();
				}
				break;
			default:
				if (!movementBlocked) {
					currentMenuManager.next();
					movementBlocked = true;
					blockStartTime = System.currentTimeMillis();
				}
				break;
			}
		}

		// W Pressed
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			switch (menuIndex) {
			case -1:
				if (!movementBlocked) {
					settingsMenuManager.prev();
					movementBlocked = true;
					blockStartTime = System.currentTimeMillis();
				}
				break;
			default:
				if (!movementBlocked) {
					currentMenuManager.prev();
					movementBlocked = true;
					blockStartTime = System.currentTimeMillis();
				}
				break;
			}
		}

		// D Pressed
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			switch (menuIndex) {
			case -1:
				break;
			default:
				if (!movementBlocked) {
					switch (settingsMenuManager.getIndex()) {
					case GRAPHICS:
						currentMenuManager.getCurrent().nextOption();
						currentMenuManager.reCalcOptions(currentMenuManager.getCurrent().getOptionSpecs(),
								currentMenuManager.getCurrent());
						movementBlocked = true;
						blockStartTime = System.currentTimeMillis();
						break;
					case SOUND:
						break;
					case CONTROLS:
						break;
					}
					break;
				}
			}
		}

		// D Pressed
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			switch (menuIndex) {
			case -1:
				break;
			default:
				if (!movementBlocked) {
					switch (settingsMenuManager.getIndex()) {
					case GRAPHICS:
						currentMenuManager.getCurrent().prevOption();
						currentMenuManager.reCalcOptions(currentMenuManager.getCurrent().getOptionSpecs(),
								currentMenuManager.getCurrent());
						movementBlocked = true;
						blockStartTime = System.currentTimeMillis();
						break;
					case SOUND:
						break;
					case CONTROLS:
						break;
					}
					break;
				}
			}
		}

		// Enter pressed
		ArrayList<Button> buttonList = new ArrayList<Button>();
		if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
			if (!movementBlocked) {
				switch (menuIndex) {
				case -1:
					switch (settingsMenuManager.getIndex()) {
					case GRAPHICS:
						buttonList = new ArrayList<Button>();
						Button tempButton = new Button(game.getConfig().getMessage("GraphicsMenu_Resolution"));
						tempButton.addOption("1920x1080");
						tempButton.addOption("1280x720");
						tempButton.addOption("720x480");
						while (!tempButton.getOption().equals(
								game.getConfig().getResolution()[0] + "x" + game.getConfig().getResolution()[1])) {
							tempButton.nextOption();
						}
						buttonList.add(tempButton);
						tempButton = new Button(game.getConfig().getMessage("GraphicsMenu_Mode"));
						tempButton.addOption("Borderless Window");
						tempButton.addOption("Fullscreen");
						tempButton.addOption("Window");
						buttonList.add(tempButton);
						tempButton = new Button(game.getConfig().getMessage("GraphicsMenu_Language"));
						tempButton.addOption("English");
						tempButton.addOption("German");
						buttonList.add(tempButton);
						tempButton = new Button(game.getConfig().getMessage("SettingsMenu_Back"));
						buttonList.add(tempButton);

						currentMenuManager = new ButtonManager(buttonList, "Graphics Menu");
						currentMenuManager.setSize(150);
						currentMenuManager.calcMidofBounds(settingsScrollImg.getWidth(), settingsScrollImg.getHeight(),
								settingsScrollPos);
						menuIndex = 0;
						settingsMenuManager.deactivate();
						break;
					case SOUND:
						buttonList = new ArrayList<Button>();
						buttonList.add(new Button(game.getConfig().getMessage("SoundMenu_Music")));
						buttonList.add(new Button(game.getConfig().getMessage("SoundMenu_Effects")));
						buttonList.add(new Button(game.getConfig().getMessage("SettingsMenu_Back")));
						currentMenuManager = new ButtonManager(buttonList, "Sound Menu");
						currentMenuManager.setSize(150);
						currentMenuManager.calcMidofBounds(settingsScrollImg.getWidth(), settingsScrollImg.getHeight(),
								settingsScrollPos);
						menuIndex = 1;
						settingsMenuManager.deactivate();
						break;
					case CONTROLS:
						break;
					case BACK:
						this.dispose();
						game.screenMainMenu();
						break;
					}
					break;
				case GRAPHICS:
					switch (currentMenuManager.getIndex()) {
					case RESOLUTION:
						break;
					case MODE:
						break;
					case LANGUAGE:
						break;
					case BACK:
						Boolean reload = false;
						String resolution = game.getConfig().getResolution()[0] + "x"
								+ game.getConfig().getResolution()[1];
						if (!resolution.equals(currentMenuManager.getButtons().get(0).getOption())) {
							reload = true;
							int[] newRes = new int[2];
							;
							if (currentMenuManager.getButtons().get(0).getOption().equals("1920x1080")) {
								newRes[0] = 1920;
								newRes[1] = 1080;
								game.getConfig().setResolution(newRes);
							}
							if (currentMenuManager.getButtons().get(0).getOption().equals("1280x720")) {
								newRes[0] = 1280;
								newRes[1] = 720;
								game.getConfig().setResolution(newRes);
							}
							if (currentMenuManager.getButtons().get(0).getOption().equals("720x480")) {
								newRes[0] = 720;
								newRes[1] = 480;
								game.getConfig().setResolution(newRes);

							}
						}
						if (reload) {
							this.resize(0, 0);
						}
						menuIndex = -1;
						currentMenuManager.dispose();
						currentMenuManager = null;
						settingsMenuManager.activate();
						break;
					}
					break;
				case SOUND:
					switch (currentMenuManager.getIndex()) {
					case 0:
						break;
					case 1:
						break;
					case 2:
						menuIndex = -1;
						currentMenuManager.dispose();
						currentMenuManager = null;
						settingsMenuManager.activate();
						break;
					}
					break;
				case CONTROLS:
					break;
				}
				movementBlocked = true;
				blockStartTime = System.currentTimeMillis();
			}
		}
	}
}
