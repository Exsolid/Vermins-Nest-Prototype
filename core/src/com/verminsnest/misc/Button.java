package com.verminsnest.misc;

import java.awt.Point;
import java.util.ArrayList;

public class Button {
	//Values
	private String text;
	private ArrayList<String> options;
	
	//Operands
	private int optionsIndex;
	private boolean isActiv;
	private int[] titleSpecs; 
	private int[] optionSpecs;
	public Button(String text){
		this.text = text;
		options = new ArrayList<String>();
		setTitleSpecs(new int[4]);
		setOptionSpecs(new int[4]);
	}
	
	//To use in ButtonManager
	public boolean isActiv() {
		return isActiv;
	}

	public void setActiv(boolean isActiv) {
		this.isActiv = isActiv;
	}
	
	//Option handling
	public void nextOption(){
		if(optionsIndex == options.size()-1) optionsIndex = 0;
		else optionsIndex++;
	}
	
	public void prevOption(){
		if(optionsIndex == 0) optionsIndex = options.size()-1;
		else optionsIndex--;
	}
	
	public void addOption(String opText){
		options.add(opText);
	}
	
	public void removeOption(){
		options.remove(optionsIndex);
	}
	
	public boolean hasOptions(){
		if(options.isEmpty()) return false;
		else return true;
	}
	
	public String getOption(){
		return options.get(optionsIndex);
	}
	//Title
	public String getText() {
		return text;
	}

	//Specs handling
	public void setTitlePos(Point pos) {
		titleSpecs[0] = pos.x;
		titleSpecs[1] = pos.y;
	}
	
	public void setTitleSize(Point titleSize){
		titleSpecs[2] = titleSize.x;
		titleSpecs[3] = titleSize.y;
	}

	public void setOptionsPos(Point optionsPos) {
		optionSpecs[0] = optionsPos.x;
		optionSpecs[1] = optionsPos.y;
	}
	
	public void setOptionsSize(Point optionsSize){
		optionSpecs[2] = optionsSize.x;
		optionSpecs[3] = optionsSize.y;
	}
	
	public int[] getTitleSpecs() {
		return titleSpecs;
	}

	public void setTitleSpecs(int[] titleSpecs) {
		this.titleSpecs = titleSpecs;
	}

	public int[] getOptionSpecs() {
		return optionSpecs;
	}

	public void setOptionSpecs(int[] optionSpecs) {
		this.optionSpecs = optionSpecs;
	}
}
