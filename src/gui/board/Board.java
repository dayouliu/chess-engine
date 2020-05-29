package gui.board;

import data.RC;
import gui.pieces.*;
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
	private List<Piece> piecesAdd = new ArrayList<Piece>();
	private boolean selected = false;
	private Piece selectedPiece;

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

	public void addPiece(Piece piece) {
		piecesAdd.add(piece);
	}

	public void init() {
		resize();
		for(int i = 0; i < row; i++) {
			addPiece(new Pawn(app, true, row-2, i));
			addPiece(new Pawn(app, false, 1, i));
		}
		addPiece(new Rook(app, true, row-1, 0));
		addPiece(new Knight(app, true, row-1, 1));
		addPiece(new Bishop(app, true, row-1, 2));
		addPiece(new Queen(app, true, row-1, 3));
		addPiece(new King(app, true, row-1, 4));
		addPiece(new Bishop(app, true, row-1, 5));
		addPiece(new Knight(app, true, row-1, 6));
		addPiece(new Rook(app, true, row-1, 7));
		addPiece(new Rook(app, false, 0, 0));
		addPiece(new Knight(app, false, 0, 1));
		addPiece(new Bishop(app, false, 0, 2));
		addPiece(new Queen(app, false, 0, 3));
		addPiece(new King(app, false, 0, 4));
		addPiece(new Bishop(app, false, 0, 5));
		addPiece(new Knight(app, false, 0, 6));
		addPiece(new Rook(app, false, 0, 7));
	}

	public void update() {
		List<Piece> piecesUpdate = new ArrayList<Piece>();
		for(Piece piece : new ArrayList<Piece>(pieces)) {
			piece.update();
			if(!piece.getRemove()) {
				piecesUpdate.add(piece);
			}
		}
		for(Piece piece : piecesAdd) {
			piecesUpdate.add(piece);
		}
		piecesAdd.clear();
		pieces = piecesUpdate;
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
		if(selected && selectedPiece != null) {
			selectedPiece.render(g);
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

	public void setSelected(boolean selected, Piece selectedPiece) {
		this.selected = selected;
		this.selectedPiece = selectedPiece;
	}

	public boolean isSelected() {
		return selected;
	}

}
