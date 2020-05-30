package game.gen;

import game.data.RC;
import game.data.State;

public class Validate {

    private Attack attack;

    public Validate() {
        attack = new Attack();
    }

    private boolean prevalidate(int[][] board, RC s, RC e, boolean turn) {
        return !Util.empty(board, s) && !Util.unmoved(s, e) && Util.bound(e) && Util.white(board, s) == turn;
    }

    private boolean validateLine(RC s, RC e) {
        return Util.dr(s, e) == 0 || Util.dc(s, e) == 0;
    }

    private boolean validateDiagonal(RC s, RC e) {
        return Math.abs(Util.dr(s, e)) == Math.abs(Util.dc(s, e));
    }

    private boolean validateClear(int[][] board, RC s, RC e, int dr, int dc, boolean take, int max) {
        RC step = new RC(s.r+dr, s.c+dc);
        for(int i = 0; i < max; ++i) {
            if(step.equals(e)) {
                boolean t1 = (take && validateMoveTake(board, s, e));
                boolean t2 = (!take && Util.empty(board, e));
                return t1 || t2;
            }
            if(!Util.bound(step) || !Util.empty(board, step)) break;
            step = new RC(step.r+dr, step.c+dc);
        }
        return false;
    }

    private boolean validateClear(int[][] board, RC s, RC e, int dr, int dc, boolean take) {
        return validateClear(board, s, e, dr, dc, take, 8);
    }

    private boolean validateTake(int[][] board, RC s, RC e) {
        return !Util.empty(board, e) && Util.opposite(board, s, e);
    }

    private boolean validateMoveTake(int[][] board, RC s, RC e) {
        return Util.empty(board, e) || validateTake(board, s, e);
    }

    private boolean validateClearLine(int[][] board, RC s, RC e) {
        return validateLine(s, e) &&
                validateClear(board, s, e, Util.dir(Util.dr(s, e)), Util.dir(Util.dc(s, e)), true);
    }

    private boolean validateClearDiagonal(int[][] board, RC s, RC e) {
        return validateDiagonal(s, e) &&
                validateClear(board, s, e, Util.dir(Util.dr(s, e)), Util.dir(Util.dc(s, e)), true);
    }

    private boolean validatePawnTake(int[][] board, RC s, RC e) {
        int pdr = Util.dr(s, e);
        int pdc = Util.dc(s, e);
        boolean first = Util.white(board, s);
        boolean w = (first && pdr == -1 && Math.abs(pdc) == 1 && validateTake(board, s, e));
        boolean b = (!first && pdr == 1 && Math.abs(pdc) == 1 && validateTake(board, s, e));
        return w || b;
    }

    private boolean validateAttacked(int[][] attack, RC p) {
        return attack[p.r][p.c] > 0;
    }

    private boolean validatePawnMove(int[][] board, RC s, RC e) {
        int pdr = Util.dr(s, e);
        int pdc = Util.dc(s, e);
        boolean white = Util.white(board, s);
        boolean take = validatePawnTake(board, s, e);
        boolean w0 = white && pdc == 0 && Util.empty(board, new RC(s.r-1, s.c));
        boolean b0 = !white && pdc == 0 && Util.empty(board, new RC(s.r+1, s.c));
        boolean w1 = w0 && pdr == -1;
        boolean b1 = b0 && pdr == 1;
        boolean w2 = w0 && Util.empty(board, new RC(s.r-2, s.c)) && pdr == -2 && s.r == 6;
        boolean b2 = b0 && Util.empty(board, new RC(s.r+2, s.c)) && pdr == 2 && s.r == 1;
        /*
        boolean enpassw0 = board[s.r-1][s.c] == State.PAWNB && s.r == 3
        boolean enpassw1 =
                enpassw0 && white && pdr == -1 && pdc == -1 && Util.empty(board, new RC(s.r-1, s.c-1));
        boolean enpassw2 =
                enpassw0 && white && pdr == -1 && pdc == 1 && Util.empty(board, new RC(s.r-1, s.c-1));
                        ;
                        
         */
        return take || w1 || w2 || b1 || b2;
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
        return validateMoveTake(board, s, e) && Math.abs(Util.dr(s, e)) <= 1 && Math.abs(Util.dc(s, e)) <= 1 &&
                !validateAttacked(attack, e);
    }

    public boolean validateMove(State state, RC s, RC e) {
        int[][] board = state.board;
        int id = state.board[s.r][s.c];
        boolean valid = false;
        if(prevalidate(board, s, e, state.turn)) {
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
                int[][] a = attack.genAttackArr(state, !state.turn);
                valid = validateKingMove(board, a, s, e);
            }
            System.out.println("move valid: " + valid);
            // check pin validity
            if(valid) {
                State next = state.copy();
                next.movei(s, e);
                int[][] a = attack.genAttackArr(next, !state.turn);
                int kid = state.turn ? State.KINGW : State.KINGB;
                RC king = state.find(kid);
                if(a[king.r][king.c] > 0) valid = false;
            }
            System.out.println("pin valid: " + valid);
        }
        System.out.println("attack arr: ");
        Util.print(attack.genAttackArr(state, !state.turn));
        System.out.println("valid: " + valid);
        System.out.println();
        return valid;
    }

}
