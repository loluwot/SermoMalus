package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Andy Cai
 * @version 1.0
 *
 * This class is to find the path of the ellipse in the splash screen
 */
public class EllipsePath {
    public float angle;
    public float centerX;
    public float centerY;
    public float slant;
    public float alpha;
    public float radiusX;
    public float radiusY;
    public Sprite image;

    /**
     * This is the constructor for the EllipsePath class. It assigns values to variables.
     * @param angle     the angle of the path
     * @param centerX   the x position of the center of the ellipse path
     * @param centerY   the y position of the center of the ellipse path
     * @param slant     the slant of the ellipse path
     * @param visible   if the path is visible
     * @param image     the sprite image that is to be animated
     * @param radiusX   the x-radius of the ellipse
     * @param radiusY   the y-radius of the ellipse
     */
    public EllipsePath(float angle, float centerX, float centerY, float slant, float visible, Sprite image, float radiusX, float radiusY){
        this.angle = angle;
        this.centerX = centerX;
        this.centerY = centerY;
        this.slant = slant;
        this.alpha = visible;
        this.image = image;
        this.radiusX = radiusX;
        this.radiusY = radiusY;
    }

    /**
     * This method draw the image batch onto x and y values that have been calculated. It will then draw them on their path.
     * @param batch SpriteBatch
     */
    public void draw(SpriteBatch batch){
        float x = (float) (radiusX * Math.cos(angle) * Math.cos(slant) - radiusY * Math.sin(angle) * Math.sin(slant) + centerX);
        float y = (float) (radiusX * Math.cos(angle) * Math.sin(slant) + radiusY * Math.sin(angle) * Math.cos(slant) + centerY);
        image.setPosition(x, y);
        image.setColor(image.getColor().r, image.getColor().g, image.getColor().b, alpha);
        image.draw(batch);
    }
}
