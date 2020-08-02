package com.verminsnest.core.engine;

import java.util.ArrayList;

import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.entities.Entity;
import com.verminsnest.entities.items.Item;
import com.verminsnest.entities.util.UtilEntity;
import com.verminsnest.world.generation.map.MapCell;

public class RenderSystem {
	public static void renderGameplay(float delta){
		//Draw removed one last time
		for(Entity ent: RuntimeData.getInstance().getEntityManager().getRemoved()){
			if(ent.getShadow() != null){
				RuntimeData.getInstance().getGame().getBatch().draw(ent.getShadow(),ent.getPos()[0]+8,ent.getPos()[1]-18);
			}
		}
		for(Entity ent: RuntimeData.getInstance().getEntityManager().getRemoved()){
			RuntimeData.getInstance().getGame().getBatch().draw(ent.getCurrentFrame(delta),ent.getPos()[0],ent.getPos()[1]);
		}
		
		ArrayList<MapCell> toDraw = calcToDraw();
		//Draw ground
		for (MapCell cell : toDraw) {
			RuntimeData.getInstance().getGame().getBatch().draw(cell.getLayers().get(0), cell.getxPos(), cell.getyPos());
			if (cell.isWalkable()) {
				for(int i = cell.getLayers().size()-1; i > 0; i--){
					RuntimeData.getInstance().getGame().getBatch().draw(cell.getLayers().get(i), cell.getxPos(), cell.getyPos());
				}
			}
		}
		
		//Sort entities by position
		ArrayList<Entity> entities = new ArrayList<>();
		for(Entity ent: RuntimeData.getInstance().getEntityManager().getAllEntities()){
			if(entities.isEmpty()){
				entities.add(ent);
			}else{
				if(ent.getPos() == null || entities.get(0).getPos() == null){
					entities.remove(0);
					continue;
				}
				int counter = 0;
				while(ent.getPos()[1]
						<entities.get(counter).getPos()[1]+
						entities.get(counter).getHitbox()[1]){
					counter++;
					if(counter == entities.size())break;
					if(entities.get(counter).getPos() == null)continue;
				}
				entities.add(counter, ent);
			}
		}
		
		//Draw grounded entities
		if(entities != null){
			for(Entity ent: entities){
				if(ent instanceof UtilEntity &&((UtilEntity)ent).isGrounded()){
					RuntimeData.getInstance().getGame().getBatch().draw(ent.getCurrentFrame(delta), ent.getPos()[0],ent.getPos()[1], ent.getSize()[0]/2, ent.getSize()[1]/2, ent.getSize()[0], ent.getSize()[1], 1, 1, ent.getRotation());
				}
			}
		}
		
		//Draw shadows
		for(Entity ent: RuntimeData.getInstance().getEntityManager().getAllEntities()){
			if(ent instanceof Item && ((Item)ent).getPos() != null && (((Item)ent).getKeeper() == null || ((Item)ent).isGrounded()) && ent.getShadow() != null){
				RuntimeData.getInstance().getGame().getBatch().draw(ent.getShadow(),ent.getPos()[0]+ent.getXShadowOffset(),ent.getPos()[1]+ent.getYShadowOffset());	
			}
			else if(!(ent instanceof Item) && ent.getShadow() != null){
				RuntimeData.getInstance().getGame().getBatch().draw(ent.getShadow(),ent.getPos()[0]+ent.getXShadowOffset(),ent.getPos()[1]+ent.getYShadowOffset());	
			}
		}
		
		//Draw walls
		for (MapCell cell : calcToDraw()) {
			if (cell.getLayers().size() > 1 && !cell.isWalkable()) {
				RuntimeData.getInstance().getGame().getBatch().draw(cell.getLayers().get(1), cell.getxPos(), cell.getyPos());
			}
		}
		//Draw entities
		if(entities != null){
			for(Entity ent: entities){
				if(ent instanceof Item){
					if(ent.getPos() != null && ((Item)ent).getKeeper() == null){
						RuntimeData.getInstance().getGame().getBatch().draw(ent.getCurrentFrame(delta), ent.getPos()[0],ent.getPos()[1], ent.getSize()[0]/2, ent.getSize()[1]/2, ent.getSize()[0], ent.getSize()[1], 1, 1, ent.getRotation());
					}else if(((Item)ent).getPos() != null && ((Item)ent).getKeeper() != null){
						RuntimeData.getInstance().getGame().getBatch().draw(ent.getCurrentFrame(delta), ent.getPos()[0],ent.getPos()[1], ent.getSize()[0]/2, ent.getSize()[1]/2, ent.getSize()[0], ent.getSize()[1], 1, 1, ent.getRotation());
					}
				}else{
					if(ent instanceof UtilEntity && !((UtilEntity)ent).isGrounded() || !(ent instanceof UtilEntity))
					RuntimeData.getInstance().getGame().getBatch().draw(ent.getCurrentFrame(delta), ent.getPos()[0],ent.getPos()[1], ent.getSize()[0]/2, ent.getSize()[1]/2, ent.getSize()[0], ent.getSize()[1], 1, 1, ent.getRotation());
				}
			}
		}
	}
	
	
	// Calculates which tiles need to be rendered
	private static ArrayList<MapCell> calcToDraw() {
		int[] width = new int[2];
		int[] height = new int[2];
		width[0] = (RuntimeData.getInstance().getEntityManager().getCharacter().getPos()[0] - 1920 / 2) / 128;
		width[1] = (RuntimeData.getInstance().getEntityManager().getCharacter().getPos()[0] + 1920 / 2) / 128;
		height[0] = (RuntimeData.getInstance().getEntityManager().getCharacter().getPos()[1] - 1055 / 2) / 128;
		height[1] = (RuntimeData.getInstance().getEntityManager().getCharacter().getPos()[1] + 1055 / 2) / 128;

		ArrayList<MapCell> toDraw = new ArrayList<MapCell>();
		for (int y = height[0]; y <= height[1]; y++) {
			for (int x = width[0]; x <= width[1]; x++) {
				toDraw.add(RuntimeData.getInstance().getMapData().getData()[x][y]);
			}
		}
		return toDraw;
	}

}
