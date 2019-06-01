package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

public class PlatformerGame implements Screen {
	OrthographicCamera camera;
	SpriteBatch batch;
	Texture img;
	World world;
	GameMap gameMap;
	Box2DDebugRenderer debug;
	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		gameMap.update(Gdx.graphics.getDeltaTime(), world);
		gameMap.render(camera, batch);
		debug.render(world, camera.combined);
		if (Gdx.input.isKeyPressed(Input.Keys.UP) && camera.position.y < 1344/ Constants.PPM) {
			camera.position.y += 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && camera.position.x > 256/Constants.PPM) {
			camera.position.x -= 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && camera.position.x < 1344/Constants.PPM) {
			camera.position.x += 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && camera.position.y > 256/Constants.PPM) {
			camera.position.y -= 1;
		}

	}

	@Override
	public void show() {
		debug = new Box2DDebugRenderer();
		world = new World(new Vector2(0, -40f), true);
		batch = new SpriteBatch();
		img = new Texture(
				"badlogic.jpg");

		camera = new OrthographicCamera();
		camera.setToOrtho(false,
				Gdx.graphics.getWidth()/Constants.PPM,
				Gdx.graphics.getHeight()/Constants.PPM);
		//128,128);
		camera.update();

		gameMap = new TiledGameMap(world);
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
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
