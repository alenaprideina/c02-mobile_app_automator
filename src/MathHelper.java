public class MathHelper
{
    final public int finalField = 5;
    public int simple_int = 7;
    public static int static_int = 7;

    public void changeSimpleInt()
    {
        this.simple_int = 8;
        static_int = 8;
    }

    public static void changeSimpleIntByStaticFunction()
    {
        // cannot do this

        // this.simple_int = 8;
    }

    public int calc (int a, int b, char action)
    {
        if (action == '+') {
            return this.plus(a, b);
        } else if (action == '-') {
            return this.minus(a, b);
        } else if (action == '*') {
            return this.multiply(a, b);
        } else if (action == '/') {
            return this.divide(a, b);
        } else {
            return this.typeAnErrorAndReturnDefaultValue("Wrong action: " + action);
        }
    }

    private int typeAnErrorAndReturnDefaultValue (String error_message)
    {
        System.out.println(error_message);
        return 0;
    }

    private int plus(int a, int b)
    {
        return a + b;
    }

    private int minus(int a, int b)
    {
        return a - b;
    }

    private int multiply(int a, int b)
    {
        return a * b;
    }

    private int divide(int num, int div)
    {
        if (div == 0) {
            return this.typeAnErrorAndReturnDefaultValue("Cannot divide by zero");
        } else {
            return num/div;
        }
    }
}
