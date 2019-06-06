package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Entity {

    protected Vector2 pos;
    protected EntityType type;
    protected float velocityY = 0;
    protected QuizMap map;
    protected Body body;
    protected boolean grounded = true;


    public Entity(float x, float y, EntityType type, QuizMap map, Body body) {
        this.pos = new Vector2 (x,y);
        this.type = type;
        this.map = map;
        this.body = body;

    }


    public void superUpdate(float deltaTime, World world) {
        float newY = body.getPosition().y;
        if (map.hitBoxCollide(pos.x, newY, getWidth(), getHeight())) {
            grounded = true;
        } else {
            grounded = false;
        }
        this.pos.x = body.getPosition().x;
        this.pos.y = body.getPosition().y;
        this.velocityY = body.getLinearVelocity().y;

    }
    public void update(float deltaTime, World world){

    }


    public abstract void render(SpriteBatch batch);


    public Vector2 getPos() {
        return pos;
    }

    public EntityType getType() {
        return type;
    }

    public float getX () {
        return pos.x;
    }

    public float getY () {
        return pos.y;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public float getWidth () {
        return type.getWidth();
    }

    public float getHeight () {
        return type.getHeight();
    }


}
