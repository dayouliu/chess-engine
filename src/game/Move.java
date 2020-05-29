package game;

import data.RC;
import main.Application;
import gui.pieces.Piece;
import gui.pieces.Queen;

public class Move {

    private int row = 8, col = 8;

    private int dr(RC s, RC e) {
        return e.r - s.r;
    }

    private int dc(RC s, RC e) {
        return e.c - s.c;
    }

    private boolean nomove(RC s, RC e) {
        return s.equals(e);
    }

    public boolean bounded(RC e) {
        return  0 <= e.r && e.r < row && 0 <= e.c && e.c < col;
    }

    public boolean empty(int[][] board, RC e) {
        return board[e.r][e.c] == 0;
    }

    private boolean precheck(RC s, RC e) {
        return getPiece(s) != null && !nomove(s, e) && bounded(e);
    }

    private boolean line(RC s, RC e) {
        return dr(s, e) == 0 || dc(s, e) == 0;
    }

    private boolean diagonal(RC s, RC e) {
        return Math.abs(dr(s, e)) == Math.abs(dc(s, e));
    }

    private int dir(int n) {
        if(n < 0) return -1; if(n > 0) return 1; return 0;
    }

    private boolean clear(int[][] board, RC s, RC e, int dr, int dc, boolean take) {
        int cdr = dr(s, e);
        int cdc = dc(s, e);
        RC step = new RC(s.r+cdr, s.c+cdc);
        while(bounded(step)) {
            if(step.equals(e)) {
                if(take && empty(board, e)) return true;
                else if(!take) return true;
                else return false;
            }
            step = new RC(step.r+cdr, step.c+cdc);
        }
        return false;
    }

    private boolean clearLine(int[][] board, RC s, RC e) {
        return line(s, e) && clear(board, s, e, dir(dr(s, e)), dir(dc(s, e)), true);
    }

    private boolean clearDiagonal(int[][] board, RC s, RC e) {
        return diagonal(s, e) && clear(board, s, e, dir(dr(s, e)), dir(dc(s, e)), true);
    }

    private boolean isPawnMove(int[][] board, RC s, RC e) {
        int pdr = dr(s, e);
        int pdc = dc(s, e);
        boolean first = getPiece(s).isFirst();
        return pawnTake(s, e) || (pdc == 0 && clear(board, s, e, pdr, 0, false) &&
                ((first && (pdr == -1 || (pdr == -2 && s.r == row - 1))) ||
                        (!first && (pdr == 1 || (pdr == 2 && s.r == 1)))));
    }


    public void move(RC s, RC e) {
        /*
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

         */
    }

    public boolean validMove(int piece, RC e) {
        RC s = piece.getPos();
        int id = piece.getId();
        if(prelimCheck(s, e)) {
            if(id == Piece.PAWN) {
                return isPawnMove(s, e);
            } else if(id == Piece.KNIGHT) {
                return isKnightMove(s, e);
            } else if(id == Piece.ROOK) {
                return isRookMove(s, e);
            } else if(id == Piece.BISHOP) {
                return isBishopMove(s, e);
            } else if(id == Piece.QUEEN) {
                return isQueenMove(s, e);
            } else if(id == Piece.KING) {
                return isKingMove(s, e);
            }
        }
        return false;
    }










    private boolean isKnightMove(RC s, RC e) {
        int kdr = Math.abs(dr(s, e));
        int kdc = Math.abs(dc(s, e));
        return moveTake(s, e) && ((kdr == 1 && kdc == 2) || (kdr == 2 && kdc == 1));
    }

    private boolean isRookMove(RC s, RC e) {
        return isClearLine(s, e);
    }

    private boolean isBishopMove(RC s, RC e) {
        return isClearDiag(s, e);
    }

    private boolean isQueenMove(RC s, RC e) {
        return isClearLine(s, e) || isClearDiag(s, e);
    }

    private boolean isKingMove(RC s, RC e) {
        return isEmpty(e) && Math.abs(dr(s, e)) <= 1 && Math.abs(dc(s, e)) <= 1 &&
                !isAttacked(e, getPiece(s).isFirst());
    }

    // Take moves

    private void take(RC e) {
        if(getPiece(e) != null) {
            app.getBoard().getPosition().remove(getPiece(e));
        }
    }

    private boolean canTake(RC s, RC e) {
        return !isEmpty(e) && getPiece(s).isFirst() != getPiece(e).isFirst();
    }

    private boolean moveTake(RC s, RC e) {
        return isEmpty(e) || canTake(s, e);
    }

    private boolean pawnTake(RC s, RC e) {
        int pdr = dr(s, e);
        int pdc = dc(s, e);
        boolean first = getPiece(s).isFirst();
        return (first && pdr == -1 && Math.abs(pdc) == 1 && canTake(s, e)) ||
                (!first && pdr == 1 && Math.abs(pdc) == 1 && canTake(s, e));
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

    // Attack moves

    private void attack() {
        attackInit();
        for(Piece piece : app.getBoard().getPieces()) {
            RC s = piece.getPos();
            int id = piece.getId();
            if (id == Piece.PAWN) {
                attackPawn(s);
            } else if (id == Piece.KNIGHT) {
                attackKnight(s);
            } else if (id == Piece.ROOK) {
                attackRook(s);
            } else if (id == Piece.BISHOP) {
                attackBishop(s);
            } else if (id == Piece.QUEEN) {
                attackQueen(s);
            } else if (id == Piece.KING) {
                attackKing(s);
            }
        }
    }

    private boolean isAttacked(RC r, boolean first) {
        RC attack = checkAttack[r.r][r.c];
        return first ? attack.c > 0 : attack.r > 0;
    }

    private void setAttack(boolean first, RC pos, int da) {
        if(first)
            checkAttack[pos.r][pos.c].r += da;
        else
            checkAttack[pos.r][pos.c].c += da;
    }

    private void updateAttack(RC s, boolean first, int da, int dr, int dc, int steps) {
        if(steps == -1) {
            steps = Math.max(irow+1, icol+1);
        }
        for(int i = 1; i <= steps; i++) {
            RC pos = new RC(s.r + dr * i, s.c + dc * i);
            if(!inBounds(pos) || !isEmpty(pos))
                break;
            setAttack(first, pos, da);
        }
    }

    private void attackMove(RC s, int dr, int dc, int steps) {
        updateAttack(s, getPiece(s).isFirst(), 1, dr, dc, steps);
    }

    private void attackPawn(RC s) {
        int pdr = getPiece(s).isFirst()?-1:1;
        attackMove(s, pdr, -1, 1);
        attackMove(s, pdr, 1, 1);
    }

    private int[] rdir = {1, -1, 1, -1};
    private int[] cdir = {-1, 1, 1, -1};

    private void attackMoveDir(RC s, int dr, int dc, int steps) {
        if(dr == 0 && dc == 0) {
            return;
        } else if(dr == 0) {
            attackMove(s, 0, dc * cdir[0], steps);
            attackMove(s, 0, dc * cdir[1], steps);
        } else if(dc == 0) {
            attackMove(s, dr * rdir[0], 0, steps);
            attackMove(s, dr * rdir[1], 0, steps);
        } else {
            for (int i = 0; i < 4; i++) {
                attackMove(s, dr * rdir[i], dc * cdir[i], steps);
            }
        }
    }

    private void attackKnight(RC s) {
        attackMoveDir(s, 1, 2, 1);
        attackMoveDir(s, 2, 1, 1);
    }

    private void attackLine(RC s) {
        attackMoveDir(s, 1, 0, -1);
        attackMoveDir(s, 0, 1, -1);
    }

    private void attackDiagonal(RC s) {
        attackMoveDir(s, 1, 1, -1);
    }

    private void attackRook(RC s) {
        attackLine(s);
    }

    private void attackBishop(RC s) {
        attackDiagonal(s);
    }

    private void attackQueen(RC s) {
        attackLine(s);
        attackDiagonal(s);
    }

    private void attackKing(RC s) {
        attackMoveDir(s, 1, 0, 1);
        attackMoveDir(s, 0, 1, 1);
        attackMoveDir(s, 1, 1, 1);
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

    // Setters and Getters

    public void addPiece(Piece p) {
        checkPieces[p.getPos().r][p.getPos().c] = p;
        attack();
    }

    public void removePiece(RC pos) {
        checkPieces[pos.r][pos.c] = null;
    }

    private Piece getPiece(RC pos) {
        return checkPieces[pos.r][pos.c];
    }

}
