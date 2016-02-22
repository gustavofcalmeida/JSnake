package br.gfca.jsnake.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import br.gfca.jsnake.GlobalVariables;
import br.gfca.jsnake.util.ResourceLoader;

/**
 * 
 * @author Gustavo de Farias Costa Almeida
 * @version 1.0
 */
public class OptionsMenu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6020088378514458666L;
	
	// atributos
	private JSlider seletorVelocidade;
	private JList seletorCenario;
	private JLabel miniatura;
	private JButton iniciar,
					ajuda,
					sobre,
					sair;

	private String[] nomes;
	private Game jogo;

	/**
	 * 
	 * @param jogo
	 */
	public OptionsMenu (Game jogo) {
		super(java.util.ResourceBundle.getBundle("br/gfca/jsnake/local_strings").getString("MO_JSnake_Opcoes_de_jogo"));
		this.jogo = jogo;

		configuraJanela();
		configuraComponentes();

		this.setVisible(true);
	}

	/**
	 * 
	 */
	private void configuraJanela () {
		getContentPane().setLayout(new FlowLayout());

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evento) {
				System.exit(0);
			}
		});

		ImageIcon i = ResourceLoader.getResourceImage("icon.png");
		setIconImage(i.getImage());

		setSize(396, 474);
		setResizable(false);
		setLocationRelativeTo(null);
	}

	/**
	 * 
	 */
	private void configuraComponentes () {
		configuraTitulo();
		configuraVelocidade();
		configuraCenarios();
		configuraBotoes();
	}

	/**
	 * 
	 */
	private void configuraTitulo () {
		getContentPane().add(new JLabel(ResourceLoader.getResourceImage("title.png")));
	}

	/**
	 * 
	 */
	private void configuraVelocidade () {
		JPanel painel = new JPanel();

		painel.add(new JLabel(java.util.ResourceBundle.getBundle("br/gfca/jsnake/local_strings").getString("MO_Velocidade")));

		seletorVelocidade = new JSlider(1, 7);
		seletorVelocidade.setMajorTickSpacing(1);
		seletorVelocidade.setPaintTicks(true);
		seletorVelocidade.setPaintLabels(true);
		seletorVelocidade.setSnapToTicks(true);
		painel.add(seletorVelocidade);

		getContentPane().add(painel);
	}

	/**
	 * 
	 */
	private void configuraCenarios () {
		JPanel painel = new JPanel(new BorderLayout());

		painel.add(new JLabel(java.util.ResourceBundle.getBundle("br/gfca/jsnake/local_strings").getString("MO_Cenario")), BorderLayout.NORTH);

		nomes = new String[ GlobalVariables.SCENARIO_COUNT ];

		for (int i = 0; i < nomes.length; i++) {
			nomes[i] = java.util.ResourceBundle.getBundle("br/gfca/jsnake/scenario/local_scenario").getString("scenario.name." + i);
		}

		seletorCenario = new JList(nomes);
		seletorCenario.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		seletorCenario.setSelectedIndex(0);
		configuraListenerCenarios();
		JScrollPane jsp = new JScrollPane(seletorCenario);
		jsp.setPreferredSize(new Dimension(150, 120));
		painel.add(jsp, BorderLayout.WEST);

		painel.add(Box.createHorizontalStrut(15), BorderLayout.CENTER);

		miniatura = new JLabel(new ImageIcon(ResourceLoader.getScenarioImage( 0 + ".png").getImage().getScaledInstance(150, 98, Image.SCALE_FAST)));
		painel.add(miniatura, BorderLayout.EAST);

		painel.add(Box.createVerticalStrut(15), BorderLayout.SOUTH);

		getContentPane().add(painel);
	}

	/**
	 * 
	 */
	private void configuraBotoes () {
		iniciar = new JButton(java.util.ResourceBundle.getBundle("br/gfca/jsnake/local_strings").getString("MO_Iniciar"));
		configuraListenerIniciar();
		getRootPane().setDefaultButton(iniciar);
		ajuda = new JButton(java.util.ResourceBundle.getBundle("br/gfca/jsnake/local_strings").getString("MO_Ajuda"));
		configuraListenerAjuda();
		sobre = new JButton(java.util.ResourceBundle.getBundle("br/gfca/jsnake/local_strings").getString("MO_Sobre"));
		configuraListenerSobre();
		sair = new JButton(java.util.ResourceBundle.getBundle("br/gfca/jsnake/local_strings").getString("MO_Sair"));
		configuraListenerSair();

		JPanel painelBotoes = new JPanel(new GridLayout(2, 3, 15, 15));
		painelBotoes.add(new JPanel());
		painelBotoes.add(iniciar);
		painelBotoes.add(new JPanel());
		painelBotoes.add(ajuda);
		painelBotoes.add(sobre);
		painelBotoes.add(sair);
		getContentPane().add(painelBotoes);
	}

	/**
	 * 
	 */
	private void configuraListenerCenarios () {
		seletorCenario.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged (ListSelectionEvent event) {
				if (seletorCenario.getSelectedIndex() == -1) {
					seletorCenario.setSelectedIndex(0);
					return;
				}				
				miniatura.setIcon(new ImageIcon(ResourceLoader.getScenarioImage( seletorCenario.getSelectedIndex() + ".png").getImage().getScaledInstance(150, 98, Image.SCALE_FAST)));
			}
		});
	}

	/**
	 * 
	 */
	private void configuraListenerIniciar () {
		iniciar.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent evento) {
				dispose();
				jogo.iniciaJogo( seletorCenario.getSelectedIndex(),
								 nomes[seletorCenario.getSelectedIndex()],
								 calculaDelay(seletorVelocidade.getValue()));
			}
        });
	}

	/**
	 * 
	 * @param velocidade
	 * @return
	 */
	private int calculaDelay (int velocidade) {
		return GlobalVariables.FATOR_DELAY / velocidade;
	}

	/**
	 * 
	 */
	private void configuraListenerAjuda () {
		ajuda.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent evento) {
				String text = java.util.ResourceBundle.getBundle("br/gfca/jsnake/local_help").getString("help_text");
				
				JTextArea texto = new JTextArea( text, 20, 45 );
				texto.setFont(new Font("Monospaced",Font.PLAIN,14));
				texto.setEditable(false);
				JScrollPane scroll = new JScrollPane(texto);

				JOptionPane.showMessageDialog(OptionsMenu.this,
											  scroll,
											  java.util.ResourceBundle.getBundle("br/gfca/jsnake/local_strings").getString("MO_Ajuda2"),
											  JOptionPane.PLAIN_MESSAGE,
											  ResourceLoader.getResourceImage("help.png"));
			}
        });
	}

	/**
	 * 
	 */
	private void configuraListenerSobre () {
		sobre.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent evento) {
				String text = java.util.ResourceBundle.getBundle("br/gfca/jsnake/local_about").getString("about_text");
				
				JOptionPane.showMessageDialog(OptionsMenu.this,
											  text,
											  java.util.ResourceBundle.getBundle("br/gfca/jsnake/local_strings").getString("MO_Sobre_JSnake"),
											  JOptionPane.PLAIN_MESSAGE,
											  ResourceLoader.getResourceImage("decoration.png"));
			}
        });
	}

	/**
	 * 
	 */
	private void configuraListenerSair () {
		sair.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent evento) {
				System.exit(0);
			}
        });
	}
}