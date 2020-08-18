package com.verminsnest.entities.items.turrets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.verminsnest.core.management.Indentifiers;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.entities.items.Item;

public abstract class Turret extends Item {

	protected Animation<TextureRegion> idleAni;
	protected Animation<TextureRegion> attackWestAni;
	protected Animation<TextureRegion> attackEastAni;
	protected Animation<TextureRegion> attackNorthAni;
	protected Animation<TextureRegion> attackSouthAni;
	
	protected float attackCooldown;
	public Turret(int textureID, boolean isPassiv) {
		super(null, textureID, isPassiv);
		attackCooldown= 0;
	}

	//TODO change/look at item structure
	@Override
	public void activate() {
		runtime = 10f;
		isGrounded = true;
		setCurrentAni(Indentifiers.STATE_IDLE);
		RuntimeData.getInstance().getEntityManager().placeOnTile(keeper.getMapPos().get(Indentifiers.DIRECTION_NORTH), keeper.getPos(), this);
		isObstacle = true;
		switchShadow();
	}

	@Override
	public void setCurrentAni(int animationKey) {
		if(keeper != null){
			switch(animationKey) {
			case Indentifiers.STATE_IDLE:
				currentAni = idleAni;
				state =Indentifiers.STATE_IDLE;
				break;
			case Indentifiers.STATE_ATTACK_EAST:
				currentAni = attackEastAni;
				state =Indentifiers.STATE_ATTACK_EAST;
				break;
			case Indentifiers.STATE_ATTACK_WEST:
				currentAni = attackWestAni;
				state =Indentifiers.STATE_ATTACK_WEST;
				break;
			case Indentifiers.STATE_ATTACK_NORTH:
				currentAni = attackNorthAni;
				state =Indentifiers.STATE_ATTACK_NORTH;
				break;
			case Indentifiers.STATE_ATTACK_SOUTH:
				currentAni = attackSouthAni;
				state =Indentifiers.STATE_ATTACK_SOUTH;
				break;
			}
		}else {
			currentAni = itemBagAni;
			state = Indentifiers.STATE_IDLE;
		}
		updateSize();
		internalStateTime = 0;
	}

	public TextureRegion getCurrentFrame(float delta) {
		if(state == Indentifiers.STATE_IDLE)
			return currentAni.getKeyFrame(internalStateTime, true);
		else
			return currentAni.getKeyFrame(internalStateTime, false);
	}
	
	@Override
	public void update(float delta) {
		internalStateTime += delta;
		runtime -= delta;
		attackCooldown -= delta;
		if(runtime < 0 && keeper != null) {
			pos = null;
			isGrounded = false;
			isObstacle = false;
			switchShadow();
		}else if (keeper != null){
			if(state != Indentifiers.STATE_IDLE && currentAni.isAnimationFinished(internalStateTime))setCurrentAni(Indentifiers.STATE_IDLE);
			doStuff();
		}
	}
	
	public abstract void doStuff();

}
