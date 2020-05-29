package gui.pieces;

import main.Chess;
import gui.Assets;

import java.awt.*;

public class Pawn extends Piece {

    public Pawn(Chess app, boolean first, int r, int c) {
        super(app, 0, first, r, c);
    }

    @Override
    public void render(Graphics g) {
        render(g, first? Assets.pawnw : Assets.pawnB);
    }

}
