package entity.mob;

import java.awt.Graphics;

import entity.Entity;
import mario.Game;
import mario.Handler;
import mario.Id;
import tile.Tile;

public class Goomba extends Entity {

	private int frame = 0;
	private int frameDelay = 0;
	
	public Goomba(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
		setVelX(3);
	}

	public void render(Graphics g) {
		g.drawImage(Game.goomba[frame].getBufferedImage(), x, y, width, height, null);
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
		
		if(velX != 0) {
			frameDelay++;
			if(frameDelay >= 8) {
				frame++;
				if(frame >= 2) {
					frame = 0;
				}
				frameDelay = 0;
			}
		}
	}
}
