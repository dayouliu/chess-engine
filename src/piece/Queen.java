package piece;

import main.Application;
import main.Assets;

import java.awt.*;

public class Queen extends Piece {

    public Queen(Application app, boolean first, int r, int c) {
        super(app, 4, first, r, c);
    }

    @Override
    public void render(Graphics g) {
        render(g, first? Assets.queenW : Assets.queenB);
    }

}
