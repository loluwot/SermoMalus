package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends Entity {

    private static final int SPEED = 80;
    private static final int JUMPSPEED = 5;
    private static final int MOVEPOWER = 1;
    private static final float JUMPPOWER = 6;
    Texture image;

    public Player (float x, float y, GameMap map, Body body) {
        super (x,y, EntityType.PLAYER,map, body);
        image = new Texture ("player.PNG");
    }

    @Override
    public void update(float deltaTime, World world) {
        if (Gdx.input.isKeyPressed(Input.Keys.W)&&grounded)
            this.body.applyLinearImpulse(0, JUMPPOWER, this.body.getPosition().x, this.body.getPosition().y, true);
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if(grounded) {
                if (body.getLinearVelocity().x >= -MAXIMUMVELOCITY) {
                    this.body.applyLinearImpulse(-MOVEPOWER, 0, body.getPosition().x, body.getPosition().y, true);
                }
            }
            else{
                if (body.getLinearVelocity().x >= -(MAXIMUMVELOCITY/2)) {
                    this.body.applyLinearImpulse(-MOVEPOWER, 0, body.getPosition().x, body.getPosition().y, true);
                }
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            if(grounded) {
                if (body.getLinearVelocity().x <= MAXIMUMVELOCITY) {
                    this.body.applyLinearImpulse(MOVEPOWER, 0, body.getPosition().x, body.getPosition().y, true);
                }
            }
            else{
                if (body.getLinearVelocity().x <= MAXIMUMVELOCITY/2) {
                    this.body.applyLinearImpulse(MOVEPOWER, 0, body.getPosition().x, body.getPosition().y, true);
                }
            }
        }
        super.update(deltaTime, world);
        this.pos.x = body.getPosition().x;
        this.pos.y = body.getPosition().y;
        this.velocityY = body.getLinearVelocity().y;

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x-getWidth()/2, pos.y-getHeight()/2, getWidth(), getHeight());
    }
}
