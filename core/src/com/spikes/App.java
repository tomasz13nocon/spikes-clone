package com.spikes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class App extends Game {

	boolean debug = false;
	SpriteBatch batch;
	AssetManager assets;
	ShapeRenderer sr;
	Preferences prefs;
	MainScreen mainScreen;
	
	@Override
	public void create () {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		batch = new SpriteBatch();
		assets = new AssetManager();
		sr = new ShapeRenderer();
		prefs = Gdx.app.getPreferences("spikes");

		//TexturePacker.process("res/birds", "../assets", "birds");

		assets.load("birds.atlas", TextureAtlas.class);
		assets.load("fonts/ccaps.fnt", BitmapFont.class);
		assets.load("loading.atlas", TextureAtlas.class);
		assets.load("uiskin.json", Skin.class);
		assets.finishLoadingAsset("loading.atlas");

		setScreen(new LoadingScreen(this));
	}

	public void showMenu() {
		mainScreen = new MainScreen(this);
		setScreen(mainScreen);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
		assets.dispose();
		sr.dispose();
	}

}
