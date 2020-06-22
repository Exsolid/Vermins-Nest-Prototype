package com.verminsnest.core.management.data;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.verminsnest.core.management.Indentifiers;

public class VNAssetManager {
	private AssetManager assetManager;
	
	public VNAssetManager(){
		assetManager = new AssetManager();
	}
	
	public void loadTextures(int id){
		switch (id){
		case(Indentifiers.ASSETMANAGER_INIT):
			assetManager.load("textures/menus/scrolls/VerticalScroll_Small.png", Texture.class);
			assetManager.load("textures/menus/scrolls/VerticalScroll_Big.png", Texture.class);
			assetManager.load("textures/misc/Cursor.png", Texture.class);

			assetManager.load("textures/general/Background.png", Texture.class);
			break;
		case Indentifiers.ASSETMANAGER_GAMEPLAY:
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
			assetManager.load("textures/level-sheets/cave/Mountain-Hole.png", Texture.class);
			assetManager.load("textures/level-sheets/cave/Dirt-Cloud.png", Texture.class);
			
			assetManager.load("textures/enemies/Shadow.png", Texture.class);
			assetManager.load("textures/characters/Character-Shadow.png", Texture.class);
			
			assetManager.load("textures/misc/Minimap-Pointer.png", Texture.class);
			assetManager.load("textures/misc/Cursor.png", Texture.class);
			break;
		case Indentifiers.ASSETMANAGER_FIREBALL:
			assetManager.load("textures/projectiles/fireball/FireBall-Shadow.png", Texture.class);
			assetManager.load("textures/projectiles/fireball/FireBall-Flying.png", Texture.class);
			assetManager.load("textures/projectiles/fireball/FireBall-Hit.png", Texture.class);
			assetManager.load("textures/projectiles/fireball/FireBall-Cast.png", Texture.class);
		case Indentifiers.ASSETMANAGER_SLASH_SMALL:
			assetManager.load("textures/projectiles/slash/SlashLeft.png", Texture.class);
			assetManager.load("textures/projectiles/slash/SlashRight.png", Texture.class);
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
			break;
		case Indentifiers.ASSETMANAGER_GORE:
			assetManager.load("textures/gore/Gore-1.png", Texture.class);
			assetManager.load("textures/gore/Gore-2.png", Texture.class);
			assetManager.load("textures/gore/Gore-3.png", Texture.class);
			break;
		case Indentifiers.ASSETMANAGER_EGG:
			assetManager.load("textures/enemies/eggs/Egg-Hatch.png", Texture.class);
			assetManager.load("textures/enemies/eggs/Egg-Idle.png", Texture.class);
			assetManager.load("textures/enemies/eggs/Egg-Leftover.png", Texture.class);
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
		case(Indentifiers.ASSETMANAGER_INIT):
			this.unload("textures/menus/scrolls/VerticalScroll_Small.png");
			this.unload("textures/menus/scrolls/VerticalScroll_Big.png");
			this.unload("textures/misc/Cursor.png");
			break;
		case Indentifiers.ASSETMANAGER_GAMEPLAY:
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
			this.unload("textures/level-sheets/cave/Mountain-Hole.png");
			this.unload("textures/level-sheets/cave/Dirt-Cloud.png");
			
			this.unload("textures/characters/Character-Shadow.png");
			this.unload("textures/enemies/Shadow.png");
			this.unload("textures/misc/Minimap-Pointer.png");
			this.unload("textures/misc/Cursor.png");
			break;		
		case Indentifiers.ASSETMANAGER_FIREBALL:
			this.unload("textures/projectiles/fireball/FireBall-Shadow.png");
			this.unload("textures/projectiles/fireball/FireBall-Flying.png");
			this.unload("textures/projectiles/fireball/FireBall-Hit.png");
			this.unload("textures/projectiles/fireball/FireBall-Cast.png");
			break;		
		case Indentifiers.ASSETMANAGER_SLASH_SMALL:
			this.unload("textures/projectiles/slash/Slash.png");
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
			break;
		case Indentifiers.ASSETMANAGER_GORE:
			this.unload("textures/gore/Gore-1.png");
			this.unload("textures/gore/Gore-2.png");
			this.unload("textures/gore/Gore-3.png");
			break;
		case Indentifiers.ASSETMANAGER_EGG:
			this.unload("textures/enemies/eggs/Egg-Hatch.png");
			this.unload("textures/enemies/eggs/Egg-Idle.png");
			this.unload("textures/enemies/eggs/Egg-Leftover.png");
			break;
		}
		assetManager.update();
	}
	
	public boolean areAssetsLoaded(int id){
		switch (id){		
		case(Indentifiers.ASSETMANAGER_INIT):
			return assetManager.isLoaded("textures/misc/Cursor.png");
		case Indentifiers.ASSETMANAGER_GAMEPLAY:
			return assetManager.isLoaded("textures/menus/scrolls/VerticalScroll_Small.png");
		case Indentifiers.ASSETMANAGER_FIREBALL:
			return assetManager.isLoaded("textures/projectiles/fireball/FireBall-Shadow.png");
		case Indentifiers.ASSETMANAGER_SLASH_SMALL:
			return assetManager.isLoaded("textures/projectiles/slash/Slash.png");
		case Indentifiers.ASSETMANAGER_MAGE:
			return assetManager.isLoaded("textures/characters/mage/Mage-W-Front.png");
		case Indentifiers.ASSETMANAGER_TINKER:
			return assetManager.isLoaded("textures/enemies/tinker/Tinker-A-Back.png");
		case Indentifiers.ASSETMANAGER_GORE:
			return assetManager.isLoaded("textures/gore/Gore-1.png");
		case Indentifiers.ASSETMANAGER_EGG:
			return assetManager.isLoaded("textures/enemies/eggs/Egg-Hatch.png");
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
