package com.verminsnest.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.verminsnest.config.Configurator;
import com.verminsnest.core.engine.VNAssetManager;
import com.verminsnest.core.singletons.RuntimeData;
import com.verminsnest.screens.LoadingScreen;
import com.verminsnest.screens.gameplay.GameManager;
import com.verminsnest.screens.mainmenus.CreditsMenu;
import com.verminsnest.screens.mainmenus.MainMenu;
import com.verminsnest.screens.mainmenus.SettingsMenu;

public class VerminsNest extends Game {
	

	private MainMenu mainMenu;
	private CreditsMenu creditsMenu;
	private GameManager gameMan;
	private SettingsMenu settingsMenu;
	private LoadingScreen loadingScreen;
	
	private boolean reload;
	
	private SpriteBatch batch;
	private Runtime r;
	private Configurator config;
	private OrthographicCamera camera;
	private FillViewport vport;
	private boolean onClose;
	
	public static final int MAINMENU = 0;
	public static final int CREDITSMENU = 1;
	public static final int SETTINGSMENU = 2;
	public static final int GAMEPLAY = 3;
	public static final int LOADGAME = 5;
	
	@Override
	public void create () {
		onClose = false;
		setConfig(new Configurator());
		this.setBatch(new SpriteBatch());	
		
		reload = false;
		camera = new OrthographicCamera();
		camera.position.set(1920/2, 1055/2, 0);
		camera.update();
		vport = new FillViewport(1920, 1080, camera);
		vport.apply();
		batch.setProjectionMatrix(camera.combined);
		
		r = Runtime.getRuntime();
		
		mainMenu = new MainMenu(this);
		settingsMenu = new SettingsMenu(this);
		creditsMenu = new CreditsMenu(this);
		gameMan = new GameManager(this);
		
		RuntimeData.getInstance().init(this);
		RuntimeData.getInstance().loadTextures(VNAssetManager.MENU);
		this.showScreen(MAINMENU);
	}
	
	public void resize(int width, int height){
		if(!onClose){
			width = config.getResolution()[0];
			height = config.getResolution()[1];
			if(!config.isFullscreen()){
				Gdx.graphics.setWindowedMode(width,height);
			}else{
				height += 25;
				Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
			}
			vport.update(width,height, false);
			if(!(gameMan != null && this.getScreen() == gameMan)){
				camera.position.set(width/2, height/2, 0);
			}
			vport.apply();
			if(this.getScreen() == mainMenu || this.getScreen() == settingsMenu || this.getScreen() == creditsMenu){
				mainMenu.resize(height, height);
				settingsMenu.resize(height, height);
				creditsMenu.resize(height, height);
			}	
		}
	}
	
	public void reload(){
		if(this.getScreen() == mainMenu || this.getScreen() == settingsMenu || this.getScreen() == creditsMenu){
			mainMenu.reload();
			settingsMenu.reload();
			creditsMenu.reload();
		}	
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
		onClose = true;
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
	
	public void showScreen(int id){
		switch(id){
		case MAINMENU:
			if(!gameMan.isDisposed()){
				gameMan.dispose();
			}
			if(RuntimeData.getInstance().areAssetsLoaded(VNAssetManager.MENU)){
				RuntimeData.getInstance().loadTextures(VNAssetManager.MENU);
			}
			if(mainMenu.isDisposed()){
				initMenus();
			}
			this.setScreen(mainMenu);
			break;
		case SETTINGSMENU:
			if(settingsMenu.isDisposed()){
				initMenus();
			}
			if(reload){
				settingsMenu.dispose();
				settingsMenu.init();
				reload = false;
				r.gc();
			}
			this.setScreen(settingsMenu);
			break;
		case CREDITSMENU:
			if(creditsMenu.isDisposed()){
				initMenus();
			}
			this.setScreen(creditsMenu);
			break;
		case LOADGAME:
			disposeMenus();
			loadingScreen = new LoadingScreen(this, LoadingScreen.GAMEMANAGER);
			this.setScreen(loadingScreen);
			break;
		case GAMEPLAY:
			gameMan.init();
			this.setScreen(gameMan);
			
			RuntimeData.getInstance().disposeTextures(VNAssetManager.MENU);
			loadingScreen = null;
			r.gc();
			break;
		}
	}
	
	private void initMenus(){
		mainMenu.init();
		settingsMenu.init();
		creditsMenu.init();
	}
	
	private void disposeMenus(){
		mainMenu.dispose();
		settingsMenu.dispose();
		creditsMenu.dispose();
		r.gc();
	}
	
	public Configurator getConfig() {
		return config;
	}

	public void setConfig(Configurator config) {
		this.config = config;
	}
}
