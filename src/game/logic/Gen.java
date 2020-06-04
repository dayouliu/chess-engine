package game.logic;

import game.data.RC;
import game.data.RCM;
import game.data.State;

import java.util.ArrayList;
import java.util.List;

public class Gen {

    private Validate validate;

    public Gen(Validate validate) {
        this.validate = validate;
    }

    private void genMove(List<List<RCM>> moves, State state, RC s, int dr, int dc, int steps) {
        for(int i = 1; i <= steps; i++) {
            RC e = new RC(s.r + dr * i, s.c + dc * i);
            if(!Util.bound(e)) break;
            List<RCM> move = validate.validateMove(state, s, e);
            if(!move.isEmpty()) moves.add(move);
            if(!Util.empty(state.board, e)) break;
        }
    }

    private void genMove(List<List<RCM>> moves, State state, RC s, int dr, int dc) {
        genMove(moves, state, s, dr, dc, 8);
    }

    private void genLineMoves(List<List<RCM>> a, State b, RC s) {
        genMove(a, b, s, -1, 0);
        genMove(a, b, s, 1, 0);
        genMove(a, b, s, 0, -1);
        genMove(a, b, s, 0, 1);
    }

    private void genDiagonalMoves(List<List<RCM>> a, State b, RC s) {
        genMove(a, b, s, -1, -1);
        genMove(a, b, s, -1, 1);
        genMove(a, b, s, 1, -1);
        genMove(a, b, s, 1, 1);
    }

    private void genPawnMoves(List<List<RCM>> a, State b, RC s) {
        if(Util.white(b.board, s)) {
            genMove(a, b, s, -1, 0, 1);
            genMove(a, b, s, -2, 0, 1);
            genMove(a, b, s, -1, -1, 1);
            genMove(a, b, s, -1, 1, 1);
        } else {
            genMove(a, b, s, 1, 0, 1);
            genMove(a, b, s, 2, 0, 1);
            genMove(a, b, s, 1, -1, 1);
            genMove(a, b, s, 1, 1, 1);
        }
    }

    private void genKnightMoves(List<List<RCM>> a, State b, RC s) {
        genMove(a, b, s, -1, 2, 1);
        genMove(a, b, s, -1, -2, 1);
        genMove(a, b, s, 1, 2, 1);
        genMove(a, b, s, 1, -2, 1);
        genMove(a, b, s, -2, -1, 1);
        genMove(a, b, s, -2, 1, 1);
        genMove(a, b, s, 2, -1, 1);
        genMove(a, b, s, 2, 1, 1);
    }

    private void genRookMoves(List<List<RCM>> a, State b, RC s) {
        genLineMoves(a, b, s);
    }

    private void genBishopMoves(List<List<RCM>> a, State b, RC s) {
        genDiagonalMoves(a, b, s);
    }

    private void genQueenMoves(List<List<RCM>> a, State b, RC s) {
        genLineMoves(a, b, s);
        genDiagonalMoves(a, b, s);
    }

    private void genKingMoves(List<List<RCM>> a, State b, RC s) {
        genMove(a, b, s, -1, 0, 1);
        genMove(a, b, s, 1, 0, 1);
        genMove(a, b, s, 0, -1, 1);
        genMove(a, b, s, 0, 1, 1);
        genMove(a, b, s, -1, -1, 1);
        genMove(a, b, s, -1, 1, 1);
        genMove(a, b, s, 1, -1, 1);
        genMove(a, b, s, 1, 1, 1);
        genMove(a, b, s, 0, 2, 1);
        genMove(a, b, s, 0, -2, 1);
    }

    public List<List<RCM>> genMoves(State state) {
        List<List<RCM>> moves = new ArrayList<List<RCM>>();
        for(int i = 0; i < Util.row; ++i) {
            for(int j = 0; j < Util.col; ++j) {
                int id = state.board[i][j];
                RC p = new RC(i, j);
                if(id != 0 && Util.white(state.board, p) == state.turn) {
                    if (id == State.PAWNW || id == State.PAWNB) {
                        genPawnMoves(moves, state, p);
                    } else if (id == State.KNIGHTW || id == State.KNIGHTB) {
                        genKnightMoves(moves, state, p);
                    } else if (id == State.BISHOPW || id == State.BISHOPB) {
                        genBishopMoves(moves, state, p);
                    } else if (id == State.ROOKW || id == State.ROOKB) {
                        genRookMoves(moves, state, p);
                    } else if (id == State.QUEENW || id == State.QUEENB) {
                        genQueenMoves(moves, state, p);
                    } else if (id == State.KINGW || id == State.KINGB) {
                        genKingMoves(moves, state, p);
                    }
                }
            }
        }
        return moves;
    }

}
