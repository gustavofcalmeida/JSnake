package br.gfca.jsnake.graphics;

import java.util.*;
import java.awt.*;

import br.gfca.jsnake.GlobalVariables;

/**
 * 
 * @author Gustavo de Farias Costa Almeida
 * @version 1.0
 */
public class PicturesQueue {

	// atributos
	private LinkedList<CoordinatePicture> filaFiguras;

	/**
	 * 
	 * @param linhaInicial
	 * @param colunaInicial
	 */
	public PicturesQueue (byte linhaInicial, byte colunaInicial) {
		filaFiguras = new LinkedList<CoordinatePicture>();

		for (byte i = 1; i <= GlobalVariables.TAMANHO_INICIAL_COBRA; i++) {
			filaFiguras.add(new BlockPicture(linhaInicial, colunaInicial--));
		}
	}

	/**
	 * 
	 * @param linha
	 * @param coluna
	 */
	public void transladaFigura (byte linha, byte coluna) {
		filaFiguras.getLast().mudaCoordenadas(linha, coluna);
		filaFiguras.addFirst(filaFiguras.removeLast());
	}

	/**
	 * 
	 * @param linha
	 * @param coluna
	 */
	public void adicionaFigura (byte linha, byte coluna) {
		filaFiguras.addFirst(new BlockPicture(linha, coluna));
	}

	/**
	 * 
	 * @param c
	 * @param g
	 */
	public void paintIcons (Component c, Graphics g) {
		Iterator<CoordinatePicture> iterador = filaFiguras.iterator();

		while (iterador.hasNext()) {
			iterador.next().paintIcon(c, g);
		}
	}
}