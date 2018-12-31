package piece;

import main.Application;
import main.Assets;

import java.awt.*;

public class Knight extends Piece {

    public Knight(Application app, boolean first, int r, int c) {
        super(app, 1, first, r, c);
    }

    @Override
    public void render(Graphics g) {
        render(g, first? Assets.knightW : Assets.knightB);
    }

}
