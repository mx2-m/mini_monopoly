package com.mygdx.game.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class Hud implements Disposable {

    public Stage stage;
    private Viewport viewport;
    private Integer money1;
    private Integer money2;
    private String name1;
    private String name2;
    private String piece1;
    private String piece2;

    SpriteBatch sprite;

    //Scene2D Widgets
    private Label playerNameLabel1, playerNameLabel2, playerMoneyLabel1, playerMoneyLabel2, pieceLabel1, pieceLabel2, e, player1L, player2L, line;


    public Hud(Player player1, Player player2) {
        sprite = new SpriteBatch();


        this.name1 = player1.getName();
        this.name2 = player2.getName();

        this.money1 = player1.getMoney();
        this.money2 = player2.getMoney();

        this.piece1 = player1.getSelectedPiece();
        this.piece2 = player2.getSelectedPiece();


        //setup the HUD viewport using a new camera seperate from gamecam
        //define stage using that viewport and games spritebatch
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport);

        //define labels using the String, and a Label style consisting of a font and color
        playerNameLabel1 = new Label(name1, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        pieceLabel1 = new Label(piece1, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        playerMoneyLabel1 = new Label(String.format("%04d", money1), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        playerNameLabel2 = new Label(name2, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        pieceLabel2 = new Label(piece2, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        playerMoneyLabel2 = new Label(String.format("%04d", money2), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        player1L = new Label("Player 1: ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        player2L = new Label("Player 2: ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        line = new Label("--------------------- ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //define a table used to organize hud's labels
        Table table = new Table();
        table.top();
        table.setFillParent(true);
        //add labels to table, padding the top, and giving them all equal width with expandX
        table.add(e).expandX().pad(30, 10, 40, 100);
        table.row();
        table.add(player1L).expandX().padRight(-700);
        table.row();
        table.add(playerNameLabel1).expandX().padRight(-700);
        table.row();
        table.add(pieceLabel1).expandX().padRight(-700);
        table.row();
        table.add(playerMoneyLabel1).expandX().padRight(-700);
        table.row();
        table.add(line).expandX().padRight(-700);
        table.row();
        table.add(player2L).expandX().padRight(-700);
        table.row();
        table.add(playerNameLabel2).expandX().padRight(-700);
        table.row();
        table.add(pieceLabel2).expandX().padRight(-700);
        table.row();
        table.add(playerMoneyLabel2).expandX().padRight(-700);

        //add table to the stage
        stage.addActor(table);


    }


    public void updatePlayer1(Integer score) {

        setMoney1(score);
        playerMoneyLabel1.setText(String.format("%04d", score));
    }

    public void updatePlayer2(Integer score) {

        setMoney2(score);
        playerMoneyLabel2.setText(String.format("%04d", score));
    }


    public void dispose() {
        stage.dispose();
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public void setMoney1(Integer money1) {
        this.money1 = money1;
    }

    public void setMoney2(Integer money2) {
        this.money2 = money2;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public void setPiece1(String piece1) {
        this.piece1 = piece1;
    }

    public void setPiece2(String piece2) {
        this.piece2 = piece2;
    }

    public void setPlayerNameLabel1(Label playerNameLabel1) {
        this.playerNameLabel1 = playerNameLabel1;
    }

    public void setPlayerNameLabel2(Label playerNameLabel2) {
        this.playerNameLabel2 = playerNameLabel2;
    }

    public void setPlayerMoneyLabel1(Label playerMoneyLabel1) {
        this.playerMoneyLabel1 = playerMoneyLabel1;
    }

    public void setPlayerMoneyLabel2(Label playerMoneyLabel2) {
        this.playerMoneyLabel2 = playerMoneyLabel2;
    }

    public void setPieceLabel1(Label pieceLabel1) {
        this.pieceLabel1 = pieceLabel1;
    }

    public void setPieceLabel2(Label pieceLabel2) {
        this.pieceLabel2 = pieceLabel2;
    }
}