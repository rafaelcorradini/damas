package main;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe que representa um servidor de uma conexão socket que manipula o jogo de damas
 * @author Rafael Corradini da Cunha (9424322)
 * @author Guilherme Alves Campos (8522320)
 *
 */
public class Servidor {
	public static void main(String[] args) throws Exception {
		ServerSocket servidor = new ServerSocket(9669);
		System.out.println("Porta 9669 aberta!");
		while(true) {
			Socket jogador1 = servidor.accept();
			Socket jogador2 = servidor.accept();
			Thread st = new ServerThread(jogador1, jogador2);
			st.start();
		}
	}
}
