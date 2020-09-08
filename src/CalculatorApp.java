import java.util.Scanner;

public class CalculatorApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a string (<var_1> <arithmetic_operation> <var_2>): ");

        String inputString = scanner.nextLine();

        Calculator calculator = new Calculator(inputString);
        calculator.printCalculationResult();
    }

}
