package com.mygdx.game.screen01;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Monopoly;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.assets.RegionNames;
import com.mygdx.game.buttons.MinusButton;
import com.mygdx.game.buttons.PlusButton;
import com.mygdx.game.buttons.SoundButton;
import com.mygdx.game.buttons.StartGameButton;
import com.mygdx.game.util.GameManager;

import sun.security.jca.GetInstance;

public class Settings implements Screen {
    private Stage stage;
    private Monopoly game;

    private TextureRegion start;
    private TextureRegion settings;
    private TextureRegion exit;
    private TextureRegion volume;
    private TextureRegion minus;
    private TextureRegion plus;


    private TextureRegion background;
    private float m = 100;
    private float i = 50;


    private StartGameButton startButton;
    private SoundButton soundButton;
    private PlusButton plusButton;
    private MinusButton minusButton;
    public static Music MUSIC = Gdx.audio.newMusic(Gdx.files.internal("JingleBells.mp3"));
    ;


    private TextureAtlas atlas;
    private AssetManager assetManager;


    public Settings(Monopoly game) {
        this.game = game;
    }

    @Override
    public void show() {


        assetManager = new AssetManager();
        assetManager.load(AssetDescriptors.SETTINGS);
        assetManager.finishLoading();


        atlas = assetManager.get(AssetDescriptors.SETTINGS);

        start = atlas.findRegion(RegionNames.BUTTON_START);
        volume = atlas.findRegion(RegionNames.BUTTON_SOUND);
        minus = atlas.findRegion(RegionNames.BUTTON_MINUS);
        plus = atlas.findRegion(RegionNames.BUTTON_PLUS);

        Settings.MUSIC.setVolume(GameManager.INSTANCE.getMusicVolume());


        background = atlas.findRegion(RegionNames.MAIN_BACKGROUND);

     /*   Settings.MUSIC = Gdx.audio.newMusic(Gdx.files.internal("JingleBells.mp3"));
        MUSIC.setLooping(true);
        MUSIC.play();*/

        stage = new Stage();
        startButton = new StartGameButton(Gdx.graphics.getWidth() - 600, Gdx.graphics.getHeight() - 450, start);
        soundButton = new SoundButton(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), volume);
        plusButton = new PlusButton(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), plus);
        minusButton = new MinusButton(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), minus);


        Table table = new Table();
        table.top();
        table.setFillParent(true);

        table.add(startButton).expandX().padTop(200);
        table.add(soundButton).expandX().padTop(200);
        table.add(plusButton).expandX().padTop(200);
        table.add(minusButton).expandX().padTop(200);


        stage.addActor(table);

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.gotoScreen(Monopoly.Screens.MAIN);
            }
        });

        soundButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (Settings.MUSIC.isPlaying())
                    Settings.MUSIC.stop();
                else
                    Settings.MUSIC.play();
                GameManager.INSTANCE.save();
            }
        });


        minusButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                m = m - 20;
                Settings.MUSIC.setVolume(m);

                GameManager.INSTANCE.save();
                ;
            }
        });

        plusButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                i = i + 20;
                Settings.MUSIC.setVolume(i);
                GameManager.INSTANCE.save();
            }
        });


        Gdx.input.setInputProcessor(stage);


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getBatch().end();
        stage.act();
        stage.draw();


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
        atlas.dispose();
        stage.dispose();
        assetManager.dispose();
        MUSIC.dispose();


    }

    @Override
    public void dispose() {

    }
}
