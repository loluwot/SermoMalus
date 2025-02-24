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

    public TiledGameMap(World world, String fileName) {
        super(world);
        tiledMap = new TmxMapLoader().load(fileName);
        MapBodyBuilder.buildShapes(tiledMap, 32, world, "collidable", false, 0.8f);
        sensors = MapBodyBuilder.buildShapes(tiledMap, 32, world,"sensors", true, 0.8f);
        MapBodyBuilder.buildShapes(tiledMap, 32, world, "bounds", false, 0);
        door = MapBodyBuilder.buildShapes(tiledMap, 32, world, "door", true, 0.8f);

        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/Constants.PPM);
    }
    public TiledGameMap(World world, String fileName, boolean temp){
        this(world, fileName);
        good = MapBodyBuilder.buildShapes(tiledMap, 32, world, "good", true, 0.8f);
        bad = MapBodyBuilder.buildShapes(tiledMap, 32, world, "bad", true, 0.8f);
        x = MapBodyBuilder.buildShapesX(tiledMap, 32, world, "interactable", true, 0.8f);
        y = MapBodyBuilder.buildShapesY(tiledMap, 32, world, "interactable", true, 0.8f);
        interactable = MapBodyBuilder.buildShapes(tiledMap, 32, world, "interactable", true, 0.8f);
    }
    @Override
    public void render(OrthographicCamera camera, SpriteBatch batch) {
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        super.render(camera, batch);
        batch.end();
    }

    @Override
    public void update(float delta, World world) {
        super.update(delta, world);
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
    }

    @Override
    public TileType getTileTypeC(int layer, int col, int row) {
        Cell cell = ((TiledMapTileLayer) tiledMap.getLayers().get(layer)).getCell(col,row);
        if (cell != null) {
            TiledMapTile tile = cell.getTile();

            if (tile != null) {
                int id = tile.getId();
                //System.out.println(id);

                return TileType.getTileType(id);
            }
            else{
                //System.out.println("who");
            }
        }
        else{
            //System.out.println("what");
        }

        return null;
    }

    @Override
    public int getWidth() {
        return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getWidth();
    }

    @Override
    public int getHeight() {
        return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getHeight();
    }

    @Override
    public int getLayers() {
        return tiledMap.getLayers().getCount();
    }
}
