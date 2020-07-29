package com.verminsnest.misc.entities;

import com.verminsnest.entities.items.Item;

public class Inventory {
	private int foodCount;
	private Item item;
	private float itemCooldown;
	
	public Inventory() {
		
	}
	
	public void setItem(Item item) {
		this.item = item;
		itemCooldown = 0;
	}
	
	public Item getItem(){
		return item;
	}
	
	public int getFoodCount() {
		return foodCount;
	}
	
	public void setFoodCount(int count) {
		foodCount = count;	
	}
	
	public float getCooldown() {
		return itemCooldown;
	}
	
	public void setCooldown(float cd) {
		itemCooldown = cd;
	}
	
	public void update(float delta) {
		itemCooldown += delta;
	}
}
