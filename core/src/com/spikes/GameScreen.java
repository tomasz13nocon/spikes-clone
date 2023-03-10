package com.spikes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends ScreenAdapter {

	private App app;
	private Map map;
	private Player player;
	private BitmapFont scoreFont;
	private Text scoreText;
	private List<Color> foregroundColors = new ArrayList<>();
	private List<Color> backgroundColors = new ArrayList<>();
	private Clock clock = new Clock();
	private boolean gameIsOver = false;
	private boolean interpolating = false; // TODO: Make that into one var (see player.isDead())

	public GameScreen(App app, Map map, Player player) {
		this.app = app;
		this.map = map;
		this.player = player;
		scoreFont = app.assets.get("fonts/ccaps.fnt", BitmapFont.class);
		scoreFont.setColor(map.getBackgroundColor());
		scoreText = new Text(scoreFont, "0");
		scoreText.setCenter(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		setColors();

		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean keyDown(int keycode) {
				if (keycode == Input.Keys.ESCAPE)
					Gdx.app.exit();
				else if (keycode == Input.Keys.SPACE)
					if (!gameIsOver)
						GameScreen.this.player.jump();
				return false;
			}
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				if (button == Input.Buttons.LEFT)
					if (!gameIsOver)
						GameScreen.this.player.jump();
				return false;
			}
		});
	}

	private void gameOver() {
		player.die();
		clock.restart();
		gameIsOver = true;
	}

	public void update(float dt) {
		// Other things updates
		player.update(dt);
		map.update(dt);

		// Player-spike collision (for top and down spikes)
		if (map.bottomSpikesIntersect(player.getBoundingRectangle())) {
			if (!gameIsOver && !interpolating) gameOver();
			player.jump();
		}
		else if (map.topSpikesIntersect(player.getBoundingRectangle())) {
			if (!gameIsOver && !interpolating) gameOver();
			player.jumpDown();
		}

		if (gameIsOver) {
			if (clock.getTimeInSeconds() > 1) {
				app.mainScreen.interpolateScreen(new GameOverScreen(app, map, player), 0.8f);
				gameIsOver = false;
				interpolating = true;
			}
		}

		if (interpolating)
			player.setColor(1, 1, 1, Math.max(2 - ((float)clock.getTimeInSeconds() * 1.25f), 0));

		// Player reached the bounds
		if (player.outOfBounds(map)) {
			if (!gameIsOver && !interpolating && map.horizontalSpikesIntersect(player.getBoundingRectangle())) {
				gameOver();
				player.changeDirection();
			}
			else if (!gameIsOver && !interpolating) {
				player.bounce();
				scoreText.setText(Integer.toString(player.getScore()));
				scoreText.setCenter(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
				if (player.getScore() % 5 == 0) {
					int colorIndex = player.getScore() / 5;
					if (foregroundColors.size() > colorIndex)
						map.animateColors(foregroundColors.get(colorIndex), backgroundColors.get(colorIndex));
				}

				int numSpikes = (int) (2.5f * ((float) Math.log(5.0f * player.getScore() + 4.0f) / (float) Math.log(5)) - 1.0f);
				if (player.isFacingRight())
					map.slideSpikes(numSpikes, true);
				else
					map.slideSpikes(numSpikes, false);
			} else { // gameIsOver == true
				player.changeDirection();
			}
		}

		scoreFont.setColor(map.getBackgroundColor());
	}

	@Override
	public void render(float delta) {
		update(1.0f/60.0f);

		app.sr.begin(ShapeRenderer.ShapeType.Filled);
		if (app.debug) {
			app.sr.setAutoShapeType(true);
			app.sr.set(ShapeRenderer.ShapeType.Line);
			app.sr.setColor(0, 0, 0, 1);
			player.drawDebug(app.sr);
		}
		app.sr.end();

		app.batch.begin();
		scoreText.draw(app.batch);
		player.draw(app.batch);
		app.batch.end();
	}

	private void setColors() {
		foregroundColors.add(new Color(0.5f, 0.5f, 0.5f, 1)); // 0
		backgroundColors.add(new Color(0.93f, 0.92f, 0.93f, 1));
		foregroundColors.add(new Color(0.39f, 0.46f, 0.52f, 1)); // 5
		backgroundColors.add(new Color(0.87f, 0.92f, 0.97f, 1));
		foregroundColors.add(new Color(0.52f, 0.41f, 0.39f, 1)); // 10
		backgroundColors.add(new Color(0.97f, 0.92f, 0.90f, 1));
		foregroundColors.add(new Color(0.45f, 0.51f, 0.39f, 1)); // 15
		backgroundColors.add(new Color(0.94f, 0.95f, 0.87f, 1));
		foregroundColors.add(new Color(0.42f, 0.38f, 0.52f, 1)); // 20
		backgroundColors.add(new Color(0.90f, 0.89f, 0.97f, 1));
		foregroundColors.add(new Color(1.0f, 1.0f, 1.0f, 1)); // 25
		backgroundColors.add(new Color(0.45f, 0.44f, 0.45f, 1));
		foregroundColors.add(new Color(0.0f, 0.75f, 0.94f, 1)); // 30
		backgroundColors.add(new Color(0.0f, 0.41f, 0.52f, 1));
		foregroundColors.add(new Color(0.52f, 0.94f, 0.0f, 1)); // 35
		backgroundColors.add(new Color(0.16f, 0.53f, 0.0f, 1));
		foregroundColors.add(new Color(0.0f, 0.41f, 0.94f, 1)); // 40
		backgroundColors.add(new Color(0.0f, 0.14f, 0.52f, 1));
//		foregroundColors.add(new Color(0.f, 0.f, 0.f, 1));
//		backgroundColors.add(new Color(0.f, 0.f, 0.f, 1));
		for (int i = 0; i < 20; i++) {
			foregroundColors.add(new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), 1));
			backgroundColors.add(new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), 1));
		}
	}

}
