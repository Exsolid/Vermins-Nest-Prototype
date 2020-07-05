package com.verminsnest.gamedev.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.verminsnest.core.VerminsNest;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Vermins Nest");
		config.setWindowIcon(Files.FileType.Internal,"textures/general/VerminsIcon32x32.png");
		config.setResizable(false);
		config.useVsync(true);
		new Lwjgl3Application(new VerminsNest(), config);
	}
}
