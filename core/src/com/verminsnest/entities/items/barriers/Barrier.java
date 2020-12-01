package com.verminsnest.entities.items.barriers;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.core.management.ids.Indentifiers;
import com.verminsnest.core.management.ids.Qualifier;
import com.verminsnest.entities.items.Item;

public abstract class Barrier extends Item {
	protected int activationHP;
	protected Animation<TextureRegion> castAni;
	protected Animation<TextureRegion> idleAni;
	protected Animation<TextureRegion> breakAni;
	
	public Barrier(int textureID, boolean isPassiv) {
		super(null, textureID, isPassiv,Qualifier.RENDER_LAYER_MID);
	}

	public void setCurrentAni(int animationKey) {
		if(keeper != null){
			switch (animationKey){
			case Indentifiers.STATE_CAST:
				currentAni = castAni;
				state = Indentifiers.STATE_CAST;
				break;
			case Indentifiers.STATE_BREAK:
				currentAni = breakAni;
				state = Indentifiers.STATE_BREAK;
				break;
			case Indentifiers.STATE_IDLE:
				currentAni = idleAni;
				state = Indentifiers.STATE_IDLE;
				break;
			}
		}else{
			currentAni = itemBagAni;
			state = Indentifiers.STATE_IDLE;
		}
	}
	
	@Override
	public TextureRegion getCurrentFrame(float delta) {
		switch (state){
		case Indentifiers.STATE_CAST:
		case Indentifiers.STATE_BREAK:
			return currentAni.getKeyFrame(internalStateTime, false);
		case Indentifiers.STATE_IDLE:
			return currentAni.getKeyFrame(internalStateTime, true);
		}
		return null;
	}
}
