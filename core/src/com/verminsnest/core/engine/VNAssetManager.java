package com.verminsnest.core.engine;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class VNAssetManager {
	
	public final static int MENU = 0;
	
	public final static int GAMEPLAY = 1;
	
	public final static int GAMEPLAY_MAGE = 2;
	
	public final static int GAMEPLAY_FIREBALL = 3;
	public final static int GAMEPLAY_SLASH_SMALL = 5;
	
	public final static int GAMEPLAY_TINKER = 4;
	private AssetManager assetManager;
	
	public VNAssetManager(){
		assetManager = new AssetManager();
	}
	
	public void loadTextures(int id){
		switch (id){
		case GAMEPLAY:
			assetManager.load("textures/menus/scrolls/VerticalScroll_Small.png", Texture.class);
			assetManager.load("textures/menus/scrolls/HorizontalScroll_Minimum.png", Texture.class);
			assetManager.load("textures/menus/scrolls/VerticalScroll_Big.png", Texture.class);
			
			assetManager.load("textures/menus/frames/HealthFrame.png", Texture.class);
			assetManager.load("textures/menus/frames/AbilityFrame.png", Texture.class);
			assetManager.load("textures/menus/frames/RoomFrame.png", Texture.class);
			assetManager.load("textures/menus/frames/AbilityFrameBackground.png", Texture.class);
			assetManager.load("textures/menus/frames/MapFrame.png", Texture.class);
			assetManager.load("textures/menus/frames/StatusFrame.png", Texture.class);
			
			assetManager.load("textures/level-sheets/cave/Mountain-Sheet.png", Texture.class);
			
			assetManager.load("textures/enemies/Shadow.png", Texture.class);
			assetManager.load("textures/characters/Character-Shadow.png", Texture.class);
			assetManager.load("textures/characters/Minimap-Pointer.png", Texture.class);
			break;
		case GAMEPLAY_FIREBALL:
			assetManager.load("textures/projectiles/fireball/FireBall-Shadow.png", Texture.class);
			assetManager.load("textures/projectiles/fireball/FireBall-Flying.png", Texture.class);
			assetManager.load("textures/projectiles/fireball/FireBall-Hit.png", Texture.class);
			assetManager.load("textures/projectiles/fireball/FireBall-Cast.png", Texture.class);
		case GAMEPLAY_SLASH_SMALL:
			assetManager.load("textures/projectiles/slash/SlashLeft.png", Texture.class);
			assetManager.load("textures/projectiles/slash/SlashRight.png", Texture.class);
			break;
		case GAMEPLAY_MAGE:
			assetManager.load("textures/characters/mage/Mage-W-Front.png", Texture.class);
			assetManager.load("textures/characters/mage/Mage-W-Back.png", Texture.class);
			assetManager.load("textures/characters/mage/Mage-W-Right.png", Texture.class);
			assetManager.load("textures/characters/mage/Mage-W-Left.png", Texture.class);
			assetManager.load("textures/characters/mage/Mage-Idle.png", Texture.class);
			assetManager.load("textures/projectiles/fireball/FireBall_Icon.png", Texture.class);
			break;
		case GAMEPLAY_TINKER:
			assetManager.load("textures/enemies/tinker/Tinker-A-Back.png", Texture.class);
			assetManager.load("textures/enemies/tinker/Tinker-A-Front.png", Texture.class);
			assetManager.load("textures/enemies/tinker/Tinker-A-Left.png", Texture.class);
			assetManager.load("textures/enemies/tinker/Tinker-A-Right.png", Texture.class);
			assetManager.load("textures/enemies/tinker/Tinker-W-Back.png", Texture.class);
			assetManager.load("textures/enemies/tinker/Tinker-W-Front.png", Texture.class);
			assetManager.load("textures/enemies/tinker/Tinker-W-Left.png", Texture.class);
			assetManager.load("textures/enemies/tinker/Tinker-W-Right.png", Texture.class);
			assetManager.load("textures/enemies/tinker/Tinker-Idle.png", Texture.class);
			break;
		case MENU:
			assetManager.load("textures/general/Background.png", Texture.class);
			assetManager.load("textures/menus/scrolls/VerticalScroll_Small.png", Texture.class);
			assetManager.load("textures/menus/scrolls/VerticalScroll_Big.png", Texture.class);
			break;
		}
		assetManager.finishLoading();
		assetManager.update();
	}
	
	public Texture getAsset(String path){
		return assetManager.get(path);
	}
	
	public void disposeTextures(int id){
		switch (id){
		case GAMEPLAY:
			this.unload("textures/menus/scrolls/VerticalScroll_Small.png");
			this.unload("textures/menus/scrolls/VerticalScroll_Big.png");
			this.unload("textures/menus/scrolls/HorizontalScroll_Minimum.png");
			
			this.unload("textures/menus/frames/HealthFrame.png");
			this.unload("textures/menus/frames/AbilityFrame.png");
			this.unload("textures/menus/frames/RoomFrame.png");
			this.unload("textures/menus/frames/AbilityFrameBackground.png");
			this.unload("textures/menus/frames/MapFrame.png");
			this.unload("textures/menus/frames/StatusFrame.png");
			
			this.unload("textures/level-sheets/cave/Mountain-Sheet.png");
			
			this.unload("textures/characters/Character-Shadow.png");
			this.unload("textures/enemies/Shadow.png");
			this.unload("textures/characters/Minimap-Pointer.png");
			break;
		case GAMEPLAY_FIREBALL:
			this.unload("textures/projectiles/fireball/FireBall-Shadow.png");
			this.unload("textures/projectiles/fireball/FireBall-Flying.png");
			this.unload("textures/projectiles/fireball/FireBall-Hit.png");
			this.unload("textures/projectiles/fireball/FireBall-Cast.png");
			break;		
		case GAMEPLAY_SLASH_SMALL:
			this.unload("textures/projectiles/slash/Slash.png");
			break;
		case GAMEPLAY_MAGE:
			this.unload("textures/characters/mage/Mage-W-Front.png");
			this.unload("textures/characters/mage/Mage-W-Back.png");
			this.unload("textures/characters/mage/Mage-W-Right.png");
			this.unload("textures/characters/mage/Mage-W-Left.png");
			this.unload("textures/characters/mage/Mage-Idle.png");
			this.unload("textures/projectiles/fireball/FireBall_Icon.png");
			break;
		case GAMEPLAY_TINKER:
			this.unload("textures/enemies/tinker/Tinker-A-Back.png");
			this.unload("textures/enemies/tinker/Tinker-A-Front.png");
			this.unload("textures/enemies/tinker/Tinker-A-Left.png");
			this.unload("textures/enemies/tinker/Tinker-A-Right.png");
			this.unload("textures/enemies/tinker/Tinker-W-Back.png");
			this.unload("textures/enemies/tinker/Tinker-W-Front.png");
			this.unload("textures/enemies/tinker/Tinker-W-Left.png");
			this.unload("textures/enemies/tinker/Tinker-W-Right.png");
			this.unload("textures/enemies/tinker/Tinker-Idle.png");
			break;
		case MENU:
			this.unload("textures/general/Background.png");
			this.unload("textures/menus/scrolls/VerticalScroll_Small.png");
			this.unload("textures/menus/scrolls/VerticalScroll_Big.png");
			break;
		}
		assetManager.update();
	}
	
	public boolean areAssetsLoaded(int id){
		switch (id){
		case GAMEPLAY:
			return assetManager.isLoaded("textures/menus/scrolls/VerticalScroll_Small.png");
		case GAMEPLAY_FIREBALL:
			return assetManager.isLoaded("textures/projectiles/fireball/FireBall-Shadow.png");
		case GAMEPLAY_SLASH_SMALL:
			return assetManager.isLoaded("textures/projectiles/slash/Slash.png");
		case GAMEPLAY_MAGE:
			return assetManager.isLoaded("textures/characters/mage/Mage-W-Front.png");
		case GAMEPLAY_TINKER:
			return assetManager.isLoaded("textures/enemies/tinker/Tinker-A-Back.png");
		case MENU:
			return assetManager.isLoaded("textures/general/Background.png");
		default:
			return false;
		}
	}
	
	private void unload(String path){
		if(assetManager.isLoaded(path))assetManager.unload(path);
	}
	public void dispose(){
		assetManager.dispose();
	}
}
