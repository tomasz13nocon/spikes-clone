package com.spikes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public abstract class MyGame extends Game {

	protected Screen prevScreen;
	private Clock clock = new Clock();
	private float duration;

	// Only thing this method does is rendering both current and given screens for a given duration.
	// If you want to e.g. alpha interpolate those screens, you have to do it in your Screen classes.
	public void interpolateScreen(Screen screen, float duration) {
		prevScreen = this.screen;
		this.screen = screen;
		this.duration = duration;
		clock.restart();
	}

	@Override
	public void render() {
		if (prevScreen != null) {
			prevScreen.render(Gdx.graphics.getDeltaTime());
			if (clock.getTimeInSeconds() > duration) prevScreen = null;
		}
		super.render();
	}
}
