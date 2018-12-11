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
		}
	}

	public static int convert(String input) {
		String[] holder = input.split(" ");
		String operand1 = holder[0];
		String operation = holder[1];
		String operand2 = holder[2];
		
        // TODO: Implement this function to produce the solution to the input
        // Checkpoint 1: Return the second operand.  Example "4/5 * 1_2/4" returns "1_2/4".
        // Checkpoint 2: Return the second operand as a string representing each part.
        //               Example "4/5 * 1_2/4" returns "whole:1 numerator:2 denominator:4".
        // Checkpoint 3: Evaluate the formula and return the result as a fraction.
        //               Example "4/5 * 1_2/4" returns "6/5".
        //               Note: Answer does not need to be reduced, but it must be correct.
        // Final project: All answers must be reduced.
        //               Example "4/5 * 1_2/4" returns "1_1/5".
        
		String whole2 = whole(operand2);
		String num2 = num(operand2);
		String denom2 = denom(operand2);
		

    }

// TODO: Fill in the space below with helper methods
    
    // find the whole number
    public static String whole(String a) {
    	
    	// mixed num
    	if(a.contains("_")) {
    		return a.substring(0, a.indexOf('_'));
		
		// just fraction
    	} else if (a.contains("/")) {
    		return "0";
		
		// just whole
    	} else {
    		return a;
    	}
    }
    
    // find the numerator
    public static String num(String a) {
    	
    	// mixed num
    	if(a.contains("_")) {
    		return a.substring(a.indexOf('_') + 1, a.indexOf('/'));
    		
		// just fraction
    	} else if (a.contains("/")) {
    		return a.substring(0, a.indexOf('/')) ;
		
		// just whole
    	} else {
    		return "0";
    	}
    }
    
    // find the denominator
    public static String denom(String a) {

		// fraction or mixed num
    	if (a.contains("/")) {
    		return a.substring(a.indexOf('/') + 1) ;
		
		// just whole
    	} else {
    		return "1";
    	}
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

	
