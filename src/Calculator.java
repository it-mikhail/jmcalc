public class Calculator {

    private String expression;
    private String variableX;
    private String variableY;
    private String calculationResult;
    private String expressionResult;
    private String[] operationCharacters;
    private int operationIndex;
    private int operationPosition;
    private String typeOfVariables; // roman or arabic

    public Calculator() {
        this.operationCharacters = new String[] { "+", "-", "/", "*" };
    }

    private void nullVariables() {
        this.variableX = null;
        this.variableY = null;
        this.calculationResult = null;
        this.expressionResult = null;
        this.typeOfVariables = null;
        this.operationIndex = -1;
        this.operationPosition = -1;
    }

    public boolean parseExpression(String inputExpression) {
        boolean result = false;

        this.expression = inputExpression;

        nullVariables();

        int i = 0;
        int operationsCount = 0;
        String splitCharacter = "";

        while (i < this.operationCharacters.length) {
            int opeartionCharCode = (int) this.operationCharacters[i].charAt(0);
            operationsCount += (int) inputExpression.chars().filter(ch -> ch == opeartionCharCode).count();
            if (inputExpression.indexOf(this.operationCharacters[i]) >= 0) {
                operationIndex = i;
                operationPosition = inputExpression.indexOf(operationCharacters[i]);
                if (operationCharacters[i].equals("+") | operationCharacters[i].equals("*")) {
                    splitCharacter = "\\" + operationCharacters[i];
                } else {
                    splitCharacter = operationCharacters[i];
                }
            }
            i++;
        }

        if (operationsCount > 1) {
            throw new CalculatorRuntimeException(
                    "Too many arithmetic operations in string. Use only one operation character.");
        } else {
            if (operationsCount == 0) {
                throw new CalculatorRuntimeException(
                        "There is no arithmetic operator in strings. Use one of these in future (+, -, *, /).");
            } else {
                String variableX = inputExpression.substring(0, operationPosition).trim();
                String variableY = inputExpression.substring(operationPosition + 1).trim();
                if (variableX.length() > 0 && variableY.length() > 0) {
                    this.variableX = variableX;
                    this.variableY = variableY;
                } else {
                    throw new CalculatorRuntimeException("Missed variable. Please check input string.");
                }
            }
        }

        if (this.variableX != null) {
            result = true;
        }

        return result;
    }

    private static boolean variableIsArabic(String inputString) {
        boolean result = false;
        String regex = "\\d+";
        result = inputString.matches(regex);

        return result;
    }

    private static boolean variableIsRoman(String inputString) {
        // check is actual for roman numbers from 1 to 3999
        boolean result = false;
        String regex = "[ivxlcdmIVXLCDM]+";
        result = inputString.matches(regex);

        return result;
    }

    private static boolean romanVariableInInputRange(String inputString) {
        boolean result = false;
        String regex = "[lcdmLCDM]+";
        if (!inputString.matches(regex)) {
            if ('X' == Character.toUpperCase(inputString.charAt(0))) {
                if (inputString.length() == 1) {
                    result = true;
                }
            } else {
                result = true;
            }
        }

        return result;
    }

    public boolean checkVariablesInRange() {
        boolean result = false;

        switch (this.typeOfVariables) {
        case "arabic":
            result = Integer.parseInt(this.variableX) <= 10 && Integer.parseInt(this.variableY) <= 10;
            break;
        case "roman":
            result = romanVariableInInputRange(this.variableX) && romanVariableInInputRange(this.variableY);
            break;
        default:
            throw new CalculatorRuntimeException("Type of variables not roman and not arabic.");
        }

        if (!result) {
            throw new CalculatorRuntimeException("Inputed numbers out of range (1 - 10).");
        }

        return result;
    }

    private boolean calculateResult() {
        boolean result = false;

        switch (this.operationCharacters[this.operationIndex]) {
        case "+":
            switch (this.typeOfVariables) {
            case "arabic":
                this.calculationResult = Integer
                        .toString(Integer.parseInt(this.variableX) + Integer.parseInt(this.variableY));
                break;
            case "roman":
                this.calculationResult = RomanNumeration
                        .toString(RomanNumeration.toInt(this.variableX) + RomanNumeration.toInt(this.variableY));
                break;
            default:
                throw new CalculatorRuntimeException("Use only arabic or roman integer numbers at the same time.");
            }
            break;
        case "-":
            switch (this.typeOfVariables) {
            case "arabic":
                this.calculationResult = Integer
                        .toString(Integer.parseInt(this.variableX) - Integer.parseInt(this.variableY));
                break;
            case "roman":
                int intResult = RomanNumeration.toInt(this.variableX) - RomanNumeration.toInt(this.variableY);
                if (intResult > 0) {
                    this.calculationResult = RomanNumeration.toString(intResult);
                } else {
                    throw new CalculatorRuntimeException(
                            "Result is 0 or negative. Roman numbers can be positive and greater than 0.");
                }
                break;
            default:
                throw new CalculatorRuntimeException("Use only arabic or roman integer numbers at the same time.");
            }
            break;
        case "*":
            System.out.println(" dev.testing :: уможение");
            switch (this.typeOfVariables) {
            case "arabic":
                this.calculationResult = Integer
                        .toString(Integer.parseInt(this.variableX) * Integer.parseInt(this.variableY));
                break;
            case "roman":
                this.calculationResult = RomanNumeration
                        .toString(RomanNumeration.toInt(this.variableX) * RomanNumeration.toInt(this.variableY));
                break;
            default:
                throw new CalculatorRuntimeException("Use only arabic or roman integer numbers at the same time.");
            }
            break;
        case "/":
            switch (this.typeOfVariables) {
            case "arabic":
                this.calculationResult = Integer
                        .toString(Integer.parseInt(this.variableX) / Integer.parseInt(this.variableY));
                break;
            case "roman":
                this.calculationResult = RomanNumeration
                        .toString(RomanNumeration.toInt(this.variableX) / RomanNumeration.toInt(this.variableY));
                break;
            default:
                throw new CalculatorRuntimeException("Use only arabic or roman integer numbers at the same time.");
            }
            break;
        default:
            throw new CalculatorRuntimeException("Unknown operation character.");
        }

        if (this.calculationResult != null) {
            result = true;
        }

        return result;
    }

    public boolean defineTypesOfVariables() { // dev.bookmark
        boolean result = false;

        if (variableIsArabic(this.variableX) && variableIsArabic(this.variableY)) {
            this.typeOfVariables = "arabic";
            result = true;
        } else if (variableIsRoman(this.variableX) && variableIsRoman(this.variableY)) { // dev.bookmark
            this.typeOfVariables = "roman";
            result = true;
        } else {
            throw new CalculatorRuntimeException(
                    "Strange characters in input. Use only arabic or roman numbers in input at the same time.");
        }

        return result;
    }

    public void printCalculationResult() {
        if (this.calculationResult != null) {
            System.out.println(this.calculationResult);
        } else {
            throw new CalculatorRuntimeException("Some mystic error in calculation. Go into debug mode, pal.");
        }
    }

    public Calculator(String inputExpression) {
        this.operationCharacters = new String[] { "+", "-", "/", "*" };

        if ((parseExpression(inputExpression) && defineTypesOfVariables()) && checkVariablesInRange()) {
            calculateResult();
        }
    }

}
