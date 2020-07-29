package com.verminsnest.world.generation.spawning;

import com.verminsnest.core.management.Indentifiers;
import com.verminsnest.core.management.LoadingModule;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.world.generation.map.rooms.Room;
import com.verminsnest.world.generation.map.rooms.Shop;

public class UtilSpawner extends LoadingModule{

	public UtilSpawner() {
		super(RuntimeData.getInstance().getGame().getConfig().getMessage("LoadingScreen_UtilGen"));
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
		setDone();
	}

}
