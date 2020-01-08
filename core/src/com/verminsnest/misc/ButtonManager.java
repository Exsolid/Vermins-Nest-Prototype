package com.verminsnest.misc;

import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.verminsnest.exceptions.OutOfBounds;

public class ButtonManager {
	
	private String name;
	//All Buttons
	private ArrayList<Button> buttonList;
	private int index;
	private String leftOption;
	private String rightOption;
	
	//Font generations
	private FreeTypeFontGenerator fontGen;
	private BitmapFont font;
	private BitmapFont opFont;
	private FreeTypeFontParameter fontPara;
	private String activColor;
	private String passivColor;
	private int size;
	
	//Create and dispose
	public ButtonManager(ArrayList<Button> buttonList, String name){
		this.buttonList = buttonList;
		this.name = name;
		this.buttonList.get(0).setActiv(true);
		leftOption = "<";
		rightOption = ">";
		
		fontPara = new FreeTypeFontParameter();
		fontGen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/saratogajean.ttf"));
		size = 50;
		fontPara.size = size;
		activColor = "838383";
		passivColor = "424242";
		font = fontGen.generateFont(fontPara);
		fontPara.size = size/2;
		opFont = fontGen.generateFont(fontPara);	
		opFont.setColor(Color.valueOf(passivColor));
	}
	
	public String getName() {
		return name;
	}

	public void dispose(){
		font.dispose();
		opFont.dispose();
		fontGen.dispose();
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
	
	public void activate(){
		buttonList.get(index).setActiv(true);
	}
	
	public void deactivate(){
		buttonList.get(index).setActiv(false);
	}
	
	//Drawing
	public void setSize(int size){
		font.dispose();
		fontPara.size = size;
		font = fontGen.generateFont(fontPara);
		fontPara.size = size/2;
		opFont = fontGen.generateFont(fontPara);
		opFont.setColor(Color.valueOf(passivColor));
		this.size = size;
	}

	public void setColors(String passiv, String activ){
		activColor = activ;
		passivColor = passiv;
	}
	
	public void draw(SpriteBatch batch) {
		for (int i = 0; i < buttonList.size(); i++) {
			if (buttonList.get(i).isActiv())
				font.setColor(Color.valueOf(activColor));
			else
				font.setColor(Color.valueOf(passivColor));
			font.draw(batch, buttonList.get(i).getText(), buttonList.get(i).getTitleSpecs()[0], buttonList.get(i).getTitleSpecs()[1]);
			if (buttonList.get(i).hasOptions()) {
				opFont.draw(batch, buttonList.get(i).getOption(), buttonList.get(i).getOptionSpecs()[0],
						buttonList.get(i).getOptionSpecs()[1]);
				if (buttonList.get(i).isActiv()) {
					if (buttonList.get(i).isActiv())
						opFont.setColor(Color.valueOf(activColor));
					else
						opFont.setColor(Color.valueOf(passivColor));					
					opFont.draw(batch, leftOption, buttonList.get(i).getOptionSpecs()[0] - 50,
							buttonList.get(i).getOptionSpecs()[1]);
					opFont.draw(batch, rightOption, buttonList.get(i).getOptionSpecs()[0] + buttonList.get(i).getOptionSpecs()[2] + 40,
							buttonList.get(i).getOptionSpecs()[1]);
					opFont.setColor(Color.valueOf(passivColor));
				}
			}
		}
	}
	
	public void calcMidofBounds(int width, int height, Point pos) throws OutOfBounds{
		//Calculate total height
		int totalHeight = 0;
		GlyphLayout layout = new GlyphLayout();
		layout.setText(font, buttonList.get(0).getText());
		for(int i = 0; i < buttonList.size(); i++){
			totalHeight += layout.height;
			if(buttonList.get(i).hasOptions())totalHeight += layout.height/3;
			if(i != buttonList.size()-1){
				if(buttonList.get(i).hasOptions())totalHeight += layout.height/1.5;
				else totalHeight += layout.height/3;
			}
			if(totalHeight > height) throw new OutOfBounds(buttonList.get(i));
		}
		
		//Set Button and options height and Width
		Point tempPos = new Point();
		int heightToBegin = totalHeight/2;
		for(int i = 0; i < buttonList.size(); i++){
			layout.setText(font, buttonList.get(i).getText());
			tempPos.x = (int) (pos.x + width/2 - layout.width/2);
			tempPos.y = (int) (pos.y + height/2 + totalHeight - heightToBegin);
			buttonList.get(i).setTitlePos(tempPos);
			buttonList.get(i).setTitleSize(new Point((int) layout.width,(int) layout.height));
			
			if(buttonList.get(i).hasOptions()){
				totalHeight -= layout.height + layout.height/3;
				layout.setText(opFont, buttonList.get(i).getOption());
				
				tempPos.x = (int) (pos.x + width/2 - layout.width/2);
				tempPos.y = (int) (pos.y + height/2 + totalHeight - heightToBegin - layout.height/2);
				buttonList.get(i).setOptionsPos(tempPos);
				buttonList.get(i).setOptionsSize(new Point((int) layout.width,(int) layout.height));
				
				layout.setText(font, buttonList.get(i).getText());
				totalHeight -= layout.height + layout.height/1.5;
			}else totalHeight -= layout.height + layout.height/3;
		}
	}
	
	public void reCalcOptions(int[] oldSpecs, Button button){
		GlyphLayout layout = new GlyphLayout();		
		layout.setText(opFont, button.getOption());
		
		button.setOptionsPos(new Point((int) ((oldSpecs[2]-layout.width)/2)+oldSpecs[0],oldSpecs[1]));
		button.setOptionsSize(new Point((int) layout.width,(int) layout.height));
	}
}
