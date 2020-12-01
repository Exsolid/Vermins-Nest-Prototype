package com.verminsnest.world.generation.map;

public class AStarMapNode{
	private int[] mapPos;
	private int weight;
	private int distGoal;
	private int distSource;
	private int[] nodePos;
	private int sourceDir;
	private String entity;
	private int[] debugGoalPos;
	
	public AStarMapNode(int[] mapPos, int[] goalMapPos, int[] nodePos) {
		this.mapPos = mapPos;
		this.nodePos = nodePos;
		distGoal = Math.abs(goalMapPos[0]-mapPos[0])+Math.abs(goalMapPos[1]-mapPos[1]);
		distSource = 0;
		sourceDir = -1;
		weight = distSource + distGoal;
		debugGoalPos = goalMapPos;
	}

	public int getDistSource() {
		return distSource;
	}

	public int getDistGoal() {
		return distGoal;
	}

	public int getWeight() {
		return weight;
	}

	public int[] getMapPos() {
		return mapPos;
	}

	public int getSourceDir() {
		return sourceDir;
	}

	public void setSourceDir(int sourceDir) {
		this.sourceDir = sourceDir;
	}
	
	public void setSourceDist(int distSource) {
		this.distSource = distSource;
		weight = distSource + distGoal;
	}

	public int[] getNodePos() {
		return nodePos;
	}

	public String getEntityID() {
		return entity;
	}

	public void setEntityID(String entity) {
		this.entity = entity;
	}
}
