package br.gfca.jsnake.gui;

import javax.swing.*;

import br.gfca.jsnake.GlobalVariables;
import br.gfca.jsnake.graphics.CoordinatePicture;
import br.gfca.jsnake.graphics.PicturesQueue;
import br.gfca.jsnake.input.KeyboardListener;
import br.gfca.jsnake.logic.Block;
import br.gfca.jsnake.logic.SnakeQueue;
import br.gfca.jsnake.logic.CollisionMatrix;
import br.gfca.jsnake.util.AudioPlayer;
import br.gfca.jsnake.util.ResourceLoader;
import br.gfca.jsnake.util.ScenarioReader;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * 
 * @author Gustavo de Farias Costa Almeida
 * @version 1.0
 */
public class Game extends JFrame implements ActionListener, Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5933062449262050476L;
	
	// atributos
	private Timer timer;
	private SnakeQueue filaCobra;
	private CollisionMatrix matrizColisao;
	private boolean colidiuAlimento;
	private byte linhaAlimento;
	private byte colunaAlimento;
	private short pontuacao;
	private int numCenario;
	private String nomeCenario;	
	private KeyboardListener ouvinteTeclado;

	private Graficos graficos;
	private PicturesQueue filaFiguras;
	private ImageIcon figuraCenario;
	private CoordinatePicture figuraAlimento;

	private AudioPlayer audioPlayer;

	/**
	 * 
	 */
	public Game () {
		super(java.util.ResourceBundle.getBundle("br/gfca/jsnake/local_strings").getString("JG_JSnake"));

		graficos = new Graficos();
		configuraJanela();

		timer = new Timer(0, this);
		ouvinteTeclado = new KeyboardListener();
		addKeyListener(ouvinteTeclado);
		new OptionsMenu(this);
	}

	/**
	 * 
	 */
	private void configuraJanela () {
		getContentPane().add(graficos, BorderLayout.CENTER);

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evento) {
				timer.stop();
				dispose();
				new OptionsMenu(Game.this);
			}
		});

		ImageIcon i = ResourceLoader.getResourceImage("icon.png");
		setIconImage(i.getImage());

		setResizable(false);
		pack();
		setSize(725 + getInsets().left + getInsets().right, 475 + getInsets().top + getInsets().bottom);
		setLocationRelativeTo(null);
	}

	/**
	 * 
	 * @param nomeCenario
	 * @param delay
	 */
	public void iniciaJogo (int numCenario, String nomeCenario, int delay) {
		this.numCenario = numCenario;
		this.nomeCenario = nomeCenario;
		timer.setDelay(delay);
		audioPlayer = new AudioPlayer();
		figuraCenario = ResourceLoader.getScenarioImage( this.numCenario + ".png");
		figuraAlimento = new CoordinatePicture( "food" + (byte)(Math.random() * GlobalVariables.NUM_FIGURAS_ALIMENTO) + ".gif",
							(byte)-10, (byte)-10);
		pontuacao = 0;
		atualizaTitulo();
		ouvinteTeclado.reset();
		colidiuAlimento = false;

		ScenarioReader lc = null;
		try {
			lc = new ScenarioReader( ResourceLoader.getScenarioURL(numCenario + ".jsn").openStream() );
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}

		filaCobra = lc.getFilaCobra();
		matrizColisao = lc.getMatrizColisao();
		filaFiguras = lc.getFilaFiguras();

		this.setVisible(true);
		graficos.repaint();
		timer.start();
		geraAlimento();
	}

	/**
	 * 
	 * @param evento
	 */
	public void actionPerformed (ActionEvent evento) {
		if (colidiuAlimento) {
			colidiuAlimento = false;
			adicionaBloco();

			if (matrizColisao.getValor(filaCobra.getPrimeiro().linha,
									   filaCobra.getPrimeiro().coluna)) {
				audioPlayer.tocaColisao();
				timer.stop();

				int resposta = decideReinicio();
				dispose();

				if (resposta == 0) {
					iniciaJogo( this.numCenario, this.nomeCenario, timer.getDelay());
				} else {
					new OptionsMenu(this);
				}
			} else {
				filaFiguras.adicionaFigura(filaCobra.getPrimeiro().linha,
										   filaCobra.getPrimeiro().coluna);
				graficos.repaint();
				geraAlimento();
			}
		} else {
			matrizColisao.setValor(filaCobra.getUltimo().linha,
								   filaCobra.getUltimo().coluna, false);
			filaCobra.removeBloco();
			matrizColisao.setValor(filaCobra.getPrimeiro().linha,
								   filaCobra.getPrimeiro().coluna, true);
			adicionaBloco();

			if (matrizColisao.getValor(filaCobra.getPrimeiro().linha,
									   filaCobra.getPrimeiro().coluna)) {
				if (linhaAlimento == filaCobra.getPrimeiro().linha &&
					colunaAlimento == filaCobra.getPrimeiro().coluna) {
					figuraAlimento.mudaCoordenadas(linhaAlimento = -10, colunaAlimento = -10);
					colidiuAlimento = true;
					filaFiguras.transladaFigura(filaCobra.getPrimeiro().linha,
												filaCobra.getPrimeiro().coluna);
					audioPlayer.tocaAlimento();
					graficos.repaint();
					pontuacao++;
					atualizaTitulo();
				} else {
					audioPlayer.tocaColisao();
					timer.stop();

					int resposta = decideReinicio();
					dispose();

					if (resposta == 0) {
						iniciaJogo( this.numCenario, this.nomeCenario, timer.getDelay() );
					} else {
						new OptionsMenu(this);
					}
				}
			} else {
				filaFiguras.transladaFigura(filaCobra.getPrimeiro().linha,
											filaCobra.getPrimeiro().coluna);
				graficos.repaint();
			}
		}
	}

	/**
	 * 
	 */
	private void adicionaBloco () {
		filaCobra.addBloco(new Block((byte)((filaCobra.getPrimeiro().linha +
									 ouvinteTeclado.getVertical() + GlobalVariables.NUM_LINHAS)
									 % GlobalVariables.NUM_LINHAS),
									 (byte)((filaCobra.getPrimeiro().coluna +
									 ouvinteTeclado.getHorizontal() + GlobalVariables.NUM_COLUNAS)
									 % GlobalVariables.NUM_COLUNAS)));
		ouvinteTeclado.setConsumido();
	}

	/**
	 * 
	 */
	private void geraAlimento () {
		Thread t = new Thread(this);
		t.start();
	}

	/**
	 * 
	 */
	public void run () {
		byte linha,
			 coluna;

		do {
			linha = (byte)((Math.random() * (GlobalVariables.NUM_LINHAS - 2)) + 1);
			coluna = (byte)((Math.random() * (GlobalVariables.NUM_COLUNAS - 2)) + 1);
		} while (matrizColisao.getValor(linha, coluna) ||
				(filaCobra.getPrimeiro().linha == linha &&
				 filaCobra.getPrimeiro().coluna == coluna));

		matrizColisao.setValor(linha, coluna, true);
		figuraAlimento.mudaCoordenadas(linhaAlimento = linha, colunaAlimento = coluna);
		graficos.repaint();
	}

	/**
	 * 
	 */
	private void atualizaTitulo () {
		setTitle(java.util.ResourceBundle.getBundle("br/gfca/jsnake/local_strings").getString("JG_JSnake2") + " - " + this.nomeCenario + " - " + java.util.ResourceBundle.getBundle("br/gfca/jsnake/local_strings").getString("JG_Pontos") + pontuacao);
	}

	/**
	 * 
	 * @return
	 */
	private int decideReinicio () {
		String[] opcoes = {java.util.ResourceBundle.getBundle("br/gfca/jsnake/local_strings").getString("JG_Sim"), java.util.ResourceBundle.getBundle("br/gfca/jsnake/local_strings").getString("JG_Nao")};
		return JOptionPane.showOptionDialog(this,
											java.util.ResourceBundle.getBundle("br/gfca/jsnake/local_strings").getString("JG_Pontuacao") + pontuacao + java.util.ResourceBundle.getBundle("br/gfca/jsnake/local_strings").getString("JG_Deseja_reiniciar_a_partida"),
											java.util.ResourceBundle.getBundle("br/gfca/jsnake/local_strings").getString("JG_Fim_de_jogo"),
											JOptionPane.YES_NO_OPTION,
											JOptionPane.PLAIN_MESSAGE,
											ResourceLoader.getResourceImage("decoration.png"),
											opcoes,
											opcoes[0]);
	}

	/**
	 * 
	 * @author Gustavo de Farias Costa Almeida
	 * @version 1.0
	 */
	private class Graficos extends JPanel {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 570345115774321367L;

		/**
		 * 
		 */
		public Graficos () {
			super();
		}

		/**
		 * 
		 * @param g
		 */
		public void paintComponent (Graphics g) {
			super.paintComponent(g);

			figuraCenario.paintIcon(this, g, 0, 0);
			figuraAlimento.paintIcon(this, g);
			filaFiguras.paintIcons(this, g);
		}
	}
}