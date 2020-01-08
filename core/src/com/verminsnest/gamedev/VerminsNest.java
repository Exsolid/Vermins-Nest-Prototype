package com.verminsnest.gamedev;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.verminsnest.config.Configurator;
import com.verminsnest.screens.MainMenu;
import com.verminsnest.screens.SettingsMenu;

public class VerminsNest extends Game {
	
	private SpriteBatch batch;
	private MainMenu mainMenu;
	private SettingsMenu settingsMenu;
	private Runtime r;
	private Configurator config;
	@Override
	public void create () {
		setConfig(new Configurator());
		this.setBatch(new SpriteBatch());
		mainMenu = new MainMenu(this);
		this.setScreen(mainMenu);
		r = Runtime.getRuntime();
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		Gdx.app.exit();
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}
	
	public void screenSettings(){
		settingsMenu = new SettingsMenu(this);
		this.setScreen(settingsMenu);
		r.gc();
	}
	
	public void screenMainMenu(){
		mainMenu = new MainMenu(this);
		this.setScreen(mainMenu);
		r.gc();
	}

	public Configurator getConfig() {
		return config;
	}

	public void setConfig(Configurator config) {
		this.config = config;
	}
}
