package com.verminsnest.screens;

import java.awt.Point;
import java.net.MalformedURLException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.verminsnest.core.VerminsNest;
import com.verminsnest.core.singletons.RuntimeData;
import com.verminsnest.exceptions.OutOfBounds;
import com.verminsnest.misc.gui.Button;
import com.verminsnest.misc.gui.ButtonManager;

public class SettingsMenu implements Screen {

	// Misc
	private VerminsNest game;
	private boolean running;

	// Textures
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

		// Buttons
		blockTime = 0;
		blockStartTime = System.currentTimeMillis();
		movementBlocked = true;
		menuIndex = -1;

		// Positions
		menuScrollPos = new Point(
				Gdx.graphics.getWidth() / 2 - (RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getWidth() + RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getWidth()) / 2,
				Gdx.graphics.getHeight() / 2 - RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getHeight() / 2);
		settingsScrollPos = new Point(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 - RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getHeight() / 2);

		// Main settings menu
		ArrayList<Button> buttonList = new ArrayList<Button>();
		buttonList.add(new Button(game.getConfig().getMessage("SettingsMenu_Graphics")));
		buttonList.add(new Button(game.getConfig().getMessage("SettingsMenu_Sound")));
		buttonList.add(new Button(game.getConfig().getMessage("SettingsMenu_Controls")));
		buttonList.add(new Button(game.getConfig().getMessage("SettingsMenu_Back")));
		settingsMenuManager = new ButtonManager(buttonList);
		settingsMenuManager.setSize(100);
		try {
			settingsMenuManager.calcMidofBounds(RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getWidth(), RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getHeight(), menuScrollPos);
		} catch (OutOfBounds e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(float delta) {
		if (running) {
			game.setPro();
			game.getBatch().begin();
			game.getBatch().draw(RuntimeData.getInstance().getAsset("textures/general/Background.png"), game.getConfig().getResolution()[0] / 2 - RuntimeData.getInstance().getAsset("textures/general/Background.png").getWidth() / 2,
					game.getConfig().getResolution()[1] / 2 - RuntimeData.getInstance().getAsset("textures/general/Background.png").getHeight() / 2);
			game.getBatch().draw(RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png"), menuScrollPos.x, menuScrollPos.y);
			game.getBatch().draw(RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png"), settingsScrollPos.x, settingsScrollPos.y);
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
				Gdx.graphics.getWidth() / 2 - (RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getWidth() + RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getWidth()) / 2,
				Gdx.graphics.getHeight() / 2 - RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getHeight() / 2);
		settingsScrollPos = new Point(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 - RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getHeight() / 2);
		try {
			settingsMenuManager.calcMidofBounds(RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getWidth(), RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Small.png").getHeight(), menuScrollPos);
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
		settingsMenuManager.dispose();
		if (currentMenuManager != null)currentMenuManager.dispose();
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
						if(currentMenuManager.getCurrent() != currentMenuManager.getButtons().get(currentMenuManager.getButtons().size()-1)){
							String oldOption = currentMenuManager.getCurrent().getOption();
							currentMenuManager.getCurrent().nextOption();
							currentMenuManager.reCalcOptions(currentMenuManager.getCurrent(), oldOption,
									currentMenuManager.getCurrent().getOption());
							if(currentMenuManager.getCurrent().getText().equals(game.getConfig().getMessage("GraphicsMenu_Resolution"))&&currentMenuManager.getButtons().get(1).getOption().equals("Fullscreen")){
								oldOption = currentMenuManager.getButtons().get(1).getOption();
								currentMenuManager.getButtons().get(1).nextOption();
								currentMenuManager.reCalcOptions(currentMenuManager.getButtons().get(1), oldOption,
										currentMenuManager.getButtons().get(1).getOption());
							}
							movementBlocked = true;
							blockStartTime = System.currentTimeMillis();
						}
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

		// A Pressed
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			switch (menuIndex) {
			case -1:
				break;
			default:
				if (!movementBlocked) {
					switch (settingsMenuManager.getIndex()) {
					case GRAPHICS:
						if(currentMenuManager.getCurrent() != currentMenuManager.getButtons().get(currentMenuManager.getButtons().size()-1)){
							String oldOption = currentMenuManager.getCurrent().getOption();
							currentMenuManager.getCurrent().prevOption();
							currentMenuManager.reCalcOptions(currentMenuManager.getCurrent(), oldOption,
									currentMenuManager.getCurrent().getOption());
							if(currentMenuManager.getCurrent().getText().equals(game.getConfig().getMessage("GraphicsMenu_Resolution"))&&currentMenuManager.getButtons().get(1).getOption().equals("Fullscreen")){
								currentMenuManager.getButtons().get(1).nextOption();
							}
							movementBlocked = true;
							blockStartTime = System.currentTimeMillis();
						}
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
						tempButton.addOption("852x480");
						while (!tempButton.getOption().equals(
								game.getConfig().getResolution()[0] + "x" + (game.getConfig().getResolution()[1]+25))) {
							tempButton.nextOption();
						}
						buttonList.add(tempButton);
						tempButton = new Button(game.getConfig().getMessage("GraphicsMenu_Mode"));
						tempButton.addOption(game.getConfig().getMessage("GraphicsMenu_Mode_Fullscreen"));
						tempButton.addOption(game.getConfig().getMessage("GraphicsMenu_Mode_Window"));
						if(!game.getConfig().isFullscreen()) tempButton.nextOption();
						buttonList.add(tempButton);
						tempButton = new Button(game.getConfig().getMessage("GraphicsMenu_Language"));
						tempButton.addOption(game.getConfig().getMessage("GraphicsMenu_Language_English"));
						tempButton.addOption(game.getConfig().getMessage("GraphicsMenu_Language_German"));
						if(!game.getConfig().getLanguage().equals("en"))tempButton.nextOption();
						buttonList.add(tempButton);
						tempButton = new Button(game.getConfig().getMessage("SettingsMenu_Back"));
						buttonList.add(tempButton);

						currentMenuManager = new ButtonManager(buttonList);
						currentMenuManager.setSize(100);
						currentMenuManager.calcMidofBounds(RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getWidth(), RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getHeight(),
								settingsScrollPos);
						menuIndex = 0;
						settingsMenuManager.deactivate();
						break;
					case SOUND:
						buttonList = new ArrayList<Button>();
						buttonList.add(new Button(game.getConfig().getMessage("SoundMenu_Music")));
						buttonList.add(new Button(game.getConfig().getMessage("SoundMenu_Effects")));
						buttonList.add(new Button(game.getConfig().getMessage("SettingsMenu_Back")));
						currentMenuManager = new ButtonManager(buttonList);
						currentMenuManager.setSize(100);
						currentMenuManager.calcMidofBounds(RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getWidth(), RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getHeight(),
								settingsScrollPos);
						menuIndex = 1;
						settingsMenuManager.deactivate();
						break;
					case CONTROLS:
						break;
					case BACK:
						game.screenMainMenu(this);
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
						Boolean resize = false;
						int[] newRes = new int[2];
						String currentLang = "";
						Boolean reload = false;
						
						String resolution = game.getConfig().getResolution()[0] + "x"
								+ game.getConfig().getResolution()[1];
						if (!resolution.equals(currentMenuManager.getButtons().get(0).getOption()) && !game.getConfig().isFullscreen()) {
							resize = true;
							if (currentMenuManager.getButtons().get(0).getOption().equals("1920x1080")) {
								newRes[0] = 1920;
								newRes[1] = 1055;
								game.getConfig().setResolution(newRes);
							}
							if (currentMenuManager.getButtons().get(0).getOption().equals("1280x720")) {
								newRes[0] = 1280;
								newRes[1] = 695;
								game.getConfig().setResolution(newRes);
							}
							if (currentMenuManager.getButtons().get(0).getOption().equals("852x480")) {
								newRes[0] = 852;
								newRes[1] = 455;
								game.getConfig().setResolution(newRes);
							}
						}
						if(currentMenuManager.getButtons().get(1).getOption().equals(game.getConfig().getMessage("GraphicsMenu_Mode_Fullscreen"))&& !Gdx.graphics.isFullscreen()){
							resize = false;
							newRes[0] = 1920;
							newRes[1] = 1055;
							game.getConfig().setResolution(newRes);
							game.getConfig().setFullscreen(true);
							this.resize(0, 0);
						}else if(currentMenuManager.getButtons().get(1).getOption().equals(game.getConfig().getMessage("GraphicsMenu_Mode_Window"))&& Gdx.graphics.isFullscreen()){
							resize = true;
							if (currentMenuManager.getButtons().get(0).getOption().equals("1920x1080")) {
								newRes[0] = 1920;
								newRes[1] = 1055;
								game.getConfig().setResolution(newRes);
							}
							if (currentMenuManager.getButtons().get(0).getOption().equals("1280x720")) {
								newRes[0] = 1280;
								newRes[1] = 695;
								game.getConfig().setResolution(newRes);
							}
							if (currentMenuManager.getButtons().get(0).getOption().equals("852x480")) {
								newRes[0] = 852;
								newRes[1] = 455;
								game.getConfig().setResolution(newRes);
							}
							
						}
						
						if (resize) {
							game.getConfig().setFullscreen(false);
							this.resize(0, 0);
						}
						if(currentMenuManager.getButtons().get(2).getOption().equals(game.getConfig().getMessage("GraphicsMenu_Language_German"))) currentLang = "de";
						else if(currentMenuManager.getButtons().get(2).getOption().equals(game.getConfig().getMessage("GraphicsMenu_Language_English"))) currentLang = "en";
						if(!currentLang.equals(currentMenuManager.getButtons().get(2).getOption())){
							try {
								game.getConfig().setLanguage(currentLang);
								reload = true;
							} catch (MalformedURLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if(reload){
							game.screenSettings();
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
