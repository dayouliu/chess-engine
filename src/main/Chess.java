package main;

import game.ai.Engine;
import game.ai.Heuristic;
import game.data.RC;
import game.data.RCM;
import game.data.ChessState;
import game.logic.*;
import gui.Assets;
import gui.board.Board;

import java.awt.*;
import java.util.List;

public class Chess {

    private Application app;

    private ChessState state;
    private Attack attack;
    private Move1 move1;
    private Move2 move2;
    private Validate validate;
    private Gen gen;
    private Flag flag;
    private Heuristic heuristic;
    private Engine engine;

    public Board board;
    public Assets assets;

    private boolean turn = true;

    public Chess(Application app) {
        this.app = app;
        attack = new Attack();
        move1 = new Move1(attack);
        validate = new Validate(move1);
        gen = new Gen(validate);
        flag = new Flag();
        move2 = new Move2(move1, validate, gen, flag);
        heuristic = new Heuristic();
        engine = new Engine(heuristic, gen, move2);
        state = new ChessState();
        state.nexts.add(gen.genMoves(state, state.turn));
    }

    public void init() {
        assets = new Assets();
        board = new Board(app);
        board.init(state);
    }

    public void move(RC s, RC e) {
        if(turn) {
            List<RCM> move = validate.validateMove(state, s, e);
            if (!move.isEmpty()) {
                move2.move(state, move);
                turn = false;
                if (!state.mate && !state.draw) {
                    engine.calc(state);
                }
            }
        }

        board.move(state);
        Util.print(state.board);
        System.out.println("mate: " + state.mate);
        System.out.println("draw: " + state.draw);
        System.out.println("heuristic: " + heuristic.heuristic(state));
        System.out.println();
    }

    public void update() {
        board.update();

        if(!turn && !state.mate && !state.draw) {
            List<RCM> m = engine.move(state);
            if(m != null) {
                Util.print(state.board);
                System.out.println("mate: " + state.mate);
                System.out.println("draw: " + state.draw);
                System.out.println("heuristic: " + heuristic.heuristic(state));
                move2.move(state, m);
                turn = true;
                board.move(state);
                System.out.println();
            }
        }
    }

    public void render(Graphics g) {
        board.render(g);
        List<List<RCM>> next = Util.next(state);

        /*
        if(next != null) {
            for (List<RCM> move : next) {
                RCM m = move.get(0);
                double len = app.getChess().getBoard().getLen();
                Point tlc = app.getChess().getBoard().getTLC();
                int sc = (int) ((tlc.x + m.s.c * len) + len / 2);
                int sr = (int) ((tlc.y + m.s.r * len) + len / 2);
                int ec = (int) ((tlc.x + m.e.c * len) + len / 2);
                int er = (int) ((tlc.y + m.e.r * len) + len / 2);
                g.drawLine(sc, sr, ec, er);
            }
        }
         */

        RCM last = Util.last(state);
        if(turn && last != null) {
            double len = app.getChess().getBoard().getLen();
            Point tlc = app.getChess().getBoard().getTLC();
            int sc = (int) ((tlc.x + last.s.c * len) + len / 2);
            int sr = (int) ((tlc.y + last.s.r * len) + len / 2);
            int ec = (int) ((tlc.x + last.e.c * len) + len / 2);
            int er = (int) ((tlc.y + last.e.r * len) + len / 2);
            g.drawLine(sc, sr, ec, er);
        }

        /*
        if(!turn) {
            g.setFont(new Font("", Font.PLAIN, 20));
            g.drawString("Thinking...", app.getCanvasWidth()/2-50, 50);
        }
        */
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

    public ChessState getState() {
        return state;
    }

    public void setState(ChessState state) {
        this.state = state;
    }

}
