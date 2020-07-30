package com.verminsnest.world.generation.spawning;

import java.util.ArrayList;
import java.util.Random;

import com.verminsnest.core.management.Indentifiers;
import com.verminsnest.core.management.LoadingModule;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.world.generation.map.rooms.Room;
import com.verminsnest.world.generation.map.rooms.Shop;

public class UtilSpawner extends LoadingModule{
	private Random rand;
	public UtilSpawner() {
		super(RuntimeData.getInstance().getGame().getConfig().getMessage("LoadingScreen_UtilGen"));
		rand = new Random();
	}

	@Override
	public void execute() {
		//Spawn shop stuff
		int pos[] = new int[2];
		for(Room room: RuntimeData.getInstance().getMapData().getRooms()) {
			if(room instanceof Shop) {
				pos[0] = room.getLayoutPos()[0]+1;
				pos[1] = room.getLayoutPos()[1]+1;
			}
		}
		pos[0] = (pos[0]*RuntimeData.getInstance().getMapData().getRooms().get(0).getData().length-RuntimeData.getInstance().getMapData().getRooms().get(0).getData().length/2+10)*128+64-18;
		pos[1] = (pos[1]*RuntimeData.getInstance().getMapData().getRooms().get(0).getData()[0].length-RuntimeData.getInstance().getMapData().getRooms().get(0).getData()[0].length/2+10)*128-50;
		RuntimeData.getInstance().getEntityManager().addUtilInit(pos[0], pos[1], Indentifiers.UTIL_SHOPKEEPER);
		
		ArrayList<Integer> itemIDs = Indentifiers.getItemPoolShop();
		int[] dirs = new int[] {1,1,1,1};
		for(int i = 0; i<3;i++) {
			int randomID = itemIDs.get(rand.nextInt(itemIDs.size()));
			int randDir = rand.nextInt(4);
			while(dirs[randDir] == 0) {
				randDir = rand.nextInt(4);
			}
			dirs[randDir] = 0;
			switch(randDir) {
			case Indentifiers.DIRECTION_NORTH:
				RuntimeData.getInstance().getEntityManager().addUtilInit(pos[0], pos[1]+rand.nextInt(100)+80, randomID);
				break;
			case Indentifiers.DIRECTION_EAST:
				RuntimeData.getInstance().getEntityManager().addUtilInit(pos[0]+rand.nextInt(100)+80, pos[1], randomID);
				break;
			case Indentifiers.DIRECTION_SOUTH:
				RuntimeData.getInstance().getEntityManager().addUtilInit(pos[0], pos[1]-rand.nextInt(100)-80, randomID);
				break;
			case Indentifiers.DIRECTION_WEST:
				RuntimeData.getInstance().getEntityManager().addUtilInit(pos[0]-rand.nextInt(100)-80, pos[1], randomID);
				break;
			}
		}
		setDone();
	}

}
