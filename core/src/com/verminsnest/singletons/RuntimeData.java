package com.verminsnest.singletons;

import java.util.ArrayList;

import com.verminsnest.entities.Projectile;
import com.verminsnest.mapgen.MapCell;

public class RuntimeData {

	private static RuntimeData instance;
	private MapCell[][] map;
	
	private ArrayList<Projectile> projectiles;
	private ArrayList<Projectile> updatedProjectiles;
	
	private RuntimeData(){
		projectiles = new ArrayList<Projectile>();
		updatedProjectiles = new ArrayList<Projectile>();
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
	}
	
	public void updateProjectiles(){
		projectiles.clear();
		projectiles.addAll(updatedProjectiles);
	}
	public ArrayList<Projectile> getCurrentProjectiles(){
		return projectiles;
	}
	public void addProjectile(Projectile prj){
		updatedProjectiles.add(prj);
	}
	
	public void removeProjectile(Projectile prj){
		prj.dispose();
		updatedProjectiles.remove(prj);
	}
	
	public void clearData(){
		for(Projectile prj: projectiles){
			prj.dispose();
		}
		for(Projectile prj: updatedProjectiles){
			prj.dispose();
		}
		updatedProjectiles.clear();
		projectiles.clear();
	}
}
