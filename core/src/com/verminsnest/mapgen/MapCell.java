package com.verminsnest.mapgen;


import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MapCell {
	private ArrayList<TextureRegion> layers;
	private boolean isWalkable;
	private int xPos;
	private int yPos;
	private CellEntity[][] grid;
	private int gridSize;
	
	public MapCell(TextureRegion firstLayer, int xPos, int yPos){
		layers = new ArrayList<TextureRegion>();
		layers.add(firstLayer);
		setxPos(xPos);
		setyPos(yPos);
		gridSize = 12;
		grid = new CellEntity[gridSize][gridSize];
		for(int y = 0; y <gridSize; y++){
			for(int x = 0; x <gridSize; x++){
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

	public int getGridSize() {
		return gridSize;
	}

	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}
}
