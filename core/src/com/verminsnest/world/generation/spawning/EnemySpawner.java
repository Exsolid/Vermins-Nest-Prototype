package com.verminsnest.world.generation.spawning;

import java.util.ArrayList;
import java.util.Random;

import com.verminsnest.core.management.Indentifiers;
import com.verminsnest.core.management.LoadingModule;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.world.generation.map.MapData;
import com.verminsnest.world.generation.map.Room;

public class EnemySpawner extends LoadingModule{
	private MapData mapData;
	private int enemiesEachRoom;
	
	public EnemySpawner(int enemiesEachRoom) {
		super(RuntimeData.getInstance().getGame().getConfig().getMessage("LoadingScreen_EnemyGen"));
		this.enemiesEachRoom = enemiesEachRoom;
	}

	@Override
	public void execute() {
		Random rand = new Random();
		mapData = RuntimeData.getInstance().getMapData();
		for(Room room: mapData.getRooms()){
			for(int i = 0; i<enemiesEachRoom;i++){
				int[] pos = new int[]{0,0};
				int[] pixlePos = new int[]{0,0};
				boolean found = true;
				while(room.getData()[pos[0]][pos[1]] != 0 || found == false){
					pos = new int[]{rand.nextInt(room.getData().length),rand.nextInt(room.getData().length)};	
					pixlePos[1] = (room.getData()[0].length*room.getLayoutPos()[1]+pos[1]+10)*128+rand.nextInt(20);
					pixlePos[0] = (room.getData().length*room.getLayoutPos()[0]+pos[0]+10)*128+rand.nextInt(64);
					found = true;
					for(int[] refPos: RuntimeData.getInstance().getEntityManager().getEnemyInits()){
						if(pixlePos[0]>refPos[0]-80 && pixlePos[0] < refPos[0]+80 &&
								pixlePos[1]>refPos[1]-100 && pixlePos[1] < refPos[1]+100){
							found = false;
						}
					}
				}
				ArrayList<Integer> ids = Indentifiers.getAllEnemyIDs();
				RuntimeData.getInstance().getEntityManager().addEnemiesInit(pixlePos[0], pixlePos[1], ids.get(rand.nextInt(ids.size())));
			}
		}
		this.setDone();
	}
	
}
