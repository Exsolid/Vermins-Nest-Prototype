package com.verminsnest.misc.entities;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.entities.Entity;

public class Death{
	private Entity killed;
	
	public Death(Entity killed) {
		this.killed = killed;
		RuntimeData.getInstance().getEntityManager().addDeath(this);
	}
	
	public void execute() {
		if(RuntimeData.getInstance().getEntityManager().isLast(killed)) {
			finishFLoor();
		}else {
			drop();
		}
	}
	
	private void drop() {
		RuntimeData.getInstance().getEntityManager().getCharacter().updateKills();
		RuntimeData.getInstance().getEntityManager().removeEntity(killed);
	}
	
	private void finishFLoor() {
		RuntimeData.getInstance().getEntityManager().getCharacter().updateKills();
		RuntimeData.getInstance().getEntityManager().setLastDeath(killed);
	}
}