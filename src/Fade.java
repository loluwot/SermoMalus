package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * @author Andy Cai
 * @version 1.0
 *
 * This class lets the fade feature occur which will make the screen turn black
 */
public class Fade {
    float alpha;

    /**
     * This is the constructor of the Fade class and will assign the value to the variable
     * @param a     float, the current alpha value
     */
    public Fade(float a){
        alpha = a;
    }

    /**
     * This method will make the color of the window go from its current brightness to black
     * @param rend
     */
    public void draw(ShapeRenderer rend){
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        rend.begin(ShapeRenderer.ShapeType.Filled);
        rend.setColor(0,0,0,alpha);
        rend.rect(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        rend.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
