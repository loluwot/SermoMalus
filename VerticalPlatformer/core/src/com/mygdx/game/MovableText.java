package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;

public class MovableText {
    public float x;
    public float y;
    public BitmapFont font;
    public float px;
    public String file;
    public float alpha;
    public int numPeriods;
    public MovableText(float x, float y, float size, String file, float alpha){
        this.x = x;
        this.y = y;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(file));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 100;
        px = size;
        this.file = file;
        font = generator.generateFont(parameter);
        font.setColor(font.getColor().r, font.getColor().g, font.getColor().b, alpha);
        font.getData().setScale(size);
        this.alpha = alpha;
        numPeriods = 0;
    }

    public float getSize(){
        return px;
    }
    public void setSize(float size){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(file));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 100;
        font = generator.generateFont(parameter);
        font.getData().setScale(size);
        px = size;
    }
    public float getAlpha(){
        return alpha;
    }
    public void setAlpha(float newAlpha){
        font.setColor(font.getColor().r, font.getColor().g, font.getColor().b, newAlpha);
        alpha = newAlpha;
    }
    public void draw(String text, SpriteBatch batch, boolean wrap){
        for (int i = 0; i < numPeriods % 3; i++){
            text += ".";
        }
        font.draw(batch, text, x, y, Gdx.graphics.getWidth(), Align.center, wrap);
    }
}
