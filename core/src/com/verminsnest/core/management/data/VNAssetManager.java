package com.verminsnest.core.management.data;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.verminsnest.core.management.Indentifiers;

public class VNAssetManager {
	private AssetManager assetManager;

	public VNAssetManager() {
		assetManager = new AssetManager();
	}

	public void loadTextures(int id) {
		switch (id) {
		case (Indentifiers.ASSETMANAGER_INIT):
			assetManager.load("textures/menus/scrolls/VerticalScroll_Small.png", Texture.class);
			assetManager.load("textures/menus/scrolls/VerticalScroll_Big.png", Texture.class);
			assetManager.load("textures/misc/Cursor.png", Texture.class);

			assetManager.load("textures/general/Background.png", Texture.class);
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
			break;
		case Indentifiers.ASSETMANAGER_BULLET_GREEN:
			assetManager.load("textures/projectiles/bullets/BulletGreen-Flying.png", Texture.class);
			assetManager.load("textures/projectiles/bullets/BulletGreen-Hit.png", Texture.class);
			break;
		case Indentifiers.ASSETMANAGER_FIREBALL:
			assetManager.load("textures/projectiles/fireball/FireBall-Flying.png", Texture.class);
			assetManager.load("textures/projectiles/fireball/FireBall-Hit.png", Texture.class);
			assetManager.load("textures/projectiles/fireball/FireBall-Cast.png", Texture.class);
			break;
		case Indentifiers.ASSETMANAGER_SLASH_SMALL:
			assetManager.load("textures/projectiles/slash/SlashLeft.png", Texture.class);
			assetManager.load("textures/projectiles/slash/SlashRight.png", Texture.class);
			break;
		case Indentifiers.ASSETMANAGER_EXPLOSION_SMALL:
			assetManager.load("textures/explosions/Explosion-Small.png", Texture.class);
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
			break;
		case Indentifiers.ASSETMANAGER_TURRET_MECHA:
			assetManager.load("textures/items/turrets/mecha-turret/MechaTurret-Idle.png", Texture.class);
			assetManager.load("textures/items/turrets/mecha-turret/MechaTurret-A-East.png", Texture.class);
			assetManager.load("textures/items/turrets/mecha-turret/MechaTurret-A-West.png", Texture.class);
			assetManager.load("textures/items/turrets/mecha-turret/MechaTurret-A-South.png", Texture.class);
			assetManager.load("textures/items/turrets/mecha-turret/MechaTurret-A-North.png", Texture.class);
			assetManager.load("textures/items/turrets/mecha-turret/MechaTurret-Icon.png", Texture.class);
			break;
		case Indentifiers.ASSETMANAGER_GORE:
			assetManager.load("textures/gore/Gore-1.png", Texture.class);
			assetManager.load("textures/gore/Gore-2.png", Texture.class);
			assetManager.load("textures/gore/Gore-3.png", Texture.class);
			break;
		}
		
		assetManager.finishLoading();
		assetManager.update();
	}

	public Texture getAsset(String path) {
		return assetManager.get(path);
	}

	public void disposeTextures(int id) {
		switch (id) {
		case (Indentifiers.ASSETMANAGER_INIT):
			this.unload("textures/menus/scrolls/VerticalScroll_Small.png");
			this.unload("textures/menus/scrolls/VerticalScroll_Big.png");
			this.unload("textures/misc/Cursor.png");
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
			break;
		case Indentifiers.ASSETMANAGER_BULLET_GREEN:
			this.unload("textures/projectiles/bullets/BulletGreen-Flying.png");
			this.unload("textures/projectiles/bullets/BulletGreen-Hit.png");
			break;
		case Indentifiers.ASSETMANAGER_FIREBALL:
			this.unload("textures/projectiles/fireball/FireBall-Flying.png");
			this.unload("textures/projectiles/fireball/FireBall-Hit.png");
			this.unload("textures/projectiles/fireball/FireBall-Cast.png");
			break;
		case Indentifiers.ASSETMANAGER_SLASH_SMALL:
			this.unload("textures/projectiles/slash/Slash.png");
			break;
		case Indentifiers.ASSETMANAGER_EXPLOSION_SMALL:
			this.unload("textures/explosions/Explosion-Small.png");
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
			this.unload("textures/items/turrets/mecha-turret/MechaTurret-Idle.png");
			this.unload("textures/items/turrets/mecha-turret/MechaTurret-A-East.png");
			this.unload("textures/items/turrets/mecha-turret/MechaTurret-A-West.png");
			this.unload("textures/items/turrets/mecha-turret/MechaTurret-A-South.png");
			this.unload("textures/items/turrets/mecha-turret/MechaTurret-A-North.png");
			this.unload("textures/items/turrets/mecha-turret/MechaTurret-Icon.png");
			break;
		case Indentifiers.ASSETMANAGER_BARRIER_BLUE:
			this.unload("textures/items/barriers/barrier-blue/Barrier-Blue-Cast.png");
			this.unload("textures/items/barriers/barrier-blue/Barrier-Blue-Idle.png");
			this.unload("textures/items/barriers/barrier-blue/Barrier-Blue-Break.png");
			this.unload("textures/items/barriers/barrier-blue/Barrier-Blue-Icon.png");
			break;
		case Indentifiers.ASSETMANAGER_GORE:
			this.unload("textures/gore/Gore-1.png");
			this.unload("textures/gore/Gore-2.png");
			this.unload("textures/gore/Gore-3.png");
			break;
		}
		assetManager.update();
	}

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
			return assetManager.isLoaded("textures/items/turrets/mecha-turret/MechaTurret-Idle.png");
		case Indentifiers.ASSETMANAGER_BARRIER_BLUE:
			return assetManager.isLoaded("textures/items/barriers/barrier-blue/Barrier-Blue-Cast.png");
		case Indentifiers.ASSETMANAGER_GORE:
			return assetManager.isLoaded("textures/gore/Gore-1.png");
		default:
			//TODO Log this
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
