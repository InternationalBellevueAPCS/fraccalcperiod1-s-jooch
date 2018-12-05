import java.util.*;
public class Calc {

	public static void main(String [] args) {
		Scanner console = new Scanner(System.in);
		String num = console.nextLine();
		System.out.println(produceAnswer(num));
	}

	public static String produceAnswer(String input) {
		String[] holder = input.split(" ");
		String operand1 = holder[0];
		String operation = holder[1];
		String operand2 = holder[2];
		return operand2;
	}
}