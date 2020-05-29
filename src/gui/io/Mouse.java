package gui.io;

import java.awt.event.*;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener{
	
	private boolean left, right;
	public int mx, my, dmx, dmy, wheel;

	@Override
	public void mousePressed(MouseEvent e) {
		left = e.getButton() == MouseEvent.BUTTON1;
		right = e.getButton() == MouseEvent.BUTTON3;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		left = e.getButton() != MouseEvent.BUTTON1;
		right = e.getButton() != MouseEvent.BUTTON3;
	}
	 
	@Override
	public void mouseMoved(MouseEvent e) {
		mx = dmx = e.getX();
		my = dmy = e.getY();
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int notches = e.getWheelRotation();
		if(notches < 0) wheel++; else wheel--;
	}
	
	public boolean left() {return left;}

	public boolean right() {return right;}

	public int mx() {return mx;}

	public int my() {return my;}

	public int wheel() {return wheel;}

	public void wheelReset() {wheel = 0;}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {
		dmx = e.getX();
		dmy = e.getY();
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

}
