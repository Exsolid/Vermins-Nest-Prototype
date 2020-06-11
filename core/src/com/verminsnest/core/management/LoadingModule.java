package com.verminsnest.core.management;

public abstract class LoadingModule{
	private String description;
	private Thread thread;
	
	public LoadingModule(String description){
		this.setDescription(description);
		init();
	}
	public void load(){
		thread = new Thread(new Runnable(){

			@Override
			public void run() {
				execute();
			}			
		});
		thread.start();
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
