package com.verminsnest.screens;

import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.verminsnest.exceptions.OutOfBounds;
import com.verminsnest.gamedev.VerminsNest;
import com.verminsnest.misc.gui.Button;
import com.verminsnest.misc.gui.ButtonManager;
import com.verminsnest.misc.gui.FontText;
import com.verminsnest.singletons.RuntimeData;

public class CreditsMenu implements Screen {

	//Misc
	private VerminsNest game;
	private boolean running;
	//Textures
	private Point scrollPos;
	//Text
	private FontText title;
	private FontText credits;
	//Button
	private boolean movementBlocked;
	private long blockTime;
	private long blockStartTime;
	private ButtonManager backButton;
	
	public CreditsMenu(VerminsNest game){
		this.game = game;
	}
	
	@Override
	public void show() {
		//Misc
		running = true;
		scrollPos = new Point(game.getConfig().getResolution()[0]/2-RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getWidth()/2,game.getConfig().getResolution()[1]/2-RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getHeight()/2);
		
		// Buttons
		blockTime = 0;
		blockStartTime = System.currentTimeMillis();
		movementBlocked = true;
		ArrayList<Button> buttonList = new ArrayList<>();
		buttonList.add(new Button(game.getConfig().getMessage("SettingsMenu_Back")));
		backButton = new ButtonManager(buttonList);
		backButton.setSize(100);
		try {
			backButton.calcMidofBounds(RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getWidth(), RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getHeight()/3, new Point(scrollPos.x,scrollPos.y));
		} catch (OutOfBounds e) {
			e.printStackTrace();
		}
		//Text
		title = new FontText(game.getConfig().getMessage("MainMenu_Credits"),100);
		credits= new FontText("-> The LIBGDX framework \n"
				+ "-> The font saratogajean \n"
				+ "(Licenes can be found in the gamefiles)", 35);
		title.setMidOfBounds(new int[]{scrollPos.x,(int) (scrollPos.y*3.5)},new int[]{RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getWidth(), RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getHeight()});
		credits.setMidOfBounds(new int[]{scrollPos.x,(int) (scrollPos.y*1.5)},new int[]{RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getWidth(), RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getHeight()});
		
	}

	@Override
	public void render(float delta) {
		if(running){
			game.getBatch().begin();
			game.getBatch().draw(RuntimeData.getInstance().getAsset("textures/general/Background.png"), 0, 0);
			game.getBatch().draw(RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png"),scrollPos.x,scrollPos.y);
			backButton.draw(game.getBatch());
			title.draw(game.getBatch());
			credits.draw(game.getBatch());
			game.getBatch().end();
			manageControls();
		}
	}

	public void manageControls(){
		blockTime = System.currentTimeMillis() - blockStartTime;
		if (blockTime > 225) {
			movementBlocked = false;
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.ENTER) && !movementBlocked){
			game.screenMainMenu(this);
			movementBlocked = true;
			blockStartTime = System.currentTimeMillis();
		}
	}
	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {
		credits.dispose();
		title.dispose();
		backButton.dispose();
	}

}
