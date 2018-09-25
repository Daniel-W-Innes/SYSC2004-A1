
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author Daniel Innes 101067175
 *
 */
public class Dice {

	private Random random;
	private int[] values;
	private final int defaultNumDice = 2;

	public Dice() {
		random = new Random(new Date().hashCode());
		values = new int[defaultNumDice];
		for (int i = 0; i < defaultNumDice; ++i) {
			values[i] = rollDie();
		}
	}

	public Dice(int numDice) {
		if (numDice < 1)
			throw new IllegalArgumentException("Nice msg");
		random = new Random(new Date().hashCode());
		values = new int[numDice];
		for (int i = 0; i < numDice; ++i) {
			values[i] = rollDie();
		}
	}

	public int roll() {
		int total = 0;
		for (int i = 0; i < values.length; ++i) {
			values[i] = rollDie();
			total += values[i];
		}
		return total;
	}

	private int rollDie() {
		int value = random.nextInt(6) + 1;
		return value;
	}

	public int[] getDieValues() {
		return values.clone();
	}

	public boolean hasDoubles() {
		Set<Integer> set = new HashSet<Integer>();
		for (int value : values) {
			if (set.contains(value))
				return true;
			set.add(value);
		}
		return false;
	}

	public String toString() {
		String output = "";
		for (int value : values) {
			output += String.valueOf(value) + " ";
		}
		output.substring(0, output.length() - 1);
		return output;
	}
}
