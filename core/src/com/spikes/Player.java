package com.spikes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {

	private Sprite sprite;
	private TextureAtlas atlas;
	private Preferences prefs;
	private float speed = 250;
	private float velocityY;
	private boolean facingRight = true;
	private int score = 0;
	private int bestScore;
	private int gamesPlayed;
	private Clock clock;
	private boolean dead;

	public Player(App app) {
		atlas = app.assets.get("birds.atlas", TextureAtlas.class);
		sprite = atlas.createSprite("bird-right");
		sprite.setCenter(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		this.prefs = app.prefs;
		bestScore = prefs.getInteger("bestScore", 0);
		gamesPlayed = prefs.getInteger("gamesPlayed", 0);
		clock = new Clock();
	}

	public int getScore() {
		return score;
	}

	public void setCenter(float x, float y) {
		sprite.setCenter(x, y);
	}

	public void setColor(float r, float g, float b, float a) {
		sprite.setColor(r, g, b, a);
	}

	public Rectangle getBoundingRectangle() {
		return sprite.getBoundingRectangle();
	}

	public void changeDirection() {
		facingRight = !facingRight;
	}

	public void bounce() {
		changeDirection();
		sprite.setRegion(atlas.findRegion("bird-" + (facingRight ? "right" : "left")));
		score++;
		speed++;
	}

	public void die() {
		prefs.putInteger("gamesPlayed", ++gamesPlayed);
		if (score > bestScore)
			prefs.putInteger("bestScore", bestScore = score);
		prefs.flush();
		sprite.setRegion(atlas.findRegion("bird-dead"));
		dead = true;
		speed = 500;
	}

	public boolean isFacingRight() {
		return facingRight;
	}

	public void jump() {
		velocityY = 420;
		clock.restart();
	}

	public void jumpDown() {
		velocityY = -300;
		clock.restart();
	}

	public int getBestScore() {
		return bestScore;
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public void restartPosition() {
		sprite.setCenter(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		clock.restart();
	}

	public void update(float dt) {
		velocityY -= 1200 * dt * (clock.getTimeInSeconds() * -0.25f + 1);
		sprite.translate(dt * speed * (facingRight ? 1 : -1), dt * velocityY);
		if (dead) sprite.rotate(500*dt);
	}

	public void idle(float dt) {
		sprite.translate(0, (float)Math.cos(clock.getTimeInSeconds()*5) * 1.6f);
	}

	public void draw(Batch batch) {
		sprite.draw(batch);
	}

	// Expects ShapeRenderer to be setup.
	public void drawDebug(ShapeRenderer sr) {
		Rectangle r = getBoundingRectangle();
		sr.rect(r.x, r.y, r.width, r.height);
	}

	public boolean outOfBounds(Map map) {
		Vector2 bounds = map.getHorizontalBounds();
		return bounds.x >= sprite.getX() || bounds.y <= sprite.getX() + sprite.getWidth();
	}

}
