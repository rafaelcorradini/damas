package gui;
import main.Damas;
import main.Jogador;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Board extends JFrame implements ActionListener, ChangeListener {

	static private final String SELECTED = new String ("selected");
	
	static Damas jogo;
	static Jogador j1;
	static Jogador j2;
	private JPanel contentPane;
	private static Celula[][] tabuleiro = new Celula[8][8];
	private int[][] tabu = new int[8][8];

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 420, 441);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		int i, j;
		for (i = 0; i < 8; i++) {
			for (j = 0; j < 8; j++) {
				tabuleiro[i][j] = new Celula (i, j, tabu[i][j]);
				contentPane.add(tabuleiro[i][j]);
				tabuleiro[i][j].addActionListener(this);
				tabuleiro[i][j].addChangeListener(this);
			}
			
		}
	
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("C:\\Users\\Guilherme\\Documents\\Imagens Damas\\Tabuleiro.png"));
		label.setBounds(0, 0, 400, 400);
		contentPane.add(label);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int check = 0;
		int i, j;
		int iniX = 0, iniY = 0;
		int endX = 0, endY = 0;
		boolean selectedPeca = false;
		boolean selectedSlot = false;
		int J = 0;
		
		if (SELECTED.equals(e.getActionCommand())) {
			
			for (i = 0; i < 8; i++) {
				for (j = 0; j < 8; j++) {
					if (tabuleiro[i][j].isSelected() && tabuleiro[i][j].myGetPeca() > 0){
						if (selectedPeca) {
							tabuleiro[i][j].setSelected(false);
							if (iniX == i && iniY == j) selectedPeca = false; 
							iniX = i;
							iniY = j;
							J = tabuleiro[i][j].myGetPeca();
						}
						else {
							iniX = i;
							iniY = j;
							J = tabuleiro[i][j].myGetPeca();
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
					check = jogo.fazerMovimento(iniX, iniY, endX, endY, jogo.getJogador());
					
					switch (check) {
						case (-1): jogo.refazerJogada();
									atualizaTabuleiro(jogo.getTabuleiroTemp(), jogo.getJogador());
									break;
									
						case (1): atualizaTabuleiro(jogo.getTabuleiroTemp(), jogo.getJogador());
									break;
						case (0): jogo.confirmarJogada();
								  atualizaTabuleiro(jogo.getTabuleiro(), jogo.getJogador());
									break;
						//default: atualizaTabuleiro(jogo.getTabuleiro(), jogo.getJogador());
					}
					//atualizaTabuleiro(jogo.getTabuleiro(), jogo.getJogador());
					
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
		
		unablePecas (jogador.getCor());
	}
	
	public static void unablePecas(int cor) {
		int i, j;
		for (i = 0; i < 8; i++) {
			for (j = 0; j < 8; j++) {
				if (tabuleiro[i][j].myGetPeca() != cor && tabuleiro[i][j].myGetPeca() != 0) {
					tabuleiro[i][j].setEnabled(false);	
				} else tabuleiro[i][j].setEnabled(true);
			}
		}
	}
	
	@Override
	public void stateChanged(ChangeEvent ev){
		ev.getSource();
	}
}

