package com.verminsnest.core.engine;

import java.util.ArrayList;

import com.verminsnest.core.engine.shaders.Shader;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.core.management.ids.Qualifier;
import com.verminsnest.entities.Entity;
import com.verminsnest.entities.items.Item;
import com.verminsnest.world.generation.map.MapCell;

public class RenderSystem {
	public static void renderGameplay(float delta){
		Shader.getInstance().begin();
		
		ArrayList<MapCell> toDraw = calcToDraw();
		//Draw ground
		for (MapCell cell : toDraw) {
			RuntimeData.getInstance().getGame().getBatch().draw(cell.getLayers().get(0), cell.getxPos(), cell.getyPos());
		}
		
		//Sort entities by position and layer
		ArrayList<Entity> entities;
		ArrayList<Entity> entitiesTop = new ArrayList<>();
		ArrayList<Entity> entitiesMid = new ArrayList<>();
		ArrayList<Entity> entitiesBot = new ArrayList<>();
		for(Entity ent: RuntimeData.getInstance().getEntityManager().getAllEntities()){
			switch(ent.getRenderLayer()){
			case Qualifier.RENDER_LAYER_TOP:
				entities = entitiesTop; 
				break;
			case Qualifier.RENDER_LAYER_MID:
				entities = entitiesMid; 
				break;
			case Qualifier.RENDER_LAYER_BOT:
				entities = entitiesBot; 
				break;
			default:
				entities = entitiesMid; 
				break;
			}
			if(entities.isEmpty()){
				entities.add(ent);
			}else{
				if(ent.getPos() == null){
					continue;
				}else if(entities.get(0).getPos() == null){
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
		entities = new ArrayList<>();
		entities.addAll(entitiesBot);
		entities.addAll(entitiesMid);
		entities.addAll(entitiesTop);
		//Draw grounded entities and particles
		for(Entity ent: entitiesBot){
			RuntimeData.getInstance().getGame().getBatch().draw(ent.getCurrentFrame(delta), ent.getPos()[0],ent.getPos()[1], ent.getSize()[0]/2, ent.getSize()[1]/2, ent.getSize()[0], ent.getSize()[1], 1, 1, ent.getRotation());
		}
		
		//Draw shadows
		for(Entity ent: entities){
			//TODO remove isGrounded from items and make shadows == null when picked/used
			if(ent instanceof Item && ((((Item)ent).getPos() != null && (((Item)ent).getKeeper() == null) || (((Item)ent).isGrounded()) && ent.getShadow() != null))){
				RuntimeData.getInstance().getGame().getBatch().draw(ent.getShadow(),ent.getPos()[0]+ent.getXShadowOffset(),ent.getPos()[1]+ent.getYShadowOffset());	
			}
			else if(!(ent instanceof Item) && ent.getShadow() != null){
				RuntimeData.getInstance().getGame().getBatch().draw(ent.getShadow(),ent.getPos()[0]+ent.getXShadowOffset(),ent.getPos()[1]+ent.getYShadowOffset());	
			}
		}
		for (MapCell cell : toDraw) {
			if (cell.isWalkable()) {
				for(int i = cell.getLayers().size()-1; i > 0; i--){
					RuntimeData.getInstance().getGame().getBatch().draw(cell.getLayers().get(i), cell.getxPos(), cell.getyPos());
				}
			}
		}
		
		//Draw walls
		for (MapCell cell : calcToDraw()) {
			if (cell.getLayers().size() > 1 && !cell.isWalkable()) {
				RuntimeData.getInstance().getGame().getBatch().draw(cell.getLayers().get(1), cell.getxPos(), cell.getyPos());
			}
		}
		for(Entity ent: entitiesMid){
			if(ent.getPos() != null)RuntimeData.getInstance().getGame().getBatch().draw(ent.getCurrentFrame(delta), ent.getPos()[0],ent.getPos()[1], ent.getSize()[0]/2, ent.getSize()[1]/2, ent.getSize()[0], ent.getSize()[1], 1, 1, ent.getRotation());				
		}
		for(Entity ent: entitiesTop){
			if(ent.getPos() != null)RuntimeData.getInstance().getGame().getBatch().draw(ent.getCurrentFrame(delta), ent.getPos()[0],ent.getPos()[1], ent.getSize()[0]/2, ent.getSize()[1]/2, ent.getSize()[0], ent.getSize()[1], 1, 1, ent.getRotation());				
		}
		Shader.getInstance().end();
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
