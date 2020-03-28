package com.verminsnest.mapgen;


import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MapCell {
	private ArrayList<TextureRegion> layers;
	private boolean isWalkable;
	private int xPos;
	private int yPos;
	private CellEntity[][] grid;
	
	public MapCell(TextureRegion firstLayer, int xPos, int yPos){
		layers = new ArrayList<TextureRegion>();
		layers.add(firstLayer);
		setxPos(xPos);
		setyPos(yPos);
		grid = new CellEntity[8][8];
		for(int y = 0; y <8; y++){
			for(int x = 0; x <8; x++){
				grid[x][y] = new CellEntity();
			}
		}
		setWalkable(true);
	}
	
	public void addLayer(TextureRegion layer){
		layers.add(layer);
	}
	
	public ArrayList<TextureRegion> getLayers(){
		return layers;
	}
	public boolean isWalkable() {
		return isWalkable;
	}

	public void setWalkable(boolean isWalkable) {
		this.isWalkable = isWalkable;
	}
	
	public CellEntity[][] getGrid(){
		return grid;
	}
	
	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
}
