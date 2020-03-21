package com.verminsnest.mapgen;

import java.util.ArrayList;

public class Room {
	
	private ArrayList<Integer[]> roomData;
	public Room(ArrayList<Integer[]> roomData){
		this.setRoomData(roomData);
	}
	public ArrayList<Integer[]> getRoomData() {
		return roomData;
	}
	public void setRoomData(ArrayList<Integer[]> roomData) {
		this.roomData = roomData;
	}
}
