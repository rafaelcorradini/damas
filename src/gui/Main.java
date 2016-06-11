package gui;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import Placar;

public class Main extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	static private final String NEW_GAME = "new_game";
	static private final String EXIT_GAME = "exit_game";

	static private final String ROLAR = "rolar_dados";
	private Placar placar;
	private RolaDados rd;
	private int rodada = 0;
	private int tries = 0;
	private int[] lastDados;
	
	
	private JLabel rdd; // controla a rodada atual 
	private final String rddString = "Round";
	private JButton rollButton;  // botão para rolar os dados
	JTextArea dadosField; // area onde mostrar os dados
	private JCheckBox[] dadoSel = new JCheckBox[5]; // para selecionar os dados a serem rolados
	private Vector<JRadioButton> radios = new Vector<JRadioButton>();
	private JTextArea placarText;
	private JLabel total;
	
	public Main() {
		super("Ultimate Bozo");
		JPanel jp = (JPanel) this.getContentPane();
		jp.setLayout(new GridLayout(1,2));
		
		JMenuBar mb = new JMenuBar();
		this.setJMenuBar(mb);
		JMenu menu = new JMenu("Game");
		mb.add(menu);
		JMenuItem item = new JMenuItem("New");
		menu.add(item);
		item.setActionCommand(NEW_GAME);
		item.addActionListener(this);
		menu.addSeparator();
		item = new JMenuItem("Exit");
		menu.add(item);
		item.setActionCommand(EXIT_GAME);
		item.addActionListener(this);
		
		
		JPanel panelL = new JPanel(new BorderLayout());
		JPanel panelR = new JPanel(new GridLayout(1,2));
		jp.add(panelL);
		jp.add(panelR);
		
		rdd = new JLabel(rddString);
		panelL.add(rdd, BorderLayout.NORTH);
		
		rollButton = new JButton("Roll");
		rollButton.setActionCommand(ROLAR);
		rollButton.addActionListener(this);
		panelL.add(rollButton, BorderLayout.SOUTH);
		
		dadosField = new JTextArea(5,25);
		dadosField.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
		dadosField.setEditable(false);
		GridLayout gl = new GridLayout(1,5);
		JPanel aux = new JPanel(gl);
		gl = new GridLayout(2,1);
		gl.setVgap(1);
		JPanel aux2 = new JPanel(gl);
		aux2.add(dadosField);
		aux2.add(aux);
		for (int i = 0; i < 5; i++ )
		{
			dadoSel[i] = new JCheckBox();
			aux.add(dadoSel[i]);
		}
		panelL.add(aux2);
		
		// Paineis do lado direito
		JPanel rbPanel = new JPanel(new GridLayout(5,2));
		ButtonGroup bgroup = new ButtonGroup();
		for (int i = 0; i < 10; i++) {
			JRadioButton jrb = new JRadioButton(Placar.getName(i));
			radios.addElement(jrb);
			bgroup.add(jrb);
			rbPanel.add(jrb);
			jrb.addActionListener(this);
		}
		panelR.add(rbPanel);
		
		JPanel panelRR = new JPanel(new FlowLayout());
		panelR.add(panelRR);
	    placarText = new JTextArea(8,28);
		placarText.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
		placarText.setEditable(false);

	    panelRR.add(placarText);
	    total = new JLabel("Total :");
	    panelRR.add(total);
	    
	    
	}
	
	
	


	@Override
	public void actionPerformed(ActionEvent e) {
		if (EXIT_GAME.equals(e.getActionCommand())) {
			System.exit(0);;
		} else if (NEW_GAME.equals(e.getActionCommand())) {
			newGame();
		} else if (ROLAR.equals(e.getActionCommand())) {
			if (rodada <= 0 || tries > 3) return;
			if (tries == 1) 
				lastDados = rd.rolar();
			else {
				boolean v[] = new boolean[5];
				for (int i = 0; i < 5; i++)
				{
					v[i] = dadoSel[i].isSelected();
				}
				lastDados = rd.rolar(v);
			}
			tries++;
			atualizaGUI();	
		} else if (e.getSource() instanceof JRadioButton) {
			JRadioButton rb = (JRadioButton) e.getSource();
			if ( rodada <= 0 || tries <= 1 )
			{
				rb.setSelected(false);
				return;
			}
			int k = radios.indexOf(rb);
			rb.setEnabled(false);
			placar.add(k, lastDados);
			tries = 1;
			rodada = ++rodada % 11;
			atualizaGUI();	
		}
	}


	private void newGame() {
		placar = new Placar();
		rd = new RolaDados(5);
		rodada = 1;
		tries = 1;
		for (JRadioButton j : radios) {
			j.setEnabled(true);
		}
		atualizaGUI();
	}




	private void atualizaGUI() {
		// if (rodada <= 0) return;
		rdd.setText(rddString + " " + rodada);
		if (tries == 1)
			dadosField.setText("");
		else
			dadosField.setText(rd.toString());
		for (JCheckBox j: dadoSel )
		{
			j.setSelected(false);
		}	
		placarText.setText(placar.toString());
		total.setText("Total: " + placar.getScore());
	}
	
	
	
	public static void main(String[] args) {
		Main frame = new Main();
		frame.setSize(880, 300);
		//frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}



}