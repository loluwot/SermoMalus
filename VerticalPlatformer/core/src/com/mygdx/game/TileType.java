package com.mygdx.game;

import java.util.HashMap;
/**
 * @author Eric Shi
 * @version 1.0
 *
 * /**
 * *@author hollowbit
 * /
 * This enum alloes the tilemap to have certain properties at tiles.
 * The original code was made by hollowbit. The altered code was created by Eric Shi
 */
public enum TileType {

    /**
     * enums
     *
     * DIRT:Serves as the wall of the tutorial level
     * STONE:Serves as the ground of the tutorial level
     */
    DIRT(2, false, "Dirt"),
    STONE(6, true, "Stone");

    /**
     * Variable name        Type        Description
     * id                   int         the id of the tile
     * collidable           boolean     whether the tile is collidable or not
     * name                 string      Name of tile
     * TILE_SIZE            int         Size of the tile
     */
    private int id;
    private boolean collidable;
    private String name;

    public static final int TILE_SIZE = 16;

    /**
     * Constructor
     * @param i id number
     * @param c collidable boolean
     * @param n name
     */
    private TileType (int i, boolean c, String n) {
        this.id = i;
        this.collidable = c;
        this.name = n;

    }

    /**
     * gets the name variable
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * gets the id variable
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * returns the collidable
     * @return collidable
     */
    public boolean getCollidable() {
        return collidable;
    }

    /**
     * tileMap is a HashMap that contains a map of ids for the tilemap
     */
    private static HashMap<Integer, TileType> tileMap;

    /**
     * this static method runs at the start. This will fill the HashMap
     */
    static {
        tileMap = new HashMap<Integer, TileType>();
        for (TileType t: TileType.values()) {
            tileMap.put(t.getId(),t);
        }
    }

    /**
     * This will take in the key variable and return the tile's id.
     * @param key
     * @return a tile id and its properties
     */
    public static TileType getTileType(int key) {
        return tileMap.get(key);
    }

}
