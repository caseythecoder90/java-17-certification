

public class TextBlocksDemo {

    public static void main(String... argumentos) {

        System.out.println("Will have a new line");

        String blockOne = """
          *
         * *
        * * *
        """;

        System.out.println(blockOne);

        String blockTwo = """
          *
         * *
        * * *""";
        

        System.out.println("This will not have a newline");
        System.out.println(blockTwo);


    }
}