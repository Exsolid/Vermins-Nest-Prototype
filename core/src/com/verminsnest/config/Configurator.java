package com.verminsnest.config;

import java.net.MalformedURLException;
import java.util.Locale;
import java.util.ResourceBundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
public class Configurator {

	private Preferences prefs;
	private ResourceBundle bundle;
	
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
	
	private void setBundle(Locale language) throws MalformedURLException{
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
		reso[1] = prefs.getInteger("resolutionY", 1055);
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
		prefs.flush();
	}
	
	public void setMusicVolume(int vol){
		prefs.putInteger("musicVolume", vol);
		prefs.flush();
	}
	
	public void setSoundVolume(int vol){
		prefs.putInteger("soundVolume", vol);
		prefs.flush();
	}
	
	public int getMusicVolume(){
		return prefs.getInteger("musicVolume",10);
	}
	
	public int getSoundVolume(){
		return prefs.getInteger("soundVolume",10);
	}
	
	public void setCurrentMonitor(int id){
		prefs.putInteger("monitorID", id);
		prefs.flush();
	}
	
	public int getCurrentMonitor(){
		return prefs.getInteger("monitorID", 0);
	}
}
