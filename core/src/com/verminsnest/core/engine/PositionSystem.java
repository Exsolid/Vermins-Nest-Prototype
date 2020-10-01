package com.verminsnest.core.engine;

import java.util.ArrayList;

import com.verminsnest.core.management.data.RuntimeData;

public class PositionSystem {
	private static PositionSystem instance;
	private final int chunkWidth = 7;
	private final int chunkHeight = 7;
	private Chunk[][] chunks;
	
	private PositionSystem(){
		int chunkCountX = (int) Math.ceil(RuntimeData.getInstance().getMapData().getData().length*1.0/chunkWidth);
		int chunkCountY = (int) Math.ceil(RuntimeData.getInstance().getMapData().getData()[0].length*1.0/chunkHeight);
		chunks = new Chunk[chunkCountX][chunkCountY];
	}
	
	public static PositionSystem getInstance(){
		if(instance == null)instance = new PositionSystem();
		return instance;
	}
	
	public void addPosition(int[] pos){
		int[] tile = getTile(pos);
		int[] chunk = new int[]{(int) Math.floor(tile[0]*1.0/chunkWidth),(int) Math.floor(tile[1]*1.0/chunkHeight)};
		if(chunks[chunk[0]][chunk[1]] != null)chunks[chunk[0]][chunk[1]].revealedPositions.add(new int[]{pos[0],pos[1]});
		else{
			chunks[chunk[0]][chunk[1]] = new Chunk();
			chunks[chunk[0]][chunk[1]].revealedPositions.add(new int[]{pos[0],pos[1]});
		}
	}
	
	public int[] getTile(int[] pos){
		int[] tile = new int[2];
		tile[0] = (int) Math.floor((pos[0]-pos[0]%128)/128);
		tile[1] = (int) Math.floor((pos[1]-pos[1]%128)/128);
		return tile;
	}
	
	public ArrayList<int[]> getRenderedRevealedPositions(){
		ArrayList<int[]> temp = new ArrayList<>();	
		for(int[] chunk : getRenderedChunks()){
			if(chunks[chunk[0]][chunk[1]] != null && !chunks[chunk[0]][chunk[1]].revealedPositions.isEmpty()){
				ArrayList<int[]> tempPos = new ArrayList<>();
				tempPos.addAll(chunks[chunk[0]][chunk[1]].revealedPositions);
				for(int[] pos: tempPos){
					if(pos != null)temp.add(pos);
				}
			}
		}
		return temp;
	}
	
	public ArrayList<int[]> getRenderedChunks(){
		ArrayList<int[]> temp = new ArrayList<>();	
		int[] tile = getTile(RuntimeData.getInstance().getEntityManager().getCharacter().getPos());
		int[] chunk = new int[]{(int) Math.floor(tile[0]*1.0/chunkWidth),(int) Math.floor(tile[1]*1.0/chunkHeight)};
		temp.add(chunk);
		temp.add(new int[]{chunk[0]+1,chunk[1]});
		temp.add(new int[]{chunk[0]-1,chunk[1]});
		temp.add(new int[]{chunk[0],chunk[1]+1});
		temp.add(new int[]{chunk[0],chunk[1]-1});
		temp.add(new int[]{chunk[0]-1,chunk[1]-1});
		temp.add(new int[]{chunk[0]+1,chunk[1]+1});
		temp.add(new int[]{chunk[0]+1,chunk[1]-1});
		temp.add(new int[]{chunk[0]-1,chunk[1]+1});
		return temp;
	}
	
	public void clearData(){
		instance = null;
	}
	
	public class Chunk{
		private ArrayList<int[]> revealedPositions;
		private Chunk(){
			revealedPositions = new ArrayList<>();
		}
	}
}
