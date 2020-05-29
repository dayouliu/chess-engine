package game.gen;

import game.data.RC;
import game.data.State;

public class Validate {

    private Attack attack;

    public Validate() {
        attack = new Attack();
    }

    private boolean prevalidate(int[][] board, RC s, RC e) {
        return !Util.empty(board, s) && !Util.unmoved(s, e) && Util.bound(e);
    }

    private boolean validateLine(RC s, RC e) {
        return Util.dr(s, e) == 0 || Util.dc(s, e) == 0;
    }

    private boolean validateDiagonal(RC s, RC e) {
        return Math.abs(Util.dr(s, e)) == Math.abs(Util.dc(s, e));
    }

    private boolean validateClear(int[][] board, RC s, RC e, int dr, int dc, boolean take) {
        int cdr = Util.dr(s, e);
        int cdc = Util.dc(s, e);
        RC step = new RC(s.r+cdr, s.c+cdc);
        while(Util.bound(step)) {
            if(step.equals(e)) {
                if(!take && Util.empty(board, e)) return true;
                else if(take) return true;
                else return false;
            }
            step = new RC(step.r+cdr, step.c+cdc);
        }
        return false;
    }

    private boolean validateTake(int[][] board, RC s, RC e) {
        return !Util.empty(board, e) && Util.opposite(board, s, e);
    }

    private boolean validateMoveTake(int[][] board, RC s, RC e) {
        return Util.empty(board, e) || validateTake(board, s, e);
    }

    private boolean validateClearLine(int[][] board, RC s, RC e) {
        return validateLine(s, e) && validateClear(board, s, e, Util.dir(Util.dr(s, e)), Util.dir(Util.dc(s, e)), true);
    }

    private boolean validateClearDiagonal(int[][] board, RC s, RC e) {
        return validateDiagonal(s, e) && validateClear(board, s, e, Util.dir(Util.dr(s, e)), Util.dir(Util.dc(s, e)), true);
    }

    private boolean validatePawnTake(int[][] board, RC s, RC e) {
        int pdr = Util.dr(s, e);
        int pdc = Util.dc(s, e);
        boolean first = Util.white(board, s);
        return (first && pdr == -1 && Math.abs(pdc) == 1 && validateTake(board, s, e)) ||
                (!first && pdr == 1 && Math.abs(pdc) == 1 && validateTake(board, s, e));
    }

    private boolean validateAttacked(int[][] attack, RC p) {
        return attack[p.r][p.c] > 0;
    }

    private boolean validatePawnMove(int[][] board, RC s, RC e) {
        int pdr = Util.dr(s, e);
        int pdc = Util.dc(s, e);
        boolean first = Util.white(board, s);
        return validatePawnTake(board, s, e) || (pdc == 0 && validateClear(board, s, e, pdr, 0, false) &&
                ((first && (pdr == -1 || (pdr == -2 && s.r == Util.row - 1))) ||
                  (!first && (pdr == 1 || (pdr == 2 && s.r == 1)))));
    }

    private boolean validateKnightMove(int[][] board, RC s, RC e) {
        int kdr = Math.abs(Util.dr(s, e));
        int kdc = Math.abs(Util.dc(s, e));
        return validateMoveTake(board, s, e) && ((kdr == 1 && kdc == 2) || (kdr == 2 && kdc == 1));
    }

    private boolean validateRookMove(int[][] board, RC s, RC e) {
        return validateClearLine(board, s, e);
    }

    private boolean validateBishopMove(int[][] board, RC s, RC e) {
        return validateClearDiagonal(board, s, e);
    }

    private boolean validateQueenMove(int[][] board, RC s, RC e) {
        return validateClearLine(board, s, e) || validateClearDiagonal(board, s, e);
    }

    private boolean validateKingMove(int[][] board, int[][] attack, RC s, RC e) {
        return Util.empty(board, e) && Math.abs(Util.dr(s, e)) <= 1 && Math.abs(Util.dc(s, e)) <= 1 &&
                !validateAttacked(attack, e);
    }

    public boolean validateMove(State state, RC s, RC e) {
        int[][] board = state.board;
        int id = state.board[s.r][s.c];
        boolean valid = false;
        if(prevalidate(board, s, e)) {
            // check space validity
            if (id == State.PAWNW || id == State.PAWNB) {
                valid = validatePawnMove(board, s, e);
            } else if (id == State.KNIGHTW || id == State.KNIGHTB) {
                valid = validateKnightMove(board, s, e);
            } else if (id == State.BISHOPW || id == State.BISHOPB) {
                valid = validateBishopMove(board, s, e);
            } else if (id == State.ROOKW || id == State.ROOKB) {
                valid = validateRookMove(board, s, e);
            } else if (id == State.QUEENW || id == State.QUEENB) {
                valid = validateQueenMove(board, s, e);
            } else if (id == State.KINGW || id == State.KINGB) {
                int[][] a = attack.genAttackArr(state, state.turn);
                valid = validateKingMove(board, a, s, e);
            }
            // check pin validity
            if(valid) {
                int[][] a = attack.genAttackArr(state.move(s, e), state.turn);
                int kid = state.turn ? 6 : 12;
                RC king = state.pieces[kid];
                if(a[king.r][king.c] > 0) valid = false;
            }
        }
        return valid;
    }

}
