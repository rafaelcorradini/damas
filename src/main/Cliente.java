package main;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Classe que representa um cliente em uma conexão socket para o jogo bozó
 * @author Rafael Corradini da Cunha (9424322)
 *
 */
public class Cliente {

	public static void main(String[] args) throws Exception {

			conexao();

		
	}
	
	private static void conexao() throws Exception {
		Socket cliente = new Socket("127.0.0.1",9669);
		int r = 1;
		Placar placar = new Placar();
		RolaDados dados = new RolaDados(5);
		int aux = 1;
		
		System.out.println("O cliente se conectou ao servidor!");
		
		PrintStream saida = new PrintStream(cliente.getOutputStream());
		Scanner teclado = new Scanner(System.in);
		Scanner server = new Scanner(cliente.getInputStream());
		
		saida.println("I 9424322");
		server.nextLine();
		while(r<=10) {
			
			saida.println("R"+r);
			dados.trocarDados(server.nextLine());
			saida.println("T "+dados.melhorRolar(placar));
			dados.trocarDados(server.nextLine());
			saida.println("T "+dados.melhorRolar(placar));
			dados.trocarDados(server.nextLine());
			aux = placar.getMelhorScore(dados.rolar(""));
			placar.add(aux-1, dados.rolar(""));
			saida.println("P"+r+" "+aux);
			r++;
			
		}
		saida.println("F");
		
		System.out.println("Cliente Fechado.");
		saida.close();
		teclado.close();
		server.close();
		cliente.close();
	}

}