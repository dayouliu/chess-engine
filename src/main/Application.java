package main;

import gui.board.Board;
import gui.io.Keys;
import gui.io.Mouse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferStrategy;

public class Application extends Thread {

	protected boolean running = true;

	protected JFrame frame;
	protected Canvas canvas;
	protected int initWidth = 600, initHeight = 600;

	protected BufferStrategy bs;
	protected Graphics g;

	protected Keys keys;
	protected Mouse mouse;

	protected Chess chess;
	
	protected void init() {
		// frame creation
		frame = new JFrame("Chess Engine");
		frame.setSize(initWidth, initHeight);
		frame.setMinimumSize(new Dimension(initWidth, initHeight));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		// canvas creation
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(initWidth, initHeight));
		canvas.setMinimumSize(new Dimension(initWidth, initHeight));
		canvas.setFocusable(false);
		frame.add(canvas);
		frame.pack();

		// input creation
		keys = new Keys();
		mouse = new Mouse();
		frame.addMouseListener(mouse);
		frame.addMouseMotionListener(mouse);
		frame.addMouseWheelListener(mouse);
		canvas.addMouseListener(mouse);
		canvas.addMouseMotionListener(mouse);
		canvas.addMouseWheelListener(mouse);
		frame.addKeyListener(keys);

		// buffer creation
		canvas.createBufferStrategy(3);

		chess = new Chess(this);
		chess.init();

		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent componentEvent) {
				resize();
			}
		});
	}

	protected void resize() {
		chess.resize();
	}

	protected long previous = System.nanoTime();
	protected double fpscap = 60.0;
	protected double timestep = 1000000000.0 / fpscap;
	protected double delta = 0;

	protected long timer = System.nanoTime();
	protected double frames = 0;
	protected double fps = 0;

	public void run() {
		init();
		while(running) {
			long current = System.nanoTime();
			long elapsed = current - previous;
			if(elapsed >= timestep) {
				delta += ((double) elapsed) / timestep;
				previous = current;
				while (delta >= 1) {
					update();
					delta -= 1;
				}
				render();
				++frames;
			}

			long cur = System.nanoTime();
			long diff = cur - timer;
			if(diff > 1000000000) {
				timer = cur;
				fps = frames / ((double)diff / 1000000000);
				frames = 0;
			}
		}
	}

	protected void update() {
		chess.update();
	}

	protected void render(Graphics g) {
		g.drawString(Double.toString(fps),20, 20);
		chess.render(g);
	}
	
	protected void render() {
		bs = canvas.getBufferStrategy();
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, getCanvasWidth(), getCanvasHeight());
		// Render start
		render(g);
		// Render end
		bs.show();
		g.dispose();
	}

	public int getFrameWidth() {
		return frame.getWidth();
	}

	public int getFrameHeight() {
		return frame.getHeight();
	}

	public int getCanvasWidth() {
		return canvas.getWidth();
	}

	public int getCanvasHeight() {
		return canvas.getHeight();
	}

	public Keys getKeys() {
		return keys;
	}

	public Mouse getMouse() {
		return mouse;
	}

	public Chess getChess() {
		return chess;
	}

	public static void main(String[] args) {
		new Application().start();
	}

}
