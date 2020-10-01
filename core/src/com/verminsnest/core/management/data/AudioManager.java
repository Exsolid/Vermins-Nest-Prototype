package com.verminsnest.core.management.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.verminsnest.core.VNLogger;

public class AudioManager {
	private Music mPlayer;
	private Sound sPlayer;
	private ArrayList<SoundQueueItem> loopingSounds;
	
	private ArrayList<String> queuedSongs;
	private ArrayList<String> finishedSongs;
	public AudioManager(){
		loopingSounds = new ArrayList<>();
		finishedSongs = new ArrayList<>();
		queuedSongs = new ArrayList<>();
	}
	
	public void playSoundEffect(String path){
		sPlayer = RuntimeData.getInstance().getSound(path);
		long id = sPlayer.play(RuntimeData.getInstance().getGame().getConfig().getSoundVolume()/10f);
		if(id == -1){
			VNLogger.log(path + " could not be played", this.getClass());
			return;
		}
	}
	/**
	 * 
	 * @param path
	 * @param pitch it must be between 0.5 and 2.0
	 */
	public void playSoundEffect(String path, float pitch){
		if(pitch > 2 || pitch < 0.5){
			VNLogger.log("Pitch is invalid: "+pitch, this.getClass());
			return;
		}
		sPlayer = RuntimeData.getInstance().getSound(path);
		long id = sPlayer.play(RuntimeData.getInstance().getGame().getConfig().getSoundVolume()/10f);
		if(id == -1){
			VNLogger.log(path + " could not be played", this.getClass());
			return;
		}
		sPlayer.setPitch(id, pitch);
	}
	/**
	 * 
	 * @param path
	 * @param pitch it must be between 0.5 and 2.0
	 */
	public void loopSoundEffect(String path, float pitch){
		if(pitch > 2 || pitch < 0.5){
			VNLogger.log("Pitch is invalid: "+pitch, this.getClass());
			return;
		}
		for(SoundQueueItem item: loopingSounds){
			if(path.equals(item.path))return;
		}
		sPlayer = RuntimeData.getInstance().getSound(path);
		long id = sPlayer.play(RuntimeData.getInstance().getGame().getConfig().getSoundVolume()/10f);
		if(id == -1){
			VNLogger.log(path + " could not be played", this.getClass());
			return;
		}
		loopingSounds.add(new SoundQueueItem(path,id));
		sPlayer.setLooping(id, true);
		sPlayer.setPitch(id, pitch);
	}
	public void loopSoundEffect(String path){
		for(SoundQueueItem item: loopingSounds){
			if(path.equals(item.path))return;
		}
		sPlayer = RuntimeData.getInstance().getSound(path);
		long id = sPlayer.play(RuntimeData.getInstance().getGame().getConfig().getSoundVolume()/10f);
		if(id == -1){
			VNLogger.log(path + " could not be played", this.getClass());
			return;
		}
		loopingSounds.add(new SoundQueueItem(path,id));
		sPlayer.setLooping(id, true);
	}
	
	public void stopLoopSoundEffect(String path){
		if(sPlayer == null)return;
		SoundQueueItem temp = null;
		for(SoundQueueItem item: loopingSounds){
			if(path.equals(item.path)){
				temp = item;
				sPlayer.stop(item.soundID);
			}
		}
		if(temp != null)loopingSounds.remove(temp);
	}
	public void playMusic(String path){
		if(mPlayer != null)mPlayer.stop();
		mPlayer = RuntimeData.getInstance().getMusic(path);
		mPlayer.setVolume(RuntimeData.getInstance().getGame().getConfig().getMusicVolume()/10f);
		mPlayer.play();
		if(queuedSongs.isEmpty() && finishedSongs.isEmpty()){
			mPlayer.setLooping(true);
		}else{
			mPlayer.setOnCompletionListener(new Music.OnCompletionListener(){
				@Override
				public void onCompletion(Music music){
					playQueuedSong();
				}
			});
		}
	}
	
	public void stopMusic(){
		mPlayer.stop();
		finishedSongs.clear();
		queuedSongs.clear();
	}
	public void playMusicQueue(List<String> paths){
		finishedSongs.clear();
		queuedSongs.clear();
		queuedSongs.addAll(paths);
		playQueuedSong();
	}
	
	private void playQueuedSong(){
		if(queuedSongs.isEmpty())queuedSongs.addAll(finishedSongs);
		Random rand = new Random();
		int posInQueue = rand.nextInt(queuedSongs.size());
		finishedSongs.add(queuedSongs.get(posInQueue));
		playMusic(queuedSongs.get(posInQueue));
		queuedSongs.remove(posInQueue);
	}
	
	
	public void dispose(){
		if(mPlayer != null)mPlayer.dispose();
		if(sPlayer != null)sPlayer.dispose();
	}
	
	
	public void reload(){
		if(mPlayer != null)mPlayer.setVolume(RuntimeData.getInstance().getGame().getConfig().getMusicVolume()/10f);
	}
	
	private class SoundQueueItem{
		private String path;
		private long soundID;
		public SoundQueueItem(String path, long soundID){
			this.path = path;
			this.soundID = soundID;
		}
	}
}
