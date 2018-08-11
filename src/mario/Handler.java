package mario;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import entity.Entity;
import entity.mob.Goomba;
import entity.mob.Player;
import entity.powerups.Mushroom;
import tile.Tile;
import tile.Wall;

public class Handler {

	public LinkedList<Entity> entity = new LinkedList<Entity>();
	public LinkedList<Tile> tile = new LinkedList<Tile>();
	
	public Handler() {
//		createLevel();
	}
	
	public void createLevel(BufferedImage level) {
//		for (int i = 0; i < Game.WIDTH*Game.SCALE/64 + 1; i++) {
//			addTile(new Wall(i*64, Game.HEIGHT*Game.SCALE - 64, 64, 64, true, Id.wall, this));
//			if(i != 0 && i != 1 && i != 14 && i != 15 && i != 16) {
//				addTile(new Wall(i*64, 300, 64, 64, true, Id.wall, this));
//			}
//		}
		
		int width = level.getWidth();
		int height = level.getHeight();
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int pixel = level.getRGB(x, y);
				
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				if(red == 0 && green == 0 && blue == 0) addTile(new Wall(x*64, y*64, 64, 64, true, Id.wall, this));
				if(red == 0 && green == 0 && blue == 255) addEntity(new Player(x*64, y*64, 64, 64, true, Id.player, this));
				if(red == 255 && green == 0 && blue == 0) addEntity(new Mushroom(x*64, x*64, 64, 64, true, Id.mushroom, this));
				if(red == 0 && green == 255 && blue == 0) addEntity(new Goomba(x*64, x*64, 64, 64, true, Id.goomba, this));
			}
		}
	}
	
	public void render(Graphics g) {
		for (Entity en : entity) {
			en.render(g);
		}
		for (Tile ti : tile) {
			ti.render(g);
		}
	}
	
	public void tick() {
		
		for (int i = 0; i < entity.size(); i++) {
			entity.get(i).tick();
		}
		for (int i = 0; i < tile.size(); i++) {
			tile.get(i).tick();
		}

	}
	
	public void addEntity(Entity en) {
		entity.add(en);
	}
	
	public void removeEntity(Entity en) {
		entity.remove(en);
	}
	
	public void addTile(Tile ti) {
		tile.add(ti);
	}
	
	public void removeTile(Tile ti) {
		tile.remove(ti);
	}
	
}
