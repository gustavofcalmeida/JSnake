package br.gfca.jsnake.logic;

/**
 * 
 * @author Gustavo de Farias Costa Almeida
 * @version 1.0
 */
public class Block {

	// atributos
	public final byte linha;
	public final byte coluna;

	/**
	 * 
	 * @param linha
	 * @param coluna
	 */
	public Block (byte linha, byte coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
}