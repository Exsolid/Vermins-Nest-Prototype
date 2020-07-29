package com.verminsnest.misc.gui;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.verminsnest.core.management.data.RuntimeData;

public class FontText {
	private int[] sizes;
	private BitmapFont font;
	private FreeTypeFontParameter fontPara;
	private FreeTypeFontGenerator fontGen;
	private ArrayList<String> text;
	private int[] pos;
	
	private String allChars;
	
	private long blinkStartTime;
	
	private String qualifier;
	public FontText(String text,int size, boolean isQualifier){
		this.sizes = new int[2];
		this.pos = new int[2];
		this.text = new ArrayList<String>();
		
		
		fontPara = new FreeTypeFontParameter();
		fontGen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/saratogajean+.ttf"));
		fontPara.color = Color.WHITE;
		fontPara.size = size;		
		font = fontGen.generateFont(fontPara);
		font.setColor(Color.valueOf("424242"));
		
		if(!isQualifier){
			allChars = text;
			this.setText(text);
		}else{
			qualifier = text;
			allChars = RuntimeData.getInstance().getGame().getConfig().getMessage(text);
			this.setText(RuntimeData.getInstance().getGame().getConfig().getMessage(text));
		}
		
		blinkStartTime = -1;	
		
	}
	
	public void reload(){
		if(qualifier == null) return;
		this.setText(RuntimeData.getInstance().getGame().getConfig().getMessage(qualifier));
	}
	
	public void setText(String text){
		allChars = text;
		this.text.clear();
		char[] chars = text.toCharArray();
		int count = 0;
		for(char c: chars){
			if(this.text.size()-1<count){
				this.text.add(String.valueOf(c));
			}else if(c == '\n'){
				count++;
			}else{
				this.text.set(count, this.text.get(count).concat(String.valueOf(c)));
			}
		}
		GlyphLayout layout = new GlyphLayout();
		int height = 0;
		int width = 0;
		
		layout.setText(font, this.text.get(0));
		height = (int) layout.height*this.text.size();
		for(String str: this.text){
			layout.setText(font, str);
			if(layout.width > width) width = (int) layout.width;
		}

		this.sizes[0] = width;
		this.sizes[1] = height;
	}
	
	public void setMidOfBounds(int[] pos, int[] size){
		this.pos[0] = pos[0]+size[0]/2-sizes[0]/2;
		this.pos[1] = pos[1]+size[1]/2-sizes[1]/2;
	}
	
	public void setPos(int[] pos){
		this.pos = new int[] {pos[0],pos[1]};
	}
	
	public int[] getPos(){
		return pos;
	}
	
	public void switchColor(boolean activ){
		if(activ){
			font.setColor(Color.WHITE);
			font.setColor(Color.valueOf("838383"));		
		}else{
			font.setColor(Color.WHITE);
			font.setColor(Color.valueOf("424242"));	
		}
	}
		
	public void dispose(){
		fontGen.dispose();
		font.dispose();
	}
	
	public void draw(SpriteBatch batch){
		int step = (int) (sizes[1]/text.size()*2);
		int currentHeight;
		if(text.size() > 1){
			currentHeight = pos[1]+sizes[1];
		}else{
			currentHeight = pos[1];
		}
		for(String str: text){
			font.draw(batch, str,pos[0],currentHeight);
			currentHeight -= step;
		}
		if(blinkStartTime != -1 && blinkStartTime+250 <=System.currentTimeMillis()){
			switchColor(false);
			blinkStartTime = -1;
		}
	}
	
	public void blink(){
		blinkStartTime = System.currentTimeMillis();
		switchColor(true);
	}
	
	public int getSize() {
		return fontPara.size;
	}
	
	public int[] getBounds(){
		return sizes;
	}
	
	public String getText(){
		return allChars;
	}
}
