package board;

import main.Application;

import java.awt.*;

public class Square {

    private Application app;
    private int r, c;
    private Color color;

    public Square(Application app, int r, int c) {
        this.app = app;
        this.r = r;
        this.c = c;
        boolean parity = ((r + c) % 2) == 0;
        if(parity) {
            color = new Color(210,180,140);
        } else {
            color = new Color(245,222,179);
        }
    }

    public void render(Graphics g) {
        double len = app.getBoard().getLen();
        Point tlc = app.getBoard().getTLC();
        int x = (int)(tlc.x + c * len);
        int y = (int)(tlc.y + r * len);
        g.setColor(color);
        g.fillRect(x, y, (int)len, (int)len);

        // Testing
        g.setColor(Color.black);
        g.drawString(r + ", " + c, x, (int)(y + len));
    }

}
