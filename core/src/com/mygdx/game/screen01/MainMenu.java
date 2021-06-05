package com.mygdx.game.screen01;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.Monopoly;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.assets.RegionNames;
import com.mygdx.game.buttons.ExitButton;
import com.mygdx.game.buttons.SettingsButton;
import com.mygdx.game.buttons.StartGameButton;
import com.mygdx.game.util.GameManager;


public class MainMenu extends ScreenAdapter implements Screen, Disposable {


    private Stage stage;
    private Monopoly game;

    private TextureRegion start;
    private TextureRegion settings;
    private TextureRegion exit;
    private TextureRegion background;


    private StartGameButton startButton;
    private ExitButton exitButton;
    private SettingsButton settingsButton;
    private Music backgroundMusic;


    private TextureAtlas atlas;
    private AssetManager assetManager;
    private Label e, a;


    public MainMenu(Monopoly game) {
        this.game = game;
    }

    @Override
    public void show() {


        GameManager.INSTANCE.load();

        assetManager = new AssetManager();
        assetManager.load(AssetDescriptors.MAIN);
        assetManager.finishLoading();

        atlas = assetManager.get(AssetDescriptors.MAIN);

        start = atlas.findRegion(RegionNames.BUTTON_START);
        exit = atlas.findRegion(RegionNames.BUTTON_EXIT);
        settings = atlas.findRegion(RegionNames.BUTTON_SETTINGS);
        background = atlas.findRegion(RegionNames.MAIN_BACKGROUND);

        // backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("JingleBells.mp3"));
        if (GameManager.INSTANCE.getMusic() == true)
            Settings.MUSIC.play();
        Settings.MUSIC.setLooping(true);

        //backgroundMusic.play();

        stage = new Stage();
        startButton = new StartGameButton(Gdx.graphics.getWidth() - 600, Gdx.graphics.getHeight() - 450, start);
        exitButton = new ExitButton(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), exit);
        settingsButton = new SettingsButton(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - 450, settings);


        Table table = new Table();
        table.top();
        table.setFillParent(true);

        table.add(a).expandX().pad(40, 10, 30, 100);
        table.row();
        table.add(e).expandX().pad(40, 10, 5, 100);
        table.row();
        table.add(startButton).expandX().pad(30, 100, 5, 100);
        table.row();
        table.add(settingsButton).expandX().pad(0, 100, 5, 100);
        table.row();
        table.add(exitButton).expandX().pad(0, 100, 40, 100);


        stage.addActor(table);

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.gotoScreen(Monopoly.Screens.GAME);
            }
        });
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.gotoScreen(Monopoly.Screens.SETTINGS);
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.exit();
            }
        });
        Gdx.input.setInputProcessor(stage);


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            game.gotoScreen(Monopoly.Screens.GAME);
        }

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
        super.hide();
        atlas.dispose();
        stage.dispose();
        assetManager.dispose();


    }


}
