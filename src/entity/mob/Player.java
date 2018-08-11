package entity.mob;

import java.awt.Graphics;

import entity.Entity;
import mario.Game;
import mario.Handler;
import mario.Id;
import tile.Tile;

public class Player extends Entity {

	private int frame = 0;
	private int frameDelay = 0;
	
	
	public Player(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
	}

	public void render(Graphics g) {
		if(facing == 0) {
			g.drawImage(Game.mario[frame+4].getBufferedImage(), x, y, width, height, null);
		}
		else if (facing == 1) {
			g.drawImage(Game.mario[frame].getBufferedImage(), x, y, width, height, null);
		}
	}

	public void tick() {
		x += velX;
		y += velY;
				
		for (Tile ti : handler.tile) {
			if(!solid) continue;
			if(ti.getId() == Id.wall) {
				if(getBoundsTop().intersects(ti.getBounds())) {
					setVelY(0);
					if(jumping) {
						jumping = false;
						gravity = 0.0;
						falling = true;
					}
				}
				if(getBoundsBottom().intersects(ti.getBounds())) {
					setVelY(0);
					if(falling) falling = false;
				}
				else {
					if(!falling && !jumping) {
						gravity = 0.0;
						falling = true;
					}
				}
				if(getBoundsLeft().intersects(ti.getBounds())) {
					setVelX(0);
					x = ti.getX() + ti.getWidth();
				}
				if(getBoundsRight().intersects(ti.getBounds())) {
					setVelX(0);
					x = ti.getX() - getWidth();
				}
			}
		}
		
		for (int i = 0; i < handler.entity.size(); i++) {
			Entity en = handler.entity.get(i);
			if(en.getId() == Id.mushroom) {
				if(getBounds().intersects(en.getBounds())) {
					x -= width;
					y -= height;
					width *= 2;
					height *= 2;
					en.die();
				}
			}
			else if (en.getId() == Id.goomba) {
				if(getBoundsBottom().intersects(en.getBounds())) en.die();
				else if(getBounds().intersects(en.getBounds())) die();
			}
		}
		
		if(jumping) {
			gravity -= 0.1;
			setVelY((int) -gravity);
			if(gravity <= 0.0) {
				jumping = false;
				falling = true;
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
				if(frame >= 4) {
					frame = 0;
				}
				frameDelay = 0;
			}
		}
		
	}
}
