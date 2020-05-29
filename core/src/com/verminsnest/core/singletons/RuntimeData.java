package com.verminsnest.core.singletons;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.verminsnest.config.Configurator;
import com.verminsnest.core.Indentifiers;
import com.verminsnest.core.VerminsNest;
import com.verminsnest.core.engine.EntityMovementSystem;
import com.verminsnest.core.engine.VNAssetManager;
import com.verminsnest.entities.Entity;
import com.verminsnest.entities.Gore;
import com.verminsnest.entities.eggs.Egg;
import com.verminsnest.entities.enemies.Tinker;
import com.verminsnest.entities.playables.Playable;
import com.verminsnest.entities.projectiles.Projectile;
import com.verminsnest.generation.map.MapCell;
import com.verminsnest.generation.map.MapData;

public class RuntimeData {

	private static RuntimeData instance;
	private MapData map;
	
	private ArrayList<Entity> removedEntities;
	private ArrayList<Entity> addedEntities;

	private ArrayList<Entity> entities;
	private ArrayList<Entity> leftovers;
	private ArrayList<Entity> gore;
	private ArrayList<Entity> projectiles;
	
	private ArrayList<int[]> toInitEntities;
	
	private Playable character;
	
	private VNAssetManager assetManager;
	private EntityMovementSystem enMoSys;
	private VerminsNest game;
	
	
	private RuntimeData(){
	}
	
	public void init(VerminsNest game){
		removedEntities = new ArrayList<Entity>();
		entities = new ArrayList<Entity>();
		leftovers = new ArrayList<Entity>();
		projectiles = new ArrayList<Entity>();
		addedEntities = new ArrayList<Entity>();
		gore = new ArrayList<Entity>();
		toInitEntities= new ArrayList<>();
		assetManager = new VNAssetManager();
		this.game = game;
	}
	
	public static RuntimeData getInstance(){
		if(instance == null) instance = new RuntimeData();
		return instance;
	}
	
	public MapCell[][] getMap() {
		return map.getData();
	}

	public void setMap(MapData map) {
		this.map = map;
		enMoSys = new EntityMovementSystem(this.map.getData());
	}
	
	public void addEntity(Entity ent){
		addedEntities.add(ent);
	}
	
	public void removeEntity(Entity ent){
		removedEntities.add(ent);
	}
	
	public MapData getMapData(){
		return map;
	}
	
	public void updateEntities(float stateTime){
		for(Entity ent: addedEntities){
			if(ent instanceof Projectile){
				projectiles.add(ent);
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
			if(ent instanceof Projectile){
				projectiles.remove(ent);
			}else if(ent instanceof Gore){
				gore.remove(ent);
			}else{
				if(ent.getState() != Indentifiers.STATE_LEFTOVER){
					entities.remove(ent);
				}else{
					leftovers.remove(ent);
				}
			}
		}
		removedEntities.clear();

		for(Entity ent: entities){
			if(ent.getPos()[0] > character.getPos()[0]-1000 && ent.getPos()[0] < character.getPos()[0]+1000
					&& ent.getPos()[1] > character.getPos()[1]-700 && ent.getPos()[1] < character.getPos()[1]+700){
				ent.update(stateTime);
			}
		}
		for(Entity ent: projectiles){
			ent.update(stateTime);
		}
		for(Entity ent: leftovers){
			if(ent.getPos()[0] > character.getPos()[0]-1000 && ent.getPos()[0] < character.getPos()[0]+1000
					&& ent.getPos()[1] > character.getPos()[1]-700 && ent.getPos()[1] < character.getPos()[1]+700){
				ent.update(stateTime);
			}
		}
		for(Entity ent: gore){
			if(ent.getPos()[0] > character.getPos()[0]-1000 && ent.getPos()[0] < character.getPos()[0]+1000
					&& ent.getPos()[1] > character.getPos()[1]-700 && ent.getPos()[1] < character.getPos()[1]+700){
				ent.update(stateTime);
			}
		}
	}

	public ArrayList<Entity> getRemoved(){
		return removedEntities;
	}
	
	public void sortToLeftovers(Entity ent){
		removedEntities.add(ent);
		leftovers.add(ent);
	}
	
	public void clearData(){
		for(Entity ent: entities){
			ent.dispose();
		}
		for(Entity ent: leftovers){
			ent.dispose();
		}
		for(Entity ent: projectiles){
			ent.dispose();
		}
		for(Entity ent: addedEntities){
			ent.dispose();
		}
		for(Entity ent: removedEntities){
			ent.dispose();
		}
		map.getData()[0][0].getLayers().get(0).getTexture().dispose();
		map = null;
		entities.clear();
		addedEntities.clear();
		removedEntities.clear();
	}

	public ArrayList<Entity> getEntities() {
		ArrayList<Entity> temp = new ArrayList<>();
		temp.addAll(gore);
		temp.addAll(leftovers);
		temp.addAll(entities);
		temp.addAll(projectiles);
		return temp;
	}
	
	public EntityMovementSystem getMovmentSystem(){
		return enMoSys;
	}
	
	public Texture getAsset(String path){
		return assetManager.getAsset(path);
	}
	
	public void loadTextures(int id){
		assetManager.loadTextures(id);
	}
	
	public void disposeTextures(int id){
		assetManager.disposeTextures(id);
	}
	public void dispose(){
		assetManager.dispose();
		instance = null;
	}

	public Playable getCharacter() {
		return character;
	}

	public void setCharacter(Playable character) {
		this.character = character;
	}
	
	public Configurator getConfig(){
		return game.getConfig();
	}
	
	public boolean areAssetsLoaded(int id){
		return assetManager.areAssetsLoaded(id);
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
	
	public Vector3 getMousePosInGameWorld() {
		 return game.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
	}
}
