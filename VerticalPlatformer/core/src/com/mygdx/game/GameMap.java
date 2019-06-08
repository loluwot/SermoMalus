package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.physics.box2d.*;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;


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
//        BodyDef playerDef1 = new BodyDef();
//        playerDef1.type = BodyDef.BodyType.DynamicBody;
//        playerDef1.position.set(12,14);
//        playerDef1.fixedRotation = true;
//        PolygonShape shape1 = new PolygonShape();
//        shape1.setAsBox(EntityType.ENEMY.getWidth()/2f,EntityType.ENEMY.getHeight()/2f);
//        FixtureDef fixtureDef1 = new FixtureDef();
//        fixtureDef1.shape = shape1;
//        fixtureDef1.density = 10f;
//        fixtureDef1.friction = 0f;
//        fixtureDef1.restitution = 0f;
//        Body body2 = world.createBody(playerDef1);
//        body2.createFixture(fixtureDef1);
//        playerDef1.position.set(10,3);
//        Body body3 = world.createBody(playerDef1);
//        body3.createFixture(fixtureDef1);
        entities.add(new Player(40,300,this, body));
//        entities.add(new Enemy(0,0,this,body2));
//        entities.add(new Enemy(0,0,this, body3));
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
            //System.out.println("weird");
            return true;
        }

        for (int row = (int) (y*2-0.08); row < Math.ceil(y+height)*2-1; row++) {
            for (int col = (int) (x*2); col < Math.ceil((x*2 + width)); col++) {
                //System.out.println(row + " " + col);
                for (int layer = 0; layer < 2; layer++) {
                    TileType type = getTileTypeC(layer, col, row);
                    //System.out.println(" ~~~~~" + col + " " + row + "~~~~~~ ");
                    if (type != null){
                        //System.out.println(type.getName());
                    }
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