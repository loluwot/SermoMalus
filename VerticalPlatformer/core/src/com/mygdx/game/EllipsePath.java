package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EllipsePath {
    public float angle;
    public float centerX;
    public float centerY;
    public float slant;
    public float alpha;
    public float radiusX;
    public float radiusY;
    public Sprite image;
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

    public void draw(SpriteBatch batch){
        float x = (float) (radiusX * Math.cos(angle) * Math.cos(slant) - radiusY * Math.sin(angle) * Math.sin(slant) + centerX);
        float y = (float) (radiusX * Math.cos(angle) * Math.sin(slant) + radiusY * Math.sin(angle) * Math.cos(slant) + centerY);
        image.setPosition(x, y);
        image.setColor(image.getColor().r, image.getColor().g, image.getColor().b, alpha);
        image.draw(batch);
    }
}
