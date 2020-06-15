package com.verminsnest.core.engine;

import java.util.ArrayList;
import java.util.Random;

import com.verminsnest.core.management.Indentifiers;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.entities.Entity;
import com.verminsnest.entities.Gore;
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
				if(((Enemy)hit).getHealth()<0)RuntimeData.getInstance().getEntityManager().removeEntity(hit);
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
				((Enemy)hit).setAlerted(RuntimeData.getInstance().getEntityManager().getCharacter());
				if(((Enemy)hit).getHealth()<0){					
					Random rand = new Random();
					for(int i = 0; i < rand.nextInt(4)+3; i++){
						new Gore(new int[]{hit.getPos()[0], hit.getPos()[1]});
					}
					
					boolean isLast = true;
					for(Entity ent: RuntimeData.getInstance().getEntityManager().getEntities()) {
						if(ent instanceof Enemy && !ent.equals(hit)) {
							isLast = false;
							break;
						}
					}
					if(isLast) {
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
}
