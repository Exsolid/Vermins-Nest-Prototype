package com.verminsnest.entities;

public abstract class Entity {
	protected int[] pos;
	protected int[] size;
	private String id;
	
	public Entity(int[] pos, int[] size){
		this.pos = pos;
		this.size = size;
		this.setId(this.toString());
	}
	
	public int[] getPos(){
		return pos;
	}
	
	public int[] getSize(){
		return size;
	}

	public String getId() {
		return id;
	}

	private void setId(String id) {
		this.id = id;
	}
	
	public abstract void dispose();
}
