package gui;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

/**
 * O objeto representa uma c�lula no tabuleiro. Pode conter uma pe�a Branca, Preta, Dama Branca, Dama Preta ou estar vazia.
 * @author Rafael Corradini da Cunha (9424322)
 * @author Guilherme Alves Campos (8522320)
 */
public class Celula extends JToggleButton {

	private int x;
	private int y;
	private int peca;
	
	/**
	 * Construtor
	 * @param x POsi��o x da celula
	 * @param y Posi�ao y da celula
	 * @param peca Inteiro representando a pe�a armazenada na celula:<\n>-1 - Celula incacessivel<\n>0 - Vazia<\n>1 - Preta<\n>2 -  Branca<\n>3 - Dama Preta<\n>4 - Dama Branca
	 */
	Celula (int x, int y, int peca){
		super("");
		this.x = x;
		this.y = y;
		this.peca = peca;
		
		//Configs para que a pessa fique invis�vel
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		this.setVisible(true);
		this.setBounds(50*y, 50*x, 50, 50); //Posi��o no absolut layout
		this.setActionCommand("selected");
		this.mySetPeca (peca);
		
	}

	/**
	 * Retorna a coordenada x da celula no tabuleiro
	 * @return a coordenada x da celula no tabuleiro
	 */
	public int myGetX () {
		return x;
	}
	
	/**
	 * Retorna a coordenada y da celula no tabuleiro
	 * @return a coordenada y da celula no tabuleiro
	 */
	public int myGetY () {
		return y;
	}
	
	/**
	 * Retorna a peca contida na celula
	 * @return Inteiro representando a pe�a armazenada na celula:<\n>-1 - Celula incacessivel<\n>0 - Vazia<\n>1 - Preta<\n>2 -  Branca<\n>3 - Dama Preta<\n>4 - Dama Branca
	 */
	public int myGetPeca () {
		return peca;
	}
	
	/**
	 * Coloca/troca a pe�a dentro da celula, atualizando o icone.
	 * @param x Inteiro representando a pe�a armazenada na celula:<\n>-1 - Celula incacessivel<\n>0 - Vazia<\n>1 - Preta<\n>2 -  Branca<\n>3 - Dama Preta<\n>4 - Dama Branca
	 */
	public void mySetPeca (int x) {
		
		//Seleciona o icone correto de acordo com o que esta na celula
		this.peca = x;
		switch (x) {
		
		case (0): //vazio
			this.setIcon(new ImageIcon(this.getClass().getResource("/vazio.png")));
			this.setSelectedIcon(new ImageIcon(this.getClass().getResource("/vazio_selected.png")));
			break;
		
		case (1): //preto
			this.setIcon(new ImageIcon(this.getClass().getResource("/peca_preta.png")));
			this.setSelectedIcon(new ImageIcon(this.getClass().getResource("/peca_preta_selected.png")));
			break;
			
		case (2): //branco
			this.setIcon(new ImageIcon(this.getClass().getResource("/peca_branca.png")));
			this.setSelectedIcon(new ImageIcon(this.getClass().getResource("/peca_branca_selected.png")));
			break;
		
		case (3): //dama preta
			this.setIcon(new ImageIcon(this.getClass().getResource("/dama_preta.png")));
			this.setSelectedIcon(new ImageIcon(this.getClass().getResource("/dama_preta_selected.png")));
			break;
		
		case (4): //dama branca
			this.setIcon(new ImageIcon(this.getClass().getResource("/dama_branca.png")));
			this.setSelectedIcon(new ImageIcon(this.getClass().getResource("/dama_branca_selected.png")));
			break;
			
		
		}
		
	}

}
