package com.verminsnest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.verminsnest.core.VerminsNest;
import com.verminsnest.core.singletons.LoadingModules;
import com.verminsnest.core.singletons.RuntimeData;
import com.verminsnest.misc.gui.FontText;

public class LoadingScreen implements Screen{
	
	//Game
	private VerminsNest game;
	private int nextScreenID;
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
		loadingText = new FontText("...",65, false);
		loadingText.setMidOfBounds(new int[]{(int) game.getCamera().position.x-RuntimeData.getInstance().getAsset("textures/menus/scrolls/HorizontalScroll_Minimum.png").getWidth()/2,(int) game.getCamera().position.y-RuntimeData.getInstance().getAsset("textures/menus/scrolls/HorizontalScroll_Minimum.png").getHeight()/2}, new int[]{RuntimeData.getInstance().getAsset("textures/menus/scrolls/HorizontalScroll_Minimum.png").getWidth(), RuntimeData.getInstance().getAsset("textures/menus/scrolls/HorizontalScroll_Minimum.png").getHeight()});
	}

	@Override
	public void render(float delta) {
			game.getBatch().begin();
			game.getBatch().draw(RuntimeData.getInstance().getAsset("textures/general/Background.png"), Gdx.graphics.getWidth() / 2 - RuntimeData.getInstance().getAsset("textures/general/Background.png").getWidth() / 2,
					Gdx.graphics.getHeight() / 2 - RuntimeData.getInstance().getAsset("textures/general/Background.png").getHeight() / 2);
			game.getBatch().draw(RuntimeData.getInstance().getAsset("textures/menus/scrolls/HorizontalScroll_Minimum.png"), game.getCamera().position.x-RuntimeData.getInstance().getAsset("textures/menus/scrolls/HorizontalScroll_Minimum.png").getWidth()/2, game.getCamera().position.y-RuntimeData.getInstance().getAsset("textures/menus/scrolls/HorizontalScroll_Minimum.png").getHeight()/2);
			loadingText.draw(game.getBatch());
			game.getBatch().end();
			if(!LoadingModules.getInstance().getModules().isEmpty() && !LoadingModules.getInstance().getModules().get(0).isRunning()){
				LoadingModules.getInstance().getModules().get(0).load();
				loadingText.setText(LoadingModules.getInstance().getModules().get(0).getDescription());
				loadingText.setMidOfBounds(new int[]{(int) game.getCamera().position.x-RuntimeData.getInstance().getAsset("textures/menus/scrolls/HorizontalScroll_Minimum.png").getWidth()/2,(int) game.getCamera().position.y-RuntimeData.getInstance().getAsset("textures/menus/scrolls/HorizontalScroll_Minimum.png").getHeight()/2}, new int[]{RuntimeData.getInstance().getAsset("textures/menus/scrolls/HorizontalScroll_Minimum.png").getWidth(), RuntimeData.getInstance().getAsset("textures/menus/scrolls/HorizontalScroll_Minimum.png").getHeight()+60});
			}else if(LoadingModules.getInstance().getModules().isEmpty()){
				switch(nextScreenID){
				case GAMEMANAGER:
					game.showScreen(VerminsNest.GAMEPLAY);
					this.dispose();
					break;
				default:
					game.showScreen(VerminsNest.MAINMENU);
				}
			}
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
	}
}
