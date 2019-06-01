package com.mygdx.game;

public enum EntityType {
    PLAYER("player", 14/Constants.PPM, 32/Constants.PPM);

    private String name;
    private float width, height;

    EntityType(String name, float width, float height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

}
