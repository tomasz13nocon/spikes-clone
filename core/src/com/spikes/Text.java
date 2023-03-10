package com.spikes;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Text {

	private BitmapFont font;
	private String text;
	private float x, y, width, height;


	public Text(BitmapFont font) {
		this(font, 0, 0);
	}

	public Text(BitmapFont font, String text) {
		this(font, text, 0, 0);
	}

	public Text(BitmapFont font, float x, float y) {
		this.font = font;
		this.text = "";
		this.x = x;
		this.y = y;
	}

	public Text(BitmapFont font, String text, float x, float y) {
		this.font = font;
		this.text = text;
		GlyphLayout layout = new GlyphLayout(font, text);
		this.width = layout.width;
		this.height = layout.height;
		this.x = x;
		this.y = y;
	}

	public float getWidth() {
		return width;
	}
	public float getHeight() {
		return height;
	}

	public void setX(float x) {
		this.x = x;
	}
	public void setY(float y) {
		this.y = y;
	}
	public void setPosition(float x, float y) {
		setX(x);
		setY(y);
	}

	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public Vector2 getPosition() {
		return new Vector2(x, y);
	}

	public void setCenter(float x, float y) {
		setPosition(x - width/2, y + height/2);
	}

	public void setText(String text) {
		this.text = text;
		GlyphLayout layout = new GlyphLayout(font, text);
		this.width = layout.width;
		this.height = layout.height;
	}

	public String getText() {
		return text;
	}

	public void draw(Batch batch) {
		font.draw(batch, text, x, y);
	}

}
