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
	public void testFazerMovimento() throws Exception {
		Damas d = new Damas();
		Jogador j = new Jogador("rafael", 1);
		int[][] t = d.getTabuleiro();
		
		assertEquals(0, d.fazerMovimento(2, 1, 3, 0, j));
		t[2][1] = 0;
		t[3][0] = 1;
		d.confirmarJogada();
		assertArrayEquals(t, d.getTabuleiro());
		
		j.setCor(2);
		assertEquals(0, d.fazerMovimento(5, 2, 4, 1, j));
		d.confirmarJogada();
		System.out.println(d);
		j.setCor(1);
		assertEquals(1, d.fazerMovimento(3, 0, 5, 2, j));
		t[3][0] = 0;
		t[5][2] = 1;
		d.confirmarJogada();
		assertArrayEquals(t, d.getTabuleiro());
		System.out.println(d);
		j.setCor(2);
		assertEquals(1, d.fazerMovimento(6, 3, 4, 1, j));
		t[6][3] = 0;
		t[4][1] = 2;
		t[5][2] = 0;
		d.confirmarJogada();
		assertArrayEquals(t, d.getTabuleiro());
		System.out.println(d);
		
		j.setCor(1);
		assertEquals(0, d.fazerMovimento(2, 3, 3, 4, j));
		assertEquals(0, d.fazerMovimento(1, 4, 2, 3, j));
		assertEquals(0, d.fazerMovimento(2, 3, 3, 2, j));
		assertEquals(0, d.fazerMovimento(0, 5, 1, 4, j));
		d.confirmarJogada();
		System.out.println(d);
		
		j.setCor(2);
		assertEquals(2, d.melhorJogada(j));
		assertEquals(1, d.fazerMovimento(4, 1, 2, 3, j));
		assertEquals(1, d.fazerMovimento(2, 3, 0, 5, j));
		d.confirmarJogada();
		System.out.println(d);
		assertEquals(0, d.fazerMovimento(0, 5, 2, 3, j));
		d.confirmarJogada();
		System.out.println(d);
		assertEquals(1, d.melhorJogada(j));
		assertEquals(1, d.fazerMovimento(2, 3, 4, 5, j));
		d.confirmarJogada();
		System.out.println(d);
		
	}


}
