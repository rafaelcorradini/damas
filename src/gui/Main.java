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
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main extends JFrame implements ActionListener {

	private Damas jogo;
	private Jogador j1;
	private Jogador j2;
	
	static Main frame;
	JLabel lblEntreComUm;
	private JPanel contentPane;
	private JTextField textField;
	private String ENTRAR = new String ("entrar");
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws Exception{
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
	public Main() throws Exception{
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
		
		lblEntreComUm = new JLabel("Entre com um nome de usu\u00E1rio");
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
			
			if (!textField.getText().equals("")) {
				System.out.println(textField.getText());
				lblEntreComUm.setText("Aguarde a Conexao");
				try {
					iniJogo(textField.getText());
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frame.setVisible(false);		
			}	
		}
	}

	private void iniJogo (final String nome) throws UnknownHostException, IOException {
		
		//j1 = new Jogador (nome, 1);
		//j2 = new Jogador ("Outro Nome", 2);
		//Board board;
		//jogo = new Damas(8, j1, j2);
		
		/*final Socket cliente = new Socket("192.168.0.15", 9669);
		PrintStream saida = new PrintStream(cliente.getOutputStream());
		Scanner teclado = new Scanner(System.in);
		final Scanner server = new Scanner(cliente.getInputStream());
		*/
		//System.out.println(server.nextLine());
		//final String jogador = new String(server.nextLine());
		//System.out.println(jogador);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Board board = new Board(nome);
					board.setVisible(true);
		
				//atualizaTabuleiro(jogo.getTabuleiro(), j1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
		
}
