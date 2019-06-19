import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class part2 {

    /**
     * Reads infix expression and variables and pass them to infixToPostfix method then prints expression result
     * @param args Given filename
     */
    public static void main(String[] args){
        String infix=null;
        MyList<String> variables = new MyList<String>();
        MyList<Double> var_values = new MyList<Double>();

        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("=")){
                    Scanner scanner = new Scanner(line);
                    while (scanner.hasNext()) {
                        if (scanner.hasNextInt()) {
                            var_values.add(scanner.nextDouble());
                        } else {
                            String temp = scanner.next();
                            if (!temp.equals("="))
                                variables.add(temp);
                        }
                    }
                }
                infix = line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Double result = infixToPostfix(infix, variables, var_values);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method convert infix to postfix and then evaluate the postfix expression with a stack based algorithm
     * @param infix Given string of infix expression
     * @param variables Given list of variables names
     * @param var_values Given list of variables values
     * @return Returns expression result
     * @throws Exception if infix == null or Wrong type expression
     */
    private static Double infixToPostfix(String infix, MyList<String> variables, MyList<Double> var_values) throws Exception{
        if (infix == null)
            throw new NullPointerException();

        String[] arr = infix.split(" |((?<=\\()|(?=\\())");
        MyStack<String> postfix = new MyStack<String>(arr.length/2);
        MyStack<Double> ret = new MyStack<Double>();
        String op, c;
        Double temp, res;

        for (int i = 0; i<arr.length; ++i)
        {
            c = arr[i];

            if (c.matches("[a-zA-Z]")){
                for (int j = 0; j<variables.getSize(); j++){
                    if (variables.get(j).equals(c))
                        ret.push(var_values.get(j));
                    else if (variables.get(j).equals(c))
                        ret.push(var_values.get(j));
                }
            }
            else if(c.equals("(")) {
                postfix.push(c);
            }
            else if( c.equals(")")) {
                while (!postfix.isEmpty() && !postfix.peek().equals("(")) {
                    op = postfix.pop();
                    temp = Calculate(ret, op);
                    ret.push(temp);
                }

                if (!postfix.isEmpty() && !postfix.peek().equals("("))
                     throw new Exception("Wrong type expression");
                else
                    postfix.pop();
            }
            else if (c.matches(".*\\d.*")) {
                ret.push(Double.parseDouble(c));
            }
            else if (isOperator(c)){
                while (!postfix.isEmpty() && Precedence(c) <= Precedence(postfix.peek())){
                    op = postfix.pop();
                    temp = Calculate(ret, op);
                    ret.push(temp);
                }
                postfix.push(c);
            }
        }
        while (!postfix.isEmpty()){
            op = postfix.pop();
            temp = Calculate(ret, op);
            ret.push(temp);
        }

        // Assign expression result to res
        res = ret.pop();

        return res;
    }

    /**
     * This method calculates one operator from stack and returns result
     * @param stack Given stack which is values of expression
     * @param operator Given operator for calculation
     * @return Returns calculation result
     */
    static Double Calculate(MyStack<Double> stack, String operator)
    {
        Double junk, junk2;
        switch (operator)
        {
            case "+":
                junk = stack.pop();
                junk2 = stack.pop();
                return junk2 + junk;
            case "-":
                junk = stack.pop();
                junk2 = stack.pop();
                return junk2 - junk;
            case "*":
                junk = stack.pop();
                junk2 = stack.pop();
                return junk2 * junk;
            case "/":
                junk = stack.pop();
                junk2 = stack.pop();
                return junk2 / junk;
            case "^":
                junk = stack.pop();
                junk2 = stack.pop();
                return pow(junk2, junk);
            case "%":
                junk = stack.pop();
                junk2 = stack.pop();
                return junk2 % junk;

            case "sin":
                junk = stack.pop();
                return sin(junk);
            case "cos":
                junk = stack.pop();
                return cos(junk);
            case "abs":
                junk = stack.pop();
                if (junk<0)
                    junk -= 2*junk;
                return junk;
        }
        return 1.0;
    }

    /**
     * This method returns precedence of operator
     * @param ch Given operator
     * @return precedence of operator, -1 is returned if the wrong operator is given
     */
    static int Precedence(String ch)
    {
        switch (ch)
        {
            case "+":
            case "-":
                return 1;

            case "*":
            case "/":
            case "%":
                return 2;

            case "^":
            case "sin":
            case "cos":
            case "abs":
                return 3;
        }
        return -1;
    }

    /**
     * This method take string and return true if it is operator.
     * @param operator Given string for checking
     * @return Returns true if given string is operator, otherwise false
     */
    static boolean isOperator(String operator)
    {
        switch (operator)
        {
            case "+":
            case "-":
            case "*":
            case "/":
            case "sin":
            case "cos":
            case "abs":
            case "^":
                return true;
        }
        return false;
    }

    /**
     * This method calculates sin value
     * @param degree Given degree value for sin
     * @return Returns sin value of given degree
     */
    public static double sin(double degree) {
        final double PI = 3.14159265358979323846;

        double radian = degree*(PI/180.0);

        if (radian == Double.NEGATIVE_INFINITY || !(radian < Double.POSITIVE_INFINITY)) {
            return Double.NaN;
        }

        radian %= 2 * PI;

        if (radian < 0) {
            radian = 2 * PI - radian;
        }

        int sign = 1;
        if (radian > PI) {
            radian -= PI;
            sign = -1;
        }

        final int PRECISION = 50;
        double temp = 0;
        for (int i = 0; i <= PRECISION; i++) {
            temp += pow(-1, i) * (pow(radian, 2 * i + 1) / factorial(2 * i + 1));
        }

        return sign * temp;

    }

    /**
     * This method calculates cos value
     * @param degree Given degree value for cos
     * @return Returns cos value of given degree
     */
    private static double cos(double degree) {
        return sin((90.0-degree));
    }

    /**
     * This method calculates factorial of given value
     * @param val Given int value for factorial
     * @return Returns factorial of given int
     */
    private static Double factorial(int val) {
        Double res=1.0;
        for (int i=1; i<val; val--)
            res *= val;

        return res;
    }

    /**
     * This method calculates power
     * @param a base number
     * @param b Exponent number
     * @return Returns result of a to the power of b
     */
    private static double pow(double a, double b) {
        double result=1;
        for (int i = 0; i < b; i++)
            result *= a;

        return result;
    }
}
