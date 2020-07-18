package com.verminsnest.misc.gui;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.verminsnest.core.management.Indentifiers;
import com.verminsnest.core.management.data.RuntimeData;

public class ChoiceDialog {
	private ButtonManager choiceManager;
	private FontText description;
	private int[] position;
	private int state;
	
	private Texture icon;
	private Texture background;
	
	private final static int ACCEPT = 0;
	private final static int CANCLE = 1;
	private int ID;
	
	public ChoiceDialog(String accept, String decline, String description, String iconPath, int[] position, int ID){
		ArrayList<ArrayList<String>> buttons = new ArrayList<>();
		ArrayList<String> temp = new ArrayList<>();
		temp.add(accept);
		buttons.add(temp);
		temp = new ArrayList<>();
		temp.add(decline);
		buttons.add(temp);
		choiceManager = new ButtonManager(buttons, 50, false, null, null, true);
		if(iconPath != null && !iconPath.equals(""))icon = RuntimeData.getInstance().getAsset(iconPath);
		background = RuntimeData.getInstance().getAsset("textures/menus/scrolls/HorizontalScroll_Medium.png");
		this.description = new FontText(description, 50, true);
		this.description.setMidOfBounds(position, new int[]{background.getWidth(),background.getHeight()});
		this.choiceManager.setMidOfBounds(new int[]{(int) (background.getWidth()*1.75),background.getHeight()}, position);
		state = Indentifiers.DIALOG_OPEN;
		this.position = position;
		this.ID = ID;
	}
	
	public void update(float delta){
		manageControls();
	}
	
	public void render(float delta){
		RuntimeData.getInstance().getGame().getBatch().draw(background,position[0],position[1]);
		if(icon != null){
			RuntimeData.getInstance().getGame().getBatch().draw(icon,position[0]+icon.getWidth()*1.5f,position[1]+background.getHeight()/2-icon.getHeight()/2);
		}
		description.draw(RuntimeData.getInstance().getGame().getBatch());
		choiceManager.draw(RuntimeData.getInstance().getGame().getBatch());
	}
	
	private void manageControls(){
		if(Gdx.input.isKeyJustPressed(Input.Keys.W)){
			choiceManager.next();
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.S)){
			choiceManager.prev();
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.E)){
			switch(choiceManager.getIndex()){
			case ACCEPT:
				state = Indentifiers.DIALOG_OKAY;
				break;
			case CANCLE:
				state = Indentifiers.DIALOG_CANCEL;
				break;
			}
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
			state = Indentifiers.DIALOG_CANCEL;
		}
	}
	
	public int getState(){
		return state;
	}
	
	public int getID(){
		return ID;
	}
	
	public void dispose(){
		choiceManager.dispose();
		description.dispose();
	}
	
	public static int[] getSize(){
		return new int[]{RuntimeData.getInstance().getAsset("textures/menus/scrolls/HorizontalScroll_Medium.png").getWidth(),RuntimeData.getInstance().getAsset("textures/menus/scrolls/HorizontalScroll_Medium.png").getHeight()};
	}
}
