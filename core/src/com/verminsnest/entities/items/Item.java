package com.verminsnest.entities.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.core.management.Indentifiers;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.entities.Entity;
import com.verminsnest.entities.playables.Playable;

public abstract class Item extends Entity {
	protected Entity keeper;
	protected boolean isPassiv;
	protected float baseCooldown;
	protected float runtime;
	protected Animation<TextureRegion> itemBagAni;
	protected String iconPath;
	protected boolean isGrounded;
	protected int price;
	
	public Item(int[] pos, int textureID, boolean isPassiv) {
		super(pos, textureID);
		isObstacle = false;
		
		price = 3;
		
		shadow = RuntimeData.getInstance().getAsset("textures/shadows/Shadow-S.png");
		yShadowOffset = -8;
		Texture bagSheet = RuntimeData.getInstance().getAsset("textures/items/Item-Bag.png");
		TextureRegion[][] temp = TextureRegion.split(bagSheet, 32, 32);
		TextureRegion[] frames = new TextureRegion[temp[0].length];

		for (int i = 0; i < temp[0].length; i++) {
			frames[i] = temp[0][i];
		}
		itemBagAni = new Animation<TextureRegion>(1f, frames);
		
		init();
		//TODO Log pos == null && keeper == null
	}
	
	public abstract void activate();

	public boolean isPassiv() {
		return isPassiv;
	}

	public Entity getKeeper() {
		return keeper;
	}

	protected void setKeeper(Entity keeper) {
		this.keeper = keeper;
		if(keeper != null){
			isObstacle = false;
		}else{
			isObstacle = true;
		}
	}
	
	public float getBaseCooldown() {
		return baseCooldown;
	}

	public void setBaseCooldown(float cd) {
		this.baseCooldown = cd;
	}
	
	public void takeItem(Entity keeper){
		setKeeper(keeper);
		price = 0;
		pos = null; 
		if(keeper instanceof Playable)((Playable)keeper).getInventory().setItem(this);
		setCurrentAni(Indentifiers.STATE_CAST);
		updateSize();
	}
	
	public void putItem(int[] pos){
		this.pos = pos;
		if(this.keeper instanceof Playable)((Playable)this.keeper).getInventory().setItem(null);
		setKeeper(null);
		isGrounded =false;
		switchShadow();
		setCurrentAni(Indentifiers.STATE_IDLE);
		updateSize();
		stop();
	}
	
	public void groundItem(int[] pos) {
		this.pos = pos;
		setCurrentAni(Indentifiers.STATE_IDLE);
		updateSize();
	}
	
	public String getIconPath(){
		return iconPath;
	}
	
	public void setIconPath(String path){
		iconPath = path;
	}
	
	public void updateSize(){
		setSize(currentAni.getKeyFrame(0).getRegionWidth(),currentAni.getKeyFrame(0).getRegionWidth());
	}
	
	public int[] getDroppedSize(){
		return new int[]{RuntimeData.getInstance().getAsset("textures/items/Item-Bag.png").getWidth(),RuntimeData.getInstance().getAsset("textures/items/Item-Bag.png").getHeight()};
	}
	
	public void stop(){
		runtime = 0;
		setCurrentAni(Indentifiers.STATE_CAST);
	}
	
	public int getPrice() {
		return price;
	}
	
	public float getRuntime() {
		return runtime;
	}
	
	public boolean isGrounded() {
		return isGrounded;
	}
	
	public void switchShadow() {
		if(isGrounded && keeper != null) {
			shadow = RuntimeData.getInstance().getAsset("textures/shadows/Shadow-M.png");
			yShadowOffset = -6;
			xShadowOffset = -3;
		}else if(keeper == null) {
			shadow = RuntimeData.getInstance().getAsset("textures/shadows/Shadow-S.png");
			yShadowOffset = -8;
			xShadowOffset = 0;
		}else {
			shadow = null;
		}
	}
}
