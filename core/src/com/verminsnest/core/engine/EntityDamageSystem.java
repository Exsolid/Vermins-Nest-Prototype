package com.verminsnest.core.engine;

import java.util.ArrayList;
import java.util.Random;

import com.verminsnest.core.management.Indentifiers;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.entities.Entity;
import com.verminsnest.entities.Gore;
import com.verminsnest.entities.eggs.Egg;
import com.verminsnest.entities.enemies.Enemy;
import com.verminsnest.entities.explosions.Explosion;
import com.verminsnest.entities.playables.Playable;
import com.verminsnest.entities.projectiles.Projectile;

public class EntityDamageSystem {

	//TODO Clear this on prj removal
	private ArrayList<ArrayList<Entity>> hitProjectiles;
	
	public EntityDamageSystem(){
		hitProjectiles = new ArrayList<>();
	}
	
	/**
	 * Calculates the damage done if a source hit an entity
	 * @param source ; The damage source
	 * @param hit ; The damage receiver
	 */
	public void addHit(Entity source, Entity hit){
		if(source instanceof Projectile)addProjectileHit((Projectile)source, hit);
		else if(source instanceof Explosion)addExplosionHit((Explosion)source, hit);//TODO Add knockback
	}
	
	/**
	 * Checks if and then calculates the damage for any collision of a source and entity
	 * @param source ; The damage source
	 * @param hit ; The damage receiver
	 */
	public void calculateHit(Entity source){
		for(Entity hit: RuntimeData.getInstance().getEntityManager().getAllBioEntities()){
			if(hit instanceof Playable || hit instanceof Enemy){
				for(int x = source.getPos()[0]; x <= source.getPos()[0]+source.getSize()[0]; x++){
					for(int y = source.getPos()[1]; y <= source.getPos()[1]+source.getSize()[1]; y++){
						if(x >= hit.getPos()[0] && x <= hit.getPos()[0]+ hit.getSize()[0]
								&& y >= hit.getPos()[1] && y <= hit.getPos()[1]+ hit.getSize()[1]){
							addHit(source, hit);
						}
					}
				}
			}
		}
	}
	
	private void addProjectileHit(Projectile source, Entity hit){
		//Check if a projectile has already hit something
		ArrayList<Entity> found = new ArrayList<Entity>();
		for(ArrayList<Entity> prj: hitProjectiles){
			if(prj.get(0) == source){
				found = prj;
			}
		}
		if(!found.isEmpty() && !found.contains(hit)){
			//If the projectile has already hit something
			found.add(hit);
			if(hit instanceof Enemy && source.isFriendly()){
				((Enemy)hit).setHealth(((Enemy)hit).getHealth()-source.getDamage());
				if(((Enemy)hit).getHealth()<0)RuntimeData.getInstance().getEntityManager().removeEntity(hit);
			}
			if(hit instanceof Playable && !source.isFriendly()){
				((Playable)hit).setHealth(((Playable)hit).getHealth()-source.getDamage());
				if(((Playable) hit).getHealth() <= 0){
					RuntimeData.getInstance().setGameOver(true);
				}
			}
		}else if(found.isEmpty()){
			//If a the projectile hit it's first entity
			found.add(source);
			found.add(hit);
			hitProjectiles.add(found);
			if(hit instanceof Enemy && source.isFriendly()){
				((Enemy)hit).setHealth(((Enemy)hit).getHealth()-source.getDamage());
				((Enemy)hit).setAlerted(RuntimeData.getInstance().getEntityManager().getCharacter());
				if(((Enemy)hit).getHealth()<0){					
					Random rand = new Random();
					for(int i = 0; i < rand.nextInt(4)+3; i++){
						new Gore(new int[]{hit.getPos()[0], hit.getPos()[1]});
					}
					
					if(isLast(hit)) {
						RuntimeData.getInstance().getEntityManager().getCharacter().updateKills();
						RuntimeData.getInstance().getEntityManager().setLastDeath(hit);
					}else {
						RuntimeData.getInstance().getEntityManager().getCharacter().updateKills();
						RuntimeData.getInstance().getEntityManager().removeEntity(hit);  
					}     
				}
			}
			if(hit instanceof Playable && !source.isFriendly()){
				((Playable)hit).setHealth(((Playable)hit).getHealth()-source.getDamage());
				if(((Playable) hit).getHealth() <= 0){
					RuntimeData.getInstance().setGameOver(true);
				}
			}
			source.setCurrentAni(Indentifiers.STATE_HIT);
		}
	}

	private void addExplosionHit(Explosion source, Entity hit){
		//Check if a explosion has already hit something
		ArrayList<Entity> found = new ArrayList<Entity>();
		for(ArrayList<Entity> prj: hitProjectiles){
			if(prj.get(0) == source){
				found = prj;
			}
		}
		if(!found.isEmpty() && !found.contains(hit)){
			//If the explosion has already hit something
			found.add(hit);
			if(hit instanceof Enemy){
				((Enemy)hit).setHealth(((Enemy)hit).getHealth()-source.getDamage());
				if(((Enemy)hit).getHealth()<0)RuntimeData.getInstance().getEntityManager().removeEntity(hit);
			}
			if(hit instanceof Playable){
				((Playable)hit).setHealth(((Playable)hit).getHealth()-source.getDamage());
				if(((Playable) hit).getHealth() <= 0){
					RuntimeData.getInstance().setGameOver(true);
				}
			}
		}else if(found.isEmpty()){
			//If a the explosion hit it's first entity
			found.add(source);
			found.add(hit);
			hitProjectiles.add(found);
			if(hit instanceof Enemy){
				((Enemy)hit).setHealth(((Enemy)hit).getHealth()-source.getDamage());
				((Enemy)hit).setAlerted(RuntimeData.getInstance().getEntityManager().getCharacter());
				if(((Enemy)hit).getHealth()<0){					
					Random rand = new Random();
					for(int i = 0; i < rand.nextInt(4)+3; i++){
						new Gore(new int[]{hit.getPos()[0], hit.getPos()[1]});
					}
					
					if(isLast(hit)) {
						RuntimeData.getInstance().getEntityManager().getCharacter().updateKills();
						RuntimeData.getInstance().getEntityManager().setLastDeath(hit);
					}else {
						RuntimeData.getInstance().getEntityManager().getCharacter().updateKills();
						RuntimeData.getInstance().getEntityManager().removeEntity(hit);  
					}     
				}
			}
			if(hit instanceof Playable){
				((Playable)hit).setHealth(((Playable)hit).getHealth()-source.getDamage());
				if(((Playable) hit).getHealth() <= 0){
					RuntimeData.getInstance().setGameOver(true);
				}
			}
		}
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
}
