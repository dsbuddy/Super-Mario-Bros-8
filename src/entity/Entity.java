package entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import mario.Handler;
import mario.Id;

public abstract class Entity {
	
	public int x, y, width, height, velX, velY;
	public boolean solid;
	public Id id;
	public Handler handler;
	public boolean jumping = false, falling = true;
	public double gravity = 0.0;
	public int facing = 1; //0 = left, 1 = right
	
	
	public Entity(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height; 
		this.solid = solid;
		this.id = id;
		this.handler = handler;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	
	public Rectangle getBoundsTop() {
		return new Rectangle(x + 10, y, width - 20, 5);
	}
	
	public Rectangle getBoundsBottom() {
		return new Rectangle(x + 10, y + height - 5, width - 20, 5);
	}
	
	public Rectangle getBoundsLeft() {
		return new Rectangle(x, y + 10, 5, height - 20);
	}
	
	public Rectangle getBoundsRight() {
		return new Rectangle(x + width - 5, y + 10, 5, height - 20);
	}
	
	
	public abstract void render(Graphics g);
	public abstract void tick();
	
	public void die() {
		handler.removeEntity(this);
	}
	
	public Id getId() {
		return id;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getVelX() {
		return velX;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public int getVelY() {
		return velY;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}

	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}
}
