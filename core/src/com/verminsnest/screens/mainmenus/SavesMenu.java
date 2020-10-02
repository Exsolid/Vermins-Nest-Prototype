package com.verminsnest.screens.mainmenus;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.verminsnest.core.VerminsNest;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.misc.gui.ButtonManager;
import com.verminsnest.screens.VNScreen;

public class SavesMenu extends VNScreen {
	
	private int[] scrollPos;
	private ButtonManager saveManager;
	
	@Override
	public void show() {
		while(saveManager.getIndex() != 0){
			saveManager.prev();
		}
	}

	@Override
	public void render(float delta) {
		RuntimeData.getInstance().getGame().setPro();
		RuntimeData.getInstance().getGame().getBatch().begin();
		RuntimeData.getInstance().getGame().getBatch().draw(RuntimeData.getInstance().getTexture("textures/general/Background.png"),
				Gdx.graphics.getWidth() / 2
						- RuntimeData.getInstance().getTexture("textures/general/Background.png").getWidth() / 2,
				Gdx.graphics.getHeight() / 2
						- RuntimeData.getInstance().getTexture("textures/general/Background.png").getHeight() / 2);
		RuntimeData.getInstance().getGame().getBatch().draw(RuntimeData.getInstance().getTexture("textures/menus/scrolls/VerticalScroll_Big.png"),scrollPos[0], scrollPos[1]);
		saveManager.draw(RuntimeData.getInstance().getGame().getBatch());
		RuntimeData.getInstance().getGame().getBatch().end();
		this.mangageControls();
	}

	private void mangageControls() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
			saveManager.next();
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
			saveManager.prev();
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			switch(saveManager.getIndex()){
			case 0:
				RuntimeData.getInstance().setSaveFile("SaveOne");
				break;
			case 1:
				RuntimeData.getInstance().setSaveFile("SaveTwo");
				break;
			case 2:
				RuntimeData.getInstance().setSaveFile("SaveThree");
				break;
			}
			if(saveManager.getIndex() != 3)RuntimeData.getInstance().getGame().showScreen(VerminsNest.STARTMENU);
			else RuntimeData.getInstance().getGame().showScreen(VerminsNest.MAINMENU);
			//TODO next screen
		}
	}

	@Override
	public void resize(int width, int height) {
		scrollPos = new int[]{RuntimeData.getInstance().getGame().getConfig().getResolution()[0] / 2 - RuntimeData.getInstance()
				.getTexture("textures/menus/scrolls/VerticalScroll_Big.png").getWidth() / 2,
		RuntimeData.getInstance().getGame().getConfig().getResolution()[1] / 2 - RuntimeData.getInstance()
				.getTexture("textures/menus/scrolls/VerticalScroll_Big.png").getHeight() / 2};
		saveManager.setMidOfBounds(new int[]{RuntimeData.getInstance().getTexture("textures/menus/scrolls/VerticalScroll_Big.png").getWidth(),
				RuntimeData.getInstance().getTexture("textures/menus/scrolls/VerticalScroll_Big.png").getHeight()},
		scrollPos);
	}

	@Override
	public void dispose() {
		saveManager.dispose();
		isDisposed = true;
	}

	@Override
	public void init() {
		scrollPos = new int[]{RuntimeData.getInstance().getGame().getConfig().getResolution()[0] / 2 - RuntimeData.getInstance()
				.getTexture("textures/menus/scrolls/VerticalScroll_Big.png").getWidth() / 2,
		RuntimeData.getInstance().getGame().getConfig().getResolution()[1] / 2 - RuntimeData.getInstance()
				.getTexture("textures/menus/scrolls/VerticalScroll_Big.png").getHeight() / 2};
		
		ArrayList<String> saveFileButton1 = new ArrayList<String>();
		saveFileButton1.add("SavesMenu_Save_1");
		ArrayList<String> saveFileButton2 = new ArrayList<String>();
		saveFileButton2.add("SavesMenu_Save_2");
		ArrayList<String> saveFileButton3 = new ArrayList<String>();
		saveFileButton3.add("SavesMenu_Save_3");
		ArrayList<String> back = new ArrayList<String>();
		back.add("SettingsMenu_Back");

		ArrayList<ArrayList<String>> buttonList = new ArrayList<>();
		buttonList.add(saveFileButton1);
		buttonList.add(saveFileButton2);
		buttonList.add(saveFileButton3);
		buttonList.add(back);
		saveManager = new ButtonManager(buttonList, 100, true, "", "", true);
		saveManager.setMidOfBounds(new int[]{RuntimeData.getInstance().getTexture("textures/menus/scrolls/VerticalScroll_Big.png").getWidth(),
											RuntimeData.getInstance().getTexture("textures/menus/scrolls/VerticalScroll_Big.png").getHeight()},
									scrollPos);
		isDisposed = false;
	}

	@Override
	public void reload() {
		saveManager.reload();
	}

}
