package com.verminsnest.world.management;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.core.singletons.RuntimeData;
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
		
		if(RuntimeData.getInstance().getLastDeath() != null) {
			if(levelHolePos == null) {
				((Enemy)RuntimeData.getInstance().getLastDeath()).setHealth(999);
				((Enemy)RuntimeData.getInstance().getLastDeath()).setIsLastDeath(true);
				RuntimeData.getInstance().addEntity(RuntimeData.getInstance().getLastDeath());
				
				if(((Enemy)RuntimeData.getInstance().getLastDeath()).isReadyToDig()) {
					levelHolePos = new int[2];
					levelHolePos[0] = RuntimeData.getInstance().getLastDeath().getPos()[0]-RuntimeData.getInstance().getLastDeath().getPos()[0]%128;
					levelHolePos[1] = RuntimeData.getInstance().getLastDeath().getPos()[1]-RuntimeData.getInstance().getLastDeath().getPos()[1]%128;
					
					
					new CloudAnimation(levelHolePos);
					RuntimeData.getInstance().removeEntity(RuntimeData.getInstance().getLastDeath());
					RuntimeData.getInstance().getMap()[levelHolePos[0]/128][levelHolePos[1]/128].addLayer(TextureRegion.split(RuntimeData.getInstance().getAsset("textures/level-sheets/cave/Mountain-Hole.png"), RuntimeData.getInstance().getAsset("textures/level-sheets/cave/Mountain-Hole.png").getWidth(),  RuntimeData.getInstance().getAsset("textures/level-sheets/cave/Mountain-Hole.png").getHeight())[0][0]);
				}
				RuntimeData.getInstance().removeEntity(RuntimeData.getInstance().getLastDeath());
				
			}
		}
		//Level connector interaction
		if(Gdx.input.isButtonJustPressed(Buttons.RIGHT) && levelHolePos != null
				&& RuntimeData.getInstance().getMousePosInGameWorld().x > levelHolePos[0] && RuntimeData.getInstance().getMousePosInGameWorld().x < levelHolePos[0]+128
				&& RuntimeData.getInstance().getMousePosInGameWorld().y > levelHolePos[1] && RuntimeData.getInstance().getMousePosInGameWorld().y < levelHolePos[1]+128) {
				
			allowEntityUpdate = false;
			levelHolePos = null;
			
			RuntimeData.getInstance().notifyNewLevel();
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
