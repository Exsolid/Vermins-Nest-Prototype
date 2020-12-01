package com.verminsnest.world.generation.map;

import java.util.ArrayList;

import com.verminsnest.core.management.ids.Indentifiers;

public class AStarMapList {
	private int dir;
	private int oriDir;
	private ArrayList<AStarMapNode> nodes;
	private AStarMapNode weight;
	private boolean first;
	/**
	 * A list of AStarMapNodes aligned vertically or horizontally
	 * @param The direction which the width of the list should face
	 */
	public AStarMapList(int direction){
		dir = direction;
		nodes = new ArrayList<>();
	}
	
	public void addNode(AStarMapNode node){
		boolean found = false;
		int counter = 0;
		switch(dir){
		case Indentifiers.DIRECTION_NORTH:
			if(nodes.isEmpty())nodes.add(node);
			else if(node.getNodePos()[1] != nodes.get(0).getNodePos()[1])break;
			found = false;
			counter = 0;
			while(!found){
				if(nodes.get(counter).getNodePos()[0] > node.getNodePos()[0] && counter != nodes.size()-1)counter++;
				else{
					nodes.add(counter,node);
					found = true;
				}
			}
			break;
		case Indentifiers.DIRECTION_EAST:
			if(nodes.isEmpty())nodes.add(node);
			else if(node.getNodePos()[0] != nodes.get(0).getNodePos()[0])break;
			found = false;
			counter = 0;			
			while(!found){
				if(nodes.get(counter).getNodePos()[1] < node.getNodePos()[1] && counter != nodes.size()-1)counter++;
				else{
					nodes.add(counter,node);
					found = true;
				}
			}
			break;
		case Indentifiers.DIRECTION_SOUTH:
			if(nodes.isEmpty())nodes.add(node);
			else if(node.getNodePos()[1] != nodes.get(0).getNodePos()[1])break;
			found = false;
			counter = 0;
			while(!found){
				if(nodes.get(counter).getNodePos()[0] < node.getNodePos()[0] && counter != nodes.size()-1)counter++;
				else{
					nodes.add(counter,node);
					found = true;
				}
			}
			break;
		case Indentifiers.DIRECTION_WEST:
			if(nodes.isEmpty())nodes.add(node);
			else if(node.getNodePos()[0] != nodes.get(0).getNodePos()[0])break;
			found = false;
			counter = 0;			
			while(!found){
				if(nodes.get(counter).getNodePos()[1] > node.getNodePos()[1] && counter != nodes.size()-1)counter++;
				else{
					nodes.add(counter,node);
					found = true;
				}
			}
			break;
		}
		updateWeight();
	}
	
	private void updateWeight(){
		int pos = (int) Math.ceil(nodes.size()/2f)-1;
		setWeight(nodes.get(pos));
		weight.setSourceDir(dir);
	}
	
	public ArrayList<AStarMapNode> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<AStarMapNode> nodes) {
		this.nodes = nodes;
	}

	public AStarMapNode getWeight() {
		return weight;
	}

	public void setWeight(AStarMapNode weight) {
		this.weight = weight;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}
	
	public int getSourceDirection(){
		return dir;
	}

	public int getOriginalDir() {
		return oriDir;
	}

	public void setOriginalDir(int oriDir) {
		this.oriDir = oriDir;
	}
}
