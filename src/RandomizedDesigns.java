import java.io.PrintWriter;

import components.Controller;
import components.Design;
import processing.core.*;

public class RandomizedDesigns extends PApplet {

	public static void main(String[] args) {
		//uncommenting present will let it run in full screen
		//form of the second string should be thisPackageName.thisClassName
		//if package is defaults, just need ClassName
		PApplet.main(new String[] { "--present", "RandomizedDesigns" });
	}

	PrintWriter writer;
	Controller c;
	Design d;
	
	public void settings() {
		fullScreen();
	}

	public void setup() {
		frameRate(60);
		background(0);
		noStroke();
		writer  = createWriter("index.txt");
		d = new Design();
		c = new Controller(d, writer);
		c.printIntroMessage();
	}

	public void draw() {

		d.resetTriggersWhen(100);

		d.drawFrame();

		if (d.clearIsOn()) { d.clearScreen(); }

		d.createNewBandWhen(100);
	} 
}
