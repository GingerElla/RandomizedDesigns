import java.io.PrintWriter;

import processing.core.*;

public class RandomizedDesigns extends PApplet {

	public static void main(String[] args) {
		//uncommenting present will let it run in full screen
		//form of the second string should be thisPackageName.thisClassName
		//if package is defaults, just need ClassName
		PApplet.main(new String[] { "--present", "RandomizedDesigns" });
	}

	int numBands = 0;
	int myRadius = 10;
	int myX = width / 2;
	int myY = height / 2;
	float limiter;
	float startTime = 0;
	int iter = 0;
	char shape = 'c'; // 'c' for circle, 'r' for rectangle, 't' for triangle
	boolean makeRipple = false;
	boolean auto = true;
	boolean randomShapes = true;
	int clear = -1;
	int mySize = 10;
	String[] introMessage;
	PrintWriter writer;

	public void settings() {
		fullScreen();
	}

	public void setup() {
		frameRate(60);
		background(0);
		noStroke();
		writer  = createWriter("index.txt");
		introMessage = loadStrings("introMessage.txt");
		printIntroMessage();
	}

	public void draw() {

		// reset trigger
		if (numBands * 100 > height * limiter) {
			numBands = 0;
			makeRipple = false;
			if (auto) {
				myX = (int) random(width);
				myY = (int) random(height);
				limiter = random(2);
				randomShape();
				randomClear();
			}
			iter++;
		}

		// draw and augment diameter
		if (makeRipple || auto && (clear < 0)) {
			randomNewRipple(myX, myY, myRadius);
			myRadius += 10;
		}

		if (clear > 0) {
			clearFromDirection(clear, mySize);
			mySize += 10; 
			if (hasClearedScreen(mySize, clear)) {
				clear = -1;
				mySize = 0;
			}
		}

		// trigger for new, smaller circle
		if (myRadius > (height * limiter - numBands * 100)) {
			if (shape == 't') { myY += 15; }
			myRadius = 0;
			numBands++;
		}
	} 

	void randomNewRipple(int x, int y, int diameter) {  
		if (diameter == 0) {
			fill(random(255), random(255), random(255));
		}

		float radius = diameter/2;

		switch (shape) {
		case 'r' :
			rectMode(2);
			rect(x, y, radius, radius);
			break;
		case 't' :
			triangle(x - radius, y + radius, 
					x, y - radius, 
					x + radius, y + radius);
			break;
		default :
			ellipse(x, y, diameter, diameter);
			break;
		}
	}

	void randomShape() {
		// pick a new random shape
		if (randomShapes) {
			int choice = (int) random(1000) % 3;
			switch(choice) {
			case 0 : 
				shape = 'c';
				break;
			case 1:
				shape = 'r';
				break;
			case 2 :
				shape = 't';
				break;
			}
		}
	}

	void randomClear() {
		int chance = (int) random(100) % 10;
		if (chance == 0) { clear = 1; }
	}

	void clearFromDirection(int direction, int size) {
		switch (direction) {
		case 1 : rect(0, 0, width, size);
		break;
		case 2 : rect(0, 0, size, height);
		break;
		case 3: rect(0, height - size, width, size);
		break;
		case 4: rect(width - size, 0, size, height);
		break;
		}
	}

	private boolean hasClearedScreen(int size, int direction) {
		boolean isVertical = (direction % 2) == 0;
		if (isVertical) {
			return size > height;
		} else {
			return size > width;
		}
	}

	private String currentTime() {
		String time;
		int[] components = {millis(), second(), minute(),
				hour(), day(), month(), year()};

		time = join(nf(components, 2), "-");
		return time;
	}

	private void printIntroMessage() {
		printDivider();
		for (String line : introMessage) {
			println(line);
		}
		printDivider();
	}

	private void printDivider() {
		println("\n==========================================="
				+ "===========================================\n");
	}

	public void keyPressed() {
		if (key == ' ') {
			String filename = "SimpleRipples" + iter + "-" + currentTime() + ".png";
			save("pics/"+filename);
			writer.println(filename);
			println("You saved a file! This filename is:");
			println(filename);
			printDivider();
		}

		if (key == BACKSPACE) {
			fill(random(255), random(255), random(255));
			clear = (int) random(3);
			makeRipple = false;
			iter = 0;
		}

		if (key == 'i') {
			writer.flush();
			String[] entries = loadStrings("index.txt");
			println("Here's a list of all the images you've saved!");
			for (String entry : entries) {
				println(entry);
			}
			printDivider();
		}

		if (key == 'a') {
			auto = !(auto);
			if (auto) {
				myX = (int) random(width);
				myY = (int) random(height);
				limiter  = random(2);
			}
		}

		if (key == 'q') {
			randomShapes = true;
		}

		if (key == 'c') {
			shape = 'c';
		}

		if (key == 'r') {
			shape = 'r';
		}

		if (key == 't') {
			shape = 't';
		}

		if ((key == ENTER) || (key == 'h')) {
			printIntroMessage();
		}

		if (key == 'q' || key == ESC) {
			writer.flush();
			writer.close();
			exit();
		}
	}

	public void mousePressed() {
		startTime = millis();
	}

	public void mouseReleased() {
		limiter = (millis() - startTime) / 500;
		myX = pmouseX;
		myY = pmouseY;
		makeRipple = true;
		clear = -1;
	}
}
