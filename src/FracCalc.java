import java.util.Scanner;

public class FracCalc {

	// Operand 1
	public static int whole1 = 1;
	public static int num1 = 1;
	public static int denom1 = 1;
	public static int impNum1 = 1;
	public static int impDenom1 = 1;

	// Operand 2
	public static int whole2 = 1;
	public static int num2 = 1;
	public static int denom2 = 1;
	public static int impNum2 = 1;
	public static int impDenom2 = 1;

	// Operator & Results
	public static String operator = "";
	public static String finalresult = "";
	// result of calling compute method
	public static int resultNum = 1;
	public static int resultDenom = 1;
	// reduced stuff
	public static int finalNum = 1;
	public static int finalDenom = 1;
	public static int finalWhole = 1;

	// boolean to check if improper fractions are being used
	public static boolean improper = false;

	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		System.out.print("Enter an equation. Enter 'quit' to end. ");
		String equation = console.nextLine();

		while (!equation.toLowerCase().equals("quit")) {
			reset();
			String results = produceAnswer(equation);
			System.out.println(results);
			reset();
			System.out.print("Enter an equation. Enter 'quit' to end. ");
			equation = console.nextLine();
		}
		System.out.println("Bye!");
	}

	public static void reset() {
		// Operand 1
		whole1 = 1;
		num1 = 1;
		denom1 = 1;
		
		// Operand 2
		whole2 = 1;
		num2 = 1;
		denom2 = 1;
		impNum2 = 1;
		impDenom2 = 1;

		// Operator & Results
		operator = "";
		finalresult = "";
		
		// result of calling compute method
		resultNum = 1;
		resultDenom = 1;
		
		// reduced stuff
		finalNum = 1;
		finalDenom = 1;
		finalWhole = 1;

		// boolean to check if improper fractions are being used
		improper = false;
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
				improper = true;
			}

			if(whole2 != 0 && num2 != 0) {
				impNum2 = num(mixedToImp(whole2, num2, denom2));
				impDenom2 = denom(mixedToImp(whole2, num2, denom2));
				improper = true;
			}

			// if whole number, change to fraction
			if (num1 == 0) {
				denom1 = denom2;
				num1 = denom2 * whole1;
			}

			if (num2 == 0) {
				denom2 = denom1;
				num2 = denom1 * whole2;
			}

			// Compute
			// + or - returns numerators, denominators computed within method but not returned (put into field newDenom)
			if (operator.equals("+") || operator.equals("-")) {
				if (improper) {
					resultNum = compute(impNum1, impNum2, operator);
				} else {
					resultNum = compute(num1, num2, operator);
				}
				// * or / returns numerator and denominator
			} else if(operator.equals("*")) {
				if (improper) {
					resultNum = compute(impNum1, impNum2, operator);
					resultDenom = compute(impDenom1, impDenom2, operator);
				} else {
					resultNum = compute(num1, num2, operator);
					resultDenom = compute(denom1, denom2, operator);
				}
			} else if (operator.equals("/")) {
				if (improper) {
					resultNum = compute(impNum1, impDenom2, operator);
					resultDenom = compute(impNum2, impDenom1, operator);
				} else {
					resultNum = compute(num1, denom2, operator);
					resultDenom = compute(num2, denom1, operator);
				}
			}

			// reduce the fractions
			int gcd = greatestCommonDivisor(resultNum, resultDenom);
			finalNum = resultNum / gcd;
			finalDenom = resultDenom / gcd;
			// whole number for mixed number
			if (Math.abs(finalNum) > finalDenom) {
				finalWhole = finalNum / finalDenom;
				finalNum %= finalDenom;
				if (finalNum == 0) {
					finalresult = finalWhole + "";
				} else {
					finalresult = finalWhole + "_" + finalNum + "/" + finalDenom;
				}
			} else if(finalNum == 0) {
				finalresult = "0";
			} else {
				finalresult = finalNum + "/" + finalDenom;
			}
		}
		return finalresult;
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
	// add/subtract: enter numerators for a and b (because all numbers will be converted to fractions)
	// multiply: enter both num and denom for a and b (call method twice w/ corresponding num and denom)
	// divide: enter a as num1 and b as denom2 and vice versa (because for division you must multiply reciprocal of operand2)
	// parameter sign = operator
	public static int compute(int a, int b, String sign) {
		int answer = 0;

		// ADDITION
		if(sign.equals("+")) {
			if (improper) {
				// multiply impNum by impDenom then add
				answer = (a * impDenom2) + (b * impDenom1);

				// don't add denominators, just change to new denominator
				resultDenom = impDenom2 * impDenom1;

			} else {
				// multiply numerator by denominator then add
				answer = (a * denom2) + (b * denom1);

				// don't add denominators, just change to new denominator
				resultDenom = denom2 * denom1;
			}

			//SUBTRACTION
		} else if(sign.equals("-")) {

			if (improper) {
				// multiply impNum by impDenom then subtract
				answer = (a * impDenom2) - (b * impDenom1);

				// don't subtract denominators, just change to new denominator
				resultDenom = impDenom2 * impDenom1;
			} else {
				// multiply numerator by denominator then subtract
				answer = (a * denom2) - (b * denom1);

				// don't subtract denominators, just change to new denominator
				resultDenom = denom2 * denom1;
			}

			// MULTIPLICATION & DIVISION (should enter different parameters, read instructions for method)
		} else {
			answer = a * b;
		}

		return answer;
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