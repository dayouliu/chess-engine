package gui.board;

import game.data.RC;
import game.data.State;
import main.Chess;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Board {
	
	private Chess app;
	private int row = 8, col = 8;
	private double scale = 1.0;
	private double len = 50;
	private Point tlc = new Point(0,0);
	private double pieceLen = 0.8;
	
	private Square[][] board;
	private List<Piece> pieces = new ArrayList<Piece>();
	private List<Piece> piecesa = new ArrayList<Piece>();
	private Piece selected;

	public Board(Chess app) {
		this.app = app;
		board = new Square[row][col];
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				board[i][j] = new Square(app, i, j);
			}
		}
	}

	public void resize() {
		len = (int)(Math.min(app.getCanvasWidth(), app.getCanvasHeight()) / Math.max(row, col) * 0.6);
		int centerX = app.getCanvasWidth() / 2;
		int centerY = (int)(app.getCanvasHeight() * 0.45);
		tlc.x = (int) (centerX - (col / 2) * len);
		tlc.y = (int) (centerY - (row / 2) * len);
	}

	public void init(State s) {
		set(s);
		resize();
	}

	public void set(State state) {
		int[][] board = state.board;
		for(int i = 0; i < row; ++i) {
			for(int j = 0; j < col; ++j) {
				int id = board[i][j];
				if(id > 0) {
					piecesa.add(new Piece(app, id, i, j));
				}
			}
		}
	}

	public void move(State state, RC s, RC e) {
		for(Piece p : pieces) {
			if(p.pos.equals(e)) p.remove();
		}
		for(Piece p : pieces) {
			if(p.pos.equals(s)) p.pos = e;
		}
		for(Piece p : pieces) {
			if(state.board[p.pos.r][p.pos.c] == 0) p.remove();
		}
	}

	public void update() {
		List<Piece> piecesu = new ArrayList<Piece>();
		for(Piece piece : new ArrayList<Piece>(pieces)) {
			piece.update();
			if(!piece.isRemove()) {
				piecesu.add(piece);
			}
		}
		for(Piece piece : piecesa) {
			piecesu.add(piece);
		}
		piecesa.clear();
		pieces = piecesu;
		for(Piece p : pieces) p.update();
	}

	public void render(Graphics g) {
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				board[i][j].render(g);
			}
		}
		for(Piece piece : new ArrayList<Piece>(pieces)) {
			if(!piece.isPieceSelected()) {
				piece.render(g);
			}
		}
		if(selected != null) {
			selected.render(g);
		}
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public double getLen() {
		return len;
	}

	public Point getTLC() {
		return tlc;
	}

	public double getPieceLen() {
		return pieceLen;
	}

	public Square getSquare(RC pos) {
		return board[pos.r][pos.c];
	}

	public List<Piece> getPieces() {
		return pieces;
	}

	public void setSelected(Piece selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected != null;
	}

}
