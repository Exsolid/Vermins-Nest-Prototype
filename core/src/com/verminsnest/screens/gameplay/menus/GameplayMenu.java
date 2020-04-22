package com.verminsnest.screens.gameplay.menus;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.gamedev.VerminsNest;
import com.verminsnest.misc.gui.FontText;
import com.verminsnest.screens.gameplay.GameManager;
import com.verminsnest.screens.gameplay.GameplayScreen;
import com.verminsnest.singletons.RuntimeData;

public class GameplayMenu extends GameplayScreen {
	private int[] dataScrollPos;
	private int[] hpDataPos;
	private int[] attackDataPos;
	private int[] abilityDataPos;
	private TextureRegion[][] attackCooldown;
	private int attackData;
	private FontText hp;

	public GameplayMenu(VerminsNest game, GameManager gameMan) {
		super(game, gameMan);
		hp = new FontText("0", 50);
		hpDataPos = new int[] { 0, 0 };
		attackDataPos = new int[] { 0, 0 };
		abilityDataPos = new int[] { 0, 0 };
		dataScrollPos = new int[] {
				(int) (game.getCamera().position.x - RuntimeData.getInstance()
						.getAsset("textures/menus/scrolls/HorizontalScroll_Minimum.png").getWidth() / 2),
				(int) (game.getCamera().position.y - game.getConfig().getResolution()[1] / 2) };
		attackCooldown = TextureRegion.split(RuntimeData.getInstance().getAsset("textures/menus/frames/AbilityFrameBackground.png"), RuntimeData.getInstance().getAsset("textures/menus/frames/AbilityFrameBackground.png").getWidth(), RuntimeData.getInstance().getAsset("textures/menus/frames/AbilityFrameBackground.png").getHeight()/50);
		attackData =0;
	}

	@Override
	public void render(float stateTime) {
		game.getBatch().begin();
		//Draw scroll
		game.getBatch().draw(RuntimeData.getInstance().getAsset("textures/menus/scrolls/HorizontalScroll_Minimum.png"),
				dataScrollPos[0], dataScrollPos[1]);
		//Draw cooldown bars
		for(int i = 0; i<attackData;i++){
			game.getBatch().draw(attackCooldown[i][0],
					attackDataPos[0], attackDataPos[1]+attackCooldown[0][0].getRegionHeight()*i);
		}
		//Draw frames
		game.getBatch().draw(RuntimeData.getInstance().getAsset("textures/menus/frames/HealthFrame.png"), hpDataPos[0],
				hpDataPos[1]);
		game.getBatch().draw(RuntimeData.getInstance().getAsset("textures/menus/frames/AbilityFrame.png"),
				abilityDataPos[0], abilityDataPos[1]);
		game.getBatch().draw(RuntimeData.getInstance().getAsset("textures/menus/frames/AbilityFrame.png"),
				attackDataPos[0], attackDataPos[1]);
		//Draw attack icon
		game.getBatch().draw(
				RuntimeData.getInstance().getAsset(RuntimeData.getInstance().getCharacter().getAttackIcon()),
				attackDataPos[0]
						+ RuntimeData.getInstance().getAsset("textures/menus/frames/AbilityFrame.png").getWidth() / 2
						- RuntimeData.getInstance().getAsset(RuntimeData.getInstance().getCharacter().getAttackIcon())
								.getWidth() / 2,
				attackDataPos[1]
						+ RuntimeData.getInstance().getAsset("textures/menus/frames/AbilityFrame.png").getHeight() / 2
						- RuntimeData.getInstance().getAsset(RuntimeData.getInstance().getCharacter().getAttackIcon())
								.getHeight() / 2);
		//Draw hp text
		hp.draw(game.getBatch());
		game.getBatch().end();
	}
	
	@Override
	public void manageControls(float stateTime) {
	}

	@Override
	public void update(float stateTime) {
		//Update scroll position
		dataScrollPos[0] = (int) (RuntimeData.getInstance().getCharacter().getPos()[0]
				- RuntimeData.getInstance().getAsset("textures/menus/scrolls/HorizontalScroll_Minimum.png").getWidth()
						/ 2);
		dataScrollPos[1] = (int) (RuntimeData.getInstance().getCharacter().getPos()[1] - 525);
		//Update hp text position
		hp.setText("HP: " + Integer.toString(RuntimeData.getInstance().getCharacter().getHealth()));
		hp.setMidOfBounds(dataScrollPos,
				new int[] {
						RuntimeData.getInstance().getAsset("textures/menus/scrolls/HorizontalScroll_Minimum.png")
								.getWidth(),
						RuntimeData.getInstance().getAsset("textures/menus/scrolls/HorizontalScroll_Minimum.png")
								.getHeight() });
		hp.getPos()[0] += RuntimeData.getInstance().getAsset("textures/menus/scrolls/HorizontalScroll_Minimum.png")
				.getWidth() / 4;
		//Update frame position
		hpDataPos[0] = hp.getPos()[0]
				- RuntimeData.getInstance().getAsset("textures/menus/frames/HealthFrame.png").getWidth() / 7;
		hpDataPos[1] = dataScrollPos[1] + 55;
		abilityDataPos[0] = dataScrollPos[0] + 100;
		abilityDataPos[1] = dataScrollPos[1] + 55;
		attackDataPos[0] = dataScrollPos[0] + 115
				+ RuntimeData.getInstance().getAsset("textures/menus/frames/AbilityFrame.png").getWidth();
		attackDataPos[1] = dataScrollPos[1] + 55;
		
		if(RuntimeData.getInstance().getCharacter().getAttackDetails(stateTime)[0]<=0){
			attackData = 59;
		}else{
			attackData =50-(int) ((RuntimeData.getInstance().getCharacter().getAttackDetails(stateTime)[0]/RuntimeData.getInstance().getCharacter().getAttackDetails(stateTime)[1])*50);
		}
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
}
