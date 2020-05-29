package game.gen;

public class Move {
    /*
    // Take moves

    public void move(RC s, RC e) {

        Piece[][] checkPieces = new Piece[irow+1][icol+1];
        RC[][] checkAttack = new RC[irow+1][icol+1];
        if(validMove(piece, e)) {
            RC s = piece.getPos();
            // Take
            take(e);
            // Move
            checkPieces[s.r][s.c] = null;
            checkPieces[e.r][e.c] = piece;
            piece.setRC(e);
            // Promote
            promote(e);
            // Attack
            attack();
            // Check and mate
            check();
            mate();
            // Next turn
            app.getBoard().getPosition().turn();
        }

    }

    private void take(RC e) {
        if(getPiece(e) != null) {
            app.getBoard().getPosition().remove(getPiece(e));
        }
    }





    // Promotion Moves

    private void promote(RC e) {
        Piece piece = getPiece(e);
        boolean first = piece.isFirst();
        boolean canPromote = piece.getId() == Piece.PAWN &&
                ((first && e.r == 0) || (!first && e.r == irow));
        if(canPromote) {
            app.getBoard().getPosition().remove(piece);
            app.getBoard().add(new Queen(app, first, e.r, e.c));
        }
    }



    // Checking and mating moves

    private void check() {
        boolean turn = app.getBoard().getPosition().getTurn();
        Piece king = turn ? app.getBoard().getPosition().getKingB() :
                app.getBoard().getPosition().getKingW();
        app.getBoard().getPosition().setCheckFlag(isAttacked(king.getPos(), turn));
    }

    private int[] alldirR = {-1, -1, -1, 0, 0, 1, 1, 1};
    private int[] alldirC = {-1, 0, 1, -1, 1, -1, 0, 1};

    private void mate() {
        if(app.getBoard().getPosition().isCheckFlag()) {
            boolean turn = app.getBoard().getPosition().getTurn();
            Piece king = turn ? app.getBoard().getPosition().getKingB() :
                    app.getBoard().getPosition().getKingW();
            RC s = king.getPos();
            for (int i = 0; i < 8; i++) {
                RC e = new RC(s.r + alldirR[i], s.c + alldirC[i]);
                if (inBounds(e)) {
                    if(!isAttacked(e, turn)) {
                        return;
                    }
                }
            }
            app.getBoard().getPosition().setMateFlag(true);
        }
    }

    */
}
