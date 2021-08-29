package sample;

import java.util.Scanner;
import java.util.Stack;
import java.text.DecimalFormat;

public class Infix2Postfix {
	// A utility function to return precedence of a given operator
	// Higher returned value means higher precedence

	private static String lastNumString;
	private static Stack<Double> stack = new Stack<>();
	private static DecimalFormat df;

	static int Prec(String ch) {
		if (ch.equals("+") || ch.equals("-"))
			return 1;
		else if (ch.equals("*") || ch.equals("/"))
			return 2;
		else if (ch.equals("^"))
			return 3;
		return -1;
	}

	// The main method that converts given infix expression
	// to postfix expression.
	static String infixToPostfix(String exp) {
		// initializing empty String for result
		String result = new String("");

		// initializing empty stack
		Stack<String> stack = new Stack<>();

		String[] values = exp.split(" ");
		boolean isNumber = false;

		for (String token : values) {

			// If the scanned character is an operand, add it to output.
			try {
				Double.parseDouble(token);
				result += token + " ";
				isNumber = true;
				continue;

			} catch (NumberFormatException nfe) {
				isNumber = false;
			}

			if (!isNumber) {
				if (token.equals("("))
					stack.push(token);
				else if (token.equals(")")) {
					while (!stack.isEmpty() && !stack.peek().equals("(")) {
						result += stack.pop() + " ";
					}
					if (!stack.isEmpty() && !stack.peek().equals("(")) {
						return "Invalid Expression";
					} // invalid expression
					else {
						stack.pop();
					}
				} else { // operator
					while (!stack.isEmpty() && Prec(token) <= Prec(stack.peek()))
						result += stack.pop() + " ";
					stack.push(token);
				}
			}
		}
		// pop all the operators from the stack
		while (!stack.isEmpty())
			result += stack.pop() + " ";

		return result;

	}

	public static String evaluatePostFix(String expression) {
		double tokenNum;
		double lastNum;
		// put tokens into array
		String[] postFixArray = expression.split(" ");

		// for every token in the array, check if number
		for (String token : postFixArray) {
			System.out.print(token + " ");
			try { // if number
				tokenNum = Double.parseDouble(token);
				stack.push(tokenNum);
			} catch (NumberFormatException e) { // if not number (is operand)
				if (stack.size() >= 2) {
					applyOperand(token);
				}
			}
		}

		lastNum = stack.peek();

		// format to string
		df = new DecimalFormat("#.#########");
		df.applyPattern("#,###,###.#########");
		lastNumString = df.format(lastNum);

		return lastNumString;
	}

	public static void applyOperand(String token) {
		double result = Double.NaN;
		switch(token) {
			case "+":
				result = stack.pop() + stack.pop();
				break;
			case "-":
				double number1 = stack.pop();
				double number2 = stack.pop();
				result = number2 - number1;
				break;
			case "*":
				result = stack.pop() * stack.pop();
				break;
			case "/":
				double num1 = stack.pop();
				double num2 = stack.pop();
				result = num2 / num1;
				break;
			default:
		}
		if (result != Double.NaN) {
			stack.push(result);
		}
	}

	// Driver method
	public static void main(String args[]) {

		System.out.println("Type in an expression like ( 1 + 2 ) * ( 3 + 4 ) / ( 12 - 5 )\n");
		Scanner scan = new Scanner(System.in);
		String str = scan.nextLine();
		System.out.println("The Expression you have typed in infix form :\n" + str);
		String postFix = infixToPostfix(str);
		System.out.println("Postfix is: ");
		String ans = evaluatePostFix( postFix );
		System.out.println("\nAnswer is :\n" + ans );
		scan.close();
	}
}