package main;

import game.data.RC;
import game.data.RCM;
import game.data.State;
import game.gen.Attack;
import game.gen.Move;
import game.gen.Util;
import game.gen.Validate;
import gui.Assets;
import gui.board.Board;
import tests.Test;

import java.awt.*;
import java.util.List;

public class Chess {

    public Application app;

    public State state;
    public Attack attack;
    public Validate validate;
    public Move move;

    public Board board;
    public Assets assets;

    public Chess(Application app) {
        this.app = app;
        state = new State();
        attack = new Attack();
        move = new Move(attack);
        validate = new Validate(move);
    }

    public void init() {
        assets = new Assets();
        board = new Board(app);
        board.init(state);
    }

    public void move(RC s, RC e) {
        List<RCM> m = validate.validateMove(state, s, e);
        if(!m.isEmpty()) {
            move.move(state, m);
            board.move(state, s, e);
        }
        Util.print(state.board);
        System.out.println();
    }

    public void unmove() {
        move.unmove(state, !state.turn);
    }

    public void update() {
        board.update();
    }

    public void render(Graphics g) {
        board.render(g);
    }

    public void resize() {
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

}
