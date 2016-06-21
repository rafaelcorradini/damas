package main;
public class Damas {
	private int[][] tabuleiro; // -1 - não é casa, 0 - vazio, 1 - peça preta, 2 - peça branca, 3 - dama preta e 4 - dama branca
	private int[][] tabuleiroTemp;
	private int tamanho = 8;
	private int melhorJogada1 = 0; // melhor jogada que pode ser feita na rodada pelo jogador 1, atribuida pelo método melhorJogada
	private int melhorJogada2 = 0; // melhor jogada que pode ser feita na rodada pelo jogador 2, atribuida pelo método melhorJogada
	private Jogador jogador1 = null;
	private Jogador jogador2 = null;
	private int vez = 1;
	private int pontosRodada = 0; // pontos que o jogador fez na rodada até o momento.
	
	/**
	 * Método construtor, atribui 8 para o tamanho do tabuleiro.
	 * Tabuleiro 8x8, 64 casas.
	 * O tamanho não poderá ser alterado posteriormente. 
	 */
	public Damas() {
		tabuleiro = new int[tamanho][tamanho];	
		initTabuleiro(tamanho);
		tabuleiroTemp = tabuleiro.clone();
		jogador1 = new Jogador("j1", 1);
		jogador2 = new Jogador("j2", 2);
		jogador1.setPecas(12);
		jogador2.setPecas(12);
	}
	
	/**
	 * Método construtor, atribui o tamanho do tabuleiro que deverá ser passado por parâmetro.
	 * Ex: 8 para um tabuleiro 8x8, 64 casas.
	 * O tamanho não poderá ser alterado posteriormente e só são aceitos tamanhos pares maiores ou iguais a 6. 
	 * @param tamanho Tamanho do tabuleiro.
	 */
	public Damas(int tamanho) {
		if (tamanho % 2 != 0 || tamanho < 6) 
			throw new IllegalArgumentException("Tamanho do tabuleiro inválido.");
		this.tamanho = tamanho;
		tabuleiro = new int[tamanho][tamanho];	
		initTabuleiro(tamanho);
		tabuleiroTemp = cloneTabuleiro(tabuleiro);
		jogador1 = new Jogador("j1", 1);
		jogador2 = new Jogador("j2", 2);
		jogador1.setPecas((tamanho-2) * tamanho / 4);
		jogador2.setPecas((tamanho-2) * tamanho / 4);
	}
	
	/**
	 * Método construtor, atribui o tamanho do tabuleiro que deverá ser passado por parâmetro.
	 * Ex: 8 para um tabuleiro 8x8, 64 casas.
	 * O tamanho não poderá ser alterado posteriormente e só são aceitos tamanhos pares maiores ou iguais a 6. 
	 * @param tamanho Tamanho do tabuleiro.
	 * @param jogador1 Referência a um objeto que representa o jogador1, que terá cor peta.
	 * @param Jogador2 Referência a um objeto que representa o jogador2, que terá cor branca.
	 */
	public Damas(int tamanho, Jogador jogador1, Jogador jogador2) {
		if (tamanho % 2 != 0 || tamanho < 6) 
			throw new IllegalArgumentException("Tamanho do tabuleiro inválido.");
		this.tamanho = tamanho;
		tabuleiro = new int[tamanho][tamanho];	
		initTabuleiro(tamanho);
		tabuleiroTemp = cloneTabuleiro(tabuleiro);
		this.jogador1 = jogador1;
		this.jogador1.setCor(1);
		this.jogador2 = jogador2;
		this.jogador2.setCor(2);
		jogador1.setPecas((tamanho-2) * tamanho / 4);
		jogador2.setPecas((tamanho-2) * tamanho / 4);
	}
	
	/**
	 * Inicializa o tabuleiro colocando as peças em seus devidos lugares
	 * @param tamanho Tamanho do tabuleiro.
	 */
	public void initTabuleiro(int tamanho) {
		for(int i = 0; i < tamanho; i++) {
			for(int j = 0; j < tamanho; j++) {
				if ((i == 0 && j == 0) || (i+j) % 2 == 0)
					tabuleiro[i][j] = -1;
				else if (i < ((tamanho-2) / 2))
					tabuleiro[i][j] = 1;
				else if (i > ((tamanho-2) / 2) + 1)
					tabuleiro[i][j] = 2;
			}
		}
	}
	
	/**
	 * Usado para clonar o tabuleiro.
	 */
	private int[][] cloneTabuleiro(int[][] oldTabuleiro) {
		int[][] newTabuleiro = new int[tamanho][tamanho];
		for(int i = 0; i < tamanho; i++) {
			newTabuleiro[i] = oldTabuleiro[i].clone(); 
		}
		
		return newTabuleiro;
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
		return cloneTabuleiro(this.tabuleiro);
	}
	
	public int[][] getTabuleiroTemp() {
		return cloneTabuleiro(this.tabuleiroTemp);
	}
	
	/**
	 * Retorna a cor do jogador que tem sua vez de jogar.
	 * @return inteiro que representa a cor do jogador.
	 */
	public int getVez() {
		return vez;
	}
	
	public Jogador getJogador() {
		return vez == 1 ? jogador1 : jogador2;
	}
	
	public int getPontosRodada() {
		return pontosRodada;
	}
	
	/**
	 * 
	 * @param fromI Linha onde a peça que será movida está.
	 * @param fromJ Coluna onde a peça que será movida está.
	 * @param toI Linha para onde a peça movida deve ir.
	 * @param toJ Coluna para onde a peça movida deve ir.
	 * @return Retorna um inteiro, -1 - se não for possível fazer o movimento, 0 - se fizer mas não fizer ponto, 1 - se fizer e fizer ponto. 
	 * @throws Exception 
	 */
	public int fazerMovimento(int fromI, int fromJ, int toI, int toJ, Jogador jogador) throws Exception {
		final int from = tabuleiroTemp[fromI][fromJ], to = tabuleiroTemp[toI][toJ];
		boolean flag = false;
		
		if (jogador.getCor() != getVez()) 
			throw new Exception("O jogador não pode fazer um movimento nem verificar a melhor jogada, pois não é sua vez.");
		
		if (from == 0) return -1;
		if (jogador.getCor() != from && jogador.getCor()+2 != from) return -1;
		if (!isInArray(fromI, fromJ, tabuleiroTemp) || !isInArray(toI, toJ, tabuleiroTemp)) return -1;
		if (fromI == toI && fromJ == toJ) return -1;
		if (to != 0) return -1;
		
		if ((from == 1 && fromI < toI) || (from == 2 && fromI > toI)) { 
			if (from == 1 && ((fromI+1 == toI && fromJ+1 == toJ) || (fromI+1 == toI && fromJ-1 == toJ)))
				flag = true;
			else if (from == 2 && ((fromI-1 == toI && fromJ+1 == toJ) || (fromI-1 == toI && fromJ-1 == toJ)))
				flag = true;
		}
		if (flag) {
			if (pontosRodada > 0) {
				return -1;
			}
			tabuleiroTemp[fromI][fromJ] = 0;
			tabuleiroTemp[toI][toJ] = from;
			if (toI == tamanho-1 && from == 1) {
				tabuleiroTemp[toI][toJ] = 3;
			}
			if (toI == 0 && from == 2) {
				tabuleiroTemp[toI][toJ] = 4;
			}
			return 0;
		}
		flag = false;
		if (from == 1 || from == 2) {
			if (((fromI-2 == toI && fromJ+2 == toJ) || (fromI-2 == toI && fromJ-2 == toJ)) || ((fromI+2 == toI && fromJ+2 == toJ) || (fromI+2 == toI && fromJ-2 == toJ))) {
				if (from == 1)
					if (tabuleiroTemp[(fromI+toI)/2][(fromJ+toJ)/2] == 2 || tabuleiroTemp[(fromI+toI)/2][(fromJ+toJ)/2] == 4)
						flag = true;
				if (from == 2)
					if (tabuleiroTemp[(fromI+toI)/2][(fromJ+toJ)/2] == 1 || tabuleiroTemp[(fromI+toI)/2][(fromJ+toJ)/2] == 3)
						flag = true;
			} 		
		}
		if (flag) {
			tabuleiroTemp[fromI][fromJ] = 0;
			tabuleiroTemp[(fromI+toI)/2][(fromJ+toJ)/2] = 0;
			tabuleiroTemp[toI][toJ] = from;
			if (toI == tamanho-1 && from == 1) {
				tabuleiroTemp[toI][toJ] = 3;
			}
			if (toI == 0 && from == 2) {
				tabuleiroTemp[toI][toJ] = 4;
			}
			pontosRodada++;
			return 1;
		}
		if ((from == 3 || from == 4) && ((fromI-toI)/(fromJ-toJ) == 1 || (fromI-toI)/(fromJ-toJ) == -1)) {
			if ((fromI-toI)/(fromJ-toJ) == -1)  {
				if (fromI>toI) {
					for (int i = fromI-1, j = fromJ+1; i != toI || j != toJ; i--, j++) {
						if (tabuleiroTemp[i][j] > 0) {
							if (isInArray(i-1, j+1, tabuleiroTemp))  {
								if (tabuleiroTemp[i-1][j+1] > 0 || (i-1 != toI && j+1 != toJ)) return -1;
							}
							
							if (jogador.getCor() == 1) {
								if (tabuleiroTemp[i][j] == 2 || tabuleiroTemp[i][j] == 4) {
									tabuleiroTemp[fromI][fromJ] = 0;
									tabuleiroTemp[i][j] = 0;
									tabuleiroTemp[toI][toJ] = from;
									pontosRodada++;
									return 1;
								} else 
									return -1;
							} else {
								if (tabuleiroTemp[i][j] == 3 || tabuleiroTemp[i][j] == 1){
									tabuleiroTemp[fromI][fromJ] = 0;
									tabuleiroTemp[i][j] = 0;
									tabuleiroTemp[toI][toJ] = from;
									pontosRodada++;
									return 1;
								} else
									return -1;
							}
							
						}
					} 
				} else {
					for (int i = fromI+1, j = fromJ-1; i != toI || j != toJ; i++, j--) {
						if (tabuleiroTemp[i][j] > 0) {
							if (isInArray(i+1, j-1, tabuleiroTemp))  {
								if (tabuleiroTemp[i+1][j-1] > 0 || (i+1 != toI && j-1 != toJ)) return -1;
							}
							if (jogador.getCor() == 1) {
								if (tabuleiroTemp[i][j] == 2 || tabuleiroTemp[i][j] == 4) {
									tabuleiroTemp[fromI][fromJ] = 0;
									tabuleiroTemp[i][j] = 0;
									tabuleiroTemp[toI][toJ] = from;
									pontosRodada++;
									return 1;
								} else 
									return -1;
							} else {
								if (tabuleiroTemp[i][j] == 3 || tabuleiroTemp[i][j] == 1){
									tabuleiroTemp[fromI][fromJ] = 0;
									tabuleiroTemp[i][j] = 0;
									tabuleiroTemp[toI][toJ] = from;
									pontosRodada++;
									return 1;
								} else
									return -1;
							}
							
						}
					} 
				}
			} else {
				if (fromI<toI) {
					for (int i = fromI+1, j = fromJ+1; i != toI || j != toJ; i++, j++) {
						if (tabuleiroTemp[i][j] > 0) {
							if (isInArray(i+1, j+1, tabuleiroTemp))  {
								if (tabuleiroTemp[i+1][j+1] > 0 || (i+1 != toI && j+1 != toJ)) return -1;
							}
							if (jogador.getCor() == 1) {
								if (tabuleiroTemp[i][j] == 2 || tabuleiroTemp[i][j] == 4) {
									tabuleiroTemp[fromI][fromJ] = 0;
									tabuleiroTemp[i][j] = 0;
									tabuleiroTemp[toI][toJ] = from;
									pontosRodada++;
									return 1;
								} else 
									return -1;
							} else {
								if (tabuleiroTemp[i][j] == 3 || tabuleiroTemp[i][j] == 1){
									tabuleiroTemp[fromI][fromJ] = 0;
									tabuleiroTemp[i][j] = 0;
									tabuleiroTemp[toI][toJ] = from;
									pontosRodada++;
									return 1;
								} else
									return -1;
							}
							
						}
					} 
				} else {
					for (int i = fromI-1, j = fromJ-1; i != toI || j != toJ; i--, j--) { 
						if (tabuleiroTemp[i][j] > 0) {
							if (isInArray(i-1, j-1, tabuleiroTemp))  {
								if (tabuleiroTemp[i-1][j-1] > 0 || (i-1 != toI && j-1 != toJ)) return -1;
							}
							if (jogador.getCor() == 1) {
								if (tabuleiroTemp[i][j] == 2 || tabuleiroTemp[i][j] == 4) {
									tabuleiroTemp[fromI][fromJ] = 0;
									tabuleiroTemp[i][j] = 0;
									tabuleiroTemp[toI][toJ] = from;
									pontosRodada++;
									return 1;
								} else 
									return -1;
							} else {
								if (tabuleiroTemp[i][j] == 3 || tabuleiroTemp[i][j] == 1){
									tabuleiroTemp[fromI][fromJ] = 0;
									tabuleiroTemp[i][j] = 0;
									tabuleiroTemp[toI][toJ] = from;
									pontosRodada++;
									return 1;
								} else
									return -1;
							}
							
						}
					} 
				}
			}
			if (pontosRodada > 0) {
				return -1;
			}
			tabuleiroTemp[fromI][fromJ] = 0;
			tabuleiroTemp[toI][toJ] = from;
			return 0;
		}
		
		return -1;
		
	}
	
	/**
	 * Confirma a jogada feita, salvando-a.
	 * @return Retorna true se o jogador tiver feito pontos suficientes, caso contraŕio retorna false.
	 */
	public boolean confirmarJogada() {
		tabuleiro = cloneTabuleiro(tabuleiroTemp);
		if (getVez() == 1) {
			if (melhorJogada1 > pontosRodada)
				return false;
			jogador2.removePecas(melhorJogada1);
			melhorJogada1 = 0;
			vez = 2;
			pontosRodada = 0;
		} else {
			if (melhorJogada2 > pontosRodada)
				return false;
			jogador1.removePecas(melhorJogada2);
			melhorJogada2 = 0;
			vez = 1;
			pontosRodada = 0;
		}
		return true;
		
	}
	
	/**
	 * Anula os movimentos já feitos pelo jogador
	 */
	public void refazerJogada() {
		tabuleiroTemp = cloneTabuleiro(tabuleiro); 
		pontosRodada = 0;
	}
	
	/**
	 * Verifica qual a pontuação da melhor jogada que pode ser feita nas configurações atuais.
	 * Passa por todas as peças, e olha qual a melhor jogada a se fazer com a peça, utilizando o método auxiliar melhorJogadaAux().
	 * @param jogador Jogador que está efetuando a jogada.
	 * @return Pontuação da melhor jogada.
	 * @throws Exception 
	 */
	public int melhorJogada(Jogador jogador) throws Exception {
		int[][] tabuleiroT = cloneTabuleiro(tabuleiroTemp);
		int pontos = pontosRodada;
		
		for (int i = 0; i < tamanho; i++) {
			for (int j = 0; j < tamanho; j++) {
				if(tabuleiro[i][j] > 0 && (jogador.getCor() == tabuleiro[i][j] || jogador.getCor()+2 == tabuleiro[i][j]))
					melhorJogadaAux(i, j, jogador, -1);
			}
		}
		
		tabuleiroTemp = cloneTabuleiro(tabuleiroT);
		pontosRodada = pontos;
		
		System.out.println(melhorJogada2);
		System.out.println(melhorJogada1);
		if (jogador.getCor() == 1)
			return melhorJogada1;
		else
			return melhorJogada2;
	}
	
	/**
	 * Usado para verificar se existe a posição no tabuleiro.
	 * @param i
	 * @param j
	 * @return
	 */
	private boolean isInArray(int i, int j, int[][] array) {
		if ((i >=0 && i < tamanho) && (j >=0 && j < tamanho))
			return true;
		else 
			return false;
	}
	
	/**
	 * Método usado em melhor jogada. Verifica qual a melhor jogada a se fazer com a peça que está em ixj.
	 * @param i Linha onde está a peça.
	 * @param j Coluna onde está a peça
	 * @param jogador Jogador que está fazendo a jogada
	 * @param cont
	 * @throws Exception 
	 */
	private void melhorJogadaAux(int i, int j, Jogador jogador, int cont) throws Exception {
		int[][] tabuleiroT = cloneTabuleiro(tabuleiroTemp);
		
		if (jogador.getCor() == 1)
			if (melhorJogada1 < cont+1) melhorJogada1 = cont+1;
		else
			if (melhorJogada2 < cont+1) melhorJogada2 = cont+1;
		
		if (tabuleiroTemp[i][j] < 3) {
			if (isInArray(i-2, j+2, tabuleiroTemp))
				if (fazerMovimento(i, j, i-2, j+2, jogador) == 1)
					melhorJogadaAux(i-2, j+2, jogador, cont+1);
			tabuleiroTemp = cloneTabuleiro(tabuleiroT);
			
			if (isInArray(i-2, j-2, tabuleiroTemp))
				if (fazerMovimento(i, j, i-2, j-2, jogador) == 1)
					melhorJogadaAux(i-2, j-2, jogador, cont+1);
			tabuleiroTemp = cloneTabuleiro(tabuleiroT);
			
			if (isInArray(i+2, j+2, tabuleiroTemp))
				if (fazerMovimento(i, j, i+2, j+2, jogador) == 1)
					melhorJogadaAux(i+2, j+2, jogador, cont+1);
			tabuleiroTemp = cloneTabuleiro(tabuleiroT);
			
			if (isInArray(i+2, j-2, tabuleiroTemp))
				if (fazerMovimento(i, j, i+2, j-2, jogador) == 1)
					melhorJogadaAux(i+2, j-2, jogador, cont+1);
			tabuleiroTemp = cloneTabuleiro(tabuleiroT);
				
		} else if (tabuleiroTemp[i][j] > 2) {
			int diag = (i+j) / 2; 
			int diag2 = Math.abs((i-j) / 2);
			
			if (diag < tamanho/2) {
				for (int u = diag*2+1, v = 0; u >= 0; u--, v++) {
					if (fazerMovimento(i, j, u, v, jogador) == 1)
						melhorJogadaAux(u, v, jogador, cont+1);
					tabuleiroTemp = cloneTabuleiro(tabuleiroT);
				}				
			} else {
				for (int u = tamanho-1, v = diag*2; v < tamanho; u--, v++) {
					if (fazerMovimento(i, j, u, v, jogador) == 1)
						melhorJogadaAux(u, v, jogador, cont+1);
					tabuleiroTemp = cloneTabuleiro(tabuleiroT);
				}	
			}
			
			if (i > j) {
				for (int u = diag2*2+1, v = 0; u < tamanho; u++, v++) {
					if (fazerMovimento(i, j, u, v, jogador) == 1)
							melhorJogadaAux(u, v, jogador, cont+1);
					tabuleiroTemp = cloneTabuleiro(tabuleiroT);
				}				
			} else {
				for (int u = 0, v = diag2*2+1; v < tamanho; u++, v++) {
					if (fazerMovimento(i, j, u, v, jogador) == 1)
						melhorJogadaAux(u, v, jogador, cont+1);
					tabuleiroTemp = cloneTabuleiro(tabuleiroT);
				}	
			}
			
		}
			
		tabuleiroTemp = cloneTabuleiro(tabuleiroT);
		
	}
	
	@Override
	public String toString() {
		String str = "";
		for(int i = 0; i < tamanho; i++) {
			for(int j = 0; j < tamanho; j++) {
				if (tabuleiro[i][j] >= 0)
					str += " "+tabuleiro[i][j]+" | ";
				else 
					str += tabuleiro[i][j]+" | ";
			}
			str += "\n";
			for(int j = 0; j < tamanho; j++) {
				str += "-----";
			}
			str += "\n";
		}
		return str;
		
	}
	
	public static void main(String[] args) {
		Damas d = new Damas();
		System.out.println(d);
	}
}