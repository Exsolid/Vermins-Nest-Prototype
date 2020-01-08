package com.verminsnest.gamedev.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.verminsnest.gamedev.VerminsNest;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Vermins Next";
		config.addIcon("textures/general/VerminsIcon32x32.png", Files.FileType.Internal);
		config.addIcon("textures/general/VerminsIcon16x16.png", Files.FileType.Internal);
		config.width = 1920;
		config.height = 1080;
		config.resizable = false;
		System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
		new LwjglApplication(new VerminsNest(), config);
	}
}
