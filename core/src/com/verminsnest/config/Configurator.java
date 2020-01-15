package com.verminsnest.config;

import java.net.MalformedURLException;
import java.util.Locale;
import java.util.ResourceBundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
public class Configurator {

	private Preferences prefs;
	private ResourceBundle bundle;
	public static AssetManager assets = new AssetManager();
	
	public Configurator(){
		Locale language;
		prefs = Gdx.app.getPreferences("com.verminsnest.prefs");
		if(prefs.getString("language").equals("")){
			prefs.putString("language", "en");
			language = new Locale("en");
		}
		else language = new Locale(this.getLanguage());
		
		try {
			this.setBundle(language);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setBundle(Locale language) throws MalformedURLException{
		bundle = ResourceBundle.getBundle("com.verminsnest.config.messages", language);
	}
	
	public String getMessage(String msg){
		return bundle.getString(msg);
	}
	
	public String getLanguage(){
		return prefs.getString("language", "en");
	}
	
	public void setLanguage(String langShort) throws MalformedURLException{
		this.setBundle(new Locale(langShort));
		prefs.putString("language", langShort);
		prefs.flush();
	}
	
	public int[] getResolution(){
		int[] reso = new int[2];
		reso[0] = prefs.getInteger("resolutionX", 1920);
		reso[1] = prefs.getInteger("resolutionY", 1080);
		return reso;
	}
	
	public void setResolution(int[] reso){
		prefs.putInteger("resolutionX", reso[0]);
		prefs.putInteger("resolutionY", reso[1]);
		prefs.flush();
	}
	
	public boolean isFullscreen(){
		return prefs.getBoolean("isFullscreen", true);
	}
	
	public void setFullscreen(boolean mode){
		prefs.putBoolean("isFullscreen", mode);
	}
}
