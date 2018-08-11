package entity.powerups;

import java.awt.Graphics;
import java.util.Random;

import entity.Entity;
import mario.Game;
import mario.Handler;
import mario.Id;
import tile.Tile;

public class Mushroom extends Entity {

	private Random rand = new Random();
	
	public Mushroom(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
		int dir = rand.nextInt(2);
		if(dir == 0) setVelX(-3);
		if(dir == 1) setVelX(3);
	}

	public void render(Graphics g) {
		g.drawImage(Game.mushroom.getBufferedImage(), x, y, width, height, null);
	}

	public void tick() {
		x += velX;
		y += velY;
		
		for (Tile ti : handler.tile) {
			if(!solid) break;
			if(ti.getId() == Id.wall) {
				if(getBoundsBottom().intersects(ti.getBounds())) {
					setVelY(0);
					if(falling) falling = false;
				}
				else {
					if(!falling) {
						gravity = 0.0;
						falling = true;
					}
				}
				if(getBoundsLeft().intersects(ti.getBounds())) {
					setVelX(3);
				}
				if(getBoundsRight().intersects(ti.getBounds())) {
					setVelX(-3);
				}
			}
		}
		
		if(falling) {
			gravity += 0.1;
			setVelY((int) gravity);
		}
	}
}
