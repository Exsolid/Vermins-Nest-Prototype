package com.verminsnest.core.management;

import java.util.ArrayList;

public class Indentifiers {
	
	//Direction IDs
	public static final int DIRECTION_NORTH = 0;
	public static final int DIRECTION_EAST = 1;
	public static final int DIRECTION_SOUTH = 2;
	public static final int DIRECTION_WEST = 3;
	
	//Animation IDs
	public static final int STATE_WALK_NORTH = 0;
	public static final int STATE_WALK_EAST = 1;
	public static final int STATE_WALK_SOUTH = 2;
	public static final int STATE_WALK_WEST = 3;
	public static final int STATE_ATTACK_NORTH = 4;
	public static final int STATE_ATTACK_EAST = 5;
	public static final int STATE_ATTACK_SOUTH = 6;
	public static final int STATE_ATTACK_WEST = 7;
	public static final int STATE_IDLE = 8;
	
	public static final int STATE_CAST = 9;
	public static final int STATE_FLYING = 10;
	public static final int STATE_HIT = 11;
	public static final int STATE_TODELETE = 12;
	public static final int STATE_BREAK = 13;
	
	public static final int STATE_SPAWNING = 13;
	public static final int STATE_LEFTOVER = 14;
	
	//Dialog IDs
	public static final int DIALOG_OPEN = 0;
	public static final int DIALOG_CANCEL = 1;
	public static final int DIALOG_OKAY = 2;
	public static final int ITEMDIALOG = 3;
	
	//Assetmanager stuff
	public final static int ASSETMANAGER_GAMEPLAY = 1;
	public final static int ASSETMANAGER_INIT = 8;
	
	public final static int ASSETMANAGER_SHOPKEEPER = 12;
	public final static int ASSETMANAGER_MAGE = 2;
	
	public final static int ASSETMANAGER_FIREBALL = 3;
	public final static int ASSETMANAGER_SLASH_SMALL = 4;
	public final static int ASSETMANAGER_EXPLOSION_SMALL = 10;
	public final static int ASSETMANAGER_BULLET_GREEN = 13;
	
	public final static int ASSETMANAGER_TINKER = 5;
	public static final int ASSETMANAGER_FLUNK = 9;
	public final static int ASSETMANAGER_EGG = 6;
	
	public final static int ASSETMANAGER_GORE = 7;
	public final static int ASSETMANAGER_SHELL = 15;
	
	public final static int ASSETMANAGER_PUDDLE_BLOOD = 16;
	
	public final static int ASSETMANAGER_BARRIER_BLUE = 11;
	public final static int ASSETMANAGER_TURRET_MECHA= 14;

	//Enemy IDs
	public static final int ENEMY_TINKER = 0;
	public static final int ENEMY_FLUNK = 1;
	
	//Util entity IDs
	public static final int UTIL_SHOPKEEPER = 0;
	public static final int UTIL_ITEM_BARRIER_BLUE = 1;
	public static final int UTIL_ITEM_TURRET_MECHA = 2;
	
	//Item IDs
	
	public static ArrayList<Integer> getAllEnemyIDs(){
		ArrayList<Integer> ids = new ArrayList<>();
		ids.add(ENEMY_TINKER);
		ids.add(ENEMY_FLUNK);
		return ids;
	}
	
	public static ArrayList<Integer> getItemPoolShop(){
		ArrayList<Integer> ids = new ArrayList<>();
		ids.add(UTIL_ITEM_BARRIER_BLUE);
		ids.add(UTIL_ITEM_TURRET_MECHA);
		return ids;
	}
}
