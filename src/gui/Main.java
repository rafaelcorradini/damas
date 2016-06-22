package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Damas;
import main.Jogador;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JScrollBar;
import javax.swing.JPasswordField;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame implements ActionListener{

	private Damas jogo;
	private Jogador j1;
	private Jogador j2;
	
	static Main frame;
	private JPanel contentPane;
	private JTextField textField;
	private String ENTRAR = new String ("entrar");
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 232, 148);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.setActionCommand("entrar");
		btnEntrar.addActionListener(this);
		panel.add(btnEntrar);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JLabel lblEntreComUm = new JLabel("Entre com um nome de usu\u00E1rio");
		lblEntreComUm.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblEntreComUm, BorderLayout.NORTH);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		textField = new JTextField();
		panel_3.add(textField);
		textField.setColumns(15);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.NORTH);
		
		JLabel lblDamasjava = new JLabel("DAMAS.JAVA");
		panel_2.add(lblDamasjava);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String nome = new String();
		
		if (ENTRAR.equals(e.getActionCommand())) {
			
			if (!textField.getAccessibleContext().equals("")) {
				iniJogo(textField.getAccessibleContext().toString());
				frame.setVisible(false);
				
				
			}
			
			
		}
		
	}

	private void iniJogo (String nome) {
		
		j1 = new Jogador (nome, 1);
		j2 = new Jogador ("Pedro", 2);
		//jogo = new Damas(8, j1, j2);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Board board = new Board(j1, j2);
					//atualizaTabuleiro(jogo.getTabuleiro(), j1);
					board.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
		
}
