package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player extends Entity {

    private static final int SPEED = 80;
    private static final int JUMPSPEED = 5;

    Texture image;

    public Player (float x, float y, GameMap map) {
        super (x,y, EntityType.PLAYER,map);
        image = new Texture ("player.PNG");
    }

    @Override
    public void update(float deltaTime, float gravity) {
        if (Gdx.input.isKeyPressed(Input.Keys.W) && grounded)
            this.velocityY += JUMPSPEED * getWeight();
        else if (Gdx.input.isKeyPressed(Input.Keys.W )&& !grounded && this.velocityY > 0)
            this.velocityY += JUMPSPEED * getWeight() * deltaTime;

        super.update(deltaTime, gravity);//Apply gravity

        if (Gdx.input.isKeyPressed(Input.Keys.A))
            moveX(-SPEED * deltaTime);

        if (Gdx.input.isKeyPressed(Input.Keys.D))
            moveX(SPEED * deltaTime);
//        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//            this.velocityY = -JUMPSPEED * deltaTime;
//        }



    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
    }
}
