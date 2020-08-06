package com.verminsnest.misc.entities;
import java.util.Random;

import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.entities.Entity;
import com.verminsnest.entities.items.Food;
import com.verminsnest.entities.particles.Gore;
import com.verminsnest.entities.util.puddles.Bloodpuddle;

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
		

		Random rand = new Random();
		for(int i = 0; i < rand.nextInt(4)+3; i++){
			new Gore(new int[]{killed.getPos()[0], killed.getPos()[1]});
		}
		if(killed.getSize()[0]*killed.getSize()[1]<3000)
		new Bloodpuddle(new int[]{killed.getPos()[0], killed.getPos()[1]},0);
		else new Bloodpuddle(new int[]{killed.getPos()[0], killed.getPos()[1]},1);
	}
	
	private void finishFLoor() {
		RuntimeData.getInstance().getEntityManager().getCharacter().updateKills();
		RuntimeData.getInstance().getEntityManager().setLastDeath(killed);
	}
}