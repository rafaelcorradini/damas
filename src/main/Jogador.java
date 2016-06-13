package main;

public class Jogador {
	private String nome;
	private int cor; // 1 - preto, 2 - branco
	
	/**
	 * Método construtor, atribui nome do jogador e a cor das peças do mesmo.
	 * @param nome Nome do jogador
	 * @param cor A cor das peças do jogador, 1 para preto e 2 para branco.
	 */
	public Jogador(String nome, int cor) {
		this.nome = nome;
		if (cor != 1 && cor != 2) 
			throw new IllegalArgumentException("Número da cor inválido");
		this.cor = cor;
	}
	
	public int getCor() {
		return cor;
	}
}
