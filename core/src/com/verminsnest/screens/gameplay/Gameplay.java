package com.verminsnest.screens.gameplay;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.verminsnest.entities.Entity;
import com.verminsnest.entities.enemies.Tinker;
import com.verminsnest.entities.playables.Mage;
import com.verminsnest.entities.projectiles.Projectile;
import com.verminsnest.gamedev.VerminsNest;
import com.verminsnest.mapgen.MapCell;
import com.verminsnest.screens.gameplay.menus.GameplayMenu;
import com.verminsnest.singletons.RuntimeData;

public class Gameplay extends GameplayScreen{
	// Controls
	private long blockTime;
	private boolean movementBlocked;
	private long blockStartTime;
	// Textures
	private ArrayList<MapCell> toDraw;
	
	//Gui
	private GameplayMenu gui;
	
	public Gameplay(VerminsNest game,GameManager gameMan) {
		super(game,gameMan);
		gui = new GameplayMenu(game,gameMan);
		RuntimeData.getInstance().setCharacter(new Mage(new int[] { 0, 0 }));
		// Controls
		blockTime = 0;
		blockStartTime = System.currentTimeMillis();
		movementBlocked = true;
		
		for (int x = 0; x < RuntimeData.getInstance().getMap().length; x++) {
			for (int y = 0; y < RuntimeData.getInstance().getMap()[0].length; y++) {
				if (RuntimeData.getInstance().getMap()[x][y].isWalkable()) {
					RuntimeData.getInstance().getCharacter().getPos()[0] = RuntimeData.getInstance().getMap()[x][y].getxPos();
					RuntimeData.getInstance().getCharacter().getPos()[1] = RuntimeData.getInstance().getMap()[x][y].getyPos();
				}
			}
		}
		for( int i = 100; i<200; i+=100){
			new Tinker(new int[]{RuntimeData.getInstance().getCharacter().getPos()[0]-i,RuntimeData.getInstance().getCharacter().getPos()[1]-100});
		}
		// Textures
		toDraw = new ArrayList<>();
		// Camera
		game.getCamera().position.set(RuntimeData.getInstance().getCharacter().getPos()[0], RuntimeData.getInstance().getCharacter().getPos()[1], 0);
		game.getCamera().update();
		game.setPro();
		calcToDraw();
	}
	
	public void render(float stateTime) {
				//Draw stuff
				game.getBatch().begin();
				//Draw removed one last time
				for(Entity ent: RuntimeData.getInstance().getRemoved()){
					if(ent.getShadow() != null){
						game.getBatch().draw(ent.getShadow(),ent.getPos()[0]+8,ent.getPos()[1]-18);
					}
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
					if(ent.getShadow() != null){
						game.getBatch().draw(ent.getShadow(),ent.getPos()[0],ent.getPos()[1]+ent.getYShadowOffset());	
					}
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
				gui.render(stateTime);
	}
	
	public void manageControls(float stateTime) {
		blockTime = System.currentTimeMillis() - blockStartTime;
		if (blockTime > 30) {
			movementBlocked = false;
		}
		calcToDraw();
		
		//Pause
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && !movementBlocked) {
			gameMan.setState(GameManager.PAUSEMENU);
			movementBlocked = true;
			blockStartTime = System.currentTimeMillis();
		}
		
		//Attacking
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
			RuntimeData.getInstance().getCharacter().attack(stateTime);
		}
		game.getCamera().position.x = RuntimeData.getInstance().getCharacter().getPos()[0];
		game.getCamera().position.y = RuntimeData.getInstance().getCharacter().getPos()[1];
		game.setPro();
	}
	
	// Calculates which tiles need to be rendered
	private void calcToDraw() {
		int[] width = new int[2];
		int[] height = new int[2];
		width[0] = (RuntimeData.getInstance().getCharacter().getPos()[0] - 1920 / 2) / 128;
		width[1] = (RuntimeData.getInstance().getCharacter().getPos()[0] + 1920 / 2) / 128;
		height[0] = (RuntimeData.getInstance().getCharacter().getPos()[1] - 1055 / 2) / 128;
		height[1] = (RuntimeData.getInstance().getCharacter().getPos()[1] + 1055 / 2) / 128;

		toDraw.clear();
		for (int y = height[0]; y <= height[1]; y++) {
			for (int x = width[0]; x <= width[1]; x++) {
				toDraw.add(RuntimeData.getInstance().getMap()[x][y]);
			}
		}
	}

	@Override
	public void dispose() {
	}

	@Override
	public void update(float stateTime) {
		RuntimeData.getInstance().updateEntities(stateTime);
		gui.update(stateTime);
		manageControls(stateTime);
	}
}
