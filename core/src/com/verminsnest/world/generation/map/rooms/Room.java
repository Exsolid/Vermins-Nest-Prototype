package com.verminsnest.world.generation.map.rooms;

import java.util.ArrayList;
import java.util.Random;

import com.verminsnest.core.management.Indentifiers;


public class Room {
	private Room[] connected;
	private int[][] data;
	private Random rand;
	private int[] layoutPos;
	private boolean found;
	
	private ArrayList<Socket> sockets;
	/**
	 * Creates a new room which fills an int[][] with 0's and 1's (0 = is a walkable tile; 1 = is a unwalkable tile)
	 * @param data the empty array
	 * @param minSize the minimum size which the room has to have. Must be smaller than length*width of data
	 * @param layoutPos the position of the room in the layout of the map
	 */
	public Room(int[][] data, int minSize,int[] layoutPos){
		found = false;
		rand = new Random();
		connected = new Room[4];
		this.data = data;
		this.layoutPos = layoutPos;
		
		sockets = new ArrayList<>();
		initRandom(minSize);
		
	}
	
	/**
	 * Creates a new room which fills an int[][] with 0's and 1's (0 = is a walkable tile; 1 = is a unwalkable tile)
	 * Used for pre-fabricated.
	 * @param data the empty array
	 * @param minSize the minimum size which the room has to have. Must be smaller than length*width of data
	 * @param layoutPos the position of the room in the layout of the map
	 */
	protected Room(int[][] data,int[] layoutPos){
		found = false;
		rand = new Random();
		connected = new Room[4];
		this.data = data;
		this.layoutPos = layoutPos;
		
		sockets = new ArrayList<>();
	}
	
	protected void initRandom(int minSize) {
		createRandom(minSize);
		
		//Search for any sockets which isn't connected to the main part
		searchSockets();
		//If sockets are found, connect them
		doPaths();
	}
	
	protected void initFill() {
		createFill();
		
		//Search for any sockets which isn't connected to the main part
		searchSockets();
		//If sockets are found, connect them
		doPaths();
	}
	
	private void createFill() {
		int tempWidthPos = 0;
		int tempHeightPos = 0;
		int width = 0;
		int height = 0;
		boolean xCounter = false;
		boolean yCounter = false;
		//Find width and height of given
		for (int x = 0; x < data.length; x++) {
			for (int y = 0; y < data[0].length; y++) {
				if(data[x][y] == 0 && yCounter == false) {
					yCounter = true;
					tempHeightPos = y;
				}else if(data[x][y] == 0) {
					if(height < y-tempHeightPos)height = y-tempHeightPos;
				}
			}
			yCounter = false;
		}
		for (int y = 0; y < data[0].length; y++) {
			for (int x = 0; x < data.length; x++) {
				if(data[x][y] == 0 && xCounter == false) {
					xCounter = true;
					tempWidthPos = x;
				}else if(data[x][y] == 0) {
					if(width < x-tempWidthPos)width = x-tempWidthPos;
				}
			}
			xCounter = false;
		}
		int[][] data = new int[this.data.length][this.data[0].length];
		int xRest =(this.data.length-width)/2;
		int yRest =(this.data[0].length-height)/2;
		//Fill walls y
				for (int y = 0; y < data[0].length; y++) {
					for (int x = 0; x < data.length; x++) {
						data[x][y] = 1;
					}
				}
		//Fill old
		for (int y = yRest; y < data[0].length-yRest+1; y++) {
			for (int x = xRest; x < data.length-xRest+1; x++) {
				data[x][y] = this.data[x-xRest][y-yRest];
			}
		}
		this.data = data;
	}
	
	private void createRandom(int minSize) {
		boolean accepted = false;
		while(!accepted){
			//Fills the int[][] with 1's and 0's with certain percentage for it to be 0
			fillRandom(55);
			//Replaces each cell with 1 or 0 depending on how its neighbors are set with an iteration of 2
			smoothRoom(2);
			int size = 0;
			//Get the count of walkable cells
			for (int x = 0; x < data.length; x++) {
				for (int y = 0; y < data[0].length; y++) {
					if(data[x][y] == 0)size++;
				}
			}
			//Accepts rooms if size requirements are met
			if(size >= minSize){
				accepted = true;
			}
		}
	}
	
	
	public int[][] getData() {
		return data;
	}

	public Room[] getConnected() {
		return connected;
	}
	
	public int[] getClosestToDirection(int direction){
		int[] biggest = null;
		switch(direction){
		case Indentifiers.DIRECTION_NORTH:
			biggest = new int[]{0,0};
			for (int x = 0; x < data.length; x++) {
				for (int y = 0; y < data[0].length; y++) {
					if(data[x][y] == 0&& y > biggest[1]){
						biggest = new int[]{x,y};
					}
				}	
			}
			break;
		case Indentifiers.DIRECTION_EAST:
			biggest = new int[]{0,0};
			for (int x = 0; x < data.length; x++) {
				for (int y = 0; y < data[0].length; y++) {
					if(data[x][y] == 0 && x > biggest[0]) biggest = new int[]{x,y};
				}	
			}
			break;
		case Indentifiers.DIRECTION_SOUTH:
			biggest = new int[]{data.length-1,data[0].length-1};
			for (int x = 0; x < data.length; x++) {
				for (int y = 0; y < data[0].length; y++) {
					if(data[x][y] == 0 && y < biggest[1]) biggest = new int[]{x,y};
				}	
			}
			break;
		case Indentifiers.DIRECTION_WEST:
			biggest = new int[]{data.length-1,data[0].length-1};
			for (int x = 0; x < data.length; x++) {
				for (int y = 0; y < data[0].length; y++) {
					if(data[x][y] == 0 && x < biggest[0]) biggest = new int[]{x,y};
				}	
			}
			break;
		}
		return biggest;
	}
	
	private void fillRandom(int fillPercantage) {
		for (int x = 0; x < data.length; x++) {
			for (int y = 0; y < data[0].length; y++) {
				if (x < 2 || x > data.length - 2 || y < 2 || y > data[0].length - 2) {
					data[x][y] = 1;
				} else {
					int randNum = rand.nextInt(100 - 1) + 1;
					if (randNum < fillPercantage)
						data[x][y] = 1;
					else
						data[x][y] = 0;
				}
			}
		}
	}

	private void smoothRoom(int count) {
		while (count != 0) {
			for (int x = 1; x < data.length-1; x++) {
				for (int y = 1; y < data[0].length-1; y++) {
					int walls = getSurroundingsCount(data, x, y);
					if (walls > 4) {
						data[x][y] = 1;
					} else {
						data[x][y] = 0;
					}
				}
			}
			count--;
		}
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
	
	private void searchSockets(){
		//Search all positions in data
		for (int x = 0; x < data.length; x++) {
			for (int y = 0; y < data[0].length; y++) {
				if(data[x][y] == 0){
					//Compare to all data in other sockets
					Integer[] temp = new Integer[]{x,y};
					boolean found = false;
					for(Socket socket: sockets){
						if(socket.contains(temp)){
							found = true;
						};
					}
					if(!found){
						//If a cut of cells was found, add all the connected cells and create a new socket
						addToSocket(temp, null);
					}
				}
			}
		}
	}
	
	private void addToSocket(Integer[] pos, Socket socket){
		if(socket == null){
			socket = new Socket();
			sockets.add(socket);
		}
		if(!socket.contains(pos)){
			socket.data.add(pos);
			if(data[pos[0]-1][pos[1]] == 0){
				addToSocket(new Integer[]{pos[0]-1,pos[1]}, socket);
			}
			if(data[pos[0]+1][pos[1]] == 0){
				addToSocket(new Integer[]{pos[0]+1,pos[1]}, socket);
			}
			if(data[pos[0]][pos[1]-1] == 0){
				addToSocket(new Integer[]{pos[0]-1,pos[1]-1}, socket);
			}
			if(data[pos[0]][pos[1]+1] == 0){
				addToSocket(new Integer[]{pos[0],pos[1]+1}, socket);
			}
		}
	}
	
	private void doPaths(){
		int[] midPos = new int[]{data.length/2,data[0].length/2};
		for(Socket socket: sockets){
			int randData = rand.nextInt(socket.data.size());
			int[] randPos = new int[]{socket.data.get(randData)[0],socket.data.get(randData)[1]};
			if(randPos[0] > midPos[0]){
				while(randPos[0] > midPos[0]){
					randPos[0]--;
					data[randPos[0]][randPos[1]] = 0;
				}
			}else{
				while(randPos[0] < midPos[0]){
					randPos[0]++;
					data[randPos[0]][randPos[1]] = 0;
				}
			}
			
			if(randPos[1] > midPos[1]){
				while(randPos[1] > midPos[1]){
					randPos[1]--;
					data[randPos[0]][randPos[1]] = 0;
				}
			}else{
				while(randPos[1] < midPos[1]){
					randPos[1]++;
					data[randPos[0]][randPos[1]] = 0;
				}
			}
		}
	}
	
	public int[] getLayoutPos() {
		return layoutPos;
	}
	
	public boolean isFound() {
		return found;
	}

	public void setFound(boolean found) {
		this.found = found;
	}

	private class Socket{
		private ArrayList<Integer[]> data = new ArrayList<>();
		
		private boolean contains(Integer[] pos){
			for(Integer[] dataPos: data){
				if(dataPos[0] == pos[0] && dataPos[1] == pos[1]){
					return true;
				}
			}
			return false;
		}
	}
}
