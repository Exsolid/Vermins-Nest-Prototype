package com.verminsnest.core.engine;

import java.util.ArrayList;

import com.verminsnest.core.Indentifiers;
import com.verminsnest.core.singletons.RuntimeData;
import com.verminsnest.entities.Entity;
import com.verminsnest.entities.enemies.Enemy;
import com.verminsnest.entities.playables.Playable;
import com.verminsnest.entities.projectiles.Projectile;

public class EntityDamageSystem {

	private ArrayList<ArrayList<Entity>> hitProjectiles;
	
	public EntityDamageSystem(){
		hitProjectiles = new ArrayList<>();
	}
	
	public void addHit(Projectile source, Entity hit){
		ArrayList<Entity> found = new ArrayList<Entity>();
		for(ArrayList<Entity> prj: hitProjectiles){
			if(prj.get(0) == source){
				found = prj;
			}
		}
		if(!found.isEmpty() && !found.contains(hit)){
			found.add(hit);
			if(hit instanceof Enemy && source.isFriendly()){
				((Enemy)hit).setHealth(((Enemy)hit).getHealth()-source.getDamage());
				if(((Enemy)hit).getHealth()<0)RuntimeData.getInstance().removeEntity(hit);
			}
			if(hit instanceof Playable && !source.isFriendly()){
				((Playable)hit).setHealth(((Playable)hit).getHealth()-source.getDamage());
			}
		}else if(found.isEmpty()){
			found.add(source);
			found.add(hit);
			hitProjectiles.add(found);
			if(hit instanceof Enemy && source.isFriendly()){
				((Enemy)hit).setHealth(((Enemy)hit).getHealth()-source.getDamage());
				if(((Enemy)hit).getHealth()<0)RuntimeData.getInstance().removeEntity(hit);
			}
			if(hit instanceof Playable && !source.isFriendly()){
				((Playable)hit).setHealth(((Playable)hit).getHealth()-source.getDamage());
			}
			source.setCurrentAni(Indentifiers.STATE_HIT);
		}
	}
}
