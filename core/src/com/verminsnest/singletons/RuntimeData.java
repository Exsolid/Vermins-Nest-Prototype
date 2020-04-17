package com.verminsnest.singletons;

import java.util.ArrayList;

import com.verminsnest.misc.assets.VNAssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.verminsnest.core.EntityMovementSystem;
import com.verminsnest.entities.Entity;
import com.verminsnest.mapgen.MapCell;

public class RuntimeData {

	private static RuntimeData instance;
	private MapCell[][] map;
	
	private ArrayList<Entity> entities;
	private ArrayList<Entity> removedEntities;
	private ArrayList<Entity> addedEntities;

	private VNAssetManager assetManager;
	private EntityMovementSystem enMoSys;
	
	private RuntimeData(){
		removedEntities = new ArrayList<Entity>();
		entities = new ArrayList<Entity>();
		addedEntities = new ArrayList<Entity>();
		assetManager = new VNAssetManager();
	}
	
	public static RuntimeData getInstance(){
		if(instance ==  null)instance = new RuntimeData();
		return instance;
	}
	
	public MapCell[][] getMap() {
		return map;
	}

	public void setMap(MapCell[][] map) {
		this.map = map;
		enMoSys = new EntityMovementSystem(this.map);
	}
	
	public void addEntity(Entity ent){
		addedEntities.add(ent);
	}
	
	public void removeEntity(Entity ent){
		removedEntities.add(ent);
	}
	
	public void updateEntities(){
		entities.addAll(addedEntities);
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
			entities.remove(ent);
		}
		removedEntities.clear();

		for(Entity ent: entities){
			ent.update();
		}
	}
	
	public ArrayList<Entity> getCurrentEntities(){
		return entities;
	}
	
	public ArrayList<Entity> getRemoved(){
		return removedEntities;
	}
	
	public void clearData(){
		for(Entity ent: entities){
			ent.dispose();
		}
		for(Entity ent: addedEntities){
			ent.dispose();
		}
		for(Entity ent: removedEntities){
			ent.dispose();
		}
		map[0][0].getLayers().get(0).getTexture().dispose();
		map = null;
		entities.clear();
		addedEntities.clear();
		removedEntities.clear();
	}

	public ArrayList<Entity> getEntities() {
		return new ArrayList<Entity>(entities);
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
}
