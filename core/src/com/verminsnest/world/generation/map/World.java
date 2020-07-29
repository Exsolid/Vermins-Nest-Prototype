package com.verminsnest.world.generation.map;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.verminsnest.core.VerminsNest;
import com.verminsnest.core.management.Indentifiers;
import com.verminsnest.core.management.LoadingModule;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.world.generation.map.rooms.Room;
import com.verminsnest.world.generation.map.rooms.Shop;

public class World extends LoadingModule{

	private int roomCount;
	private int maxRoomSizeX;
	private int maxRoomSizeY;
	private int minRoomSize;
	private Texture sheet;
	private MapData map;
	
	private Random rand;

	public World(VerminsNest game) {
		super(game.getConfig().getMessage("LoadingScreen_MapGen"));
		rand = new Random();
	}
	
	@Override
	public void execute() {
		//For room count
		map = new MapData(new int[]{maxRoomSizeX,maxRoomSizeY});
		int roomCounter = 0;
		while(roomCounter < roomCount){
			for(int currentRoom = roomCounter; currentRoom < roomCount; currentRoom++){
					boolean found = false;
					//Find room with empty connectors
					int randRoom = 0;
					if(map.getRooms().size()-1>0){
						randRoom = rand.nextInt(map.getRooms().size()-1);	
					}
					ArrayList<Integer> possibleDirs = new ArrayList<>();
					int randDir = 0;
					while(!found && !map.getRooms().isEmpty()){
						int dirCounter = 0;
						for(Room connected: map.getRooms().get(randRoom).getConnected()){
							if(connected == null)possibleDirs.add(dirCounter);
							dirCounter +=1;
						}
						if(!possibleDirs.isEmpty()){
							found = true;
	
							if(possibleDirs.size()-1>0){
								randDir = rand.nextInt(possibleDirs.size()-1);
							}
						}
						else{
							if(map.getRooms().size()-1>0){
								randRoom = rand.nextInt(map.getRooms().size()-1);	
							}
						}
					}
					if(!possibleDirs.isEmpty()){
						randDir = possibleDirs.get(randDir);
					}
					
					if(genPathing(randDir, randRoom,roomCounter)){
						roomCounter++;
					}
				
			}
		}
		map.computeData(maxRoomSizeX, maxRoomSizeY, sheet);
		RuntimeData.getInstance().setMap(map);
		this.setDone();
	}
	
	private boolean genPathing(int randDir, int randRoom, int currentRoom){
		//Gen pathing
		if(currentRoom == 0){
			//Gen room
			map.addRoom(new Shop(new int[maxRoomSizeX][maxRoomSizeY], new int[]{0,0}));
			map.getLayout()[map.getRooms().get(currentRoom).getLayoutPos()[0]][map.getRooms().get(currentRoom).getLayoutPos()[1]] = map.getRooms().get(currentRoom);
		}else{
			int[] pos;
			int[] newRoomPos;
			switch(randDir){
			case Indentifiers.DIRECTION_NORTH:
				if(map.getLayout()[0].length == map.getRooms().get(randRoom).getLayoutPos()[1]+1){
					this.updateLayout(Indentifiers.DIRECTION_NORTH);
				}else if(map.getLayout()[map.getRooms().get(randRoom).getLayoutPos()[0]][map.getRooms().get(randRoom).getLayoutPos()[1]+1] != null){
					return false;
				}
				map.addRoom(new Room(new int[maxRoomSizeX][maxRoomSizeY],minRoomSize, new int[]{map.getRooms().get(randRoom).getLayoutPos()[0],map.getRooms().get(randRoom).getLayoutPos()[1]+1}));
				map.getLayout()[map.getRooms().get(currentRoom).getLayoutPos()[0]][map.getRooms().get(currentRoom).getLayoutPos()[1]] = map.getRooms().get(currentRoom);
				pos = map.getRooms().get(randRoom).getClosestToDirection(Indentifiers.DIRECTION_NORTH);
				map.getRooms().get(randRoom).getConnected()[Indentifiers.DIRECTION_NORTH] = map.getRooms().get(currentRoom);
				newRoomPos = map.getRooms().get(currentRoom).getClosestToDirection(Indentifiers.DIRECTION_SOUTH);
				map.getRooms().get(currentRoom).getConnected()[Indentifiers.DIRECTION_SOUTH] = map.getRooms().get(randRoom);
				//Path y in random room
				while(pos[1] < maxRoomSizeY-1){
					pos[1]++;
					map.getRooms().get(randRoom).getData()[pos[0]][pos[1]] = 0;
				}
				//Path x in old room
				if(pos[0] > newRoomPos[0]){
					while(pos[0] > newRoomPos[0]){
						pos[0]--;		
						map.getRooms().get(randRoom).getData()[pos[0]][pos[1]] = 0;
					}
				}else{
					while(pos[0] < newRoomPos[0]){
						pos[0]++;		
						map.getRooms().get(randRoom).getData()[pos[0]][pos[1]] = 0;
					}
				}
				//Path y in new room
				pos[1] = newRoomPos[1];
				while(pos[1] > 0){
					pos[1]--;		
					map.getRooms().get(currentRoom).getData()[pos[0]][pos[1]] = 0;
				}
				break;
			case Indentifiers.DIRECTION_EAST:
				if(map.getLayout().length == map.getRooms().get(randRoom).getLayoutPos()[0]+1){
					this.updateLayout(Indentifiers.DIRECTION_EAST);
				}else if(map.getLayout()[map.getRooms().get(randRoom).getLayoutPos()[0]+1][map.getRooms().get(randRoom).getLayoutPos()[1]] != null){
					return false;
				}
				map.addRoom(new Room(new int[maxRoomSizeX][maxRoomSizeY],minRoomSize, new int[]{map.getRooms().get(randRoom).getLayoutPos()[0]+1,map.getRooms().get(randRoom).getLayoutPos()[1]}));
				map.getLayout()[map.getRooms().get(currentRoom).getLayoutPos()[0]][map.getRooms().get(currentRoom).getLayoutPos()[1]] = map.getRooms().get(currentRoom);
				pos = map.getRooms().get(randRoom).getClosestToDirection(Indentifiers.DIRECTION_EAST);
				map.getRooms().get(randRoom).getConnected()[Indentifiers.DIRECTION_EAST] = map.getRooms().get(currentRoom);
				newRoomPos = map.getRooms().get(currentRoom).getClosestToDirection(Indentifiers.DIRECTION_WEST);
				map.getRooms().get(currentRoom).getConnected()[Indentifiers.DIRECTION_WEST] = map.getRooms().get(randRoom);
				//Path x in random room
				while(pos[0] < maxRoomSizeX-1){
					pos[0]++;
					map.getRooms().get(randRoom).getData()[pos[0]][pos[1]] = 0;
				}
				//Path y in new room
				if(pos[1] > newRoomPos[1]){
					while(pos[1] > newRoomPos[1]){
						pos[1]--;		
						map.getRooms().get(randRoom).getData()[pos[0]][pos[1]] = 0;
					}
				}else{
					while(pos[1] < newRoomPos[1]){
						pos[1]++;		
						map.getRooms().get(randRoom).getData()[pos[0]][pos[1]] = 0;
					}
				}
				//Path x in new room
				pos[0] = newRoomPos[0];
				while(pos[0] > 0){
					pos[0]--;		
					map.getRooms().get(currentRoom).getData()[pos[0]][pos[1]] = 0;
				}
				break;
			case Indentifiers.DIRECTION_SOUTH:
				if(0 > map.getRooms().get(randRoom).getLayoutPos()[1]-1){
					this.updateLayout(Indentifiers.DIRECTION_SOUTH);
				}else if(map.getLayout()[map.getRooms().get(randRoom).getLayoutPos()[0]][map.getRooms().get(randRoom).getLayoutPos()[1]-1] != null){
					return false;
				}
				map.addRoom(new Room(new int[maxRoomSizeX][maxRoomSizeY],minRoomSize, new int[]{map.getRooms().get(randRoom).getLayoutPos()[0],map.getRooms().get(randRoom).getLayoutPos()[1]-1}));
				map.getLayout()[map.getRooms().get(currentRoom).getLayoutPos()[0]][map.getRooms().get(currentRoom).getLayoutPos()[1]] = map.getRooms().get(currentRoom);
				pos = map.getRooms().get(randRoom).getClosestToDirection(Indentifiers.DIRECTION_SOUTH);
				map.getRooms().get(randRoom).getConnected()[Indentifiers.DIRECTION_SOUTH] = map.getRooms().get(currentRoom);
				newRoomPos = map.getRooms().get(currentRoom).getClosestToDirection(Indentifiers.DIRECTION_NORTH);
				map.getRooms().get(currentRoom).getConnected()[Indentifiers.DIRECTION_NORTH] = map.getRooms().get(randRoom);
				//Path y in random room
				while(pos[1] > 0){
					pos[1]--;
					map.getRooms().get(randRoom).getData()[pos[0]][pos[1]] = 0;
				}
				//Path x in new room
				if(pos[0] > newRoomPos[0]){
					while(pos[0] > newRoomPos[0]){
						pos[0]--;		
						map.getRooms().get(randRoom).getData()[pos[0]][pos[1]] = 0;
					}
				}else{
					while(pos[0] < newRoomPos[0]){
						pos[0]++;		
						map.getRooms().get(randRoom).getData()[pos[0]][pos[1]] = 0;
					}
				}
				//Path y in new room
				pos[1] = maxRoomSizeY-1;
				while(pos[1] > newRoomPos[1]){
					pos[1]--;		
					map.getRooms().get(currentRoom).getData()[pos[0]][pos[1]] = 0;
				}
				break;
			case Indentifiers.DIRECTION_WEST:
				if(0 > map.getRooms().get(randRoom).getLayoutPos()[0]-1){
					this.updateLayout(Indentifiers.DIRECTION_WEST);
				}else if(map.getLayout()[map.getRooms().get(randRoom).getLayoutPos()[0]-1][map.getRooms().get(randRoom).getLayoutPos()[1]] != null){
					return false;
				}
				this.updateLayout(Indentifiers.DIRECTION_WEST);
				map.addRoom(new Room(new int[maxRoomSizeX][maxRoomSizeY],minRoomSize, new int[]{map.getRooms().get(randRoom).getLayoutPos()[0]-1,map.getRooms().get(randRoom).getLayoutPos()[1]}));
				map.getLayout()[map.getRooms().get(currentRoom).getLayoutPos()[0]][map.getRooms().get(currentRoom).getLayoutPos()[1]] = map.getRooms().get(currentRoom);
				pos = map.getRooms().get(randRoom).getClosestToDirection(Indentifiers.DIRECTION_WEST);
				map.getRooms().get(randRoom).getConnected()[Indentifiers.DIRECTION_WEST] = map.getRooms().get(currentRoom);
				newRoomPos = map.getRooms().get(currentRoom).getClosestToDirection(Indentifiers.DIRECTION_EAST);
				map.getRooms().get(currentRoom).getConnected()[Indentifiers.DIRECTION_EAST] = map.getRooms().get(randRoom);
				//Path y in random room
				while(pos[0] > 0){
					pos[0]--;
					map.getRooms().get(randRoom).getData()[pos[0]][pos[1]] = 0;
				}
				//Path x in new room
				if(pos[1] > newRoomPos[1]){
					while(pos[1] > newRoomPos[1]){
						pos[1]--;		
						map.getRooms().get(randRoom).getData()[pos[0]][pos[1]] = 0;
					}
				}else{
					while(pos[1] < newRoomPos[1]){
						pos[1]++;		
						map.getRooms().get(randRoom).getData()[pos[0]][pos[1]] = 0;
					}
				}
				//Path y in new room
				pos[0] = maxRoomSizeX-1;
				while(pos[0] > newRoomPos[0]){
					pos[0]--;		
					map.getRooms().get(currentRoom).getData()[pos[0]][pos[1]] = 0;
				}
				break;
			}
		}
		return true;
	}
	
	private void updateLayout(int dir){
		Room[][] newLayout = null;
		switch(dir){
		case Indentifiers.DIRECTION_NORTH:
			newLayout = new Room[map.getLayout().length][map.getLayout()[0].length+1];
			for(int y = 0; y < map.getLayout()[0].length; y++){
				for(int x = 0; x < map.getLayout().length; x++){
					newLayout[x][y] = map.getLayout()[x][y];
				}
			}
			map.setLayout(newLayout);
			break;
		case Indentifiers.DIRECTION_WEST:
			newLayout = new Room[map.getLayout().length+1][map.getLayout()[0].length];
			for(int y = 0; y < map.getLayout()[0].length; y++){
				for(int x = 0; x < map.getLayout().length; x++){
					newLayout[x+1][y] = map.getLayout()[x][y];
				}
			}
			map.setLayout(newLayout);
			for(Room room: map.getRooms()){
				room.getLayoutPos()[0] += 1;
			}
			break;
		case Indentifiers.DIRECTION_SOUTH:
			newLayout = new Room[map.getLayout().length][map.getLayout()[0].length+1];
			for(int y = 0; y < map.getLayout()[0].length; y++){
				for(int x = 0; x < map.getLayout().length; x++){
					newLayout[x][y+1] = map.getLayout()[x][y];
				}
			}
			map.setLayout(newLayout);
			for(Room room: map.getRooms()){
				room.getLayoutPos()[1] += 1;
			}
			break;
		case Indentifiers.DIRECTION_EAST:
			newLayout = new Room[map.getLayout().length+1][map.getLayout()[0].length];
			for(int y = 0; y < map.getLayout()[0].length; y++){
				for(int x = 0; x < map.getLayout().length; x++){
					newLayout[x][y] = map.getLayout()[x][y];
				}
			}
			map.setLayout(newLayout);
			break;
		}
	}
	
	public void setData(int roomCount, int maxRoomSizeX, int maxRoomSizeY, int minRoomSize, Texture sheet){
		this.maxRoomSizeX = maxRoomSizeX;
		this.maxRoomSizeY = maxRoomSizeY;
		this.roomCount = roomCount+1;
		this.minRoomSize = minRoomSize;
		this.sheet = sheet;
	}
}
