package com.spikes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MenuScreen extends ScreenAdapter {

	private App app;
	private Map map;
	private Player player;
	private Stage stage;

	public MenuScreen(App app, Map map) {
		this.app = app;
		this.map = map;
		this.player = new Player(app);

		stage = new Stage(new ScreenViewport(), app.batch);
		Skin skin = app.assets.get("uiskin.json", Skin.class);

		// TODO: Enclose those in another class (DRY is broken)
		Label title = new Label("DON'T TOUCH\nTHE SPIKES", skin, "title");
		title.setColor(map.getForegroundColor());
		title.setAlignment(Align.center);
		title.setPosition(Gdx.graphics.getWidth()/2 - title.getWidth()/2, 540);

		Label protip = new Label("CLICK\nTO JUMP", skin, "small");
		protip.setColor(1, 0.2f, 0.2f, 1);
		protip.setAlignment(Align.center);
		protip.setPosition(Gdx.graphics.getWidth()/2 - protip.getWidth()/2, 400);

		Label bestScore = new Label("BEST SCORE: " + player.getBestScore(), skin, "small");
		bestScore.setColor(map.getForegroundColor());
		bestScore.setAlignment(Align.center);
		bestScore.setPosition(Gdx.graphics.getWidth()/2 - bestScore.getWidth()/2, 90);

		Label gamesPlayed = new Label("GAMES PLAYED: " + player.getGamesPlayed(), skin, "small");
		gamesPlayed.setColor(map.getForegroundColor());
		gamesPlayed.setAlignment(Align.center);
		gamesPlayed.setPosition(Gdx.graphics.getWidth()/2 - gamesPlayed.getWidth()/2, 60);

		stage.addActor(title);
		stage.addActor(protip);
		stage.addActor(bestScore);
		stage.addActor(gamesPlayed);

		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				if (button == Input.Buttons.LEFT)
					startGame();
				return false;
			}
			@Override
			public boolean keyDown(int keycode) {
				if (keycode == Input.Keys.SPACE)
					startGame();
				return false;
			}
		});
	}

	private void startGame() {
		app.mainScreen.interpolateScreen(new GameScreen(app, map, player), 0.6f);
		stage.addAction(Actions.fadeOut(0.6f, Interpolation.linear));
		player.jump();
	}

	@Override
	public void resume() {
		player.restartPosition();
	}

	private void update(float dt) {
		player.idle(dt);
		stage.act(dt);
	}

	@Override
	public void render(float delta) {
		update(delta);

		stage.draw();

		// TODO: Should Player extend Actor and be a part of a Stage?
		app.batch.begin();
		player.draw(app.batch);
		app.batch.end();
	}
}
