
/**
 * @author Daniel Innes 101067175
 *
 */
public class DiceClient {
	/**
	 * The main client for TicTacToe.
	 */
	public static void main(String[] args) {
		final int numRuns = 4000;
		int[] output = new int[numRuns + 1];
		int[] temp = new int[1];
		Dice dice = new Dice();
		for (int i = 1; i <= numRuns; i++) {
			if (i % 2 != 0) {
				dice.roll();
				temp = dice.getDieValues();
				output[i] = temp[0];
			} else
				output[i] = temp[1];
		}
		System.out.println("The average roll was " + mean(output));
		System.out.println("The standard deviation of the rolls was " + std_dev(output));
		System.out.println(genHist(output, 6, 1));
	}
	
	/**
	 * Computes the standard deviation of the input.
	 * @param input 
	 * @return
	 */
	private static double std_dev(int input[]) {
		if (input.length == 0)
			return 0.0;
		double sum = 0;
		double sq_sum = 0;
		for (int i = 0; i < input.length; ++i) {
			sum += input[i];
			sq_sum += input[i] * input[i];
		}
		double mean = sum / input.length;
		double variance = sq_sum / input.length - mean * mean;
		return Math.sqrt(variance);
	}

	private static double mean(int[] input) {
		if (input.length == 0)
			return 0.0;
		double sum = 0;
		for (int i = 0; i < input.length; ++i) {
			sum += input[i];
		}
		return sum / input.length;
	}

	private static String genHist(int[] input, int max, int min) {
		int[] freq = new int[max + 1];
		for (int i = 0; i < input.length; ++i) {
			freq[input[i]]++;
		}
		String output = "The histogram of the rolls is: \n";
		for (int i = min; i <= max; ++i) {
			output += i + " (" + freq[i] + ")  :";
			for (int j = 0; j <= (int) freq[i] / 10; ++j) {
				output += "*";
			}
			output += "\n";
		}
		return output;
	}
}
