package com.mygdx.game;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Menu implements Screen {
    private Stage stage;
    private Table table;
    private TextButton buttonStart, buttonInstructions, buttonExit;
    private TextButton.TextButtonStyle style;
    private TextureAtlas atlas;
    private SpriteBatch batch;
    private BitmapFont titleFont;
    private TweenManager manager;
    private boolean atCenter;
    private TextArea textArea;
    public void show(){


        atCenter = true;
        manager = new TweenManager();
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
        textArea.setSize(600,450);
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
        buttonStart = new TextButton("START", style);
        buttonStart.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new PlatformerGame());
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
                    Tween.set(buttonStart, ButtonAccessor.X).target(buttonStart.getX()).start(manager);
                    Tween.to(buttonStart, ButtonAccessor.X, 1).target(-300).start(manager);
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
                    Tween.set(buttonStart, ButtonAccessor.X).target(buttonStart.getX()).start(manager);
                    Tween.to(buttonStart, ButtonAccessor.X, 1).target(0).start(manager);
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
        Container<TextButton> container = new Container<TextButton>(buttonStart);
        container.width(300);
        container.height(150);
        Container<TextButton> containerExit = new Container<TextButton>(buttonExit);
        containerExit.width(300);
        containerExit.height(150);
        Container<TextButton> containerInstruc = new Container<TextButton>(buttonInstructions);
        containerInstruc.width(300);
        containerInstruc.height(150);
        table.setWidth(Gdx.graphics.getWidth());
        table.center().bottom();
        table.add(container).colspan(1).center();
        table.row();
        table.add(containerInstruc).colspan(1).center();
        table.row();
        table.add(containerExit).colspan(1).center();
        table.setHeight(300);
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

    }
}
