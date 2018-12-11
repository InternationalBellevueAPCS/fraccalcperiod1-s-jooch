

import java.util.Scanner;

//Algorithm
// First Rule! Set all Fractions to Improper Fractions to make operations easier
// produceAnswer = parses operands
// parseFractions = parses operands string then sets static class fields
// Each Operation Function or Method performs proper calculation then calls SetResult method
// SetResult = converts the result to the proper string output. This method also calls GCD and ConvertFromImproperToMixed methods
// findGcd = used to simplify improper fractions
// turnImproperFractionToMixedFraction = convert improper fraction (as appropriate) to final results

public class FracCalc {

	// Operand 1
	public static int operand1Wholenumber = 0;
	public static int operand1Numerator = 0;
	public static int operand1Denominator = 0;
	public static int operand1ImproperNumerator = 0;

	// Operand 2
	public static int operand2Wholenumber = 0;
	public static int operand2Numerator = 0;
	public static int operand2Denominator = 0;
	public static int operand2ImproperNumerator = 0;

	// Operator
	public static String operator = "";
	public static String finalresult = "";

	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		System.out.print("Please enter your equation (quit to quit): ");
		String equation = console.nextLine();

		while (!equation.toLowerCase().equals("quit")) {
			String results = produceAnswer(equation);
			System.out.println(results);
			System.out.print("Please enter your equation (quit to quit): ");
			equation = console.nextLine();
		}
		console.close();
		System.out.print("Fraction Calculator is Closed!");
	}

	public static String produceAnswer(String input) {
		String[] arrayinput = input.split(" ");
		if (arrayinput.length < 3) {
			return "ERROR: Input is in an invalid format.";
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
		if (FracCalc.operand1Denominator == 0 || FracCalc.operand2Denominator == 0) {
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
				FracCalc.finalresult = "ERROR: Input is in an invalid format.";
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
			FracCalc.operand1Wholenumber = wholenumber;
			FracCalc.operand1Numerator = numerator;
			FracCalc.operand1Denominator = denominator;
			if (wholenumber != 0) {
				// Check if it's negative then do absolute value and add the negative value back
				if (Integer.toString(wholenumber).contains("-")) {
					wholenumber = Integer.parseInt(Integer.toString(wholenumber).split("-")[1]);
					// Convert back to negative
					FracCalc.operand1ImproperNumerator = ((wholenumber * denominator) + numerator) * (-1);
				} else {
					FracCalc.operand1ImproperNumerator = (wholenumber * denominator) + numerator;
				}
			} else {
				FracCalc.operand1ImproperNumerator = numerator;
			}

		} else {
			FracCalc.operand2Wholenumber = wholenumber;
			FracCalc.operand2Numerator = numerator;
			FracCalc.operand2Denominator = denominator;
			if (wholenumber != 0) {
				// Check if it's negative then do absolute value and add the negative value back
				if (Integer.toString(wholenumber).contains("-")) {
					wholenumber = Integer.parseInt(Integer.toString(wholenumber).split("-")[1]);
					// Convert back to negative
					FracCalc.operand2ImproperNumerator = ((wholenumber * denominator) + numerator) * (-1);
				} else {
					FracCalc.operand2ImproperNumerator = (wholenumber * denominator) + numerator;
				}
			} else {
				FracCalc.operand2ImproperNumerator = numerator;
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
		int tempnumerator = 0;
		int commondenominator = 0;
		if (FracCalc.operand1Denominator == FracCalc.operand2Denominator) {
			// Call Calculate Numerator based on operator
			tempnumerator = FracCalc.operand1ImproperNumerator + FracCalc.operand2ImproperNumerator;
			// Since the denominator are the same let's just use Operand1 denom
			SetResult(tempnumerator, FracCalc.operand1Denominator);
		} else {
			commondenominator = FracCalc.operand1Denominator * FracCalc.operand2Denominator;
			FracCalc.operand1ImproperNumerator = FracCalc.operand1ImproperNumerator
					* (commondenominator / FracCalc.operand1Denominator);
			FracCalc.operand2ImproperNumerator = FracCalc.operand2ImproperNumerator
					* (commondenominator / FracCalc.operand2Denominator);
			tempnumerator = FracCalc.operand1ImproperNumerator + FracCalc.operand2ImproperNumerator;
			SetResult(tempnumerator, commondenominator);
		}
		return FracCalc.finalresult;
	}

	public static String Multiply() {
		int tempnumerator = 0;
		int tempdenominator = 0;
		tempnumerator = FracCalc.operand1ImproperNumerator * FracCalc.operand2ImproperNumerator;
		tempdenominator = FracCalc.operand1Denominator * FracCalc.operand2Denominator;
		SetResult(tempnumerator, tempdenominator);
		return FracCalc.finalresult;
	}

	public static String Subract() {
		int tempnumerator = 0;
		int commondenominator = 0;
		if (FracCalc.operand1Denominator == FracCalc.operand2Denominator) {
			// Call Calculate Numerator based on operator
			tempnumerator = FracCalc.operand1ImproperNumerator - FracCalc.operand2ImproperNumerator;
			// Since the denominator are the same let's just use Operand1 denom
			SetResult(tempnumerator, FracCalc.operand1Denominator);
		} else {
			commondenominator = FracCalc.operand1Denominator * FracCalc.operand2Denominator;
			FracCalc.operand1ImproperNumerator = FracCalc.operand1ImproperNumerator
					* (commondenominator / FracCalc.operand1Denominator);
			FracCalc.operand2ImproperNumerator = FracCalc.operand2ImproperNumerator
					* (commondenominator / FracCalc.operand2Denominator);
			tempnumerator = FracCalc.operand1ImproperNumerator - FracCalc.operand2ImproperNumerator;
			SetResult(tempnumerator, commondenominator);
		}
		return FracCalc.finalresult;
	}

	public static String Divide() {
		int tempnumerator = 0;
		int tempdenominator = 0;
		// Invert reciprocal of Operand 2
		// Again Check to see if the remainder is negative
		if (FracCalc.operand2ImproperNumerator < 0) {
			FracCalc.operand2ImproperNumerator = Integer
					.parseInt(Integer.toString(FracCalc.operand2ImproperNumerator).split("-")[1]);
			int tempvalue = FracCalc.operand2Denominator;
			FracCalc.operand2Denominator = FracCalc.operand2ImproperNumerator;
			FracCalc.operand2ImproperNumerator = tempvalue;
			tempnumerator = (FracCalc.operand1ImproperNumerator * -1) * FracCalc.operand2ImproperNumerator;
			tempdenominator = FracCalc.operand1Denominator * FracCalc.operand2Denominator;
		} else {
			int tempvalue = FracCalc.operand2Denominator;
			FracCalc.operand2Denominator = FracCalc.operand2ImproperNumerator;
			FracCalc.operand2ImproperNumerator = tempvalue;
			tempnumerator = FracCalc.operand1ImproperNumerator * FracCalc.operand2ImproperNumerator;
			tempdenominator = FracCalc.operand1Denominator * FracCalc.operand2Denominator;
		}
		SetResult(tempnumerator, tempdenominator);
		return FracCalc.finalresult;
	}
}