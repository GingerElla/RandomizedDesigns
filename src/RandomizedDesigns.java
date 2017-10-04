import java.io.PrintWriter;

import components.Design;
import processing.core.*;

public class RandomizedDesigns extends PApplet {

	public static void main(String[] args) {
		//uncommenting present will let it run in full screen
		//form of the second string should be thisPackageName.thisClassName
		//if package is defaults, just need ClassName
		PApplet.main(new String[] { "--present", "RandomizedDesigns" });
	}

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

		Design d = new Design();
		d.resetTriggersWhen(100);

		d.drawFrame();

		if (d.clearIsOn()) { d.clearScreen(); }

		// trigger for new, smaller circle
		d.createNewBandWhen(100);
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
