package piece;

import main.Application;
import main.Assets;

import java.awt.*;

public class Pawn extends Piece {

    public Pawn(Application app, boolean first, int r, int c) {
        super(app, 0, first, r, c);
    }

    @Override
    public void render(Graphics g) {
        render(g, first? Assets.pawnW : Assets.pawnB);
    }

}
