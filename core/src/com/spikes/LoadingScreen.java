package com.spikes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class LoadingScreen extends ScreenAdapter {

	private App app;
	private Animation<TextureAtlas.AtlasRegion> loadingAnimation;
	private float stateTime;

	public LoadingScreen(App app) {
		this.app = app;
		loadingAnimation = new Animation<>(0.1f,
				app.assets.get("loading.atlas", TextureAtlas.class).getRegions(),
				Animation.PlayMode.LOOP);
	}


	@Override
	public void render(float delta) {
		if (app.assets.update()) {
			Gdx.gl.glClearColor(1, 1, 1, 1);
			app.showMenu();
		}
		stateTime += delta;
		app.batch.begin();
		app.batch.draw(loadingAnimation.getKeyFrame(stateTime),
				Gdx.graphics.getWidth()/2 - loadingAnimation.getKeyFrame(0).getRegionWidth()/2,
				Gdx.graphics.getHeight()/2 - loadingAnimation.getKeyFrame(0).getRegionHeight()/2);
		app.batch.end();
	}
}
