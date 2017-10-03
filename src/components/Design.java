package components;

import processing.core.PApplet;

public class Design {
	private PApplet p;
	
	int numBands = 0;
	int radius = 10;
	int size = 10;
	int x = p.width / 2;
	int y = p.height / 2;
	float limiter;
	float startTime = 0;
	int iteration = 0;
	char shape = 'c'; // 'c' for circle, 'r' for rectangle, 't' for triangle
	boolean makeRipple = false;
	boolean auto = true;
	boolean randomShapes = true;
	boolean clear = false;
	
	public Design() { }
	
	public Design(boolean auto, int radius, boolean randomShapes) {
		this(auto, randomShapes);
		this.radius = radius;
	}
	
	public Design(boolean auto) {
		this.auto = auto;
	}
	
	public Design(int radius) {
		this.radius = radius;
	}
	
	public Design(boolean auto, int radius) {
		this(auto);
		this.radius = radius;
	}
	
	public Design(boolean auto, boolean randomShapes) {
		this(auto);
		this.randomShapes = randomShapes;
	}
	
	public Design(int radius, boolean randomShapes) {
		this(radius);
		this.randomShapes = randomShapes;
	}
}
