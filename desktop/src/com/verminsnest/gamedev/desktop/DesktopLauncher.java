package com.verminsnest.gamedev.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.verminsnest.core.VerminsNest;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Vermins Nest";
		config.addIcon("textures/general/VerminsIcon32x32.png", Files.FileType.Internal);
		config.addIcon("textures/general/VerminsIcon16x16.png", Files.FileType.Internal);
		config.width = 1920;
		config.height = 1055;
		config.x = 0;
		config.y = 0;
		config.resizable = false;
		config.vSyncEnabled = false;
		new LwjglApplication(new VerminsNest(), config);
	}
}
