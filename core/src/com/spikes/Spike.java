package com.spikes;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Spike {

	private List<Vector2> vertices = new ArrayList<>();
	private boolean on = true; // TODO: Come up with a better name for this var >.<
	private boolean animating = false;
	private Facing facing;

	public enum Facing {
		Down,
		Up,
		Left,
		Right
	}

	public Spike(Facing facing, int index) {
		this.facing = facing;
		switch (facing) {
			case Down:
				vertices.add(new Vector2(35 + index * 60, 679));
				vertices.add(new Vector2(58 + index * 60, 651));
				vertices.add(new Vector2(81 + index * 60, 679));
				break;
			case Up:
				vertices.add(new Vector2(35 + index * 60, 21));
				vertices.add(new Vector2(58 + index * 60, 49));
				vertices.add(new Vector2(81 + index * 60, 21));
				break;
			case Left:
				vertices.add(new Vector2(493, 41 + index * 58));
				vertices.add(new Vector2(465, 64 + index * 58));
				vertices.add(new Vector2(493, 87 + index * 58));
				on = false;
				break;
			case Right:
				vertices.add(new Vector2(-13, 41 + index * 58));
				vertices.add(new Vector2(15, 64 + index * 58));
				vertices.add(new Vector2(-13, 87 + index * 58));
				on = false;
				break;
		}

	}

	public void show(boolean show) {
		if (on == show) return;
		on = show;
		animating = true;
	}

	public Rectangle getBoundingRectangle() {
		Rectangle r = new Rectangle();
		switch (facing) {
			case Down:
				r.x = vertices.get(0).x;
				r.y = vertices.get(1).y;
				r.width = vertices.get(2).x - r.x;
				r.height = vertices.get(2).y - r.y;
				break;
			case Up:
				r.x = vertices.get(0).x;
				r.y = vertices.get(0).y;
				r.width = vertices.get(2).x - r.x;
				r.height = vertices.get(1).y - r.y;
				break;
			case Left:
				r.x = vertices.get(1).x + 5;
				r.y = vertices.get(0).y + 5;
				r.width = vertices.get(0).x - r.x;
				r.height = vertices.get(2).y - r.y - 5;
				break;
			case Right:
				r.x = vertices.get(0).x;
				r.y = vertices.get(0).y + 5;
				r.width = vertices.get(1).x - r.x - 5;
				r.height = vertices.get(2).y - r.y - 5;
				break;
		}
		return r;
	}

	public void update(float dt) {
		if (animating) {
			if (facing == Facing.Right) {
				for (Vector2 vertex : vertices)
					vertex.add(on ? 80*dt : -80*dt, 0);
				if (on ? vertices.get(0).x >= 14 : vertices.get(0).x < -12)
					animating = false;
			} else if (facing == Facing.Left) {
				for (Vector2 vertex : vertices)
					vertex.add(on ? -80*dt : 80*dt, 0);
				if (on ? vertices.get(0).x <= 465 : vertices.get(0).x > 493)
					animating = false;
			}
		}
	}

	public void draw(ShapeRenderer sr) {
		sr.triangle(vertices.get(0).x, vertices.get(0).y,
				vertices.get(1).x, vertices.get(1).y,
				vertices.get(2).x, vertices.get(2).y);
	}
}
