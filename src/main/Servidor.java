package main;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe que representa um servidor de uma conexão socket que manipula o jogo bozó
 * @author root
 *
 */
public class Servidor {
	public static void main(String[] args) throws Exception {
		ServerSocket servidor = new ServerSocket(9669);
		System.out.println("Porta 9669 aberta!");
		while(true) {
			Socket cliente = servidor.accept();
			Thread st = new ServerThread(cliente);
			st.start();
		}
	}
}
