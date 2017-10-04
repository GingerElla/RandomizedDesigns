package components;

import processing.core.PApplet;

public class Design {
	private PApplet p;
	private Graphics g;

	int numBands = 0;
	int radius = 10;
	int clearSize = 10;
	int x;
	int y;
	float limiter;
	int iteration = 0;
	char shape = 'c'; // 'c' for circle, 'r' for rectangle, 't' for triangle
	int clearDirection = -1;
	boolean makeRipple = false;
	boolean auto = true;
	boolean randomShapes = true;

	public Design(PApplet parent) {
		this.p = parent;
		this.g = new Graphics(parent);
		this.x = p.width / 2;
		this.y = p.height / 2;
	}

	public Design(PApplet parent, boolean auto) {
		this(parent);
		this.auto = auto;
	}
	
	public void draw() {
		drawFrame();	
	}

	public boolean clearIsOn() {
		return this.clearDirection >= 0;
	}

	public void resetTriggersWhen(int bandLimit) {
		if (numBands * bandLimit > p.height * limiter) {
			numBands = 0;
			makeRipple = false;
			if (auto) {
				this.x = (int) p.random(p.width);
				this.y = (int) p.random(p.height);
				limiter = p.random(2);
				this.clearDirection = g.tossForRandomClear();
			}
			if (randomShapes) {
				this.shape = g.pickRandomShape();
			}
			this.iteration++;
		}
	}

	public void drawFrame() {
		// draw shape and augment diameter
		if (this.makeRipple || (this.auto && (this.clearDirection < 0))) {
			g.createRandomRipple(this.x, this.y, this.radius, this.shape);
			this.radius += 10;
		}
	}

	public void createNewBandWhen(int bandLimiter) {
		if (this.radius > (p.height * this.limiter - this.numBands * bandLimiter)) {
			if (this.shape == 't') { this.y += 15; }
			this.radius = 0;
			numBands++;
		}
	}

	public void clearScreen() {
		g.clearFromDirection(clearDirection, this.clearSize);
		this.clearSize += 10; 
		if (g.hasClearedScreen(clearDirection, this.clearSize)) {
			clearDirection = -1;
			this.clearSize = 0;
		}
	}
}
