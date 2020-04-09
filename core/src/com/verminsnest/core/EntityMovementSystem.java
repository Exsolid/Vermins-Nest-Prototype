package com.verminsnest.core;


import com.verminsnest.entities.Entity;
import com.verminsnest.mapgen.MapCell;
import com.verminsnest.singletons.RuntimeData;

public class EntityMovementSystem {
	
	private MapCell[][] map;
	
	public EntityMovementSystem(MapCell[][] map){
		this.map = map;
	}
	
	public boolean moveTop(Entity entity, int speed){
		int mapPosXStart = (int) Math.floor(entity.getPos()[0]/128);
		int mapPosXEnd = (int) Math.floor((entity.getPos()[0]+entity.getSize()[0])/128);
		for(int i = 1; i<=speed; i++){
			int refMapPosYEnd = (int) Math.floor((entity.getPos()[1]+entity.getSize()[1]+1)/128);
			if(!map[mapPosXStart][refMapPosYEnd].isWalkable() || !map[mapPosXEnd][refMapPosYEnd].isWalkable()){
				return false;
			}else{
				int y = entity.getPos()[1]+entity.getSize()[1]+1;
				for(Entity refEnt: RuntimeData.getInstance().getEntities()){
					if(!entity.equals(refEnt)){
						for(int x = entity.getPos()[0]; x <= entity.getPos()[0]+entity.getSize()[0]; x++){
							if(x <= refEnt.getPos()[0]+refEnt.getSize()[0] && x >= refEnt.getPos()[0] && y <= refEnt.getPos()[1]+refEnt.getSize()[1] && y >= refEnt.getPos()[1]){
								return false;
							}
						}
					}
				}
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
				int y = entity.getPos()[1]-1;
				for(Entity refEnt: RuntimeData.getInstance().getEntities()){
					if(!entity.equals(refEnt)){
						for(int x = entity.getPos()[0]; x <= entity.getPos()[0]+entity.getSize()[0]; x++){
							if(x <= refEnt.getPos()[0]+refEnt.getSize()[0] && x >= refEnt.getPos()[0] && y <= refEnt.getPos()[1]+refEnt.getSize()[1] && y >= refEnt.getPos()[1]){
								return false;
							}
						}
					}
				}
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
				int x = entity.getPos()[0]-1;
				for(Entity refEnt: RuntimeData.getInstance().getEntities()){
					if(!entity.equals(refEnt)){
						for(int y = entity.getPos()[1]; y <= entity.getPos()[1]+entity.getSize()[1]; y++){
							if(x <= refEnt.getPos()[0]+refEnt.getSize()[0] && x >= refEnt.getPos()[0] && y <= refEnt.getPos()[1]+refEnt.getSize()[1] && y >= refEnt.getPos()[1]){
								return false;
							}
						}
					}
				}
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
				int x = entity.getPos()[0]+entity.getSize()[0]+1;
				for(Entity refEnt: RuntimeData.getInstance().getEntities()){
					if(!entity.equals(refEnt)){
						for(int y = entity.getPos()[1]; y <= entity.getPos()[1]+entity.getSize()[1]; y++){
							if(x <= refEnt.getPos()[0]+refEnt.getSize()[0] && x >= refEnt.getPos()[0] && y <= refEnt.getPos()[1]+refEnt.getSize()[1] && y >= refEnt.getPos()[1]){
								return false;
							}
						}
					}
				}
					entity.getPos()[0] += 1;
			}
		}
		return true;
	}
}
