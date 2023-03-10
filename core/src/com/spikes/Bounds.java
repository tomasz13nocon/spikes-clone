package com.spikes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bounds {

	private Color color = new Color(0.5f, 0.5f, 0.5f, 1);
	private Rectangle innerBounds;

	public Bounds() {
		innerBounds = new Rectangle(15, 21, 450, 658);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color.set(color);
	}

	public Vector2 getHorizontalBounds() {
		return new Vector2(innerBounds.x, innerBounds.x + innerBounds.width);
	}

	// x is bottom, y is top
	public Vector2 getVerticalBounds() {
		return new Vector2(innerBounds.y, innerBounds.y + innerBounds.height);
	}

	public void update(float dt) {

	}

	// TODO: Draw it based on bounds fields.
	public void draw(ShapeRenderer sr) {
		sr.setColor(color);
		sr.rect(0, 679, 482, 21); // top
		sr.rect(0, 21, 15, 669); // left
		sr.rect(0, 0, 482, 21); // bottom
		sr.rect(465, 21, 17, 669); // right
	}
}
