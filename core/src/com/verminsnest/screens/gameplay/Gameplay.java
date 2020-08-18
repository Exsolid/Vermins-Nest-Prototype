package com.verminsnest.screens.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.verminsnest.core.engine.RenderSystem;
import com.verminsnest.core.management.Indentifiers;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.entities.Entity;
import com.verminsnest.entities.items.Item;
import com.verminsnest.misc.gui.dialogs.ChoiceDialog;
import com.verminsnest.misc.gui.dialogs.TradeDialog;
import com.verminsnest.screens.gameplay.menus.GameplayMenu;
import com.verminsnest.world.management.FloorManager;

public class Gameplay extends GameplayOverlay{
	//Gui
	private GameplayMenu gui;
	
	//Entities
	private Entity interactable;
	
	public Gameplay(GameManager gameMan) {
		super(gameMan);
		gui = new GameplayMenu(gameMan);
		// Camera
		RuntimeData.getInstance().getGame().getCamera().position.set(RuntimeData.getInstance().getEntityManager().getCharacter().getPos()[0], RuntimeData.getInstance().getEntityManager().getCharacter().getPos()[1], 0);
		RuntimeData.getInstance().getGame().getCamera().update();
		RuntimeData.getInstance().getGame().setPro();
	}
	
	public void render(float delta) {
		RenderSystem.renderGameplay(delta);
		gui.render(delta);
	}
	
	public void manageControls(float delta) {
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
			int distance = 999;
			Entity interactable = null;
			for(Entity ent: RuntimeData.getInstance().getEntityManager().getInteractables()){
				int dist = RuntimeData.getInstance().getEntityManager().getDistanceBetween(RuntimeData.getInstance().getEntityManager().getCharacter(), ent);
				if(dist <= distance){
					distance = dist;
					interactable = ent;
				}
			}
			//TODO implement separate shop dialog
			if(distance < 15 && interactable != null && interactable instanceof Item){
				if(((Item)interactable).getPrice() == 0) gameMan.setDialog(new ChoiceDialog("Gameplay_Dialog_Accept","Gameplay_Dialog_Cancel","Gameplay_Dialog_Description_Item",((Item)interactable).getIconPath(),new int[]{RuntimeData.getInstance().getEntityManager().getCharacter().getPos()[0]-ChoiceDialog.getSize()[0]/2,RuntimeData.getInstance().getEntityManager().getCharacter().getPos()[1]+ChoiceDialog.getSize()[1]}, Indentifiers.ITEMDIALOG));
				else gameMan.setDialog(new TradeDialog(((Item)interactable).getIconPath(),new int[]{RuntimeData.getInstance().getEntityManager().getCharacter().getPos()[0]-ChoiceDialog.getSize()[0]/2,RuntimeData.getInstance().getEntityManager().getCharacter().getPos()[1]+ChoiceDialog.getSize()[1]},((Item)interactable).getPrice()));
				this.interactable = interactable;
			}
		}
		
		RuntimeData.getInstance().getGame().getCamera().position.x = RuntimeData.getInstance().getEntityManager().getCharacter().getPos()[0];
		RuntimeData.getInstance().getGame().getCamera().position.y = RuntimeData.getInstance().getEntityManager().getCharacter().getPos()[1];
		RuntimeData.getInstance().getGame().setPro();
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
				if(((Item)interactable).getPrice() > 0 && RuntimeData.getInstance().getEntityManager().getCharacter().getInventory().getFoodCount() >= ((Item)interactable).getPrice()) {
					RuntimeData.getInstance().getEntityManager().getCharacter().getInventory().setFoodCount(RuntimeData.getInstance().getEntityManager().getCharacter().getInventory().getFoodCount()-((Item)interactable).getPrice());
					if(RuntimeData.getInstance().getEntityManager().getCharacter().getInventory().getItem() != null){
						RuntimeData.getInstance().getEntityManager().getCharacter().getInventory().getItem().putItem(new int[]{interactable.getPos()[0],interactable.getPos()[1]});
					}
					((Item)interactable).takeItem(RuntimeData.getInstance().getEntityManager().getCharacter());
				}else if(((Item)interactable).getPrice() == 0){
					if(RuntimeData.getInstance().getEntityManager().getCharacter().getInventory().getItem() != null){
						RuntimeData.getInstance().getEntityManager().getCharacter().getInventory().getItem().putItem(new int[]{interactable.getPos()[0],interactable.getPos()[1]});
					}
					((Item)interactable).takeItem(RuntimeData.getInstance().getEntityManager().getCharacter());
				}
				break;
			}
			gameMan.setDialog(null);
			interactable = null;
		}else if(gameMan.getDialog() != null && gameMan.getDialog().getState() == Indentifiers.DIALOG_CANCEL){
			gameMan.setDialog(null);
		}
	}
}
