package com.spikes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;

public class Map {

	private Bounds bounds;
	private RectangleShape bg;
	private CircleShape circle;
	private Spikes spikes;
	private boolean animatingColors = false;
	private int colorAnimationStep;
	private Vector3 foregroundStep = new Vector3();
	private Vector3 backgroundStep = new Vector3();

	public Map() {
		bounds = new Bounds();
		bg = new RectangleShape(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		bg.setColor(0.93f, 0.92f, 0.93f, 1);
		circle = new CircleShape(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 120);
		circle.setColor(1, 1, 1, 1);
		spikes = new Spikes();
	}

	public Color getBackgroundColor() {
		return bg.getColor();
	}

	public Color getForegroundColor() {
		return bounds.getColor();
	}

	public Vector2 getHorizontalBounds() {
		return bounds.getHorizontalBounds();
	}

	public void animateColors(Color foregroundColor, Color backgroundColor) {
		float step = 1.0f/25.0f;
		foregroundStep.set(new Vector3(foregroundColor.r, foregroundColor.g, foregroundColor.b)
				.sub(bounds.getColor().r, bounds.getColor().g, bounds.getColor().b)
				.scl(step));
		backgroundStep.set(new Vector3(backgroundColor.r, backgroundColor.g, backgroundColor.b)
				.sub(bg.getColor().r, bg.getColor().g, bg.getColor().b)
				.scl(step));
		animatingColors = true;
	}

	public void slideSpikes(int howMany, boolean rightOnes) {
		spikes.slideSpikes(howMany, rightOnes);
	}

	public boolean horizontalSpikesIntersect(Rectangle rect) {
		return spikes.horizontalOnesIntersect(rect);
	}

	public boolean topSpikesIntersect(Rectangle rect) {
		return new Rectangle(0, bounds.getVerticalBounds().y - 24, 480, 24).overlaps(rect);
	}

	public boolean bottomSpikesIntersect(Rectangle rect) {
		return new Rectangle(0, bounds.getVerticalBounds().x, 480, 24).overlaps(rect);
	}

	public void update(float dt) {
		bounds.update(dt);
		spikes.update(dt);

		// Color change animation
		if (animatingColors) {
			bounds.getColor().add(foregroundStep.x, foregroundStep.y, foregroundStep.z, 0);
			bg.getColor().add(backgroundStep.x, backgroundStep.y, backgroundStep.z, 0);
			colorAnimationStep++;
			if (colorAnimationStep == 25) {
				animatingColors = false;
				colorAnimationStep = 0;
			}
		}
	}

	public void draw(ShapeRenderer sr) {
		bg.draw(sr);
		circle.draw(sr);
		bounds.draw(sr);
		spikes.draw(sr);
	}

	public void drawDebug(ShapeRenderer sr) {
		spikes.drawDebug(sr);
	}

}
