package main;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Usado na classe Servidor para que o mesmo aceite várias conexões simultaneas parelelas de clientes
 * @author Rafael Corradini da Cunha (9424322)
 *
 */
public class ServerThread extends Thread {
	private Socket cliente;
	
	/**
	 * Construtor da classe
	 * @param cliente Objeto Socket do cliente que está fazendo a conexão
	 */
	public ServerThread(Socket cliente) {
		this.cliente = cliente;
	}
	
	/**
	 * método que é executado para cada Thread(conexão) criada
	 */
	public void run(){
		System.out.println("Nova conexão com o cliente "+ cliente.getInetAddress().getHostAddress());
		PrintStream saida = null;
		Scanner in = null;
		String line = null;
		Placar placar = null;
		RolaDados dados = null;
		int[] dadosSorteados;
		int aux;
		
		try {
			saida = new PrintStream(cliente.getOutputStream());
			in = new Scanner(cliente.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			placar = new Placar();
			dados = new RolaDados(5);
			line = in.nextLine();
			saida.println("Conectado com sucesso!");
			if(!getComando(line).equals("I")) 
				throw new Exception("Comando inválido, esperava-se o comando I");
			System.out.println("Usuário: "+ line.split(" ")[1]);
			
			
			dadosSorteados = new int[5];
			
			
			for(int i = 1; i<=10; i++) {
				line = in.nextLine();
				if(!getComando(line).equals("R"+i)) 
					throw new Exception("Comando inválido, esperava-se o comando R"+i);
				System.out.println("R"+i);
						
				dadosSorteados = dados.rolar();
				
				saida.println(dados.strDados());
				System.out.println("dados -->"+ dados.strDados());
				
				line = in.nextLine();
				if(!getComando(line).equals("T")) 
					throw new Exception("Comando inválido, esperava-se o comando T");
				dadosSorteados = dados.rolar(toBoolean(line.substring(2,11)));	
				saida.println(dados.strDados());
				
				System.out.println("T1 --> "+line.substring(2,11));
				System.out.println("dados -->"+ dados.strDados());
				line = in.nextLine();
				if(!getComando(line).equals("T")) 
					throw new Exception("Comando inválido, esperava-se o comando T");
				
				dadosSorteados = dados.rolar(toBoolean(line.substring(2,11)));	
				saida.println(dados.strDados());
				
				
				
				System.out.println("T2 --> "+line.substring(2,11));
				System.out.println("dados --> "+dados.strDados());
				
				line = in.nextLine();
				if(!getComando(line).equals("P"+i)) 
					throw new Exception("Comando inválido, esperava-se o comando P"+i);
				aux = Integer.parseInt(line.split(" ")[1]);
				System.out.println("AI escolheu placar --> "+aux);
				if(aux>10 || aux<1) 
					throw new Exception("Posição inválida ou já preenchida no comando P"+i);
				placar.add(aux-1, dadosSorteados);
				
				System.out.println(placar);
				
			
				
			}	
		} catch(Exception e) {
			System.out.println(e);
		}
		
		System.out.println("\nPontos obtidos: "+ placar.getScore());
		System.out.println("Fim da conexao com "+ cliente.getInetAddress().getHostAddress());
	}
	
	private boolean[] toBoolean(String str) {
		String arr[] = str.split(" ");
		boolean[] ret = new boolean[5];
		for(int i = 0; i < 5; i++) {
			
			if(arr[i].equals("1")){ 
				ret[i] = true;
			} else {
				ret[i] = false;
			}	
				
		}
		return ret;
	}
	
	private String getComando(String line) {
		return line.split(" ")[0];
	}
}
