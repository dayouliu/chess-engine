package main;

import game.data.RC;
import game.data.State;
import game.gen.Attack;
import game.gen.Move;
import game.gen.Validate;
import gui.Assets;
import gui.board.Board;
import tests.Test;

import java.awt.*;

public class Chess extends Application {

    protected Test test;

    protected State state;
    protected Attack attack;
    protected Validate validate;
    protected Move move;

    protected Board board;
    protected Assets assets;

    public Chess() {
        state = new State();
        attack = new Attack();
        validate = new Validate(attack);
        move = new Move(attack, validate);
    }

    protected void init() {
        super.init();
        assets = new Assets();
        board = new Board(this);
        board.init(state);
        super.initrz();
    }

    public void move(RC s, RC e) {
        if(validate.validateMove(state, s, e)) {
            move.move(state, s, e);
            board.move(state, s, e);
        }
    }

    protected void update() {
        board.update();
    }

    protected void render(Graphics g) {
        super.render(g);
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
