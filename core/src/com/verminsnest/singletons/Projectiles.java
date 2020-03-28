package com.verminsnest.singletons;

import java.util.ArrayList;

import com.verminsnest.entities.Projectile;

public class Projectiles {
	private static Projectiles instance;
	private ArrayList<Projectile> projectiles;
	private ArrayList<Projectile> updatedProjectiles;
	
	private Projectiles(){
		projectiles = new ArrayList<>();
		updatedProjectiles = new ArrayList<>();
	}
	
	public static Projectiles getInstance(){
		if(instance == null)instance = new Projectiles();
		return instance;
	}
	
	public void add(Projectile prj){
		updatedProjectiles.add(prj);
	}
	
	public void remove(Projectile prj){
		updatedProjectiles.remove(prj);
	}
	
	public ArrayList<Projectile> getAll(){
		return projectiles;
	}
	public void update(){
		projectiles.clear();
		projectiles.addAll(updatedProjectiles);
	}
}
