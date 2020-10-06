package com.verminsnest.core.management.ids;

import java.util.ArrayList;
import java.util.List;

public class Pools {
	
	public static List<Integer> getEnemyIDs(){
		ArrayList<Integer> temp = new ArrayList<>();
		temp.add(Indentifiers.ASSETMANAGER_TINKER);
		temp.add(Indentifiers.ASSETMANAGER_FLUNK);
		return temp;
	}
	
	public static List<Integer> getAllItemIDs(){
		ArrayList<Integer> temp = new ArrayList<>();
		temp.add(Indentifiers.ASSETMANAGER_TURRET_MECHA);
		temp.add(Indentifiers.ASSETMANAGER_BARRIER_BLUE);
		return temp;
	}
	
	public static List<Integer> getShopItemIDs(){
		return getAllItemIDs();
		//TODO more items for shop only
	}
	
	public static List<Integer> getMainMenuSongIDs(){ 
		ArrayList<Integer> temp = new ArrayList<>();
		temp.add(Indentifiers.ASSETMANAGER_AUDIO_ADVENTURE);
		return temp;
	}
	
	public static List<Integer> getDungeonSongIDs(){ 
		ArrayList<Integer> temp = new ArrayList<>();
		temp.add(Indentifiers.ASSETMANAGER_AUDIO_INTOTHEUNKOWN);
		return temp;
	}
}
