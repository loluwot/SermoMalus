package com.mygdx.game;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

public class Level1 implements Screen {
	OrthographicCamera camera;
	SpriteBatch batch;
	SpriteBatch hudText;
	Texture img;
	World world;
	QuizMap quizMap;
	TweenManager manager;
	Box2DDebugRenderer debug;
	ShapeRenderer statBarRenderer;
	BitmapFont font;
	int currentSensor = -1;
	float mininum = 0; //temp delete later
	BitmapFont smallFont;
	OrthographicCamera textCamera;
	SpriteBatch smoothText;
	ArrayList<String> questions;
	ArrayList<Integer> answers;
	int currentQuestionNumber;
	int selectedAnswer = -1;
	float time = 3;
	int lastAnswer = -1;
	@Override
	public void render (float delta) {
		textCamera.update();
		System.out.println(currentSensor + "sensor");
		Player player = ((Player) quizMap.entities.get(0));

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		manager.update(delta);
		if (quizMap.entities.get(0).body.getLinearVelocity().y < mininum){
			mininum = quizMap.entities.get(0).body.getLinearVelocity().y;
		}

		camera.update();

		float width = 200;
		float height = 20;
		quizMap.update(Gdx.graphics.getDeltaTime(), world);
		quizMap.render(camera, batch);
		System.out.println(time);
		if (currentSensor == lastAnswer && currentSensor != -1 && time > 0){
			time-= delta;
		}
		else if (currentSensor == lastAnswer && currentSensor != -1 && time <= 0){
			selectedAnswer = currentSensor;
		}
		else{
			time = 3;
			selectedAnswer = -1;
		}
		smoothText.setProjectionMatrix(textCamera.combined);

		smoothText.begin();
		if(selectedAnswer != -1)
			smallFont.draw(smoothText, "Testing Testing\n"+(char)('A'+selectedAnswer), (15/2f)*1.5f*Constants.PPM, 12f*1.5f*Constants.PPM, 10f*1.5f*Constants.PPM, Align.center, true);
		else
			smallFont.draw(smoothText, "Testing Testing", (15/2f)*1.5f*Constants.PPM, 12f*1.5f*Constants.PPM, 10f*1.5f*Constants.PPM, Align.center, true);

		if (time < 3 && time > 0){
			smallFont.draw(smoothText, (int)(Math.ceil(time))+"", (15/2f)*1.5f*Constants.PPM, 19/2f*1.5f*Constants.PPM, 10f*1.5f*Constants.PPM, Align.center, true);
		}
		smallFont.draw(smoothText, "A", (18/2f)*1.5f*Constants.PPM, (11/2f)*1.5f*Constants.PPM, 1f*1.5f*Constants.PPM, Align.center, true);
		smallFont.draw(smoothText, "B", (23/2f)*1.5f*Constants.PPM, (11/2f)*1.5f*Constants.PPM, 1f*1.5f*Constants.PPM, Align.center, true);
		smallFont.draw(smoothText, "C", (28/2f)*1.5f*Constants.PPM, (11/2f)*1.5f*Constants.PPM, 1f*1.5f*Constants.PPM, Align.center, true);
		smoothText.end();
		cameraChange(camera, quizMap, quizMap.entities.get(0).grounded || quizMap.entities.get(0).body.getLinearVelocity().y < -17);
		//This is the HUD that shows the player's current movement speed. Movement speed is increased by eating powerups that will temporarily increase movement speed.
		statBarRenderer.begin(ShapeRenderer.ShapeType.Line);
		statBarRenderer.setColor(0f,1f,0.5f,0.75f);
		statBarRenderer.rect(20,20,width, height);
		statBarRenderer.setColor(171/255f, 32/255f, 253/255f, 0.75f);
		statBarRenderer.rect(20,60,width,height);
		statBarRenderer.end();
		statBarRenderer.begin(ShapeRenderer.ShapeType.Filled);
		statBarRenderer.setColor(0f,1f,0.5f,0.75f);
		statBarRenderer.rect(20,20,(width*player.getCurrentMaxSpeed()/player.MAXIMUMVELOCITY), height);
		statBarRenderer.setColor(171/255f, 32/255f, 253/255f, 0.75f);
		statBarRenderer.rect(20,60, width*player.getCurrentJumpPower()/player.JUMPPOWER, height);
		statBarRenderer.end();
		hudText.begin();
		hudText.enableBlending();
		hudText.setColor(1,1,1,0.5f);
		font.draw(hudText, "Movement Speed", 20, 20);
		font.draw(hudText, "Jump Power", 20,60);
		hudText.end();
		textCamera.update();
		lastAnswer = currentSensor;
	}

	@Override
	public void show() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		font = generator.generateFont(parameter);
		debug = new Box2DDebugRenderer();
		statBarRenderer = new ShapeRenderer();
		world = new World(new Vector2(0, -40f), true);
		FreeTypeFontGenerator generator1 = new FreeTypeFontGenerator(Gdx.files.internal("Roboto.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter1.size = 20;
		smallFont = generator1.generateFont(parameter1);
		batch = new SpriteBatch();
		hudText = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false,
				Gdx.graphics.getWidth()/Constants.PPM/1.5f,
				Gdx.graphics.getHeight()/Constants.PPM/1.5f);
		//128,128);
		camera.update();
		manager = new TweenManager();
		quizMap = new TiledGameMap(world, "quiz.tmx");
		final Player player = ((Player) quizMap.entities.get(0));
		world.setContactListener(new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
				for (int i = 0; i < ((TiledGameMap) quizMap).sensors.size; i++){
					if (contact.getFixtureA().equals(player.body.getFixtureList().get(0)) && contact.getFixtureB().equals(((TiledGameMap) quizMap).sensors.get(i).getFixtureList().get(0))){
						currentSensor = i;
						break;
					}
				}
			}
			@Override
			public void endContact(Contact contact) {
				currentSensor = -1;
			}
			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
			}
			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
			}
		});
		textCamera = new OrthographicCamera();
		textCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		smoothText = new SpriteBatch();

		textCamera.update();

	}
	public void cameraChange (OrthographicCamera camera, QuizMap map, boolean updateY) {
		float xPos = quizMap.entities.get(0).body.getPosition().x;
		float yPos = quizMap.entities.get(0).body.getPosition().y;
		if (xPos <= camera.viewportWidth/2) {
			camera.position.x = camera.viewportWidth/2;
		}

		if (xPos >= (map.getPixelWidth()/32f - camera.viewportWidth/2)) {
			camera.position.x = (map.getPixelWidth()/32f - camera.viewportWidth/2);
		}

		if (xPos > camera.viewportWidth/2 && xPos < (map.getPixelWidth()/32f - camera.viewportWidth/2)) {
			camera.position.x = xPos;

		}
		if (updateY) {
			if (yPos < camera.viewportHeight / 2 && manager.getRunningTweensCount() == 0) {
				System.out.println(1);
				Tween.registerAccessor(OrthographicCamera.class, new CameraAccessor());
				Tween.set(camera, CameraAccessor.Y).target(camera.position.y).start(manager);
				Tween.to(camera, CameraAccessor.Y, 0.5f).target(camera.viewportHeight / 2).start(manager).ease(TweenEquations.easeNone);
			}
			if (yPos >= map.getPixelHeight() - camera.viewportHeight / 2) {
				Tween.registerAccessor(OrthographicCamera.class, new CameraAccessor());
				System.out.println(2);
				Tween.set(camera, CameraAccessor.Y).target(camera.position.y).start(manager);
				Tween.to(camera, CameraAccessor.Y, 0.5f).target(map.getPixelHeight() - camera.viewportHeight / 2).start(manager).ease(TweenEquations.easeNone);

			}
			if (yPos > camera.viewportHeight / 2 && yPos < map.getPixelHeight() - camera.viewportHeight / 2) {
				System.out.println("bruh");
				Tween.registerAccessor(OrthographicCamera.class, new CameraAccessor());
				Tween.set(camera, CameraAccessor.Y).target(camera.position.y).start(manager);
				System.out.println(camera.position.y + "y");
				Tween.to(camera, CameraAccessor.Y, 0.5f).target(yPos).start(manager).ease(TweenEquations.easeNone);

			}
		}
		textCamera.position.x = camera.position.x*1.5f*Constants.PPM;
		System.out.println(textCamera.position.x);
		textCamera.position.y = camera.position.y*1.5f*Constants.PPM;
		System.out.println(textCamera.position.y);
		textCamera.update();
		camera.update();
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
