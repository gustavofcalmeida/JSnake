package br.gfca.jsnake.graphics;

import javax.swing.*;

import br.gfca.jsnake.GlobalVariables;
import br.gfca.jsnake.util.ResourceLoader;

import java.awt.*;

/**
 * 
 * @author Gustavo de Farias Costa Almeida
 * @version 1.0
 */
public class CoordinatePicture extends ImageIcon {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8013514277606920821L;
	
	// atributos
	private short coordX;
	private short coordY;

	/**
	 * 
	 * @param filename
	 * @param linha
	 * @param coluna
	 */
	public CoordinatePicture (String filename, byte linha, byte coluna) {
		super( ResourceLoader.getResourceURL( filename ) );

		coordX = (short)(coluna * GlobalVariables.LADO_BLOCO);
		coordY = (short)(linha * GlobalVariables.LADO_BLOCO);
	}

	/**
	 * 
	 * @param c
	 * @param g
	 */
	public void paintIcon (Component c, Graphics g) {
		super.paintIcon(c, g, coordX, coordY);
	}

	/**
	 * 
	 * @param linha
	 * @param coluna
	 */
	public void mudaCoordenadas (byte linha, byte coluna) {
		coordX = (short)(coluna * GlobalVariables.LADO_BLOCO);
		coordY = (short)(linha * GlobalVariables.LADO_BLOCO);
	}
}