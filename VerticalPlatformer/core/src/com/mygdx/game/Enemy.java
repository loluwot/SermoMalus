package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Enemy extends Entity {
    private Texture image;
    private boolean goingLeft;
    private float velocity = 3;
    public Enemy (float x, float y, GameMap map, Body body) {
        super(x, y, EntityType.ENEMY, map, body);
        image = new Texture("player.PNG");
        body.setLinearVelocity(5, 0);
    }
    @Override
    public void update(float deltaTime, World world){
        if (goingLeft){
            System.out.println((int)((body.getPosition().x-getWidth()/2)*2) + "xxxxx");
            System.out.println((int) ((body.getPosition().y-getHeight()/2)*2)-1+ "yyyy");
            body.setLinearVelocity(new Vector2(-velocity,body.getLinearVelocity().y));
            TileType type = this.map.getTileTypeC(1,(int) ((body.getPosition().x-getWidth()/2)*2), (int) ((body.getPosition().y-getHeight()/2)*2)-1);
            if (type == null){
                System.out.println("lol");
                body.setLinearVelocity(new Vector2(velocity,body.getLinearVelocity().y));
                goingLeft = !goingLeft;
            }
            else{
                if (!type.getCollidable()){
                    System.out.println("lol1");
                    body.setLinearVelocity(new Vector2(velocity,body.getLinearVelocity().y));
                    goingLeft = !goingLeft;
                }
            }
        }
        else{
            TileType type = this.map.getTileTypeC(1,(int) ((body.getPosition().x-getWidth()/2)*2)+1, (int) ((body.getPosition().y-getHeight()/2)*2)-1);
            System.out.println((int) ((body.getPosition().x-getWidth()/2)*2) + "xxxxx");
            System.out.println((int) ((body.getPosition().y-getHeight()/2)*2)-1 + "yyyy");
            body.setLinearVelocity(new Vector2(velocity,body.getLinearVelocity().y));
            if (type == null){
                System.out.println("lol2");
                body.setLinearVelocity(new Vector2(-velocity,body.getLinearVelocity().y));
                goingLeft = !goingLeft;
            }
            else{
                if (!type.getCollidable()){
                    System.out.println("lol3");
                    body.setLinearVelocity(new Vector2(-velocity,body.getLinearVelocity().y));
                    goingLeft = !goingLeft;
                }
            }
        }

    }
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x-getWidth()/2, pos.y-getHeight()/2, getWidth(), getHeight());
    }
}
