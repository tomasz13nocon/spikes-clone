package com.spikes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameOverScreen extends ScreenAdapter {

	private App app;
	private Map map;
	private Stage stage;

	public GameOverScreen(App app, Map map, Player player) {
		this.app = app;
		this.map = map;
		stage = new Stage(new ScreenViewport(), app.batch);

		Skin skin = app.assets.get("uiskin.json", Skin.class);

		Table table = new Table(skin);
		table.setFillParent(true);
		stage.addActor(table);

		Label title = new Label("DON'T TOUCH\nTHE SPIKES", skin, "title");
		title.setColor(map.getForegroundColor());
		title.setAlignment(Align.center);
		title.setPosition(Gdx.graphics.getWidth()/2 - title.getWidth()/2, 540);

		Button scoreButton = new Button(skin);
		scoreButton.add(new Label(Integer.toString(player.getScore()), skin, "title"));
		scoreButton.row();
		scoreButton.add(new Label("POINTS", skin, "small"));

		final TextButton replayButton = new TextButton("REPLAY", skin);

		Label bestScore = new Label("BEST SCORE: " + player.getBestScore(), skin, "small");
		bestScore.setColor(map.getForegroundColor());
		bestScore.setAlignment(Align.center);
		bestScore.setPosition(Gdx.graphics.getWidth()/2 - bestScore.getWidth()/2, 90);

		Label gamesPlayed = new Label("GAMES PLAYED: " + player.getGamesPlayed(), skin, "small");
		gamesPlayed.setColor(map.getForegroundColor());
		gamesPlayed.setAlignment(Align.center);
		gamesPlayed.setPosition(Gdx.graphics.getWidth()/2 - gamesPlayed.getWidth()/2, 60);

		stage.addActor(title);
		table.defaults().minWidth(360).space(6);
		table.add(scoreButton);
		table.row();
		table.add(replayButton);
		stage.addActor(bestScore);
		stage.addActor(gamesPlayed);

		stage.addAction(sequence(fadeOut(0), fadeIn(0.8f, Interpolation.pow4Out)));

		Gdx.input.setInputProcessor(stage);
		stage.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (actor == replayButton)
					GameOverScreen.this.app.showMenu();
			}
		});
	}

	@Override
	public void render(float delta) {
		stage.act(delta);
		stage.draw();
	}
}
