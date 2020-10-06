package com.verminsnest.misc.gui.dialogs;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.core.management.ids.Indentifiers;
import com.verminsnest.misc.gui.ButtonManager;
import com.verminsnest.misc.gui.FontText;

public class ChoiceDialog {
	protected ButtonManager choiceManager;
	protected FontText description;
	protected int[] position;
	protected int state;
	
	protected Texture icon;
	protected Texture background;
	
	protected final static int ACCEPT = 0;
	protected final static int CANCLE = 1;
	protected int ID;
	
	public ChoiceDialog(String accept, String decline, String description, String iconPath, int[] position, int ID){
		ArrayList<ArrayList<String>> buttons = new ArrayList<>();
		ArrayList<String> temp = new ArrayList<>();
		temp.add(accept);
		buttons.add(temp);
		temp = new ArrayList<>();
		temp.add(decline);
		buttons.add(temp);
		choiceManager = new ButtonManager(buttons, 50, false, null, null, true);
		if(iconPath != null && !iconPath.equals(""))icon = RuntimeData.getInstance().getTexture(iconPath);
		background = RuntimeData.getInstance().getTexture("textures/menus/scrolls/HorizontalScroll_Medium.png");
		this.description = new FontText(description, 50, true);
		this.description.setMidOfBounds(position, new int[]{background.getWidth(),background.getHeight()});
		this.description.getPos()[0] -= background.getWidth()/10;
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
			RuntimeData.getInstance().getGame().getBatch().draw(icon,(float) (position[0]+background.getWidth()-icon.getWidth()*5),position[1]+background.getHeight()/2-icon.getHeight()/2);
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
		return new int[]{RuntimeData.getInstance().getTexture("textures/menus/scrolls/HorizontalScroll_Medium.png").getWidth(),RuntimeData.getInstance().getTexture("textures/menus/scrolls/HorizontalScroll_Medium.png").getHeight()};
	}
}
