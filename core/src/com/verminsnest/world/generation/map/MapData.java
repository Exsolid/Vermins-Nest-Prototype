package com.verminsnest.world.generation.map;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.world.generation.map.rooms.Room;

public class MapData {

	private ArrayList<Room> rooms;
	private MapCell[][] data;
	private int[] roomSize;
	
	private int[][] numericMap;
	private Random rand;
	private Room[][] layout;
	
	public MapData(int[] roomSize){
		this.roomSize = roomSize;
		layout = new Room[1][1];
		rand = new Random();
		rooms = new ArrayList<Room>();
	}
	
	public MapCell[][] getData() {
		return data;
	}
	public ArrayList<Room> getRooms() {
		return rooms;
	}
	public void addRoom(Room room) {
		rooms.add(room);	
	}
	
	public Room[][] getRoomLayout(){
		Room[][] temp = layout;
		return temp;
	}
	
	public void computeData(int maxRoomWidth, int maxRoomHeight, Texture sheet){
		computeMap(maxRoomWidth, maxRoomHeight);
		this.data = this.fillGraphics(sheet, numericMap);
	}
	
	private void computeMap(int maxRoomWidth, int maxRoomHeight) {
		int[][] map = new int[maxRoomWidth*layout.length+20][maxRoomHeight*layout[0].length+20];
		for (int xMap = 0; xMap < map.length; xMap++) {
			for (int yMap = 0; yMap < map[0].length; yMap++) {
				map[xMap][yMap] = 1;
			}
		}
		//Copy layout
		for (int x = 0; x < layout.length; x++) {
			for (int y = 0; y < layout[0].length; y++) {
				if(layout[x][y] == null){
					//Copy empty
					for (int xMap = 0; xMap < map.length; xMap++) {
						for (int yMap = 0; yMap < map[0].length; yMap++) {
							for (int xRoom = 0; xRoom < maxRoomWidth; xRoom++) {
								for (int yRoom = 0; yRoom < maxRoomHeight; yRoom++) {
									map[10+x*maxRoomWidth+xRoom][10+y*maxRoomHeight+yRoom] = 1;
								}
							}
						}
					}
				}else{
					for (int xMap = 0; xMap < map.length; xMap++) {
						for (int yMap = 0; yMap < map[0].length; yMap++) {
							//Copy room
							for (int xRoom = 0; xRoom < layout[x][y].getData().length; xRoom++) {
								for (int yRoom = 0; yRoom < layout[x][y].getData()[0].length; yRoom++) {
									map[10+x*maxRoomWidth+xRoom][10+y*maxRoomHeight+yRoom] = layout[x][y].getData()[xRoom][yRoom];
								}
							}
						}
					}
				}
			}
		}
		
		for (int y = map[0].length - 2; y > 0; y--) {
			for (int x = 1; x < map.length-1; x++) {
				if(map[x][y] == 1 && map[x][y+1] == 0 && map[x-1][y] == 1 && map[x+1][y] == 0 && map[x+1][y+1] == 0&& map[x-1][y+1] == 1)map[x][y] = 0;
				if(map[x][y] == 1 && map[x-1][y] == 0 && map[x][y-1] == 0 && map[x-1][y+1] == 1) map[x-1][y+1] = 0;				
			}
		}
		numericMap =map;
	}

	// Set all graphics
	private MapCell[][] fillGraphics(Texture sheet, int[][] map) {
		MapCell[][] graphicMap = new MapCell[map.length][map[0].length];
		TextureRegion[][] mapping = TextureRegion.split(sheet, sheet.getWidth() / 8, sheet.getHeight() / 5);
		for (int y = map[0].length - 1; y > -1; y--) {
			for (int x = 0; x < map.length; x++) {
				int variation = rand.nextInt(2);
				boolean isShadow = false;
				MapCell tempCell = null;
				if(x == 0 || y == 0 || x == map.length-1 || y == map[0].length-1){
					tempCell = new MapCell(mapping[4][7], x*128, y*128);
					tempCell.setWalkable(false);
				}
				else if(map[x][y] == 1){
					if(map[x][y-1] == 1 && map[x][y+1] == 1 && map[x-1][y] == 1 && map[x+1][y] == 1 && map[x+1][y+1] == 1&& map[x+1][y-1] == 1&& map[x-1][y-1] == 1&& map[x-1][y+1] == 1){
						//All walls
						variation = rand.nextInt(100)+1;
						if(variation >80){
							variation = rand.nextInt(3);
							switch(variation){
							case 0:
								tempCell = new MapCell(mapping[3][2], x*128, y*128);
								break;
							case 1:
								tempCell = new MapCell(mapping[3][3], x*128, y*128);
								break;
							case 2:
								tempCell = new MapCell(mapping[2][3], x*128, y*128);
								break;
							}
						}else tempCell = new MapCell(mapping[4][7], x*128, y*128);
				
					}else if(map[x][y-1] == 1 && map[x][y+1] == 1 && map[x-1][y] == 1 && map[x+1][y] == 1 && map[x+1][y+1] == 1&& map[x+1][y-1] == 0&& map[x-1][y-1] == 1&& map[x-1][y+1] == 1){
						//Inner corner top left
						tempCell = new MapCell(mapping[0][0], x*128, y*128);
					}else if(map[x][y-1] == 1 && map[x][y+1] == 1 && map[x-1][y] == 1 && map[x+1][y] == 1 && map[x+1][y+1] == 1&& map[x+1][y-1] == 1&& map[x-1][y-1] == 0&& map[x-1][y+1] == 1){
						//Inner corner top right
						tempCell = new MapCell(mapping[0][4], x*128, y*128);
					}else if(map[x][y-1] == 1 && map[x][y+1] == 1 && map[x-1][y] == 1 && map[x+1][y] == 1 && map[x+1][y+1] == 0&& map[x+1][y-1] == 1&& map[x-1][y-1] == 1&& map[x-1][y+1] == 1){
						//Inner corner bottom left
						tempCell = new MapCell(mapping[4][0], x*128, y*128);
					}else if(map[x][y-1] == 1 && map[x][y+1] == 1 && map[x-1][y] == 1 && map[x+1][y] == 1 && map[x+1][y+1] == 1&& map[x+1][y-1] == 1&& map[x-1][y-1] == 1&& map[x-1][y+1] == 0){
						//Inner corner bottom right
						tempCell = new MapCell(mapping[4][4], x*128, y*128);
					}else if((map[x-1][y-1] == 0||map[x][y-1] == 0||map[x+1][y-1] == 0)&& map[x][y+1] == 1 && map[x-1][y] == 1 && map[x+1][y] == 1 && map[x+1][y+1] == 1&& map[x-1][y+1] == 1){
						//Top walls
						variation = rand.nextInt(3);
						tempCell = new MapCell(mapping[4][7], x*128, y*128);
						tempCell.addLayer(mapping[0][1+variation]);
					}else if((map[x][y+1] == 0||map[x+1][y+1] == 0|| map[x-1][y+1] == 0) &&map[x][y-1] == 1 && map[x-1][y] == 1 && map[x+1][y] == 1 && map[x+1][y-1] == 1&& map[x-1][y-1] == 1){
						//Bottom walls
						variation = rand.nextInt(2);
						tempCell = new MapCell(mapping[4][7], x*128, y*128);
						if((map[x][y+1] == 0||map[x+1][y+1] == 0) && map[x-1][y+1] == 1) tempCell.addLayer(mapping[4][1]);
						else tempCell.addLayer(mapping[4][2+variation]);
					}else if((map[x-1][y] == 0 ||map[x-1][y-1] == 0||map[x-1][y+1] == 0) &&map[x][y-1] == 1 && map[x][y+1] == 1 && map[x+1][y] == 1 && map[x+1][y+1] == 1&& map[x+1][y-1] == 1){
						//Right walls
						variation = rand.nextInt(2);
						tempCell = new MapCell(mapping[4][7], x*128, y*128);
						if((map[x-1][y] == 0 ||map[x-1][y-1] == 0) &&map[x-1][y+1] == 1)tempCell.addLayer(mapping[1][4]);
						else tempCell.addLayer(mapping[2+variation][4]);
					}else if((map[x+1][y] == 0 ||map[x+1][y+1] == 0|| map[x+1][y-1] == 0)&& map[x][y-1] == 1 && map[x][y+1] == 1 && map[x-1][y] == 1 && map[x-1][y-1] == 1&& map[x-1][y+1] == 1){
						//Left walls
						variation = rand.nextInt(3);
						tempCell = new MapCell(mapping[4][7], x*128, y*128);
						tempCell.addLayer(mapping[1+variation][0]);
					}else if(map[x][y-1] == 0 && map[x][y+1] == 0 && map[x-1][y] == 0 && map[x+1][y] == 0 && map[x+1][y+1] == 0&& map[x+1][y-1] == 0&& map[x-1][y-1] == 0&& map[x-1][y+1] == 0){
						//Single
						tempCell = new MapCell(mapping[4][7], x*128, y*128);
						tempCell.addLayer(mapping[2][2]);
					}else if(map[x][y-1] == 0 && map[x][y+1] == 1 && map[x-1][y] == 0 && map[x+1][y] == 1 && map[x+1][y+1] == 1 && map[x-1][y-1] == 0){
						//Outer corner top right
						tempCell = new MapCell(mapping[4][7], x*128, y*128);
						tempCell.addLayer(mapping[1][5]);
					}else if(map[x][y-1] == 0 && map[x][y+1] == 1 && map[x-1][y] == 1 && map[x+1][y] == 0 && map[x+1][y-1] == 0 && map[x-1][y+1] == 1){
						//Outer corner top left
						tempCell = new MapCell(mapping[1][6], x*128, y*128);
					}else if(map[x][y-1] == 1 && map[x][y+1] == 0 && map[x-1][y] == 0 && map[x+1][y] == 1 && map[x+1][y-1] == 1 && map[x-1][y+1] == 0){
						//Outer corner bottom right
						tempCell = new MapCell(mapping[4][7], x*128, y*128);
						tempCell.addLayer(mapping[0][5]);
					}else if(map[x][y-1] == 1 && map[x][y+1] == 0 && map[x-1][y] == 1 && map[x+1][y] == 0 && map[x+1][y+1] == 0 && map[x-1][y-1] == 1){
						//Outer corner bottom left
						tempCell = new MapCell(mapping[4][7], x*128, y*128);
						tempCell.addLayer(mapping[0][6]);
					}
					if(tempCell != null)tempCell.setWalkable(false);
				}else if(map[x][y] == 0){
					if(map[x][y+1] == 1){
						if(map[x-1][y+1] == 1 && map[x-1][y] == 1){
							//Inner corner shadow
							tempCell = new MapCell(mapping[4][7], x*128, y*128);
							tempCell.addLayer(mapping[1][1]);
							isShadow = true;
						}else if(map[x-1][y+1] == 0&& map[x-1][y] == 0){
							//End shadow left
							tempCell = new MapCell(mapping[4][7], x*128, y*128);
							tempCell.addLayer(mapping[2][5]);
							isShadow = true;
						}else{
							//Top shadow
							tempCell = new MapCell(mapping[4][7], x*128, y*128);
							tempCell.addLayer(mapping[1][2]);
							isShadow = true;
						}
					}else if(map[x-1][y] == 1){
						if(map[x-1][y+1] == 1 && map[x][y+1] == 0){
							//Left shadow
							tempCell = new MapCell(mapping[4][7], x*128, y*128);
							tempCell.addLayer(mapping[2][1]);
							isShadow = true;
						}else if(map[x-1][y+1] == 0 && map[x][y+1] == 0){
							//End shadow top
							tempCell = new MapCell(mapping[4][7], x*128, y*128);
							tempCell.addLayer(mapping[0][7]);
							isShadow = true;
						}
						isShadow = true;
					}else if(map[x-1][y] == 0){
						if(map[x-1][y+1] == 1 && map[x][y+1] == 0){
							//Outer corner shadow
							tempCell = new MapCell(mapping[4][7], x*128, y*128);
							tempCell.addLayer(mapping[2][7]);
							isShadow = true;
						}
					}
					if(!isShadow){
						variation = rand.nextInt(100)+1;
						if(variation >85){
							if(variation >98){
								variation = rand.nextInt(2);
								tempCell = new MapCell(mapping[4][5+variation], x*128, y*128);
							}else{
								variation = rand.nextInt(3);
								tempCell = new MapCell(mapping[3][5+variation], x*128, y*128);
							}
						}else{
							tempCell = new MapCell(mapping[4][7], x*128, y*128);
						}
					}
				}
				if(tempCell == null){
					map[x][y] = 0;
					return fillGraphics(sheet, map);
				}
				graphicMap[x][y] = tempCell;
			}
		}
		return graphicMap;
	}
	
	public int[] getRoomSize(){
		int[] temp = roomSize;
		return temp;
	}
	
	public Room[][] getLayout(){
		return layout;
	}
	public void setLayout( Room[][] layout){
		this.layout = layout;
	}
	
	public boolean[] getWalkableDirs(int[] pos) {
		boolean[] dirs = new boolean[5];
		if(numericMap[pos[0]][pos[1]+1] == 0) {
			dirs[0] = true;
		}
		if(numericMap[pos[0]+1][pos[1]] == 0) {
			dirs[1] = true;
		}
		if(numericMap[pos[0]][pos[1]-1] == 0) {
			dirs[2] = true;
		}
		if(numericMap[pos[0]-1][pos[1]] == 0) {
			dirs[3] = true;
		}
		if(numericMap[pos[0]][pos[1]] == 0) {
			dirs[4] = true;
		}
		return dirs;
	}
	
	public AStarMapNode[][] getAStarMap(int[] goalPos, int[] sourcePos) {
		AStarMapNode[][] nodeMap = new AStarMapNode[Math.abs(goalPos[0]-sourcePos[0])+10][Math.abs(goalPos[1]-sourcePos[1])+10];
		
		if(goalPos[0] < sourcePos[0]) {
			if(goalPos[1] > sourcePos[1] ) {
				for(int y = -(Math.abs(goalPos[1]-sourcePos[1])+5); y < 5; y++) {
					for(int x = -5; x < nodeMap.length-5; x++) {
						nodeMap[x+5][y+Math.abs(goalPos[1]-sourcePos[1])+5] = new AStarMapNode(new int[] {goalPos[0]+x,goalPos[1]+y}, goalPos, new int[] {x+5,y+Math.abs(goalPos[1]-sourcePos[1])+5});
					}
				}
			}else {
				for(int y = -5; y < nodeMap[0].length-5; y++) {
					for(int x = -5; x < nodeMap.length-5; x++) {
						nodeMap[x+5][y+5] = new AStarMapNode(new int[] {goalPos[0]+x,goalPos[1]+y}, goalPos, new int[] {x+5,y+5});
					}
				}
			}
				
		}else {
			if(goalPos[1] < sourcePos[1] ) {
				for(int y = -(Math.abs(goalPos[1]-sourcePos[1])+5); y < 5; y++) {
					for(int x = -5; x < nodeMap.length-5; x++) {
						nodeMap[x+5][y+Math.abs(goalPos[1]-sourcePos[1])+5] = new AStarMapNode(new int[] {sourcePos[0]+x,sourcePos[1]+y}, goalPos, new int[] {x+5,y+Math.abs(goalPos[1]-sourcePos[1])+5});
					}
				}
			}else {
				for(int y = -5; y < nodeMap[0].length-5; y++) {
					for(int x = -5; x < nodeMap.length-5; x++) {
						nodeMap[x+5][y+5] = new AStarMapNode(new int[] {sourcePos[0]+x,sourcePos[1]+y}, goalPos, new int[] {x+5,y+5});
					}
				}
			}
		}
		return nodeMap;
	}
}
