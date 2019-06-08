package com.mygdx.game;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
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

import java.util.ArrayList;
import java.util.Random;

public class Level2 implements Screen {
    private OrthographicCamera camera;
    private OrthographicCamera textCamera;

    private ShapeRenderer statBarRenderer;
    private SpriteBatch batch;
    private SpriteBatch hudText;
    private SpriteBatch smoothText;
    private Fade fade;
    private ShapeRenderer fadeRenderer;
    private ShapeRenderer smoothShape;

    private BitmapFont smallFont;
    private BitmapFont smallerFont;
    private BitmapFont font;

    private World world;

    private GameMap gameMap;
    private TweenManager manager;

    private boolean interact;
    private int goodCharacter = -1;
    private int badCharacter = -1;
    private int interactable = -1;
    private float textWidth = -1;
    private int counter = 0;


    boolean dialogUp;
    ArrayList<String> badText;
    ArrayList<String> goodText;
    ArrayList<Integer> alreadyTalkedTo;
    String dialogText = "";
    int randomResponse = -1;
    float timer = 3;

    @Override
    public void show()
    {
        alreadyTalkedTo = new ArrayList<Integer>();
        smoothShape = new ShapeRenderer();
        world = new World(new Vector2(0, -40f), true);
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

            }
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

    @Override
    public void render(float delta) {
        textCamera.update();

        final Player player = ((Player) gameMap.entities.get(0));
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        manager.update(delta);

        camera.update();
        gameMap.update(Gdx.graphics.getDeltaTime(), world);
        gameMap.render(camera, batch);
        if(interact && interactable != -1 && timer == 3 && !alreadyTalkedTo.contains(interactable)){
            smoothText.setProjectionMatrix(textCamera.combined);
            smoothText.begin();
            smallFont.draw(smoothText, "E", ((TiledGameMap)gameMap).x.get(interactable)*2 + 12, ((TiledGameMap)gameMap).y.get(interactable)*2f+(4f)*Constants.PPM);
            smoothText.end();
        }
        if (timer < 3 && timer > 0){
            timer -= delta;
        }
        else if (timer <= 0){
            dialogUp = false;
            dialogText = "";
            timer = 3;
            randomResponse = -1;
        }
        if (interact && goodCharacter != -1 && Gdx.input.isKeyPressed(Input.Keys.E) && timer == 3 && !alreadyTalkedTo.contains(interactable)){
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
        if (interact && badCharacter != -1 && Gdx.input.isKeyPressed(Input.Keys.E) && timer == 3 && !alreadyTalkedTo.contains(interactable)){
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
        textCamera.position.x = camera.position.x*2f*Constants.PPM;
        
        textCamera.position.y = camera.position.y*2f*Constants.PPM;
        
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
    public void dispose() {

    }
}
