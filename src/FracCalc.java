import java.util.Scanner;

public class FracCalc {

	// Operand 1
	public static int whole1 = 0;
	public static int num1 = 0;
	public static int denom1 = 0;
	public static int impNum1 = 0;
	public static int impDenom1 = 0;

	// Operand 2
	public static int whole2 = 0;
	public static int num2 = 0;
	public static int denom2 = 0;
	public static int impNum2 = 0;
	public static int impDenom2 = 0;

	// Operator
	public static String operator = "";
	public static String finalresult = "";

	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		System.out.print("Enter an equation. Enter 'quit' to end. ");
		String equation = console.nextLine();

		while (!equation.toLowerCase().equals("quit")) {
			String results = produceAnswer(equation);
			System.out.println(results);
			System.out.print("Enter an equation. Enter 'quit' to end. ");
			equation = console.nextLine();
		}

		System.out.println("Bye!");
	}

	// TODO: Implement this function to produce the solution to the input
	// Checkpoint 1: Return the second operand.  Example "4/5 * 1_2/4" returns "1_2/4".
	// Checkpoint 2: Return the second operand as a string representing each part.
	//               Example "4/5 * 1_2/4" returns "whole:1 numerator:2 denominator:4".
	// Checkpoint 3: Evaluate the formula and return the result as a fraction.
	//               Example "4/5 * 1_2/4" returns "6/5".
	//               Note: Answer does not need to be reduced, but it must be correct.
	// Final project: All answers must be reduced.
	//               Example "4/5 * 1_2/4" returns "1_1/5".

	public static String produceAnswer(String input) {

		// check that the input is in the right format
		if (input.length() < 3) {
			return "ERROR: input in wrong format.";
		} else {			
			// Split input into operand 1, operand 2, and the operator
			String[] holder = input.split(" ");
			String operand1 = holder[0];
			String operation = holder[1];
			String operand2 = holder[2];

			// Use whole, num, and denom methods to convert operand1 and operand2
			whole1 = whole(operand1);
			num1 = num(operand1);
			denom1 = denom(operand1);

			whole2 = whole(operand2);
			num2 = num(operand2);
			denom2 = denom(operand2);

			// Set operator field to operation found
			operator = operation;

			// if mixed number, change to improper fraction
			if(whole1 != 0 && num1 != 0) {
				impNum1 = num(mixedToImp(whole1, num1, denom1));
				impDenom1 = denom(mixedToImp(whole1, num1, denom1));
			}

			if(whole2 != 0 && num2 != 0) {
				impNum2 = num(mixedToImp(whole2, num2, denom2));
				impDenom2 = denom(mixedToImp(whole2, num2, denom2));
			}

			// Compute using add, subtract, multiply, and divide methods



		}
	}

	// TODO: Fill in the space below with helper methods

	// find the whole number
	public static int whole(String a) {

		// mixed num
		if(a.contains("_")) {
			return Integer.parseInt(a.substring(0, a.indexOf('_')));

			// just fraction
		} else if (a.contains("/")) {
			return Integer.parseInt("0");

			// just whole
		} else {
			return Integer.parseInt(a);
		}
	}

	// find the numerator
	public static int num(String a) {

		// mixed num
		if(a.contains("_")) {
			return Integer.parseInt(a.substring(a.indexOf('_') + 1, a.indexOf('/')));

			// just fraction
		} else if (a.contains("/")) {
			return Integer.parseInt(a.substring(0, a.indexOf('/')));

			// just whole
		} else {
			return Integer.parseInt("0");
		}
	}

	// find the denominator
	public static int denom(String a) {

		// fraction or mixed num
		if (a.contains("/")) {
			return Integer.parseInt(a.substring(a.indexOf('/') + 1));

			// just whole
		} else {
			return Integer.parseInt("1");
		}
	}

	// add, subtract, divide, and multiply
	public static int compute(int a, int b, String sign) {
		int answer = 0;

		// ADDITION
		if(sign == "+") {

			// whole number
			answer = a + b;

			// numerator or denominator
			answer = (a * denom2) + (b * denom1);

		//SUBTRACTION
		} else if(sign == "-") {

			// whole number
			answer = a - b;

			// numerator or denominator
			answer = (a * denom2) - (b * denom1);

		// MULTIPLICATION			
		} else if(sign == "*") {




		}  	
	}

	public static String mixedToImp(int w, int n, int d) {
		int new_n = (d * w) + n;
		String imp = new_n + "/" + d;
		return imp;    	
	}

	public static String impToMixed(int n, int d) {
		int w = n % d;
		int new_n = (n - (w * d));
		String mixed = w + "_" + new_n + "/" + d;
		return mixed;
	}

	/**
	 * greatestCommonDivisor - Find the largest integer that evenly divides two integers.
	 *      Use this helper method in the Final Checkpoint to reduce fractions.
	 *      Note: There is a different (recursive) implementation in BJP Chapter 12.
	 * @param a - First integer.
	 * @param b - Second integer.
	 * @return The GCD.
	 */

	public static int greatestCommonDivisor(int a, int b)
	{
		a = Math.abs(a);
		b = Math.abs(b);
		int max = Math.max(a, b);
		int min = Math.min(a, b);
		while (min != 0) {
			int tmp = min;
			min = max % min;
			max = tmp;
		}
		return max;
	}

	/**
	 * leastCommonMultiple - Find the smallest integer that can be evenly divided by two integers.
	 *      Use this helper method in Checkpoint 3 to evaluate expressions.
	 * @param a - First integer.
	 * @param b - Second integer.
	 * @return The LCM.
	 */

	public static int leastCommonMultiple(int a, int b)
	{
		int gcd = greatestCommonDivisor(a, b);
		return (a*b)/gcd;
	}
}


