package test;

import static org.junit.Assert.*;

import org.junit.Test;

import main.Damas;
import main.Jogador;

public class TesteDamas {

	@Test
	public void testDamas() {
		Damas d = new Damas();
	}

	@Test
	public void testDamasInt() {
		Damas d = new Damas(10);
	}

	@Test
	public void testInitTabuleiro() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTamanho() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTabuleiro() {
		fail("Not yet implemented");
	}

	@Test
	public void testFazerMovimento() {
		Damas d = new Damas();
		Jogador j = new Jogador("rafael", 1);
		int[][] t = d.getTabuleiro();
		
		assertEquals(0, d.fazerMovimento(2, 1, 3, 0, j));
		t[2][1] = 0;
		t[3][0] = 1;
		d.confirmarJogada();
		System.out.println(d);
		
		assertArrayEquals(t, d.getTabuleiro());
	}

	@Test
	public void testMelhorJogada() {
		fail("Not yet implemented");
	}

}
