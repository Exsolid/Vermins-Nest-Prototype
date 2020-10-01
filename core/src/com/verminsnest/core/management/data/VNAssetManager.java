package com.verminsnest.core.management.data;

import java.util.ArrayList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.verminsnest.core.VNLogger;
import com.verminsnest.core.management.Indentifiers;

public class VNAssetManager {
	private AssetManager assetManager;
	private ArrayList<Integer> loadedIDs;
	private ArrayList<Integer> loadedAudio;
	public VNAssetManager() {
		assetManager = new AssetManager();
		loadedIDs = new ArrayList<>();
		loadedAudio = new ArrayList<>();
	}
	
	/**
	 * Loads the assets (textures and audio) for the given ID
	 * @param id
	 */
	public void loadAssets(int id) {
		switch (id) {
		case (Indentifiers.ASSETMANAGER_INIT):
			assetManager.load("textures/menus/scrolls/VerticalScroll_Small.png", Texture.class);
			assetManager.load("textures/menus/scrolls/VerticalScroll_Big.png", Texture.class);
			assetManager.load("textures/misc/Cursor.png", Texture.class);

			assetManager.load("textures/general/Background.png", Texture.class);
			this.loadAudio(Indentifiers.ASSETMANAGER_AUDIO_ADVENTURE);
			break;
		case Indentifiers.ASSETMANAGER_GAMEPLAY:
			assetManager.load("textures/menus/scrolls/VerticalScroll_Small.png", Texture.class);
			assetManager.load("textures/menus/scrolls/HorizontalScroll_Minimum.png", Texture.class);
			assetManager.load("textures/menus/scrolls/VerticalScroll_Big.png", Texture.class);
			assetManager.load("textures/menus/scrolls/HorizontalScroll_Medium.png", Texture.class);

			assetManager.load("textures/menus/frames/HealthFrame.png", Texture.class);
			assetManager.load("textures/menus/frames/AbilityFrame.png", Texture.class);
			assetManager.load("textures/menus/frames/RoomFrame.png", Texture.class);
			assetManager.load("textures/menus/frames/AbilityFrameBackground.png", Texture.class);
			assetManager.load("textures/menus/frames/MapFrame.png", Texture.class);
			assetManager.load("textures/menus/frames/StatusFrame.png", Texture.class);

			assetManager.load("textures/level-sheets/cave/Mountain-Sheet.png", Texture.class);
			assetManager.load("textures/level-sheets/cave/Mountain-Hole.png", Texture.class);
			assetManager.load("textures/level-sheets/cave/Dirt-Cloud.png", Texture.class);

			assetManager.load("textures/shadows/Shadow-XS.png", Texture.class);
			assetManager.load("textures/shadows/Shadow-S.png", Texture.class);
			assetManager.load("textures/shadows/Shadow-M.png", Texture.class);
			assetManager.load("textures/shadows/Shadow-L.png", Texture.class);
			assetManager.load("textures/shadows/Shadow-Long-M.png", Texture.class);

			assetManager.load("textures/misc/Minimap-Pointer.png", Texture.class);
			assetManager.load("textures/misc/Cursor.png", Texture.class);
			assetManager.load("textures/items/Item-Bag.png", Texture.class);
			assetManager.load("textures/items/Food.png", Texture.class);
			this.loadAudio(Indentifiers.ASSETMANAGER_AUDIO_INTOTHEUNKOWN);
			this.loadAudio(Indentifiers.ASSETMANAGER_AUDIO_WALKINGONSTONE);
			this.loadAudio(Indentifiers.ASSETMANAGER_AUDIO_ITEMPICKDROP);
			this.loadAudio(Indentifiers.ASSETMANAGER_AUDIO_WALKINGNEXTLEVEL);
			this.loadAudio(Indentifiers.ASSETMANAGER_AUDIO_LEVELUP);
			this.loadAudio(Indentifiers.ASSETMANAGER_AUDIO_FOODEATING);
			break;
		case Indentifiers.ASSETMANAGER_BULLET_GREEN:
			assetManager.load("textures/projectiles/bullets/BulletGreen-Flying.png", Texture.class);
			assetManager.load("textures/projectiles/bullets/BulletGreen-Hit.png", Texture.class);
			this.loadAudio(Indentifiers.ASSETMANAGER_AUDIO_BULLETSHOOTING);
			break;
		case Indentifiers.ASSETMANAGER_FIREBALL:
			assetManager.load("textures/projectiles/fireball/FireBall-Flying.png", Texture.class);
			assetManager.load("textures/projectiles/fireball/FireBall-Hit.png", Texture.class);
			assetManager.load("textures/projectiles/fireball/FireBall-Cast.png", Texture.class);
			this.loadAudio(Indentifiers.ASSETMANAGER_AUDIO_FIRE1);
			break;
		case Indentifiers.ASSETMANAGER_SLASH_SMALL:
			assetManager.load("textures/projectiles/slash/SlashLeft.png", Texture.class);
			assetManager.load("textures/projectiles/slash/SlashRight.png", Texture.class);
			break;
		case Indentifiers.ASSETMANAGER_EXPLOSION_SMALL:
			assetManager.load("textures/explosions/Explosion-Small.png", Texture.class);
			this.loadAudio(Indentifiers.ASSETMANAGER_AUDIO_FIRE2);
			break;
		case Indentifiers.ASSETMANAGER_SHOPKEEPER:
			assetManager.load("textures/characters/shopkeeper/Rabbit-Idle.png", Texture.class);
			assetManager.load("textures/util/Blanket.png", Texture.class);
			break;
		case Indentifiers.ASSETMANAGER_MAGE:
			assetManager.load("textures/characters/mage/Mage-W-Front.png", Texture.class);
			assetManager.load("textures/characters/mage/Mage-W-Back.png", Texture.class);
			assetManager.load("textures/characters/mage/Mage-W-Right.png", Texture.class);
			assetManager.load("textures/characters/mage/Mage-W-Left.png", Texture.class);
			assetManager.load("textures/characters/mage/Mage-Idle.png", Texture.class);
			assetManager.load("textures/projectiles/fireball/FireBall-Icon.png", Texture.class);
			break;
		case Indentifiers.ASSETMANAGER_TINKER:
			assetManager.load("textures/enemies/tinker/Tinker-A-Back.png", Texture.class);
			assetManager.load("textures/enemies/tinker/Tinker-A-Front.png", Texture.class);
			assetManager.load("textures/enemies/tinker/Tinker-A-Left.png", Texture.class);
			assetManager.load("textures/enemies/tinker/Tinker-A-Right.png", Texture.class);
			assetManager.load("textures/enemies/tinker/Tinker-W-Back.png", Texture.class);
			assetManager.load("textures/enemies/tinker/Tinker-W-Front.png", Texture.class);
			assetManager.load("textures/enemies/tinker/Tinker-W-Left.png", Texture.class);
			assetManager.load("textures/enemies/tinker/Tinker-W-Right.png", Texture.class);
			assetManager.load("textures/enemies/tinker/Tinker-Idle.png", Texture.class);
			this.loadAudio(Indentifiers.ASSETMANAGER_AUDIO_CLAW);
			break;
		case Indentifiers.ASSETMANAGER_FLUNK:
			assetManager.load("textures/enemies/flunk/Flunk-All.png", Texture.class);
			break;
		case Indentifiers.ASSETMANAGER_EGG:
			assetManager.load("textures/enemies/eggs/Egg-Hatch.png", Texture.class);
			assetManager.load("textures/enemies/eggs/Egg-Idle.png", Texture.class);
			assetManager.load("textures/enemies/eggs/Egg-Leftover.png", Texture.class);
			break;
		case Indentifiers.ASSETMANAGER_BARRIER_BLUE:
			assetManager.load("textures/items/barriers/barrier-blue/Barrier-Blue-Cast.png", Texture.class);
			assetManager.load("textures/items/barriers/barrier-blue/Barrier-Blue-Idle.png", Texture.class);
			assetManager.load("textures/items/barriers/barrier-blue/Barrier-Blue-Break.png", Texture.class);
			assetManager.load("textures/items/barriers/barrier-blue/Barrier-Blue-Icon.png", Texture.class);
			this.loadAudio(Indentifiers.ASSETMANAGER_AUDIO_BARRIERBUILDUP);
			break;
		case Indentifiers.ASSETMANAGER_TURRET_MECHA:
			assetManager.load("textures/items/turrets/mecha-turret/4-Way-MechaTurret-Idle.png", Texture.class);
			assetManager.load("textures/items/turrets/mecha-turret/4-Way-MechaTurret-A-All.png", Texture.class);
			assetManager.load("textures/items/turrets/mecha-turret/4-Way-MechaTurret-Icon.png", Texture.class);
			break;
		case Indentifiers.ASSETMANAGER_GORE:
			assetManager.load("textures/particles/gore/Gore-1.png", Texture.class);
			assetManager.load("textures/particles/gore/Gore-2.png", Texture.class);
			assetManager.load("textures/particles/gore/Gore-3.png", Texture.class);
			assetManager.load("textures/particles/gore/Gore-4.png", Texture.class);
			assetManager.load("textures/particles/gore/Gore-5.png", Texture.class);
			break;
		case Indentifiers.ASSETMANAGER_SHELL:
			assetManager.load("textures/particles/shells/Shell.png", Texture.class);
			break;
		case Indentifiers.ASSETMANAGER_PUDDLE_BLOOD:
			assetManager.load("textures/puddles/bloodpuddles/Bloodpuddle-Small-1.png", Texture.class);
			assetManager.load("textures/puddles/bloodpuddles/Bloodpuddle-Small-2.png", Texture.class);
			assetManager.load("textures/puddles/bloodpuddles/Bloodpuddle-Big-1.png", Texture.class);
			assetManager.load("textures/puddles/bloodpuddles/Bloodpuddle-Big-2.png", Texture.class);
			break;
		default:
			VNLogger.log("ID was not found: "+id, this.getClass());
			break;
		}
		
		assetManager.finishLoading();
		assetManager.update();
		loadedIDs.add(id);
		VNLogger.log("Loaded assets of ID "+id, this.getClass());
	}

	/**
	 * Returns the asset to be found a the given path
	 * @param path
	 * @return
	 */
	public Object getAsset(String path) {
		return assetManager.get(path);
	}

	/**
	 * Unloads the assets of the given ID from the asset manager
	 * @param id
	 */
	public void unloadAssets(int id) {
		switch (id) {
		case (Indentifiers.ASSETMANAGER_INIT):
			this.unload("textures/menus/scrolls/VerticalScroll_Small.png");
			this.unload("textures/menus/scrolls/VerticalScroll_Big.png");
			this.unload("textures/misc/Cursor.png");
			this.unloadAudio(Indentifiers.ASSETMANAGER_AUDIO_ADVENTURE);
			break;
		case Indentifiers.ASSETMANAGER_GAMEPLAY:
			this.unload("textures/menus/scrolls/VerticalScroll_Small.png");
			this.unload("textures/menus/scrolls/VerticalScroll_Big.png");
			this.unload("textures/menus/scrolls/HorizontalScroll_Minimum.png");
			this.unload("textures/menus/scrolls/HorizontalScroll_Medium.png");

			this.unload("textures/menus/frames/HealthFrame.png");
			this.unload("textures/menus/frames/AbilityFrame.png");
			this.unload("textures/menus/frames/RoomFrame.png");
			this.unload("textures/menus/frames/AbilityFrameBackground.png");
			this.unload("textures/menus/frames/MapFrame.png");
			this.unload("textures/menus/frames/StatusFrame.png");

			this.unload("textures/level-sheets/cave/Mountain-Sheet.png");
			this.unload("textures/level-sheets/cave/Mountain-Hole.png");
			this.unload("textures/level-sheets/cave/Dirt-Cloud.png");

			this.unload("textures/shadows/Shadow-XS.png");
			this.unload("textures/shadows/Shadow-S.png");
			this.unload("textures/shadows/Shadow-M.png");
			this.unload("textures/shadows/Shadow-L.png");
			this.unload("textures/shadows/Shadow-Long-M.png");
			
			this.unload("textures/misc/Minimap-Pointer.png");
			this.unload("textures/misc/Cursor.png");
			this.unload("textures/items/Item-Bag.png");
			this.unload("textures/items/Food.png");
			this.unloadAudio(Indentifiers.ASSETMANAGER_AUDIO_INTOTHEUNKOWN);
			this.unloadAudio(Indentifiers.ASSETMANAGER_AUDIO_WALKINGONSTONE);
			this.unloadAudio(Indentifiers.ASSETMANAGER_AUDIO_ITEMPICKDROP);
			this.unloadAudio(Indentifiers.ASSETMANAGER_AUDIO_WALKINGNEXTLEVEL);
			this.unloadAudio(Indentifiers.ASSETMANAGER_AUDIO_LEVELUP);
			this.unloadAudio(Indentifiers.ASSETMANAGER_AUDIO_FOODEATING);
			break;
		case Indentifiers.ASSETMANAGER_BULLET_GREEN:
			this.unload("textures/projectiles/bullets/BulletGreen-Flying.png");
			this.unload("textures/projectiles/bullets/BulletGreen-Hit.png");
			this.unloadAudio(Indentifiers.ASSETMANAGER_AUDIO_BULLETSHOOTING);
			break;
		case Indentifiers.ASSETMANAGER_FIREBALL:
			this.unload("textures/projectiles/fireball/FireBall-Flying.png");
			this.unload("textures/projectiles/fireball/FireBall-Hit.png");
			this.unload("textures/projectiles/fireball/FireBall-Cast.png");
			this.unloadAudio(Indentifiers.ASSETMANAGER_AUDIO_FIRE1);
			break;
		case Indentifiers.ASSETMANAGER_SLASH_SMALL:
			this.unload("textures/projectiles/slash/Slash.png");
			break;
		case Indentifiers.ASSETMANAGER_EXPLOSION_SMALL:
			this.unload("textures/explosions/Explosion-Small.png");
			this.unloadAudio(Indentifiers.ASSETMANAGER_AUDIO_FIRE2);
			break;
		case Indentifiers.ASSETMANAGER_SHOPKEEPER:
			this.unload("textures/characters/shopkeeper/Rabbit-Idle.png");
			this.unload("textures/util/Blanket.png");
			break;
		case Indentifiers.ASSETMANAGER_MAGE:
			this.unload("textures/characters/mage/Mage-W-Front.png");
			this.unload("textures/characters/mage/Mage-W-Back.png");
			this.unload("textures/characters/mage/Mage-W-Right.png");
			this.unload("textures/characters/mage/Mage-W-Left.png");
			this.unload("textures/characters/mage/Mage-Idle.png");
			this.unload("textures/projectiles/fireball/FireBall-Icon.png");
			break;
		case Indentifiers.ASSETMANAGER_TINKER:
			this.unload("textures/enemies/tinker/Tinker-A-Back.png");
			this.unload("textures/enemies/tinker/Tinker-A-Front.png");
			this.unload("textures/enemies/tinker/Tinker-A-Left.png");
			this.unload("textures/enemies/tinker/Tinker-A-Right.png");
			this.unload("textures/enemies/tinker/Tinker-W-Back.png");
			this.unload("textures/enemies/tinker/Tinker-W-Front.png");
			this.unload("textures/enemies/tinker/Tinker-W-Left.png");
			this.unload("textures/enemies/tinker/Tinker-W-Right.png");
			this.unload("textures/enemies/tinker/Tinker-Idle.png");
			this.unloadAudio(Indentifiers.ASSETMANAGER_AUDIO_CLAW);
			break;
		case Indentifiers.ASSETMANAGER_FLUNK:
			this.unload("textures/enemies/flunk/Flunk-All.png");
			break;
		case Indentifiers.ASSETMANAGER_EGG:
			this.unload("textures/enemies/eggs/Egg-Hatch.png");
			this.unload("textures/enemies/eggs/Egg-Idle.png");
			this.unload("textures/enemies/eggs/Egg-Leftover.png");
			break;
		case Indentifiers.ASSETMANAGER_TURRET_MECHA:
			this.unload("textures/items/turrets/mecha-turret/4-Way-MechaTurret-Idle.png");
			this.unload("textures/items/turrets/mecha-turret/4-Way-MechaTurret-A-All.png");
			this.unload("textures/items/turrets/mecha-turret/4-Way-MechaTurret-Icon.png");
			break;
		case Indentifiers.ASSETMANAGER_BARRIER_BLUE:
			this.unload("textures/items/barriers/barrier-blue/Barrier-Blue-Cast.png");
			this.unload("textures/items/barriers/barrier-blue/Barrier-Blue-Idle.png");
			this.unload("textures/items/barriers/barrier-blue/Barrier-Blue-Break.png");
			this.unload("textures/items/barriers/barrier-blue/Barrier-Blue-Icon.png");
			this.unloadAudio(Indentifiers.ASSETMANAGER_AUDIO_BARRIERBUILDUP);
			break;
		case Indentifiers.ASSETMANAGER_GORE:
			this.unload("textures/particles/gore/Gore-1.png");
			this.unload("textures/particles/gore/Gore-2.png");
			this.unload("textures/particles/gore/Gore-3.png");
			this.unload("textures/particles/gore/Gore-4.png");
			this.unload("textures/particles/gore/Gore-5.png");
			break;
		case Indentifiers.ASSETMANAGER_PUDDLE_BLOOD:
			this.unload("textures/puddles/bloodpuddles/Bloodpuddle-Small-1.png");
			this.unload("textures/puddles/bloodpuddles/Bloodpuddle-Small-2.png");
			this.unload("textures/puddles/bloodpuddles/Bloodpuddle-Big-1.png");
			this.unload("textures/puddles/bloodpuddles/Bloodpuddle-Big-2.png");
			break;
		case Indentifiers.ASSETMANAGER_SHELL:
			this.unload("textures/particles/shells/Shell.png");
			break;
		default:
			VNLogger.log("ID was not found: "+id, this.getClass());
			break;
		}
		assetManager.update();
		loadedIDs.remove(Integer.valueOf(id));
		VNLogger.log("Unloaded assets of ID "+id, this.getClass());
	}
	
	/**
	 * Returns true if the assets of given ID are loaded
	 * @param id
	 * @return
	 */
	public boolean areAssetsLoaded(int id) {
		switch (id) {
		case (Indentifiers.ASSETMANAGER_INIT):
			return assetManager.isLoaded("textures/misc/Cursor.png");
		case Indentifiers.ASSETMANAGER_GAMEPLAY:
			return assetManager.isLoaded("textures/menus/scrolls/VerticalScroll_Small.png");
		case Indentifiers.ASSETMANAGER_BULLET_GREEN:
			return assetManager.isLoaded("textures/projectiles/bullets/BulletGreen-Flying.png");
		case Indentifiers.ASSETMANAGER_FIREBALL:
			return assetManager.isLoaded("textures/projectiles/fireball/FireBall-Flying.png");
		case Indentifiers.ASSETMANAGER_SLASH_SMALL:
			return assetManager.isLoaded("textures/projectiles/slash/Slash.png");
		case Indentifiers.ASSETMANAGER_EXPLOSION_SMALL:
			return assetManager.isLoaded("textures/explosions/Explosion-Small.png");
		case Indentifiers.ASSETMANAGER_SHOPKEEPER:
			return assetManager.isLoaded("textures/characters/shopkeeper/Rabbit-Idle.png");
		case Indentifiers.ASSETMANAGER_MAGE:
			return assetManager.isLoaded("textures/characters/mage/Mage-W-Front.png");
		case Indentifiers.ASSETMANAGER_TINKER:
			return assetManager.isLoaded("textures/enemies/tinker/Tinker-A-Back.png");
		case Indentifiers.ASSETMANAGER_FLUNK:
			return assetManager.isLoaded("textures/enemies/flunk/Flunk-All.png");
		case Indentifiers.ASSETMANAGER_EGG:
			return assetManager.isLoaded("textures/enemies/eggs/Egg-Hatch.png");
		case Indentifiers.ASSETMANAGER_TURRET_MECHA:
			return assetManager.isLoaded("textures/items/turrets/mecha-turret/4-Way-MechaTurret-Idle.png");
		case Indentifiers.ASSETMANAGER_BARRIER_BLUE:
			return assetManager.isLoaded("textures/items/barriers/barrier-blue/Barrier-Blue-Cast.png");
		case Indentifiers.ASSETMANAGER_GORE:
			return assetManager.isLoaded("textures/particles/gore/Gore-1.png");
		case Indentifiers.ASSETMANAGER_SHELL:
			return assetManager.isLoaded("textures/particles/shells/Shell.png");
		case Indentifiers.ASSETMANAGER_PUDDLE_BLOOD:
			return assetManager.isLoaded("textures/puddles/bloodpuddles/Bloodpuddle-Small-1.png");
		default:
			VNLogger.log("ID was not found: "+id, this.getClass());
			return false;
		}
	}
	
	public void loadAudio(int id){
		switch(id){
		case Indentifiers.ASSETMANAGER_AUDIO_INTOTHEUNKOWN:
			assetManager.load("audio/music/Into The Unknown.mp3", Music.class);
			break;
		case Indentifiers.ASSETMANAGER_AUDIO_CLAW:
			assetManager.load("audio/sounds/Claw.mp3", Sound.class);
			break;
		case Indentifiers.ASSETMANAGER_AUDIO_WALKINGONSTONE:
			assetManager.load("audio/sounds/walking/Walking-On-Stone.mp3", Sound.class);
			break;
		case Indentifiers.ASSETMANAGER_AUDIO_FIRE1:
			assetManager.load("audio/sounds/fire/Fire-1.mp3", Sound.class);
			break;
		case Indentifiers.ASSETMANAGER_AUDIO_FIRE2:
			assetManager.load("audio/sounds/fire/Fire-2.mp3", Sound.class);
			break;
		case Indentifiers.ASSETMANAGER_AUDIO_ITEMPICKDROP:
			assetManager.load("audio/sounds/items/Item-Pick-Drop.mp3", Sound.class);
			break;
		case Indentifiers.ASSETMANAGER_AUDIO_WALKINGNEXTLEVEL:
			assetManager.load("audio/sounds/walking/Walking-Next-Level.mp3", Sound.class);
			break;
		case Indentifiers.ASSETMANAGER_AUDIO_LEVELUP:
			assetManager.load("audio/sounds/general/Level-Up.mp3", Sound.class);
			break;
		case Indentifiers.ASSETMANAGER_AUDIO_BULLETSHOOTING:
			assetManager.load("audio/sounds/projectiles/Bullet-Shooting.mp3", Sound.class);
			break;
		case Indentifiers.ASSETMANAGER_AUDIO_BARRIERBUILDUP:
			assetManager.load("audio/sounds/items/Barrier-Build-Up.mp3", Sound.class);
			break;
		case Indentifiers.ASSETMANAGER_AUDIO_FOODEATING:
			assetManager.load("audio/sounds/items/Food-Eating.mp3", Sound.class);
			break;
		case Indentifiers.ASSETMANAGER_AUDIO_ADVENTURE:
			assetManager.load("audio/music/Adventure.mp3", Music.class);
			break;
		default:
			VNLogger.log("Audio ID was not found: "+id, this.getClass());
			break;
		}
		loadedAudio.add(id);
		assetManager.finishLoading();
		assetManager.update();
	}

	public void unloadAudio(int id){
		switch (id) {
		case Indentifiers.ASSETMANAGER_AUDIO_CLAW:
			this.unload("audio/sounds/Claw.mp3");
			break;
		case Indentifiers.ASSETMANAGER_AUDIO_WALKINGONSTONE:
			this.unload("audio/sounds/walking/Walking-On-Stone.mp3");
			break;
		case Indentifiers.ASSETMANAGER_AUDIO_FIRE1:
			this.unload("audio/sounds/fire/Fire-1.mp3");
			break;
		case Indentifiers.ASSETMANAGER_AUDIO_FIRE2:
			this.unload("audio/sounds/fire/Fire-2.mp3");
			break;
		case Indentifiers.ASSETMANAGER_AUDIO_ITEMPICKDROP:
			this.unload("audio/sounds/items/Item-Pick-Drop.mp3");
			break;
		case Indentifiers.ASSETMANAGER_AUDIO_WALKINGNEXTLEVEL:
			this.unload("audio/sounds/walking/Walking-Next-Level.mp3");
			break;
		case Indentifiers.ASSETMANAGER_AUDIO_LEVELUP:
			this.unload("audio/sounds/general/Level-Up.mp3");
			break;
		case Indentifiers.ASSETMANAGER_AUDIO_BULLETSHOOTING:
			this.unload("audio/sounds/projectiles/Bullet-Shooting.mp3");
			break;
		case Indentifiers.ASSETMANAGER_AUDIO_BARRIERBUILDUP:
			this.unload("audio/sounds/items/Barrier-Build-Up.mp3");
			break;
		case Indentifiers.ASSETMANAGER_AUDIO_FOODEATING:
			this.unload("audio/sounds/items/Food-Eating.mp3");
			break;
		case Indentifiers.ASSETMANAGER_AUDIO_ADVENTURE:
			this.unload("audio/music/Adventure.mp3");
			break;
		default:
			VNLogger.log("Audio ID was not found: "+id, this.getClass());
			break;
		}
		loadedAudio.remove(Integer.valueOf(id));
		assetManager.update();
	}
	
	public boolean isAudioLoaded(int id){
		switch (id) {
		case Indentifiers.ASSETMANAGER_AUDIO_CLAW:
			return assetManager.isLoaded("audio/sounds/Claw.mp3");
		case Indentifiers.ASSETMANAGER_AUDIO_WALKINGONSTONE:
			return assetManager.isLoaded("audio/sounds/walking/Walking-On-Stone.mp3");
		case Indentifiers.ASSETMANAGER_AUDIO_FIRE1:
			return assetManager.isLoaded("audio/sounds/fire/Fire-1.mp3");
		case Indentifiers.ASSETMANAGER_AUDIO_FIRE2:
			return assetManager.isLoaded("audio/sounds/fire/Fire-2.mp3");
		case Indentifiers.ASSETMANAGER_AUDIO_ITEMPICKDROP:
			return assetManager.isLoaded("audio/sounds/items/Item-Pick-Drop.mp3");
		case Indentifiers.ASSETMANAGER_AUDIO_WALKINGNEXTLEVEL:
			return assetManager.isLoaded("audio/sounds/walking/Walking-Next-Level.mp3");
		case Indentifiers.ASSETMANAGER_AUDIO_LEVELUP:
			return assetManager.isLoaded("audio/sounds/general/Level-Up.mp3");
		case Indentifiers.ASSETMANAGER_AUDIO_BULLETSHOOTING:
			return assetManager.isLoaded("audio/sounds/projectiles/Bullet-Shooting.mp3");
		case Indentifiers.ASSETMANAGER_AUDIO_BARRIERBUILDUP:
			return assetManager.isLoaded("audio/sounds/items/Barrier-Build-Up.mp3");
		case Indentifiers.ASSETMANAGER_AUDIO_FOODEATING:
			return assetManager.isLoaded("audio/sounds/items/Food-Eating.mp3");
		case Indentifiers.ASSETMANAGER_AUDIO_ADVENTURE:
			return assetManager.isLoaded("audio/music/Adventure.mp3");
		default:
			VNLogger.log("Audio ID was not found: "+id, this.getClass());
			return false;
		}
	}
	
	private void unload(String path) {
		if (assetManager.isLoaded(path))
			assetManager.unload(path);
	}

	public void dispose() {
		assetManager.dispose();
	}
}
