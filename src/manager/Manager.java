package manager;

import main.Application;
import piece.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Manager {

    private Application app;

    private Movement movement;
    private List<Piece> pieces = new ArrayList<Piece>();
    private List<Piece> piecesAdd = new ArrayList<Piece>();

    private boolean turnFlag = true;
    private boolean checkFlag = false;
    private boolean mateFlag = false;
    private boolean drawFlag = false;
    private Piece kingW, kingB;

    private boolean selected = false;
    private Piece selectedPiece;

    public Manager(Application app) {
        this.app = app;
        movement = new Movement(app);
    }

    public void init() {
        movement.init();

        // Tests
        /*
        Piece pawn = new Pawn(app, true, 7, 3);
        Piece knight = new Knight(app, true, 5, 3);
        Piece rook = new Rook(app, true, 4, 3);
        Piece bishop = new Bishop(app, false, 2, 3);
        Piece queen = new Queen(app, true, 5, 4);
        Piece king = new King(app, true, 5, 7);
        Piece pawnB = new Pawn(app, false, 0, 0);
        add(pawn);
        add(knight);
        add(rook);
        add(bishop);
        add(queen);
        add(king);
        add(pawnB);
        */

        int row = app.getBoard().getRow();
        int col = app.getBoard().getCol();
        for(int i = 0; i < row; i++) {
            add(new Pawn(app, true, row-2, i));
            add(new Pawn(app, false, 1, i));
        }
        add(new Rook(app, true, row-1, 0));
        add(new Knight(app, true, row-1, 1));
        add(new Bishop(app, true, row-1, 2));
        add(new Queen(app, true, row-1, 3));
        add(new King(app, true, row-1, 4));
        add(new Bishop(app, true, row-1, 5));
        add(new Knight(app, true, row-1, 6));
        add(new Rook(app, true, row-1, 7));

        add(new Rook(app, false, 0, 0));
        add(new Knight(app, false, 0, 1));
        add(new Bishop(app, false, 0, 2));
        add(new Queen(app, false, 0, 3));
        add(new King(app, false, 0, 4));
        add(new Bishop(app, false, 0, 5));
        add(new Knight(app, false, 0, 6));
        add(new Rook(app, false, 0, 7));
    }

    public void update() {
        List<Piece> piecesUpdate = new ArrayList<Piece>();
        for(Piece piece : new ArrayList<Piece>(pieces)) {
            piece.update();
            if(!piece.getRemove()) {
                piecesUpdate.add(piece);
            }
        }
        for(Piece piece : piecesAdd) {
            piecesUpdate.add(piece);
        }
        piecesAdd.clear();
        pieces = piecesUpdate;
    }

    public void render(Graphics g) {
        for(Piece piece : new ArrayList<Piece>(pieces)) {
            if(!piece.isPieceSelected()) {
                piece.render(g);
            }
        }
        if(selected && selectedPiece != null) {
            selectedPiece.render(g);
        }
    }

    // Game

    public void done() {
        if(mateFlag) {
            System.out.println("MATE");
        }
    }

    // Add and remove to pieces

    public void add(Piece piece) {
        if(movement.inBounds(piece.getPos()) && movement.isEmpty(piece.getPos())) {
            piecesAdd.add(piece);
            movement.addPiece(piece);
        }
        if(piece.getId() == Piece.KING) {
            if(piece.isFirst())
                kingW = piece;
            else
                kingB = piece;
        }
    }

    public void remove(Piece piece) {
        piece.setRemove();
        movement.removePiece(piece.getPos());
    }


    // Getters and setters

    public List<Piece> getPieces() {
        return pieces;
    }

    public Movement getMovement() {
        return movement;
    }

    public void turn() {
        turnFlag = !turnFlag;
    }

    public boolean getTurn() {
        return turnFlag;
    }

    public boolean isCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(boolean checkFlag) {
        this.checkFlag = checkFlag;
    }

    public boolean isMateFlag() {
        return mateFlag;
    }

    public void setMateFlag(boolean mateFlag) {
        this.mateFlag = mateFlag;
    }

    public boolean isDrawFlag() {
        return drawFlag;
    }

    public void setDrawFlag(boolean drawFlag) {
        this.drawFlag = drawFlag;
    }

    public Piece getKingW() {
        return kingW;
    }

    public Piece getKingB() {
        return kingB;
    }

    public void setSelected(boolean selected, Piece selectedPiece) {
        this.selected = selected;
        this.selectedPiece = selectedPiece;
    }

    public boolean isSelected() {
        return selected;
    }

}
