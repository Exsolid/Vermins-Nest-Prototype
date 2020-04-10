package com.verminsnest.misc.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class VNAssetManager {
	
	public final static int MENU = 0;
	
	public final static int GAMEPLAY = 1;
	public final static int GAMEPLAY_MAGE = 2;
	public final static int GAMEPLAY_FIREBALL = 3;
	public final static int GAMEPLAY_TINKER = 4;
	private AssetManager assetManager;
	
	public VNAssetManager(){
		assetManager = new AssetManager();
	}
	
	public void loadTextures(int id){
		switch (id){
		case GAMEPLAY:
			assetManager.load("textures/menus/scrolls/VerticalScroll_Small.png", Texture.class);
			assetManager.load("textures/level-sheets/cave/Mountain-Sheet.png", Texture.class);
			break;
			case GAMEPLAY_FIREBALL:
			assetManager.load("textures/projectiles/fireball/FireBall-Shadow.png", Texture.class);
			assetManager.load("textures/projectiles/fireball/FireBall-Flying.png", Texture.class);
			assetManager.load("textures/projectiles/fireball/FireBall-Hit.png", Texture.class);
			assetManager.load("textures/projectiles/fireball/FireBall-Cast.png", Texture.class);
		case GAMEPLAY_MAGE:
			assetManager.load("textures/characters/mage/Mage-W-Front.png", Texture.class);
			assetManager.load("textures/characters/mage/Mage-W-Back.png", Texture.class);
			assetManager.load("textures/characters/mage/Mage-W-Right.png", Texture.class);
			assetManager.load("textures/characters/mage/Mage-W-Left.png", Texture.class);
			assetManager.load("textures/characters/mage/Mage-Idle.png", Texture.class);
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
			this.unload("textures/level-sheets/cave/Mountain-Sheet.png");
			break;
		case GAMEPLAY_FIREBALL:
			this.unload("textures/projectiles/fireball/FireBall-Shadow.png");
			this.unload("textures/projectiles/fireball/FireBall-Flying.png");
			this.unload("textures/projectiles/fireball/FireBall-Hit.png");
			this.unload("textures/projectiles/fireball/FireBall-Cast.png");
			break;
		case GAMEPLAY_MAGE:
			this.unload("textures/characters/mage/Mage-W-Front.png");
			this.unload("textures/characters/mage/Mage-W-Back.png");
			this.unload("textures/characters/mage/Mage-W-Right.png");
			this.unload("textures/characters/mage/Mage-W-Left.png");
			this.unload("textures/characters/mage/Mage-Idle.png");
			break;
		case GAMEPLAY_TINKER:
			assetManager.unload("textures/enemies/tinker/Tinker-A-Back.png");
			assetManager.unload("textures/enemies/tinker/Tinker-A-Front.png");
			assetManager.unload("textures/enemies/tinker/Tinker-A-Left.png");
			assetManager.unload("textures/enemies/tinker/Tinker-A-Right.png");
			assetManager.unload("textures/enemies/tinker/Tinker-W-Back.png");
			assetManager.unload("textures/enemies/tinker/Tinker-W-Front.png");
			assetManager.unload("textures/enemies/tinker/Tinker-W-Left.png");
			assetManager.unload("textures/enemies/tinker/Tinker-W-Right.png");
			assetManager.unload("textures/enemies/tinker/Tinker-Idle.png");
			break;
		case MENU:
			this.unload("textures/general/Background.png");
			this.unload("textures/menus/scrolls/VerticalScroll_Small.png");
			this.unload("textures/menus/scrolls/VerticalScroll_Big.png");
			break;
		}
		assetManager.update();
	}
	
	private void unload(String path){
		if(assetManager.isLoaded(path))assetManager.unload(path);
	}
	public void dispose(){
		assetManager.dispose();
	}
}
