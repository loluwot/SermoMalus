package com.mygdx.game;

public enum EntityType {
    PLAYER("player", 14, 32, 40);

    private String name;
    private int width, height;
    private float weight;

    EntityType(String name, int width, int height, float weight) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }
}
