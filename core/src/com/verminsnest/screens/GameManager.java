package com.verminsnest.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.core.EntityMovementSystem;
import com.verminsnest.entities.Mage;
import com.verminsnest.entities.Playable;
import com.verminsnest.entities.Projectile;
import com.verminsnest.gamedev.VerminsNest;
import com.verminsnest.mapgen.MapCell;
import com.verminsnest.singletons.RuntimeData;

public class GameManager implements Screen {

	// Textures
	private Playable character;
	private MapCell[][] map;
	private ArrayList<MapCell> toDraw;

	// Controls
	private long blockTime;
	private boolean movementBlocked;
	private long blockStartTime;
	private char currentKey;
	private char prevKey;

	// Game
	private VerminsNest game;
	private EntityMovementSystem enMoSys;
	
	// Rendering
	private float timeSinceRender = 0;
	private float updateStep = 1 / 120f;
	private float stateTime;
	private boolean running;

	public GameManager( VerminsNest game) {
		this.game = game;
	}

	@Override
	public void show() {
		// Camera
		game.getCamera().position.set(character.getPos()[0], character.getPos()[1], 0);
		game.getCamera().update();
		game.setPro();
		calcToDraw();
	}

	// Initialization
	public void init() {
		character = new Mage(new int[] { 0, 0 });
		// Controls
		blockTime = 0;
		blockStartTime = System.currentTimeMillis();
		movementBlocked = true;
		prevKey = '-';

		// Rendering
		running = true;
		stateTime = 0f;
		
		this.map = RuntimeData.getInstance().getMap();
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[0].length; y++) {
				if (this.map[x][y].isWalkable()) {
					character.getPos()[0] = this.map[x][y].getxPos();
					character.getPos()[1] = this.map[x][y].getyPos();
				}
			}
		}
		enMoSys = new EntityMovementSystem(this.map);
		enMoSys.setPos(character);
		// Textures
		toDraw = new ArrayList<>();
	}

	@Override
	public void render(float delta) {
		if (running) {
			timeSinceRender += Gdx.graphics.getDeltaTime();
			//Cap out rendering cycle to 120 frames/second
			if (timeSinceRender >= updateStep) {
				stateTime += delta;
				timeSinceRender -= updateStep;
				manageControls();
				
				//Draw stuff
				game.getBatch().begin();
				//Draw ground
				for (MapCell cell : toDraw) {
					game.getBatch().draw(cell.getLayers().get(0), cell.getxPos(), cell.getyPos());
					if (cell.getLayers().size() > 1 && cell.isWalkable()) {
						game.getBatch().draw(cell.getLayers().get(1), cell.getxPos(), cell.getyPos());
					}
				}
				//Draw shadows
				game.getBatch().draw(character.getShadow(), character.getPos()[0] + 8, character.getPos()[1] - 18);
				drawProjectileShadow();
				//Draw character
				game.getBatch().draw(character.getCurrentFrame(stateTime), character.getPos()[0],
						character.getPos()[1]);
				character.setCurrentAni(Playable.IDLE);
				//Draw projectiles
				drawProjectiles();
				//Draw walls
				for (MapCell cell : toDraw) {
					if (cell.getLayers().size() > 1 && !cell.isWalkable()) {
						game.getBatch().draw(cell.getLayers().get(1), cell.getxPos(), cell.getyPos());
					}
				}
				game.getBatch().end();
			}
		}
	}
		
	private void drawProjectiles(){
		for(Projectile prj: RuntimeData.getInstance().getCurrentProjectiles()){
			game.getBatch().draw(prj.getCurrentFrame(stateTime),prj.getPos()[0],prj.getPos()[1]);
		}
	}
	private void drawProjectileShadow(){
		for(Projectile prj: RuntimeData.getInstance().getCurrentProjectiles()){
			prj.updatePosition(enMoSys);
			game.getBatch().draw(prj.getShadow(),prj.getPos()[0]+8,prj.getPos()[1]-25);
		}
		RuntimeData.getInstance().updateProjectiles();
	}

	// Calculates which tiles need to be rendered
	private void calcToDraw() {
		int[] width = new int[2];
		int[] height = new int[2];
		width[0] = (character.getPos()[0] - 1920 / 2) / 128;
		width[1] = (character.getPos()[0] + 1920 / 2) / 128;
		height[0] = (character.getPos()[1] - 1055 / 2) / 128;
		height[1] = (character.getPos()[1] + 1055 / 2) / 128;

		toDraw.clear();
		for (int y = height[0]; y <= height[1]; y++) {
			for (int x = width[0]; x <= width[1]; x++) {
				toDraw.add(map[x][y]);
			}
		}
	}

	private void manageControls() {

		blockTime = System.currentTimeMillis() - blockStartTime;
		if (blockTime > 30) {
			movementBlocked = false;
		}

		// Movement
		// S Pressed
		if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
			prevKey = currentKey;
			currentKey = 'S';
		}
		// D Pressed
		if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
			prevKey = currentKey;
			currentKey = 'D';
		}
		// A Pressed
		if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
			prevKey = currentKey;
			currentKey = 'A';
		}
		// W Pressed
		if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
			prevKey = currentKey;
			currentKey = 'W';
		}

		//Calculates proper movement
		switch (currentKey) {
		case 'W':
			if (!Gdx.input.isKeyPressed(Input.Keys.W)) {
				if (prevKey != '-') {
					currentKey = prevKey;
					prevKey = '-';
				} else {
					currentKey = '-';
				}
			}
			enMoSys.moveTop(character, character.getSpeed());
			character.setCurrentAni(Playable.W_BACK);
			break;
		case 'D':
			if (!Gdx.input.isKeyPressed(Input.Keys.D)) {
				if (prevKey != '-') {
					currentKey = prevKey;
					prevKey = '-';
				} else {
					currentKey = '-';
				}
			}
			enMoSys.moveRight(character, character.getSpeed());
			character.setCurrentAni(Playable.W_RIGHT);
			break;
		case 'S':
			if (!Gdx.input.isKeyPressed(Input.Keys.S)) {
				if (prevKey != '-') {
					currentKey = prevKey;
					prevKey = '-';
				} else {
					currentKey = '-';
				}
			}
			enMoSys.moveDown(character, character.getSpeed());
			character.setCurrentAni(Playable.W_FRONT);
			break;
		case 'A':
			if (!Gdx.input.isKeyPressed(Input.Keys.A)) {
				if (prevKey != '-') {
					currentKey = prevKey;
					prevKey = '-';
				} else {
					currentKey = '-';
				}
			}
			enMoSys.moveLeft(character, character.getSpeed());
			character.setCurrentAni(Playable.W_LEFT);
			break;
		case '-':
			prevKey = '-';
		}

		game.getCamera().position.x = character.getPos()[0];
		game.getCamera().position.y = character.getPos()[1];
		game.setPro();
		calcToDraw();
		
		//Pause
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && !movementBlocked) {
			game.togglePause();
			movementBlocked = true;
			blockStartTime = System.currentTimeMillis();
		}
		
		//Attacking
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
			character.attack(stateTime);
		}
	}

	public boolean isRunning() {
		return running;
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void pause() {
		running = false;
	}

	@Override
	public void resume() {
		running = true;
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		for (int y = 0; y < map[0].length; y++) {
			for (int x = 0; x < map.length; x++) {
				for (TextureRegion text : map[x][y].getLayers()) {
					text.getTexture().dispose();
				}
			}
		}
	}
}
