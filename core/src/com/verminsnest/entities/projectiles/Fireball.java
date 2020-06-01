package com.verminsnest.entities.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.core.Indentifiers;
import com.verminsnest.core.singletons.RuntimeData;

public class Fireball extends Projectile{

	public static String iconPath = "textures/projectiles/fireball/FireBall-Icon.png";
	
	public Fireball(int direction, int agility, int damage, int[] position, float stateTime) {
		super(Indentifiers.ASSETMANAGER_FIREBALL,direction, agility, damage, position, stateTime);
	}

	@Override
	public void init() {
		int agility = speed;
		if(agility <11){
			agility = 11;
		}
		//Textures
		Texture flyingSheet = RuntimeData.getInstance().getAsset("textures/projectiles/fireball/FireBall-Flying.png");
		Texture hitSheet = RuntimeData.getInstance().getAsset("textures/projectiles/fireball/FireBall-Hit.png");
		Texture castSheet = RuntimeData.getInstance().getAsset("textures/projectiles/fireball/FireBall-Cast.png");
		TextureRegion[][] temp = TextureRegion.split(flyingSheet, flyingSheet.getHeight(), flyingSheet.getHeight());
		TextureRegion[] frames = new TextureRegion[temp[0].length];
		
		for(int i = 0; i< temp[0].length; i++){
			frames[i] = temp[0][i];
		}
		flyingAni = new Animation<TextureRegion>(0.85f/agility,frames);
		
		temp = TextureRegion.split(hitSheet, hitSheet.getHeight(), hitSheet.getHeight());
		frames = new TextureRegion[temp[0].length];
		
		for(int i = 0; i< temp[0].length; i++){
			frames[i] = temp[0][i];
		}
		hitAni = new Animation<TextureRegion>((float) ((agility*1f-1)/agility*1f-0.9f),frames);
		
		temp = TextureRegion.split(castSheet, castSheet.getHeight(), castSheet.getHeight());
		frames = new TextureRegion[temp[0].length];
		
		for(int i = 0; i< temp[0].length; i++){
			frames[i] = temp[0][i];
		}
		castAni = new Animation<TextureRegion>(0.75f/agility,frames);
	}

	
}
