package br.gfca.jsnake.util;

import java.applet.*;
import java.net.URL;

import br.gfca.jsnake.GlobalVariables;

/**
 * 
 * @author Gustavo de Farias Costa Almeida
 * @version 1.0
 */
public class AudioPlayer extends Applet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 609343691682202595L;
	
	// atributos
	private AudioClip colisaoClip;
	private AudioClip alimentoClip;

	/**
	 * 
	 */
	public AudioPlayer () {
		byte num = (byte)(Math.random() * GlobalVariables.NUM_SONS_COLISAO);
		URL url1 = ResourceLoader.getSoundURL("collision" + num + ".wav");
		colisaoClip = Applet.newAudioClip( url1 );
		
		num = (byte)(Math.random() * GlobalVariables.NUM_SONS_ALIMENTO);
		URL url2 = ResourceLoader.getSoundURL("food" + num + ".wav");
		alimentoClip = Applet.newAudioClip( url2 );
	}

	/**
	 * 
	 */
	public void tocaColisao () {
		colisaoClip.play();
	}

	/**
	 * 
	 */
	public void tocaAlimento () {
		alimentoClip.play();
	}
}