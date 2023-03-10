package com.spikes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MainScreen extends ScreenAdapter {

	private App app;
	private Screen screen;
	private Screen prevScreen;
	private Map map;
	private Clock clock = new Clock();
	private float duration;

	public MainScreen(App app) {
		this.app = app;
		this.map = new Map();

		setScreen(new MenuScreen(app, map));
	}

	@Override
	public void dispose () {
		if (screen != null) screen.hide();
	}

	@Override
	public void pause () {
		if (screen != null) screen.pause();
	}

	@Override
	public void resume () {
		if (screen != null) screen.resume();
	}

	@Override
	public void resize (int width, int height) {
		if (screen != null) screen.resize(width, height);
	}

	public void setScreen (Screen screen) {
		if (this.screen != null) this.screen.hide();
		this.screen = screen;
		if (this.screen != null) {
			this.screen.show();
			this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
	}

	// Only thing this method does is rendering both current and given screens for a given duration.
	// If you want to e.g. alpha interpolate those screens, you have to do it in your Screen classes.
	public void interpolateScreen(Screen screen, float duration) {
		prevScreen = this.screen;
		this.screen = screen;
		this.duration = duration;
		clock.restart();
	}

	public Screen getScreen () {
		return screen;
	}

	@Override
	public void render(float delta) {
		app.sr.begin(ShapeRenderer.ShapeType.Filled);
		map.draw(app.sr);
		if (app.debug) {
			app.sr.setAutoShapeType(true);
			app.sr.set(ShapeRenderer.ShapeType.Line);
			app.sr.setColor(0, 0, 0, 1);
			map.drawDebug(app.sr);
		}
		app.sr.end();

		if (prevScreen != null) {
			prevScreen.render(delta);
			if (clock.getTimeInSeconds() > duration) prevScreen = null;
		}
		if (screen != null) screen.render(delta);
	}
}
