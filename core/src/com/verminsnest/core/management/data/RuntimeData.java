package com.verminsnest.core.management.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.verminsnest.core.VerminsNest;
import com.verminsnest.core.engine.EntityDamageSystem;
import com.verminsnest.core.engine.EntityMovementSystem;
import com.verminsnest.world.generation.map.MapData;

public class RuntimeData {

	private static RuntimeData instance;
	private MapData map;
	
	private VNAssetManager assetManager;
	private EntityMovementSystem enMoSys;
	private EntityDamageSystem entDmgSys;
	private EntityManager enMan;
	private VerminsNest game;
	
	private boolean gameOver;
	
	private RuntimeData(){
	}
	
	public void init(VerminsNest game){
		assetManager = new VNAssetManager();
		enMan = new EntityManager();
		this.game = game;
		setGameOver(false);

		entDmgSys = new EntityDamageSystem();
	}
	
	public static RuntimeData getInstance(){
		if(instance == null) instance = new RuntimeData();
		return instance;
	}

	public void setMap(MapData map) {
		this.map = map;
		enMoSys = new EntityMovementSystem(this.map.getData());
	}
	
	public MapData getMapData(){
		return map;
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
		game.dispose();
		assetManager.dispose();
		instance = null;
	}
	
	public boolean areAssetsLoaded(int id){
		return assetManager.areAssetsLoaded(id);
	}
	
	public Vector3 getMousePosInGameWorld() {
		 return new Vector3(Gdx.input.getX(), Gdx.input.getY(),0);
	}
	
	public VerminsNest getGame(){
		return game;
	}
	
	public EntityManager getEntityManager(){
		return enMan;
	}

	public EntityDamageSystem getEntityDamageSystem(){
		return entDmgSys;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
}
