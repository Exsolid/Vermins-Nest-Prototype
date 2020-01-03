package com.verminsnest.misc;

import java.util.ArrayList;

public class ButtonManager {
	
	private ArrayList<Button> buttonList;
	private int index;
	
	public ButtonManager(Button button){
		index = 0;
		buttonList = new ArrayList<Button>();
		buttonList.add(button);
		buttonList.get(index).setActiv(true);
	}

	public ButtonManager(ArrayList<Button> buttonList){
		index = 0;
		this.buttonList = buttonList;
		this.buttonList.get(index).setActiv(true);
	}
	
	public void next(){
		if(index+1 == buttonList.size()){
			buttonList.get(index).setActiv(false);
			index = 0;
			buttonList.get(index).setActiv(true);
		}else{
			buttonList.get(index).setActiv(false);
			index++;
			buttonList.get(index).setActiv(true);
		}
	}
	public void previous(){
		if(index-1 == -1){
			buttonList.get(index).setActiv(false);
			index = buttonList.size()-1;
			buttonList.get(index).setActiv(true);
		}else{
			buttonList.get(index).setActiv(false);
			index--;
			buttonList.get(index).setActiv(true);
		}	
	}
	
	public void deactivate(){
		buttonList.get(index).setActiv(false);
	}
	
	public void activate(){
		buttonList.get(index).setActiv(true);
	}
	
	public void addButton(Button button){
		if(button!=null) buttonList.add(button);
	}
	
	public void removeButton(int index){
		buttonList.remove(index);
	}
	
	public ArrayList<Button> getButtonList() {
		return buttonList;
	}
	
	public void setButtonList(ArrayList<Button> buttonList) {
		this.buttonList = buttonList;
	}
	
	public int getIndex(){
		return index;
	}
	
	public void dispose(){
		for(Button button: buttonList){
			button.setActiv(false);
			button.getCurrent().dispose();
			button.setActiv(true);
			button.getCurrent().dispose();
		}
		buttonList = null;
	}
}
