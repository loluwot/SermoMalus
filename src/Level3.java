package com.mygdx.game;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Align;

/**
 * @author Eric Shi
 * @version 1.0
 *
 * <p>This is the third level of the game</p>
 */
public class Level3 implements Screen {
    private OrthographicCamera camera;
    private World world;
    private SpriteBatch batch;
    BitmapFont smallFont;
    OrthographicCamera textCamera;
    Fade fade;
    ShapeRenderer fadeRenderer;
    private GameMap gameMap;
    SpriteBatch smoothText;
    private TweenManager manager;
    private boolean atDoor;
    private boolean[] visited;
    private boolean[] displayed;
    private String[] dialogue;
    private int counter = 0;
    float timer = 6;

    /**
     * This method creates the objects and dialogue used for the game and sensors
     */
    @Override
    public void show() {
        world = new World(new Vector2(0, -40f), true);
        gameMap = new TiledGameMap(world, "thirdMap.tmx");
        ((Player)(gameMap.entities.get(0))).setCurrentJumpPower(5);
        World.setVelocityThreshold(0.01f);
        smoothText = new SpriteBatch();
        FreeTypeFontGenerator generator1 = new FreeTypeFontGenerator(Gdx.files.internal("Roboto.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter1.size = 20;
        smallFont = generator1.generateFont(parameter1);
        fade = new Fade(1);
        fadeRenderer = new ShapeRenderer();
        manager = new TweenManager();
        Tween.registerAccessor(Fade.class, new FadeAccessor());
        Tween.set(fade, FadeAccessor.ALPHA).target(1).start(manager);
        Tween.to(fade, FadeAccessor.ALPHA, 1).target(0).start(manager);


        batch = new SpriteBatch();
        visited = new boolean[8];
        displayed = new boolean[8];
        dialogue = new String[9];


        dialogue[8] = "Welcome to the third level also known as \"Letting it go with Generated Dialogue\" \n The object of this level is to collect the hearts. Collecting them is supposed to represent stopping rumors.";


        dialogue[7] = "Congratulations, you collected your first heart and also metaphorically stopped the first rumor.";
        dialogue[6] = "Most people don't like it when something bad about them is said behind their backs or when they're made fun of.";
        dialogue[5] = "Wow really going for this one huh? That's commitment, but also represents that stopping some rumors will make you lose progress.";
        dialogue[4] = "Whats important is that what they say doesn't really matter and shouldn't.";
        dialogue[3] = "Rumors can really be hurtful but only if you let them. So think about what you should do.";
        dialogue[2] = "Are rumors really that large of an issue?";
        dialogue[1] = "Congratulations, you've gotten the rumor before the last one. Ask yourself, is it really worth it? \n Do you really believe what the others say about you? \n Anyways, the doors unlocked, you're free to go but if you want to continue, be my guest.";
        dialogue[0] = "Wow, I don't know how you got here but you shouldn't have because it's impossible. \n I'm not saying you're a cheater but...";



        for (int x = 0; x < visited.length; x++) {
            visited[x] = false;
        }

        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                Gdx.graphics.getWidth()/Constants.PPM/2f,
                Gdx.graphics.getHeight()/Constants.PPM/2f);

        //128,128);
        camera.update();

        final Player player = ((Player) gameMap.entities.get(0));

        world.setContactListener(new ContactListener() {
            /**
             * This method identifies when contact is beginned
             * @param contact
             */
            @Override
            public void beginContact(Contact contact) {
                for (int i = 0; i < ((TiledGameMap) gameMap).sensors.size; i++){
                    if (contact.getFixtureA().equals(player.body.getFixtureList().get(0)) && contact.getFixtureB().equals(((TiledGameMap) gameMap).sensors.get(i).getFixtureList().get(0))){
                      if (!visited[i]) {
                          visited[i] = true;
                          counter++;
                          break;
                      }
                    }
                }
                if (contact.getFixtureA().equals(player.body.getFixtureList().get(0)) && contact.getFixtureB().equals(((TiledGameMap) gameMap).door.get(0).getFixtureList().get(0))){
                    atDoor = true;
                }

            }
            /**
             * This method identifies when contact is ended
             * @param contact
             */
            @Override
            public void endContact(Contact contact) {
                for (int i = 0; i < ((TiledGameMap) gameMap).sensors.size; i++){
                    if (contact.getFixtureA().equals(player.body.getFixtureList().get(0)) && contact.getFixtureB().equals(((TiledGameMap) gameMap).sensors.get(i).getFixtureList().get(0))){
                        break;
                    }
                }
                if (contact.getFixtureA().equals(player.body.getFixtureList().get(0)) && contact.getFixtureB().equals(((TiledGameMap) gameMap).door.get(0).getFixtureList().get(0))){
                    atDoor = false;
                }
            }

            /**
             * satisfies override
             * @param contact
             * @param oldManifold
             */
            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }
            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        });


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        manager.update(delta);

        camera.update();
        gameMap.update(Gdx.graphics.getDeltaTime(), world);
        gameMap.render(camera, batch);



        cameraChange(camera, gameMap, gameMap.entities.get(0).grounded || gameMap.entities.get(0).body.getLinearVelocity().y < -17);
        if (atDoor && counter >= 6){
            smoothText.setProjectionMatrix(textCamera.combined);
            smoothText.begin();
            smallFont.draw(smoothText, "E", (1 / 2f) * 12 * Constants.PPM, 12/4f * 1.5f * Constants.PPM, 1/2f * 1.5f * Constants.PPM, Align.center, true);
            smoothText.end();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E) && atDoor && counter >= 6){
            Tween.registerAccessor(Fade.class, new FadeAccessor());
            Tween.set(fade, FadeAccessor.ALPHA).target(0).start(manager);
            Tween.to(fade, FadeAccessor.ALPHA, 1).target(1).start(manager).setCallback(new TweenCallback() {
                @Override
                public void onEvent(int type, BaseTween<?> source) {
                    ((Game)Gdx.app.getApplicationListener()).setScreen(new Menu(true));
                }
            });

        }
        if (timer <= 0){
            timer = 6;
        }

        for (int x = 0 ; x < 8; x++) {
            if (visited[x] && !displayed[x] && timer > 0) {

                timer -= delta;
                if (timer < 6 && timer > 0){
                    timer -= delta;
                }
                smoothText.setProjectionMatrix(textCamera.combined);
                smoothText.begin();
                smallFont.draw(smoothText, dialogue[x], textCamera.position.x - 6*Constants.PPM, textCamera.position.y + 2 * Constants.PPM,  16f * Constants.PPM, Align.center, true);
                System.out.println (x);
                smoothText.end();
                if (timer <= 0 ) {
                    displayed[x] = true;
                }
                break;
            }
        }

    }
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
        textCamera = new OrthographicCamera();
        textCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        smoothText = new SpriteBatch();
        textCamera.position.x = camera.position.x*2f*Constants.PPM;
        textCamera.position.y = camera.position.y*2f*Constants.PPM;
        textCamera.update();
        camera.update();

        smoothText.setProjectionMatrix(textCamera.combined);
        smoothText.begin();
        smallFont.draw(smoothText, dialogue[8], 12*Constants.PPM, 10*Constants.PPM,  16f * Constants.PPM, Align.center, true);
        smoothText.end();

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
