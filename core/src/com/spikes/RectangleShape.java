package com.spikes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class RectangleShape extends Rectangle {

	private Color color = new Color(1, 0, 1, 1);

	public RectangleShape() {
		super();
	}

	public RectangleShape(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	public RectangleShape(Rectangle rect) {
		super(rect);
	}

	public RectangleShape(RectangleShape rect) {
		super(rect);
		color.set(rect.color);
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
		sr.rect(x, y, width, height);
	}

}
