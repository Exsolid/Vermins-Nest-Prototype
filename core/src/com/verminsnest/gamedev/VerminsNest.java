package com.verminsnest.gamedev;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.verminsnest.config.Configurator;
import com.verminsnest.screens.LoadingScreen;
import com.verminsnest.screens.MainMenu;
import com.verminsnest.screens.SettingsMenu;
import com.verminsnest.screens.gameplay.GameManager;

public class VerminsNest extends Game {
	

	private MainMenu mainMenu;
	private GameManager gameMan;
	private SettingsMenu settingsMenu;
	private LoadingScreen loadingScreen;
	
	private SpriteBatch batch;
	private Runtime r;
	private Configurator config;
	private OrthographicCamera camera;
	private FillViewport vport;
	
	@Override
	public void create () {
		setConfig(new Configurator());
		this.setBatch(new SpriteBatch());		
		mainMenu = new MainMenu(this);
		camera = new OrthographicCamera();
		camera.position.set(1920/2, 1055/2, 0);
		camera.update();
		vport = new FillViewport(1920, 1055, camera);
		vport.apply();
		batch.setProjectionMatrix(camera.combined);
		this.setScreen(mainMenu);
		r = Runtime.getRuntime();
	}
	
	public void resize(int width, int height){
		if(!config.isFullscreen()){
			Gdx.graphics.setWindowedMode(config.getResolution()[0], config.getResolution()[1]);
		}else{
			Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		}
		vport.update(config.getResolution()[0], config.getResolution()[1], false);
		camera.position.set(config.getResolution()[0]/2, config.getResolution()[1]/2, 0);
	}
	
	@Override
	public void render () {
		super.render();
	}
	
	public void setPro(){
		vport.apply();
		batch.setProjectionMatrix(camera.combined);
	}
	@Override
	public void dispose () {
		super.dispose();
		Gdx.app.exit();
	}
	
	public OrthographicCamera getCamera(){
		return camera;
	}
	public SpriteBatch getBatch() {
		return batch;
	}

	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}
	
	public void screenSettings(){
		if(settingsMenu == null) settingsMenu = new SettingsMenu(this);
		this.setScreen(settingsMenu);
		r.gc();
	}
	
	public void screenMainMenu(Screen toDispose){
		if(mainMenu == null)mainMenu = new MainMenu(this);
		this.setScreen(mainMenu);
		toDispose.dispose();
		r.gc();
	}
	
	public void screenGameManager(Screen toDispose){
		if(gameMan == null)gameMan = new GameManager(this);
		this.setScreen(gameMan);
		toDispose.dispose();
		r.gc();
	}
	
	public void screenLoading(int nextScreenID, Screen toDispose){
		if(loadingScreen == null) loadingScreen = new LoadingScreen(this,nextScreenID);
		this.setScreen(loadingScreen);
		toDispose.dispose();
		r.gc();
	}
	
	public Configurator getConfig() {
		return config;
	}

	public void setConfig(Configurator config) {
		this.config = config;
	}
}
