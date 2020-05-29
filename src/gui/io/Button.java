package gui.io;

import main.Application;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Button {
	
	private Application app;
	private float posx, posy;
	private int w, h;
	private String s;
	private Rectangle b;
	private Click press;
	private BufferedImage i;
	private boolean active = true;
	public boolean hide;
	
	public Button(Application app, BufferedImage i, float posx, float posy, int w, int h, String s, Click press) {
		this.app = app;
		this.i = i;
		this.posx = posx;
		this.posy = posy;
		this.w = w;
		this.h = h;
		this.s = s;
		b = new Rectangle();
		b.setSize(w, h);
		this.press = press;
	}
	
	public void render(Graphics g) {
		if(!hide) {
			b.setLocation((int)(posx*app.getFrameWidth()-(float)w/2), (int)(posy*app.getFrameHeight()-(float)h/2));
			g.drawImage(i, b.x, b.y, b.width, b.height, null);
			text(g, b, s);
			if(active && app.getMouse().left() && b.contains(app.getMouse().mx(), app.getMouse().my())) {
				click();
				active = false;
			}
			if(!app.getMouse().left())
				active = true;
		}
	}
	
	public void text(Graphics g, Rectangle r, String s) {
		FontMetrics m = g.getFontMetrics(g.getFont());
		g.drawString(s, r.x+(r.width-m.stringWidth(s))/2, r.y+(r.height-m.getHeight())/2+m.getAscent());
	}
	
	public void click() {press.click();}

}
