package com.verminsnest.world.generation.map.rooms;

public class Shop extends Room{

	public Shop(int[][] data, int[] layoutPos) {
		super(data, layoutPos);
		//TODO log small room
		int[][] temp = new int[][] {
			{0,0,0,0,0,0},
			{0,1,0,0,1,0},
			{0,0,0,0,0,0},
			{0,1,0,0,1,0},
			{0,0,0,0,0,0},
		};
		for (int x = 0; x < data.length; x++) {
			for (int y = 0; y < data[0].length; y++) {
				if(x >= temp.length || y >= temp[0].length)data[x][y] = 1;
				else data[x][y] = temp[x][y];
			}
		}
		initFill();
	}

}
