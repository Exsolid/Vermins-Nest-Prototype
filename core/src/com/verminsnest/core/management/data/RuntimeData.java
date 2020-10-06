package com.verminsnest.core.management.data;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.verminsnest.core.VNLogger;
import com.verminsnest.core.VerminsNest;
import com.verminsnest.core.engine.EntityDamageSystem;
import com.verminsnest.core.engine.EntityMovementSystem;
import com.verminsnest.world.generation.map.MapData;

public class RuntimeData {

	private static RuntimeData instance;
	private MapData map;
	
	private VNAssetManager assetManager;
	private AudioManager audioMan;
	private EntityMovementSystem enMoSys;
	private EntityDamageSystem entDmgSys;
	private EntityManager enMan;
	private VerminsNest game;
	
	private String saveFile;
	private boolean gameOver;
	
	private RuntimeData(){
	}
	
	public void init(VerminsNest game){
		assetManager = new VNAssetManager();
		audioMan = new AudioManager();
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
	
	public Texture getTexture(String path){
		return (Texture)assetManager.getAsset(path);
	}
	
	public Sound getSound(String path){
		return (Sound)assetManager.getAsset(path);
	}
	public Music getMusic(String path){
		return (Music)assetManager.getAsset(path);
	}
	public void dispose(){
		Gdx.app.exit();
		assetManager.dispose();
		audioMan.dispose();
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
	
	public AudioManager getAudioManager(){
		return audioMan;
	}

	public String getSaveFile() {
		return saveFile;
	}

	public void setSaveFile(String saveFile) {
		this.saveFile = saveFile;
	}
	
	public VNAssetManager getAssetManager(){
		return assetManager;
	}
	public void disposeGameplay(){
		this.getAudioManager().stopMusic();
		ArrayList<Integer> temp = new ArrayList<>();
		temp.addAll(assetManager.getLoadedIDs());
		for(Integer id: temp){
			assetManager.unloadAssets(id);
		}
		this.getEntityManager().clearData();

		map = null;
		map = null;
		if(assetManager.getLoadedIDs() == null || assetManager.getLoadedIDs().isEmpty())VNLogger.log("All assets disposed", this.getClass());
		else{
			for(Integer i : assetManager.getLoadedIDs()){
				VNLogger.logErr("Asset of ID " + i + " is still loaded", this.getClass());
			}
		}
	}
}
