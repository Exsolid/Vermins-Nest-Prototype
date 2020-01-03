package com.verminsnest.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.verminsnest.gamedev.VerminsNest;

public class SettingsMenu implements Screen {

	//Misc
	private VerminsNest game;
	private boolean running;
	//Textures
	private Texture backgroundImg;
	
	public SettingsMenu(VerminsNest game){
		this.game = game;
	}
	
	@Override
	public void show() {
		//Misc
		running = true;		
		//Textures
		backgroundImg = new Texture("mainMenu/Background.png");
	}

	@Override
	public void render(float delta) {
		if(running){
			game.getBatch().begin();
			game.getBatch().draw(backgroundImg, 0, 0);
			game.getBatch().end();
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
