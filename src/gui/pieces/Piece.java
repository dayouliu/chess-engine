package gui.pieces;

import game.data.RC;
import main.Chess;
import game.gen.Validate;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Piece {

    public static int
            PAWN = 0, KNIGHT = 1, ROOK = 2,
            BISHOP = 3, QUEEN = 4, KING = 5;

    protected Chess app;
    protected Validate move;
    protected int id;
    protected RC pos;
    protected boolean first;
    protected boolean remove = false;

    protected Rectangle bounds = new Rectangle();
    protected boolean pieceSelected = false;

    public Piece(Chess app, int id, boolean first, int r, int c) {
        this.app = app;
        this.id = id;
        this.first = first;
        this.pos = new RC(r,  c);
    }

    public void update() {
        double len = app.getBoard().getLen();
        double pieceLen = len * app.getBoard().getPieceLen();
        int dmx = app.getMouse().dmx;
        int dmy = app.getMouse().dmy;
        // boolean isTurn = app.getBoard().getPosition().getTurn() == first;
        /*
        if(pieceSelected && !app.getMouse().left()) {
            pieceSelected = false;
            app.getBoard().setSelected(false, null);
            Point tlc = app.getBoard().getTLC();
            int nr = (int)((dmy - tlc.y) / len);
            int nc = (int)((dmx - tlc.x) / len);
            app.getBoard().getPosition().getMove().move(this, new RC(nr, nc));
        }
        */
        if(pieceSelected) {
            /*
            bounds.x = (int)(dmx - pieceLen / 2);
            bounds.y = (int)(dmy - pieceLen / 2);
            bounds.width = (int)pieceLen;
            bounds.height = (int)pieceLen;
             */
        } else {
            Point tlc = app.getBoard().getTLC();
            double pieceOffset = (len - pieceLen) / 2;
            bounds.x = (int)(tlc.x + pos.c * len + pieceOffset);
            bounds.y = (int)(tlc.y + pos.r * len + pieceOffset);
            bounds.width = (int)pieceLen;
            bounds.height = (int)pieceLen;

            /*
            pieceSelected = isTurn && !app.getBoard().isSelected() &&
                    app.getMouse().left() && bounds.contains(dmx, dmy);


            // Not DONE:
            if(app.getBoard().getPosition().isMateFlag()) {

            } else if(app.getBoard().getPosition().isCheckFlag()) {
                if(pieceSelected && id == Piece.KING) {
                    app.getBoard().setSelected(true, this);
                }
            } else {
                if (pieceSelected) {
                    app.getBoard().setSelected(true, this);
                }
            }
             */
        }
    }

    public void render(Graphics g, BufferedImage bi) {
        g.drawImage(bi, bounds.x, bounds.y, bounds.width, bounds.height, null);
    }

    public abstract void render(Graphics g);

    public String toString() {
        if(this == null)
            return "null";
        return "" + id;
    }

    public int getId() {
        return id;
    }

    public boolean isFirst() {
        return first;
    }

    public RC getPos() {
        return pos;
    }

    public void setRC(RC pos) {
        this.pos = pos;
    }

    public boolean isPieceSelected() {
        return pieceSelected;
    }

    public boolean getRemove() {
        return remove;
    }

    public void setRemove() {
        remove = true;
    }

}
