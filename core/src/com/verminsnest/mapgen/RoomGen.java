package com.verminsnest.mapgen;

import java.util.ArrayList;
import java.util.Random;

public class RoomGen {

	Random rand = new Random();
	public RoomGen(){
		
	}
	public int[][] genRoom(int maxWidth, int maxHeight,int minSize){
		int[][] room = new int[maxWidth][maxHeight];
		boolean accepted = false;
		
		while(!accepted){
			fillRandom(room,55);
			smoothRoom(room,2);
			ArrayList<Room> rooms = saveRooms(room);
			int size = 0;
			if(!rooms.isEmpty()){
				for(Room tempRoom: rooms){
					size += tempRoom.getRoomData().size();
				}
				if(size >= minSize){
					accepted = true;
				}
			}
		}
		return room;
	}
	
	private int[][] fillRandom(int[][] map, int fillPercantage) {
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[0].length; y++) {
				if (x < 2 || x > map.length - 2 || y < 2 || y > map[0].length - 2) {
					map[x][y] = 1;
				} else {
					int randNum = rand.nextInt(100 - 1) + 1;
					if (randNum < fillPercantage)
						map[x][y] = 1;
					else
						map[x][y] = 0;
				}
			}
		}
		return map;
	}

	private int[][] smoothRoom(int[][] room, int count) {
		while (count != 0) {
			for (int x = 1; x < room.length-1; x++) {
				for (int y = 1; y < room[0].length-1; y++) {
					int walls = getSurroundingsCount(room, x, y);
					if (walls > 4) {
						room[x][y] = 1;
					} else {
						room[x][y] = 0;
					}
				}
			}
			count--;
		}
		return room;
	}

	private int getSurroundingsCount(int[][] room, int x, int y) {
		int count = 0;
		for (int xS = x - 1; xS < x + 2; xS++) {
			for (int yS = y - 1; yS < y + 2; yS++) {
				if (xS != -1 && yS != -1 && xS != room.length && yS != room[0].length) {
					if (yS != y || xS != x) {
						count += room[xS][yS];
					}
				} else {
					count++;
				}
			}
		}
		return count;
	}
	
	public ArrayList<Room> saveRooms(int[][] map) {
		ArrayList<Room> rooms = new ArrayList<>(); 
		for (int y = map[0].length - 1; y > -1; y--) {
			for (int x = 0; x < map.length; x++) {
				if (map[x][y] == 0) {
					boolean found = false;
					for (Room room : rooms) {
						for (Integer[] allPosInRoom : room.getRoomData()) {
							if (x == allPosInRoom[0] && y == allPosInRoom[1]) {
								found = true;
							}
						}
					}
					if (!found) {
						Integer[] pos = new Integer[2];
						pos[0] = x;
						pos[1] = y;
						ArrayList<Integer[]> tempList = new ArrayList<>();
						tempList.add(pos);
						Room tempRoom = new Room(tempList);
						tempRoom = saveRoom(tempRoom, map);
						rooms.add(tempRoom);
					}
				}
			}
		}
		return rooms;
	}

	private Room saveRoom(Room room, int[][] map) {
		int x = room.getRoomData().get(room.getRoomData().size() - 1)[0];
		int y = room.getRoomData().get(room.getRoomData().size() - 1)[1];
		for (int xS = x - 1; xS < x + 2; xS++) {
			for (int yS = y - 1; yS < y + 2; yS++) {
				if (xS != x - 1 && yS != y - 1 || xS != x - 1 && yS != y + 1 || xS != x + 1 && yS != y - 1
						|| xS != x + 1 && yS != y + 1) {
					if (map[xS][yS] == 0) {
						boolean found = false;
						for (Integer[] allPosInRoom : room.getRoomData()) {
							if (xS == allPosInRoom[0] && yS == allPosInRoom[1]) {
								found = true;
							}
						}
						if (!found) {
							Integer[] pos = new Integer[2];
							pos[0] = xS;
							pos[1] = yS;
							room.getRoomData().add(pos);
							room = saveRoom(room, map);
						}
					}
				}
			}
		}
		return room;
	}
}
