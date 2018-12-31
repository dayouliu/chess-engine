package piece;

import main.Application;
import main.Assets;

import java.awt.*;

public class King extends Piece {

    public King(Application app, boolean first, int r, int c) {
        super(app, 5, first, r, c);
    }

    @Override
    public void render(Graphics g) {
        render(g, first? Assets.kingW : Assets.kingB);
    }

}
