package com.verminsnest.entities.particles;

import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.core.management.ids.Indentifiers;

public class Shell extends Particle {

	public Shell(int[] pos) {
		super(pos, Indentifiers.ASSETMANAGER_SHELL);
	}

	@Override
	public void init() {
		texture = RuntimeData.getInstance().getTexture("textures/particles/shells/Shell.png");
		setSize(texture.getWidth(),texture.getHeight());
	}

}
