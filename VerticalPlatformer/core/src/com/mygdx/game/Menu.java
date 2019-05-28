package com.mygdx.game;

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
    public void show(){
        stage = new Stage(new ScreenViewport());
        style = new TextButton.TextButtonStyle();
        atlas = new TextureAtlas("button-packed/pack.atlas");
        table = new Table();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        FreeTypeFontGenerator generator1 = new FreeTypeFontGenerator(Gdx.files.internal("Deftone.ttf"));
        batch = new SpriteBatch();
        NinePatchDrawable npn = new NinePatchDrawable(atlas.createPatch("buttonUp"));
        NinePatchDrawable npo = new NinePatchDrawable(atlas.createPatch("buttonHover"));
        NinePatchDrawable npd = new NinePatchDrawable(atlas.createPatch("buttonDown"));
        BitmapFont font = generator.generateFont(parameter);
        parameter.size = 100;
        parameter.color = new Color(245/255f, 127/255f, 23/255f, 1);
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
                return true;
            }
        });
        buttonInstructions = new TextButton("INSTRUCTIONS", style);
        Container<TextButton> container = new Container<TextButton>(buttonStart);
        container.width(200);

        Container<TextButton> containerExit = new Container<TextButton>(buttonExit);
        containerExit.width(200);

        Container<TextButton> containerInstruc = new Container<TextButton>(buttonInstructions);
        containerInstruc.width(275);
        table.setHeight(300);
        table.setWidth(Gdx.graphics.getWidth());
        table.center().bottom();
        table.add(container).colspan(1).center();
        table.row();
        table.add(containerInstruc).colspan(1).center();
        table.row();
        table.add(containerExit).colspan(1).center();
        Gdx.input.setInputProcessor(stage);

    }
    public void render(float delta){
        Gdx.gl.glClearColor(120f/255, 144f/255, 156f/255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.addActor(table);
        stage.act(delta);
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
