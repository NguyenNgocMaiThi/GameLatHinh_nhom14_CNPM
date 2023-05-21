package View;

import java.awt.Graphics;

public interface Paintable {
	
	public abstract void paint(Graphics g);			// the paint method
	public abstract boolean update(int X, int Y);   // mouse position update for hovers etc.
	public abstract String getButton (int X, int Y); // button updates.
	

}
