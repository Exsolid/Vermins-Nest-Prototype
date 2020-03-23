package com.verminsnest.projectiles;

import java.util.ArrayList;

public class Projectiles {
	private static Projectiles instance;
	private ArrayList<Projectile> projectiles;
	
	private Projectiles(){
		projectiles = new ArrayList<>();
	}
	
	public static Projectiles getInstance(){
		if(instance == null)instance = new Projectiles();
		return instance;
	}
	
	public void add(Projectile prj){
		projectiles.add(prj);
	}
	
	public void remove(Projectile prj){
		projectiles.remove(prj);
	}
	
	public ArrayList<Projectile> getAll(){
		return projectiles;
	}
}
