package gui;
import main.Damas;
import main.Jogador;

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
import java.awt.event.ActionEvent;


public class Board extends JFrame implements ActionListener {

	static private final String SELECTED = new String ("selected");
	
	private static JLabel labelTop;
	private boolean turno;
	private int pontos = 0;
	static Damas jogo;
	static Jogador j1;
	static Jogador j2;
	private JPanel contentPane;
	private int melhor = 0;
	private static Celula[][] tabuleiro = new Celula[8][8];

	/**
	 * Launch the application.
	 */
	
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
	
	/**
	 * Create the frame.
	 */
	public Board(int[][] tabu) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/Tabuleiro.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 427, 463);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		labelTop = new JLabel("JOGADOR 1");
		labelTop.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(labelTop, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		
		
		int i, j;
		for (i = 0; i < 8; i++) {
			for (j = 0; j < 8; j++) {
				tabuleiro[i][j] = new Celula (i, j, tabu[i][j]);
				panel.add(tabuleiro[i][j]);
				tabuleiro[i][j].addActionListener(this);
			}
			
		}
	
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(this.getClass().getResource("/Tabuleiro.png")));
		label.setBounds(0, 0, 400, 400);
		panel.add(label);
		
	}

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
						jogo.confirmarJogada();
						atualizaTabuleiro(jogo.getTabuleiro(), jogo.getJogador());
						System.out.println(jogo);
						melhor = jogo.melhorJogada(jogo.getJogador());
						pontos = 0;
						turno = false;
					}  else if(turno) {
						atualizaTabuleiro(jogo.getTabuleiroTemp(), jogo.getJogador());
						System.out.println(jogo);
						turno = false;
					} else {
						jogo.refazerJogada();
						atualizaTabuleiro(jogo.getTabuleiro(), jogo.getJogador());
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
	}

	static public void atualizaTabuleiro(int tabu[][], Jogador jogador) {
		int i, j;
		for (i = 0; i < 8; i++) {
			for (j = 0; j < 8; j++) {
				tabuleiro[i][j].mySetPeca(tabu[i][j]);
			}
		}
		
		labelTop.setText("JOGADOR " + jogo.getVez());
		unablePecas (jogador.getCor());
	}
	
	public static void unablePecas(int cor) {
		int i, j;
		for (i = 0; i < 8; i++) {
			for (j = 0; j < 8; j++) {
				if (tabuleiro[i][j].myGetPeca() != cor && tabuleiro[i][j].myGetPeca() != 0 &&tabuleiro[i][j].myGetPeca() != cor+2) {
					tabuleiro[i][j].setEnabled(false);	
				} else tabuleiro[i][j].setEnabled(true);
			}
		}
	}
}

