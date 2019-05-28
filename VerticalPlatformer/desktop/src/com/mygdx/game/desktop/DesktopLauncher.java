package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Splash;
import com.mygdx.game.VerticalPlatformer;

import java.awt.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 600;
		config.width = 1000;
		config.x = (dimension.width-config.width)/2;
		config.y = (dimension.height-config.height)/2;
		new LwjglApplication(new VerticalPlatformer(), config);
	}
}
