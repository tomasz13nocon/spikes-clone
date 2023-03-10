package com.spikes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class CircleShape extends Circle {

	private Color color = new Color(1, 0, 1, 1);

	public CircleShape() {
		super();
	}

	public CircleShape(float x, float y, float radius) {
		super(x, y, radius);
	}

	public CircleShape(Vector2 position, float radius) {
		super(position, radius);
	}

	public CircleShape(Circle circle) {
		super(circle);
	}

	public CircleShape(CircleShape circle) {
		super(circle);
		color.set(circle.color);
	}

	public CircleShape(Vector2 center, Vector2 edge) {
		super(center, edge);
	}

	public void setColor(Color color) {
		this.color.set(color);
	}

	public void setColor(float r, float g, float b, float a) {
		color.set(r, g, b, a);
	}

	public Color getColor() {
		return color;
	}

	public void draw(ShapeRenderer sr) {
		sr.setColor(color);
		sr.circle(x, y, radius, 48);
	}
}
