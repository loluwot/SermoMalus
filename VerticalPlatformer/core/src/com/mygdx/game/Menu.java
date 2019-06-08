package com.mygdx.game;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Menu implements Screen {
    private Stage stage;
    private Table table;
    private TextButton buttonLevel1, buttonInstructions, buttonExit, buttonLevel2, buttonLevel3;
    private TextButton.TextButtonStyle style;
    private TextureAtlas atlas;
    private SpriteBatch batch;
    private BitmapFont titleFont;
    private TweenManager manager;
    private boolean atCenter;
    private TextArea textArea;
    private Fade fade;
    private ShapeRenderer fadeRender;
    private boolean fadeIn;
    public Menu(boolean fadeIn){
        this.fadeIn = fadeIn;
    }
    public void show(){
        manager = new TweenManager();

        if (fadeIn) {
            fade = new Fade(1);
        }
        else{
            fade = new Fade(0);
        }
        fadeRender = new ShapeRenderer();
        if (fadeIn){
            Tween.registerAccessor(Fade.class, new FadeAccessor());
            Tween.set(fade, FadeAccessor.ALPHA).target(1).start(manager);
            Tween.to(fade, FadeAccessor.ALPHA, 1).target(0).start(manager);
        }
        atCenter = true;

        stage = new Stage(new ScreenViewport());
        style = new TextButton.TextButtonStyle();
        atlas = new TextureAtlas("button-packed/pack.atlas");
        table = new Table();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator generator1 = new FreeTypeFontGenerator(Gdx.files.internal("Deftone.ttf"));
        TextField.TextFieldStyle textStyle = new TextField.TextFieldStyle();

        batch = new SpriteBatch();
        NinePatchDrawable npn = new NinePatchDrawable(atlas.createPatch("buttonUp"));
        NinePatchDrawable npo = new NinePatchDrawable(atlas.createPatch("buttonHover"));
        NinePatchDrawable npd = new NinePatchDrawable(atlas.createPatch("buttonDown"));
        textStyle.font = generator.generateFont(parameter);
        textStyle.fontColor = new Color(0,0,0,1);
        textStyle.background = npn;
        textArea = new TextArea("Instructions\n\nDur dur dur dur dur dur ", textStyle);
        textArea.setSize(600,350);
        textArea.setPosition(Gdx.graphics.getWidth()/2-150, 0);
        textArea.setColor(1, 1, 1, 0);
        BitmapFont font = generator.generateFont(parameter);
        parameter.size = 100;
        parameter.color = new Color(1, 1, 1, 1);
        parameter.shadowColor = new Color(0,0,0,0.75f);
        parameter.shadowOffsetX = 10;
        parameter.shadowOffsetY = 10;
        titleFont = generator1.generateFont(parameter);
        font.setColor(0,0,0,1);
        style = new TextButton.TextButtonStyle();
        style.up = npn;
        style.down = npd;
        style.over = npo;
        style.checked = npd;
        style.font = font;
        style.fontColor = new Color(0,0,0,1);
        buttonLevel1 = new TextButton("LEVEL 1", style);
        buttonLevel1.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Tween.registerAccessor(Fade.class, new FadeAccessor());
                Tween.set(fade, FadeAccessor.ALPHA).target(0).start(manager);
                Tween.to(fade, FadeAccessor.ALPHA, 1).target(1).start(manager).setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        dispose();
                        ((Game)Gdx.app.getApplicationListener()).setScreen(new Level1());
                    }
                });

                return true;
            }
        });
        buttonLevel2 = new TextButton("LEVEL 2", style);
        buttonLevel2.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                ((Game)Gdx.app.getApplicationListener()).setScreen(new Level2());
                return true;
            }
        });
        buttonLevel3 = new TextButton("LEVEL 3", style);
        buttonLevel3.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                ((Game)Gdx.app.getApplicationListener()).setScreen(new Level2());
                return true;
            }
        });
        buttonExit = new TextButton("EXIT", style);
        buttonExit.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                Gdx.app.exit();
                return true;
            }
        });
        buttonInstructions = new TextButton("INSTRUCTIONS", style);
        buttonInstructions.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if (atCenter) {
                    Tween.registerAccessor(TextButton.class, new ButtonAccessor());
                    Tween.set(buttonExit, ButtonAccessor.X).target(buttonExit.getX()).start(manager);
                    Tween.to(buttonExit, ButtonAccessor.X, 1).target(-300).start(manager);
                    Tween.set(buttonInstructions, ButtonAccessor.X).target(buttonInstructions.getX()).start(manager);
                    Tween.to(buttonInstructions, ButtonAccessor.X, 1).target(-300).start(manager);
                    Tween.registerAccessor(TextArea.class, new TextAreaAccessor());
                    Tween.set(textArea, TextAreaAccessor.ALPHA).target(0).start(manager);
                    Tween.to(textArea, TextAreaAccessor.ALPHA, 0.5f).target(1).delay(1).start(manager);

                }
                else{
                    Tween.registerAccessor(TextButton.class, new ButtonAccessor());
                    Tween.set(buttonLevel1, ButtonAccessor.X).target(buttonLevel1.getX()).start(manager);
                    Tween.to(buttonLevel1, ButtonAccessor.X, 1).target(0).start(manager);
                    Tween.set(buttonExit, ButtonAccessor.X).target(buttonExit.getX()).start(manager);
                    Tween.to(buttonExit, ButtonAccessor.X, 1).target(0).start(manager);
                    Tween.set(buttonInstructions, ButtonAccessor.X).target(buttonInstructions.getX()).start(manager);
                    Tween.to(buttonInstructions, ButtonAccessor.X, 1).target(0).start(manager);
                    Tween.registerAccessor(TextArea.class, new TextAreaAccessor());
                    Tween.set(textArea, TextAreaAccessor.ALPHA).target(1).start(manager);
                    Tween.to(textArea, TextAreaAccessor.ALPHA, 0.5f).target(0).start(manager);
                }
                atCenter = !atCenter;
                return true;
            }
        });
        Container<TextButton> container = new Container<TextButton>(buttonLevel1);
        container.width(300);
        container.height(150);
        Container<TextButton> container1 = new Container<TextButton>(buttonLevel2);
        container1.width(300);
        container1.height(150);
        Container<TextButton> container2 = new Container<TextButton>(buttonLevel3);
        container2.width(300);
        container2.height(150);
        Container<TextButton> containerExit = new Container<TextButton>(buttonExit);
        containerExit.width(300);
        containerExit.height(150);
        Container<TextButton> containerInstruc = new Container<TextButton>(buttonInstructions);
        containerInstruc.width(300);
        containerInstruc.height(150);
        table.setWidth(Gdx.graphics.getWidth());
        table.center().bottom();
        table.add(container).center();
        table.add(container1).center();
        table.add(container2).center();
        table.row();
        table.add(containerInstruc).colspan(3).center();
        table.row();
        table.add(containerExit).colspan(3).center();
        table.setHeight(300);
        table.setWidth(1000);
        Gdx.input.setInputProcessor(stage);

    }
    public void render(float delta){
        Gdx.gl.glClearColor(120f/255, 144f/255, 156f/255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.addActor(textArea);
        stage.addActor(table);
        stage.act(delta);
        manager.update(delta);
        stage.draw();
        batch.begin();
        titleFont.draw(batch, "Sermo Malus", 0,550,Gdx.graphics.getWidth(), Align.center, true);
        batch.end();
        fade.draw(fadeRender);
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


    public void hide(){

    };

    @Override
    public void dispose () {
        buttonExit.remove();
        buttonInstructions.remove();
        buttonLevel1.remove();
        stage.dispose();
    }
}
