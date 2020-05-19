package com.verminsnest.screens.mainmenus;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.verminsnest.core.VerminsNest;
import com.verminsnest.core.singletons.RuntimeData;
import com.verminsnest.misc.gui.ButtonManager;
import com.verminsnest.misc.gui.FontText;
import com.verminsnest.screens.VNScreen;

public class CreditsMenu extends VNScreen {
	// Textures
	private int[] scrollPos;
	// Text
	private FontText title;
	private FontText credits;
	
	private ButtonManager backButton;

	public CreditsMenu(VerminsNest game) {
		super(game);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		game.getBatch().begin();
		game.getBatch().draw(RuntimeData.getInstance().getAsset("textures/general/Background.png"),
				Gdx.graphics.getWidth() / 2
				- RuntimeData.getInstance().getAsset("textures/general/Background.png").getWidth() / 2,
		Gdx.graphics.getHeight() / 2
				- RuntimeData.getInstance().getAsset("textures/general/Background.png").getHeight() / 2);
		game.getBatch().draw(RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png"),
				scrollPos[0], scrollPos[1]);
		backButton.draw(game.getBatch());
		title.draw(game.getBatch());
		credits.draw(game.getBatch());
		game.getBatch().end();
		manageControls();
	}

	public void manageControls() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			game.showScreen(VerminsNest.MAINMENU);
		}
	}

	@Override
	public void resize(int width, int height) {
		backButton.setMidOfBounds(new int[] {
				RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getWidth(),
				RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getHeight() / 3 },
				new int[] { scrollPos[0], scrollPos[1] });
		title.setMidOfBounds(new int[] { scrollPos[0], (int) (scrollPos[1] * 3.5) }, new int[] {
				RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getWidth(),
				RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getHeight() });
		credits.setMidOfBounds(new int[] { scrollPos[0], (int) (scrollPos[1] * 1.5) }, new int[] {
				RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getWidth(),
				RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getHeight() });
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
		credits.dispose();
		title.dispose();
		backButton.dispose();
		isDisposed = true;
	}

	@Override
	public void init() {
		// Misc
		scrollPos = new int[] { game.getConfig().getResolution()[0] / 2
				- RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getWidth() / 2,
				game.getConfig().getResolution()[1] / 2 - RuntimeData.getInstance()
						.getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getHeight() / 2 };

		ArrayList<ArrayList<String>> buttonList = new ArrayList<>();
		ArrayList<String> back = new ArrayList<>();
		back.add("SettingsMenu_Back");
		buttonList.add(back);
		backButton = new ButtonManager(buttonList, 100, true, "", "", true);
		backButton.setMidOfBounds(new int[] {
				RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getWidth(),
				RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getHeight() / 3 },
				new int[] { scrollPos[0], scrollPos[1] });

		// Text
		title = new FontText("MainMenu_Credits", 100, true);
		credits = new FontText(game.getConfig().getMessage("CreditsMenu_Credits"), 35, false);
		title.setMidOfBounds(new int[] { scrollPos[0], (int) (scrollPos[1] * 3.5) }, new int[] {
				RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getWidth(),
				RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getHeight() });
		credits.setMidOfBounds(new int[] { scrollPos[0], (int) (scrollPos[1] * 1.5) }, new int[] {
				RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getWidth(),
				RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getHeight() });
		isDisposed = false;
	}

	@Override
	public void reload() {
		title.reload();
		credits.reload();
		backButton.reload();
	}

}
