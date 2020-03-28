package com.verminsnest.core;


import com.verminsnest.entities.Entity;
import com.verminsnest.mapgen.MapCell;

public class EntityMovementSystem {
	
	private MapCell[][] map;
	
	public EntityMovementSystem(MapCell[][] map){
		this.map = map;
	}
	
	public void setPos(Entity entity){
		int mapPosXStart = (int) Math.floor(entity.getPos()[0]/128);
		int mapPosYStart = (int) Math.floor((entity.getPos()[1])/128);
		
		int gridPosXStart = (int) Math.floor((128 - entity.getPos()[0]%128)/18);
		int gridPosYStart = (int) Math.floor((128 - entity.getPos()[1]%127)/18);
		int gridPosXEnd = (int) Math.floor((128 - (entity.getPos()[0]+entity.getSize()[0])%128)/18);
		int gridPosYEnd = (int) Math.floor((128 - (entity.getPos()[1]+entity.getSize()[1])%128)/18);
		
		for(int y = gridPosYStart; y <=gridPosYEnd;y++){
			for(int x = gridPosXStart; x <=gridPosXEnd;x++){
				map[mapPosXStart][mapPosYStart].getGrid()[x][y].setEntity(entity.getId());;
			}
		}
		
	}
	public boolean moveTop(Entity entity, int speed){
		int mapPosXStart = (int) Math.floor(entity.getPos()[0]/128);
		int mapPosXEnd = (int) Math.floor((entity.getPos()[0]+entity.getSize()[0])/128);
		for(int i = 1; i<=speed; i++){
			int refMapPosYEnd = (int) Math.floor((entity.getPos()[1]+entity.getSize()[1]+i)/128);
			if(!map[mapPosXStart][refMapPosYEnd].isWalkable() || !map[mapPosXEnd][refMapPosYEnd].isWalkable()){
				return false;
			}else{
				//TODO If no wall is detected check for other entities				
				entity.getPos()[1] += 1;
			}
		}
		return true;
	}
	
	public boolean moveDown(Entity entity, int speed){
		int mapPosXStart = (int) Math.floor(entity.getPos()[0]/128);
		int mapPosXEnd = (int) Math.floor((entity.getPos()[0]+entity.getSize()[0])/128);
		for(int i = 1; i<=speed; i++){
			int refMapPosYStart = (int) Math.floor((entity.getPos()[1]-i)/128);
			if(!map[mapPosXStart][refMapPosYStart].isWalkable() || !map[mapPosXEnd][refMapPosYStart].isWalkable()){
				return false;
			}else{
				//TODO If no wall is detected check for other entities				
				entity.getPos()[1] -= 1;
			}
		}
		return true;
	}
	
	public boolean moveLeft(Entity entity, int speed){
		int mapPosYStart = (int) Math.floor(entity.getPos()[1]/128);
		int mapPosYEnd = (int) Math.floor((entity.getPos()[1]+entity.getSize()[1])/128);
		for(int i = 1; i<=speed; i++){
			int refMapPosXStart = (int) Math.floor((entity.getPos()[0]-i)/128);
			if(!map[refMapPosXStart][mapPosYStart].isWalkable() || !map[refMapPosXStart][mapPosYEnd].isWalkable()){
				return false;
			}else{
				//TODO If no wall is detected check for other entities				
				entity.getPos()[0] -= 1;
			}
		}
		return true;
	}
	
	public boolean moveRight(Entity entity, int speed){
		int mapPosYStart = (int) Math.floor(entity.getPos()[1]/128);
		int mapPosYEnd = (int) Math.floor((entity.getPos()[1]+entity.getSize()[1])/128);
		for(int i = 1; i<=speed; i++){
			int refMapPosXEnd = (int) Math.floor((entity.getPos()[0]+entity.getSize()[0]+i)/128);
			if(!map[refMapPosXEnd][mapPosYStart].isWalkable() || !map[refMapPosXEnd][mapPosYEnd].isWalkable()){
				return false;
			}else{
				//TODO If no wall is detected check for other entities				
				entity.getPos()[0] += 1;
			}
		}
		return true;
	}
//	public boolean setOnPos(int xRest, int yRest, int width, int height, String entity){
//		int xInvert = 127 - xRest;
//		int yInvert = 127 - xRest;
//		int xStart = (xInvert+xInvert%8)/8;
//		int yStart = (yInvert+yInvert%8)/8;
//		int xEnd = xInvert+width;
//		if(xEnd>127) xEnd = 127;
//		int yEnd = yInvert+height;
//		if(yEnd>127) yEnd = 127;
//		
//		for(int y = 0; y < grid[0].length; y++){
//			for(int x = 0; x < grid.length; x++){
//				if(x >= xStart && x <= xEnd && y <= yEnd && y>= yStart){
//					if(!grid[x][y].getEntity().equals(entity) && !grid[x][y].getEntity().equals(null)){
//						return false;
//					}else{
//						grid[x][y].setEntity(entity);
//					}
//				}else{
//					if(grid[x][y].getEntity().equals(entity)){
//						grid[x][y].setEntity(null);
//					}
//				}
//			}
//		}
//		return true;
//	}
}
