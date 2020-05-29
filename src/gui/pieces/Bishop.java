package gui.pieces;

import main.Chess;
import gui.Assets;

import java.awt.*;

public class Bishop extends Piece {

    public Bishop(Chess app, boolean first, int r, int c) {
        super(app, 3, first, r, c);
    }

    @Override
    public void render(Graphics g) {
        render(g, first? Assets.bishopW : Assets.bishopB);
    }

}
