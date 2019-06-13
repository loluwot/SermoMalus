package com.mygdx.game;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Andy Cai
 * @version 1.0 Created level, made basic structure
 * @version 2.0 Completed level.
 *
 * <p>This is the second level of the game</p>
 *
 */
public class Level2 implements Screen {
    //cameras
    private OrthographicCamera camera; //normal camera in world coordinates
    private OrthographicCamera textCamera; //camera for text, separate so that smooth text projection is possible.

    //renderers
    private ShapeRenderer statBarRenderer; //renders the rectangles for jump speed and movement speed
    private SpriteBatch batch; //regular sprite batch, player is drawn here
    private SpriteBatch hudText; //text that doesnt move and stays on screen
    private SpriteBatch smoothText; //text that moves with the sidescrolling but is still smooth.
    private Fade fade; //fade object, used to fade in and fade out
    private ShapeRenderer fadeRenderer; //used to render the fade
    private ShapeRenderer smoothShape; //used to render small shapes without losing resolution

    private BitmapFont smallFont; //font that is small, used on smoothText
    private BitmapFont smallerFont; //font that is smaller, used on smoothText
    private BitmapFont font; //font that is used on everything else

    private World world; //the box2d world

    private GameMap gameMap; //the game map object, contains and processes the TMX tilemap.

    private TweenManager manager; //the TweenEngine manager, used for animations and smooth transitions

    //states
    private boolean interact; //if player is on interactable object
    private int goodCharacter = -1; //id of good character player is on, -1 if player is not on good character
    private int badCharacter = -1; //id of bad character player is on, -1 if player is not on
    private int interactable = -1; //id of interactable object player is on
    private float textWidth = -1; //width of text displayed on dialog
    private int counter = 0; //counts number of nice people player has talked to
    boolean dialogUp; //checks if dialog should be up
    boolean atDoor; //checks if player is at a door

    //possible responses for characters
    ArrayList<String> badText; //bad or mean characters
    ArrayList<String> goodText; //nice characters

    ArrayList<Integer> alreadyTalkedTo; //people player has already talked to
    String dialogText = ""; //current text in dialog
    int randomResponse = -1; //which random response from badText or goodText
    float timer = 2; //timer that counts down from 2, the delay for how long dialog remains up.
    float currentDoorX = -1; //the current x position of the door the player is standing in. -1 if player is not standing on door
    float time = 0; //timer counting time taken to complete

    /**
     * <p>This method initializes objects and adds in many contact listeners.</p>
     */
    @Override
    public void show()
    {
        alreadyTalkedTo = new ArrayList<Integer>();
        smoothShape = new ShapeRenderer();
        world = new World(new Vector2(0, -100f), true);
        gameMap = new TiledGameMap(world, "hallwayMap.tmx", true);
        World.setVelocityThreshold(0.01f);
        goodText = new ArrayList<String>();
        badText = new ArrayList<String>();
        goodText.add("Don't worry about what they say!");
        goodText.add("You look nice today!");
        badText.add("You are a nerd!");
        badText.add("You suck!");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        font = generator.generateFont(parameter);
        parameter.size = 20;
        smallFont = generator.generateFont(parameter);
        parameter.size = 16;
        smallerFont = generator.generateFont(parameter);
        manager = new TweenManager();
        fade = new Fade(1);
        fadeRenderer = new ShapeRenderer();
        statBarRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        hudText = new SpriteBatch();
        smoothText = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                Gdx.graphics.getWidth()/Constants.PPM/2f,
                //Gdx.graphics.getHeight()/Constants.PPM/2f);
                gameMap.getPixelHeight()/Constants.PPM);
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
                for (int i = 0; i < ((TiledGameMap) gameMap).good.size; i++) {
                    if (contact.getFixtureA().equals(player.body.getFixtureList().get(0)) && contact.getFixtureB().equals(((TiledGameMap) gameMap).good.get(i).getFixtureList().get(0))) {
                        goodCharacter = i;
                        
                        break;
                    }
                }
                for (int i = 0; i < ((TiledGameMap) gameMap).bad.size; i++) {
                    if (contact.getFixtureA().equals(player.body.getFixtureList().get(0)) && contact.getFixtureB().equals(((TiledGameMap) gameMap).bad.get(i).getFixtureList().get(0))) {
                        badCharacter = i;
                        break;
                    }
                }
                for (int i = 0; i < ((TiledGameMap) gameMap).interactable.size; i++) {
                    if (contact.getFixtureA().equals(player.body.getFixtureList().get(0)) && contact.getFixtureB().equals(((TiledGameMap) gameMap).interactable.get(i).getFixtureList().get(0))) {
                        interact = true;
                        interactable = i;
                        break;
                    }
                }
                for (int i = 0; i < ((TiledGameMap) gameMap).door.size; i++){
                    if (contact.getFixtureA().equals(player.body.getFixtureList().get(0)) && contact.getFixtureB().equals(((TiledGameMap) gameMap).door.get(i).getFixtureList().get(0))) {
                        atDoor = true;
                        currentDoorX = ((TiledGameMap) gameMap).doorX.get(i);
                        break;
                    }
                }

            }
            /**
             * This method identifies when contact is ended
             * @param contact
             */
            @Override
            public void endContact(Contact contact) {
                for (int i = 0; i < ((TiledGameMap) gameMap).interactable.size; i++) {
                    if (contact.getFixtureA().equals(player.body.getFixtureList().get(0)) && contact.getFixtureB().equals(((TiledGameMap) gameMap).interactable.get(i).getFixtureList().get(0))) {
                        interact = false;
                        interactable = -1;
                        break;
                    }
                }
                for (int i = 0; i < ((TiledGameMap) gameMap).good.size; i++) {
                    if (contact.getFixtureA().equals(player.body.getFixtureList().get(0)) && contact.getFixtureB().equals(((TiledGameMap) gameMap).good.get(i).getFixtureList().get(0))) {
                        goodCharacter = -1;

                        break;
                    }
                }
                for (int i = 0; i < ((TiledGameMap) gameMap).bad.size; i++) {
                    if (contact.getFixtureA().equals(player.body.getFixtureList().get(0)) && contact.getFixtureB().equals(((TiledGameMap) gameMap).bad.get(i).getFixtureList().get(0))) {
                        badCharacter = -1;
                        break;
                    }
                }
                for (int i = 0; i < ((TiledGameMap) gameMap).door.size; i++){
                    if (contact.getFixtureA().equals(player.body.getFixtureList().get(0)) && contact.getFixtureB().equals(((TiledGameMap) gameMap).door.get(i).getFixtureList().get(0))) {
                        atDoor = false;
                        currentDoorX = -1;
                        break;
                    }
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
        Tween.registerAccessor(Fade.class, new FadeAccessor());
        Tween.set(fade, FadeAccessor.ALPHA).target(1).start(manager);
        Tween.to(fade, FadeAccessor.ALPHA, 1).target(0).start(manager);
    }

    /**
     * This method adds in the responses and is run during run time. This also creates the interactable objects.
     * @param delta time change between each frame
     */
    @Override
    public void render(float delta) {
        if (counter < 7) {
            time += delta;
        }
        System.out.println((int)(time*100) + "time");
        textCamera.update();
        System.out.println(counter);
        final Player player = ((Player) gameMap.entities.get(0));
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        manager.update(delta);

        camera.update();
        gameMap.update(Gdx.graphics.getDeltaTime(), world);
        gameMap.render(camera, batch);
        if(interact && interactable != -1 && timer == 2 && !alreadyTalkedTo.contains(interactable) && counter < 7){
            smoothText.setProjectionMatrix(textCamera.combined);
            smoothText.begin();
            smallFont.draw(smoothText, "E", ((TiledGameMap)gameMap).x.get(interactable)*2 + 12, ((TiledGameMap)gameMap).y.get(interactable)*2f+(4f)*Constants.PPM);
            smoothText.end();
        }
        if (atDoor){
            smoothText.setProjectionMatrix(textCamera.combined);
            smoothText.begin();
            smallFont.draw(smoothText, "F", (currentDoorX)*2 + 12, 10.5f*Constants.PPM, 1f*Constants.PPM, Align.center, true);
            smoothText.end();
        }
        if (counter < 7){
            smoothText.setProjectionMatrix(textCamera.combined);
            smoothText.begin();
            smallerFont.draw(smoothText, "Go and talk to 7 nice people! You can determine who is nice based on their dialogue with other people. You can talk to a person by pressing E near them. You can leave at any time by moving onto a door and pressing F.", 3*Constants.PPM, 13f*Constants.PPM, 11f*Constants.PPM, Align.center, true);
            smoothText.end();
        }
        else{
            smoothText.setProjectionMatrix(textCamera.combined);
            smoothText.begin();
            smallFont.draw(smoothText, "Congratulations for completing this level! Your time was " + (int)(time*100) + ". Feel free to exit by pressing F on any of the doors.", 3*Constants.PPM, 12f*Constants.PPM, 11f*Constants.PPM, Align.center, true);
            smoothText.end();
        }
        if (atDoor && Gdx.input.isKeyPressed(Input.Keys.F)){
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
        if (timer < 2 && timer > 0){
            timer -= delta;
        }
        else if (timer <= 0){
            dialogUp = false;
            dialogText = "";
            timer = 2;
            randomResponse = -1;
        }
        if (interact && goodCharacter != -1 && Gdx.input.isKeyPressed(Input.Keys.E) && timer == 2 && !alreadyTalkedTo.contains(interactable) && counter < 7){

            if (randomResponse == -1) {
                Random random = new Random();
                randomResponse = random.nextInt(goodText.size());
            }
            dialogText = goodText.get(randomResponse);
            dialogUp = true;
            timer -= delta;
            System.out.println(player.getCurrentMaxSpeed() + " d");
            player.setCurrentMaxSpeed(Math.min(player.getCurrentMaxSpeed()+0.5f, player.MAXIMUMVELOCITY));
            counter++;
            alreadyTalkedTo.add(interactable);
        }
        if (interact && badCharacter != -1 && Gdx.input.isKeyPressed(Input.Keys.E) && timer == 2 && !alreadyTalkedTo.contains(interactable) && counter < 7){

            if (randomResponse == -1) {
                Random random = new Random();
                randomResponse = random.nextInt(badText.size());
            }
            dialogText = badText.get(randomResponse);
            dialogUp = true;
            timer -= delta;
            alreadyTalkedTo.add(interactable);
            if (player.getCurrentMaxSpeed() - 0.1f >= 0.1f){
                player.setCurrentMaxSpeed(player.getCurrentMaxSpeed() - 0.1f);
            }
        }
        smoothText.setProjectionMatrix(textCamera.combined);
        smoothText.begin();
        font.setColor(new Color(0,0,0,1));
        font.draw(smoothText, "How are you doing?", 70*2+3, 105*2, 48*2-6, Align.center, true);
        font.draw(smoothText, "Fine, how about you?", 200*2+3, 105*2,48*2-6, Align.center, true);
        font.draw(smoothText, "I hate you!", (499)*2, 96*2, 48*2-10, Align.center, true);
        font.draw(smoothText, "Your shirt looks nice today!", 560*2, 106*2, 48*2-4, Align.center, true);
        font.draw(smoothText, "Thanks a lot!", 594*2, 140*2, 48*2-10, Align.center, true);
        font.draw(smoothText, "I like the positivity!", 675*2, 122*2, 48*2-10, Align.center, true);
        font.draw(smoothText,  "I hate John, he's a nerd.", 1028*2, 102*2, 48*2-10, Align.center, true);
        font.draw(smoothText, "Yes, let's bully him.", 1043*2, 143*2, 48*2-10, Align.center, true);
        font.draw(smoothText, "Thank you for being a great teacher!", 1315*2, 140*2 , 48*2-10, Align.center, true);
        font.draw(smoothText, "Thanks, I try my best.", 1380*2, 122*2, 48*2-11, Align.center, true);
        font.draw(smoothText, "You suck as a teacher!", 1475*2, 104*2, 48*2-12, Align.center, true);
        smoothText.end();
        cameraChange(camera, gameMap, gameMap.entities.get(0).grounded || gameMap.entities.get(0).body.getLinearVelocity().y < -17);
        float width = 200;
        float height = 20;
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
        font.setColor(new Color(0,0,0,1));
        font.draw(hudText, "Movement Speed", 20, 20);
        font.draw(hudText, "Jump Power", 20,60);
        font.draw(hudText, "Time: " + (int)(time*100), 20, 100);
        font.setColor(new Color(1,1,1,1));
        if (counter < 7) {
            font.draw(hudText, "Number of Nice People Left to Talk To: " + (7 - counter), 0, 550, Gdx.graphics.getWidth(), Align.center, true);
        }
        else{
            font.draw(hudText, "Level has been completed, go back to start.", 0, 550, Gdx.graphics.getWidth(), Align.center, true);
        }
        hudText.end();

        if (dialogUp){
            if (textWidth == -1){
                GlyphLayout layout = new GlyphLayout();
                layout.setText(font, dialogText);
                textWidth = layout.width;
            }

            smoothShape.begin(ShapeRenderer.ShapeType.Filled);
            smoothShape.setColor(new Color(1,1,1,1));
            smoothShape.rect(Gdx.graphics.getWidth()/2-10,40,textWidth+20,40);
            smoothShape.end();

            hudText.begin();
            font.setColor(new Color(0,0,0,1));
            font.draw(hudText, dialogText, Gdx.graphics.getWidth()/2, 70);
            hudText.end();

        }

        if (!dialogUp){
            textWidth = -1;
        }
        fade.draw(fadeRenderer);


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
        textCamera.position.x = camera.position.x*2f*Constants.PPM;
        
        textCamera.position.y = camera.position.y*2f*Constants.PPM;
        
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
     * satisfies override, not used
     */
    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    /**
     * satisfies override, not used
     */
    @Override
    public void hide() {

    }


    @Override
    public void dispose() {

    }
}
