package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.hud.Player;
import com.mygdx.game.screen01.ScreenMonopoly;
import com.mygdx.game.screen01.Settings;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

public class GameManager {

    private Preferences PREFS;
    private final String MUSIC_VOLUME = "musicVolume";
    private final String SOUND_VOLUME = "soundVolume";
    private final String MUSIC_ON = "musicOn";
    private final String MUSIC_LOOP = "musicLoop";
    ;
    public String userID;

    public static final GameManager INSTANCE = new GameManager();


    public void save() {
        setMusicVolume();
        setMusic();
        setMusicLoop();

    }

    public void load() {
        json = new Json();

        getMusicVolume();
        getMusic();
        getMusicLoop();

    }

    private GameManager() {
        json = new Json();
        PREFS = Gdx.app.getPreferences("Monopoly");
        if (!PREFS.contains(MUSIC_VOLUME)) {
            PREFS.putFloat(MUSIC_VOLUME, 0.3f).flush();
        }
      /*  if (!PREFS.contains(SOUND_VOLUME)) {
            PREFS.putFloat(SOUND_VOLUME, 0.3f).flush();
        }*/

        userID = "minela";
        load();
    }


    public void setMusicVolume() {
        PREFS.putFloat(MUSIC_VOLUME, Settings.MUSIC.getVolume()).flush();
    }

    public float getMusicVolume() {
        return PREFS.getFloat(MUSIC_VOLUME);
    }

    public void setMusic() {
        PREFS.putBoolean(MUSIC_ON, Settings.MUSIC.isPlaying()).flush();
    }

    public boolean getMusic() {
        return PREFS.getBoolean(MUSIC_ON);
    }

    public void setMusicLoop() {
        PREFS.putBoolean(MUSIC_LOOP, Settings.MUSIC.isPlaying()).flush();
    }

    public boolean getMusicLoop() {
        return PREFS.getBoolean(MUSIC_LOOP);
    }

    /*public void setSoundVolume() {
        PREFS.putFloat(SOUND_VOLUME,sound.getSound() ).flush();
    }*/
}

