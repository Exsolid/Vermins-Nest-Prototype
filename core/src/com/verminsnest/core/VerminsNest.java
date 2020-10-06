package com.verminsnest.core;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Window;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.verminsnest.config.Configurator;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.core.management.ids.Indentifiers;
import com.verminsnest.screens.LoadingScreen;
import com.verminsnest.screens.gameplay.GameManager;
import com.verminsnest.screens.mainmenus.CreditsMenu;
import com.verminsnest.screens.mainmenus.MainMenu;
import com.verminsnest.screens.mainmenus.SavesMenu;
import com.verminsnest.screens.mainmenus.SettingsMenu;
import com.verminsnest.screens.mainmenus.StartGameMenu;

public class VerminsNest extends Game {
	

	private MainMenu mainMenu;
	private CreditsMenu creditsMenu;
	private GameManager gameMan;
	private SettingsMenu settingsMenu;
	private LoadingScreen loadingScreen;
	private SavesMenu savesMenu;
	private StartGameMenu startMenu;
	
	private SpriteBatch batch;
	private Configurator config;
	private OrthographicCamera camera;
	private FillViewport vport;
	private boolean onClose;
	
	public static final int MAINMENU = 0;
	public static final int CREDITSMENU = 1;
	public static final int SETTINGSMENU = 2;
	public static final int GAMEPLAY = 3;
	public static final int LOADGAME = 4;
	public static final int STARTMENU = 5;
	public static final int SAVESMENU = 6;
	
	@Override
	public void create () {
		onClose = false;
		setConfig(new Configurator());
		this.setBatch(new SpriteBatch());	
		
		camera = new OrthographicCamera();
		camera.position.set(1920/2, 1055/2, 0);
		camera.update();
		vport = new FillViewport(1920, 1080, camera);
		vport.apply();
		batch.setProjectionMatrix(camera.combined);
		
		mainMenu = new MainMenu();
		settingsMenu = new SettingsMenu();
		creditsMenu = new CreditsMenu();
		gameMan = new GameManager();
		startMenu = new StartGameMenu();
		savesMenu = new SavesMenu();
		
		RuntimeData.getInstance().init(this);
		initMenus();
		Pixmap pixmap = new Pixmap(Gdx.files.internal("textures/misc/Cursor.png"));
		int xHotspot = pixmap.getWidth() / 2;
		int yHotspot = pixmap.getHeight() / 2;
		Cursor cursor = Gdx.graphics.newCursor(pixmap, xHotspot, yHotspot);
		Gdx.graphics.setCursor(cursor);
		pixmap.dispose();
		this.showScreen(MAINMENU);
	}
	
	public void resize(int width, int height){
		if(!onClose){
			width = config.getResolution()[0];
			height = config.getResolution()[1];
			if(!config.isFullscreen()){
				Gdx.graphics.setWindowedMode(width,height);
				
				Lwjgl3Graphics g = (Lwjgl3Graphics) Gdx.graphics;
			    Lwjgl3Window window = g.getWindow();
			    window.setPosition(Gdx.graphics.getMonitors()[config.getCurrentMonitor()].virtualX,Gdx.graphics.getMonitors()[config.getCurrentMonitor()].virtualY+25);
			}else{
				height += 25;
				Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode(Gdx.graphics.getMonitors()[config.getCurrentMonitor()]));
			}
			vport.update(width,height, false);
			if(!(gameMan != null && this.getScreen() == gameMan)){
				camera.position.set(width/2, height/2, 0);
			}
			vport.apply();
			if(this.getScreen() == mainMenu || this.getScreen() == settingsMenu || this.getScreen() == creditsMenu
					|| this.getScreen() == savesMenu || this.getScreen() == startMenu){
				mainMenu.resize(height, height);
				settingsMenu.resize(height, height);
				creditsMenu.resize(height, height);
				savesMenu.resize(height, height);
				startMenu.resize(height, height);
			}	
		}
	}
	
	public void reload(){
		if(this.getScreen() == mainMenu || this.getScreen() == settingsMenu || this.getScreen() == creditsMenu){
			mainMenu.reload();
			settingsMenu.reload();
			creditsMenu.reload();
			startMenu.reload();
			savesMenu.reload();
		}	
	}
	
	@Override
	public void render () {
		try{
			super.render();
		}catch(Exception e){
			StringWriter err = new StringWriter();
			e.printStackTrace(new PrintWriter(err));
			VNLogger.logErr(err.toString(),this.getClass());
			this.dispose();
		}
	}
	
	public void setPro(){
		vport.apply();
		batch.setProjectionMatrix(camera.combined);
	}
	
	@Override
	public void dispose () {
		VNLogger.log("Exiting game", this.getClass());
		onClose = true;
		if(!gameMan.isDisposed())gameMan.dispose();
		disposeMenus();
		super.dispose();
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
			initMenus();
			this.setScreen(mainMenu);
			break;
		case SETTINGSMENU:
			initMenus();
			this.setScreen(settingsMenu);
			break;
		case CREDITSMENU:
			initMenus();
			this.setScreen(creditsMenu);
			break;
		case SAVESMENU:
			initMenus();
			this.setScreen(savesMenu);
			break;
		case STARTMENU:
			initMenus();
			this.setScreen(startMenu);
			break;
		case LOADGAME:
			if(this.getScreen() instanceof StartGameMenu) {
				disposeMenus();
			}
			loadingScreen = new LoadingScreen(this, LoadingScreen.GAMEMANAGER);
			this.setScreen(loadingScreen);
			break;
		case GAMEPLAY:
			gameMan.init();
			this.setScreen(gameMan);
			disposeMenus();
			loadingScreen.dispose();
			loadingScreen = null;
			break;
		}
	}
	
	private void initMenus(){
		if(mainMenu.isDisposed()){
			RuntimeData.getInstance().getAssetManager().loadAssets(Indentifiers.ASSETMANAGER_INIT);
			mainMenu.init();
		}
		if(settingsMenu.isDisposed())settingsMenu.init();
		if(creditsMenu.isDisposed())creditsMenu.init();
		if(startMenu.isDisposed())startMenu.init();
		if(savesMenu.isDisposed())savesMenu.init();
	}
	
	private void disposeMenus(){
		if(!mainMenu.isDisposed()){
			RuntimeData.getInstance().getAssetManager().unloadAssets(Indentifiers.ASSETMANAGER_INIT);
			mainMenu.dispose();
		}
		if(!settingsMenu.isDisposed())settingsMenu.dispose();
		if(!creditsMenu.isDisposed())creditsMenu.dispose();
		if(!startMenu.isDisposed())startMenu.dispose();
		if(!savesMenu.isDisposed())savesMenu.dispose();
	}
	
	public Configurator getConfig() {
		return config;
	}

	public void setConfig(Configurator config) {
		this.config = config;
	}
}
