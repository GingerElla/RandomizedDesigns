package components;

import java.io.PrintWriter;

import processing.core.*;

public class Controller {
	
	private static final char BACKSPACE = processing.core.PConstants.BACKSPACE;
	private static final char ESC = processing.core.PConstants.ESC;
	
	private PApplet p;
	private Design d;
	private PrintWriter writer;
	private int startTime = 0;
	private String[] introMessage;
	
	public Controller() {
		introMessage = p.loadStrings("introMessage.txt");
	}
	
	public Controller(Design d, PrintWriter writer) {
		this.d = d;
		introMessage = p.loadStrings("introMessage.txt");
	}

	@SuppressWarnings("static-access")
	private String currentTime() {
		String time;
		int[] components = {p.millis(), p.second(), p.minute(),
				p.hour(), p.day(), p.month(), p.year()};

		time = p.join(p.nf(components, 2), "-");
		return time;
	}

	@SuppressWarnings("static-access")
	public void printIntroMessage() {
		printDivider();
		for (String line : introMessage) {
			p.println(line);
		}
		printDivider();
	}

	@SuppressWarnings("static-access")
	public void printDivider() {
		p.println("\n==========================================="
				+ "===========================================\n");
	}

	@SuppressWarnings("static-access")
	public void keyPressed() {
		
		switch (p.key) {
		case ' ' :
			String filename = "SimpleRipples" + d.iteration + "-" + currentTime() + ".png";
			p.save("pics/"+filename);
			writer.println(filename);
			p.println("You saved a file! This filename is:");
			p.println(filename);
			printDivider();
			break;
		case BACKSPACE :
			p.fill(p.random(255), p.random(255), p.random(255));
			d.clearDirection = (int) p.random(3);
			d.makeRipple = false;
			d.iteration = 0;
			break;
		case 'i' :
			writer.flush();
			String[] entries = p.loadStrings("index.txt");
			p.println("Here's a list of all the images you've saved!");
			for (String entry : entries) {
				p.println(entry);
			}
			printDivider();
			break;
		case 'a' :
			d.auto = !(d.auto);
			if (d.auto) {
				d.x = (int) p.random(p.width);
				d.y = (int) p.random(p.height);
				d.limiter  = p.random(2);
			}
			break;
		case 'q' : 
			d.randomShapes = true;
			break;
		case 'c' :
			d.shape = 'c';
			break;
		case 'r' :
			d.shape = 'r';
			break;
		case 't' :
			d.shape = 't';
			break;
		case 'h' :
			printIntroMessage();
			break;
		case ESC : 
			writer.flush();
			writer.close();
			p.exit();
			break;
		}
	}

	public void mousePressed() {
		startTime = p.millis();
	}

	public void mouseReleased() {
		d.limiter = (p.millis() - startTime) / 500;
		d.x = p.pmouseX;
		d.y = p.pmouseY;
		d.makeRipple = true;
		d.clearDirection = -1;
	}
}
