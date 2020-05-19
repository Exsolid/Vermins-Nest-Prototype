package com.verminsnest.misc.gui;

import java.util.ArrayList;

public class Button {
	//Values
	private FontText text;
	private ArrayList<FontText> leftArrow;
	private ArrayList<FontText> rightArrow;
	private ArrayList<FontText> options;
	
	//Operands
	private int optionsIndex;
	private boolean isActiv;
	
	public Button(String text, int size){
		this.text = new FontText(text, size, true);
		options = new ArrayList<>();
		leftArrow = new ArrayList<>();
		rightArrow = new ArrayList<>();
	}
	
	//To use in ButtonManager
	public boolean isActiv() {
		return isActiv;
	}

	public void setActiv(boolean isActiv) {
		this.isActiv = isActiv;
		text.switchColor(isActiv);
	}
	
	//Option handling
	public void nextOption(){
		if(optionsIndex == options.size()-1) optionsIndex = 0;
		else optionsIndex++;
		rightArrow.get(optionsIndex).blink();
	}
	
	public void prevOption(){
		if(optionsIndex == 0) optionsIndex = options.size()-1;
		else optionsIndex--;
		leftArrow.get(optionsIndex).blink();
	}
	
	public void addOption(String opText, String leftArrow, String rightArrow, boolean optsWithQualifiers){
		options.add(new FontText(opText,text.getSize()/2, optsWithQualifiers));
		this.leftArrow.add(new FontText(leftArrow,text.getSize()/2, false));
		this.rightArrow.add(new FontText(rightArrow,text.getSize()/2, false));
	}
	
	public boolean hasOptions(){
		if(options.isEmpty()) return false;
		else return true;
	}
	
	public FontText getOption(){
		if(options.isEmpty()) return null;
		return options.get(optionsIndex);
	}
	
	public ArrayList<FontText> getOptions(){
		return options;
	}
	
	public FontText getText() {
		return text;
	}
	
	public void dispose(){
		text.dispose();
		for(FontText opts: options){
			opts.dispose();
		}
		for(FontText arr: leftArrow){
			arr.dispose();
		}
		for(FontText arr: rightArrow){
			arr.dispose();
		}
	}

	public FontText getRightArrow() {
		return rightArrow.get(optionsIndex);
	}

	public FontText getLeftArrow() {
		return leftArrow.get(optionsIndex);
	}
	
	public ArrayList<FontText> getRightArrows() {
		return rightArrow;
	}

	public ArrayList<FontText> getLeftArrows() {
		return leftArrow;
	}
	
	public void reload(){
		text.reload();
		for(FontText opts: options){
			opts.reload();
		}
	}
}
