package main;
class Damas {
	private int[][] tabuleiro = new int[8][8]; // 0 - vazio, 1 - peça preta, 2 - peça branca, 3 - dama preta e 4 - dama branca
	private int tamanho = 8;
	
	/**
	 * Método construtor, atribui 8 para o tamanho do tabuleiro.
	 * Tabuleiro 8x8, 64 casas.
	 * O tamanho não poderá ser alterado posteriormente. 
	 */
	public Damas() {
		this.tamanho = tamanho;
		tabuleiro = new int[tamanho][tamanho];	
	}
	
	/**
	 * Método construtor, atribui o tamanho do tabuleiro que deverá ser passado por parâmetro.
	 * Ex: 8 para um tabuleiro 8x8, 64 casas.
	 * O tamanho não poderá ser alterado posteriormente. 
	 * @param tamanho Tamanho do tabuleiro.
	 */
	public Damas(int tamanho) {
		this.tamanho = tamanho;
		tabuleiro = new int[tamanho][tamanho];	
	}
	
	/**
	 * Retorna o tamanho do tabuleiro, a quantidade de casas que tem uma linha do tabuleiro.
	 * Ex: 8 para um tabuleiro 8x8, 64 casas.
	 * @return Inteiro com o tamanho do tabuleiro.
	 */
	public int getTamanho() {
		return this.tamanho;
	}
	
	/**
	 * Retorna uma matriz que representa o tabuleiro
	 * 0 representa uma casa vazia, 1 uma peça branca, 2 uma peça preta, 3 uma dama branca e 4 uma dama preta.
	 * @return Retorna uma matriz de duas dimensões de inteiros.
	 */
	public int[][] getTabuleiro() {
		return this.tabuleiro;
	}
	
	/**
	 * 
	 * @param fromI
	 * @param fromJ
	 * @param toI
	 * @param toJ
	 * @return Retorna True caso a jogada seja feita, False caso não seja possível fazer a jogada.
	 */
	public boolean fazerJogada(int fromI, int fromJ, int toI, int toJ, Jogador jogador) {
		final int from = tabuleiro[fromI][fromJ], to = tabuleiro[toI][toJ]; 
		
		if((from == 3 || from == 4) || (from == 1 && fromI < toI) || (from == 2 && fromI > toI)) { // aq
			
		} else {
			return false;
		}
		
		
	}
}