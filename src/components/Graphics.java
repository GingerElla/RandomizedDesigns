package components;

import processing.core.PApplet;

public class Graphics {
	private PApplet p;

	public char pickRandomShape() {
		// pick a new random shape
		int choice = (int) p.random(1000) % 3;
		switch(choice) {
		case 0 : 
			return 'c';
		case 1:
			return 'r';
		default :
			return 't';
		}
	}

	public int tossForRandomClear() {
		int chance = (int) p.random(100);
		if (chance % 10 == 0) { 
			return chance % 4;
		}
		return -1;
	}

	public void createRandomRipple(int x, int y, int diameter, char shape) {  
		if (diameter == 0) {
			p.fill(p.random(255), p.random(255), p.random(255));
		}

		float radius = diameter/2;

		switch (shape) {
		case 'r' :
			p.rectMode(2);
			p.rect(x, y, radius, radius);
			break;
		case 't' :
			p.triangle(x - radius, y + radius, 
					x, y - radius, 
					x + radius, y + radius);
			break;
		default :
			p.ellipse(x, y, diameter, diameter);
			break;
		}
	}

	public void clearFromDirection(int direction, int size) {
		switch (direction) {
		case 1 : 
			p.rect(0, 0, p.width, size);
			break;
		case 2 : 
			p.rect(0, 0, size, p.height);
			break;
		case 3: 
			p.rect(0, p.height - size, p.width, size);
			break;
		default: 
			p.rect(p.width - size, 0, size, p.height);
			break;
		}
	}

	public boolean hasClearedScreen(int direction, int size) {
		boolean isVertical = (direction % 2) == 0;
		if (isVertical) {
			return size > p.height;
		} else {
			return size > p.width;
		}
	}
}
