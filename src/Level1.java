package com.mygdx.game;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
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

import javax.naming.Name;
import java.util.ArrayList;
import java.util.Arrays;

public class Level1 implements Screen {
	/**
	 * @author Andy Cai
	 * @version 1.0
	 *
	 * This is the first level of the game
	 */
	//cameras
	OrthographicCamera camera;
	OrthographicCamera textCamera;

	World world; //Box2d world

	GameMap gameMap; //processes map and provides utility for TMX files

	//TweemEngine
	TweenManager manager;

	//renderers
	ShapeRenderer statBarRenderer;
	ShapeRenderer fadeRenderer;
	ShapeRenderer progressBar;
	SpriteBatch batch;
	SpriteBatch hudText;
	SpriteBatch smoothText;

	//fonts
	BitmapFont font;
	BitmapFont smallFont;
	BitmapFont smallerFont;

	//arraylist of questions and answers
	ArrayList<String> questions;
	ArrayList<Integer> answers;
	ArrayList<Triplet> possibleAnswers;

	//fade object
	Fade fade;

	//states
	int currentQuestionNumber;
	int selectedAnswer = -1;
	float time = 3;
	int lastAnswer = -1;
	float timer2 = 2;
	boolean atDoor;
	int score;
	int currentSensor = -1;

	/**
	 * This method adds in the responses and is run during run time. This also creates the interactable objects.
	 * @param delta time change between each frame
	 */
	@Override
	public void render (float delta) {
		textCamera.update();
		
		Player player = ((Player) gameMap.entities.get(0));

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		manager.update(delta);

		camera.update();

		float width = 200;
		float height = 20;
		gameMap.update(Gdx.graphics.getDeltaTime(), world);
		gameMap.render(camera, batch);
		
		if (currentSensor == lastAnswer && currentSensor != -1 && time > 0){
			time-= delta;
		}
		else if (currentSensor == lastAnswer && currentSensor != -1 && time <= 0 && timer2 == 2){
			selectedAnswer = currentSensor;
		}
		else if (time > 0 && (currentSensor != lastAnswer || currentSensor == -1)){
			time = 3;
			selectedAnswer = -1;
		}
		if (selectedAnswer != -1 && timer2 > 0 && currentQuestionNumber < questions.size()){
			smoothText.setProjectionMatrix(textCamera.combined);
			smoothText.begin();
			String draw = "";
			if (selectedAnswer == answers.get(currentQuestionNumber)){
				draw = "Correct Answer!";
				score++;
			}
			else{
				draw = "Incorrect Answer! The correct answer was " + (char)(answers.get(currentQuestionNumber) +'A') + ") " + possibleAnswers.get(currentQuestionNumber).triplet[answers.get(currentQuestionNumber)];
			}
			smallFont.draw(smoothText, draw, (16 / 2f) * 1.5f * Constants.PPM, 23/2f * 1.5f * Constants.PPM, 9f * 1.5f * Constants.PPM, Align.center, true);
			timer2 -= delta;
			smoothText.end();
		}
		else if (selectedAnswer != -1 && timer2 <= 0){
			currentQuestionNumber++;
			timer2 = 2;
			time = 3;
			selectedAnswer = -1;
		}
		if (currentQuestionNumber < questions.size()) {
			smoothText.setProjectionMatrix(textCamera.combined);
			smoothText.begin();
			smallFont.draw(smoothText, "Question " + (currentQuestionNumber + 1), (15 / 2f) * 1.5f * Constants.PPM, 12f * 1.5f * Constants.PPM, 10f * 1.5f * Constants.PPM, Align.center, true);
			String question = questions.get(currentQuestionNumber)+ "\n";
			for (int i = 0; i < 3; i++){
				question += (char)('A' + i) + ") ";
				question += possibleAnswers.get(currentQuestionNumber).triplet[i];
				question += "\n";
			}
			if (selectedAnswer == -1) {
				smallFont.draw(smoothText, question, (16 / 2f) * 1.5f * Constants.PPM, 23 / 2f * 1.5f * Constants.PPM, 9f * 1.5f * Constants.PPM, Align.center, true);
			}
			smallFont.draw(smoothText, "A", (19 / 2f) * 1.5f * Constants.PPM, (7 / 2f) * 1.5f * Constants.PPM, 1f * 1.5f * Constants.PPM, Align.center, true);
			smallFont.draw(smoothText, "B", (24 / 2f) * 1.5f * Constants.PPM, (7 / 2f) * 1.5f * Constants.PPM, 1f * 1.5f * Constants.PPM, Align.center, true);
			smallFont.draw(smoothText, "C", (29 / 2f) * 1.5f * Constants.PPM, (7 / 2f) * 1.5f * Constants.PPM, 1f * 1.5f * Constants.PPM, Align.center, true);
			smoothText.end();
			if (time < 3 && time > 0) {
				progressBar.setProjectionMatrix(textCamera.combined);
				progressBar.begin(ShapeRenderer.ShapeType.Line);
				progressBar.rect((16 / 2f) * 1.5f * Constants.PPM, (17 / 2f) * 1.5f * Constants.PPM, (18 / 2f) * 1.5f * Constants.PPM, (1 / 2f) * 1.5f * Constants.PPM);
				progressBar.end();
				progressBar.begin(ShapeRenderer.ShapeType.Filled);
				progressBar.rect((16 / 2f) * 1.5f * Constants.PPM, (17 / 2f) * 1.5f * Constants.PPM, ((18 / 2f) * 1.5f * Constants.PPM) * (time) / 3f, (1 / 2f) * 1.5f * Constants.PPM);
				progressBar.end();
			}
		}
		else{
			smoothText.setProjectionMatrix(textCamera.combined);
			smoothText.begin();
			smallFont.draw(smoothText, "The quiz is complete! You can go back to the main menu by going to the door and pressing E. Your score was " + (score/60)  + "/" + questions.size() + ".", (15 / 2f) * 1.5f * Constants.PPM, 12f * 1.5f * Constants.PPM, 10f * 1.5f * Constants.PPM, Align.center, true);
			smoothText.end();

		}
		if (atDoor){
			smoothText.setProjectionMatrix(textCamera.combined);
			smoothText.begin();
			smallFont.draw(smoothText, "E", (1 / 2f) * 1.5f * Constants.PPM, 12/2f * 1.5f * Constants.PPM, 1/2f * 1.5f * Constants.PPM, Align.center, true);
			smoothText.end();
		}
		smoothText.setProjectionMatrix(textCamera.combined);
		smoothText.begin();
		smallerFont.draw(smoothText, "This is the first level. It is a quiz. To answer the question on the screen, move the character to the button labelled with your selected answer. Stay there until the progress bar becomes completely empty. ", (4 / 2f) * 1.5f * Constants.PPM, 24/2f * 1.5f * Constants.PPM, 7/2f * 1.5f * Constants.PPM, Align.center, true);
		smoothText.end();
		cameraChange(camera, gameMap, gameMap.entities.get(0).grounded || gameMap.entities.get(0).body.getLinearVelocity().y < -17);
		//This is the HUD that shows the player's current movement speed and jump power.
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
		if (Gdx.input.isKeyPressed(Input.Keys.E) && atDoor){
			Tween.registerAccessor(Fade.class, new FadeAccessor());
			Tween.set(fade, FadeAccessor.ALPHA).target(0).start(manager);
			Tween.to(fade, FadeAccessor.ALPHA, 1).target(1).start(manager).setCallback(new TweenCallback() {
				/**
				 * This method will check if the animation is done, and then go to main menu.
				 * @param type			integer
				 * @param source		this is the source of the BaseTween
				 */
				@Override
				public void onEvent(int type, BaseTween<?> source) {
					((Game)Gdx.app.getApplicationListener()).setScreen(new Menu(true));
				}
			});

		}
		textCamera.update();
		fade.draw(fadeRenderer);
		lastAnswer = currentSensor;
	}

	/**
	 * <p>This method initializes objects and adds in many contact listeners.</p>
	 */
	@Override
	public void show() {
		//add questions
		questions = new ArrayList<String>();
		answers = new ArrayList<Integer>();
		possibleAnswers = new ArrayList<Triplet>();
		FileHandle internal = Gdx.files.internal("qa.txt");
		String bigString = internal.readString();
		
		String [] words = bigString.split("\n");
		
		for (int i = 0; i < words.length; i++){
			if (i % 5 == 0){
				questions.add(words[i]);
			}
			if(i % 5 == 1){
				Triplet triplet = new Triplet(words[i], words[i+1], words[i+2]);
				i += 3;
				possibleAnswers.add(triplet);

			}
			if (i % 5 == 4){
				
				

				int answer = (words[i]).charAt(0) - '0';
				
				answers.add(answer);
			}
		}
		progressBar = new ShapeRenderer();
		manager = new TweenManager();
		fade = new Fade(1);
		fadeRenderer = new ShapeRenderer();
		Tween.registerAccessor(Fade.class, new FadeAccessor());
		Tween.set(fade, FadeAccessor.ALPHA).target(1).start(manager);
		Tween.to(fade, FadeAccessor.ALPHA, 1).target(0).start(manager);
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		font = generator.generateFont(parameter);
		statBarRenderer = new ShapeRenderer();
		world = new World(new Vector2(0, -100f), true);
		World.setVelocityThreshold(0.01f);
		FreeTypeFontGenerator generator1 = new FreeTypeFontGenerator(Gdx.files.internal("Roboto.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter1.size = 20;
		smallFont = generator1.generateFont(parameter1);
		parameter1.size = 18;
		smallerFont = generator1.generateFont(parameter1);
		batch = new SpriteBatch();
		hudText = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false,
				Gdx.graphics.getWidth()/Constants.PPM/1.5f,
				Gdx.graphics.getHeight()/Constants.PPM/1.5f);
		//128,128);
		camera.update();

		gameMap = new TiledGameMap(world, "quiz.tmx");
		final Player player = ((Player) gameMap.entities.get(0));
		world.setContactListener(new ContactListener() {
			/**
			 * This method checks if there is an interactable contact
			 * @param contact Contact object
			 */
			@Override
			public void beginContact(Contact contact) {
				for (int i = 0; i < ((TiledGameMap) gameMap).sensors.size; i++){
					if (contact.getFixtureA().equals(player.body.getFixtureList().get(0)) && contact.getFixtureB().equals(((TiledGameMap) gameMap).sensors.get(i).getFixtureList().get(0))){
						currentSensor = i;
						break;
					}
				}
				if (contact.getFixtureA().equals(player.body.getFixtureList().get(0)) && contact.getFixtureB().equals(((TiledGameMap) gameMap).door.get(0).getFixtureList().get(0))){
					atDoor = true;
				}

			}
			/**
			 * This method checks for when the contact ends
			 * @param contact	contact object
			 */
			@Override
			public void endContact(Contact contact) {
				for (int i = 0; i < ((TiledGameMap) gameMap).sensors.size; i++){
					if (contact.getFixtureA().equals(player.body.getFixtureList().get(0)) && contact.getFixtureB().equals(((TiledGameMap) gameMap).sensors.get(i).getFixtureList().get(0))){
						currentSensor = -1;
						break;
					}
				}
				if (contact.getFixtureA().equals(player.body.getFixtureList().get(0)) && contact.getFixtureB().equals(((TiledGameMap) gameMap).door.get(0).getFixtureList().get(0))){
					atDoor = false;
				}
			}
			/**
			 * satisfies overload, not used
			 * @param contact
			 * @param oldManifold
			 */
			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {

			}

			/**
			 * satisfies overload, not used
			 * @param contact
			 * @param impulse
			 */
			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {

			}
		});
		textCamera = new OrthographicCamera();
		textCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		smoothText = new SpriteBatch();

		textCamera.update();

	}
	/**
	 * This is the main side scroll algorithm
	 * @param camera	Orthographic camera camera
	 * @param map		Map map
	 * @param updateY	boolean update
	 */
	public void cameraChange (OrthographicCamera camera, GameMap map, boolean updateY) {
		float xPos = gameMap.entities.get(0).body.getPosition().x;
		float yPos = gameMap.entities.get(0).body.getPosition().y;
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

				Tween.registerAccessor(OrthographicCamera.class, new CameraAccessor());
				Tween.set(camera, CameraAccessor.Y).target(camera.position.y).start(manager);
				Tween.to(camera, CameraAccessor.Y, 0.5f).target(camera.viewportHeight / 2).start(manager).ease(TweenEquations.easeNone);
			}
			if (yPos >= map.getPixelHeight() - camera.viewportHeight / 2) {
				Tween.registerAccessor(OrthographicCamera.class, new CameraAccessor());

				Tween.set(camera, CameraAccessor.Y).target(camera.position.y).start(manager);
				Tween.to(camera, CameraAccessor.Y, 0.5f).target(map.getPixelHeight() - camera.viewportHeight / 2).start(manager).ease(TweenEquations.easeNone);

			}
			if (yPos > camera.viewportHeight / 2 && yPos < map.getPixelHeight() - camera.viewportHeight / 2) {

				Tween.registerAccessor(OrthographicCamera.class, new CameraAccessor());
				Tween.set(camera, CameraAccessor.Y).target(camera.position.y).start(manager);

				Tween.to(camera, CameraAccessor.Y, 0.5f).target(yPos).start(manager).ease(TweenEquations.easeNone);

			}
		}
		textCamera.position.x = camera.position.x*1.5f*Constants.PPM;

		textCamera.position.y = camera.position.y*1.5f*Constants.PPM;

		textCamera.update();
		camera.update();

	}
	/**
	 * satisfies override, not used
	 * @param width
	 * @param height
	 */
	@Override
	public void resize(int width, int height) {

	}

	/**
	 * <p>satisfies override, not used</p>
	 */
	@Override
	public void pause() {

	}

	/**
	 * <p>satisfies override, not used</p>
	 */
	@Override
	public void resume() {

	}

	/**
	 * <p>satisfies override, not used</p>
	 */
	@Override
	public void hide() {

	}

	/**
	 * <p>Garbage collection</p>
	 */
	@Override
	public void dispose () {
		batch.dispose();
		hudText.dispose();
		smoothText.dispose();
		statBarRenderer.dispose();
		progressBar.dispose();
	}
}
