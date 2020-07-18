package com.verminsnest.screens.gameplay;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.verminsnest.core.management.Indentifiers;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.entities.Entity;
import com.verminsnest.entities.items.Item;
import com.verminsnest.misc.gui.ChoiceDialog;
import com.verminsnest.screens.gameplay.menus.GameplayMenu;
import com.verminsnest.world.generation.map.MapCell;
import com.verminsnest.world.management.FloorManager;

public class Gameplay extends GameplayOverlay{
	// Textures
	private ArrayList<MapCell> toDraw;
	
	//Gui
	private GameplayMenu gui;
	
	//Entities
	private Entity interactable;
	
	public Gameplay(GameManager gameMan) {
		super(gameMan);
		gui = new GameplayMenu(gameMan);
		// Textures
		toDraw = new ArrayList<>();
		// Camera
		RuntimeData.getInstance().getGame().getCamera().position.set(RuntimeData.getInstance().getEntityManager().getCharacter().getPos()[0], RuntimeData.getInstance().getEntityManager().getCharacter().getPos()[1], 0);
		RuntimeData.getInstance().getGame().getCamera().update();
		RuntimeData.getInstance().getGame().setPro();
		calcToDraw();
	}
	
	public void render(float delta) {
				//Draw stuff
				//Draw removed one last time
				for(Entity ent: RuntimeData.getInstance().getEntityManager().getRemoved()){
					if(ent.getShadow() != null){
						RuntimeData.getInstance().getGame().getBatch().draw(ent.getShadow(),ent.getPos()[0]+8,ent.getPos()[1]-18);
					}
				}
				for(Entity ent: RuntimeData.getInstance().getEntityManager().getRemoved()){
					RuntimeData.getInstance().getGame().getBatch().draw(ent.getCurrentFrame(delta),ent.getPos()[0],ent.getPos()[1]);
				}      
				//Draw ground
				for (MapCell cell : toDraw) {
					RuntimeData.getInstance().getGame().getBatch().draw(cell.getLayers().get(0), cell.getxPos(), cell.getyPos());
					if (cell.isWalkable()) {
						for(int i = cell.getLayers().size()-1; i > 0; i--){
							RuntimeData.getInstance().getGame().getBatch().draw(cell.getLayers().get(i), cell.getxPos(), cell.getyPos());
						}
					}
					
				}
				//Draw shadows
				for(Entity ent: RuntimeData.getInstance().getEntityManager().getAllEntities()){
					if(ent instanceof Item && ((Item)ent).getPos() != null && ((Item)ent).getKeeper() == null && ent.getShadow() != null){
						RuntimeData.getInstance().getGame().getBatch().draw(ent.getShadow(),ent.getPos()[0],ent.getPos()[1]+ent.getYShadowOffset());	
					}
					else if(!(ent instanceof Item) && ent.getShadow() != null){
						RuntimeData.getInstance().getGame().getBatch().draw(ent.getShadow(),ent.getPos()[0],ent.getPos()[1]+ent.getYShadowOffset());	
					}
				}
				
				//Draw walls
				for (MapCell cell : toDraw) {
					if (cell.getLayers().size() > 1 && !cell.isWalkable()) {
						RuntimeData.getInstance().getGame().getBatch().draw(cell.getLayers().get(1), cell.getxPos(), cell.getyPos());
					}
				}
				
				//Draw ground items
				for(Entity ent: RuntimeData.getInstance().getEntityManager().getItems()){
					if(((Item)ent).getPos() != null && ((Item)ent).getKeeper() == null){
						RuntimeData.getInstance().getGame().getBatch().draw(ent.getCurrentFrame(delta), ent.getPos()[0],ent.getPos()[1], ent.getSize()[0]/2, ent.getSize()[1]/2, ent.getSize()[0], ent.getSize()[1], 1, 1, ent.getRotation());
					}
				}
				
				//Draw entities
				for(Entity ent: RuntimeData.getInstance().getEntityManager().getAllBioEntities()){
					RuntimeData.getInstance().getGame().getBatch().draw(ent.getCurrentFrame(delta), ent.getPos()[0],ent.getPos()[1], ent.getSize()[0]/2, ent.getSize()[1]/2, ent.getSize()[0], ent.getSize()[1], 1, 1, ent.getRotation());
				}
				//Draw kept items
				for(Entity ent: RuntimeData.getInstance().getEntityManager().getItems()){
					if(((Item)ent).getPos() != null && ((Item)ent).getKeeper() != null){
						RuntimeData.getInstance().getGame().getBatch().draw(ent.getCurrentFrame(delta), ent.getPos()[0],ent.getPos()[1], ent.getSize()[0]/2, ent.getSize()[1]/2, ent.getSize()[0], ent.getSize()[1], 1, 1, ent.getRotation());
					}
				}
				gui.render(delta);
	}
	
	public void manageControls(float delta) {
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
		
		//Entity interaction
		if(Gdx.input.isKeyJustPressed(Input.Keys.E)){
			int distance = 0;
			Entity interactable = null;
			for(Entity ent: RuntimeData.getInstance().getEntityManager().getInteractables()){
				int dist = RuntimeData.getInstance().getEntityManager().getDistanceBetween(RuntimeData.getInstance().getEntityManager().getCharacter(), ent);
				if(dist >= distance){
					distance = dist;
					interactable = ent;
				}
			}
			if(distance < 15 && interactable != null && interactable instanceof Item){
				gameMan.setDialog(new ChoiceDialog("Gameplay_Dialog_Accept","Gameplay_Dialog_Cancel","Gameplay_Dialog_Description_Item",((Item)interactable).getIconPath(),new int[]{RuntimeData.getInstance().getEntityManager().getCharacter().getPos()[0]-ChoiceDialog.getSize()[0]/2,RuntimeData.getInstance().getEntityManager().getCharacter().getPos()[1]+ChoiceDialog.getSize()[1]}, Indentifiers.ITEMDIALOG));
				this.interactable = interactable;
			}
		}
		
		RuntimeData.getInstance().getGame().getCamera().position.x = RuntimeData.getInstance().getEntityManager().getCharacter().getPos()[0];
		RuntimeData.getInstance().getGame().getCamera().position.y = RuntimeData.getInstance().getEntityManager().getCharacter().getPos()[1];
		RuntimeData.getInstance().getGame().setPro();
	}
	
	// Calculates which tiles need to be rendered
	private void calcToDraw() {
		int[] width = new int[2];
		int[] height = new int[2];
		width[0] = (RuntimeData.getInstance().getEntityManager().getCharacter().getPos()[0] - 1920 / 2) / 128;
		width[1] = (RuntimeData.getInstance().getEntityManager().getCharacter().getPos()[0] + 1920 / 2) / 128;
		height[0] = (RuntimeData.getInstance().getEntityManager().getCharacter().getPos()[1] - 1055 / 2) / 128;
		height[1] = (RuntimeData.getInstance().getEntityManager().getCharacter().getPos()[1] + 1055 / 2) / 128;

		toDraw.clear();
		for (int y = height[0]; y <= height[1]; y++) {
			for (int x = width[0]; x <= width[1]; x++) {
				toDraw.add(RuntimeData.getInstance().getMapData().getData()[x][y]);
			}
		}
	}

	@Override
	public void dispose() {
		gui.dispose();
	}

	@Override
	public void update(float delta) {
		RuntimeData.getInstance().getEntityManager().updateEntities(delta);
		
		if(FloorManager.getInstane().allowEntityUpdate()) {
			gui.update(delta);
			manageControls(delta);
		}
		
		if(gameMan.getDialog() != null && gameMan.getDialog().getState() == Indentifiers.DIALOG_OKAY){
			switch(gameMan.getDialog().getID()){
			case Indentifiers.ITEMDIALOG:
				((Item)interactable).takeItem(RuntimeData.getInstance().getEntityManager().getCharacter());
				break;
			}
			gameMan.setDialog(null);
			interactable = null;
		}else if(gameMan.getDialog() != null && gameMan.getDialog().getState() == Indentifiers.DIALOG_CANCEL){
			gameMan.setDialog(null);
		}
	}
}
