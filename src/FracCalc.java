import java.util.Scanner;

public class FracCalc {

	// Operand 1
	public static int operand1Whole = 0;
	public static int operand1Num = 0;
	public static int operand1Denom = 0;
	public static int operand1ImpNum = 0;

	// Operand 2
	public static int operand2Whole = 0;
	public static int operand2Num = 0;
	public static int operand2Denom = 0;
	public static int operand2ImpNum = 0;

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

	public static String produceAnswer(String input) {
		String[] arrayinput = input.split(" ");
		if (arrayinput.length < 3) {
			return "ERROR: Input in wrong format.";
		} else {
			CalculateEpxression(arrayinput[1], arrayinput[0], arrayinput[2]);
			for (int i = 3; i < arrayinput.length; i += 2) {
				CalculateEpxression(arrayinput[i], FracCalc.finalresult, arrayinput[i + 1]);
			}
			return FracCalc.finalresult;
		}
	}

	public static void CalculateEpxression(String operator, String operand1, String operand2) {
		FracCalc.parseFraction(operand1, true);
		FracCalc.parseFraction(operand2, false);
		FracCalc.operator = operator;
		if (FracCalc.operand1Denom == 0 || FracCalc.operand2Denom == 0) {
			FracCalc.finalresult = "ERROR: Cannot divide by zero.";
		} else {
			if (operator.equalsIgnoreCase("+")) {
				FracCalc.Add();
			} else if (operator.equalsIgnoreCase("*")) {
				FracCalc.Multiply();
			} else if (operator.equalsIgnoreCase("-")) {
				FracCalc.Subract();
			} else if (operator.equalsIgnoreCase("/")) {
				FracCalc.Divide();
			} else if (operator.length() > 1) {
				FracCalc.finalresult = "ERROR: Input in wrong format.";
			}
		}
	}

	public static void parseFraction(String operand, Boolean operand1) {
		String fraction = operand;
		String wholenumber = "0";
		String numerator = "0";
		String denominator = "0";
		String[] splitwholenumber = fraction.split("_");
		if (splitwholenumber.length == 2) {
			// Mixed Fraction Condition
			wholenumber = fraction.split("_")[0];
			numerator = fraction.split("_")[1].split("/")[0];
			denominator = fraction.split("_")[1].split("/")[1];
		} else {
			// Check if it's whole number only
			String[] splitnumeratoranddenominator = fraction.split("/");
			// If there are 2 values in array, this means it's a fraction else it's only a
			// whole number
			if (splitnumeratoranddenominator.length == 2) {
				numerator = splitnumeratoranddenominator[0];
				denominator = splitnumeratoranddenominator[1];
			} else {
				wholenumber = operand;
				denominator = "1";
			}
		}
		// Set Static Variables
		SetStaticFieldValues(operand1, Integer.parseInt(wholenumber), Integer.parseInt(numerator),
				Integer.parseInt(denominator));
	}

	public static void SetStaticFieldValues(Boolean operand1, int wholenumber, int numerator, int denominator) {
		if (operand1) {
			FracCalc.operand1Whole = wholenumber;
			FracCalc.operand1Num = numerator;
			FracCalc.operand1Denom = denominator;
			if (wholenumber != 0) {
				// Check if it's negative then do absolute value and add the negative value back
				if (Integer.toString(wholenumber).contains("-")) {
					wholenumber = Integer.parseInt(Integer.toString(wholenumber).split("-")[1]);
					// Convert back to negative
					FracCalc.operand1ImpNum = ((wholenumber * denominator) + numerator) * (-1);
				} else {
					FracCalc.operand1ImpNum = (wholenumber * denominator) + numerator;
				}
			} else {
				FracCalc.operand1ImpNum = numerator;
			}

		} else {
			FracCalc.operand2Whole = wholenumber;
			FracCalc.operand2Num = numerator;
			FracCalc.operand2Denom = denominator;
			if (wholenumber != 0) {
				// Check if it's negative then do absolute value and add the negative value back
				if (Integer.toString(wholenumber).contains("-")) {
					wholenumber = Integer.parseInt(Integer.toString(wholenumber).split("-")[1]);
					// Convert back to negative
					FracCalc.operand2ImpNum = ((wholenumber * denominator) + numerator) * (-1);
				} else {
					FracCalc.operand2ImpNum = (wholenumber * denominator) + numerator;
				}
			} else {
				FracCalc.operand2ImpNum = numerator;
			}
		}
	}

	public static void SetResult(int numerator, int denominator) {
		if (numerator % denominator == 0) {
			FracCalc.finalresult = Integer.toString(numerator / denominator);
		} else {
			// Simplify Fractions
			int gcd = FracCalc.findGcd(numerator, denominator);
			// Remove negative number on gcd (Get Absolute value)
			if (Integer.toString(gcd).contains("-")) {
				gcd = Integer.parseInt(Integer.toString(gcd).split("-")[1]);
			}
			int simplifiednumerator = numerator / gcd;
			int simplifieddenominator = denominator / gcd;
			// Convert back to Mixed Form
			FracCalc.finalresult = turnImproperFractionToMixedFraction(simplifiednumerator, simplifieddenominator);
		}
	}

	public static int findGcd(int number1, int number2) {
		if (number2 == 0) {
			return number1;
		}
		return findGcd(number2, number1 % number2);
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

public static String turnImproperFractionToMixedFraction(int numerator, int denominator) {
	Integer wholenumber = numerator / denominator;
	Integer remainder = numerator % denominator;
	// Check to see if the result is a negative result and remove the "-" char from
	// the fraction and only show it in the whole number
	if (wholenumber < 0)
		// if (Integer.toString(remainder).contains("-"))
	{
		// Again Check to see if the remainder is negative
		if (Integer.toString(remainder).contains("-")) {
			remainder = Integer.parseInt(Integer.toString(remainder).split("-")[1]);
		}
	}
	return wholenumber != 0 ? (wholenumber + "_" + remainder + "/" + denominator) : (remainder + "/" + denominator);
}

// Calculations
public static String Add() {
	int tempnum = 0;
	int commondenom = 0;
	if (FracCalc.operand1Denom == FracCalc.operand2Denom) {
		// Call Calculate Numerator based on operator
		tempnum = FracCalc.operand1ImpNum + FracCalc.operand2ImpNum;
		// Since the denominator are the same let's just use Operand1 denom
		SetResult(tempnum, FracCalc.operand1Denom);
	} else {
		commondenom = FracCalc.operand1Denom * FracCalc.operand2Denom;
		FracCalc.operand1ImpNum = FracCalc.operand1ImpNum
				* (commondenom / FracCalc.operand1Denom);
		FracCalc.operand2ImpNum = FracCalc.operand2ImpNum
				* (commondenom / FracCalc.operand2Denom);
		tempnum = FracCalc.operand1ImpNum + FracCalc.operand2ImpNum;
		SetResult(tempnum, commondenom);
	}
	return FracCalc.finalresult;
}

public static String Multiply() {
	int tempnumerator = 0;
	int tempdenominator = 0;
	tempnumerator = FracCalc.operand1ImpNum * FracCalc.operand2ImpNum;
	tempdenominator = FracCalc.operand1Denom * FracCalc.operand2Denom;
	SetResult(tempnumerator, tempdenominator);
	return FracCalc.finalresult;
}

public static String Subract() {
	int tempnum = 0;
	int commondenom = 0;
	if (FracCalc.operand1Denom == FracCalc.operand2Denom) {
		// Call Calculate Numerator based on operator
		tempnum = FracCalc.operand1ImpNum - FracCalc.operand2ImpNum;
		// Since the denominator are the same let's just use Operand1 denom
		SetResult(tempnum, FracCalc.operand1Denom);
	} else {
		commondenom = FracCalc.operand1Denom * FracCalc.operand2Denom;
		FracCalc.operand1ImpNum = FracCalc.operand1ImpNum
				* (commondenom / FracCalc.operand1Denom);
		FracCalc.operand2ImpNum = FracCalc.operand2ImpNum
				* (commondenom / FracCalc.operand2Denom);
		tempnum = FracCalc.operand1ImpNum - FracCalc.operand2ImpNum;
		SetResult(tempnum, commondenom);
	}
	return FracCalc.finalresult;
}

public static String Divide() {
	int tempnum = 0;
	int tempdenom = 0;
	// Invert reciprocal of Operand 2
	// Again Check to see if the remainder is negative
	if (FracCalc.operand2ImpNum < 0) {
		FracCalc.operand2ImpNum = Integer
				.parseInt(Integer.toString(FracCalc.operand2ImpNum).split("-")[1]);
		int tempvalue = FracCalc.operand2Denom;
		FracCalc.operand2Denom = FracCalc.operand2ImpNum;
		FracCalc.operand2ImpNum = tempvalue;
		tempnum = (FracCalc.operand1ImpNum * -1) * FracCalc.operand2ImpNum;
		tempdenom = FracCalc.operand1Denom * FracCalc.operand2Denom;
	} else {
		int tempvalue = FracCalc.operand2Denom;
		FracCalc.operand2Denom = FracCalc.operand2ImpNum;
		FracCalc.operand2ImpNum = tempvalue;
		tempnum = FracCalc.operand1ImpNum * FracCalc.operand2ImpNum;
		tempdenom = FracCalc.operand1Denom * FracCalc.operand2Denom;
	}
	SetResult(tempnum, tempdenom);
	return FracCalc.finalresult;
}
}