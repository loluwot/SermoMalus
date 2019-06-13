package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends Entity {

    public static final int SPEED = 80;
    public static final int JUMPSPEED = 5;
    public static final float MOVEPOWER = 2;
    public static final float JUMPPOWER = 13;
    public final float MAXIMUMVELOCITY = 9;
    private float currentMovePower = 1;
    private float currentMaxSpeed = 6;
    private float currentJumpPower = 9;
    Texture image;

    public Player (float x, float y, GameMap map, Body body) {
        super (x,y, EntityType.PLAYER,map, body);
        image = new Texture ("playerGOOD.png");
    }
    public float getCurrentMaxSpeed(){
        return currentMaxSpeed;
    }
    public void setCurrentMaxSpeed(float m){
        currentMaxSpeed = m;
    }
    public float getCurrentJumpPower(){
       return currentJumpPower;
    }
    public void setCurrentJumpPower(float j){
        currentJumpPower = j;
    }
    @Override
    public void update(float deltaTime, World world) {
        if (Gdx.input.isKeyPressed(Input.Keys.W)&&grounded)
            this.body.applyLinearImpulse(0, currentJumpPower, this.body.getPosition().x, this.body.getPosition().y, true);
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if(grounded) {
                if (body.getLinearVelocity().x >= -currentMaxSpeed) {
                    this.body.applyLinearImpulse(-currentMovePower, 0, body.getPosition().x, body.getPosition().y, true);
                }
            }
            else{
                if (body.getLinearVelocity().x >= -(currentMaxSpeed/2)) {
                    this.body.applyLinearImpulse(-currentMovePower, 0, body.getPosition().x, body.getPosition().y, true);
                }
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            if(grounded) {
                if (body.getLinearVelocity().x <= currentMaxSpeed) {
                    this.body.applyLinearImpulse(currentMovePower, 0, body.getPosition().x, body.getPosition().y, true);
                }
            }
            else{
                if (body.getLinearVelocity().x <= currentMaxSpeed/2) {
                    this.body.applyLinearImpulse(currentMovePower, 0, body.getPosition().x, body.getPosition().y, true);
                }
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
            this.setCurrentJumpPower(Math.min(this.getCurrentJumpPower() + 0.1f, this.JUMPPOWER));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)){
            this.setCurrentJumpPower(Math.max(this.getCurrentJumpPower()-0.1f, 0));
        }


    }


    @Override
    public void render(SpriteBatch batch) {

        batch.draw(image, pos.x-getWidth()/2, pos.y-getHeight()/2, getWidth(), getHeight());

    }
}
