package com.verminsnest.world.generation.spawning;

import java.util.ArrayList;
import java.util.Random;

import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.core.management.ids.Indentifiers;
import com.verminsnest.core.management.ids.Pools;
import com.verminsnest.core.management.loaders.LoadingModule;
import com.verminsnest.world.generation.map.rooms.Room;
import com.verminsnest.world.generation.map.rooms.Shop;

public class UtilSpawner extends LoadingModule{
	private Random rand;
	public UtilSpawner() {
		super(RuntimeData.getInstance().getGame().getConfig().getMessage("LoadingScreen_UtilGen"));
		rand = new Random();
	}

	@Override
	public void execute() {
		setShop();
		setLevelDesign();
		setDone();
	}
	
	private void setShop(){
		//Keeper
		int pos[] = new int[2];
		for(Room room: RuntimeData.getInstance().getMapData().getRooms()) {
			if(room instanceof Shop) {
				pos[0] = room.getLayoutPos()[0]+1;
				pos[1] = room.getLayoutPos()[1]+1;
			}
		}
		pos[0] = (int) ((pos[0]*RuntimeData.getInstance().getMapData().getRooms().get(0).getData().length-Math.ceil(RuntimeData.getInstance().getMapData().getRooms().get(0).getData().length/2.0)+10)*128+64-18);
		pos[1] = (int) ((pos[1]*RuntimeData.getInstance().getMapData().getRooms().get(0).getData()[0].length-Math.floor(RuntimeData.getInstance().getMapData().getRooms().get(0).getData()[0].length/2.0)+10)*128-50);
		RuntimeData.getInstance().getEntityManager().addUtilInit(pos[0], pos[1], Indentifiers.ASSETMANAGER_SHOPKEEPER);
		//Items
		ArrayList<Integer> itemIDs = (ArrayList<Integer>) Pools.getShopItemIDs();
		int[] dirs = new int[] {1,1,1,1};
		for(int i = 0; i<3;i++) {
			int randomID = itemIDs.get(rand.nextInt(itemIDs.size()));
			int randDir = rand.nextInt(4);
			while(dirs[randDir] == 0) {
				randDir = rand.nextInt(4);
			}
			dirs[randDir] = 0;
			switch(randDir) {
			case Indentifiers.DIRECTION_NORTH:
				RuntimeData.getInstance().getEntityManager().addUtilInit(pos[0], pos[1]+rand.nextInt(100)+80, randomID);
				break;
			case Indentifiers.DIRECTION_EAST:
				RuntimeData.getInstance().getEntityManager().addUtilInit(pos[0]+rand.nextInt(100)+80, pos[1], randomID);
				break;
			case Indentifiers.DIRECTION_SOUTH:
				RuntimeData.getInstance().getEntityManager().addUtilInit(pos[0], pos[1]-rand.nextInt(100)-80, randomID);
				break;
			case Indentifiers.DIRECTION_WEST:
				RuntimeData.getInstance().getEntityManager().addUtilInit(pos[0]-rand.nextInt(100)-80, pos[1], randomID);
				break;
			}
		}
	}
	/**
	 * Places decoration
	 */
	private void setLevelDesign(){
		for(Room room: RuntimeData.getInstance().getMapData().getRooms()){
			ArrayList<int[]> temp = new ArrayList<>(); 
			if(!(room instanceof Shop))temp.addAll(RuntimeData.getInstance().getMapData().getWalkableTilePosOfRoom(room, false));
			else continue;
			//Get all valid postions
			ArrayList<int[]> tilePosToSpawn = new ArrayList<>();
			for(int[] pos: temp){
				if(validatePos(pos))tilePosToSpawn.add(pos);
			}
			temp.clear();
			int count = (int) (tilePosToSpawn.size()/5);
			int clustersCount = rand.nextInt(3)+2;
			count = count < 1 ? 1 :count;
			ArrayList<Cluster> clusters = new ArrayList<>();
			ArrayList<Cluster> closedClusters = new ArrayList<>();
			for(int i = 0; i < clustersCount; i++){
				clusters.add(new Cluster());
			}
			
			for(int i = 0; i < count; i++){
				int cluster = rand.nextInt(clusters.size());
				if(clusters.get(cluster).open.isEmpty() && clusters.get(cluster).closed.isEmpty()){
					//New first pos in cluster
					int tile = rand.nextInt(tilePosToSpawn.size());
					clusters.get(cluster).open.add(new ClusterPos(tilePosToSpawn.get(tile),4));
					tilePosToSpawn.remove(tile);
				}else{
					boolean posFound = false;
					int[] randTile = null;
					ClusterPos pos = null;
					if(clusters.get(cluster).open.isEmpty()){
						closedClusters.add(clusters.get(cluster));
						clusters.remove(cluster);
						continue;
					}
					while(!posFound && !clusters.get(cluster).open.isEmpty()){
						//Remove if no open ClusterPos 
						if(clusters.get(cluster).open.isEmpty()){
							closedClusters.add(clusters.get(cluster));
							clusters.remove(cluster);
							continue;
						}
						//Get a random ClusterPos, count down its surrounding tiles and close it if needed
						pos = clusters.get(cluster).open.get(rand.nextInt(clusters.get(cluster).open.size()));
						while(pos != null && pos.surTiles == 0){
							clusters.get(cluster).closed.add(pos);
							clusters.get(cluster).open.remove(pos);
							if(!clusters.get(cluster).open.isEmpty())pos = clusters.get(cluster).open.get(rand.nextInt(clusters.get(cluster).open.size()));
							else pos = null;
						}
						if(pos == null)continue;
						//Check side tiles and choose random new
						ArrayList<int[]> tiles = new ArrayList<>();
						for(int x = -1; x<2; x++){
							for(int y = -1; y<2; y++){
								int[] tilePos = new int[]{pos.pos[0]+x,pos.pos[1]+y};
								if(x != y && validatePos(tilePos)){
									for(int[] refPos: tilePosToSpawn){
										if(refPos[0] == tilePos[0] && refPos[1] == tilePos[1]){
											tiles.add(tilePos);
											break;
										}
									}
								}
							}
						}
						if(!tiles.isEmpty()){
							pos.surTiles--;
							if(pos.surTiles == 0){
								clusters.get(cluster).open.remove(pos);
								clusters.get(cluster).closed.add(pos);
							}
							randTile = tiles.get(rand.nextInt(tiles.size()));
							posFound = true;
						}
						else{
							clusters.get(cluster).open.remove(pos);
							clusters.get(cluster).closed.add(pos);
						}
					}

					if(clusters.get(cluster).open.isEmpty()){
						closedClusters.add(clusters.get(cluster));
						clusters.remove(cluster);
						continue;
					}
					int arrayPos = 0;
					//Remove from available
					for(int[] refPos: tilePosToSpawn){
						if(refPos[0] == randTile[0] && refPos[1] == randTile[1]){
							break;
						}
						arrayPos++;
					}
					tilePosToSpawn.remove(arrayPos);
					//Add new ClusterPos
					if(pos.stretch-1 == 0)clusters.get(cluster).closed.add(new ClusterPos(randTile,pos.stretch-1));
					else clusters.get(cluster).open.add(new ClusterPos(randTile,pos.stretch-1));
				}
			}
			for(Cluster cluster: clusters){
				for(ClusterPos cPos: cluster.open){
					setEntity(cPos);
				}
				for(ClusterPos cPos: cluster.closed){
					setEntity(cPos);
				}
			}
			for(Cluster cluster: closedClusters){
				for(ClusterPos cPos: cluster.open){
					setEntity(cPos);
				}
				for(ClusterPos cPos: cluster.closed){
					setEntity(cPos);
				}
			}
		}
	}
	/**
	 * Checks if a given position would block the way
	 * @param pos
	 * @return true/false
	 */
	private boolean validatePos(int[] pos){
		int surroundCount = 0;
		for(int x = -1; x<2; x++){
			for(int y = -1; y<2; y++){
				if((x & y) != 0 && !RuntimeData.getInstance().getMapData().getData()[pos[0]+x][pos[1]+y].isWalkable())surroundCount++;
			}
		}
		if(surroundCount < 2) return true;
		else return false;
	}
	
	private void setEntity(ClusterPos cPos){
		int count = rand.nextInt(cPos.objCount)+1;
		for(int i = 0; i < count; i++){
			RuntimeData.getInstance().getEntityManager().addUtilInit(cPos.pos[0]*128+rand.nextInt(64),cPos.pos[1]*128+rand.nextInt(64),Indentifiers.ASSETMANAGER_ROCKS);
		}
	}

	private class ClusterPos{
		private int surTiles;
		private int stretch;
		private int[] pos;
		private int objCount;
		
		private ClusterPos(int[] pos, int stretch){
			objCount = 4;
			this.stretch = stretch;
			this.pos = pos;
			surTiles = 0;
			for(int x = -1; x<2; x++){
				for(int y = -1; y<2; y++){
					if(x != y && validatePos(new int[]{pos[0]+x,pos[1]+y}))surTiles++;
				}
			}
		}
	}
	
	private class Cluster{
		private ArrayList<ClusterPos> open;
		private ArrayList<ClusterPos> closed;
		private Cluster(){
			open = new ArrayList<>();
			closed = new ArrayList<>();
		}
	}
}
