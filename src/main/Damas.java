package main;
class Damas {
	private int[][] tabuleiro = new int[8][8]; // 0 - vazio, 1 - peça preta, 2 - peça branca, 3 - dama preta e 4 - dama branca
	private int[][] tabuleiroTemp = new int[8][8];
	private int pontosJogada = 0; // pontos acumulados na jogada atual.
	private int tamanho = 8;
	private int melhorJogada = 0;
	
	/**
	 * Método construtor, atribui 8 para o tamanho do tabuleiro.
	 * Tabuleiro 8x8, 64 casas.
	 * O tamanho não poderá ser alterado posteriormente. 
	 */
	public Damas() {
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
	 * @param fromI Linha onde a peça que será movida está.
	 * @param fromJ Coluna onde a peça que será movida está.
	 * @param toI Linha para onde a peça movida deve ir.
	 * @param toJ Coluna para onde a peça movida deve ir.
	 * @return Retorna um inteiro, -1 - se não for possível fazer o movimento, 0 - se fizer mas não fizer ponto, 1 - se fizer e fizer ponto. 
	 */
	public int fazerMovimento(int fromI, int fromJ, int toI, int toJ, Jogador jogador) {
		final int from = tabuleiro[fromI][fromJ], to = tabuleiro[toI][toJ];
		boolean flag = false;
		
		if (from == 0) return -1;
		if (jogador.getCor() != from && jogador.getCor() != (from+2)) return -1;
		if (fromI == toI && fromJ == toJ) return -1;
		if ((from == 1 && fromI < toI) || (from == 2 && fromI > toI)) { 
			if (from == 1 && ((fromI-1 == toI && fromJ+1 == toJ) || (fromI-1 == toI && fromJ-1 == toJ)))
				flag = true;
			else if (from == 2 && ((fromI+1 == toI && fromJ+1 == toJ) || (fromI+1 == toI && fromJ-1 == toJ)))
				flag = true;
		}
		if (flag && to == 0) {
			tabuleiroTemp[fromI][fromJ] = 0;
			tabuleiroTemp[toI][toJ] = from;
			return 0;
		}
		flag = false;
		if (from == 1 || from == 2) {
			if (((fromI-2 == toI && fromJ+2 == toJ) || (fromI-2 == toI && fromJ-2 == toJ)) || ((fromI+1 == toI && fromJ+1 == toJ) || (fromI+1 == toI && fromJ-1 == toJ))) {
				if (from == 1)
					if (tabuleiroTemp[(fromI+toI)/2][(fromJ+toJ)/2] == 2 || tabuleiroTemp[(fromI+toI)/2][(fromJ+toJ)/2] == 4)
						flag = true;
				if (from == 2)
					if (tabuleiroTemp[(fromI+toI)/2][(fromJ+toJ)/2] == 1 || tabuleiroTemp[(fromI+toI)/2][(fromJ+toJ)/2] == 3)
						flag = true;
			} 		
		}
		if (flag && to == 0) {
			tabuleiroTemp[fromI][fromJ] = 0;
			tabuleiroTemp[(fromI+toI)/2][(fromJ+toJ)/2] = 0;
			tabuleiroTemp[toI][toJ] = from;
			return 1;
		}
		if (((from == 3 || from == 4) && ((fromI-toI/fromJ-toJ) == 1 || (fromI-toI/fromJ-toJ) == -1)) && to == 0) {
			if ((fromI-toI/fromJ-toJ) == 1)  {
				if (fromI>toI) {
					for (int i = fromI, j = fromJ; i != toI || j != toJ; i--, j++) {
						if (tabuleiroTemp[i][j] > 0) {
							if (tabuleiroTemp[i-1][j+1] > 0 || (i-1 != toI && j+1 != toJ)) return -1;
							if (jogador.getCor() == 1) {
								if (tabuleiroTemp[i][j] == 2 || tabuleiroTemp[i][j] == 4) {
									tabuleiroTemp[fromI][fromJ] = 0;
									tabuleiroTemp[toI][toJ] = from;
									return 1;
								} else 
									return -1;
							} else {
								if (tabuleiroTemp[i][j] == 3 || tabuleiroTemp[i][j] == 1){
									tabuleiroTemp[fromI][fromJ] = 0;
									tabuleiroTemp[toI][toJ] = from;
									return 1;
								} else
									return -1;
							}
							
						}
					} 
				} else {
					for (int i = fromI, j = fromJ; i != toI || j != toJ; i++, j--) {
						if (tabuleiroTemp[i][j] > 0) {
							if (tabuleiroTemp[i+1][j-1] > 0 || (i+1 != toI && j-1 != toJ)) return -1;
							if (jogador.getCor() == 1) {
								if (tabuleiroTemp[i][j] == 2 || tabuleiroTemp[i][j] == 4) {
									tabuleiroTemp[fromI][fromJ] = 0;
									tabuleiroTemp[toI][toJ] = from;
									return 1;
								} else 
									return -1;
							} else {
								if (tabuleiroTemp[i][j] == 3 || tabuleiroTemp[i][j] == 1){
									tabuleiroTemp[fromI][fromJ] = 0;
									tabuleiroTemp[toI][toJ] = from;
									return 1;
								} else
									return -1;
							}
							
						}
					} 
				}
			} else {
				if (fromI<toI) {
					for (int i = fromI, j = fromJ; i != toI || j != toJ; i++, j++) {
						if (tabuleiroTemp[i][j] > 0) {
							if (tabuleiroTemp[i+1][j+1] > 0 || (i+1 != toI && j+1 != toJ)) return -1;
							if (jogador.getCor() == 1) {
								if (tabuleiroTemp[i][j] == 2 || tabuleiroTemp[i][j] == 4) {
									tabuleiroTemp[fromI][fromJ] = 0;
									tabuleiroTemp[toI][toJ] = from;
									return 1;
								} else 
									return -1;
							} else {
								if (tabuleiroTemp[i][j] == 3 || tabuleiroTemp[i][j] == 1){
									tabuleiroTemp[fromI][fromJ] = 0;
									tabuleiroTemp[toI][toJ] = from;
									return 1;
								} else
									return -1;
							}
							
						}
					} 
				} else {
					for (int i = fromI, j = fromJ; i != toI || j != toJ; i--, j--) { // terminar
						if (tabuleiroTemp[i][j] > 0) {
							if (tabuleiroTemp[i-1][j-1] > 0 || (i-1 != toI && j-1 != toJ)) return -1;
							if (jogador.getCor() == 1) {
								if (tabuleiroTemp[i][j] == 2 || tabuleiroTemp[i][j] == 4) {
									tabuleiroTemp[fromI][fromJ] = 0;
									tabuleiroTemp[toI][toJ] = from;
									return 1;
								} else 
									return -1;
							} else {
								if (tabuleiroTemp[i][j] == 3 || tabuleiroTemp[i][j] == 1){
									tabuleiroTemp[fromI][fromJ] = 0;
									tabuleiroTemp[toI][toJ] = from;
									return 1;
								} else
									return -1;
							}
							
						}
					} 
				}
			}
		}

		return -1;
	}
	
	public void confirmarJogada() {
		tabuleiro = tabuleiroTemp;
	}
	
	public int melhorJogada(Jogador jogador) {
		final int cor = jogador.getCor();
		int[][] tabuleiroT = tabuleiroTemp;
		
		for (int i = 0; i < tamanho; i++) {
			for (int j = 0; j < tamanho; j++) {
				if(tabuleiro[i][j] > 0)
					melhorJogadaAux(i, j, jogador, 0);
			}
		}
		
		tabuleiroTemp = tabuleiroT;
		return melhorJogada;
	}
	
	private void melhorJogadaAux(int i, int j, Jogador jogador, int cont) {
		int[][] tabuleiroT = tabuleiroTemp;
		
		if(melhorJogada < cont+1) melhorJogada = cont+1;
		
		if (tabuleiro[i][j] < 3) {
			if (fazerMovimento(i, j, i-2, j+2, jogador) == 1)
				melhorJogadaAux(i-2, j+2, jogador, cont+1);
			
			if (fazerMovimento(i, j, i-2, j-2, jogador) == 1)
				melhorJogadaAux(i-2, j-2, jogador, cont+1);
			
			if (fazerMovimento(i, j, i+2, j+2, jogador) == 1)
				melhorJogadaAux(i+2, j+2, jogador, cont+1);
			
			if (fazerMovimento(i, j, i+2, j-2, jogador) == 1)
				melhorJogadaAux(i+2, j-2, jogador, cont+1);
				
		} else if (tabuleiro[i][j] > 2) {
			// terminar
		}
		

	}
	
	
}