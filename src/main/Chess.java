package main;

import game.ai.Heuristic;
import game.data.RC;
import game.data.RCM;
import game.data.State;
import game.logic.*;
import gui.Assets;
import gui.board.Board;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Chess {

    public Application app;

    public State state;
    public Attack attack;
    public Movement movement;
    public Validate validate;
    public Gen gen;
    public Flag flag;

    public Heuristic heuristic;

    public Board board;
    public Assets assets;

    public Chess(Application app) {
        this.app = app;
        state = new State();
        attack = new Attack();
        movement = new Movement(attack);
        validate = new Validate(movement);
        gen = new Gen(validate);
        flag = new Flag(gen);
        heuristic = new Heuristic();
    }

    public void init() {
        assets = new Assets();
        board = new Board(app);
        board.init(state);
        state.next = gen.genMoves(state, state.turn);
    }

    public void move(RC s, RC e) {
        List<RCM> m = validate.validateMove(state, s, e);
        if(!m.isEmpty()) {
            movement.move(state, m);
            flag.flag(state);
            board.move(state, s, e);
        }
        /*
        Util.print(state.attack[0]);
    `   System.out.println();
        Util.print(state.attack[1]);
        System.out.println();`
         */
        Util.print(state.board);
        System.out.println("valid: " + !m.isEmpty());
        System.out.println("check: " + state.check);
        System.out.println("mate: " + state.mate);
        System.out.println("heuristic: " + heuristic.heuristic(state));
        System.out.println();
    }

    public void unmove() {
        movement.unmove(state);
    }

    public void update() {
        board.update();
    }

    public void render(Graphics g) {
        board.render(g);
        for(List<RCM> move : state.next) {
            RCM m = move.get(0);
            double len = app.getChess().getBoard().getLen();
            Point tlc = app.getChess().getBoard().getTLC();
            int sc = (int)((tlc.x + m.s.c * len) + len/2);
            int sr = (int)((tlc.y + m.s.r * len) + len/2);
            int ec = (int)((tlc.x + m.e.c * len) + len/2);
            int er = (int)((tlc.y + m.e.r * len) + len/2);
            g.drawLine(sc, sr, ec, er);
        }
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
