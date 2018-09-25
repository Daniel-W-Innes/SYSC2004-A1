
import java.util.StringTokenizer;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Schramm
 */
public class DiceTest {

	public DiceTest() {
	}

	@Test
	public void testDefaultConstructor() {
		Dice dice = new Dice();
		int nums[] = dice.getDieValues();
		assertEquals(2, nums.length);
	}

	@Test
	public void testOneArgConstructor() {
		Dice dice = new Dice(4);
		int nums[] = dice.getDieValues();
		assertEquals(4, nums.length);
	}

	@Test
	public void testBadOneArgConstructor() {
		try {
			@SuppressWarnings("unused")
			Dice dice = new Dice(0);
			fail(); // You did not throw an exception!
		} catch (IllegalArgumentException e) {
		}
		;
	}

	@Test
	/*
	 * Won't test randomness in this method. Will just test that numbers are between
	 * 1 and 6
	 */
	public void testRoll() {
		Dice dice = new Dice();
		@SuppressWarnings("unused")
		int total;
		for (int i = 0; i < 2000; i++) { // Arbitrary sample size because 1st roll might be okay by fluke
			total = dice.roll();
			int nums[] = dice.getDieValues();
			assertTrue(nums[0] >= 1 && nums[0] <= 6);
			assertTrue(nums[0] >= 1 && nums[0] <= 6);
		}
	}

	@Test
	public void testToString() {
		Dice dice = new Dice(); // Using default of 2
		String s = dice.toString();
		StringTokenizer tokens = new StringTokenizer(s, " ");
		assertEquals(2, tokens.countTokens());
		try {
			int first = Integer.parseUnsignedInt(tokens.nextToken());
			assertTrue(first >= 1 && first <= 6);
			int second = Integer.parseUnsignedInt(tokens.nextToken());
			assertTrue(second >= 1 && second <= 6);
		} catch (Exception e) {
			fail();
		}

	}

	/**
	 * Test of getDieValues method, of class Dice.
	 */
	@Test
	public void testGetDieValues() {
		Dice dice = new Dice();
		int[] before = dice.getDieValues();
		int defensiveCopy[] = new int[2];
		defensiveCopy[0] = before[0];
		defensiveCopy[1] = before[1];
		before[0] = -1;
		before[1] = 55;

		int[] after = dice.getDieValues();
		assertArrayEquals(defensiveCopy, after);
	}

	/**
	 * Test of hasDoubles method, of class Dice.
	 */
	@Test
	public void testHasDoubles() {
		Dice instance = new Dice();
		int dice[] = instance.getDieValues();
		while (dice[0] != dice[1]) {
			instance.roll();
			dice = instance.getDieValues();
		}
		System.out.println(instance);
		assertTrue(instance.hasDoubles());

		// If I was on the job, I would also test for nDice > 2
	}

}
