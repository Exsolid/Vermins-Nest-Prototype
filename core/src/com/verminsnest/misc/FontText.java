package com.verminsnest.misc;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class FontText {
	private int[] sizes;
	private BitmapFont font;
	private FreeTypeFontParameter fontPara;
	private FreeTypeFontGenerator fontGen;
	private ArrayList<String> text;
	private int[] pos;
	
	public FontText(String text,int size){
		this.sizes = new int[2];
		this.pos = new int[2];
		this.text = new ArrayList<String>();
		
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
		fontPara = new FreeTypeFontParameter();
		fontGen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/saratogajean+.ttf"));
		fontPara.color = Color.valueOf("424242");
		fontPara.size = size;		
		font = fontGen.generateFont(fontPara);
	}
	
	public void setText(String text){
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
	}
	
	public void setMidOfBounds(int[] pos, int[] size){
		GlyphLayout layout = new GlyphLayout();
		int height = 0;
		int width = 0;
		
		layout.setText(font, text.get(0));
		height = (int) (layout.height*text.size()*2);
		for(String str: text){
			layout.setText(font, str);
			if(layout.width > width) width = (int) layout.width;
		}
		
		this.pos[1] = pos[1]+size[1]/2-height/2;
		this.pos[0] = pos[0]+size[0]/2-width/2;
		this.sizes[0] = width;
		this.sizes[1] = height;
	}
	
	public void dispose(){
		fontGen.dispose();
		font.dispose();
	}
	public void draw(SpriteBatch batch){
		int step = sizes[1]/text.size();
		int currentHeight = pos[1];
		for(String str: text){
			font.draw(batch, str,pos[0],currentHeight);
			currentHeight -= step;
		}
	}
}
