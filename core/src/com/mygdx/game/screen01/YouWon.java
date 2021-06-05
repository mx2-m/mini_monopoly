package com.mygdx.game.screen01;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Monopoly;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.assets.RegionNames;
import com.mygdx.game.buttons.ExitButton;
import com.mygdx.game.buttons.SettingsButton;
import com.mygdx.game.buttons.StartGameButton;

public class YouWon implements Screen {

    private Stage stage;
    private Monopoly game;

    private TextureRegion start;
    private TextureRegion home;
    private TextureRegion exit;
    private TextureRegion background;


    private StartGameButton startButton;
    private ExitButton exitButton;
    private SettingsButton homeButton;


    private TextureAtlas atlas;
    private AssetManager assetManager;
    private Label e, a;

    public YouWon(Monopoly game) {
        this.game = game;
    }


    @Override
    public void show() {
        assetManager = new AssetManager();
        assetManager.load(AssetDescriptors.GAME_OVER);
        assetManager.finishLoading();

        atlas = assetManager.get(AssetDescriptors.GAME_OVER);

        start = atlas.findRegion(RegionNames.BUTTON_START);
        exit = atlas.findRegion(RegionNames.BUTTON_EXIT);
        home = atlas.findRegion(RegionNames.BUTTON_HOME);
        background = atlas.findRegion(RegionNames.WON_BACKGROUND);

        stage = new Stage();
        startButton = new StartGameButton(Gdx.graphics.getWidth() - 600, Gdx.graphics.getHeight() - 450, start);
        exitButton = new ExitButton(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), exit);
        homeButton = new SettingsButton(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - 450, home);


        Table table = new Table();
        table.top();
        table.setFillParent(true);


        table.add(startButton).expandX().padTop(300);
        table.add(homeButton).expandX().padTop(300);
        table.add(exitButton).expandX().padTop(300);


        stage.addActor(table);

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.gotoScreen(Monopoly.Screens.GAME);
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.exit();
            }
        });

        homeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.gotoScreen(Monopoly.Screens.MAIN);
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
        //super.hide();
        atlas.dispose();
        stage.dispose();
        assetManager.dispose();

    }

    @Override
    public void dispose() {

    }
}
