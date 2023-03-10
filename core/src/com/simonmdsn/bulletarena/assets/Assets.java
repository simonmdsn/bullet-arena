package com.simonmdsn.bulletarena.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.maps.tiled.TideMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.io.File;

public class Assets {

    private final AssetManager assetManager;
    private final static String assetsFolder = Gdx.files.internal("").file().getAbsolutePath();
    private final BitmapFont bitmapFont = new BitmapFont();

    public Assets() {
        this.assetManager = new AssetManager();
        loadAssets();
        assetManager.finishLoading();
    }

    public void loadAssets() {
        iterateFiles(new File(assetsFolder));
    }

    private void iterateFiles(File file) {
        if (file.isDirectory()) {
            for (File listFile : file.listFiles()) {
                iterateFiles(listFile);
            }
        } else {
            System.out.println("Loading asset: " + file.getAbsolutePath());
            String pathToFile = file.getPath().substring(assetsFolder.length() +1);
            String[] dots = pathToFile.split("\\.");
            String fileExtension = dots[dots.length - 1];
            if ("jpg".equals(fileExtension) || "png".equals(fileExtension)) {
                assetManager.load(pathToFile, Texture.class);
            } else if ("tmx".equals(fileExtension) || "tsx".equals(fileExtension)) {
                if (fileExtension.equals("tsx")) return;
                if (assetManager.getLoader(TiledMap.class) == null) {
                    assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
                }
                assetManager.load(pathToFile, TiledMap.class);
            } else if ("party".equals(fileExtension)) {
                assetManager.load(pathToFile, ParticleEffect.class);
            } else if ("ttf".equals(fileExtension)) {
            } else {
                System.out.println("Asset Manager does not support file extension " + fileExtension);
            }
        }
    }

    public <T> T get(String filename, Class<T> type) {
        return assetManager.get(filename, type);
    }

    public AssetManager assetManager() {
        return assetManager;
    }

    public Texture superHero() {
        return assetManager.get("super-hero.png", Texture.class);
    }

    public Texture redMonster1() {
        return assetManager.get("red-monster1.png", Texture.class);
    }

    public Texture redMonster2() {
        return assetManager.get("red-monster2.png", Texture.class);
    }

    public Texture slimeMonster() {
        return assetManager.get("slimeboi.png", Texture.class);
    }

    public Texture slimeBullet() {
        return assetManager.get("slime-bullet.png", Texture.class);
    }

    public Texture defaultBullet() {
        return assetManager.get("base-bullet.png", Texture.class);
    }
    public Texture laserBullet() {
        return assetManager.get("laser.png", Texture.class);
    }

    public Texture arrow() {
        return assetManager.get("arrow.png", Texture.class);
    }

    public Texture knight() {
        return assetManager.get("knight.png", Texture.class);
    }


    public BitmapFont bitmapFont() {
        return bitmapFont;
    }
}
