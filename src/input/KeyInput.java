package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import entity.Entity;
import mario.Game;
import mario.Id;

public class KeyInput implements KeyListener {

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		for (Entity en : Game.handler.entity) {
			if (en.getId() == Id.player) {
				switch (key) {
				case KeyEvent.VK_UP:
					if (!en.jumping) {
						en.jumping = true;
						en.gravity = 10.0;
					}
					break;
				case KeyEvent.VK_LEFT:
					en.setVelX(-5);
					en.facing = 0;
					break;
				case KeyEvent.VK_RIGHT:
					en.setVelX(5);
					en.facing = 1;
					break;
				}
			}

		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		for (Entity en : Game.handler.entity) {
			if (en.getId() == Id.player) {
				switch (key) {
				case KeyEvent.VK_UP:
					en.setVelY(0);
					break;
				case KeyEvent.VK_LEFT:
					en.setVelX(0);
					break;
				case KeyEvent.VK_RIGHT:
					en.setVelX(0);
					break;
				}
			}
		}
	}

	public void keyTyped(KeyEvent e) {}

}
