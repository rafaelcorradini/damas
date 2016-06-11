package util;
import java.util.Calendar;
/**
 * Gerador simples de números aleatórios.
 * 
 *
 */
public class Random {
	private long p = 2147483648L;
	private long m = 843314861L;
	private long a = 453816693L;
	private long xi = 1023L; // semente
	
	@Override
	public String toString() {
		return ""+(xi);
	}
	
	/**
	 * Construtor que permite criar o gerador, especificando o valor inicial da semente.
	 * @param k O valor inicial da semente.
	 */
	public Random(int k) {
		xi = k;
	}
	
	/**
	 * Construtor que usa uma semente aleatória, adquerida usando o método Calendar.getTimeInMillis().
	 */
	public Random() {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
		}
		
		xi = Calendar.getInstance().getTimeInMillis() % p;
	}
	
	public void setSemente(int k) {
		xi = k % p;
	}
	
	/**
	 * Retorna um número aleatório no intervalo (0,1[
	 * @return o número gerado.
	 */
	public double getRand() {
		xi = (a + m * xi ) % p;
		double d = xi;
		return d / p;
	}
	
	/**
	 * Retorna um valor inteiro no intervalo (0,max[
	 * @param max O valor limite para a geração do número inteiro.
	 * @return o número gerado.
	 */
	public int getIntRand(int max) {
		double d = max * getRand();
		return (int) d;
	}
	
	/**
	 * Retorna um número aleatório no intervalo (0,1[
	 * @return o número gerado.
	 */
	public int getIntRand() {
		double d = getRand();
		return (int) d;
	}
	
	/**
	 * Retorna um número aleatório no intervalo (min,max[
	 * @return o número gerado.
	 */
	public int getIntRand(int min, int max) throws IllegalArgumentException {
		if(max <= min) {
			throw new IllegalArgumentException("Parâmetros inválidos");
		}
		double d = min + getIntRand(max-min);
		return (int) d;
	}
}
