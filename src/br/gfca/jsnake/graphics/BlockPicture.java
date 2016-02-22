package br.gfca.jsnake.graphics;

/**
 * 
 * @author Gustavo de Farias Costa Almeida
 * @version 1.0
 */
public class BlockPicture extends CoordinatePicture {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4167543348737565376L;

	/**
	 * 
	 * @param linha
	 * @param coluna
	 */
	public BlockPicture (byte linha, byte coluna) {
		super( "block.png", linha, coluna );
	}
}