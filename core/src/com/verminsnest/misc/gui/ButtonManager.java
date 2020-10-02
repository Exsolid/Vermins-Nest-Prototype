package com.verminsnest.misc.gui;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonManager {
	
	//All Buttons
	private ArrayList<Button> buttonList;
	private int index;
	
	//Button options
	private boolean isVertical;
	
	//Reload data
	private int[] reSize;
	private int[] rePos;
	//Create and dispose
	public ButtonManager(ArrayList<ArrayList<String>> buttonList, int size, boolean isVertical, String leftButton, String rightButton, boolean optsWithQualifiers){
		this.buttonList = new ArrayList<>();
		createButtons(buttonList, size,leftButton ,rightButton, optsWithQualifiers);	
		this.isVertical = isVertical;
		this.buttonList.get(0).setActiv(true);
	}

	private void createButtons(ArrayList<ArrayList<String>> buttonList, int size, String leftButton, String rightButton, boolean optsWithQualifiers){
		int counter = 0;
		for(ArrayList<String> button: buttonList){
			this.buttonList.add(new Button(button.get(0),size));
			for(int i = 1; i<button.size(); i++){
				this.buttonList.get(counter).addOption(button.get(i),leftButton.equals("")? "<" :leftButton,rightButton.equals("")? ">" :rightButton, optsWithQualifiers);
			}
			counter++;
		}
	}
	
	public void dispose(){
		for(Button button: buttonList){
			button.dispose();
		}
	}
	
	//Button handling
	public void next(){
		buttonList.get(index).setActiv(false);
		if(index == buttonList.size()-1) index = 0;
		else index++;
		buttonList.get(index).setActiv(true);
	}
	
	public void prev(){
		buttonList.get(index).setActiv(false);
		if(index == 0) index = buttonList.size()-1;
		else index--;
		buttonList.get(index).setActiv(true);
	}
	
	public ArrayList<Button> getButtons(){
		return buttonList;
	}
	
	public Button getCurrent(){
		return buttonList.get(index);
	}
	
	public int getIndex(){
		return index;
	}
	
	public void draw(SpriteBatch batch) {
		for(Button button: buttonList){
			button.getText().draw(batch);
			if(button.getOption() != null){
				button.getOption().draw(batch);
				button.getLeftArrow().draw(batch);
				button.getRightArrow().draw(batch);
			}
		}
	}
	
	public void setMidOfBounds(int[] size, int[] pos){
		reSize = size;
		rePos = pos;
		
		int totalHeight = 0;
		FontText biggestX = null;
		
		for(Button button: buttonList){
			totalHeight += button.getText().getBounds()[1]*2;
			if(isVertical){
				if(button.getOption() != null){
					totalHeight += button.getOption().getBounds()[1]*2;
				}
			}else{
				if(biggestX == null ||button.getText().getBounds()[0] < biggestX.getBounds()[0])biggestX =button.getText();
			}
		}
		int[] tempPos = new int[]{pos[0]+size[0]/2,pos[1]+size[1]/2+totalHeight/2};
		
		for(Button button: buttonList){
			tempPos[1] -= button.getText().getBounds()[1];
			int count = 0;
			if(isVertical){
				button.getText().getPos()[0] = tempPos[0]-button.getText().getBounds()[0]/2;
				button.getText().getPos()[1] = tempPos[1];
				tempPos[1] -= button.getText().getBounds()[1];
				if(button.hasOptions()){
					tempPos[1] -= button.getOption().getBounds()[1];
				}
				for(FontText option: button.getOptions()){
					option.getPos()[0] = tempPos[0]-option.getBounds()[0]/2;
					option.getPos()[1] = tempPos[1];
					button.getLeftArrows().get(count).getPos()[0] = option.getPos()[0]-33;
					button.getLeftArrows().get(count).getPos()[1] = option.getPos()[1];
					button.getRightArrows().get(count).getPos()[0] = option.getPos()[0]+option.getBounds()[0]+25;
					button.getRightArrows().get(count).getPos()[1] = option.getPos()[1];		
					count++;
				}
				if(button.hasOptions()){
					tempPos[1] -= button.getOption().getBounds()[1];
				}
			}else{
				for(FontText option: button.getOptions()){
					option.getPos()[0] = tempPos[0]-option.getBounds()[0]/2+biggestX.getBounds()[0]/2+80;
					option.getPos()[1] = tempPos[1];
					button.getLeftArrows().get(count).getPos()[0] = option.getPos()[0]-33;
					button.getLeftArrows().get(count).getPos()[1] = option.getPos()[1];
					button.getRightArrows().get(count).getPos()[0] = option.getPos()[0]+option.getBounds()[0]+25;
					button.getRightArrows().get(count).getPos()[1] = option.getPos()[1];
					count++;
				}
				if(count != 0){
					button.getText().getPos()[0] = tempPos[0]-button.getText().getBounds()[0]/2-80;
	 				button.getText().getPos()[1] = tempPos[1];
				}else{
					button.getText().getPos()[0] = tempPos[0]-button.getText().getBounds()[0]/2;
					button.getText().getPos()[1] = tempPos[1];
				}
				tempPos[1] -= button.getText().getBounds()[1]*1.25;
			}
		}
	}
	
	public void reload(){
		for(Button butt: buttonList){
			butt.reload();
		}
		if(reSize != null){
			setMidOfBounds(reSize,rePos);
		}
	}
}
