package piece;

import main.Application;
import main.Assets;

import java.awt.*;

public class Rook extends Piece {

    public Rook(Application app, boolean first, int r, int c) {
        super(app, 2, first, r, c);
    }

    @Override
    public void render(Graphics g) {
        render(g, first? Assets.rookW : Assets.rookB);
    }

}
