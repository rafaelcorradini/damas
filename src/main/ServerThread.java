package main;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Usado na classe Servidor para que o mesmo aceite várias conexões simultaneas parelelas de clientes
 * @author Rafael Corradini da Cunha (9424322)
 * @author Guilherme Alves Campos (8522320)
 */
public class ServerThread extends Thread {
	private Socket jogador1;
	private Socket jogador2;
	boolean e1 = false;
	boolean e2 = false;
	
	/**
	 * Construtor da classe
	 * @param cliente Objeto Socket do cliente que está fazendo a conexão
	 */
	public ServerThread(Socket jogador1, Socket jogador2) {
		this.jogador1 = jogador1;
		this.jogador2 = jogador2;
	}
	
	/**
	 * método que é executado para cada Thread(conexão) criada
	 */
	public void run(){
		System.out.println("Nova conexão com o jogador1: "+ jogador1.getInetAddress().getHostAddress());
		System.out.println("Nova conexão com o jogador2: "+ jogador2.getInetAddress().getHostAddress());
		
		PrintStream j1Out = null;
		Scanner j1In = null;
		PrintStream j2Out = null;
		Scanner j2In = null;
		String line = null;
		Jogador j1 = null;
		Jogador j2 = null;
		Jogador jTemp = null;
		Damas damas = null;
		
		try {
			j1Out = new PrintStream(jogador1.getOutputStream());
			j1In = new Scanner(jogador1.getInputStream());
			j2Out = new PrintStream(jogador2.getOutputStream());
			j2In = new Scanner(jogador2.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			j1Out.println("Conectado com sucesso!");
			j1Out.println("J 1");
			j2Out.println("Conectado com sucesso!");
			j2Out.println("J 2");
			
			line = j1In.nextLine();
			if(!getComando(line).equals("I")) 
				throw new Exception("Comando inválido, esperava-se o comando I");
			j1 = new Jogador(line.split(" ")[1], 1);
			
			line = j2In.nextLine();
			if(!getComando(line).equals("I")) 
				throw new Exception("Comando inválido, esperava-se o comando I");
			j2 = new Jogador(line.split(" ")[1], 2);
			
			System.out.println("Jogador1: "+ j1.getNome() + " Cor: "+ j1.getCor());
			System.out.println("Jogador2: "+ j2.getNome() + " Cor: "+ j2.getCor());
			
			j1Out.println(j2.getNome());
			j2Out.println(j1.getNome());
			
			damas = new Damas(8, j1, j2);
			
			while(true) {
				if (damas.getVez() == 1) {
					line = j1In.nextLine();
					jTemp = j1;	
				} else {
					line = j2In.nextLine();
					jTemp = j2;
				}
				System.out.println(line);
				if(getComando(line).equals("M")) { 
					damas.fazerMovimento(getComandoInt(line, 1), getComandoInt(line, 2), getComandoInt(line, 3), getComandoInt(line, 4), jTemp);
				} else if(getComando(line).equals("C")) { 
					if (damas.confirmarJogada()) {
						if (damas.getVez() == 2) {
							j2Out.println(damas.getTabuleiroS());
						} else {
							j1Out.println(damas.getTabuleiroS());
						}
						
					}
				} else if(getComando(line).equals("R")) { 
					damas.refazerJogada();				
				} else if (getComando(line).equals("E")) {
					if (jTemp == j1) {
						e1 = true;
					} else if (jTemp == j2) {
						e2 = true;
					}
					
					if (e1 && e2) {
						j1Out.println("E");
						j2Out.println("E");
					}
				} else {
					System.out.println(damas.getVitoria());
					if (damas.getVitoria() == 1) {
						j1Out.println("V1");
						j2Out.println("V1");
						System.out.println("Jogador1 vencedor" + j1.getPecas());
						break;
					}
				
					if (damas.getVitoria() == 2) {
						j1Out.println("V2");
						j2Out.println("V2");
						System.out.println("Jogador2 vencedor " + j2.getPecas());
						break;
					}
				}
							
			}	
		} catch(Exception e) {
			System.out.println(e);
		}
		System.out.println("desconectado");
	}
	
	private String getComando(String line) {
		return line.split(" ")[0];
	}
	
	/**
	 * Método privado, usado no método run(), faz split em uma string e devolve o comando da posição n transformando-o em um inteiro.
	 * @param line
	 * @param n
	 * @return comando inteiro.
	 */
	private int getComandoInt(String line, int n) {
		return Integer.parseInt(line.split(" ")[n]);
	}
}
