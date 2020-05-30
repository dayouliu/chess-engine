package main;

import gui.Assets;
import gui.board.Board;
import gui.io.Keys;
import gui.io.Mouse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferStrategy;

public class Application {

	protected boolean running;

	protected JFrame frame;
	protected Canvas canvas;
	protected int initWidth = 600, initHeight = 600;

	protected BufferStrategy bs;
	protected Graphics g;

	protected Keys keys;
	protected Mouse mouse;

	protected void start() {
		running = true;
		run();
	}
	
	protected void init() {
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
	}

	protected void resize() {}

	protected void initrz() {
		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent componentEvent) {
				resize();
			}
		});
	}

	protected long previous = System.nanoTime();
	protected double fpscap = 60.0;
	protected double timestep = 1000000000.0 / fpscap;
	protected double delta = 0;

	protected long timer = System.currentTimeMillis();
	protected double frames = 0;
	protected double fps = 0;

	protected void run() {
		init();
		while(running) {
			long current = System.nanoTime();
			long elapsed = current - previous;
			if(elapsed > timestep) {
				delta += ((double) elapsed) / timestep;
				previous = current;
				while (delta >= 1) {
					update();
					delta -= 1;
				}
				render();
				++frames;
			}

			long cur = System.currentTimeMillis();
			long diff = cur - timer;
			if(diff > 1000) {
				timer = cur;
				fps = frames / ((double)diff / 1000);
				frames = 0;
			}
		}
		stop();
	}

	protected void update() {}

	protected void render(Graphics g) {
		g.drawString(Double.toString(fps),20, 20);
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
	
	protected void stop() {
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

}
