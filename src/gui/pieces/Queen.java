package gui.pieces;

import main.Chess;
import gui.Assets;

import java.awt.*;

public class Queen extends Piece {

    public Queen(Chess app, boolean first, int r, int c) {
        super(app, 4, first, r, c);
    }

    @Override
    public void render(Graphics g) {
        render(g, first? Assets.queenW : Assets.queenB);
    }

}
