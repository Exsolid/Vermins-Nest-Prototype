package com.verminsnest.core.engine;


import java.util.ArrayList;

import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.core.management.ids.Indentifiers;
import com.verminsnest.entities.Entity;
import com.verminsnest.entities.enemies.Enemy;
import com.verminsnest.entities.explosions.Explosion;
import com.verminsnest.entities.items.Food;
import com.verminsnest.entities.playables.Playable;
import com.verminsnest.entities.projectiles.Projectile;
import com.verminsnest.entities.util.UtilEntity;
import com.verminsnest.world.generation.map.AStarMapList;
import com.verminsnest.world.generation.map.AStarMapNode;
import com.verminsnest.world.generation.map.MapCell;

public class EntityMovementSystem {
	
	private MapCell[][] map;
	
	private ArrayList<AStarMapNode> openNodes;
	private ArrayList<AStarMapList> openLists;
	private ArrayList<AStarMapNode> closedNodes;
	private AStarMapNode[][] nodeMap;
	
	public EntityMovementSystem(MapCell[][] map){
		this.map = map;
		this.openNodes = new ArrayList<>();
		openLists = new ArrayList<>();
		this.closedNodes = new ArrayList<>();
	}
	
	public boolean moveTop(Entity entity, int speed, int[] goalPos){
		int mapPosXStart = (int) Math.floor(entity.getPos()[0]/128);
		int mapPosXEnd = (int) Math.floor((entity.getPos()[0]+entity.getHitbox()[0])/128);
		for(int i = 1; i<=speed; i++){
			int refMapPosYEnd = (int) Math.floor((entity.getPos()[1]+entity.getHitbox()[1]+1)/128);
			if(!map[mapPosXStart][refMapPosYEnd].isWalkable() || !map[mapPosXEnd][refMapPosYEnd].isWalkable()){
				return false;
			}else{
				int y = entity.getPos()[1]+entity.getHitbox()[1]+1;
				for(Entity refEnt: RuntimeData.getInstance().getEntityManager().getAllEntities()){
					if(!entity.equals(refEnt) && refEnt.isObstacle()){
						for(int x = entity.getPos()[0]; x <= entity.getPos()[0]+entity.getHitbox()[0]; x++){
							if(x <= refEnt.getPos()[0]+refEnt.getHitbox()[0] && x >= refEnt.getPos()[0] && y <= refEnt.getPos()[1]+refEnt.getHitbox()[1] && y >= refEnt.getPos()[1]){
								if(!checkEntities(entity,refEnt)){
									entity.setSearchAlternativPaths(true);
									return false;
								}
							}
						}
					}
				}
				entity.getPos()[1] += 1;
				updateTiles(entity);
			}
		}
		return true;
	}
	
	public boolean moveDown(Entity entity, int speed, int[] goalPos){
		int mapPosXStart = (int) Math.floor(entity.getPos()[0]/128);
		int mapPosXEnd = (int) Math.floor((entity.getPos()[0]+entity.getHitbox()[0])/128);
		for(int i = 1; i<=speed; i++){
			int refMapPosYStart = (int) Math.floor((entity.getPos()[1]-i)/128);
			if(!map[mapPosXStart][refMapPosYStart].isWalkable() || !map[mapPosXEnd][refMapPosYStart].isWalkable()){
				return false;
			}else{
				int y = entity.getPos()[1]-1;
				for(Entity refEnt: RuntimeData.getInstance().getEntityManager().getAllEntities()){
					if(!entity.equals(refEnt) && refEnt.isObstacle()){
						for(int x = entity.getPos()[0]; x <= entity.getPos()[0]+entity.getHitbox()[0]; x++){
							if(x <= refEnt.getPos()[0]+refEnt.getHitbox()[0] && x >= refEnt.getPos()[0] && y <= refEnt.getPos()[1]+refEnt.getHitbox()[1] && y >= refEnt.getPos()[1]){
								if(!checkEntities(entity,refEnt)){
									entity.setSearchAlternativPaths(true);
									return false;
								}
							}
						}
					}
				}
					entity.getPos()[1] -= 1;
					updateTiles(entity);
			}
		}
		return true;
	}
	
	public boolean moveLeft(Entity entity, int speed, int[] goalPos){
		int mapPosYStart = (int) Math.floor(entity.getPos()[1]/128);
		int mapPosYEnd = (int) Math.floor((entity.getPos()[1]+entity.getHitbox()[1])/128);
		for(int i = 1; i<=speed; i++){
			int refMapPosXStart = (int) Math.floor((entity.getPos()[0]-i)/128);
			if(!map[refMapPosXStart][mapPosYStart].isWalkable() || !map[refMapPosXStart][mapPosYEnd].isWalkable()){
				return false;
			}else{
				int x = entity.getPos()[0]-1;
				for(Entity refEnt: RuntimeData.getInstance().getEntityManager().getAllEntities()){
					if(!entity.equals(refEnt) && refEnt.isObstacle()){
						for(int y = entity.getPos()[1]; y <= entity.getPos()[1]+entity.getHitbox()[1]; y++){
							if(x <= refEnt.getPos()[0]+refEnt.getHitbox()[0] && x >= refEnt.getPos()[0] && y <= refEnt.getPos()[1]+refEnt.getHitbox()[1] && y >= refEnt.getPos()[1]){
								if(!checkEntities(entity,refEnt)){

									entity.setSearchAlternativPaths(true);
									return false;
								}
							}
						}
					}
				}
					entity.getPos()[0] -= 1;
					updateTiles(entity);
			}
		}
		return true;
	}
	
	public boolean moveRight(Entity entity, int speed, int[] goalPos){
		int mapPosYStart = (int) Math.floor(entity.getPos()[1]/128);
		int mapPosYEnd = (int) Math.floor((entity.getPos()[1]+entity.getHitbox()[1])/128);
		for(int i = 1; i<=speed; i++){
			int refMapPosXEnd = (int) Math.floor((entity.getPos()[0]+entity.getHitbox()[0]+i)/128);
			if(!map[refMapPosXEnd][mapPosYStart].isWalkable() || !map[refMapPosXEnd][mapPosYEnd].isWalkable()){
				return false;
			}else{
				int x = entity.getPos()[0]+entity.getHitbox()[0]+1;
				for(Entity refEnt: RuntimeData.getInstance().getEntityManager().getAllEntities()){
					if(!entity.equals(refEnt) && refEnt.isObstacle()){
						for(int y = entity.getPos()[1]; y <= entity.getPos()[1]+entity.getHitbox()[1]; y++){
							if(x <= refEnt.getPos()[0]+refEnt.getHitbox()[0] && x >= refEnt.getPos()[0] && y <= refEnt.getPos()[1]+refEnt.getHitbox()[1] && y >= refEnt.getPos()[1]){
								if(!checkEntities(entity,refEnt)){
									entity.setSearchAlternativPaths(true);
									return false;
								}
							}
						}
					}
				}
					entity.getPos()[0] += 1;
					updateTiles(entity);
			}
		}
		return true;
	}
	
	public boolean move(Entity entity, int[] speed){
		int mapPosYStart = (int) Math.floor(entity.getPos()[1]/128);
		int mapPosYEnd = (int) Math.floor((entity.getPos()[1]+entity.getHitbox()[1])/128);
		for(int i = 1; i<=Math.abs(speed[0]); i++){
			int refMapPosXEnd = (int) Math.floor((entity.getPos()[0]+entity.getHitbox()[0]+i)/128);
			if(!map[refMapPosXEnd][mapPosYStart].isWalkable() || !map[refMapPosXEnd][mapPosYEnd].isWalkable()){
				return false;
			}else{
				int x = entity.getPos()[0]+entity.getHitbox()[0]+1;
				for(Entity refEnt: RuntimeData.getInstance().getEntityManager().getAllEntities()){
					if(!entity.equals(refEnt) && refEnt.isObstacle()){
						for(int y = entity.getPos()[1]; y <= entity.getPos()[1]+entity.getHitbox()[1]; y++){
							if(x <= refEnt.getPos()[0]+refEnt.getHitbox()[0] && x >= refEnt.getPos()[0] && y <= refEnt.getPos()[1]+refEnt.getHitbox()[1] && y >= refEnt.getPos()[1]){
								if(!checkEntities(entity,refEnt)){
									return false;
								}
							}
						}
					}
				}
				if(speed[0] > 0){
					entity.getPos()[0] += 1;
				}else{
					entity.getPos()[0] -= 1;
				}
			}
		}
		int mapPosXStart = (int) Math.floor(entity.getPos()[0]/128);
		int mapPosXEnd = (int) Math.floor((entity.getPos()[0]+entity.getHitbox()[0])/128);
		for(int i = 1; i<=Math.abs(speed[1]); i++){
			int refMapPosYStart = (int) Math.floor((entity.getPos()[1]-i)/128);
			if(!map[mapPosXStart][refMapPosYStart].isWalkable() || !map[mapPosXEnd][refMapPosYStart].isWalkable()){
				return false;
			}else{
				int y = entity.getPos()[1]+entity.getHitbox()[1]+1;
				for(Entity refEnt: RuntimeData.getInstance().getEntityManager().getAllEntities()){
					if(!entity.equals(refEnt) && refEnt.isObstacle()){
						for(int x = entity.getPos()[0]; x <= entity.getPos()[0]+entity.getHitbox()[0]; x++){
							if(x <= refEnt.getPos()[0]+refEnt.getHitbox()[0] && x >= refEnt.getPos()[0] && y <= refEnt.getPos()[1]+refEnt.getHitbox()[1] && y >= refEnt.getPos()[1]){
								if(!checkEntities(entity,refEnt)){
									return false;
								}
							}
						}
					}
				}
				if(speed[1] > 0){
					entity.getPos()[1] += 1;
				}else{
					entity.getPos()[1] -= 1;
				}
			}
		}
		return true;
	}
	
	private boolean checkEntities(Entity entity, Entity refEnt){
		if(!refEnt.isObstacle() || !entity.isObstacle())return true;
		if(entity instanceof Projectile || entity instanceof Explosion){
				if(((Projectile)entity).isFriendly() && !(refEnt instanceof Playable) && !(refEnt instanceof Food)
					|| !((Projectile)entity).isFriendly() && refEnt instanceof Playable){
				RuntimeData.getInstance().getEntityDamageSystem().addHit(entity, refEnt);
			}else{
				return true;
			}
		}else if(refEnt instanceof Food && !((Food)refEnt).isPicked()) {
			if(entity instanceof Playable) {
				((Food)refEnt).setPicked(true);
				((Playable)entity).getInventory().setFoodCount(((Playable)entity).getInventory().getFoodCount()+1);
				RuntimeData.getInstance().getEntityManager().removeEntity(refEnt);
			}
		}else if(entity instanceof Playable && refEnt instanceof Projectile && ((Projectile)refEnt).isFriendly()
				|| entity instanceof Enemy && refEnt instanceof Projectile && !((Projectile)refEnt).isFriendly()){
			return true;
		}else if(!entity.caresObstacles() && (refEnt instanceof UtilEntity || refEnt instanceof Enemy)){
			return true;
		}
		else{
			return false;
		}
		return true;
	}
	
	private int findPathing(Entity source, int[] goalPos) {
		//Positions and AStar mapping
		int[] newGoalPos = new int[]{(goalPos[0]-goalPos[0]%128)/128,(goalPos[1]-goalPos[1]%128)/128};
		
		int[] sourcePos = new int[] {(source.getPos()[0]-source.getPos()[0]%128)/128,(source.getPos()[1]-source.getPos()[1]%128)/128};
		nodeMap = RuntimeData.getInstance().getMapData().getAStarMap(newGoalPos, sourcePos);
		
		int[] currentPos = new int[] {sourcePos[0]-nodeMap[0][0].getMapPos()[0],sourcePos[1]-nodeMap[0][0].getMapPos()[1]};
		openNodes.add(nodeMap[currentPos[0]][currentPos[1]]);
		
		//Calculate the best direction
		int defDir = calculatePathing(source, goalPos);
		int[] currentShiftedPos = null;
		
		//If the entity is not on a single tile, check both tiles
		switch(defDir) {
		case Indentifiers.DIRECTION_EAST:
		case Indentifiers.DIRECTION_WEST:
			sourcePos = new int[] {(source.getPos()[0]-source.getPos()[0]%128)/128,(source.getPos()[1]+source.getHitbox()[1]-((source.getPos()[1]+source.getHitbox()[1])%128))/128};
			currentShiftedPos = new int[] {sourcePos[0]-nodeMap[0][0].getMapPos()[0],sourcePos[1]-nodeMap[0][0].getMapPos()[1]};
			if(currentShiftedPos[0] == currentPos[1])return defDir;
			else {
				nodeMap = RuntimeData.getInstance().getMapData().getAStarMap(newGoalPos, sourcePos);
				currentShiftedPos = new int[] {sourcePos[0]-nodeMap[0][0].getMapPos()[0],sourcePos[1]-nodeMap[0][0].getMapPos()[1]};
				openNodes.add(nodeMap[currentShiftedPos[0]][currentShiftedPos[1]]);

				return calculatePathing(source, goalPos);
			}
		case Indentifiers.DIRECTION_NORTH:
		case Indentifiers.DIRECTION_SOUTH:
			sourcePos = new int[] {(source.getPos()[0]+source.getHitbox()[0]-((source.getPos()[0]+source.getHitbox()[0])%128))/128,(source.getPos()[1]-source.getPos()[1]%128)/128};
			currentShiftedPos = new int[] {sourcePos[0]-nodeMap[0][0].getMapPos()[0],sourcePos[1]-nodeMap[0][0].getMapPos()[1]};
			if(currentShiftedPos[0] == currentPos[1])return defDir;
			else {
				nodeMap = RuntimeData.getInstance().getMapData().getAStarMap(newGoalPos, sourcePos);
				currentShiftedPos = new int[] {sourcePos[0]-nodeMap[0][0].getMapPos()[0],sourcePos[1]-nodeMap[0][0].getMapPos()[1]};
				openNodes.add(nodeMap[currentShiftedPos[0]][currentShiftedPos[1]]);

				return calculatePathing(source, goalPos);
			}
		default:
			//If no solution was found, return -1 (closed room)
			if(sourcePos[0] != newGoalPos[0] || sourcePos[1] != newGoalPos[1])return -1;
			else{
				nodeMap = RuntimeData.getInstance().getMapData().getDetailedAStarMap(source, goalPos);
				initOpenLists(source.getHitbox(), goalPos, source.getPos());
				return calculateDetailedDir(source,source.getSize(),source.getHitbox());
			}
		}
	}

	private int calculatePathing(Entity entity, int[] goalPos) {
		MapCell[][] map = RuntimeData.getInstance().getMapData().getData();
		while (!openNodes.isEmpty()) {
			// Find best choice
			AStarMapNode best = null;
			for (AStarMapNode ref : openNodes) {
				if (best == null || best.getWeight() > ref.getWeight()
						|| best.getWeight() == ref.getWeight() && best.getDistSource() > ref.getDistSource()) {
					best = ref;
				}
			}
			// Return source direction if the goal was found
			if (best.getDistGoal() == 0 || entity.isSearchAlternativPaths()) {
				openNodes.clear();
				closedNodes.clear();
				if(best.getDistSource() < 1){
					entity.setSearchAlternativPaths(false);
					nodeMap = RuntimeData.getInstance().getMapData().getDetailedAStarMap(entity, goalPos);
					initOpenLists(entity.getHitbox(), goalPos, entity.getPos());
					return calculateDetailedDir(entity,entity.getSize(),entity.getHitbox());
				}else{
					return best.getSourceDir();
				}
			}
			openNodes.remove(best);
			closedNodes.add(best);
			// Evaluate neighbors if not checked before and is walkable
			// West(Left)
			if (best.getNodePos()[0] - 1 >= 0) {
				AStarMapNode neighbor = nodeMap[best.getNodePos()[0] - 1][best.getNodePos()[1]];
				if (!closedNodes.contains(neighbor)
						&& map[neighbor.getMapPos()[0]][neighbor.getMapPos()[1]].isWalkable()) {
					// Set new 'walked' distance
					int refDist = best.getDistSource() + 1;

					if (openNodes.contains(neighbor)) {
						if (refDist > neighbor.getWeight()) {
							// Set new data
							neighbor.setSourceDist(refDist);
							if (best.getSourceDir() == -1) {
								// Set source direction to return
								neighbor.setSourceDir(Indentifiers.DIRECTION_WEST);
							} else {
								neighbor.setSourceDir(best.getSourceDir());
							}
						}
					} else {
						// Add new data
						neighbor.setSourceDist(refDist);
						if (best.getSourceDir() == -1) {
							// Set source direction to return
							neighbor.setSourceDir(Indentifiers.DIRECTION_WEST);
						} else {
							neighbor.setSourceDir(best.getSourceDir());
						}
						openNodes.add(neighbor);
					}
				}
			}
			// East(Right)
			if (best.getNodePos()[0] + 1 < nodeMap.length) {
				AStarMapNode neighbor = nodeMap[best.getNodePos()[0] + 1][best.getNodePos()[1]];
				if (!closedNodes.contains(neighbor)
						&& map[neighbor.getMapPos()[0]][neighbor.getMapPos()[1]].isWalkable()) {
					// Set new 'walked' distance
					int refDist = best.getDistSource() + 1;

					if (openNodes.contains(neighbor)) {
						if (refDist > neighbor.getWeight()) {
							// Set new data
							neighbor.setSourceDist(refDist);
							if (best.getSourceDir() == -1) {
								// Set source direction to return
								neighbor.setSourceDir(Indentifiers.DIRECTION_EAST);
							} else {
								neighbor.setSourceDir(best.getSourceDir());
							}
						}
					} else {
						// Add new data
						neighbor.setSourceDist(refDist);
						if (best.getSourceDir() == -1) {
							// Set source direction to return
							neighbor.setSourceDir(Indentifiers.DIRECTION_EAST);
						} else {
							neighbor.setSourceDir(best.getSourceDir());
						}
						openNodes.add(neighbor);
					}
				}
			}
			// North(Top)
			if (best.getNodePos()[1] + 1 < nodeMap[0].length) {
				AStarMapNode neighbor = nodeMap[best.getNodePos()[0]][best.getNodePos()[1] + 1];
				if (!closedNodes.contains(neighbor)
						&& map[neighbor.getMapPos()[0]][neighbor.getMapPos()[1]].isWalkable()) {
					// Set new 'walked' distance
					int refDist = best.getDistSource() + 1;

					if (openNodes.contains(neighbor)) {
						if (refDist > neighbor.getWeight()) {
							// Set new data
							neighbor.setSourceDist(refDist);
							if (best.getSourceDir() == -1) {
								// Set source direction to return
								neighbor.setSourceDir(Indentifiers.DIRECTION_NORTH);
							} else {
								neighbor.setSourceDir(best.getSourceDir());
							}
						}
					} else {
						// Add new data
						neighbor.setSourceDist(refDist);
						if (best.getSourceDir() == -1) {
							// Set source direction to return
							neighbor.setSourceDir(Indentifiers.DIRECTION_NORTH);
						} else {
							neighbor.setSourceDir(best.getSourceDir());
						}
						openNodes.add(neighbor);
					}
				}
			}
			// South(Down)
			if (best.getNodePos()[1] - 1 >= 0) {
				AStarMapNode neighbor = nodeMap[best.getNodePos()[0]][best.getNodePos()[1] - 1];
				if (!closedNodes.contains(neighbor)
						&& map[neighbor.getMapPos()[0]][neighbor.getMapPos()[1]].isWalkable()) {
					// Set new 'walked' distance
					int refDist = best.getDistSource() + 1;

					if (openNodes.contains(neighbor)) {
						if (refDist > neighbor.getWeight()) {
							// Set new data
							neighbor.setSourceDist(refDist);
							if (best.getSourceDir() == -1) {
								// Set source direction to return
								neighbor.setSourceDir(Indentifiers.DIRECTION_SOUTH);
							} else {
								neighbor.setSourceDir(best.getSourceDir());
							}
						}
					} else {
						// Add new data
						neighbor.setSourceDist(refDist);
						if (best.getSourceDir() == -1) {
							// Set source direction to return
							neighbor.setSourceDir(Indentifiers.DIRECTION_SOUTH);
						} else {
							neighbor.setSourceDir(best.getSourceDir());
						}
						openNodes.add(neighbor);
					}
				}
			}
		}
		// If no open nodes (== no possible way) found return -1 and clear data
		openNodes.clear();
		closedNodes.clear();
		return -1;
	}
	
	private int calculateDetailedDir(Entity entity, int[] size, int[] hitbox) {
		while (!openLists.isEmpty()) {
			// Find best choice
			AStarMapList best = null;
			for (AStarMapList ref : openLists) {
				if (best == null || best.getWeight().getWeight() > ref.getWeight().getWeight()
						|| best.getWeight().getWeight() == ref.getWeight().getWeight() && best.getWeight().getDistSource() > ref.getWeight().getDistSource()) {
					best = ref;
				}
			}
			// Return source direction if the goal was found
			for(AStarMapNode node: best.getNodes()){
				if (node.getDistGoal() <= (int)(Math.ceil(size[0]/8)/2)+1) {
					openLists.clear();
					openNodes.clear();
					closedNodes.clear();
					return best.getSourceDirection();
				}
			}
			openLists.remove(best);
			openNodes.remove(best.getWeight());
			closedNodes.addAll(best.getNodes());
			AStarMapNode anchor = best.getNodes().get(0);
			switch(best.getSourceDirection()){
			case Indentifiers.DIRECTION_NORTH:
				if(anchor.getNodePos()[1]+1 < nodeMap[0].length){
					//Create new list
					int[] interpolatedSize = new int[]{(int) (Math.ceil(hitbox[0]/16)),(int) (Math.ceil(hitbox[1]/16))};
					//North
					AStarMapList list = new AStarMapList(Indentifiers.DIRECTION_NORTH);
					for(int i = 0; i<interpolatedSize[0]; i++){
						if(anchor.getNodePos()[0]-i>0)list.addNode(nodeMap[anchor.getNodePos()[0]-i][anchor.getNodePos()[1]+1]);
					}
					
					if(!valdiateAStarMapList(list, entity))break;
					updateList(list,best,Indentifiers.DIRECTION_NORTH);
					
					//East
					AStarMapList list2 = new AStarMapList(Indentifiers.DIRECTION_EAST);
					for(int i = 0; i<interpolatedSize[1]; i++){
						if(anchor.getNodePos()[1]-i>0)list2.addNode(nodeMap[anchor.getNodePos()[0]][anchor.getNodePos()[1]-i+1]);
					}
					
					if(!valdiateAStarMapList(list2, entity))list2 = null;
					updateList(list2,best,Indentifiers.DIRECTION_EAST);
					
					//West
					AStarMapList list3 = new AStarMapList(Indentifiers.DIRECTION_WEST);
					if(anchor.getNodePos()[0]-interpolatedSize[0] < 0)list3 = null;
					for(int i = 0; i<interpolatedSize[1]; i++){
						if(list3 != null && anchor.getNodePos()[1]-i>0)list3.addNode(nodeMap[anchor.getNodePos()[0]-interpolatedSize[0]][anchor.getNodePos()[1]-i+1]);
					}
					
					if(!valdiateAStarMapList(list3, entity))list3 = null;
					updateList(list3,best,Indentifiers.DIRECTION_WEST);
				}
				break;
			case Indentifiers.DIRECTION_EAST:
				if(anchor.getNodePos()[0]+1 < nodeMap.length){
					//Create new list
					int[] interpolatedSize = new int[]{(int) (Math.ceil(hitbox[0]/16)),(int) (Math.ceil(hitbox[1]/16))};
					//East
					AStarMapList list = new AStarMapList(Indentifiers.DIRECTION_EAST);
					for(int i = 0; i<interpolatedSize[1]; i++){
						if(anchor.getNodePos()[1]+i<nodeMap[0].length)list.addNode(nodeMap[anchor.getNodePos()[0]+1][anchor.getNodePos()[1]+i]);
					}
					
					if(!valdiateAStarMapList(list, entity))break;
					updateList(list,best,Indentifiers.DIRECTION_EAST);
					//North
					AStarMapList list2 = new AStarMapList(Indentifiers.DIRECTION_NORTH);
					if(anchor.getNodePos()[1]+interpolatedSize[1] >= nodeMap[0].length)list2 = null;
					for(int i = 0; i<interpolatedSize[0]; i++){
						if(list2 != null && anchor.getNodePos()[0]-i>=0)list2.addNode(nodeMap[anchor.getNodePos()[0]-i+1][anchor.getNodePos()[1]+interpolatedSize[1]]);
					}
					
					if(!valdiateAStarMapList(list2, entity))list2 = null;
					updateList(list2,best,Indentifiers.DIRECTION_NORTH);
					
					//South
					AStarMapList list3 = new AStarMapList(Indentifiers.DIRECTION_SOUTH);
					for(int i = 0; i<interpolatedSize[0]; i++){
						if(anchor.getNodePos()[0]-i>=0)list3.addNode(nodeMap[anchor.getNodePos()[0]-i+1][anchor.getNodePos()[1]]);
					}
					
					if(!valdiateAStarMapList(list3, entity))list3 = null;
					updateList(list3,best,Indentifiers.DIRECTION_SOUTH);
				}
				break;
			case Indentifiers.DIRECTION_SOUTH:
				if(anchor.getNodePos()[1]-1 >= 0){
					//Create new list
					int[] interpolatedSize = new int[]{(int) (Math.ceil(hitbox[0]/16)),(int) (Math.ceil(hitbox[1]/16))};
					//North
					AStarMapList list = new AStarMapList(Indentifiers.DIRECTION_SOUTH);
					for(int i = 0; i<interpolatedSize[0]; i++){
						if(anchor.getNodePos()[0]+i<nodeMap.length)list.addNode(nodeMap[anchor.getNodePos()[0]+i][anchor.getNodePos()[1]-1]);
					}
					
					if(!valdiateAStarMapList(list, entity))break;
					updateList(list,best,Indentifiers.DIRECTION_SOUTH);
					
					//East
					AStarMapList list2 = new AStarMapList(Indentifiers.DIRECTION_EAST);
					if(anchor.getNodePos()[0]+interpolatedSize[0] >= nodeMap.length)list2 = null;
					for(int i = 0; i<interpolatedSize[1]; i++){
						if(list2!= null && anchor.getNodePos()[1]+i<nodeMap[0].length)list2.addNode(nodeMap[anchor.getNodePos()[0]+interpolatedSize[0]][anchor.getNodePos()[1]+i-1]);
					}
					if(!valdiateAStarMapList(list2, entity))list2 = null;
					updateList(list2,best,Indentifiers.DIRECTION_EAST);
					
					//West
					AStarMapList list3 = new AStarMapList(Indentifiers.DIRECTION_WEST);
					for(int i = 0; i<interpolatedSize[1]; i++){
						if(anchor.getNodePos()[1]+i<nodeMap[0].length)list3.addNode(nodeMap[anchor.getNodePos()[0]][anchor.getNodePos()[1]+i-1]);
					}
					
					if(!valdiateAStarMapList(list3, entity))list3 = null;
					updateList(list3,best,Indentifiers.DIRECTION_WEST);
				}
				break;
			case Indentifiers.DIRECTION_WEST:
				if(anchor.getNodePos()[0]-1 >= 0){
					//Create new list
					int[] interpolatedSize = new int[]{(int) (Math.ceil(hitbox[0]/16)),(int) (Math.ceil(hitbox[1]/16))};
					//West
					AStarMapList list = new AStarMapList(Indentifiers.DIRECTION_WEST);
					for(int i = 0; i<interpolatedSize[1]; i++){
						if(anchor.getNodePos()[1]-i>=0)list.addNode(nodeMap[anchor.getNodePos()[0]-1][anchor.getNodePos()[1]-i]);
					}
					
					if(!valdiateAStarMapList(list, entity))break;
					updateList(list,best,Indentifiers.DIRECTION_WEST);
					//North
					AStarMapList list2 = new AStarMapList(Indentifiers.DIRECTION_NORTH);
					for(int i = 0; i<interpolatedSize[0]; i++){
						if(anchor.getNodePos()[0]+i<nodeMap.length)list2.addNode(nodeMap[anchor.getNodePos()[0]+i-1][anchor.getNodePos()[1]]);
					}
					
					if(!valdiateAStarMapList(list2, entity))list2 = null;
					updateList(list2,best,Indentifiers.DIRECTION_NORTH);
					//South
					AStarMapList list3 = new AStarMapList(Indentifiers.DIRECTION_SOUTH);
					if(anchor.getNodePos()[1]-interpolatedSize[1] < 0) list3 = null;
					for(int i = 0; i<interpolatedSize[0]; i++){
						if(list3 != null && anchor.getNodePos()[0]+i<nodeMap.length)list3.addNode(nodeMap[anchor.getNodePos()[0]+i-1][anchor.getNodePos()[1]-interpolatedSize[1]]);
					}
					
					if(!valdiateAStarMapList(list3, entity))list3 = null;
					updateList(list3,best,Indentifiers.DIRECTION_SOUTH);
				}
				break;
			}
		}
		// If no open nodes (== no possible way) found return -1 and clear data
		openNodes.clear();
		openLists.clear();
		closedNodes.clear();
		return -1;
	}
	
	public boolean goToPos(Entity source, int[] goalPos, int speed) {
		switch(findPathing(source,goalPos)) {
		case -1:
		default:
			return false;
		case Indentifiers.DIRECTION_EAST:
			source.setCurrentAni(Indentifiers.STATE_WALK_EAST);
			return this.moveRight(source, speed, goalPos);
		case Indentifiers.DIRECTION_WEST:
			source.setCurrentAni(Indentifiers.STATE_WALK_WEST);
			return this.moveLeft(source, speed, goalPos);
		case Indentifiers.DIRECTION_NORTH:
			source.setCurrentAni(Indentifiers.STATE_WALK_NORTH);
			return this.moveTop(source, speed, goalPos);
		case Indentifiers.DIRECTION_SOUTH:
			source.setCurrentAni(Indentifiers.STATE_WALK_SOUTH);
			return this.moveDown(source, speed, goalPos);
		}
	}
	
	public void updateTiles(Entity ent){
		ArrayList<int[]> tiles = ent.getMapPos();
		ArrayList<int[]> checkedTiles = new ArrayList<>();
		for(int[] tile: tiles){
			for(int y = -1; y<2; y++){
				for(int x = -1; x<2; x++){
					boolean found = false;
					int[] pos = new int[]{tile[0]+x,tile[1]+y};
					for(int[] refTile: checkedTiles){
						if(refTile[0] == pos[0] && refTile[1] == pos[1])found = true;
					}
					
					if(found){
						RuntimeData.getInstance().getMapData().getData()[pos[0]][pos[1]].updateWalkableGrid(ent.getId(), ent.getPos(), ent.getHitbox());
						checkedTiles.add(pos);
					}
				}
			}
		}
	}
	
	private boolean valdiateAStarMapList(AStarMapList list, Entity entity){
		if(list == null)return false;
		boolean isClosed = true;
		for(AStarMapNode node: list.getNodes()){
			if(!closedNodes.contains(node))isClosed = false;
			if(node.getEntityID() != null && !node.getEntityID().equals(entity.getId()) && (!(entity instanceof Enemy) || entity instanceof Enemy && !node.getEntityID().equals(((Enemy)entity).getAlerted().getId())))return false;
		}
		return !isClosed;
	}
	
	private void initOpenLists(int[] hitbox, int[] goalPos, int[] sourcePos){
		int[] currentPos = null;
		int divSize = 16;
		int divider = 8;
		int xDiff = (int) (Math.abs(Math.floor(sourcePos[0]/128f) - Math.floor(goalPos[0]/128f))+1);
		int yDiff = (int) (Math.abs(Math.floor(sourcePos[1]/128f) - Math.floor(goalPos[1]/128f))+1);
		if(goalPos[0] > sourcePos[0]){
			if(goalPos[1] > sourcePos[1]){
				currentPos = new int[]{(int) (Math.floor((sourcePos[0]+hitbox[0]/2)%128/divSize)+divider),(int) (Math.floor((sourcePos[1]+hitbox[1]/2)%128/divSize)+divider)};
			}else{
				currentPos = new int[]{(int) (Math.floor((sourcePos[0]+hitbox[0]/2)%128/divSize)+divider),(int) (Math.floor((sourcePos[1]+hitbox[1]/2)%128/divSize))+divider*yDiff};
			}
		}else{
			if(goalPos[1] > sourcePos[1]){
				currentPos = new int[]{(int) (Math.floor((sourcePos[0]+hitbox[0]/2)%128/divSize)+divider*xDiff),(int) (Math.floor((sourcePos[1]+hitbox[1]/2)%128/divSize)+divider)};
			}else{
				currentPos = new int[]{(int) (Math.floor((sourcePos[0]+hitbox[0]/2)%128/divSize)+divider*xDiff),(int) (Math.floor((sourcePos[1]+hitbox[1]/2)%128/divSize))+divider*yDiff};
			}
		}
		openNodes.add(nodeMap[currentPos[0]][currentPos[1]]);
		int[] interpolatedSize = new int[]{(int) (Math.ceil(hitbox[0]/16)),(int) (Math.ceil(hitbox[1]/16))};
		AStarMapList listN = new AStarMapList(Indentifiers.DIRECTION_NORTH);
		for(int i = 0; i<interpolatedSize[0]; i++){
			listN.addNode(nodeMap[currentPos[0]+interpolatedSize[0]-1-i][currentPos[1]+interpolatedSize[1]-1]);
		}
		listN.setFirst(true);
		openLists.add(listN);
		AStarMapList listE = new AStarMapList(Indentifiers.DIRECTION_EAST);
		for(int i = 0; i<interpolatedSize[1]; i++){
			listE.addNode(nodeMap[currentPos[0]+interpolatedSize[0]-1][currentPos[1]+i]);
		}
		listE.setFirst(true);
		openLists.add(listE);
		AStarMapList listS = new AStarMapList(Indentifiers.DIRECTION_SOUTH);
		for(int i = 0; i<interpolatedSize[0]; i++){
			listS.addNode(nodeMap[currentPos[0]+i][currentPos[1]]);
		}
		listS.setFirst(true);
		openLists.add(listS);
		AStarMapList listW = new AStarMapList(Indentifiers.DIRECTION_WEST);
		for(int i = 0; i<interpolatedSize[1]; i++){
			listW.addNode(nodeMap[currentPos[0]][currentPos[1]+interpolatedSize[1]-1-i]);
		}
		listW.setFirst(true);
		openLists.add(listW);
	}
	
	private void updateList(AStarMapList list,AStarMapList best,int isDir){
		if(list != null){
			int refDist = best.getWeight().getDistSource() + 1;
			// Add new data
			list.getWeight().setSourceDist(refDist);
			if (best.getOriginalDir() == -1) {
				// Set source direction to return
				list.setOriginalDir(isDir);
			} else {
				list.setOriginalDir(best.getOriginalDir());
			}
			openNodes.add(list.getWeight());
			openLists.add(list);
		}
	}
}
