package com.verminsnest.misc.entities;
import java.util.Random;

import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.entities.Entity;
import com.verminsnest.entities.items.Food;

public class Death{
	private Entity killed;
	private Random rand;
	
	public Death(Entity killed) {
		this.killed = killed;
		RuntimeData.getInstance().getEntityManager().addDeath(this);
		rand = new Random();
	}
	
	public void execute() {
		if(RuntimeData.getInstance().getEntityManager().isLast(killed)) {
			finishFLoor();
		}else {
			drop();
		}
	}
	
	private void drop() {
		int chance = rand.nextInt(3);
		switch(chance) {
		case 0:
			new Food(killed.getPos());
			break;
		default:
			break;
		}
		RuntimeData.getInstance().getEntityManager().getCharacter().updateKills();
		RuntimeData.getInstance().getEntityManager().removeEntity(killed);
	}
	
	private void finishFLoor() {
		RuntimeData.getInstance().getEntityManager().getCharacter().updateKills();
		RuntimeData.getInstance().getEntityManager().setLastDeath(killed);
	}
}