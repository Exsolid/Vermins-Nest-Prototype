package com.verminsnest.screens.gameplay.menus;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.core.management.Indentifiers;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.misc.gui.FontText;
import com.verminsnest.screens.gameplay.GameManager;
import com.verminsnest.screens.gameplay.GameplayOverlay;
import com.verminsnest.world.generation.map.rooms.Room;

public class GameplayMenu extends GameplayOverlay {
	private int[] dataScrollPos;
	private int[] mapPos;
	private int[] attackDataPos;
	private int[] itemDataPos;
	private int[] foodDataPos;

	private ArrayList<MinimapRoom> rooms;

	private TextureRegion[][] attackCooldown;
	private TextureRegion[][] itemCooldown;
	private int attackData;
	private int itemData;
	private FontText hp;
	private FontText foodCounter;
	private FontText level;
	private FontText killCounter;

	public GameplayMenu(GameManager gameMan) {
		super(gameMan);
		rooms = new ArrayList<>();
		
		hp = new FontText("0", 40, false);
		foodCounter = new FontText("0", 40, false);
		level = new FontText("0", 50, false);
		killCounter = new FontText("0", 35, false);
		
		attackDataPos = new int[] { 0, 0 };
		itemDataPos = new int[] { 0, 0 };
		foodDataPos = new int[] { 0, 0 };
		mapPos = new int[] { 0, 0 };
		dataScrollPos = new int[] {
				(int) (RuntimeData.getInstance().getGame().getCamera().position.x - RuntimeData.getInstance()
						.getAsset("textures/menus/scrolls/HorizontalScroll_Minimum.png").getWidth() / 2),
				(int) (RuntimeData.getInstance().getGame().getCamera().position.y - RuntimeData.getInstance().getGame().getConfig().getResolution()[1] / 2) };
		attackCooldown = TextureRegion.split(
				RuntimeData.getInstance().getAsset("textures/menus/frames/AbilityFrameBackground.png"),
				RuntimeData.getInstance().getAsset("textures/menus/frames/AbilityFrameBackground.png").getWidth(),
				RuntimeData.getInstance().getAsset("textures/menus/frames/AbilityFrameBackground.png").getHeight()
						/ 50);
		itemCooldown = TextureRegion.split(
				RuntimeData.getInstance().getAsset("textures/menus/frames/AbilityFrameBackground.png"),
				RuntimeData.getInstance().getAsset("textures/menus/frames/AbilityFrameBackground.png").getWidth(),
				RuntimeData.getInstance().getAsset("textures/menus/frames/AbilityFrameBackground.png").getHeight()
						/ 50);
		attackData = 0;
		itemData = 0;
	}

	@Override
	public void render(float stateTime) {
		// Draw background stuff
		RuntimeData.getInstance().getGame().getBatch().draw(RuntimeData.getInstance().getAsset("textures/menus/frames/StatusFrame.png"),
				dataScrollPos[0], dataScrollPos[1]);
		RuntimeData.getInstance().getGame().getBatch().draw(RuntimeData.getInstance().getAsset("textures/menus/frames/MapFrame.png"), mapPos[0],
				mapPos[1]);
		// Draw cooldown bars
		for (int i = 0; i < attackData; i++) {
			RuntimeData.getInstance().getGame().getBatch().draw(attackCooldown[i][0], attackDataPos[0],
					attackDataPos[1] + 8 + attackCooldown[0][0].getRegionHeight() * i);
		}
		for (int i = 0; i < itemData; i++) {
			RuntimeData.getInstance().getGame().getBatch().draw(itemCooldown[i][0], itemDataPos[0],
					itemDataPos[1] + 8 + itemCooldown[0][0].getRegionHeight() * i);
		}

		// Draw frames
		RuntimeData.getInstance().getGame().getBatch().draw(RuntimeData.getInstance().getAsset("textures/menus/frames/AbilityFrame.png"),
				itemDataPos[0], itemDataPos[1]);
		RuntimeData.getInstance().getGame().getBatch().draw(RuntimeData.getInstance().getAsset("textures/menus/frames/AbilityFrame.png"),
				attackDataPos[0], attackDataPos[1]);
		// Draw attack icon
		RuntimeData.getInstance().getGame().getBatch().draw(
				RuntimeData.getInstance().getAsset(RuntimeData.getInstance().getEntityManager().getCharacter().getAttackIcon()),
				attackDataPos[0]
						+ RuntimeData.getInstance().getAsset("textures/menus/frames/AbilityFrame.png").getWidth() / 2
						- RuntimeData.getInstance().getAsset(RuntimeData.getInstance().getEntityManager().getCharacter().getAttackIcon())
								.getWidth() / 2
						- 4,
				attackDataPos[1]
						+ RuntimeData.getInstance().getAsset("textures/menus/frames/AbilityFrame.png").getHeight() / 2
						- RuntimeData.getInstance().getAsset(RuntimeData.getInstance().getEntityManager().getCharacter().getAttackIcon())
								.getHeight() / 2
						+ 4);
		//Draw item icon
		if(RuntimeData.getInstance().getEntityManager().getCharacter().getInventory().getItem() != null){
			RuntimeData.getInstance().getGame().getBatch().draw(
					RuntimeData.getInstance().getAsset(RuntimeData.getInstance().getEntityManager().getCharacter().getInventory().getItem().getIconPath()),
					itemDataPos[0]
							+ RuntimeData.getInstance().getAsset("textures/menus/frames/AbilityFrame.png").getWidth() / 2
							- RuntimeData.getInstance().getAsset(RuntimeData.getInstance().getEntityManager().getCharacter().getInventory().getItem().getIconPath())
									.getWidth() / 2
							- 4,
					itemDataPos[1]
							+ RuntimeData.getInstance().getAsset("textures/menus/frames/AbilityFrame.png").getHeight() / 2
							- RuntimeData.getInstance().getAsset(RuntimeData.getInstance().getEntityManager().getCharacter().getInventory().getItem().getIconPath())
									.getHeight() / 2
							+ 4);

		}
		// Draw character data
		hp.draw(RuntimeData.getInstance().getGame().getBatch());
		level.draw(RuntimeData.getInstance().getGame().getBatch());
		killCounter.draw(RuntimeData.getInstance().getGame().getBatch());
		foodCounter.draw(RuntimeData.getInstance().getGame().getBatch());
		//Draw food icon
		RuntimeData.getInstance().getGame().getBatch().draw(RuntimeData.getInstance().getAsset("textures/items/Food.png"),foodDataPos[0],foodDataPos[1]);
		// Draw map
		for (MinimapRoom room : rooms) {
			for (int y = 0; y < room.roomTextures[0].length; y++) {
				for (int x = 0; x < room.roomTextures.length; x++) {
					if (room.roomTextures[x][y] != null
							&& room.position[0] + room.roomTextures[x][y].getRegionWidth() * x > mapPos[0]
							&& room.position[0] + room.roomTextures[x][y].getRegionWidth() * x < mapPos[0] + RuntimeData
									.getInstance().getAsset("textures/menus/frames/MapFrame.png").getWidth()
							&& room.position[1] + room.roomTextures[x][y].getRegionHeight() * y > mapPos[1]
							&& room.position[1] + room.roomTextures[x][y].getRegionHeight() * y < mapPos[1]
									+ RuntimeData.getInstance().getAsset("textures/menus/frames/MapFrame.png")
											.getHeight() 
							&& room.toDraw) {
						RuntimeData.getInstance().getGame().getBatch().draw(room.roomTextures[x][y],
								room.position[0] + room.roomTextures[x][y].getRegionWidth() * x,
								room.position[1] + room.roomTextures[x][y].getRegionHeight() * y);
					}
				}
			}
		}
		RuntimeData.getInstance().getGame().getBatch().draw(RuntimeData.getInstance().getAsset("textures/misc/Minimap-Pointer.png"),
				mapPos[0] + RuntimeData.getInstance().getAsset("textures/menus/frames/MapFrame.png").getWidth() / 2
						- RuntimeData.getInstance().getAsset("textures/misc/Minimap-Pointer.png").getWidth() / 2,
				mapPos[1] + RuntimeData.getInstance().getAsset("textures/menus/frames/MapFrame.png").getHeight() / 2
						- RuntimeData.getInstance().getAsset("textures/misc/Minimap-Pointer.png").getHeight()
								/ 2);
	}

	@Override
	public void manageControls(float stateTime) {
	}

	@Override
	public void update(float delta) {
		// Update background stuff position
		dataScrollPos[0] = (int) (RuntimeData.getInstance().getEntityManager().getCharacter().getPos()[0]
				- RuntimeData.getInstance().getAsset("textures/menus/frames/StatusFrame.png").getWidth() / 2);
		dataScrollPos[1] = (int) (RuntimeData.getInstance().getEntityManager().getCharacter().getPos()[1] - 525 + 5);
		mapPos[0] = (int) (RuntimeData.getInstance().getEntityManager().getCharacter().getPos()[0] + 960
				- RuntimeData.getInstance().getAsset("textures/menus/frames/MapFrame.png").getWidth() - 5);
		mapPos[1] = (int) (RuntimeData.getInstance().getEntityManager().getCharacter().getPos()[1] + 525
				- RuntimeData.getInstance().getAsset("textures/menus/frames/MapFrame.png").getHeight() - 5);
		// Update hp text position
		hp.setText("HP: " + Integer.toString(RuntimeData.getInstance().getEntityManager().getCharacter().getHealth()) + "/"
				+ Integer.toString(RuntimeData.getInstance().getEntityManager().getCharacter().getMaxHealth()));
		hp.setMidOfBounds(dataScrollPos,
				new int[] { RuntimeData.getInstance().getAsset("textures/menus/frames/StatusFrame.png").getWidth(),
						RuntimeData.getInstance().getAsset("textures/menus/frames/StatusFrame.png").getHeight() });
		hp.getPos()[0] += RuntimeData.getInstance().getAsset("textures/menus/frames/StatusFrame.png").getWidth() / 4;
		hp.getPos()[1] += 10;
		//Update food data position
		foodDataPos = new int[] {hp.getPos()[0]+hp.getBounds()[0]+20,hp.getPos()[1]-RuntimeData.getInstance().getAsset("textures/items/Food.png").getHeight()/2};
		foodCounter.setText("x "+RuntimeData.getInstance().getEntityManager().getCharacter().getInventory().getFoodCount());
		foodCounter.setPos(new int[] {(int) (foodDataPos[0]+RuntimeData.getInstance().getAsset("textures/items/Food.png").getWidth()),hp.getPos()[1]});
		//Update kills and levels
		if(RuntimeData.getInstance().getEntityManager().getCharacter().getSkillPoints() != 0){
			level.setText("Level: " + Integer.toString(RuntimeData.getInstance().getEntityManager().getCharacter().getLevelData()[0])+ "(+)");
		}else{
			level.setText("Level: " + Integer.toString(RuntimeData.getInstance().getEntityManager().getCharacter().getLevelData()[0]));
		}
		level.setMidOfBounds(dataScrollPos,
				new int[] { RuntimeData.getInstance().getAsset("textures/menus/frames/StatusFrame.png").getWidth(),
						RuntimeData.getInstance().getAsset("textures/menus/frames/StatusFrame.png").getHeight() });
		level.getPos()[1] += 10;
		
		// Update hp text position
		killCounter.setText(Integer.toString(RuntimeData.getInstance().getEntityManager().getCharacter().getLevelData()[1]) + "/"
				+ Integer.toString(RuntimeData.getInstance().getEntityManager().getCharacter().getLevelData()[2]));
		killCounter.setMidOfBounds(dataScrollPos,
				new int[] { RuntimeData.getInstance().getAsset("textures/menus/frames/StatusFrame.png").getWidth(),
						RuntimeData.getInstance().getAsset("textures/menus/frames/StatusFrame.png").getHeight()});
		killCounter.getPos()[1] -= killCounter.getBounds()[1];
		// Update frame position
		itemDataPos[0] = dataScrollPos[0] + 100;
		itemDataPos[1] = dataScrollPos[1]
				+ RuntimeData.getInstance().getAsset("textures/menus/frames/StatusFrame.png").getHeight() / 2
				- RuntimeData.getInstance().getAsset("textures/menus/frames/AbilityFrame.png").getWidth() / 2 - 5;
		attackDataPos[0] = dataScrollPos[0] + 115
				+ RuntimeData.getInstance().getAsset("textures/menus/frames/AbilityFrame.png").getWidth();
		attackDataPos[1] = dataScrollPos[1]
				+ RuntimeData.getInstance().getAsset("textures/menus/frames/StatusFrame.png").getHeight() / 2
				- RuntimeData.getInstance().getAsset("textures/menus/frames/AbilityFrame.png").getWidth() / 2 - 5;

		if (RuntimeData.getInstance().getEntityManager().getCharacter().getAttackDetails()[0] <= 0 || RuntimeData.getInstance().getEntityManager().getCharacter().getAttackDetails()[0]
				> RuntimeData.getInstance().getEntityManager().getCharacter().getAttackDetails()[1]) {
			attackData = 59;
		} else {
			attackData = (int) ((RuntimeData.getInstance().getEntityManager().getCharacter().getAttackDetails()[0]
					/ RuntimeData.getInstance().getEntityManager().getCharacter().getAttackDetails()[1]) * 59);
		}
		if(RuntimeData.getInstance().getEntityManager().getCharacter().getInventory().getItem() == null){
			itemData = 59;
		} else if (RuntimeData.getInstance().getEntityManager().getCharacter().getItemDetails()[0] <= 0|| RuntimeData.getInstance().getEntityManager().getCharacter().getItemDetails()[0]
				> RuntimeData.getInstance().getEntityManager().getCharacter().getItemDetails()[1]) {
			itemData = 59;
		} else {
			itemData = (int) ((RuntimeData.getInstance().getEntityManager().getCharacter().getItemDetails()[0]
					/ RuntimeData.getInstance().getEntityManager().getCharacter().getItemDetails()[1]) * 59);
			if(itemData > 59){
				itemData = 0;
			}
		}
		calculateMiniMap();
	}

	private void calculateMiniMap() {
		rooms.clear();
		int[] charPos = RuntimeData.getInstance().getEntityManager().getCharacter().getPos();
		int[] roomSize = RuntimeData.getInstance().getMapData().getRoomSize();
		int[] roomNum = new int[] { ((charPos[0]-10*128) / 128) / roomSize[0],
				((charPos[1]-10*128)/ 128) / roomSize[1]};
		int[] posZero = new int[] {
				mapPos[0] + RuntimeData.getInstance().getAsset("textures/menus/frames/MapFrame.png").getWidth() / 2
						- RuntimeData.getInstance().getAsset("textures/menus/frames/RoomFrame.png").getWidth() / 2,
				mapPos[1] + RuntimeData.getInstance().getAsset("textures/menus/frames/MapFrame.png").getHeight() / 2
						- RuntimeData.getInstance().getAsset("textures/menus/frames/RoomFrame.png").getHeight() / 2 };
		Room[][] layout = RuntimeData.getInstance().getMapData().getRoomLayout();
		
		layout[roomNum[0]][roomNum[1]].setFound(true);
		
		for (int y = 0; y < layout[0].length; y++) {
			for (int x = 0; x < layout.length; x++) {
				if (layout[x][y] != null) {
					TextureRegion[][] tempTex = TextureRegion
							.split(RuntimeData.getInstance().getAsset("textures/menus/frames/RoomFrame.png"), 4, 4);
					if (layout[x][y].getConnected()[Indentifiers.DIRECTION_SOUTH] != null) {
						for (int i = -1; i < 2; i++) {
							tempTex[tempTex.length / 2 + i][0] = null;
							tempTex[tempTex.length / 2 + i][1] = null;
						}
					}
					if (layout[x][y].getConnected()[Indentifiers.DIRECTION_NORTH] != null) {
						for (int i = -1; i < 2; i++) {
							tempTex[tempTex.length / 2 + i][tempTex[0].length - 1] = null;
							tempTex[tempTex.length / 2 + i][tempTex[0].length - 2] = null;
						}
					}
					if (layout[x][y].getConnected()[Indentifiers.DIRECTION_EAST] != null) {
						for (int i = -1; i < 2; i++) {
							tempTex[tempTex.length - 1][tempTex[0].length / 2 + i] = null;
							tempTex[tempTex.length - 2][tempTex[0].length / 2 + i] = null;
						}
					}
					if (layout[x][y].getConnected()[Indentifiers.DIRECTION_WEST] != null) {
						for (int i = -1; i < 2; i++) {
							tempTex[0][tempTex[0].length / 2 + i] = null;
							tempTex[1][tempTex[0].length / 2 + i] = null;
						}
					}
					int[] tempPos = new int[2];

					tempPos[0] = posZero[0] - (roomNum[0] - x)
							* (RuntimeData.getInstance().getAsset("textures/menus/frames/RoomFrame.png").getWidth());
					tempPos[1] = posZero[1] - (roomNum[1] - y)
							* (RuntimeData.getInstance().getAsset("textures/menus/frames/RoomFrame.png").getHeight());
					rooms.add(new MinimapRoom(tempPos, tempTex,layout[x][y].isFound()));
				}
			}
		}

	}

	@Override
	public void dispose() {
		hp.dispose();
		level.dispose();
		killCounter.dispose();
		foodCounter.dispose();
	}

	private class MinimapRoom {
		private MinimapRoom(int[] position, TextureRegion[][] roomTextures, boolean toDraw) {
			this.position = position;
			this.roomTextures = roomTextures;
			this.toDraw = toDraw;
		}
		
		private boolean toDraw;
		private TextureRegion[][] roomTextures;
		private int[] position;
	}
}
