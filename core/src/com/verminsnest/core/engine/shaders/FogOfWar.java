package com.verminsnest.core.engine.shaders;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.verminsnest.core.VNLogger;
import com.verminsnest.core.engine.PositionSystem;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.world.management.FloorManager;

public class FogOfWar extends Shader{
	private float pixelRadius;
	private float[] positions;
	
	private Thread threadCalculator;
	private boolean running;
	
	public FogOfWar(){
		super();
		ShaderProgram.pedantic = false;
		shader = new ShaderProgram(Gdx.files.internal("shaders/fow.vsh"),Gdx.files.internal("shaders/fow.fsh"));
		if(shader.isCompiled()){
			VNLogger.log("Compiled", this.getClass());
		}else{
			VNLogger.logErr(shader.getLog(), this.getClass());
		}
		pixelRadius = 6f;
		positions = calculateData();
		running = true;
		threadCalculator = new Thread(){
			public void run(){
				while(running){
					if(FloorManager.getInstane().allowEntityUpdate())
					positions = calculateData();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						StringWriter err = new StringWriter();
						e.printStackTrace(new PrintWriter(err));
						VNLogger.logErr(err.toString(),this.getClass());
					}
				}
			}
		};
		threadCalculator.setName("FogOfWarCalculator");
		threadCalculator.start();
	}
	
	public void begin(){
		shader.begin();
		shader.setUniform1fv("positions", positions, 0,400);
		shader.setUniformf("size", pixelRadius);
		int [] res = RuntimeData.getInstance().getGame().getConfig().getResolution();
		shader.setUniform1fv("resolution", new float[]{res[0],res[1]},0,2);
		shader.end();
		RuntimeData.getInstance().getGame().getBatch().setShader(shader);
		RuntimeData.getInstance().getGame().getBatch().flush();
	}
	
	public void end(){
		RuntimeData.getInstance().getGame().getBatch().setShader(null);
		RuntimeData.getInstance().getGame().getBatch().flush();
	}
	
	public void dispose(){
		shader.dispose();
		running = false;
		instance = null;
		PositionSystem.getInstance().clearData();
		VNLogger.log("Disposed", this.getClass());
	}
	
	private float[] calculateData(){
		float[] temp = initPositions();
		int[] res = RuntimeData.getInstance().getGame().getConfig().getResolution();
		int[] midPos = RuntimeData.getInstance().getEntityManager().getCharacter().getPos();
		ArrayList<int[]> inGamePositions = PositionSystem.getInstance().getRenderedRevealedPositions();
		for(int i = 0; i < inGamePositions.size(); i++){
			if(inGamePositions.get(i)[0]>midPos[0]-1920-150 && inGamePositions.get(i)[0]<midPos[0]+1920+150
					&& inGamePositions.get(i)[1]>midPos[1]-1080-100 && inGamePositions.get(i)[1]<midPos[1]+1080+100){
				boolean put = false;
				if(temp[temp.length-1] != -1)put = true;
				int counter = 0;
				while(!put){
					if(temp[counter] == -1){
						float[] pos = interpolatePosition(inGamePositions.get(i), midPos, res);
						temp[counter] = pos[0];
						temp[counter+1] = pos[1];
						put = true;
					}else{
						counter = counter +2;
					}
				}
			}
		}
		return temp;
	}
	private float[] interpolatePosition(int[] original, int[] midPos, int[] res){
		float xPos = 1920 - (midPos[0]+1920/2-original[0]);
		float yPos = 960 -(midPos[1]+960/2-original[1]);
		float interpolateX = xPos/1920 * pixelRadius;
		float interpolateY = yPos/960 * pixelRadius/2f;
		return new float[]{interpolateX,interpolateY};
	}
	private float[] initPositions(){
		float[] temp = new float[400];
		for(int i = 0; i < temp.length; i++){
			temp[i] = -1;
		}
		return temp;
	}
	public void addPosition(int[] position){
		position = new int[]{position[0],position[1]};
		position[0] = position[0]-position[0]%128+64;
		position[1] = position[1]-position[1]%128+64;
		boolean found = false;
		ArrayList<int[]> inGamePositions = PositionSystem.getInstance().getRenderedRevealedPositions();
		int[] tempPosition = PositionSystem.getInstance().getTile(position);
		for(int i = 0; i < inGamePositions.size(); i++){
			int[] tempInGamePositions = PositionSystem.getInstance().getTile(inGamePositions.get(i));
			if((tempInGamePositions[0] == tempPosition[0] && tempInGamePositions[1] == tempPosition[1])){
				found = true;
			}
		}
		if(!found){
			PositionSystem.getInstance().addPosition(new int[]{position[0],position[1]});
		}
	}

}
