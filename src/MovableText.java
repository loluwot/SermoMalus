package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;

/**
 * @author Andy Cai
 * @version 1.0
 *
 * <p>This class will create moveable text for the splash screen</p>
 */
public class MovableText {
    public float x;
    public float y;
    public BitmapFont font;
    public float px;
    public String file;
    public float alpha;
    public int numPeriods;

    /**
     * This is the constructor for the Moveable Text class which will assign values to variables as well as create objects used in the class
     * @param x     float x for position
     * @param y     float y for position
     * @param size  float size for size
     * @param file  string file for the file generator
     * @param alpha float alpha value for brightness
     */
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

    /**
     *  This will return the size in pixels
     * @return float pixel size
     */
    public float getSize(){
        return px;
    }

    /**
     *  This will set the size
     * @param size  size of pixels
     */
    public void setSize(float size){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(file));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 100;
        font = generator.generateFont(parameter);
        font.getData().setScale(size);
        px = size;
    }

    /**
     *this will return the alpha value
     * @return  float alpha value
     */
    public float getAlpha(){
        return alpha;
    }

    /**
     *This will set the alpha value
     * @param newAlpha  float new alpha value
     */
    public void setAlpha(float newAlpha){
        font.setColor(font.getColor().r, font.getColor().g, font.getColor().b, newAlpha);
        alpha = newAlpha;
    }

    /**
     *This will draw the moveable text
     * @param text  String Text that will be displayed
     * @param batch SpriteBatch batch that will be used to draw
     * @param wrap  boolean Wrap to indicate whether the text is wrapped or not
     */
    public void draw(String text, SpriteBatch batch, boolean wrap){
        for (int i = 0; i < numPeriods % 3; i++){
            text += ".";
        }
        font.draw(batch, text, x, y, Gdx.graphics.getWidth(), Align.center, wrap);
    }
}
