package com.verminsnest.core.singletons;

import java.util.ArrayList;

import com.verminsnest.core.LoadingModule;

public class LoadingModules {
	private static LoadingModules instance;
	private ArrayList<LoadingModule> modules;
	private LoadingModules(){
		setModules(new ArrayList<LoadingModule>());
	}
	public ArrayList<LoadingModule> getModules() {
		return modules;
	}
	private void setModules(ArrayList<LoadingModule> modules) {
		this.modules = modules;
	}
	public static LoadingModules getInstance(){
		if(instance == null) instance = new LoadingModules();
		return instance;
	}
}
