package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.physics.box2d.*;



public abstract class GameMap {

    protected ArrayList<Entity> entities;
    protected World world;
    public GameMap(World world) {
        entities = new ArrayList<Entity>();
        BodyDef playerDef = new BodyDef();
        playerDef.type = BodyDef.BodyType.DynamicBody;
        playerDef.position.set(1,5);
        playerDef.fixedRotation = true;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(EntityType.PLAYER.getWidth()/2f,EntityType.PLAYER.getHeight()/2f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.6f;
        fixtureDef.restitution = 0f;
        Body body = world.createBody(playerDef);
        body.createFixture(fixtureDef);
        entities.add(new Player(40,300,this, body));
        this.world = world;
    }

    public void render (OrthographicCamera camera, SpriteBatch batch) {
        for (Entity entity : entities) {
            entity.render(batch);
        }
    }

    public void update (float delta, World world) {
        for (Entity entity : entities) {
            entity.update(delta, world);
        }
        world.step(1/60f, 8, 3);
        for(Entity entity : entities){
            entity.superUpdate(delta, world);
        }

    }

    public void dispose () {

    }

    public TileType getTileTypeL(int layer, float x, float y) {
        return this.getTileTypeC(layer, (int) (x / TileType.TILE_SIZE), (int) (y / TileType.TILE_SIZE));
    }


    public abstract TileType getTileTypeC(int layer, int col, int row);

    public boolean hitBoxCollide(float x, float y, float width, float height) {
        x -= width/2;
        y -= height/2;
        if (x < 0 || y < 0 || x + width > getPixelWidth() / Constants.PPM || y + height > getPixelHeight() / Constants.PPM){
            return true;
        }

        for (int row = (int) (y*2-0.08); row < Math.ceil(y+height)*2-1; row++) {
            for (int col = (int) (x*2); col < Math.ceil((x*2 + width)); col++) {
                for (int layer = 0; layer < 2; layer++) {
                    TileType type = getTileTypeC(layer, col, row);
                    if (type != null && type.getCollidable())
                        return true;
                }
            }
        }

        return false;
    }

    public abstract int getWidth();
    public abstract int getHeight();
    public abstract int getLayers();

    public int getPixelWidth() {
        return this.getWidth() * TileType.TILE_SIZE;
    }

    public int getPixelHeight() {
        return this.getHeight() * TileType.TILE_SIZE;
    }

}