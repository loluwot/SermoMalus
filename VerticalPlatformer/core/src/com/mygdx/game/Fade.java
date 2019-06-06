package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
public class Fade {
    float alpha;
    public Fade(float a){
        alpha = a;
    }
    public void draw(ShapeRenderer rend){
        rend.begin();
        rend.setColor(1,1,1,alpha);
        rend.rect(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        rend.end();
    }
}
