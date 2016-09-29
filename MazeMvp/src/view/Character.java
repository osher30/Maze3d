package view;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

import algorithms.mazeGenerators.Position;

public class Character {
	private Position pos;
	private Image img;
	
	public Character() {
		img = new Image(null, "lib/images/character.jpg");
	}

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}
	
	public void draw(int cellWidth, int cellHeight, GC gc) {
		gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, 
				cellWidth * pos.getX(), cellHeight * pos.getY(), cellWidth, cellHeight);
	}
	
	public void moveRight() {
		int right = pos.getX();
		right++;
	}
	
	public void moveLeft() {
		int left = pos.getX();
		left--;
	}
	
	public void moveForward() {
		int forward = pos.getY();
		forward++;
	}
	
	public void moveBackward() {
		int backward = pos.getY();
		backward--;
	}
	
	public void moveUp() {
		int up = pos.getZ();
		up--;
	}
	
	public void moveDown() {
		int down = pos.getZ();
		down++;
	}
}
