package com.verminsnest.screens.gameplay;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.verminsnest.core.VerminsNest;
import com.verminsnest.core.singletons.RuntimeData;
import com.verminsnest.entities.Entity;
import com.verminsnest.screens.gameplay.menus.GameplayMenu;
import com.verminsnest.world.generation.map.MapCell;
import com.verminsnest.world.management.FloorManager;

public class Gameplay extends GameplayOverlay{
	// Textures
	private ArrayList<MapCell> toDraw;
	
	//Gui
	private GameplayMenu gui;
	
	public Gameplay(VerminsNest game,GameManager gameMan) {
		super(game,gameMan);
		gui = new GameplayMenu(game,gameMan);
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
					game.getBatch().draw(ent.getCurrentFrame(stateTime), ent.getPos()[0],ent.getPos()[1], ent.getSize()[0]/2, ent.getSize()[1]/2, ent.getSize()[0], ent.getSize()[1], 1, 1, ent.getRotation());
				}
				gui.render(stateTime);
	}
	
	public void manageControls(float stateTime) {
		calcToDraw();
		
		//Pause
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && !gameMan.isControlBlocked()) {
			gameMan.setState(GameManager.PAUSEMENU);
			gameMan.resetBlocked();
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.I) && !gameMan.isControlBlocked()) {
			gameMan.setState(GameManager.LEVELMENU);
			gameMan.resetBlocked();
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
		gui.dispose();
	}

	@Override
	public void update(float stateTime) {
		RuntimeData.getInstance().updateEntities(stateTime);

		if(FloorManager.getInstane().allowEntityUpdate()) {
			gui.update(stateTime);
			manageControls(stateTime);
		}
	}
}
