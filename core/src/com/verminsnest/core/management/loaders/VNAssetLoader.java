package com.verminsnest.core.management.loaders;

import java.util.ArrayList;
import java.util.List;

import com.verminsnest.core.management.data.RuntimeData;

public class VNAssetLoader extends LoadingModule{

	private ArrayList<Integer> IDsToLoad;
	
	public VNAssetLoader() {
		super(RuntimeData.getInstance().getGame().getConfig().getMessage("LoadingScreen_AssetLoad"));
		IDsToLoad = new ArrayList<>();
		inThread = false;
	}
	
	@Override
	public void execute() {
		for(Integer id: IDsToLoad){
			RuntimeData.getInstance().getAssetManager().loadAssets(id);
		}
	}
	
	public void addIDsToLoad(List<Integer> ids){
		IDsToLoad.addAll(ids);
	}
	public void addIDsToLoad(int id){
		IDsToLoad.add(id);
	}
	
}
