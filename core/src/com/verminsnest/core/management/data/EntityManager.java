package com.verminsnest.core.management.data;

import java.util.ArrayList;
import java.util.Random;

import com.verminsnest.core.VerminsNest;
import com.verminsnest.core.management.Indentifiers;
import com.verminsnest.entities.Entity;
import com.verminsnest.entities.Gore;
import com.verminsnest.entities.eggs.Egg;
import com.verminsnest.entities.enemies.Flunk;
import com.verminsnest.entities.enemies.Tinker;
import com.verminsnest.entities.explosions.Explosion;
import com.verminsnest.entities.playables.Playable;
import com.verminsnest.entities.projectiles.Projectile;
import com.verminsnest.world.generation.map.World;
import com.verminsnest.world.generation.spawning.EnemySpawner;
import com.verminsnest.world.management.FloorManager;

public class EntityManager {
	private ArrayList<Entity> removedEntities;
	private ArrayList<Entity> addedEntities;

	private ArrayList<Entity> entities;
	private ArrayList<Entity> leftovers;
	private ArrayList<Entity> gore;
	private ArrayList<Entity> damage;
	
	private ArrayList<int[]> toInitEntities;
	
	private Playable character;
	private Entity lastDeath;
	
	public EntityManager(){
		removedEntities = new ArrayList<Entity>();
		entities = new ArrayList<Entity>();
		leftovers = new ArrayList<Entity>();
		damage = new ArrayList<Entity>();
		addedEntities = new ArrayList<Entity>();
		gore = new ArrayList<Entity>();
		toInitEntities= new ArrayList<>();
	}
	
	public void addEntity(Entity ent){
		addedEntities.add(ent);
	}
	
	public void removeEntity(Entity ent){
		removedEntities.add(ent);
	}
	public void updateEntities(float delta){
		FloorManager.getInstane().update();
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
					gore.remove(ent);
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
					gore.add(ent);
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
			for(Entity ent: gore){
				if(ent.getPos()[0] > character.getPos()[0]-updateXRange && ent.getPos()[0] < character.getPos()[0]+updateXRange
						&& ent.getPos()[1] > character.getPos()[1]-updateYRange && ent.getPos()[1] < character.getPos()[1]+updateYRange){
					ent.update(delta);
				}
			}
			if(lastDeath != null && lastDeath.getPos()[0] > character.getPos()[0]-updateXRange && lastDeath.getPos()[0] < character.getPos()[0]+updateXRange
					&& lastDeath.getPos()[1] > character.getPos()[1]-updateYRange && lastDeath.getPos()[1] < character.getPos()[1]+updateYRange) {
				lastDeath.update(delta);
			}
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
		for(Entity ent: gore){
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
		RuntimeData.getInstance().getMapData().getData()[0][0].getLayers().get(0).getTexture().dispose();
		addedEntities.clear();
		removedEntities.clear();
		
		character = null;
		lastDeath = null;
		
		entities.clear();
		damage.clear();
		gore.clear();
		leftovers.clear();
		FloorManager.getInstane().reset();
	}

	public ArrayList<Entity> getEntities() {
		ArrayList<Entity> temp = new ArrayList<>();
		temp.addAll(gore);
		temp.addAll(leftovers);
		temp.addAll(entities);
		temp.addAll(damage);
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
	
	public void addEnemiesInit(int xPos, int yPos, int enemyID){
		toInitEntities.add(new int[]{xPos,yPos,enemyID});
	}
	
	public ArrayList<int[]> getEnemyInits(){
		return toInitEntities;
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
		//Clear data
		for(Entity ent: entities){
			if(!(ent instanceof Playable)) {
				ent.dispose();
			}
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
		gore.clear();
		leftovers.clear();
		
		entities.add(character);

		RuntimeData.getInstance().getGame().showScreen(VerminsNest.LOADGAME);
	}
	
	public void sortToLeftover(Entity leftOver){
		addedEntities.add(leftOver);
		removedEntities.add(leftOver);
	}
}
