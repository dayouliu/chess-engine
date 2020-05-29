package main;

import game.data.State;
import gui.Assets;
import gui.board.Board;
import tests.Test;

import java.awt.*;

public class Chess extends Application {

    protected Test test;
    protected Board board;
    protected Assets assets;

    protected State state;

    protected void init() {
        super.init();
        assets = new Assets();
        board = new Board(this);
        board.init();
        super.initrz();
        test.init();
    }

    protected void update() {
        board.update();
    }

    protected void render(Graphics g) {
        board.render(g);
    }

    protected void resize() {
        board.resize();
    }

    public Assets getAssets() {
        return assets;
    }

    public Board getBoard() {
        return board;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public static void main(String[] args) {
        new Chess().start();
    }

}
