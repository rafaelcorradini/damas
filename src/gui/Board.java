package gui;

import main.*;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.awt.event.ActionEvent;


public class Board extends JFrame implements ActionListener {

	static private final String SELECTED = new String ("selected");
	
	static Damas jogo;
	static Jogador j1;
	static Jogador j2;
	private boolean turno;
	private int pontos = 0;
	private int melhor = 0;
	
	private JPanel contentPane;
	private static JLabel labelTop;
	private static Celula[][] tabuleiro = new Celula[8][8];
	
	private Socket cliente;
	private PrintStream saida;
	private Scanner teclado;
	private static Scanner server;	
	
	static boolean gambiarra = true;
	/*
	public static void main(String[] args) {
		
		j1 = new Jogador ("Carlos", 1);
		j2 = new Jogador ("Pedro", 2);
		jogo = new Damas(8, j1, j2);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Board frame = new Board(jogo.getTabuleiro());
					atualizaTabuleiro(jogo.getTabuleiro(), j1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/
	/**
	 * Create the frame.
	 */
	public Board(String nome) throws Exception{
		cliente = new Socket("192.168.0.15", 9669);
		saida = new PrintStream(cliente.getOutputStream());
		teclado = new Scanner(System.in);
		server = new Scanner(cliente.getInputStream());
		
		server.nextLine();
		String jogador = new String(server.nextLine());
		
		saida.println("I "+nome);

		String nome2 = new String (server.nextLine());
		
		if (jogador.equals("J 1")) {
			j1 = new Jogador(nome, 1);
			j2 = new Jogador(nome2, 2);
			jogo = new Damas(8, j1, j2);
		}
		else if (jogador.equals("J 2")) {
			j1 = new Jogador(nome, 2);
			j2 = new Jogador(nome2, 1);	
			jogo = new Damas(8, j2, j1);
		}
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/Tabuleiro.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 427, 463);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		labelTop = new JLabel("Aguarde um parceiro para jogar");
		labelTop.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(labelTop, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		int i, j;
		for (i = 0; i < 8; i++) {
			for (j = 0; j < 8; j++) {
				tabuleiro[i][j] = new Celula (i, j, jogo.getTabuleiro()[i][j]);
				panel.add(tabuleiro[i][j]);
				tabuleiro[i][j].addActionListener(this);
				tabuleiro[i][j].setEnabled(false);
			}
			
		}
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(this.getClass().getResource("/Tabuleiro.png")));
		label.setBounds(0, 0, 400, 400);
		panel.add(label);
		
		this.initTabuleiro(jogo.getTabuleiro(), jogo.getJogador());
		this.setVisible(true);
	
	
		if (jogo.getVez() != j1.getCor()) {
				esperaVez();
		}
	}
	
	/*
	public static void goTo() {

		if (jogo.getVez() != j1.getCor()) {
			esperaVez();
		}
	}*/

	@Override
	public void actionPerformed(ActionEvent e) {
		int check = 0;
		int i, j;
		int iniX = 0, iniY = 0;
		int endX = 0, endY = 0;
		boolean selectedPeca = false;
		boolean selectedSlot = false;
		
		
		if (SELECTED.equals(e.getActionCommand())) {
			
			for (i = 0; i < 8; i++) {
				for (j = 0; j < 8; j++) {
					if (tabuleiro[i][j].isSelected() && tabuleiro[i][j].myGetPeca() > 0){
						if (selectedPeca) {
							tabuleiro[i][j].setSelected(false);
							if (iniX == i && iniY == j) selectedPeca = false; 
							iniX = i;
							iniY = j;
						}
						else {
							iniX = i;
							iniY = j;
							selectedPeca = true;
						}
					}
				}
			}
			
			for (i = 0; i < 8; i++) {
				for (j = 0; j < 8; j++) {
					if (tabuleiro[i][j].isSelected() && tabuleiro[i][j].myGetPeca() == 0 && selectedPeca) {
						if (selectedSlot) {
							tabuleiro[endX][endY].setSelected(false);
							if (endX == i && endY == j) selectedSlot = false; 
							endX = i;
							endY = j;
						} else {
							endX = i;
							endY = j;
							selectedSlot = true;
						}
					}
					else if (tabuleiro[i][j].myGetPeca() == 0) tabuleiro[i][j].setSelected(false);
				}
			}
			
			if (selectedPeca && selectedSlot) {
				
				try {
					
					System.out.println("De: " + iniX + "x" + iniY + "Para: " + endX + "x" + endY);
					check = jogo.fazerMovimento(iniX, iniY, endX, endY, jogo.getJogador());
					saida.println("M" + iniX+" "+iniY+" "+endX+" "+endY);
					
					System.out.println("check:"+check);
					
					switch (check) {
									
						case (-1):  turno = false;
									break;	
									
						case (0): 	if(melhor > 0)
										turno = false;
									else
										turno = true;	
									break;
									
						case (1): 	pontos++;
									turno = true;
									break;
					}
					System.out.println("Melhor: " + melhor + "  Pontos: " + pontos);
					
					if (melhor == pontos && turno) {
						
						saida.println("C");
						atualizaTabuleiro();
						
						jogo.confirmarJogada();
						System.out.println(jogo);
						melhor = jogo.melhorJogada(jogo.getJogador());
						pontos = 0;
						turno = false;
						
					}  else if(turno) {
						atualizaTabuleiroAux();
						System.out.println(jogo);
						turno = false;
					} else {
						
						jogo.refazerJogada();
						saida.println("R");
						
						atualizaTabuleiro();
						System.out.println("Jogada n�o valida");
						pontos = 0;
					}
					
				} catch (Exception e1) {e1.printStackTrace();}
				
				
				tabuleiro[endX][endY].setSelected(false);
				tabuleiro[iniX][iniY].setSelected(false);
				selectedPeca = false;
				selectedSlot = false;
			}
		}	
	
		//else atualizaTabuleiro();
	}
	
	static public void initTabuleiro(int tabu[][], Jogador jogador) {
		int i, j;
		for (i = 0; i < 8; i++) {
			for (j = 0; j < 8; j++) {
				tabuleiro[i][j].mySetPeca(tabu[i][j]);
			}
		}
		unablePecas (jogador.getCor());
		
		if (jogo.getVez() == j1.getCor()) labelTop.setText("Sua vez");
		else labelTop.setText(jogo.getJogador().getNome() + " esta jogando...");
		
	}

	static public void atualizaTabuleiro() {
		int tabu[][] = jogo.getTabuleiro();
		Jogador jogador = jogo.getJogador();
		
		atualizaTabuleiroAux();
		labelTop.setText(jogo.getJogador().getNome() + " esta jogando...");
		unablePecas (jogador.getCor());
		
		gambiarra = true;
	}
	
	static public void esperaVez() {
		
		jogo.setBoard(server.nextLine());
		jogo.changeVez();
		
		atualizaTabuleiroAux();
		labelTop.setText("Sua vez");
		unablePecas (jogo.getVez());
		
		gambiarra = false;
	}
	
	static private void atualizaTabuleiroAux() {
		int tabu[][] = jogo.getTabuleiro();
		
		int i, j;
		for (i = 0; i < 8; i++) {
			for (j = 0; j < 8; j++) {
				tabuleiro[i][j].mySetPeca(tabu[i][j]);
			}
		}
		
	}
	
	public static void unablePecas(int cor) {
		int i, j;
		
		if (j1.getCor() == cor) {
			for (i = 0; i < 8; i++) {
				for (j = 0; j < 8; j++) {
					if (tabuleiro[i][j].myGetPeca() != cor && tabuleiro[i][j].myGetPeca() != 0 &&tabuleiro[i][j].myGetPeca() != cor+2) {
						tabuleiro[i][j].setEnabled(false);	
					} else tabuleiro[i][j].setEnabled(true);
				}
			}
		}
		
		else {
			
			for (i = 0; i < 8; i++) {
				for (j = 0; j < 8; j++) {
					tabuleiro[i][j].setEnabled(false);
				}
				
			}
		}
	}
	
}

