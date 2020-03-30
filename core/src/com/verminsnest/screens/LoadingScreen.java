package com.verminsnest.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.verminsnest.gamedev.VerminsNest;
import com.verminsnest.misc.FontText;
import com.verminsnest.singletons.LoadingModules;

public class LoadingScreen implements Screen{
	
	//Game
	private VerminsNest game;
	private int nextScreenID;
	
	//Textures
	private Texture	backgroundImg;
	private Texture scrollImg;
	//Text
	private FontText loadingText;
	
	//IDs
	public final static int GAMEMANAGER = 0;
	
	public LoadingScreen(VerminsNest game, int nextScreenID){
		this.game = game;
		this.nextScreenID = nextScreenID;
	}
	
	@Override
	public void show() {
		backgroundImg = new Texture("textures/menus/Background.png");
		scrollImg = new Texture("textures/menus/SettingsScroll.png");
		
		loadingText = new FontText("Loading..",75);
		loadingText.setMidOfBounds(new int[]{(int) game.getCamera().position.x-scrollImg.getWidth()/2,(int) game.getCamera().position.y-scrollImg.getHeight()/2}, new int[]{scrollImg.getWidth(), scrollImg.getHeight()});
	}

	@Override
	public void render(float delta) {
		if(!LoadingModules.getInstance().getModules().isEmpty() && !LoadingModules.getInstance().getModules().get(0).isRunning()){
			LoadingModules.getInstance().getModules().get(0).load();
			loadingText.setText(LoadingModules.getInstance().getModules().get(0).getDescription());
			loadingText.setMidOfBounds(new int[]{(int) game.getCamera().position.x-scrollImg.getWidth()/2,(int) game.getCamera().position.y-scrollImg.getHeight()/2}, new int[]{scrollImg.getWidth(), scrollImg.getHeight()});
		}else if(LoadingModules.getInstance().getModules().isEmpty()){
			this.dispose();
			switch(nextScreenID){
			case GAMEMANAGER:
				game.screenGameManager();
				break;
			default:
				game.screenMainMenu();
				//TODO Log this
			}
		}
		
		game.getBatch().begin();
		game.getBatch().draw(backgroundImg,0,0);
		game.getBatch().draw(scrollImg, game.getCamera().position.x-scrollImg.getWidth()/2, game.getCamera().position.y-scrollImg.getHeight()/2);
		loadingText.draw(game.getBatch());
		game.getBatch().end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		loadingText.dispose();
		scrollImg.dispose();
		backgroundImg.dispose();
	}
}
