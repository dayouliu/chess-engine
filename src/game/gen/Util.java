package game.gen;

import game.data.RC;

public class Util {

    public static int row = 8, col = 8;
    private int[] rdir = {1, -1, 1, -1};
    private int[] cdir = {-1, 1, 1, -1};

    public static int dr(RC s, RC e) {
        return e.r - s.r;
    }

    public static int dc(RC s, RC e) {
        return e.c - s.c;
    }

    public static int dir(int n) {
        if(n < 0) return -1; if(n > 0) return 1; return 0;
    }

    public static boolean white(int[][] board, RC p) {
        return board[p.r][p.c] <= 6;
    }

    public static boolean opposite(int[][] board, RC s, RC e) {
        return white(board, s) != white(board, e);
    }

    public static boolean unmoved(RC s, RC e) {
        return s.equals(e);
    }

    public static boolean bound(RC e) {
        return  0 <= e.r && e.r < row && 0 <= e.c && e.c < col;
    }

    public static boolean empty(int[][] board, RC e) {
        return board[e.r][e.c] == 0;
    }

}
