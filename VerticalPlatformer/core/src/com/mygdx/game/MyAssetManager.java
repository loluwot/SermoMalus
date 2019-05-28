package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class MyAssetManager {

    public final AssetManager manager = new AssetManager();

    // Textures
    public final String imagesPack 	= "button-packed/pack.atlas";

    public void loadImages(){
        manager.load(imagesPack, TextureAtlas.class);
    }
}
