package gui.board;

import game.data.RC;
import gui.Assets;
import main.Application;
import game.logic.Validate;

import java.awt.*;

public class Piece {

    protected Application app;
    protected Validate move;
    protected int id;
    protected RC pos;
    protected boolean remove = false;

    protected Rectangle bounds = new Rectangle();
    protected boolean pieceSelected = false;

    public Piece(Application app, int id, int r, int c) {
        this.app = app;
        this.id = id;
        this.pos = new RC(r,  c);
    }

    public void update() {
        double len = app.getChess().getBoard().getLen();
        double pieceLen = len * app.getChess().getBoard().getPieceLen();
        int dmx = app.getMouse().dmx;
        int dmy = app.getMouse().dmy;

        if(pieceSelected && !app.getMouse().left()) {
            pieceSelected = false;
            app.getChess().getBoard().setSelected(null);
            Point tlc = app.getChess().getBoard().getTLC();
            int nr = (int)((dmy - tlc.y) / len);
            int nc = (int)((dmx - tlc.x) / len);
            RC e = new RC(nr, nc);
            app.getChess().move(pos, e);
        }

        if(pieceSelected) {
            bounds.x = (int)(dmx - pieceLen / 2);
            bounds.y = (int)(dmy - pieceLen / 2);
            bounds.width = (int)pieceLen;
            bounds.height = (int)pieceLen;
        } else {
            Point tlc = app.getChess().getBoard().getTLC();
            double pieceOffset = (len - pieceLen) / 2;
            bounds.x = (int)(tlc.x + pos.c * len + pieceOffset);
            bounds.y = (int)(tlc.y + pos.r * len + pieceOffset);
            bounds.width = (int)pieceLen;
            bounds.height = (int)pieceLen;
            pieceSelected = !app.getChess().getBoard().isSelected() &&
                    app.getMouse().left() && bounds.contains(dmx, dmy);
            if (pieceSelected) {
                app.getChess().getBoard().setSelected(this);
            }
        }
    }

    public void render(Graphics g) {
        g.drawImage(Assets.pieces[id], bounds.x, bounds.y, bounds.width, bounds.height, null);
    }

    public String toString() {
        return (this == null) ? "null" : "" + id;
    }

    public int getId() {
        return id;
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

    public void remove() {
        remove = true;
    }

    public boolean isRemove() {
        return remove;
    }

}
