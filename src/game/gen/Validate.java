package game.gen;

import game.data.RC;
import game.data.RCM;
import game.data.State;

import java.util.ArrayList;
import java.util.List;

public class Validate {

    private Move movement;

    public Validate(Move move) {
        this.movement = move;
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

    public boolean validatePawnEnpassant(int[][] board, RCM last, RC s, RC e) {
        if(!Util.pawn(board[s.r][s.c])) return false;
        int pdr = Util.dr(s, e);
        int pdc = Util.dc(s, e);
        boolean white = Util.white(board, s);
        boolean enpasswl =
                s.r == 3 && pdr == -1 && pdc == -1 &&
                        last.s != null && last.s.equals(s.r-2, s.c-1) && last.e.equals(s.r, s.c-1) &&
                        board[s.r][s.c-1] == State.PAWNB && white && Util.empty(board, new RC(s.r-1, s.c-1));
        boolean enpasswr =
                s.r == 3 && pdr == -1 && pdc == 1 &&
                        last.s != null && last.s.equals(s.r-2, s.c+1) && last.e.equals(s.r, s.c+1) &&
                        board[s.r][s.c+1] == State.PAWNB && white && Util.empty(board, new RC(s.r-1, s.c+1));
        boolean enpassbl =
                s.r == 4 && pdr == 1 && pdc == -1 &&
                        last.s != null && last.s.equals(s.r+2, s.c-1) && last.e.equals(s.r, s.c-1) &&
                        board[s.r][s.c-1] == State.PAWNW && !white && Util.empty(board, new RC(s.r+1, s.c-1));
        boolean enpassbr =
                s.r == 4 && pdr == 1 && pdc == 1 &&
                        last.s != null && last.s.equals(s.r+2, s.c+1) && last.e.equals(s.r, s.c+1) &&
                        board[s.r][s.c+1] == State.PAWNW && !white && Util.empty(board, new RC(s.r+1, s.c+1));
        return enpasswl || enpasswr || enpassbl || enpassbr;
    }

    private List<RCM> validatePawnMove(int[][] board, RCM last, RC s, RC e) {
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
        boolean enpass = validatePawnEnpassant(board, last, s, e);

        List<RCM> move = new ArrayList<RCM>();
        if(take || w1 || w2 || b1 || b2) {
            move.add(Util.move(board, s, e));
        } else if(enpass) {
            move.add(Util.move(board, s, e));
            RC other = new RC(e.r-pdr, e.c);
            move.add(new RCM(other, other, State.EMPTY, board[e.r-pdr][e.c]));
        }
        return move;
    }

    private List<RCM> validateKnightMove(int[][] board, RC s, RC e) {
        int kdr = Math.abs(Util.dr(s, e));
        int kdc = Math.abs(Util.dc(s, e));
        List<RCM> move = new ArrayList<RCM>();
        if(validateMoveTake(board, s, e) && ((kdr == 1 && kdc == 2) || (kdr == 2 && kdc == 1))) {
            move.add(Util.move(board, s, e));
        }
        return move;
    }

    private List<RCM> validateRookMove(int[][] board, RC s, RC e) {
        List<RCM> move = new ArrayList<RCM>();
        if(validateClearLine(board, s, e)) {
            move.add(Util.move(board, s, e));
        }
        return move;
    }

    private List<RCM> validateBishopMove(int[][] board, RC s, RC e) {
        List<RCM> move = new ArrayList<RCM>();
        if(validateClearDiagonal(board, s, e)) {
            move.add(Util.move(board, s, e));
        }
        return move;
    }

    private List<RCM> validateQueenMove(int[][] board, RC s, RC e) {
        List<RCM> move = new ArrayList<RCM>();
        if(validateClearLine(board, s, e) || validateClearDiagonal(board, s, e)) {
            move.add(Util.move(board, s, e));
        }
        return move;
    }

    public boolean validateKingCastle(int[][] board, int[][] attack, int[][] moved, boolean check, RC s, RC e) {
        boolean wcOO =
                board[s.r][s.c] == State.KINGW && s.equals(7, 4) && e.equals(7,6) &&
                !check && validateClear(board, s, e, 0, 1, false) &&
                !validateAttacked(board, new RC(s.r, s.c+1)) && !validateAttacked(board, new RC(s.r, s.c+2)) &&
                moved[7][4] == 0 && moved[7][7] == 0;
        boolean wcOOO =
                board[s.r][s.c] == State.KINGW && s.equals(7, 4) && e.equals(7,2) &&
                !check && validateClear(board, s, e, 0, -1, false) &&
                !validateAttacked(board, new RC(s.r, s.c-1)) && !validateAttacked(board, new RC(s.r, s.c-2)) &&
                moved[7][4] == 0 && moved[7][0] == 0;
        boolean bcOO =
                board[s.r][s.c] == State.KINGB && s.equals(0, 4) && e.equals(0,6) &&
                !check && validateClear(board, s, e, 0, 1, false) &&
                !validateAttacked(board, new RC(s.r, s.c+1)) && !validateAttacked(board, new RC(s.r, s.c+2)) &&
                moved[0][4] == 0 && moved[0][7] == 0;
        boolean bcOOO =
                board[s.r][s.c] == State.KINGB && s.equals(0, 4) && e.equals(0,2) &&
                !check && validateClear(board, s, e, 0, -1, false) &&
                !validateAttacked(board, new RC(s.r, s.c-1)) && !validateAttacked(board, new RC(s.r, s.c-2)) &&
                moved[0][4] == 0 && moved[0][0] == 0;
        return wcOO || wcOOO || bcOO || bcOOO;
    }

    private List<RCM> validateKingMove(int[][] board, int[][] attack, int[][] moved, boolean check, RC s, RC e) {
        List<RCM> move = new ArrayList<RCM>();
        boolean knc = validateMoveTake(board, s, e) && !validateAttacked(attack, e) &&
                Math.abs(Util.dr(s, e)) <= 1 && Math.abs(Util.dc(s, e)) <= 1;
        boolean kc = validateKingCastle(board, attack, moved, check, s, e);
        if(knc || kc) {
            move.add(Util.move(board, s, e));
        }
        return move;
    }

    public List<RCM> validateMove(State state, RC s, RC e) {
        int[][] b = state.board;
        int[][] a = state.attack;
        int id = state.board[s.r][s.c];
        List<RCM> move = new ArrayList<RCM>();
        if(prevalidate(b, s, e, state.turn)) {
            // check space validity
            if (id == State.PAWNW || id == State.PAWNB) {
                move = validatePawnMove(b, state.last(), s, e);
            } else if (id == State.KNIGHTW || id == State.KNIGHTB) {
                move = validateKnightMove(b, s, e);
            } else if (id == State.BISHOPW || id == State.BISHOPB) {
                move = validateBishopMove(b, s, e);
            } else if (id == State.ROOKW || id == State.ROOKB) {
                move = validateRookMove(b, s, e);
            } else if (id == State.QUEENW || id == State.QUEENB) {
                move = validateQueenMove(b, s, e);
            } else if (id == State.KINGW || id == State.KINGB) {
                move = validateKingMove(b, a, state.moved, state.check, s, e);
            }

            System.out.println("move valid: " + !move.isEmpty());
            // check pin validity
            if(!move.isEmpty()) {
                movement.move(state, move, state.turn);
                RC king = state.find(state.turn ? State.KINGW : State.KINGB);
                if(a[king.r][king.c] > 0)
                    move.clear();
                movement.unmove(state, state.turn);
            }
            System.out.println("pin valid: " + !move.isEmpty());
        }

        return move;
    }

}
