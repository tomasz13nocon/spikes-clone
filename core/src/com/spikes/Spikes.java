package com.spikes;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.*;

public class Spikes {

	private List<Spike> spikes = new ArrayList<>();

	public Spikes() {
		for (int i=0; i<7; i++) {
			spikes.add(new Spike(Spike.Facing.Up, i));
			spikes.add(new Spike(Spike.Facing.Down, i));
		}
		for (int i=0; i<11; i++)
			spikes.add(new Spike(Spike.Facing.Left, i));
		for (int i=0; i<11; i++)
			spikes.add(new Spike(Spike.Facing.Right, i));
	}

	public boolean verticalOnesIntersect(Rectangle other) {
		Iterator<Spike> it = spikes.iterator();
		Spike spike;
		for (int i=0; i <14; i++) {
			try {
				spike = it.next();
			} catch (NoSuchElementException e) {
				throw new IllegalStateException("There is less spikes than expected.");
			}
			if (spike.getBoundingRectangle().overlaps(other))
				return true;
		}
		return false;
	}

	// Should this be splitted into right and left? I think not, it would be an overkill.
	public boolean horizontalOnesIntersect(Rectangle other) {
		Spike spike;
		for (ListIterator<Spike> it = spikes.listIterator(14); it.hasNext();) {
			spike = it.next();
			if (spike.getBoundingRectangle().overlaps(other))
				return true;
		}
		return false;
	}

	public void slideSpikes(int howMany, boolean rightOnes) {
		if (rightOnes) {
			for (int i = 25; i < 36; i++)
				spikes.get(i).show(false);
			int[] indices = randomNums(howMany);
			for (int i = 0; i < howMany; i++)
				spikes.get(14 + indices[i]).show(true);
		} else {
			for (int i = 14; i < 25; i++)
				spikes.get(i).show(false);
			int[] indices = randomNums(howMany);
			for (int i = 0; i < howMany; i++)
				spikes.get(25 + indices[i]).show(true);
		}
	}

	// Random unique numbers from 0 to 10
	private int[] randomNums(int howMany) {
		int[] result = new int[howMany];
		for (int i=0; i<howMany; i++) {
			int rand;
			do {
				rand = (int) (Math.random() * 11);
			} while (checkForRepeats(result, rand));
			result[i] = rand;
		}
		return result;
	}

	// Returns false if num isn't inside array. True otherwise.
	private boolean checkForRepeats(int[] array, int num) {
		for (int i : array)
			if (i == num) return true;
		return false;
	}

	public void update(float dt) {
		for (Spike spike : spikes)
			spike.update(dt);
	}

	public void draw(ShapeRenderer sr) {
		for (Spike spike : spikes)
			spike.draw(sr);
	}

	// Expects ShapeRenderer to be setup.
	public void drawDebug(ShapeRenderer sr) {
		Rectangle r;
		for (Spike spike : spikes) {
			r = spike.getBoundingRectangle();
			sr.rect(r.x, r.y, r.width, r.height);
		}
	}

}
