package br.gfca.jsnake.util;

import java.net.URL;

import javax.swing.ImageIcon;

public class ResourceLoader {
	
	private static final String RESOURCE_PATH = "br/gfca/jsnake/img/";
	private static final String SCENARIO_PATH = "br/gfca/jsnake/scenario/";
	private static final String SOUND_PATH = "br/gfca/jsnake/sound/";
	
	/**
	 * Obtém uma imagem que está como um recurso
	 * dentro das pastas de código fonte.
	 * @param nome o nome do arquivo de imagem.
	 * @return A imagem.
	 */
	public static ImageIcon getResourceImage( String nome ) {
		return new ImageIcon( ResourceLoader.class.getClassLoader().getResource( ResourceLoader.RESOURCE_PATH + nome ) );
	}
	
	public static ImageIcon getScenarioImage( String nome ) {
		return new ImageIcon( ResourceLoader.class.getClassLoader().getResource( ResourceLoader.SCENARIO_PATH + nome ) );
	}
	
	public static URL getSoundURL( String filename ) {
		return ResourceLoader.class.getClassLoader().getResource( ResourceLoader.SOUND_PATH + filename );
	}

	public static URL getResourceURL(String filename) {
		return ResourceLoader.class.getClassLoader().getResource( ResourceLoader.RESOURCE_PATH + filename );
	}

	public static URL getScenarioURL(String filename) {
		return ResourceLoader.class.getClassLoader().getResource( ResourceLoader.SCENARIO_PATH + filename );
	}
}