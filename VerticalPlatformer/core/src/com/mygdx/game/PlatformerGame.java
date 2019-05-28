package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlatformerGame implements Screen {
	OrthographicCamera camera;
	SpriteBatch batch;
	Texture img;

	GameMap gameMap;
	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		gameMap.update(Gdx.graphics.getDeltaTime());
		gameMap.render(camera, batch);

		if (Gdx.input.isKeyPressed(Input.Keys.UP) && camera.position.y < 1344) {
			camera.position.y += 32;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && camera.position.x > 256) {
			camera.position.x -= 32;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && camera.position.x < 1344) {
			camera.position.x += 32;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && camera.position.y > 256) {
			camera.position.y -= 32;
		}

	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		img = new Texture(
				"badlogic.jpg");

		camera = new OrthographicCamera();
		camera.setToOrtho(false,
				Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		//128,128);
		camera.update();

		gameMap = new TiledGameMap();
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
