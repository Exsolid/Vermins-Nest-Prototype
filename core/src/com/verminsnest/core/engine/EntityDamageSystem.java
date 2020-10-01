package com.verminsnest.core.engine;

import java.util.ArrayList;

import com.verminsnest.core.management.Indentifiers;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.entities.Entity;
import com.verminsnest.entities.enemies.Enemy;
import com.verminsnest.entities.explosions.Explosion;
import com.verminsnest.entities.playables.Playable;
import com.verminsnest.entities.projectiles.Projectile;
import com.verminsnest.misc.entities.Death;

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
		else if(source instanceof Explosion)addExplosionHit((Explosion)source, hit);
	}
	
	/**
	 * Checks if and then calculates the damage for any collision of a source and entity
	 * @param source ; The damage source
	 * @param hit ; The damage receiver
	 */
	public void calculateHit(Entity source){
		for(Entity hit: RuntimeData.getInstance().getEntityManager().getAllBioEntities()){
			if(hit instanceof Playable || hit instanceof Enemy){
				for(int x = source.getPos()[0]; x <= source.getPos()[0]+source.getHitbox()[0]; x++){
					for(int y = source.getPos()[1]; y <= source.getPos()[1]+source.getHitbox()[1]; y++){
						if(x >= hit.getPos()[0] && x <= hit.getPos()[0]+ hit.getHitbox()[0]
								&& y >= hit.getPos()[1] && y <= hit.getPos()[1]+ hit.getHitbox()[1]){
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
					new Death(hit);     
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
					new Death(hit);
				}
			}
			if(hit instanceof Playable){
				((Playable)hit).setHealth(((Playable)hit).getHealth()-source.getDamage());
				if(((Playable) hit).getHealth() <= 0){
					RuntimeData.getInstance().setGameOver(true);
				}
			}
		}
		if(hit instanceof Playable || hit instanceof Enemy){
			int x = hit.getPos()[0]+hit.getHitbox()[0]/2-(source.getPos()[0]+source.getHitbox()[0]/2);
			int y = hit.getPos()[1]+hit.getHitbox()[1]/2-(source.getPos()[1]+source.getHitbox()[1]/2);
			if(Math.abs(x)<Math.abs(y)){
				if(x<0)hit.setForced(true, 0.08f, Indentifiers.DIRECTION_WEST);
				else hit.setForced(true, 0.08f,Indentifiers.DIRECTION_EAST);
			}else{
				if(y<0)hit.setForced(true, 0.08f, Indentifiers.DIRECTION_SOUTH);
				else hit.setForced(true, 0.08f, Indentifiers.DIRECTION_NORTH);
			}
		}
	}
	
	public void knockBack(Entity ent, int dir){
		switch(dir){
		case Indentifiers.DIRECTION_NORTH:
			RuntimeData.getInstance().getMovmentSystem().moveTop(ent, 15, null);
			break;
		case Indentifiers.DIRECTION_SOUTH:
			RuntimeData.getInstance().getMovmentSystem().moveDown(ent, 15, null);
			break;
		case Indentifiers.DIRECTION_EAST:
			RuntimeData.getInstance().getMovmentSystem().moveRight(ent, 15, null);
			break;
		case Indentifiers.DIRECTION_WEST:
			RuntimeData.getInstance().getMovmentSystem().moveLeft(ent, 15, null);
			break;
		}
	}
}
