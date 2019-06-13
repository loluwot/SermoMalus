package com.mygdx.game;

/**
 * @author Andy Cai
 * @version  1.0
 *
 * This enum contains several enum objects which each contain specific important properties.
 */
public enum EntityType {
    PLAYER("player", 14/Constants.PPM, 32/Constants.PPM);

    private String name;
    private float width, height;

    /**
     * This is the constructor of the enums. This will asign the values to the variables
     * @param name      name of the entity
     * @param width     width of the entity
     * @param height    height of the entity
     */
    EntityType(String name, float width, float height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }

    /**
     * returns the name of the entity
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * returns the width of the entity
     * @return float width
     */
    public float getWidth() {
        return width;
    }

    /**
     * returns the height of the entity
     * @return float height
     */
    public float getHeight() {
        return height;
    }

}
