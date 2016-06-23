package gui;

import main.*;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.awt.event.ActionEvent;

/**
 * INterface que representa um tabuleiro de Damas. Se conecta com o servidor
 * @author Guilherme Alves Campos
 *
 */
public class Board extends JFrame implements ActionListener {

	//Actions
	static private final String SELECTED = new String ("selected");
	static private final String EMPATE = new String ("empate");
	
	//Variaveis para controle do jogo
	static Damas jogo;
	static Jogador j1;
	static Jogador j2;
	private boolean turno;
	private int pontos = 0;
	private int melhor = 0;
	
	//Variaveis para controle da interface
	private JPanel contentPane;
	private static JLabel labelTop;
	private static JButton botaoEmpate;
	private static Celula[][] tabuleiro = new Celula[8][8];
	
	//Variaveis para conexao cliente-servidor
	private Socket cliente;
	private PrintStream saida;
	private static Scanner server;	
	
	/**
	 * Cria o tabuleiro
	 * @param nome Nome do jogador conectado a esse client
	 * @throws Exception
	 */
	public Board(String nome) throws Exception{
		cliente = new Socket(InetAddress.getLocalHost(), 9669);
		saida = new PrintStream(cliente.getOutputStream());
		server = new Scanner(cliente.getInputStream());
		
		server.nextLine();
		String jogador = new String(server.nextLine());
		
		saida.println("I "+nome);

		String nome2 = new String (server.nextLine());
		
		//Cria o proprio jogo espelhado no do servidor
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
		
		//Monta interface
		setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/Tabuleiro.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 427, 495);
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
		
		botaoEmpate = new JButton("Pedir Empate");
		botaoEmpate.setActionCommand("empate");
		botaoEmpate.addActionListener(this);
		contentPane.add(botaoEmpate, BorderLayout.SOUTH);
		
		//Coloca as celulas nas posições corretas
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
		
		//Inicia o tabuleiro e o torna visivel
		Board.initTabuleiro(jogo.getTabuleiro(), jogo.getJogador());
		this.setVisible(true);
	
		//Espera, se for o caso, pela sua vez
		if (j1.getCor() == 2) {
			esperaVez();
		}
	}
	
	/**
	 * Recebe ações e atualiza a interface
	 * @param e Ação
	 */
	private void animate(final ActionEvent e) {
	    Thread thread = new Thread(new Runnable() {
	        public void run() {
	        	int check = 0;
	    		int i, j;
	    		int iniX = 0, iniY = 0;
	    		int endX = 0, endY = 0;
	    		boolean selectedPeca = false;
	    		boolean selectedSlot = false;
	    		
	    		//Controla a seleção dos botões para que apenas uma peça e um espaço em branco poçam ser selecionados. 
	    		//É possível trocar a peça
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
	    			//Seleciona espaço em branco
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
	    			
	    			//Quando esta preparado, manda o comando de movimento para o servidor e para o jogo
	    			if (selectedPeca && selectedSlot) {
	    				
	    				try {
	    					
	    					System.out.println("De: " + iniX + "x" + iniY + "Para: " + endX + "x" + endY);
	    					check = jogo.fazerMovimento(iniX, iniY, endX, endY, jogo.getJogador());
	    					System.out.println("check:"+ check);
	    					saida.println("M " + iniX+" " +iniY+" " +endX+" "+endY);
	    					
	    					System.out.println("check:"+check);
	    					
	    					//Processa o retorno
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
	    					
	    					//Decide se o turno acabou ou deve continuar
	    					if (melhor == pontos && turno) {
	    						saida.println("C");
	    						jogo.confirmarJogada();
	    						atualizaTabuleiro();
	    						System.out.println(jogo);
	    						
	    						
	    						pontos = 0;
	    						turno = false;
	    						esperaVez();
	    						melhor = jogo.melhorJogada(jogo.getJogador());
	    						
	    					}  else if(turno) {
	    						atualizaTabuleiroAux(jogo.getTabuleiroTemp());
	    						System.out.println(jogo);
	    						turno = false;
	    					} else {
	    						
	    						jogo.refazerJogada();
	    						saida.println("R");
	    						atualizaTabuleiro();	    						
	    						System.out.println("Jogada nï¿½o valida");
	    						pontos = 0;
	    					}
	    		
	    					
	    				} catch (Exception e1) {e1.printStackTrace();}
	    				
	    				//Reinicia variaveis
	    				tabuleiro[endX][endY].setSelected(false);
	    				tabuleiro[iniX][iniY].setSelected(false);
	    				selectedPeca = false;
	    				selectedSlot = false;
	    			}
	    		}	
	    		
	    		//Envia o comando de empate para o servidor quando acontecer
	    		else if (EMPATE.equals(e.getActionCommand())) {
	    			saida.println("E");
	    		}
	    		
	        }
	    });
	    thread.setPriority(Thread.NORM_PRIORITY);
	    thread.start();
	}
	
	/**
	 * Chama o metodo @animate
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		animate(e);
	}
	
	/**
	 * Inicia o tabuleiro: JLabel com o tezto correto e botões corretos enablados ou não
	 * @param tabu tabuleiro base
	 * @param jogador conectado neste client
	 */
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

	/**
	 * Atualiza tabuleiro com base no jogo. JLabel correto. Celulas corretas enabladas ou não.
	 */
	static public void atualizaTabuleiro() {
		Jogador jogador = jogo.getJogador();
		
		atualizaTabuleiroAux(jogo.getTabuleiro());
		labelTop.setText(jogo.getJogador().getNome() + " esta jogando...");
		unablePecas (jogador.getCor());
	}
	
	/**
	 * Aguarda uma resposta do servidor de que o adversário já executou sua jogada. Recebe a nova configuração do tabuleiro. Atualiza a interface. Checa se houve vitora de algum dos jogadores. Checa se houve empate.
	 */
	static public void esperaVez() {
		
		//Checa se houve vitoria
		if (jogo.getVitoria() == 1) {
			if (j1.getCor() == 1) {
				labelTop.setText("Vocï¿½ venceu!");
			}
			else labelTop.setText("Vocï¿½ perdeu");
			
			unablePecas(10);
		} else if (jogo.getVitoria() == 2) {
			if (j1.getCor() == 2) {
				labelTop.setText("Vocï¿½ venceu!");
			}
			else labelTop.setText("Vocï¿½ perdeu");
			
			unablePecas(10);
		}
		
		//aguarda mensagem do servidor
		String line = server.nextLine();
		System.out.println("board: "+line);
		
		//Checa se foi empate
		if (line.equals("E")) {
			labelTop.setText("Os jogadores concordaram com um empate");
			unablePecas(10);
		}
		
		//Checa por vitoria do jogador 1
		else if (line.equals("V1")) {
			if (j1.getCor() == 1) {
				labelTop.setText("Vocï¿½ venceu!");
			}
			else labelTop.setText("Vocï¿½ perdeu");
			
			unablePecas(10);
		}
		
		//Checa por vitoria do jogador 2
		else if (line.equals("V2")) {
			if (j1.getCor() == 2) {
				labelTop.setText("Vocï¿½ venceu!");
			}
			else labelTop.setText("Vocï¿½ perdeu");
			
			unablePecas(10);
		}
		
		//Se não atualiza a interface com a nova configuração do tabuleiro
		else {
			jogo.setBoard(line);
			jogo.changeVez();
			System.out.println("---"+jogo.getVez());
		
			atualizaTabuleiroAux(jogo.getTabuleiro());
			labelTop.setText("Sua vez");
			unablePecas (jogo.getVez());
		}

		//Checa por vitoria mais uma vez
		if (jogo.getVitoria() == 1) {
			if (j1.getCor() == 1) {
				labelTop.setText("Voce venceu!");
			}
			else labelTop.setText("Voce perdeu");
			
			unablePecas(10);
		} else if (jogo.getVitoria() == 2) {
			if (j1.getCor() == 2) {
				labelTop.setText("Voce venceu!");
			}
			else labelTop.setText("Voce perdeu");
			
			unablePecas(10);
		}
		
	}
	
	/**
	 * Atualiza apenas as celulas do tabuleiro de acordo com tabu
	 * @param tabu tabuleiro base para a atualização do tabuleiro da interface
	 */
	static private void atualizaTabuleiroAux(int tabu[][]) {
		//int tabu[][] = jogo.getTabuleiro();
		
		int i, j;
		for (i = 0; i < 8; i++) {
			for (j = 0; j < 8; j++) {
				tabuleiro[i][j].mySetPeca(tabu[i][j]);
			}
		}
		
	}
	
	/**
	 * ativa (anabla) ou desativa (desenabla?) as celulas de acordo com a cor
	 * @param cor
	 */
	public static void unablePecas(int cor) {
		int i, j;
		
		//Se for sua vez ativa celulas brancas e da sua cor e botão de empate
		if (j1.getCor() == cor) {
			for (i = 0; i < 8; i++) {
				for (j = 0; j < 8; j++) {
					if (tabuleiro[i][j].myGetPeca() != cor && tabuleiro[i][j].myGetPeca() != 0 &&tabuleiro[i][j].myGetPeca() != cor+2) {
						tabuleiro[i][j].setEnabled(false);	
					} else tabuleiro[i][j].setEnabled(true);
				}
			}
			botaoEmpate.setEnabled(true);
		}
		
		//Se não desativa tudo
		else {
			
			for (i = 0; i < 8; i++) {
				for (j = 0; j < 8; j++) {
					tabuleiro[i][j].setEnabled(false);
				}
			}
			botaoEmpate.setEnabled(false);
		}
	}
	
}
