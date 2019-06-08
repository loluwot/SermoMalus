package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
public class Fade {
    float alpha;
    public Fade(float a){
        alpha = a;
    }
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
