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
		writer  = createWriter("index.txt");
		d = new Design(this);
		c = new Controller(this, d, writer);
		c.printIntroMessage();
		background(0);
	}

	public void draw() {
		c.draw();
	} 
	
	public void keyPressed() {
		c.keyPressed();
	}
	
	public void mousePressed() {
		c.mousePressed();
	}
	
	public void mouseReleased() {
		c.mouseReleased();
	}
}
