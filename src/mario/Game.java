package mario;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import entity.Entity;
import entity.mob.Player;
import graphics.Sprite;
import graphics.SpriteSheet;
import input.KeyInput;

public class Game extends Canvas implements Runnable {


	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 270, HEIGHT = WIDTH * 9 / 16, SCALE = 4;
	public static final String TITLE = "Super Mario Bros.";
	private Thread thread;
	private boolean running;
	public static Handler handler;
	public static SpriteSheet sheet;
	public static Sprite brick, mario[];
	public Camera cam;
	private BufferedImage level1;
	public static Sprite mushroom;
	public static Sprite goomba[];
	
	
	
	public static int getFrameWidth() {
		return WIDTH*SCALE;
	}
	
	public static int getFrameHeight() {
		return HEIGHT*SCALE;
	}
	
	public synchronized void start() {
		if(running) return;
		running = true;
		thread = new Thread(this, "Thread");
		thread.start();
	}
	
	public synchronized void stop() {
		if(!running) return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void init() {
		handler = new Handler();
		
		sheet = new SpriteSheet("/marioSpriteSheet.png");
		brick = new Sprite(sheet, 2, 2);
		mario = new Sprite[8];
		for (int i = 0; i < mario.length; i++) {
			mario[i] = new Sprite(sheet, i+1, 1);
		}
		mushroom = new Sprite(sheet, 3, 2);
		goomba = new Sprite[3];
		goomba[0] = new Sprite(sheet, 4, 2);
		goomba[1] = new Sprite(sheet, 5, 2);
		goomba[2] = new Sprite(sheet, 6, 2);
		
		cam = new Camera();
		
		addKeyListener(new KeyInput());
		
		try {
			level1 = ImageIO.read(getClass().getResource("/level1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		handler.createLevel(level1);
		
	}
	
	public void run() {
		init();
		requestFocus();
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double delta = 0.0;
		double ns = 1000000000.0 / 60.0;
		int frames = 0;
		int ticks = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				ticks++;
				delta--;
			}
			render();
			frames++;
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(frames + " fps " + ticks + " updates/sec");
				frames = 0;
				ticks = 0;
			}
		}
		stop();
	}
	
	public void render() {
		BufferStrategy bs  = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.translate(cam.getX(), cam.getY());
		handler.render(g);
		g.dispose();
		bs.show();
	}
	
	public void tick() {
		handler.tick();
		for (Entity en : handler.entity) {
			if(en.getId() == Id.player) {
				cam.tick(en);
			}
		}
	}
	
	public Game() {
		Dimension size = new Dimension(WIDTH*SCALE, HEIGHT*SCALE);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		JFrame frame = new JFrame(TITLE);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		game.start();
	}


}
