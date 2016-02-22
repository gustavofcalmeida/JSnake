package br.gfca.jsnake.input;

import java.awt.event.*;

/**
 * 
 * @author Gustavo de Farias Costa Almeida
 * @version 1.0
 */
public class KeyboardListener extends KeyAdapter {

	// atributos
	private byte vertical;
	private byte horizontal;
	public boolean consumiu;

	/**
	 * 
	 */
	public KeyboardListener () {
		reset();
	}

	/**
	 * 
	 * @param e
	 */
	public void keyPressed (KeyEvent e) {
		if (consumiu) {
			switch ((byte)e.getKeyCode()) {
				case (byte)KeyEvent.VK_RIGHT: {
					if (vertical != 0) {
						consumiu = false;
						vertical = 0;
						horizontal = 1;
					}
					break;
				}
				case (byte)KeyEvent.VK_UP: {
					if (horizontal != 0) {
						consumiu = false;
						vertical = -1;
						horizontal = 0;
					}
					break;
				}
				case (byte)KeyEvent.VK_DOWN: {
					if (horizontal != 0) {
						consumiu = false;
						vertical = 1;
						horizontal = 0;
					}
					break;
				}
				case (byte)KeyEvent.VK_LEFT: {
					if (vertical != 0) {
						consumiu = false;
						vertical = 0;
						horizontal = -1;
					}
					break;
				}
			}
		}
	}

	/**
	 * 
	 */
	public void reset () {
		vertical = 0;
		horizontal = 1;
	}

	/**
	 * 
	 * @return
	 */
	public byte getHorizontal () {
		return horizontal;
	}

	/**
	 * 
	 * @return
	 */
	public byte getVertical () {
		return vertical;
	}

	/**
	 * 
	 */
	public void setConsumido () {
		consumiu = true;
	}
}