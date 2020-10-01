package com.verminsnest.world.generation.map.rooms;

import com.verminsnest.core.VNLogger;

public class Shop extends Room{

	public Shop(int[][] data, int[] layoutPos) {
		super(data, layoutPos);
		int[][] temp = new int[][] {
			{0,0,0,0,0,0},
			{0,1,0,0,1,0},
			{0,0,0,0,0,0},
			{0,1,0,0,1,0},
			{0,0,0,0,0,0},
		};
		if(data[0].length*data.length <temp[0].length*temp.length){
			VNLogger.log("The size of the room to generate is to small", this.getClass());
		}
		for (int x = 0; x < data.length; x++) {
			for (int y = 0; y < data[0].length; y++) {
				if(x >= temp.length || y >= temp[0].length)data[x][y] = 1;
				else data[x][y] = temp[x][y];
			}
		}
		initFill();
	}

}
