package jarexample;

public class MainApp {
    public static void main(String[] args) {
        System.out.println("Welcome to Java JAR Example!");
        
        Calculator calc = new Calculator();
        int result = calc.add(5, 3);
        System.out.println("5 + 3 = " + result);
        
        StringUtil stringUtil = new StringUtil();
        String reversed = stringUtil.reverse("Hello World");
        System.out.println("Reversed: " + reversed);
        
        System.out.println("JAR file executed successfully!");
    }
}