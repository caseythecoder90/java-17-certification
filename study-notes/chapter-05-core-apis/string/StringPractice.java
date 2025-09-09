

public class StringPractice {

    public static void main(String... args) {

        var block = """
            a
             b
            c""";
        
        var concat = " a\n" 
                 +   "  b\n"
                 +   " c";

        System.out.println(block.length());                 // 6
        System.out.println(concat.length());                // 9
        System.out.println(block.indent(1).length());       // 10
        System.out.println(concat.indent(-1).length());     // 7
        System.out.println(concat.indent(-4).length());     // 6
        System.out.println(concat.stripIndent().length());  // 6

        var str = "1\\t2";
        System.out.println(str);
        System.out.println(str.translateEscapes());

        str = "1\\n....";
        System.out.println(str);
        System.out.println(str.translateEscapes());


        System.out.println();

        str = "1\\\n....";
        System.out.println(str);
        System.out.println(str.translateEscapes());


    }
}