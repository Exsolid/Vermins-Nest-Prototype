package com.verminsnest.screens.gameplay.menus;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.verminsnest.core.VerminsNest;
import com.verminsnest.core.singletons.RuntimeData;
import com.verminsnest.misc.gui.ButtonManager;
import com.verminsnest.misc.gui.FontText;
import com.verminsnest.screens.gameplay.GameManager;
import com.verminsnest.screens.gameplay.GameplayOverlay;

public class LevelMenu extends GameplayOverlay {
	
	private FontText title;
	private FontText points;
	
	private ButtonManager levelType;
	
	private final int HEALTH = 0;
	private final int STRENGTH = 1;
	private final int SPEED = 2;
	private final int AGILITY = 3;
	private final int BACK = 4;
	
	private int skillPoints;
	private int usedPointsHealth;
	private int usedPointsStrength;
	private int usedPointsAgility;
	private int usedPointsSpeed;
	
	public LevelMenu(VerminsNest game, GameManager gameMan) {
		super(game, gameMan);
	}

	public void init(){
		skillPoints = RuntimeData.getInstance().getCharacter().getSkillPoints();
		usedPointsHealth = 0;
		usedPointsStrength = 0;
		usedPointsAgility = 0;
		usedPointsSpeed = 0;
		
		
		ArrayList<String> health = new ArrayList<>();
		health.add("Gameplay_LevelType_Health");
		health.add(RuntimeData.getInstance().getCharacter().getMaxHealth()+"");
		
		ArrayList<String> strength = new ArrayList<>();
		strength.add("Gameplay_LevelType_Strength");
		strength.add(RuntimeData.getInstance().getCharacter().getStrength()+"");
		
		ArrayList<String> agility = new ArrayList<>();
		agility.add("Gameplay_LevelType_Agility");
		agility.add(RuntimeData.getInstance().getCharacter().getAgility()+"");
		
		ArrayList<String> speed = new ArrayList<>();
		speed.add("Gameplay_LevelType_Speed");
		speed.add(RuntimeData.getInstance().getCharacter().getSpeed()+"");
		
		ArrayList<String> back = new ArrayList<>();
		back.add("SettingsMenu_Back");
		
		ArrayList<ArrayList<String>> buttonList = new ArrayList<>();
		buttonList.add(health);
		buttonList.add(strength);
		buttonList.add(speed);
		buttonList.add(agility);
		buttonList.add(back);
		
		levelType = new ButtonManager(buttonList, 80, false, "-","+", false);
		title = new FontText(game.getConfig().getMessage("Gameplay_LevelMenu_Title"),100, false);
		points = new FontText(game.getConfig().getMessage("Gameplay_LevelMenu_Points")+": "+Integer.toString(skillPoints),80, false);
		
		points
		.setMidOfBounds(
				new int[] {
						(int) (game.getCamera().position.x - RuntimeData.getInstance()
								.getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getWidth() / 2),
						(int) (game.getCamera().position.y - RuntimeData.getInstance()
								.getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getHeight() / 2) },
				new int[] {
						RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png")
								.getWidth(),
						RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png")
								.getHeight() });
		
		title
		.setMidOfBounds(
				new int[] {
						(int) (game.getCamera().position.x - RuntimeData.getInstance()
								.getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getWidth() / 2),
						(int) (game.getCamera().position.y - RuntimeData.getInstance()
								.getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getHeight() / 2) },
				new int[] {
						RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png")
								.getWidth(),
						RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png")
								.getHeight() });
		
		levelType
				.setMidOfBounds(
						new int[] {
								RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png")
										.getWidth(),
								RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png")
										.getHeight() },
						new int[] {
								(int) (game.getCamera().position.x - RuntimeData.getInstance()
										.getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getWidth() / 2),
								(int) (game.getCamera().position.y - RuntimeData.getInstance()
										.getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getHeight() / 2) });
		
		title.getPos()[1] += 325;
		points.getPos()[1] += 225;
	}
	
	@Override
	public void render(float stateTime) {
		game.getBatch()
				.draw(RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png"),
						game.getCamera().position.x - RuntimeData.getInstance()
								.getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getWidth() / 2,
						game.getCamera().position.y - RuntimeData.getInstance()
								.getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getHeight() / 2);
		levelType.draw(game.getBatch());
		title.draw(game.getBatch());
		points.draw(game.getBatch());
	}

	@Override
	public void manageControls(float stateTime) {
		// Unpause
		if ((Gdx.input.isKeyPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyPressed(Input.Keys.I))
				&& !gameMan.isControlBlocked()) {
			gameMan.setState(GameManager.RUNNING);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.S) && !gameMan.isControlBlocked()){
			levelType.next();
			gameMan.resetBlocked();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W) && !gameMan.isControlBlocked()){
			levelType.prev();
			gameMan.resetBlocked();
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.D) && !gameMan.isControlBlocked()){
			switch(levelType.getIndex()){
			case HEALTH:
				if(skillPoints != 0){
					levelType.getCurrent().getOption().setText(Integer.toString(Integer.parseInt(levelType.getCurrent().getOption().getText())+10));
					levelType.getCurrent().getRightArrow().blink();
					skillPoints--;
					usedPointsHealth++;
				}
				break;
			case SPEED:
				if(skillPoints != 0){
					levelType.getCurrent().getOption().setText(Integer.toString(Integer.parseInt(levelType.getCurrent().getOption().getText())+1));
					levelType.getCurrent().getRightArrow().blink();
					skillPoints--;
					usedPointsSpeed++;
				}
				break;
			case AGILITY:
				if(skillPoints != 0){
					levelType.getCurrent().getOption().setText(Integer.toString(Integer.parseInt(levelType.getCurrent().getOption().getText())+1));
					levelType.getCurrent().getRightArrow().blink();
					skillPoints--;
					usedPointsAgility++;
				}
				break;
			case STRENGTH:
				if(skillPoints != 0){
					levelType.getCurrent().getOption().setText(Integer.toString(Integer.parseInt(levelType.getCurrent().getOption().getText())+1));
					levelType.getCurrent().getRightArrow().blink();
					skillPoints--;
					usedPointsStrength++;
				}
				break;
			case BACK:
				break;
			}
			gameMan.resetBlocked();
			updateTextPos();
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.A) && !gameMan.isControlBlocked()){
			switch(levelType.getIndex()){
			case HEALTH:
				if(usedPointsHealth != 0){
					levelType.getCurrent().getOption().setText(Integer.toString(Integer.parseInt(levelType.getCurrent().getOption().getText())-10));
					levelType.getCurrent().getLeftArrow().blink();
					usedPointsHealth--;
					skillPoints++;
				}
				break;
			case SPEED:
				if(usedPointsSpeed != 0){
					levelType.getCurrent().getOption().setText(Integer.toString(Integer.parseInt(levelType.getCurrent().getOption().getText())-1));
					levelType.getCurrent().getLeftArrow().blink();
					usedPointsSpeed--;
					skillPoints++;
				}
				break;
			case AGILITY:
				if(usedPointsAgility != 0){
					levelType.getCurrent().getOption().setText(Integer.toString(Integer.parseInt(levelType.getCurrent().getOption().getText())-1));
					levelType.getCurrent().getLeftArrow().blink();
					usedPointsAgility--;
					skillPoints++;
				}
				break;
			case STRENGTH:
				if(usedPointsStrength != 0){
					levelType.getCurrent().getOption().setText(Integer.toString(Integer.parseInt(levelType.getCurrent().getOption().getText())-1));
					levelType.getCurrent().getLeftArrow().blink();
					usedPointsStrength--;
					skillPoints++;
				}
				break;
			case BACK:
				break;
			}
			gameMan.resetBlocked();
			updateTextPos();
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.ENTER) && !gameMan.isControlBlocked()){
			if(levelType.getIndex() == BACK){
				RuntimeData.getInstance().getCharacter().setMaxHealth(Integer.parseInt(levelType.getButtons().get(HEALTH).getOption().getText()));
				RuntimeData.getInstance().getCharacter().setAgility(Integer.parseInt(levelType.getButtons().get(AGILITY).getOption().getText()));
				RuntimeData.getInstance().getCharacter().setSpeed(Integer.parseInt(levelType.getButtons().get(SPEED).getOption().getText()));
				RuntimeData.getInstance().getCharacter().setStrength(Integer.parseInt(levelType.getButtons().get(STRENGTH).getOption().getText()));
				RuntimeData.getInstance().getCharacter().setSkilPoints(skillPoints);
				gameMan.setState(GameManager.RUNNING);
			}
		}
	}

	private void updateTextPos(){
		points.setText(game.getConfig().getMessage("Gameplay_LevelMenu_Points")+": "+Integer.toString(skillPoints));
		levelType
		.setMidOfBounds(
				new int[] {
						RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png")
								.getWidth(),
						RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png")
								.getHeight() },
				new int[] {
						(int) (game.getCamera().position.x - RuntimeData.getInstance()
								.getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getWidth() / 2),
						(int) (game.getCamera().position.y - RuntimeData.getInstance()
								.getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getHeight() / 2) });
		points
		.setMidOfBounds(
				new int[] {
						(int) (game.getCamera().position.x - RuntimeData.getInstance()
								.getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getWidth() / 2),
						(int) (game.getCamera().position.y - RuntimeData.getInstance()
								.getAsset("textures/menus/scrolls/VerticalScroll_Big.png").getHeight() / 2) },
				new int[] {
						RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png")
								.getWidth(),
						RuntimeData.getInstance().getAsset("textures/menus/scrolls/VerticalScroll_Big.png")
								.getHeight() });
		points.getPos()[1] += 225;
	}
	
	@Override
	public void update(float stateTime) {
		manageControls(stateTime);

	}

	@Override
	public void dispose() {
		levelType.dispose();
	}

}
