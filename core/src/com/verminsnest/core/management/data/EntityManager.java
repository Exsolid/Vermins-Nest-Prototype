package com.verminsnest.core.management.data;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.verminsnest.core.VNLogger;
import com.verminsnest.core.VerminsNest;
import com.verminsnest.core.engine.PositionSystem;
import com.verminsnest.core.management.ids.Indentifiers;
import com.verminsnest.entities.Entity;
import com.verminsnest.entities.eggs.Egg;
import com.verminsnest.entities.enemies.Enemy;
import com.verminsnest.entities.enemies.Flunk;
import com.verminsnest.entities.enemies.Tinker;
import com.verminsnest.entities.explosions.Explosion;
import com.verminsnest.entities.items.Food;
import com.verminsnest.entities.items.Item;
import com.verminsnest.entities.items.barriers.BarrierActiv;
import com.verminsnest.entities.items.turrets.FourWayMechaTurret;
import com.verminsnest.entities.particles.Gore;
import com.verminsnest.entities.playables.Mage;
import com.verminsnest.entities.playables.Playable;
import com.verminsnest.entities.projectiles.Projectile;
import com.verminsnest.entities.util.UtilEntity;
import com.verminsnest.entities.util.leveldesign.Rock;
import com.verminsnest.entities.util.shop.Blanket;
import com.verminsnest.entities.util.shop.Shopkeeper;
import com.verminsnest.misc.entities.Death;
import com.verminsnest.world.generation.map.World;
import com.verminsnest.world.generation.spawning.EnemySpawner;
import com.verminsnest.world.generation.spawning.UtilSpawner;
import com.verminsnest.world.management.FloorManager;

public class EntityManager {
	private ArrayList<Entity> removedEntities;
	private ArrayList<Entity> addedEntities;

	private ArrayList<Entity> util;
	private ArrayList<Entity> entities;
	private ArrayList<Entity> leftovers;
	private ArrayList<Entity> particle;
	private ArrayList<Entity> damage;
	private ArrayList<Entity> items;
	private ArrayList<Entity> food;
	
	private ArrayList<int[]> toInitEntities;
	private ArrayList<int[]> toInitUtil;
	private int toInitCharacter;
	
	private Playable character;
	
	private Entity lastDeath;
	private ArrayList<Death> deaths;
	
	public EntityManager(){

		toInitEntities= new ArrayList<>();
		toInitUtil= new ArrayList<>();
		
		removedEntities = new ArrayList<Entity>();
		addedEntities = new ArrayList<Entity>();
		entities = new ArrayList<Entity>();
		
		items = new ArrayList<Entity>();
		food = new ArrayList<Entity>();
		util= new ArrayList<Entity>();
		leftovers = new ArrayList<Entity>();
		damage = new ArrayList<Entity>();
		particle = new ArrayList<Entity>();
		deaths= new ArrayList<>();
	}
	
	public void addEntity(Entity ent){
		addedEntities.add(ent);
	}
	
	public void addDeath(Death killed) {
		deaths.add(killed);
	}
	
	public void removeEntity(Entity ent){
		removedEntities.add(ent);
	}
	
	public void updateEntities(float delta){
		FloorManager.getInstane().update(delta);
		if(FloorManager.getInstane().allowEntityUpdate()) {
			for(Entity ent: removedEntities){
				if(ent instanceof Projectile || ent instanceof Explosion){
					damage.remove(ent);
				}else if(ent instanceof Gore){
					particle.remove(ent);
				}else if(ent instanceof Item ){
					items.remove(ent);
				}else if(ent instanceof Food){
					food.remove(ent);
				}else if(ent instanceof UtilEntity){
					util.remove(ent);
				}else{
					if(!entities.remove(ent)) {
						leftovers.remove(ent);
					}
				}
				ent.dispose();
			}
			removedEntities.clear();
			
			for(Entity ent: addedEntities){
				if(ent instanceof Projectile || ent instanceof Explosion){
					damage.add(ent);
				}else if(ent instanceof Gore){
					particle.add(ent);
				}else if(ent instanceof Item){
					items.add(ent);
				}else if(ent instanceof Food){
					food.add(ent);
				}else if(ent instanceof UtilEntity){
					util.add(ent);
				}else{
					if(ent.getState() != Indentifiers.STATE_LEFTOVER){
						entities.add(ent);
					}else{
						leftovers.add(ent);
					}
				}
			}
			addedEntities.clear();
			if(character != null){
				int updateXRange = 1500;
				int updateYRange = 1000;
				for(Entity ent: entities){
					if(ent.getPos()[0] > character.getPos()[0]-updateXRange && ent.getPos()[0] < character.getPos()[0]+updateXRange
							&& ent.getPos()[1] > character.getPos()[1]-updateYRange && ent.getPos()[1] < character.getPos()[1]+updateYRange){
						ent.update(delta);
					}
				}
				
				for(Entity ent: damage){
					ent.update(delta);
				}
				for(Entity ent: leftovers){
					if(ent.getPos()[0] > character.getPos()[0]-updateXRange && ent.getPos()[0] < character.getPos()[0]+updateXRange
							&& ent.getPos()[1] > character.getPos()[1]-updateYRange && ent.getPos()[1] < character.getPos()[1]+updateYRange){
						ent.update(delta);
					}
				}
				for(Entity ent: particle){
					if(ent.getPos()[0] > character.getPos()[0]-updateXRange && ent.getPos()[0] < character.getPos()[0]+updateXRange
							&& ent.getPos()[1] > character.getPos()[1]-updateYRange && ent.getPos()[1] < character.getPos()[1]+updateYRange){
						ent.update(delta);
					}
				}
				for(Entity ent: util){
					if(ent.getPos()[0] > character.getPos()[0]-updateXRange && ent.getPos()[0] < character.getPos()[0]+updateXRange
							&& ent.getPos()[1] > character.getPos()[1]-updateYRange && ent.getPos()[1] < character.getPos()[1]+updateYRange){
						ent.update(delta);
					}
				}
				if(lastDeath != null && lastDeath.getPos()[0] > character.getPos()[0]-updateXRange && lastDeath.getPos()[0] < character.getPos()[0]+updateXRange
						&& lastDeath.getPos()[1] > character.getPos()[1]-updateYRange && lastDeath.getPos()[1] < character.getPos()[1]+updateYRange) {
					lastDeath.update(delta);
				}
				for(Entity ent: items){
					if(ent.getPos() != null && ent.getPos()[0] > character.getPos()[0]-updateXRange && ent.getPos()[0] < character.getPos()[0]+updateXRange
							&& ent.getPos()[1] > character.getPos()[1]-updateYRange && ent.getPos()[1] < character.getPos()[1]+updateYRange){
						ent.update(delta);
					}
				}
				
				for(Death killed: deaths) {
					killed.execute();
				}
				deaths.clear();
			}
		}
	}

	public ArrayList<Entity> getRemoved(){
		return removedEntities;
	}
	
	public void clearData(){
		addedEntities.clear();
		removedEntities.clear();
		
		character = null;
		lastDeath = null;
		
		for(Entity ent: getAllEntities()){
			ent.dispose();
		}
		entities.clear();
		util.clear();
		food.clear();
		damage.clear();
		particle.clear();
		leftovers.clear();
		items.clear();
		deaths.clear();
		FloorManager.getInstane().reset();
	}

	public ArrayList<Entity> getAllBioEntities() {
		ArrayList<Entity> temp = new ArrayList<>();
		temp.addAll(particle);
		temp.addAll(leftovers);
		temp.addAll(food);
		temp.addAll(entities);
		temp.addAll(damage);
		return temp;
	}
	
	public ArrayList<Entity> getAllEntities() {
		ArrayList<Entity> temp = new ArrayList<>();
		temp.addAll(particle);
		temp.addAll(leftovers);
		temp.addAll(items);
		temp.addAll(food);
		temp.addAll(entities);
		temp.addAll(util);
		temp.addAll(damage);
		return temp;
	}
	
	public ArrayList<Entity> getAllObstacleEntities() {
		ArrayList<Entity> temp = new ArrayList<>();
		temp.addAll(entities);
		temp.addAll(util);
		temp.addAll(damage);
		for(Entity ent: items){
			if(ent.isObstacle())temp.add(ent);
		}
		return temp;
	}
	
	public ArrayList<Entity> getInteractables() {
		ArrayList<Entity> temp = new ArrayList<>();
		for(Entity ent: items){
			if(((Item)ent).getKeeper() == null){
				temp.add(ent);
			}
		}
		return temp;
	}
	
	public ArrayList<Entity> getAllEnemies() {
		ArrayList<Entity> temp = new ArrayList<>();
		for(Entity ent: entities){
			if(ent instanceof Enemy){
				temp.add(ent);
			}
		}
		return temp;
	}
	
	public ArrayList<Entity> getItems(){
		ArrayList<Entity> temp = new ArrayList<>();
		temp.addAll(items);
		return temp;
	}
	
	public ArrayList<Entity> getUtil(){
		ArrayList<Entity> temp = new ArrayList<>();
		temp.addAll(util);
		return temp;
	}
	
	public void initEnemies(){
		Random rand = new Random();
		for(int[] data: toInitEntities){
			switch(rand.nextInt(3)){
			case 0:
			case 1:
				new Egg(new int[]{data[0], data[1]}, data[2]);
				break;
			case 2:
				Entity enemy  = null;
				switch(data[2]){
				case Indentifiers.ASSETMANAGER_TINKER:
					enemy = new Tinker(new int[]{data[0], data[1]});
					break;
				case Indentifiers.ASSETMANAGER_FLUNK:
					enemy = new Flunk(new int[]{data[0], data[1]});
					break;
				}
				if(placeOnTile(new int[]{data[0]/128, data[1]/128},new int[]{data[0], data[1]},enemy));
				else removedEntities.add(enemy);
				break;
			}
			updateEntities(0);
		}
		toInitEntities.clear();
	}
	
	public void initUtil(){
		for(int[] data: toInitUtil){
			switch(data[2]){
			case Indentifiers.ASSETMANAGER_SHOPKEEPER:
				new Blanket(new int[]{data[0]-40, data[1]-40});
				new Shopkeeper(new int[]{data[0], data[1]});
				break;
			case Indentifiers.ASSETMANAGER_BARRIER_BLUE:
				BarrierActiv ba = new BarrierActiv();
				ba.putItem(new int[]{data[0], data[1]});
				break;
			case Indentifiers.ASSETMANAGER_TURRET_MECHA:
				FourWayMechaTurret metu = new FourWayMechaTurret();
				metu.putItem(new int[]{data[0], data[1]});
				break;
			case Indentifiers.ASSETMANAGER_ROCKS:
				Rock rock = new Rock(new int[]{0, 0},0);
				if(placeOnTile(new int[]{data[0]/128, data[1]/128},null,rock));
				else removedEntities.add(rock);
				break;
			}
			updateEntities(0);
		}
		toInitUtil.clear();
	}
	
	public void addEnemiesInit(int xPos, int yPos, int enemyID){
		toInitEntities.add(new int[]{xPos,yPos,enemyID});
	}
	
	public ArrayList<int[]> getEnemyInits(){
		return toInitEntities;
	}
	
	public void addUtilInit(int xPos, int yPos, int enemyID){
		toInitUtil.add(new int[]{xPos,yPos,enemyID});
	}
	
	public ArrayList<int[]> getUtilInits(){
		return toInitUtil;
	}
	
	public Playable getCharacter() {
		return character;
	}

	public void setCharacter(Playable character) {
		this.character = character;
	}
	
	public Entity getLastDeath() {
		return lastDeath;
	}
	
	public void setLastDeath(Entity ent) {
		lastDeath =ent;
	}
	
	public void notifyNewLevel() {
		World gen = new World(RuntimeData.getInstance().getGame());
		gen.setData(3, 15, 15, 10,
				(RuntimeData.getInstance().getTexture("textures/level-sheets/cave/Mountain-Sheet.png")));
		new EnemySpawner(3);
		new UtilSpawner();
		//Clear data
		lastDeath = null;
		
		addedEntities.clear();
		removedEntities.clear();
		
		for(Entity ent: getAllEntities()){
			if(!ent.equals(character) && !ent.equals(character.getInventory().getItem()))ent.dispose();
		}
		
		entities.clear();
		damage.clear();
		particle.clear();
		leftovers.clear();
		items.clear();
		food.clear();
		util.clear();
		
		if(character.getInventory().getItem() != null)items.add(character.getInventory().getItem());
		entities.add(character);
		
		RuntimeData.getInstance().getGame().showScreen(VerminsNest.LOADGAME);
		PositionSystem.getInstance().clearData();
	}
	
	public boolean isLast(Entity hit){
		boolean isLast = true;
		for(Entity ent: RuntimeData.getInstance().getEntityManager().getAllBioEntities()) {
			if((ent instanceof Enemy | (ent instanceof Egg && ent.getState() != Indentifiers.STATE_LEFTOVER)) && !ent.equals(hit)) {
				isLast = false;
				break;
			}
		}
		return isLast;
	}
	
	public void sortToLeftover(Entity leftOver){
		addedEntities.add(leftOver);
		removedEntities.add(leftOver);
	}
	
	/**
	 *
	 * @param ent1 ; The first entity
	 * @param ent2 ; The second entity
	 * @returns the distance between ent1 and ent2
	 */
	public int getDistanceBetween(Entity ent1, Entity ent2){
		int distance = 0;
		
		//Create vector grid
		Vector2 topLeft = new Vector2(ent1.getPos()[0]+ent1.getHitbox()[0]/2,ent1.getPos()[1]+ent1.getHitbox()[1]/2).nor();
		topLeft.setAngle(135);
		Vector2 bottomLeft = new Vector2(ent1.getPos()[0]+ent1.getHitbox()[0]/2,ent1.getPos()[1]+ent1.getHitbox()[1]/2).nor();
		bottomLeft.setAngle(-135);
		Vector2 topRight = new Vector2(ent1.getPos()[0]+ent1.getHitbox()[0]/2,ent1.getPos()[1]+ent1.getHitbox()[1]/2).nor();
		topRight.setAngle(45);
		Vector2 bottomRight = new Vector2(ent1.getPos()[0]+ent1.getHitbox()[0]/2,ent1.getPos()[1]+ent1.getHitbox()[1]/2).nor();
		bottomRight.setAngle(-45);
		
		Vector2 ent2Vector = new Vector2((ent2.getPos()[0]+ent2.getHitbox()[0]/2)-(ent1.getPos()[0]+ent1.getHitbox()[0]/2),(ent2.getPos()[1]+ent2.getHitbox()[1]/2)-(ent1.getPos()[1]+ent1.getHitbox()[1]/2)).nor();
		if(ent2Vector.y - bottomLeft.y >= 0 && ent2Vector.y - topLeft.y <= 0 && (ent2.getPos()[0]+ent2.getHitbox()[0]/2) < (ent1.getPos()[0]+ent1.getHitbox()[0]/2)){
			//Ent1 looking west
			distance = Math.abs((ent1.getPos()[0])-(ent2.getPos()[0]+ent2.getHitbox()[0]));
		}else if(ent2Vector.y - bottomRight.y >= 0 && ent2Vector.y - topRight.y <= 0&& (ent2.getPos()[0]+ent2.getHitbox()[0]/2) > (ent1.getPos()[0]+ent1.getHitbox()[0]/2)){
			//Ent1 looking east
			distance = Math.abs((ent1.getPos()[0]+ent1.getHitbox()[0])-(ent2.getPos()[0]));
		}if(ent2Vector.x - topRight.x <= 0 && ent2Vector.x - topLeft.x >= 0 && (ent2.getPos()[1]+ent2.getHitbox()[1]/2) > (ent1.getPos()[1]+ent1.getHitbox()[1]/2)){
			//Ent1 looking north
			distance = Math.abs((ent1.getPos()[1]+ent1.getHitbox()[1])-(ent2.getPos()[1]));
		}else if(ent2Vector.x - bottomRight.x <= 0 && ent2Vector.x - bottomLeft.x >= 0 && (ent2.getPos()[1]+ent2.getHitbox()[1]/2) < (ent1.getPos()[1]+ent1.getHitbox()[1]/2)){
			//Ent1 looking south
			distance = Math.abs((ent1.getPos()[1])-(ent2.getPos()[1]+ent2.getHitbox()[1]));
		}
		
		return distance;
	}
	/**
	 * 
	 * @param ent1 ; The first entity
	 * @param ent2 ; The second entity
	 * @returns a int[] which stores the direction in which ent1 will line up with ent2 at [0], and the distance to it in [1]
	 */
	public int[] getDirToLineUp(Entity ent1, Entity ent2){
		
		int xDistance = (ent1.getPos()[0]+ent1.getSize()[0]/2)-(ent2.getPos()[0]+ent2.getSize()[0]/2);
		int yDistance = (ent1.getPos()[1]+ent1.getSize()[1]/2)-(ent2.getPos()[1]+ent2.getSize()[1]/2);
		
		int[] result = new int[2];
		if(Math.abs(yDistance) > Math.abs(xDistance)){
			//Take x axis
			result[1] = (int) Math.abs(xDistance);
			if(xDistance < 0){
				result[0] = Indentifiers.DIRECTION_EAST;
			}else{
				result[0] = Indentifiers.DIRECTION_WEST;
			}
		}else{
			//Take y axis
			result[1] = (int) Math.abs(yDistance);
			if(yDistance < 0){
				result[0] = Indentifiers.DIRECTION_NORTH;
			}else{
				result[0] = Indentifiers.DIRECTION_SOUTH;
			}
		}
		return result;
	}
	
	/**
	 * Places the entity "toPlace" on the preferred or close to the preferred position. If "preferredPos" it will choose a random position.
	 * @param mapPos ; The tile on which the placement should occur
	 * @param preferredPos ; The preferred position for the placement
	 * @param toPlace ; The entity to place
	 * @returns true or false depending on if the placement was successful
	 */
	public boolean placeOnTile(int[] mapPos, int[] preferredPos, Entity toPlace){
		//Get all entities within the same tile
		if(!RuntimeData.getInstance().getMapData().getData()[mapPos[0]][mapPos[1]].isWalkable()){
			VNLogger.log("Could not place entity: " + toPlace.getClass().getSimpleName(), this.getClass());
			return false;
		}
		ArrayList<Entity> allEnts = RuntimeData.getInstance().getEntityManager().getAllObstacleEntities();
		ArrayList<Entity> relevantEnts = new ArrayList<>();
		for(Entity ent: allEnts){
			for(int[] pos: ent.getMapPos()){
				for(int x = -1; x<2;x++){
					for(int y = -1; y<2;y++){
						if(pos[0] == mapPos[0]+x && pos[1] == mapPos[1]+y){
							if(!relevantEnts.contains(ent)){
								relevantEnts.add(ent);
							}
						}
					}
				}
			}
		}
		//If no one is there, place
		if(relevantEnts.isEmpty() && preferredPos != null){
			if(toPlace instanceof Item){
				((Item)toPlace).putItem(new int[]{preferredPos[0],preferredPos[1]});
			}else{
				toPlace.getPos()[0] = preferredPos[0];
				toPlace.getPos()[1] = preferredPos[1];
			}
			if(toPlace instanceof Rock)((Rock)toPlace).persistPos();
			return true;
		}
		//Get all available places
		ArrayList<Integer[]> availablePos = new ArrayList<>();
		int[] size = null;
		if(toPlace instanceof Item){
			size = ((Item)toPlace).getDroppedSize();
		}else{
			size = toPlace.getHitbox();
		}
		Random random = new Random();
		int dislocationX = random.nextInt(size[0])-size[0]/2;
		int dislocationY = random.nextInt(size[1])-size[1]/2;
			for(int y = 1; y <= 128-size[0]-dislocationX ; y++){
				for(int x = 1; x <= 128-size[1]-dislocationY ; x++){
					Integer[] temp = new Integer[2];
					temp[0] = x+mapPos[0]*128+dislocationX;
					temp[1] = y+mapPos[1]*128+dislocationY;
					ArrayList<Integer[]> allCorners = new ArrayList<>();
					
					allCorners.add(temp);
					allCorners.add(new Integer[]{temp[0]+size[0],temp[1]});
					allCorners.add(new Integer[]{temp[0],temp[1]+size[1]});
					allCorners.add(new Integer[]{temp[0]+size[0],temp[1]+size[1]});
					for(Entity ent: relevantEnts){
						for(Integer[] pos: allCorners){
							if((pos[0] <= ent.getPos()[0]+ent.getSize()[0] && pos[0]>= ent.getPos()[0]
									&& pos[1] <= ent.getPos()[1]+ent.getSize()[1] && pos[1]>= ent.getPos()[1])
												|| !RuntimeData.getInstance().getMapData().getData()[pos[0]/128][pos[1]/128].isWalkable()){
								temp = null;
								break;
							}
						}
						if(temp == null) break;
						ArrayList<Integer[]> allCornersRef = new ArrayList<>();
						allCornersRef.add(new Integer[]{ent.getPos()[0],ent.getPos()[1]});
						allCornersRef.add(new Integer[]{ent.getPos()[0]+ent.getSize()[0],ent.getPos()[1]});
						allCornersRef.add(new Integer[]{ent.getPos()[0],ent.getPos()[1]+ent.getSize()[1]});
						allCornersRef.add(new Integer[]{ent.getPos()[0]+ent.getSize()[0],ent.getPos()[1]+ent.getSize()[1]});
						
						for(Integer[] pos: allCornersRef){
							if(pos[0] <= temp[0]+size[0] && pos[0]>= temp[0]
									&& pos[1] <= temp[1]+size[1] && pos[1]>= temp[1]){
								temp = null;
								break;
							}
						}
					}
					if(temp != null)availablePos.add(temp);
				}
			}
		if(availablePos.isEmpty()){
			VNLogger.log("Could not place entity: " + toPlace.getClass().getSimpleName(), this.getClass());
			return false;
		}
		//Chose random or closest to preferred position
		Integer[] placedPos = new Integer[]{0,0};
		if(preferredPos != null){
			for(Integer[] pos: availablePos){
				if(Math.abs(placedPos[0]-preferredPos[0]) > Math.abs(pos[0]-preferredPos[0])
						&& Math.abs(placedPos[1]-preferredPos[1]) > Math.abs(pos[1]-preferredPos[1])){
					placedPos =pos;
				}
			}
		}else{
			Random rand = new Random();
			placedPos = availablePos.get(rand.nextInt(availablePos.size()));
		}
		//Place
		if(toPlace instanceof Item){
			if(!((Item)toPlace).isGrounded())((Item)toPlace).putItem(new int[]{placedPos[0],placedPos[1]});
			else ((Item)toPlace).groundItem(new int[]{placedPos[0],placedPos[1]});
		}else{
			toPlace.getPos()[0] = placedPos[0];
			toPlace.getPos()[1] = placedPos[1];
		}
		if(toPlace instanceof Rock)((Rock)toPlace).persistPos();
		return true;
	}

	public void setToInitCharacter(int toInitCharacter) {
		this.toInitCharacter = toInitCharacter;
	}
	
	public void createCharacter(){
		switch(toInitCharacter){
		case Indentifiers.ASSETMANAGER_MAGE:
			character = new Mage(new int[] { 0, 0 });
			break;
		default:
			character = new Mage(new int[] { 0, 0 });
			break;
		}
	}
}
