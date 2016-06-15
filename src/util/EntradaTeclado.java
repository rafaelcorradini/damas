package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Esta classe foi desenhada para facilitar a entrada de dados
 * pelo teclado. Basicamente ela implementa funções para
 * ler strings, inteiros e doubles. 
 * Ela não faz verificações se o que foi digitado realmente
 * � o esperado.
 * Todos 
 * @author delamaro
 *
 */
public class EntradaTeclado {
	static InputStreamReader isr = new InputStreamReader(System.in);
	static BufferedReader br = new BufferedReader(isr);


	/**
	 * Le um string digitado pelo teclado, até que seja pressionado
	 * um enter. Ou seja, le a linha toda.
	 * @return o string que foi digitado pelo usuário.
	 * @throws IOException
	 */
	public static String leString() {
		String x;
		
		while (true) {
			
			try {
				x = br.readLine();
				return x;
			} catch (IOException e) {
				System.out.print("Entrada inválida, tente novamente.");
			}
		}

	}
	
	/**
	 * Le um char do teclado
	 * @return o string que foi digitado pelo usuário.
	 * @throws IOException
	 */
	public static char leChar() throws IOException {
		char x;
		x = (char) br.read();
		return x;

	}

	/**
	 * Le um string do teclado (uma linha toda) e tenta transformá-lo num inteiro. 
	 * Verifica se o dado digitado pode ser convertido para um inteiro,
	 * se não for válida a entrada espera que o usuário digite novamente.
	 * @return  o valor inteiro digitado pelo usuário. 
	 * @throws IOException
	 */
	public static int leInt() throws IOException {
		
		while(true) {
			try {
				String x = leString();
				return Integer.parseInt(x);
			} catch(NumberFormatException e) {
				System.out.println("Entrada inválida, digite novamente: ");
			}
		}
		
		
	}
	
	/**
	 * Le um string do teclado (uma linha toda) e tenta transformá-lo num inteiro. 
	 * Verifica se o dado digitado pode ser convertido para um inteiro, e se está dentro de [min, max].
	 * Se a entrada não for válida espera o usuário digitar novamente.
	 * @param min Valor mínimo.
	 * @param max Valor máximo.
	 * @return  o valor inteiro digitado pelo usuário. 
	 * @throws IOException
	 */
	public static int leInt(int min, int max) throws IOException {
		
		while(true) {
			try {
				String x = leString();
				int a = Integer.parseInt(x);
				if(a>=min && a<=max) {
					return a;
				} else {
					throw new NumberFormatException("");
				}
				
			} catch(NumberFormatException e) {
				System.out.println("Entrada inválida, digite novamente: ");
			}
		}
		
		
	}
	
	

	/**
	 * Le um string do teclado (uma linha toda) e tenta transformá-lo num double. 
	 * Verifica se o dado digitado pode ser convertido para um double,
	 * se não for válida a entrada espera que o usuário digite novamente.
	 * @return  o valor double digitado pelo usuário. 
	 * @throws IOException
	 */

	public static double leDouble() throws IOException {
		
		while(true) {
			try {
				String x = leString();
				return Double.parseDouble(x);
			} catch(NumberFormatException e) {
				System.out.println("Entrada inválida, digite novamente: ");
			}
		}

	}
}