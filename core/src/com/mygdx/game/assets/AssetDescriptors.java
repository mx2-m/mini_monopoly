package com.mygdx.game.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetDescriptors {

    public static final AssetDescriptor<TextureAtlas> GAME_PLAY =
            new AssetDescriptor<TextureAtlas>(AssetPaths.GAME_PLAY, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> MAIN =
            new AssetDescriptor<TextureAtlas>(AssetPaths.MAIN, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> SANTA_ANIMATION =
            new AssetDescriptor<TextureAtlas>(AssetPaths.SANTA_ANIMATION, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> SNOWMAN_ANIMATION =
            new AssetDescriptor<TextureAtlas>(AssetPaths.SNOWMAN_ANIMATION, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> GAME_OVER =
            new AssetDescriptor<TextureAtlas>(AssetPaths.GAME_OVER, TextureAtlas.class);

    public static final AssetDescriptor<Sound> SOUND =
            new AssetDescriptor<Sound>(AssetPaths.SOUND, Sound.class);

    public static final AssetDescriptor<TextureAtlas> SETTINGS =
            new AssetDescriptor<TextureAtlas>(AssetPaths.SETTINGS, TextureAtlas.class);


    private AssetDescriptors() {
    }
}
