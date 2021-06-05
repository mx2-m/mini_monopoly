package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.hud.Player;
import com.mygdx.game.screen01.Board;
import com.mygdx.game.screen01.GameOver;
import com.mygdx.game.screen01.MainMenu;
import com.mygdx.game.screen01.ScreenMonopoly;
import com.mygdx.game.screen01.Settings;
import com.mygdx.game.screen01.YouWon;

import java.util.ArrayList;


public class Monopoly extends Game {

    // private ScreenMonopoly screen;

    public Monopoly() {
    }

    @Override
    public void create() {
        this.setScreen(new MainMenu(this));
    }


    @Override
    public void dispose() {

        //  screen.dispose();
    }


    public enum Screens {
        MAIN, GAME, SETTINGS, GAME_OVER, WON
    }

    public void gotoScreen(Screens screenType) {

        switch (screenType) {
            case MAIN:
                super.setScreen(new MainMenu(this));
                break;
            case GAME_OVER:
                super.setScreen(new GameOver(this));
                break;
            case WON:
                super.setScreen(new YouWon(this));
                break;

            case GAME:
                super.setScreen(new Board(this));
                break;
            case SETTINGS:
                super.setScreen(new Settings(this));
                break;
            default:
        }
        System.gc();
    }


}
