package com.verminsnest.world.generation.map;


import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MapCell {
	private ArrayList<TextureRegion> layers;
	private boolean isWalkable;
	private int xPos;
	private int yPos;
	private int obstacleCount;
	
	private String[][] walkableGrid;
	
	public MapCell(TextureRegion firstLayer, int xPos, int yPos){
		layers = new ArrayList<TextureRegion>();
		layers.add(firstLayer);
		setxPos(xPos);
		setyPos(yPos);
		setWalkable(true);
		setWalkableGrid(new String[8][8]);
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
	
	/**
	 * Checks if UtilEntities are within this MapCell, which aren't passable at all
	 * @return
	 */
	public boolean containsUnpassableUtilEntities(){
		return containsObstacleUtilEntities();
	}
	
	/**
	 * Checks if UtilEntities are within this MapCell, which aren't passable without flight
	 * @return
	 */
	public boolean containsObstacleUtilEntities(){
		return obstacleCount != 0;
	}
	
	public void setWalkable(boolean isWalkable) {
		this.isWalkable = isWalkable;
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

	public int getObstacleCount() {
		return obstacleCount;
	}

	public void editObstacleCount(int obstacleCount) {
		this.obstacleCount += obstacleCount;
	}

	public String[][] getWalkableGrid() {
		return walkableGrid;
	}

	public void setWalkableGrid(String[][] walkableGrid) {
		this.walkableGrid = walkableGrid;
	}
	
	public void updateWalkableGrid(String id, int[] pos, int[] size){
		int xGridPos = -1;
		int yGridPos = -1;
		if(Math.floor(pos[0]/128) < xPos){
			xGridPos = 0;
		}else if(Math.floor(pos[0]/128) > xPos){
			xGridPos = 7;
		}else{
			xGridPos = (int) Math.floor((pos[0] - xPos*128)/8);
		}
		if(Math.floor(pos[1]/128) < yPos){
			yGridPos = 0;
		}else if(Math.floor(pos[1]/128) > yPos){
			yGridPos = 7;
		}else{
			yGridPos = (int) Math.floor((pos[1] - yPos*128)/8);
		}
		
		int xGridSizePos = (int) Math.floor((pos[0]+size[0] - xPos*128)/8);
		int yGridSizePos = (int) Math.floor((pos[1]+size[0] - yPos*128)/8);
		if(xGridSizePos > 128)xGridSizePos = 7;
		else xGridSizePos = (int) Math.floor(xGridSizePos/8);
		if(yGridSizePos > 128)yGridSizePos = 7;
		else yGridSizePos = (int) Math.floor(yGridSizePos/8);
		for(int y = 0; y < 8; y++){
			for(int x = 0; x < 8; x++){
				if(walkableGrid != null && walkableGrid[x][y] != null && walkableGrid[x][y].equals(id))walkableGrid[x][y] = null;
				if(x >= xGridPos && x<= xGridSizePos
						&& y >= yGridPos && y<= yGridSizePos)walkableGrid[x][y] = id;
			}
		}
	}
}
