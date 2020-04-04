package com.verminsnest.mapgen;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.core.LoadingModule;
import com.verminsnest.gamedev.VerminsNest;
import com.verminsnest.singletons.RuntimeData;

public class WorldGen extends LoadingModule{

	private Random rand = new Random();
	private ArrayList<Room> rooms;

	private int roomCount;
	private int maxRoomSizeX;
	private int maxRoomSizeY;
	private int minRoomSize;
	private Texture sheet;
	
	public WorldGen(VerminsNest game) {
		super(game.getConfig().getMessage("LoadingScreen_MapGen"));
		rooms = new ArrayList<>();
	}
	
	public void setData(int roomCount, int maxRoomSizeX, int maxRoomSizeY, int minRoomSize, Texture sheet){
		this.maxRoomSizeX = maxRoomSizeX;
		this.maxRoomSizeY = maxRoomSizeY;
		this.roomCount = roomCount;
		this.minRoomSize = minRoomSize;
		this.sheet = sheet;
	}
	
	@Override
	public void execute() {
		this.caveGen();
	}
	
	public void caveGen() {
		if(roomCount%2!=0){
			roomCount++;
		}
		int[][] map = new int[roomCount/2*maxRoomSizeX+20][roomCount/2*maxRoomSizeY+20];
		
		for (int y = map[0].length - 1; y > -1; y--) {
			for (int x = 0; x < map.length; x++) {
				map[x][y] = 1;
			}
		}
		boolean[][] mapCheck = new boolean[roomCount/2][roomCount/2];
		RoomGen roomGen = new RoomGen();
		for(int i = 0; i < roomCount; i++){
			for (int x = 0; x < mapCheck.length; x++) {
				for (int y = 0; y < mapCheck[0].length; y++) {
					if(!mapCheck[x][y]){
						int[][] room = roomGen.genRoom(maxRoomSizeX, maxRoomSizeY, minRoomSize);
						for (int xR = 0; xR < room.length; xR++) {
							for (int yR = 0; yR < room[0].length; yR++) {
								map[xR+maxRoomSizeX*x+10][yR+maxRoomSizeY*y+10] = room[xR][yR];
							}
						}
						mapCheck[x][y] = true;
					}
				}
			}
		}
		
		rooms = roomGen.saveRooms(map);
		doPaths(map);
		for (int y = map[0].length - 2; y > 0; y--) {
			for (int x = 1; x < map.length-1; x++) {
				if(map[x][y] == 1 && map[x][y+1] == 0 && map[x-1][y] == 1 && map[x+1][y] == 0 && map[x+1][y+1] == 0&& map[x-1][y+1] == 1)map[x][y] = 0;
				if(map[x][y] == 1 && map[x-1][y] == 0 && map[x][y-1] == 0 && map[x-1][y+1] == 1) map[x-1][y+1] = 0;				
			}
		}
		printArray(map);
		RuntimeData.getInstance().setMap(this.fillGraphics(sheet, map));
		this.setDone();
	}

	

	private void doPaths(int[][] map) {
		for (Room room : rooms) {
			int from = rand.nextInt(room.getRoomData().size());
			boolean diff = false;
			int roomNum = 0;
			while (!diff) {
				roomNum = rand.nextInt(rooms.size());
				if (!room.equals(rooms.get(roomNum))) {
					diff = true;
				}
			}
			int to = rand.nextInt(rooms.get(roomNum).getRoomData().size());
			Integer[] currentPos = room.getRoomData().get(from);
			int xDistance = Math.abs(currentPos[0] - rooms.get(roomNum).getRoomData().get(to)[0])+2;
			int yDistance = Math.abs(currentPos[1] - rooms.get(roomNum).getRoomData().get(to)[1])+2;
			
			while (xDistance > 0 && yDistance > 0) {
				int toWalk = rand.nextInt(yDistance)+1;
				yDistance -= toWalk;
				toWalk += 2;
				while (toWalk > 0) {
					if (currentPos[1] >= rooms.get(roomNum).getRoomData().get(to)[1]) {
						map[currentPos[0]][currentPos[1]] = 0;
						currentPos[1] = currentPos[1] - 1;
					} else if (currentPos[1] <= rooms.get(roomNum).getRoomData().get(to)[1]) {
						map[currentPos[0]][currentPos[1]] = 0;
						currentPos[1] = currentPos[1] + 1;
					}
					toWalk--;
				}
				toWalk = rand.nextInt(xDistance);
				xDistance -= toWalk;
				toWalk += 2;
				while (toWalk > 0) {
					if (currentPos[0] >= rooms.get(roomNum).getRoomData().get(to)[0]) {
						map[currentPos[0]][currentPos[1]] = 0;
						currentPos[0] = currentPos[0] - 1;
					} else if (currentPos[0] <= rooms.get(roomNum).getRoomData().get(to)[0]) {
						map[currentPos[0]][currentPos[1]] = 0;
						currentPos[0] = currentPos[0] + 1;
					}
					toWalk--;
				}
			}
		}
	}
	
	private void printArray(int[][] map){
		for (int y = map[0].length - 1; y > -1; y--) {
			for (int x = 0; x < map.length; x++) {
				if(map[x][y] == 0){
					System.out.print("[x]");
				}else{
					System.out.print("[-]");
				}
			}
			System.out.print("\n");
		}
	}

	

	// Set all graphics
	public MapCell[][] fillGraphics(Texture sheet, int[][] map) {
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
}
