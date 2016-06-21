package gui;

import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

public class Celula extends JToggleButton {

	private int x;
	private int y;
	private int peca;
	
	Celula (int x, int y, int peca){
		super("");
		this.x = x;
		this.y = y;
		this.peca = peca;
		
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		this.setVisible(true);
		this.setBounds(50*y, 50*x, 50, 50);
		this.setActionCommand("selected");
		this.mySetPeca (peca);
		
	}

	public int myGetX () {
		return x;
	}
	public int myGetY () {
		return y;
	}
	public int myGetPeca () {
		return peca;
	}
	
	public void mySetPeca (int x) {
		
		this.peca = x;
		switch (x) {
		
		case (0):
			this.setIcon(new ImageIcon(this.getClass().getResource("/vazio.png")));
			this.setSelectedIcon(new ImageIcon(this.getClass().getResource("/vazio_selected.png")));
			break;
		
		case (1):
			this.setIcon(new ImageIcon(this.getClass().getResource("/peca_preta.png")));
			this.setSelectedIcon(new ImageIcon(this.getClass().getResource("/peca_preta_selected.png")));
			break;
			
		case (2):
			this.setIcon(new ImageIcon(this.getClass().getResource("/peca_branca.png")));
			this.setSelectedIcon(new ImageIcon(this.getClass().getResource("/peca_branca_selected.png")));
			break;
		
		case (3):
			this.setIcon(new ImageIcon(this.getClass().getResource("/dama_preta.png")));
			this.setSelectedIcon(new ImageIcon(this.getClass().getResource("/dama_preta_selected.png")));
			break;
		
		case (4):
			this.setIcon(new ImageIcon(this.getClass().getResource("/dama_branca.png")));
			this.setSelectedIcon(new ImageIcon(this.getClass().getResource("/dama_branca_selected.png")));
			break;
			
		
		}
		
	}

}
