package br.gfca.jsnake.util;

import java.io.*;

import br.gfca.jsnake.GlobalVariables;
import br.gfca.jsnake.graphics.PicturesQueue;
import br.gfca.jsnake.logic.SnakeQueue;
import br.gfca.jsnake.logic.CollisionMatrix;

/**
 * 
 * @author Gustavo de Farias Costa Almeida
 * @version 1.0
 */
public class ScenarioReader {

	// atributos
	private String[] linhasCenario;
	private byte linhaCabeca;
	private byte colunaCabeca;

	/**
	 * 
	 * @param arquivo
	 */
	public ScenarioReader ( InputStream input ) {

		linhasCenario = new String[GlobalVariables.NUM_LINHAS];

		try {
			BufferedReader in = new BufferedReader( new InputStreamReader( input ) );

			for (byte i = 0; i < GlobalVariables.NUM_LINHAS; i++) {
				linhasCenario[i] = in.readLine();
			}

			linhaCabeca = Byte.parseByte(in.readLine());
			colunaCabeca = Byte.parseByte(in.readLine());

			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * 
	 * @return
	 */
	public CollisionMatrix getMatrizColisao () {
		return new CollisionMatrix(linhasCenario);
	}

	/**
	 * 
	 * @return
	 */
	public SnakeQueue getFilaCobra () {
		return new SnakeQueue(linhaCabeca, colunaCabeca);
	}

	/**
	 * 
	 * @return
	 */
	public PicturesQueue getFilaFiguras () {
		return new PicturesQueue(linhaCabeca, colunaCabeca);
	}
}