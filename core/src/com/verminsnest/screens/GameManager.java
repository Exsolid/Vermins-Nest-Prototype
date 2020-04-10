package com.verminsnest.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.verminsnest.entities.Entity;
import com.verminsnest.entities.enemies.Tinker;
import com.verminsnest.entities.playables.Mage;
import com.verminsnest.entities.playables.Playable;
import com.verminsnest.entities.projectiles.Projectile;
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
	// Game
	private VerminsNest game;
	
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
		new Tinker(new int[]{character.getPos()[0]-200,character.getPos()[1]-100});
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
				
				//Draw removed one last time
				for(Entity ent: RuntimeData.getInstance().getRemoved()){
					game.getBatch().draw(ent.getShadow(),ent.getPos()[0]+8,ent.getPos()[1]-18);
				}
				for(Entity ent: RuntimeData.getInstance().getRemoved()){
					game.getBatch().draw(ent.getCurrentFrame(stateTime),ent.getPos()[0],ent.getPos()[1]);
				}      
				//Draw ground
				for (MapCell cell : toDraw) {
					game.getBatch().draw(cell.getLayers().get(0), cell.getxPos(), cell.getyPos());
					if (cell.getLayers().size() > 1 && cell.isWalkable()) {
						game.getBatch().draw(cell.getLayers().get(1), cell.getxPos(), cell.getyPos());
					}
				}
				//Draw shadows
				for(Entity ent: RuntimeData.getInstance().getEntities()){
					game.getBatch().draw(ent.getShadow(),ent.getPos()[0]+8,ent.getPos()[1]-18);
				}
				//Draw walls
				for (MapCell cell : toDraw) {
					if (cell.getLayers().size() > 1 && !cell.isWalkable()) {
						game.getBatch().draw(cell.getLayers().get(1), cell.getxPos(), cell.getyPos());
					}
				}
				//Draw entities
				for(Entity ent: RuntimeData.getInstance().getEntities()){
					if(ent instanceof Projectile){
						game.getBatch().draw(ent.getCurrentFrame(stateTime), ent.getPos()[0],ent.getPos()[1], ent.getSize()[0]/2, ent.getSize()[1]/2, ent.getSize()[0], ent.getSize()[1], 1, 1, ((Projectile) ent).getRotation());
					}else{
						game.getBatch().draw(ent.getCurrentFrame(stateTime),ent.getPos()[0],ent.getPos()[1]);
					}
				}
				game.getBatch().end();

				RuntimeData.getInstance().updateEntities();
			}
		}
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
		map[0][0].getLayers().get(0).getTexture().dispose();
		character.dispose();
	}
}
