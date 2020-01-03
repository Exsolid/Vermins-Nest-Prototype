package com.verminsnest.screens;

import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.verminsnest.gamedev.VerminsNest;
import com.verminsnest.misc.Button;
import com.verminsnest.misc.ButtonManager;

public class MainMenu implements Screen {
	
	//Misc
	private VerminsNest game;
	private boolean running;
	//Buttons
	private ButtonManager bManager;
	private boolean movementBlocked;
	private long blockTime;
	private long blockStartTime;
	private static final int START = 0;
	private static final int SETTINGS = 1;
	private static final int CREDITS = 2;
	private static final int QUIT = 3;	
	//Textures
	private Texture backgroundImg;
	
	public MainMenu(VerminsNest game){
		this.game = game;
	}
	
	@Override
	public void show() {
		//Textures
		backgroundImg = new Texture("mainMenu/Background.png");
		
		//Buttons
		blockTime = 0;
		blockStartTime = 0;	
		movementBlocked = false;
		Button playButton = new Button("mainMenu/PlayButton.png", "mainMenu/PlayButtonA.png");
		Button settingsButton = new Button("mainMenu/SettingsButton.png", "mainMenu/SettingsButtonA.png");
		Button creditsButton = new Button("mainMenu/CreditsButton.png", "mainMenu/CreditsButtonA.png");
		Button quitButton = new Button("mainMenu/QuitButton.png", "mainMenu/QuitButtonA.png");
		ArrayList<Button> buttonList = new ArrayList<Button>();
		buttonList.add(playButton);
		buttonList.add(settingsButton);
		buttonList.add(creditsButton);
		buttonList.add(quitButton);
		bManager = new ButtonManager(buttonList);		
		this.calculateButtonPos();
		
		//Misc
		running = true;
	}

	@Override
	public void render(float delta) {
		if(running){
			game.getBatch().begin();
			game.getBatch().draw(backgroundImg, 0,0);
			for(int i = 0; i<bManager.getButtonList().size();i++){
				game.getBatch().draw(bManager.getButtonList().get(i).getCurrent(),bManager.getButtonList().get(i).getPos().x, bManager.getButtonList().get(i).getPos().y);	
			}
			game.getBatch().end();
			
			this.mangageControls();
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
		running = false;
		bManager.dispose();
	}
	
	public void close(){
		game.dispose();
	}
	
	//Change to preferences later  
	private void mangageControls(){
		
		blockTime = System.currentTimeMillis() - blockStartTime;
		if(blockTime > 250){
			movementBlocked = false;
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.S)){
			if(!movementBlocked){
				bManager.next();
				movementBlocked = true;
				blockStartTime = System.currentTimeMillis();
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)){
			if(!movementBlocked){
				bManager.previous();
				movementBlocked = true;
				blockStartTime = System.currentTimeMillis();
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
			if(!movementBlocked){				
				switch(bManager.getIndex()){
				case START:
					break;
				case SETTINGS:
					game.setScreen(new SettingsMenu(game));
					this.dispose();
					break;
				case CREDITS:
					game.setScreen(new CreditsMenu(game));
					this.dispose();
					break;
				case QUIT:					
					this.dispose();
					this.close();
					break;
				}			
				movementBlocked = true;
				blockStartTime = System.currentTimeMillis();
			}
		}
	}
	
	private void calculateButtonPos(){	
		//Calculate total height
		int totalHeight = 0;		
		for(int i = 0; i<bManager.getButtonList().size();i++){
			totalHeight += bManager.getButtonList().get(i).getCurrent().getHeight();
			if(i != bManager.getButtonList().size()-1){
				totalHeight += bManager.getButtonList().get(i).getCurrent().getHeight()/2;
			}
		}
		
		//Calculate height for each button
		int staticHeight = totalHeight/2+bManager.getButtonList().get(0).getCurrent().getHeight();
		for(int i = 0; i<bManager.getButtonList().size();i++){
			Point pos = new Point(0,0);
			pos.x = Gdx.graphics.getWidth()/2-bManager.getButtonList().get(i).getCurrent().getWidth()/2;
			pos.y = Gdx.graphics.getHeight()/2-staticHeight+totalHeight;
			totalHeight -= bManager.getButtonList().get(i).getCurrent().getHeight() + bManager.getButtonList().get(i).getCurrent().getHeight()/2; 		
			bManager.getButtonList().get(i).setPos(pos);
		}
	}
}
