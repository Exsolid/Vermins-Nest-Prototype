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
import com.verminsnest.world.generation.spawning.UtilSpawner;

public class StartGameMenu extends VNScreen{
	private int[] scrollPos;
	private ButtonManager optionsManager;
	
	@Override
	public void show() {
		while(optionsManager.getIndex() != 0){
			optionsManager.prev();
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
		optionsManager.draw(RuntimeData.getInstance().getGame().getBatch());
		RuntimeData.getInstance().getGame().getBatch().end();
		this.mangageControls();
	}

	private void mangageControls() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
			optionsManager.next();
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
			optionsManager.prev();
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
			if(optionsManager.getCurrent().hasOptions())optionsManager.getCurrent().nextOption();
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
			if(optionsManager.getCurrent().hasOptions())optionsManager.getCurrent().prevOption();
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			switch(optionsManager.getIndex()){
			case 2:
				// World generation
				RuntimeData.getInstance().loadTextures(Indentifiers.ASSETMANAGER_GAMEPLAY);
				World gen = new World(RuntimeData.getInstance().getGame());
				String[] diffOpts = new String[]{RuntimeData.getInstance().getGame().getConfig().getMessage("StartGameMenu_Difficulty_Egg"),
						RuntimeData.getInstance().getGame().getConfig().getMessage("StartGameMenu_Difficulty_Larva"),
						RuntimeData.getInstance().getGame().getConfig().getMessage("StartGameMenu_Difficulty_Insect")};
				if(optionsManager.getButtons().get(1).getOption().getText().equals(diffOpts[0])){
					gen.setData(2, 15, 15, 10,(RuntimeData.getInstance().getTexture("textures/level-sheets/cave/Mountain-Sheet.png")));
					new EnemySpawner(2);
				}else if(optionsManager.getButtons().get(1).getOption().getText().equals(diffOpts[1])){
					gen.setData(4, 18, 18, 10,(RuntimeData.getInstance().getTexture("textures/level-sheets/cave/Mountain-Sheet.png")));
					new EnemySpawner(4);
				}else if(optionsManager.getButtons().get(1).getOption().getText().equals(diffOpts[2])){
					gen.setData(5, 21, 21, 10,(RuntimeData.getInstance().getTexture("textures/level-sheets/cave/Mountain-Sheet.png")));
					new EnemySpawner(5);
				}
				//Character
				String[] charaOpts = new String[]{RuntimeData.getInstance().getGame().getConfig().getMessage("StartGameMenu_Character_Mage")};
				if(optionsManager.getButtons().get(0).getOption().getText().equals(charaOpts[0])){
					RuntimeData.getInstance().getEntityManager().setToInitCharacter(Indentifiers.ASSETMANAGER_MAGE);
				}
				//Stuff
				new UtilSpawner();
				RuntimeData.getInstance().getGame().showScreen(VerminsNest.LOADGAME);
				break;
			case 3:
				RuntimeData.getInstance().getGame().showScreen(VerminsNest.SAVESMENU);
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		scrollPos = new int[]{RuntimeData.getInstance().getGame().getConfig().getResolution()[0] / 2 - RuntimeData.getInstance()
				.getTexture("textures/menus/scrolls/VerticalScroll_Big.png").getWidth() / 2,
		RuntimeData.getInstance().getGame().getConfig().getResolution()[1] / 2 - RuntimeData.getInstance()
				.getTexture("textures/menus/scrolls/VerticalScroll_Big.png").getHeight() / 2};
		
		optionsManager.setMidOfBounds(new int[]{RuntimeData.getInstance().getTexture("textures/menus/scrolls/VerticalScroll_Big.png").getWidth(),
				RuntimeData.getInstance().getTexture("textures/menus/scrolls/VerticalScroll_Big.png").getHeight()},
		scrollPos);
	}

	@Override
	public void dispose() {
		optionsManager.dispose();
		isDisposed = true;
	}

	@Override
	public void init() {
		scrollPos = new int[]{RuntimeData.getInstance().getGame().getConfig().getResolution()[0] / 2 - RuntimeData.getInstance()
				.getTexture("textures/menus/scrolls/VerticalScroll_Big.png").getWidth() / 2,
		RuntimeData.getInstance().getGame().getConfig().getResolution()[1] / 2 - RuntimeData.getInstance()
				.getTexture("textures/menus/scrolls/VerticalScroll_Big.png").getHeight() / 2};
		
		ArrayList<String> startButton = new ArrayList<String>();
		startButton.add("StartGameMenu_IntoTheDungeon");
		ArrayList<String> charaButton = new ArrayList<String>();
		charaButton.add("StartGameMenu_Character");
		charaButton.add("StartGameMenu_Character_Mage");
		ArrayList<String> diffiButton = new ArrayList<String>();
		diffiButton.add("StartGameMenu_Difficulty");
		diffiButton.add("StartGameMenu_Difficulty_Egg");
		diffiButton.add("StartGameMenu_Difficulty_Larva");
		diffiButton.add("StartGameMenu_Difficulty_Insect");
		ArrayList<String> back = new ArrayList<String>();
		back.add("SettingsMenu_Back");
		
		ArrayList<ArrayList<String>> buttonList = new ArrayList<>();
		buttonList.add(charaButton);
		buttonList.add(diffiButton);
		buttonList.add(startButton);
		buttonList.add(back);
		optionsManager = new ButtonManager(buttonList, 90, true, "", "", true);
		optionsManager.setMidOfBounds(new int[]{RuntimeData.getInstance().getTexture("textures/menus/scrolls/VerticalScroll_Big.png").getWidth(),
											RuntimeData.getInstance().getTexture("textures/menus/scrolls/VerticalScroll_Big.png").getHeight()},
									scrollPos);
		isDisposed = false;
	}

	@Override
	public void reload() {
		optionsManager.reload();
	}
}
