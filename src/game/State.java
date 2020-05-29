package game;

import data.RC;
import gui.pieces.*;
import main.Application;

import java.awt.*;

public class State {

    public int
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

    private boolean turn = true;
    private boolean check = false;
    private boolean mate = false;
    private boolean draw = false;

    private RC[] pieces;
    private int[][] board;

    public State() {
        pieces = new RC[12];
    }

}
