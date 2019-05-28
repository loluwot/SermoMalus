package com.mygdx.game;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Linear;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;

import java.awt.*;

public class SplashScreen implements Screen {
    private SpriteBatch batch;
    private Sprite sprite;
    private TweenManager manager;
    private BitmapFont font;
    private MovableText font2;
    private MovableText loading;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private EllipsePath ball1;
    private EllipsePath ball2;
    private EllipsePath ball3;
    private Sprite ball1Img;
    private Sprite ball2Img;
    private Sprite ball3Img;
    @Override
    public void show() {
        Tween.setCombinedAttributesLimit(4);
        ball1Img = new Sprite(new Texture("ball.png"));
        ball1Img.setSize(50,50);
        ball2Img = new Sprite(new Texture("ball1.png"));
        ball2Img.setSize(50,50);
        ball3Img = new Sprite(new Texture("ball2.png"));
        ball3Img.setSize(50,50);
        ball1 = new EllipsePath(0, (Gdx.graphics.getWidth()-ball1Img.getWidth())/2,Gdx.graphics.getHeight()*3/4-100, 0, 0, ball1Img, 100, 50);
        ball2 = new EllipsePath(0, (Gdx.graphics.getWidth()-ball1Img.getWidth())/2,Gdx.graphics.getHeight()*3/4-100, (float)Math.PI/6, 0, ball2Img, 100, 50);
        ball3 = new EllipsePath(0, (Gdx.graphics.getWidth()-ball1Img.getWidth())/2,Gdx.graphics.getHeight()*3/4-100, (float)Math.PI/3, 0, ball3Img, 100, 50);
        batch = new SpriteBatch();
        Texture logo = new Texture("logo.png");
        sprite = new Sprite(logo);
        manager = new TweenManager();
        font2 = new MovableText(0,Gdx.graphics.getHeight()/4,0.4f, "Azonix.otf",0);
        loading = new MovableText(0,Gdx.graphics.getHeight()/3,0.4f, "Azonix.otf",0);
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        sprite.setPosition((Gdx.graphics.getWidth()-sprite.getWidth())/2, ((float)((Gdx.graphics.getHeight())*0.6188))-sprite.getHeight()/2);
        //sprite.setPosition((Gdx.graphics.getWidth()-sprite.getWidth())/2, ((float)((Gdx.graphics.getHeight())*0.51)));
        Tween.set(sprite, SpriteAccessor.ALPHA).target(0).start(manager);
        Tween.to(sprite, SpriteAccessor.ALPHA, 2).target(1).start(manager);
        Tween.to(sprite, SpriteAccessor.ALPHA, 1).target(0).delay(2).start(manager);
        Tween.registerAccessor(MovableText.class, new MoveableFontAccessor());
        Tween.set(font2, MoveableFontAccessor.ALPHA).target(0).start(manager);
        Tween.to(font2, MoveableFontAccessor.ALPHA, 2).target(1).start(manager);
        Tween.to(font2, MoveableFontAccessor.XYSIZEALPHA, 2).target(0, Gdx.graphics.getHeight()/2, 0.7f, 1).delay(2).start(manager);
        Tween.to(font2, MoveableFontAccessor.ALPHA, 1).target(0).delay(5).start(manager);
        Tween.set(loading, MoveableFontAccessor.ALPHA).target(0).start(manager);
        Tween.to(loading, MoveableFontAccessor.ALPHA, 1).target(1).delay(6).start(manager);
        Tween.to(loading, MoveableFontAccessor.ALPHA, 1).target(0).delay(14).start(manager);
        Tween.set(loading, MoveableFontAccessor.TEXT).target(0).start(manager);
        Tween.to(loading, MoveableFontAccessor.TEXT, 10).target(12).delay(6).ease(Linear.INOUT).start(manager);
        Tween.registerAccessor(EllipsePath.class, new EllipsePathAccessor());
        Tween.set(ball1, EllipsePathAccessor.ANGLE).target(0).start(manager);
        Tween.to(ball1, EllipsePathAccessor.ANGLE, 10).target((float)(13*Math.PI)).delay(5).ease(Linear.INOUT).start(manager);
        Tween.set(ball1, EllipsePathAccessor.ALPHA).target(0).start(manager);
        Tween.to(ball1, EllipsePathAccessor.ALPHA, 2).target(1).delay(6).start(manager);
        Tween.to(ball1, EllipsePathAccessor.ALPHA, 1).target(0).delay(14).start(manager);
        Tween.set(ball2, EllipsePathAccessor.ANGLE).target(0).start(manager);
        Tween.to(ball2, EllipsePathAccessor.ANGLE, 10).target((float)(17*Math.PI)).delay(5).ease(Linear.INOUT).start(manager);
        Tween.set(ball2, EllipsePathAccessor.ALPHA).target(0).start(manager);
        Tween.to(ball2, EllipsePathAccessor.ALPHA, 2).target(1).delay(6).start(manager);
        Tween.to(ball2, EllipsePathAccessor.ALPHA, 1).target(0).delay(14).start(manager);
        Tween.set(ball3, EllipsePathAccessor.ANGLE).target(0).start(manager);
        Tween.to(ball3, EllipsePathAccessor.ANGLE, 10).target((float)(15*Math.PI)).delay(5).ease(Linear.INOUT).start(manager);
        Tween.set(ball3, EllipsePathAccessor.ALPHA).target(0).start(manager);
        Tween.to(ball3, EllipsePathAccessor.ALPHA, 2).target(1).delay(6).start(manager);
        Tween.to(ball3, EllipsePathAccessor.ALPHA, 1).target(0).delay(14).start(manager).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new Menu());
            }
        });

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(120f/255, 144f/255, 156f/255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        manager.update(delta);

        batch.begin();
        ball1.draw(batch);
        ball2.draw(batch);
        ball3.draw(batch);
        sprite.draw(batch);
        font2.draw("DEUTERIUM INDUSTRIES", batch, true);
        loading.draw("Loading", batch, true);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
