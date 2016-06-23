package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Primeira interface onde o usuario se conecta ao servidor. Chama o objeto {@Board}
 * @author Guilherme
 *
 */
public class Main extends JFrame implements ActionListener {
	
	static Main frame;
	JLabel lblEntreComUm;
	private JPanel contentPane;
	private JTextField textField;
	private String ENTRAR = new String ("entrar");
	
	/**
	 * Inicia a interface
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
	 * Cria a interface
	 */
	public Main() throws Exception{
		
		//Painel pricipal
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 232, 148);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		//Botão
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.setActionCommand("entrar");
		btnEntrar.addActionListener(this);
		panel.add(btnEntrar);
		
		//Novo painel colocado no centro
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		//Label
		lblEntreComUm = new JLabel("Entre com um nome de usu\u00E1rio");
		lblEntreComUm.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblEntreComUm, BorderLayout.NORTH);
		
		//TextField
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

	/**
	 * Loda com as ações a serem tomadas caso o botão seja precionado 
	 * @param e Ação
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//Inicia jogo se o botão foi clicado e algo foi digitado como nome
		if (ENTRAR.equals(e.getActionCommand())) {
			
			if (!textField.getText().equals("")) {
				System.out.println(textField.getText());
				lblEntreComUm.setText("Aguarde a Conexao");
				try {
					iniJogo(textField.getText());
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				frame.setVisible(false);		
			}	
		}
	}

	/**
	 * Inicia a classe Board, que ira se conectar ao servidor
	 * @param nome Nome do jogador digitado pelo usuario
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	private void iniJogo (final String nome) throws UnknownHostException, IOException {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Board board = new Board(nome);
					board.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
		
}
