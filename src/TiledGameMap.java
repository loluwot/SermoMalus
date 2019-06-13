package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * @author Andy Cai
 * @version 1.0
 */
public class TiledGameMap extends GameMap {

    TiledMap tiledMap;
    OrthogonalTiledMapRenderer tiledMapRenderer;
    Array<Body> sensors;
    Array<Body> door;
    Array<Body> good;
    Array<Body> bad;
    Array<Body> interactable;
    Array<Float> x;
    Array<Float> y;
    Array<Float> doorX;

    /**
     * Generates default world with no special sensors.
     * @param world the box2d world
     * @param fileName the filename of the tilemap
     */
    public TiledGameMap(World world, String fileName) {
        super(world);
        tiledMap = new TmxMapLoader().load(fileName);
        MapBodyBuilder.buildShapes(tiledMap, 32, world, "collidable", false, 0.8f);
        sensors = MapBodyBuilder.buildShapes(tiledMap, 32, world,"sensors", true, 0.8f);
        MapBodyBuilder.buildShapes(tiledMap, 32, world, "bounds", false, 0);
        door = MapBodyBuilder.buildShapes(tiledMap, 32, world, "door", true, 0.8f);

        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/Constants.PPM);
    }

    /**
     * Generates world with more sensors for level 2.
     * @param world box2d world
     * @param fileName filename of the tilemap
     * @param temp overloaded boolean
     */
    public TiledGameMap(World world, String fileName, boolean temp){
        this(world, fileName);
        good = MapBodyBuilder.buildShapes(tiledMap, 32, world, "good", true, 0.8f);
        bad = MapBodyBuilder.buildShapes(tiledMap, 32, world, "bad", true, 0.8f);
        x = MapBodyBuilder.buildShapesX(tiledMap, 32, world, "interactable", true, 0.8f);
        y = MapBodyBuilder.buildShapesY(tiledMap, 32, world, "interactable", true, 0.8f);
        doorX = MapBodyBuilder.buildShapesX(tiledMap, 32, world, "door", true, 0.8f);
        interactable = MapBodyBuilder.buildShapes(tiledMap, 32, world, "interactable", true, 0.8f);
    }

    /**
     * Renders the tile map
     * @param camera camera to render on
     * @param batch Spritebatch to render
     */
    @Override
    public void render(OrthographicCamera camera, SpriteBatch batch) {
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        super.render(camera, batch);
        batch.end();
    }

    /**
     * Updates the gamemap.
     * @param delta
     * @param world
     */
    @Override
    public void update(float delta, World world) {
        super.update(delta, world);
    }

    /**
     * Garbage collection
     */
    @Override
    public void dispose() {
        tiledMap.dispose();
    }

    /**
     * Utility method that gets the TileType of the column and row in the current tilemap
     * @param layer layer id
     * @param col column
     * @param row row
     * @return
     */
    @Override
    public TileType getTileTypeC(int layer, int col, int row) {
        Cell cell = ((TiledMapTileLayer) tiledMap.getLayers().get(layer)).getCell(col,row);
        if (cell != null) {
            TiledMapTile tile = cell.getTile();
            if (tile != null) {
                int id = tile.getId();
                return TileType.getTileType(id);
            }
        }
        return null;
    }

    /**
     * Gets width of tilemap
     * @return width of tilemap
     */
    @Override
    public int getWidth() {
        return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getWidth();
    }

    /**
     * Gets height of tilemap
     * @return height of tilemap
     */
    @Override
    public int getHeight() {
        return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getHeight();
    }

    /**
     * Gets number of layers in tilemap
     * @return number of layers
     */
    @Override
    public int getLayers() {
        return tiledMap.getLayers().getCount();
    }
}
