package main;

public class Jogador {
	private String nome;
	private int cor; // 1 - preto, 2 - branco
	private int pecas; // número de peças que o jogador tem
	
	/**
	 * Método construtor, atribui nome do jogador e a cor das peças do mesmo.
	 * @param nome Nome do jogador
	 * @param cor A cor das peças do jogador, 1 para preto e 2 para branco.
	 */
	public Jogador(String nome, int cor) {
		this.nome = nome;
		if (cor != 1 && cor != 2) 
			throw new IllegalArgumentException("Número da cor inválido.");
		this.cor = cor;
		pecas = 12;
	}
	
	/**
	 * Atribui a número de peças do jogador.
	 * @param pecas Número de peças
	 */
	public void setPecas(int pecas) {
		this.pecas = pecas;
	}
	
	/**
	 * Atribui a número de peças do jogador.
	 * @return número de peças do jogador.
	 */
	public int getPecas() {
		return pecas;
	}
	
	/**
	 * Remove n peças do jogador.
	 * @param n Número de peças a serem removidas
	 */
	public void removePecas(int n) {
		pecas = pecas-n;
	}
	
	/**
	 * Atribui a cor das peças do jogador.
	 * @param cor
	 */
	public void setCor(int cor) {
		this.cor = cor;
	}
	
	/**
	 * @return Retorna a cor das peças do jogador.
	 */
	public int getCor() {
		return cor;
	}
	
	/**
	 * Atribui o nome do jogador.
	 * @param Nome Nome do jogador.
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/**
	 * @return Retorna o nome do jogador.
	 */
	public String getNome() {
		return nome;
	}
}
