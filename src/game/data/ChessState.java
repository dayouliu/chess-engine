package game.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChessState {

    public static int
            EMPTY = 0,
            PAWNW = 1,
            KNIGHTW = 2,
            BISHOPW = 3,
            ROOKW = 4,
            QUEENW = 5,
            KINGW = 6,
            PAWNB = 7,
            KNIGHTB = 8,
            BISHOPB = 9,
            ROOKB = 10,
            QUEENB = 11,
            KINGB = 12;

    public boolean turn = true;
    public boolean mate = false;
    public boolean draw = false;

    public int[][] moved = new int[8][8];
    public int[][][] attacked = new int[2][8][8];
    public List<List<RCM>> moves = new ArrayList<List<RCM>>();
    public List<List<List<RCM>>> nexts = new ArrayList<List<List<RCM>>>();

    public int[][] board = {{10, 8, 9, 11, 12, 9, 8, 10},
                            {7, 7, 7, 7, 7, 7, 7, 7},
                            {0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0},
                            {1, 1, 1, 1, 1, 1, 1, 1},
                            {4, 2, 3, 5, 6, 3, 2, 4}};

    public ChessState copy() {
        ChessState state = new ChessState();
        for(int i = 0; i < 8; ++i)
            state.board[i] = Arrays.copyOf(board[i], 8);
        for(int i = 0; i < 2; ++i)
            for(int j = 0; j < 8; ++j)
                state.attacked[i][j] = Arrays.copyOf(attacked[i][j], 8);
        state.moves = new ArrayList<>(moves);
        state.nexts = new ArrayList<>();
        for(List<List<RCM>> next : nexts) state.nexts.add(new ArrayList<>(next));
        return state;
    }

}
