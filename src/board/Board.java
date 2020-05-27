package board;

import datastruct.RC;
import main.Application;
import manager.Manager;

import java.awt.*;

public class Board {
	
	private Application app;
	private int row = 8, col = 8;
	private double scale = 1.0;
	private double len = 50;
	private Point tlc = new Point(0,0);
	private double pieceLen = 0.8;
	
	private Square[][] board;
	private Manager manager;


	public Board(Application app) {
		this.app = app;
		board = new Square[row][col];
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				board[i][j] = new Square(app, i, j);
			}
		}
		calc();
		manager = new Manager(app);
	}

	public void init() {
		calc();
		manager.init();
	}

	public void update() {
		manager.update();
	}

	public void render(Graphics g) {
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				board[i][j].render(g);
			}
		}
		manager.render(g);
	}

	public void resize() {
		calc();
	}

	private void calc() {
		len = (int)(Math.min(app.getCanvasWidth(), app.getCanvasHeight()) / Math.max(row, col) * 0.6);
		int centerX = app.getCanvasWidth() / 2;
		int centerY = (int)(app.getCanvasHeight() * 0.45);
		tlc.x = (int) (centerX - (col / 2) * len);
		tlc.y = (int) (centerY - (row / 2) * len);
	}

	private void cam() {

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

	public Manager getManager() {
		return manager;
	}

	public Square getSquare(RC pos) {
		return board[pos.r][pos.c];
	}

}
