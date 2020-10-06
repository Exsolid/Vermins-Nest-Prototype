package com.verminsnest.core.management.loaders;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.verminsnest.core.VNLogger;

public abstract class LoadingModule{
	private String description;
	private Thread thread;
	protected boolean inThread;
	
	public LoadingModule(String description){
		this.setDescription(description);
		inThread = true;
		init();
	}
	
	public void load(){
		if(inThread){
			thread = new Thread(new Runnable(){
	
				@Override
				public void run() {
					execute();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						StringWriter err = new StringWriter();
						e.printStackTrace(new PrintWriter(err));
						VNLogger.logErr(err.toString(),this.getClass());
					}
				}			
			});
			thread.start();
		}else{
			execute();
			this.setDone();
		}
	}
	
	public boolean isRunning(){
		return LoadingModules.getInstance().getModules().contains(this) && thread != null;
	}
	
	public abstract void execute();
	
	public void init(){
		LoadingModules.getInstance().getModules().add(this);
	}
	
	public void setDone(){
		LoadingModules.getInstance().getModules().remove(this);
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
