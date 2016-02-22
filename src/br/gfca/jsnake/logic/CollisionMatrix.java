package br.gfca.jsnake.logic;

import br.gfca.jsnake.GlobalVariables;

/**
 * 
 * @author Gustavo de Farias Costa Almeida
 * @version 1.0
 */
public class CollisionMatrix {

	// atributos
	private boolean[][] matrizColisao;

	/**
	 * 
	 * @param linhas
	 */
	public CollisionMatrix (String[] linhas) {

		matrizColisao = new boolean[GlobalVariables.NUM_LINHAS][GlobalVariables.NUM_COLUNAS];

		for (byte l = 0; l < GlobalVariables.NUM_LINHAS; l++) {
			for (byte c = 0; c < GlobalVariables.NUM_COLUNAS; c++) {
				matrizColisao[l][c] = linhas[l].charAt(c) == '1' ? true : false;
			}
		}
	}

	/**
	 * 
	 * @param linha
	 * @param coluna
	 * @return
	 */
	public boolean getValor (byte linha, byte coluna) {
		return matrizColisao[linha][coluna];
	}

	/**
	 * 
	 * @param linha
	 * @param coluna
	 * @param valor
	 */
	public void setValor (byte linha, byte coluna, boolean valor) {
		matrizColisao[linha][coluna] = valor;
	}
}