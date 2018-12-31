package main;

import board.Board;
import io.Keys;
import io.Mouse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferStrategy;

public class Application {

	private boolean running;

	private JFrame frame;
	private Canvas canvas;
	private int initWidth = 600, initHeight = 600;

	private BufferStrategy bs;
	private Graphics g;

	private Keys keys;
	private Mouse mouse;

	private Board board;
	private Assets assets;

	private void start() {
		running = true;
		run();
	}
	
	private void init() {
		// Frame creation
		frame = new JFrame("DY Chess Engine");
		frame.setSize(initWidth, initHeight);
		frame.setMinimumSize(new Dimension(initWidth, initHeight));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		// Canvas creation
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(initWidth, initHeight));
		canvas.setMinimumSize(new Dimension(initWidth, initHeight));
		canvas.setFocusable(false);
		frame.add(canvas);
		frame.pack();

		// Input creation
		keys = new Keys();
		mouse = new Mouse();
		frame.addMouseListener(mouse);
		frame.addMouseMotionListener(mouse);
		frame.addMouseWheelListener(mouse);
		canvas.addMouseListener(mouse);
		canvas.addMouseMotionListener(mouse);
		canvas.addMouseWheelListener(mouse);
		frame.addKeyListener(keys);

		// BS creation
		canvas.createBufferStrategy(3);

		// Game creation
		assets = new Assets();
		board = new Board(this);
		board.init();

		// Resize
		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent componentEvent) {
				resize();
			}
		});
	}

	private double previousTime = System.currentTimeMillis();
	private double lagTime = 0.000;
	private int fpsCap = 60;
	private double timestep = 1000.000 / fpsCap;

	private void run() {
		init();
		while(running) {
			double currentTime = System.currentTimeMillis();
			double elapsedTime = currentTime - previousTime;
			if(elapsedTime >= timestep) {
				previousTime = currentTime;
				lagTime += elapsedTime;
				while (lagTime > timestep) {
					// Update start
					update();
					// Update end
					lagTime -= timestep;
				}
				render();
			}
		}
		stop();
	}

	private void update() {
		board.update();
	}
	
	private void render() {
		bs = canvas.getBufferStrategy();
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, getCanvasWidth(), getCanvasHeight());
		// Render start
		board.render(g);
		// Render end
		bs.show();
		g.dispose();
	}

	private void resize() {
		board.resize();
	}
	
	private void stop() {
		running = false;
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

	public Assets getAssets() {
		return assets;
	}

	public Board getBoard() {
		return board;
	}

	public static void main(String[] args) {
		new Application().start();
	}
	
}
