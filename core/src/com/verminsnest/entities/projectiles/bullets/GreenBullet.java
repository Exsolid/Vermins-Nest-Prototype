package com.verminsnest.entities.projectiles.bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.core.management.ids.Indentifiers;
import com.verminsnest.entities.projectiles.Projectile;

public class GreenBullet extends Projectile{
	public GreenBullet(int direction, int agility, int damage, int[] position) {
		super(Indentifiers.ASSETMANAGER_BULLET_GREEN, direction, agility, damage, position);
	}
	
	@Override
	public void init() {
		Texture flyingSheet = RuntimeData.getInstance().getTexture("textures/projectiles/bullets/BulletGreen-Flying.png");
		Texture hitSheet = RuntimeData.getInstance().getTexture("textures/projectiles/bullets/BulletGreen-Hit.png");
		shadow = RuntimeData.getInstance().getTexture("textures/shadows/Shadow-XS.png");
		yShadowOffset = -20;
		setSize(flyingSheet.getWidth(),flyingSheet.getHeight());
		
		TextureRegion[][] temp = TextureRegion.split(flyingSheet, flyingSheet.getWidth(), flyingSheet.getHeight());
		TextureRegion[] frames = new TextureRegion[temp[0].length];
		
		for(int i = 0; i< temp[0].length; i++){
			frames[i] = temp[0][i];
		}
		flyingAni = new Animation<TextureRegion>(1f,frames);
		temp = TextureRegion.split(hitSheet, hitSheet.getWidth(), hitSheet.getHeight());
		frames = new TextureRegion[temp[0].length];
		
		for(int i = 0; i< temp[0].length; i++){
			frames[i] = temp[0][i];
		}
		hitAni = new Animation<TextureRegion>(0.1f,frames);
		RuntimeData.getInstance().getAudioManager().playSoundEffect("audio/sounds/projectiles/Bullet-Shooting.mp3");
	}
	
	@Override
	public void setCurrentAni(int aniKey){
		if(aniKey == Indentifiers.STATE_CAST) {
			super.setCurrentAni(Indentifiers.STATE_FLYING);
		}else {
			super.setCurrentAni(aniKey);
		}
	}

}
