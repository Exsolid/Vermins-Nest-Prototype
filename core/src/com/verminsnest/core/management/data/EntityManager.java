package com.verminsnest.core.management.data;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.verminsnest.core.VerminsNest;
import com.verminsnest.core.engine.PositionSystem;
import com.verminsnest.core.management.Indentifiers;
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
import com.verminsnest.entities.playables.Playable;
import com.verminsnest.entities.projectiles.Projectile;
import com.verminsnest.entities.util.UtilEntity;
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
			
			boolean found = false;
			for(Entity ent: removedEntities){
				for(Entity refEnt: entities){
					if(refEnt.getClass().equals(ent.getClass())){
						found = true;
					}
				}
				if(!found){
					ent.dispose();
				}
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

	public ArrayList<Entity> getRemoved(){
		return removedEntities;
	}
	
	public void clearData(){
		for(Entity ent: entities){
			ent.dispose();
		}
		for(Entity ent: leftovers){
			ent.dispose();
		}
		for(Entity ent: particle){
			ent.dispose();
		}
		for(Entity ent: damage){
			ent.dispose();
		}
		for(Entity ent: items){
			ent.dispose();
		}
		for(Entity ent: food){
			ent.dispose();
		}
		for(Entity ent: util){
			ent.dispose();
		}
		for(Entity ent: addedEntities){
			ent.dispose();
		}
		for(Entity ent: removedEntities){
			ent.dispose();
		}
		RuntimeData.getInstance().getMapData().getData()[0][0].getLayers().get(0).getTexture().dispose();
		addedEntities.clear();
		removedEntities.clear();
		
		character = null;
		lastDeath = null;
		
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
				switch(data[2]){
				case Indentifiers.ENEMY_TINKER:
					new Tinker(new int[]{data[0], data[1]});
					break;
				case Indentifiers.ENEMY_FLUNK:
					new Flunk(new int[]{data[0], data[1]});
					break;
				}
				break;
			}
		}
		toInitEntities.clear();
	}
	
	public void initUtil(){
		for(int[] data: toInitUtil){
			switch(data[2]){
			case Indentifiers.UTIL_SHOPKEEPER:
				new Blanket(new int[]{data[0]-40, data[1]-40});
				new Shopkeeper(new int[]{data[0], data[1]});
				break;
			case Indentifiers.UTIL_ITEM_BARRIER_BLUE:
				BarrierActiv ba = new BarrierActiv();
				ba.putItem(new int[]{data[0], data[1]});
				break;
			case Indentifiers.UTIL_ITEM_TURRET_MECHA:
				FourWayMechaTurret metu = new FourWayMechaTurret();
				metu.putItem(new int[]{data[0], data[1]});
				break;
			}
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
				(RuntimeData.getInstance().getAsset("textures/level-sheets/cave/Mountain-Sheet.png")));
		new EnemySpawner(3);
		new UtilSpawner();
		//Clear data
		for(Entity ent: entities){
			if(!(ent instanceof Playable)) {
				ent.dispose();
			}
		}
		for(Entity ent: items){
			if(!(((Item)ent).getKeeper() instanceof Playable))ent.dispose();
		}
		for(Entity ent: food){
			ent.dispose();
		}
		for(Entity ent: util){
			ent.dispose();
		}
		for(Entity ent: leftovers){
			ent.dispose();
		}
		for(Entity ent: damage){
			ent.dispose();
		}
		for(Entity ent: addedEntities){
			ent.dispose();
		}
		for(Entity ent: removedEntities){
			ent.dispose();
		}
		lastDeath = null;
		
		addedEntities.clear();
		removedEntities.clear();
		
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
	//TODO add to enemy spawning
	public boolean placeOnTile(int[] mapPos, int[] preferredPos, Entity toPlace){
		//Get all entities within the same tile
		if(!RuntimeData.getInstance().getMapData().getData()[mapPos[0]][mapPos[1]].isWalkable())return false;
		ArrayList<Entity> allEnts = RuntimeData.getInstance().getEntityManager().getAllObstacleEntities();
		ArrayList<Entity> relevantEnts = new ArrayList<>();
		for(Entity ent: allEnts){
			for(int[] pos: ent.getMapPos()){
				if(pos[0] == mapPos[0] && pos[0] == mapPos[0]){
					if(!relevantEnts.contains(ent))relevantEnts.add(ent);
				}
			}
		}
		//If no one is there place
		if(relevantEnts.isEmpty() && preferredPos != null){
			if(toPlace instanceof Item){
				((Item)toPlace).putItem(new int[]{preferredPos[0],preferredPos[1]});
			}else{
				toPlace.getPos()[0] = preferredPos[0];
				toPlace.getPos()[1] = preferredPos[1];
			}
			return true;
		}
		//Get all available places
		ArrayList<Integer[]> availablePos = new ArrayList<>();
		int[] size = null;
		if(toPlace instanceof Item){
			size = ((Item)toPlace).getDroppedSize();
		}else{
			size = toPlace.getSize();
		}
		for(int y = 1; y <= Math.floor(128/size[1]) ; y++){
			for(int x = 1; x <= Math.floor(128/size[0]) ; x++){
				Integer[] temp = new Integer[2];
				temp[0] = x*size[0]+mapPos[0]*128;
				temp[1] = y*size[1]+mapPos[1]*128;
				ArrayList<Integer[]> allCorners = new ArrayList<>();
				
				allCorners.add(temp);
				allCorners.add(new Integer[]{x*size[0]+mapPos[0]*128+size[0],y*size[1]+mapPos[1]*128});
				allCorners.add(new Integer[]{x*size[0]+mapPos[0]*128,y*size[1]+mapPos[1]*128+size[1]});
				allCorners.add(new Integer[]{x*size[0]+mapPos[0]*128+size[0],y*size[1]+mapPos[1]*128+size[1]});
				for(Entity ent: relevantEnts){
					for(Integer[] pos: allCorners){
						if(pos[0] <= ent.getPos()[0]+ent.getSize()[0] && pos[0]>= ent.getPos()[0]
								&& pos[1] <= ent.getPos()[1]+ent.getSize()[1] && pos[1]>= ent.getPos()[1]){
							temp = null;
						}
					}
				}
				if(temp != null)availablePos.add(temp);
			}
		}
		if(availablePos.isEmpty())return false;
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
		return true;
	}
}
