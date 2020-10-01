package com.verminsnest.entities.particles;

import com.verminsnest.core.management.Indentifiers;
import com.verminsnest.core.management.data.RuntimeData;

public class Gore extends Particle {
	
	public Gore(int[] pos) {
		super(pos, Indentifiers.ASSETMANAGER_GORE);
	}

	@Override
	public void init() {
		textureNum = rand.nextInt(5)+1;
		switch(textureNum){
		case 1:
			texture = RuntimeData.getInstance().getTexture("textures/particles/gore/Gore-1.png");
			break;
		case 2:
			texture = RuntimeData.getInstance().getTexture("textures/particles/gore/Gore-2.png");
			break;
		case 3:
			texture = RuntimeData.getInstance().getTexture("textures/particles/gore/Gore-3.png");
			break;
		case 4:
			texture = RuntimeData.getInstance().getTexture("textures/particles/gore/Gore-4.png");
			break;
		case 5:
			texture = RuntimeData.getInstance().getTexture("textures/particles/gore/Gore-5.png");
			break;
		}
		this.setSize(RuntimeData.getInstance().getTexture("textures/particles/gore/Gore-"+textureNum+".png").getWidth(), RuntimeData.getInstance().getTexture("textures/particles/gore/Gore-"+textureNum+".png").getHeight());
	}
}
