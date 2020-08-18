package com.verminsnest.core.engine;


import java.util.ArrayList;

import com.verminsnest.core.management.Indentifiers;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.entities.Entity;
import com.verminsnest.entities.enemies.Enemy;
import com.verminsnest.entities.explosions.Explosion;
import com.verminsnest.entities.items.Food;
import com.verminsnest.entities.playables.Playable;
import com.verminsnest.entities.projectiles.Projectile;
import com.verminsnest.world.generation.map.AStarMapNode;
import com.verminsnest.world.generation.map.MapCell;

public class EntityMovementSystem {
	
	private MapCell[][] map;
	
	private ArrayList<AStarMapNode> openNodes;
	private ArrayList<AStarMapNode> closedNodes;
	private AStarMapNode[][] nodeMap;
	
	public EntityMovementSystem(MapCell[][] map){
		this.map = map;
		this.openNodes = new ArrayList<>();
		this.closedNodes = new ArrayList<>();
	}
	
	public boolean moveTop(Entity entity, int speed, int[] goalPos){
		int mapPosXStart = (int) Math.floor(entity.getPos()[0]/128);
		int mapPosXEnd = (int) Math.floor((entity.getPos()[0]+entity.getHitbox()[0])/128);
		for(int i = 1; i<=speed; i++){
			int refMapPosYEnd = (int) Math.floor((entity.getPos()[1]+entity.getHitbox()[1]+1)/128);
			if(!map[mapPosXStart][refMapPosYEnd].isWalkable() || !map[mapPosXEnd][refMapPosYEnd].isWalkable()){
				if(goalPos == null)return false;
				else return goToPos(entity, goalPos, speed);
			}else{
				int y = entity.getPos()[1]+entity.getHitbox()[1]+1;
				for(Entity refEnt: RuntimeData.getInstance().getEntityManager().getAllEntities()){
					if(!entity.equals(refEnt) && refEnt.isObstacle()){
						for(int x = entity.getPos()[0]; x <= entity.getPos()[0]+entity.getHitbox()[0]; x++){
							if(x <= refEnt.getPos()[0]+refEnt.getHitbox()[0] && x >= refEnt.getPos()[0] && y <= refEnt.getPos()[1]+refEnt.getHitbox()[1] && y >= refEnt.getPos()[1]){
								if(!checkEntities(entity,refEnt))return false;
							}
						}
					}
				}
				entity.getPos()[1] += 1;
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
				if(goalPos == null)return false;
				else return goToPos(entity, goalPos, speed);
			}else{
				int y = entity.getPos()[1]-1;
				for(Entity refEnt: RuntimeData.getInstance().getEntityManager().getAllEntities()){
					if(!entity.equals(refEnt) && refEnt.isObstacle()){
						for(int x = entity.getPos()[0]; x <= entity.getPos()[0]+entity.getHitbox()[0]; x++){
							if(x <= refEnt.getPos()[0]+refEnt.getHitbox()[0] && x >= refEnt.getPos()[0] && y <= refEnt.getPos()[1]+refEnt.getHitbox()[1] && y >= refEnt.getPos()[1]){
								if(!checkEntities(entity,refEnt))return false;
							}
						}
					}
				}
					entity.getPos()[1] -= 1;
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
				if(goalPos == null)return false;
				else return goToPos(entity, goalPos, speed);
			}else{
				int x = entity.getPos()[0]-1;
				for(Entity refEnt: RuntimeData.getInstance().getEntityManager().getAllEntities()){
					if(!entity.equals(refEnt) && refEnt.isObstacle()){
						for(int y = entity.getPos()[1]; y <= entity.getPos()[1]+entity.getHitbox()[1]; y++){
							if(x <= refEnt.getPos()[0]+refEnt.getHitbox()[0] && x >= refEnt.getPos()[0] && y <= refEnt.getPos()[1]+refEnt.getHitbox()[1] && y >= refEnt.getPos()[1]){
								if(!checkEntities(entity,refEnt))return false;
							}
						}
					}
				}
					entity.getPos()[0] -= 1;
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
				if(goalPos == null)return false;
				else return goToPos(entity, goalPos, speed);
			}else{
				int x = entity.getPos()[0]+entity.getHitbox()[0]+1;
				for(Entity refEnt: RuntimeData.getInstance().getEntityManager().getAllEntities()){
					if(!entity.equals(refEnt) && refEnt.isObstacle()){
						for(int y = entity.getPos()[1]; y <= entity.getPos()[1]+entity.getHitbox()[1]; y++){
							if(x <= refEnt.getPos()[0]+refEnt.getHitbox()[0] && x >= refEnt.getPos()[0] && y <= refEnt.getPos()[1]+refEnt.getHitbox()[1] && y >= refEnt.getPos()[1]){
								if(!checkEntities(entity,refEnt))return false;
							}
						}
					}
				}
					entity.getPos()[0] += 1;
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
		}
		else{
			return false;
		}
		return true;
	}
	
	private int findPathing(Entity source, int[] goalPos) {
		//Positions and AStar mapping
		goalPos[0] = (goalPos[0]-goalPos[0]%128)/128;
		goalPos[1] = (goalPos[1]-goalPos[1]%128)/128;
		
		int[] sourcePos = new int[] {(source.getPos()[0]-source.getPos()[0]%128)/128,(source.getPos()[1]-source.getPos()[1]%128)/128};
		nodeMap = RuntimeData.getInstance().getMapData().getAStarMap(goalPos, sourcePos);
		
		int[] currentPos = new int[] {sourcePos[0]-nodeMap[0][0].getMapPos()[0],sourcePos[1]-nodeMap[0][0].getMapPos()[1]};
		openNodes.add(nodeMap[currentPos[0]][currentPos[1]]);
		
		//Calculate the best direction
		int defDir = calculatePathing();
		int[] currentShiftedPos = null;
		
		//If the entity is not on a single tile, check both tiles
		switch(defDir) {
		case Indentifiers.DIRECTION_EAST:
		case Indentifiers.DIRECTION_WEST:
			sourcePos = new int[] {(source.getPos()[0]-source.getPos()[0]%128)/128,(source.getPos()[1]+source.getHitbox()[1]-((source.getPos()[1]+source.getHitbox()[1])%128))/128};
			currentShiftedPos = new int[] {sourcePos[0]-nodeMap[0][0].getMapPos()[0],sourcePos[1]-nodeMap[0][0].getMapPos()[1]};
			if(currentShiftedPos == currentPos)return defDir;
			else {
				nodeMap = RuntimeData.getInstance().getMapData().getAStarMap(goalPos, sourcePos);
				currentShiftedPos = new int[] {sourcePos[0]-nodeMap[0][0].getMapPos()[0],sourcePos[1]-nodeMap[0][0].getMapPos()[1]};
				openNodes.add(nodeMap[currentShiftedPos[0]][currentShiftedPos[1]]);

				return calculatePathing();
			}
		case Indentifiers.DIRECTION_NORTH:
		case Indentifiers.DIRECTION_SOUTH:
			sourcePos = new int[] {(source.getPos()[0]+source.getHitbox()[0]-((source.getPos()[0]+source.getHitbox()[0])%128))/128,(source.getPos()[1]-source.getPos()[1]%128)/128};
			currentShiftedPos = new int[] {sourcePos[0]-nodeMap[0][0].getMapPos()[0],sourcePos[1]-nodeMap[0][0].getMapPos()[1]};
			if(currentShiftedPos == currentPos)return defDir;
			else {
				nodeMap = RuntimeData.getInstance().getMapData().getAStarMap(goalPos, sourcePos);
				currentShiftedPos = new int[] {sourcePos[0]-nodeMap[0][0].getMapPos()[0],sourcePos[1]-nodeMap[0][0].getMapPos()[1]};
				openNodes.add(nodeMap[currentShiftedPos[0]][currentShiftedPos[1]]);

				return calculatePathing();
			}
		default:
			//If no solution was found, return -1 (closed room)
			return -1;
		}
	}
	
	private int calculatePathing() {
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
			if (best.getDistGoal() == 0) {
				openNodes.clear();
				closedNodes.clear();
				return best.getSourceDir();
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
	
	public boolean goToPos(Entity source, int[] goalPos, int speed) {
		
		switch(findPathing(source,goalPos)) {
		case -1:
		default:
			return false;
		case Indentifiers.DIRECTION_EAST:
			source.setCurrentAni(Indentifiers.STATE_WALK_EAST);
			return this.moveRight(source, speed, null);
		case Indentifiers.DIRECTION_WEST:
			source.setCurrentAni(Indentifiers.STATE_WALK_WEST);
			return this.moveLeft(source, speed, null);
		case Indentifiers.DIRECTION_NORTH:
			source.setCurrentAni(Indentifiers.STATE_WALK_NORTH);
			return this.moveTop(source, speed, null);
		case Indentifiers.DIRECTION_SOUTH:
			source.setCurrentAni(Indentifiers.STATE_WALK_SOUTH);
			return this.moveDown(source, speed, null);
		}
	}
}
