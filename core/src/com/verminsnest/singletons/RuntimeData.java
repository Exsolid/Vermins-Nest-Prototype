package com.verminsnest.singletons;

import com.verminsnest.mapgen.MapCell;

public class RuntimeData {

	private static RuntimeData instance;
	private MapCell[][] map;
	
	private RuntimeData(){
		
	}
	
	public static RuntimeData getInstance(){
		if(instance ==  null)instance = new RuntimeData();
		return instance;
	}

	public MapCell[][] getMap() {
		return map;
	}

	public void setMap(MapCell[][] map) {
		this.map = map;
	}
}
