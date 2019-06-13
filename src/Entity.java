package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author Eric Shi
 * @version 1.0
 *
 * @author Andy Cai
 * @version 1.1
 *
 * This abstract class serves as a template for all entities in the game, player included.
 */
public abstract class Entity {

    protected Vector2 pos;
    protected EntityType type;
    protected float velocityY = 0;
    protected GameMap map;
    protected Body body;
    protected boolean grounded = true;


    /**
     * This is the constructor of the Entity abstract class and assigns values to
     * variables and also initiates and assigns values to a Vector2 Object.
     * @param x     x position
     * @param y     y position
     * @param type  type fo entity
     * @param map   which map its in
     * @param body  body object to give it collision
     */
    public Entity(float x, float y, EntityType type, GameMap map, Body body) {
        this.pos = new Vector2 (x,y);
        this.type = type;
        this.map = map;
        this.body = body;

    }

    /**
     * This method is used to apply gravity to entities
     * @param deltaTime     time spent interacting
     * @param world         which world it is effecting
     */
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

    /**
     * update method placeholder as an abstract method. This method will draw in the entity.
     * @param deltaTime     time in world
     * @param world         which world it is in
     */
    public abstract void update(float deltaTime, World world);


    /**
     * This is a place holder method that will draw in the images
     * @param batch SpriteBatch to allow for images
     */
    public abstract void render(SpriteBatch batch);

    /**
     * get method to get the x location
     * @return  vector2 object 'x' variable
     */
    public float getX () {
        return pos.x;
    }

    /**
     * get method to get the y location
     * @return vector2 object 'y' variable
     */
    public float getY () {
        return pos.y;
    }

    /**
     * get method to get the width of the entity type
     * @return returns the width of the entity type in float form
     */
    public float getWidth () {
        return type.getWidth();
    }

    /**
     * get method to get the height of the entity type
     * @return returns the height of the entity type in float form
     */
    public float getHeight () {
        return type.getHeight();
    }


}
