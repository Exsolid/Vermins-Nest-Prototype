package com.verminsnest.misc;

import java.awt.Point;

import com.badlogic.gdx.graphics.Texture;

public class Button {
	private Texture defaultImg;
	private Texture activImg;
	private boolean isActiv;
	private Point pos;
	
	public Button(String defaultImgPath, String activImgPath){
		this.defaultImg = new Texture(defaultImgPath);
		this.activImg = new Texture(activImgPath);
		this.setActiv(false);
	}
	
	public Texture getCurrent(){
		if(isActiv){
			return activImg;
		}else
		{
			return defaultImg;
		}
	}
	
	public boolean isActiv() {
		return isActiv;
	}

	public void setActiv(boolean isActiv) {
		this.isActiv = isActiv;
	}

	public Point getPos() {
		return pos;
	}

	public void setPos(Point pos) {
		this.pos = pos;
	}
	
}
