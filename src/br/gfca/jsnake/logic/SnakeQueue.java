package br.gfca.jsnake.logic;

import java.util.*;

import br.gfca.jsnake.GlobalVariables;

/**
 * 
 * @author Gustavo de Farias Costa Almeida
 * @version 1.0
 */
public class SnakeQueue {

	// atributos
	private LinkedList<Block> filaCobra;

	/**
	 * 
	 * @param linhaInicial
	 * @param colunaInicial
	 */
	public SnakeQueue (byte linhaInicial, byte colunaInicial) {
		filaCobra = new LinkedList<Block>();

		for (byte i = 1; i <= GlobalVariables.TAMANHO_INICIAL_COBRA; i++) {
			filaCobra.add(new Block(linhaInicial, colunaInicial--));
		}
	}

	/**
	 * 
	 * @return
	 */
	public Block getPrimeiro () {
		return filaCobra.getFirst();
	}

	/**
	 * 
	 * @return
	 */
	public Block getUltimo () {
		return filaCobra.getLast();
	}

	/**
	 * 
	 * @param bloco
	 */
	public void addBloco (Block bloco) {
		filaCobra.addFirst(bloco);
	}

	/**
	 * 
	 * @return
	 */
	public Block removeBloco () {
		return filaCobra.removeLast();
	}
}