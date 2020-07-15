package com.verminsnest.world.management;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.entities.enemies.Enemy;
import com.verminsnest.entities.util.CloudAnimation;

public class FloorManager {
	
	private static FloorManager instance;
	
	private int[] levelHolePos;
	
	private boolean allowEntityUpdate;
	
	private FloorManager() {
		allowEntityUpdate = true;
	}
	
	public static FloorManager getInstane() {
		if(instance == null) instance = new FloorManager();
		return instance;
	}
	
	public void update() {
		
		if(RuntimeData.getInstance().getEntityManager().getLastDeath() != null) {
			if(levelHolePos == null) {
				((Enemy)RuntimeData.getInstance().getEntityManager().getLastDeath()).setHealth(999);
				((Enemy)RuntimeData.getInstance().getEntityManager().getLastDeath()).setToLastDeath(true);
				
				if(((Enemy)RuntimeData.getInstance().getEntityManager().getLastDeath()).isReadyToDig()) {
					levelHolePos = new int[2];
					levelHolePos[0] = RuntimeData.getInstance().getEntityManager().getLastDeath().getPos()[0]-RuntimeData.getInstance().getEntityManager().getLastDeath().getPos()[0]%128;
					levelHolePos[1] = RuntimeData.getInstance().getEntityManager().getLastDeath().getPos()[1]-RuntimeData.getInstance().getEntityManager().getLastDeath().getPos()[1]%128;
					
					
					new CloudAnimation(levelHolePos);
					RuntimeData.getInstance().getEntityManager().removeEntity(RuntimeData.getInstance().getEntityManager().getLastDeath());
					RuntimeData.getInstance().getEntityManager().setLastDeath(null);
					RuntimeData.getInstance().getMapData().getData()[levelHolePos[0]/128][levelHolePos[1]/128].addLayer(TextureRegion.split(RuntimeData.getInstance().getAsset("textures/level-sheets/cave/Mountain-Hole.png"), RuntimeData.getInstance().getAsset("textures/level-sheets/cave/Mountain-Hole.png").getWidth(),  RuntimeData.getInstance().getAsset("textures/level-sheets/cave/Mountain-Hole.png").getHeight())[0][0]);
				}				
			}
		}
		//Level connector interaction
		if(Gdx.input.isKeyJustPressed(Input.Keys.E) && levelHolePos != null
				&& RuntimeData.getInstance().getGame().getCamera().position.x > levelHolePos[0] && RuntimeData.getInstance().getGame().getCamera().position.x < levelHolePos[0]+128
				&& RuntimeData.getInstance().getGame().getCamera().position.y > levelHolePos[1] && RuntimeData.getInstance().getGame().getCamera().position.y < levelHolePos[1]+128) {
				//TODO cancel on item e
			allowEntityUpdate = false;
			levelHolePos = null;
			
			RuntimeData.getInstance().getEntityManager().notifyNewLevel();
		}
	}
	
	public boolean allowEntityUpdate() {
		return allowEntityUpdate;
	}
	
	public void setEntityUpdate(boolean allow) {
		allowEntityUpdate = allow;
	}
	
	public void reset() {
		levelHolePos = null;
	}
}
