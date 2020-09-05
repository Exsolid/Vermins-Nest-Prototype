package com.verminsnest.core.engine.shaders;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.verminsnest.core.management.data.RuntimeData;

public abstract class Shader {
	protected static Shader instance;
	protected ShaderProgram shader;
	protected Shader(){
		instance = this;
	}
	public static Shader getInstance(){
		return instance == null ? null : instance;
	}
	public abstract void begin();
	public abstract void end();
	public abstract void dispose();
	public abstract void addPosition(int[] position);
	
	protected float getScaling(){
		return (RuntimeData.getInstance().getGame().getConfig().getResolution()[1]/1080f);
	}
}
