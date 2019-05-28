package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;


public class Splash extends ApplicationAdapter {
    SpriteBatch batch;
    Texture logo;
    float alpha;
    BitmapFont font;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    float dA;
    float frames;
    OrthographicCamera camera;
    int windowWidth;
    int windowHeight;
    Texture ball;
    Texture ball1;
    Texture ball2;
    int ballX;
    int ballY;
    int ballX1;
    int ballY1;
    int ballX2;
    int ballY2;
    float angle;
    float angle1;
    float angle2;
    float pathAngle;
    @Override
    public void resize(int width, int height){
        camera.setToOrtho(false, width, height);
        windowHeight = height;
        windowWidth = width;
    }
    @Override
    public void create() {
        windowHeight = 600;
        windowWidth = 1000;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Azonix.otf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        batch = new SpriteBatch();
        //images
        logo = new Texture("logo.png");
        ball = new Texture("ball.png");
        ball1 = new Texture("ball1.png");
        ball2 = new Texture("ball2.png");
        //ball init
        angle = (float)-Math.PI/2;
        angle1 = 0;
        angle2 = (float)Math.PI/2;
        pathAngle = (float)Math.PI/6;
        //
        alpha = 0;
        parameter.size = 30;
        font = generator.generateFont(parameter);
        font.setColor(new Color(0.2f, 0.2f, 0.2f, 0));
        dA = 0.02f;
        frames = 20;

    }

    @Override
    public void render() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Color c = batch.getColor();
        Color f = font.getColor();
        if (alpha >= 0) {
            if (alpha >= 1 && frames > 0) {
                alpha = 1;
                frames--;
            }
            if (alpha >= 1 && frames <= 0) {
                alpha = 1;
                dA = -0.03f;
            }
            batch.setColor(c.r, c.g, c.b, alpha);
            batch.begin();
            batch.draw(logo, (windowWidth - 512) / 2 - 10, (windowHeight - 512) / 2, 512, 512);
            font.setColor(f.r, f.g, f.b, alpha);
            font.draw(batch, "Deuterium Industries", 0, (windowHeight - 512) / 4 + font.getXHeight() + 40, Gdx.graphics.getWidth(), Align.center, true);
            batch.end();
            if (alpha < 1 || (alpha >= 1 && dA < 0)) {
                alpha += dA;
            }
        }
        else if (angle < 12*Math.PI){
            int rX = 125;
            ballX = (int)(rX*Math.cos(angle)*Math.cos(pathAngle) - 50*Math.sin(angle)*Math.sin(pathAngle) + windowWidth/2-50);
            ballY = (int)(rX*Math.cos(angle)*Math.sin(pathAngle) + 50*Math.sin(angle)*Math.cos(pathAngle) + windowHeight/2+80);
            pathAngle += Math.PI/4;
            ballX1 = (int)(rX*Math.cos(angle1)*Math.cos(pathAngle) - 50*Math.sin(angle1)*Math.sin(pathAngle) + windowWidth/2-50);
            ballY1 = (int)(rX*Math.cos(angle1)*Math.sin(pathAngle) + 50*Math.sin(angle1)*Math.cos(pathAngle) + windowHeight/2+80);
            pathAngle += Math.PI/4;
            ballX2 = (int)(rX*Math.cos(angle2)*Math.cos(pathAngle) - 50*Math.sin(angle2)*Math.sin(pathAngle) + windowWidth/2-50);
            ballY2 = (int)(rX*Math.cos(angle2)*Math.sin(pathAngle) + 50*Math.sin(angle2)*Math.cos(pathAngle) + windowHeight/2+80);
            batch.setColor(1,1,1,1);
            batch.begin();
            batch.draw(ball, ballX, ballY,100,100);
            batch.draw(ball1, ballX1, ballY1, 100,100);
            batch.draw(ball2, ballX2, ballY2, 100,100);
            font.setColor(0,0,0,1);
            String s = "Loading";
            for (int i = 0; i < (angle/1)%3; i++){
                s += ".";
            }
            font.draw(batch, s, 0, Gdx.graphics.getHeight()/2-100, Gdx.graphics.getWidth(), Align.center, true);

            batch.end();
            pathAngle -= 2*Math.PI/4;
            angle += 0.1f;
            angle1 += 0.1f;
            angle2 += 0.1f;


        }
        else{

        }

    }

    private void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        logo.dispose();
    }
}
