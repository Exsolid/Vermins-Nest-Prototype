package com.verminsnest.gamedev;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.verminsnest.screens.MainMenu;

public class VerminsNest extends Game {
	
	private SpriteBatch batch;
	@Override
	public void create () {
		this.setBatch(new SpriteBatch());
		this.setScreen(new MainMenu(this));
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
}
