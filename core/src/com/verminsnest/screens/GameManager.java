package com.verminsnest.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.entities.Mage;
import com.verminsnest.entities.Playable;
import com.verminsnest.gamedev.VerminsNest;
import com.verminsnest.mapgen.MapCell;
import com.verminsnest.mapgen.WorldGen;

public class GameManager implements Screen {

	private Texture sheet;
	private Playable character;
	private MapCell[][] map;
	private ArrayList<MapCell> toDraw;
	
	private long blockTime;
	private boolean movementBlocked;
	private long blockStartTime;
	private char currentKey;
	private char prevKey;

	private VerminsNest game;
	
	private float timeSinceRender = 0;
	private float updateStep = 1/120f;
	private float stateTime;
	private boolean running;

	public GameManager(Texture sheet, VerminsNest game) {
		this.game = game;
		this.sheet = sheet;
		character = new Mage( new int[]{0,0});
	}

	@Override
	public void show() {
		
	}

	public void init(){
		blockTime = 0;
		blockStartTime = System.currentTimeMillis();
		movementBlocked = true;
		
		prevKey = '-';
		running = true;
		
		stateTime = 0f;
		WorldGen gen = new WorldGen();
		int[][] map = gen.caveGen(6,30,30,10);
		this.map = gen.fillGraphics(sheet, map);
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[0].length; y++) {
				if (this.map[x][y].isWalkable()) {
					character.getPos()[0] = this.map[x][y].getxPos();
					character.getPos()[1] = this.map[x][y].getyPos();
				}
			}
		}
		game.getCamera().position.set(character.getPos()[0], character.getPos()[1], 0);
		game.getCamera().update();
		game.setPro();
		toDraw = new ArrayList<>();
		calcToDraw();
	}
	
	@Override
	public void render(float delta) {
		if(running){
			if(stateTime>60)stateTime =0;
			timeSinceRender += Gdx.graphics.getDeltaTime();
			if(timeSinceRender >= updateStep){
				stateTime += delta;
				timeSinceRender -= updateStep;
				manageControls();
				game.getBatch().begin();
				for (MapCell cell: toDraw) {
					game.getBatch().draw(cell.getLayers().get(0), cell.getxPos(), cell.getyPos());
					if(cell.getLayers().size()>1 && cell.isWalkable()){
						game.getBatch().draw(cell.getLayers().get(1), cell.getxPos(), cell.getyPos());
					}
				}
				game.getBatch().draw(character.getShadow(),character.getPos()[0]+8, character.getPos()[1]-18);
				game.getBatch().draw(character.getCurrentFrame(stateTime), character.getPos()[0], character.getPos()[1]);
				character.setCurrentAni(Playable.IDLE);
				
				for (MapCell cell: toDraw) {
					if(cell.getLayers().size()>1 && !cell.isWalkable()){
						game.getBatch().draw(cell.getLayers().get(1), cell.getxPos(), cell.getyPos());
					}
				}
				game.getBatch().end();
			}
			game.getCamera().update();
		}
	}
	private void calcToDraw(){
		int[] width = new int[2];
		int[] height = new int[2];
		width[0] = (character.getPos()[0]-1920/2)/128;
		width[1] = (character.getPos()[0]+1920/2)/128;
		height[0] = (character.getPos()[1]-1055/2)/128;
		height[1] = (character.getPos()[1]+1055/2)/128;
		
		toDraw.clear();
		for (int y = height[0]; y <= height[1]; y++) {
			for (int x = width[0]; x <= width[1]; x++) {
				toDraw.add(map[x][y]);		
			}
			game.getCamera().update();
		}
	}
	private void manageControls() {

		blockTime = System.currentTimeMillis() - blockStartTime;
		if (blockTime > 30) {
			movementBlocked = false;
		}
		int[] toWalk = new int[]{0,0};
		int charPosXStart = 0;
		int charPosYStart = 0;
		int charPosXEnd = 0;
		int charPosYEnd = 0;
		// S Pressed
		if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
			prevKey = currentKey;
			currentKey = 'S';
		}
		// D Pressed
		if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
			prevKey = currentKey;
			currentKey = 'D';
		}
		// A Pressed
		if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
			prevKey = currentKey;
			currentKey = 'A';
		}
		// W Pressed
		if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
			prevKey = currentKey;
			currentKey = 'W';
		}
		
			switch (currentKey){
			case 'W':
				if(!Gdx.input.isKeyPressed(Input.Keys.W)){
					if(prevKey != '-'){
						currentKey = prevKey;
						prevKey = '-';
					}else{
						currentKey = '-';
					}
				}
				charPosXStart = (character.getPos()[0]+character.getPos()[0]%128)/128;
				charPosYStart = (character.getPos()[1]+character.getPos()[1]%128)/128;
				charPosXEnd = (character.getPos()[0]-character.getCurrentFrame(stateTime).getRegionWidth()+(character.getPos()[0]-character.getCurrentFrame(stateTime).getRegionWidth())%128)/128;
				
				toWalk[1] = character.getSpeed();
				if(!map[charPosXStart][charPosYStart+1].isWalkable() || !map[charPosXEnd][charPosYStart+1].isWalkable()){
					toWalk[1] = map[charPosXStart][charPosYStart+1].getyPos()-character.getPos()[1]-character.getCurrentFrame(stateTime).getRegionHeight()-1;
					if(toWalk[1] > character.getSpeed()){
						toWalk[1] = character.getSpeed();
					}
				}
				toWalk[0] = 0;
				character.setCurrentAni(Playable.W_BACK);
				break;
			case 'D':
				if(!Gdx.input.isKeyPressed(Input.Keys.D)){
					if(prevKey != '-'){
						currentKey = prevKey;
						prevKey = '-';
					}else{
						currentKey = '-';
					}
				}
				charPosXStart = (character.getPos()[0]+character.getPos()[0]%128)/128;
				charPosYStart = character.getPos()[1]/128;
				charPosYEnd = (character.getPos()[1]+character.getCurrentFrame(stateTime).getRegionHeight())/128;
				toWalk[0] = character.getSpeed();
				if(!map[charPosXStart+1][charPosYStart].isWalkable() || !map[charPosXStart+1][charPosYEnd].isWalkable()){
					toWalk[0] = map[charPosXStart+1][charPosYStart].getxPos()-character.getPos()[0]-character.getCurrentFrame(stateTime).getRegionWidth()-2;
					if(toWalk[0] > character.getSpeed()){
						toWalk[0] = character.getSpeed();
					}
				}
				toWalk[1] = 0;
				character.setCurrentAni(Playable.W_RIGHT);
				break;
			case 'S':
				if(!Gdx.input.isKeyPressed(Input.Keys.S)){
					if(prevKey != '-'){
						currentKey = prevKey;
						prevKey = '-';
					}else{
						currentKey = '-';
					}
				}
				charPosXStart = (character.getPos()[0]+character.getPos()[0]%128)/128;
				charPosYStart = (character.getPos()[1]+character.getPos()[1]%128)/128;
				charPosXEnd = (character.getPos()[0]-character.getCurrentFrame(stateTime).getRegionWidth()+(character.getPos()[0]-character.getCurrentFrame(stateTime).getRegionWidth())%128)/128;
				
				toWalk[1] = -character.getSpeed();
				if(!map[charPosXStart][charPosYStart-1].isWalkable() || !map[charPosXEnd][charPosYStart-1].isWalkable()){
					toWalk[1] = -(character.getPos()[1] - map[charPosXStart][charPosYStart-1].getyPos()-129);
					if(toWalk[1] < character.getSpeed() && toWalk[1] != 0){
						toWalk[1] = -character.getSpeed();
					}
				}
				toWalk[0] = 0;
				character.setCurrentAni(Playable.W_FRONT);
				break;
			case 'A':
				charPosXStart = (character.getPos()[0]+character.getPos()[0]%128)/128;
				charPosYStart = character.getPos()[1]/128;
				charPosYEnd = (character.getPos()[1]+character.getCurrentFrame(stateTime).getRegionHeight())/128;
				
				toWalk[0] = -character.getSpeed();
				if(!map[charPosXStart-1][charPosYStart].isWalkable() || !map[charPosXStart-1][charPosYEnd].isWalkable()){
					toWalk[0] = -(character.getPos()[0] - map[charPosXStart-1][charPosYStart].getxPos()-128);
					if(toWalk[0] < character.getSpeed()&& toWalk[0] != 0){
						toWalk[0] = -character.getSpeed();
					}
				}
				toWalk[1] = 0;
				character.setCurrentAni(Playable.W_LEFT);
				if(!Gdx.input.isKeyPressed(Input.Keys.A)){
					if(prevKey != '-'){
						currentKey = prevKey;
						prevKey = '-';
					}else{
						currentKey = '-';
					}
				}
				break;
			case '-':
				prevKey = '-';
			}
			character.getPos()[0] += toWalk[0];
			character.getPos()[1] += toWalk[1];
			game.getCamera().position.x = character.getPos()[0];
			game.getCamera().position.y = character.getPos()[1];
			game.getCamera().update();
			game.setPro();
			calcToDraw();
		
		// Enter Pressed
		if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
			WorldGen gen = new WorldGen();
			int[][] map = gen.caveGen(4,15,20,10);
			this.map = gen.fillGraphics(sheet, map);
			for (int x = 0; x < map.length; x++) {
				for (int y = 0; y < map[0].length; y++) {
					if (this.map[x][y].isWalkable()) {
						character.getPos()[0] = this.map[x][y].getxPos();
						character.getPos()[1] = this.map[x][y].getyPos();
					}
				}
			}
			game.getCamera().position.set(character.getPos()[0], character.getPos()[1], 0);
			game.getCamera().update();
			game.setPro();
			calcToDraw();
			movementBlocked = true;
			blockStartTime = System.currentTimeMillis();
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && !movementBlocked) {
			game.togglePause();
			movementBlocked = true;
			blockStartTime = System.currentTimeMillis();
		}
	}
	
	public boolean isRunning(){
		return running;
	}
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void pause(){
		running = false;
	}

	@Override
	public void resume() {
		game.getCamera().update();
		game.setPro();
		running = true;
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		for (int y = 0; y < map[0].length; y++) {
			for (int x = 0; x < map.length; x++) {
				for (TextureRegion text : map[x][y].getLayers()) {
				text.getTexture().dispose();
				}
			}
		}
	}
}
