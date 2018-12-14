import java.util.*;

public class FracCalc {

	public static void main(String[] args) {
		Scanner console = new Scanner(System.in); 
		
		// to check if you should keep accepting equations
		boolean running = true;

		// to receive input
		while (running) {
			System.out.println("Enter a fraction equation. Please enter an equation with spaces. Enter 'quit' to end. ");
			String input = console.nextLine();
			
			// Sentinel value = "quit"
			if (input.toLowerCase().equals("quit")) {
				running = false;

			} else {
				
				// prints answer
				System.out.println(produceAnswer(input));

			}
		} 

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
		
		// splits input into 1st operand, 2nd operand, and operator
		String[] theInput = input.split(" ");
		String first = theInput[0];
		String op = theInput[1];
		String second = theInput[2];

		// calls improper method to make the first and second operands improper fraction
		String firstImproper = toImproper(first);
		String secondImproper = toImproper(second);

		String fullMixed = "";

		// ADD
		if (op.equals("+")) {
			fullMixed += ((getNum(firstImproper) * getDen(secondImproper)) + (getNum(secondImproper) * getDen(firstImproper))) +  "/" + (getDen(firstImproper) * getDen(secondImproper));

		}
		
		//SUBTRACT
		else if (op.equals("-")) {
			fullMixed += ((getNum(firstImproper) * getDen(secondImproper)) - (getNum(secondImproper) * getDen(firstImproper))) +  "/" + (getDen(firstImproper) * getDen(secondImproper));

		}
		
		// MULTIPLY
		else if (op.equals("*")) {
			fullMixed += (getNum(firstImproper) * getNum(secondImproper)) + "/" + (getDen(firstImproper) * getDen(secondImproper));

		}
		
		// DIVIDE
		else if (op.equals("/")) {
			fullMixed += (getNum(firstImproper) * getDen(secondImproper)) + "/" + (getDen(firstImproper) * getNum(secondImproper));

		}
		
		// return answer after changing to mixed number
		return toMixed(fullMixed); 
	}

	// TODO: Fill in the space below with helper methods
	
	// method to convert improper fraction to most reduced and mixed form
	public static String toMixed(String improper) {
		
		// gets numerator and denominator and looks for GCD
		int num = getNum(improper);
		int den = getDen(improper);
		int divisor = greatestCommonDivisor(num, den);
		
		// checks if number is negative
		boolean isNegative = false;
		if (num < 0) {
			isNegative = true;
			num *= -1;
		}
		if (den < 0) {
			if (isNegative) {
				isNegative = false;
				den *= -1;
			} else {
				isNegative = true;
				den *= -1;
			}         
		}

		// reduces the numerator and denominator
		num = num/divisor;
		den = den/divisor;

		if (num == 0) {
			return "0";
		}
		if (den == 1) {
			if (isNegative) {
				return "-" + num + "";

			} else {
				return num + "";
			}

		}
				
		// looks for how many times the denominator goes into the numerator
		int counter = 0;
		boolean running = true;

		if (num > 0) {    
			counter = num / den;
		}        

		// looks for remainder
		int remainder = num % den;

		if (counter == 0) {
			
			if (isNegative) {
				return "-" + num + "/" + den;
			} else {
				return num + "/" + den;
			}

		} else {
			if (remainder == 0) {
				if (isNegative) {
					return "-" + counter + "";
				} else {
					return counter + "";
				}
			} 
			else if (den == 1) {
				if (isNegative) {
					return "-" + counter + "_" + remainder;
				} else {
					return counter + "_" + remainder;
				}

			} else {
				if (isNegative) {
					return "-" + counter + "_" + remainder + "/" + den;
				} else {
					return counter + "_" + remainder + "/" + den;
				}

			}
		}

	}
	
	// changes fraction to an improper fraction
	public static String toImproper(String input) {
		String whole = "";
		String num = "";
		String den = "";

		if (input.contains("_")) {
			String[] number = input.split("_");
			whole = number[0];
			num = number[1];

		} else {
			whole = "0";
			num = input;

		}

		if (num.contains("/")) {
			String[] number = num.split("/");
			den = number[1];
			num = number[0];

		} else {
			den = "1";

		}
		
		// changes everything to an int
		int wholeNum = Integer.parseInt(whole);
		int numNum = Integer.parseInt(num);
		int denNum = Integer.parseInt(den);

		boolean isNegative = false;

		// checks if number is negative
		if (wholeNum < 0 || numNum < 0 || denNum <0) {
			isNegative = true;

		}
		
		// changes numerator to positive because need that to do operations
		int newNum = ((Math.abs(wholeNum) * Math.abs(denNum)) + Math.abs(numNum));

		String retString = newNum + "/" + den;

		// changes back to negative if was originally negative to return into produceAnswer method
		if (isNegative) {
			return "-" + retString;
		} else {
			return retString;

		}

	}   

	// find numerator
	public static int getNum(String input) {

		String[] split = input.split("/");
		return Integer.parseInt(split[0]);

	}

	// find denominator
	public static int getDen(String input) {

		String[] split = input.split("/");
		return Integer.parseInt(split[1]);

	}   

	/**
	 * greatestCommonDivisor - Find the largest integer that evenly divides two integers.
	 *      Use this helper method in the Final Checkpoint to reduce fractions.
	 *      Note: There is a different (recursive) implementation in BJP Chapter 12.
	 * @param a - First integer.
	 * @param b - Second integer.
	 * @return The GCD.
	 */

	public static int greatestCommonDivisor(int a, int b) {
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

	public static int leastCommonMultiple(int a, int b) {
		int gcd = greatestCommonDivisor(a, b);
		return (a*b)/gcd;
	}

}
