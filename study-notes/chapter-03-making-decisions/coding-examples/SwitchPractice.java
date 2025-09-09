import java.util.stream.IntStream;
import java.util.Random;
import java.util.List;

public class SwitchPractice {

    String switchPractice = "SWITCH_PRACTICE";
    Random random = new Random();

    public static void main(String... argumentos) {
        SwitchPractice switchPractice = new SwitchPractice();
        switchPractice.executeSwitch();
        switchPractice.executeStreamOperations();

        CLEANING: 
        for (char stables = 'a'; stables <= 'd'; stables++) {
            
            for (int leopard = 1; leopard < 4; leopard++) {

                if (stables == 'b' || leopard == 2) 
                    continue CLEANING;

                System.out.println("Cleaning: " + stables + ", " + leopard);
            }

        }


        System.out.print("-".repeat(100));

        System.out.println();

        CLEANING2: 
        for (char stables = 'a'; stables <= 'd'; stables++) {
            
            for (int leopard = 1; leopard < 4; leopard++) {

                if (stables == 'b' || leopard == 2) 
                    continue;

                System.out.println("Cleaning: " + stables + ", " + leopard);
            }

        }
    }

    public void executeStreamOperations() {
           IntStream.rangeClosed(0, 5)
            .forEach(System.out::println);

        for (int i = 4; i >= 0; i--) System.out.print(i + " ");

        System.out.println();

        List<Integer> nums = IntStream.generate(() -> random.nextInt(0, 5000000))
            .limit(5000)
            .distinct()
            .boxed()
            .toList();

        for (var num : nums) {

            if (num % 2 == 0) System.out.println("Num [%d] is even".formatted(num));

            else System.out.println("Num [%d] - is odd".formatted(num));
        
        }

    }


    public void executeSwitch() {
         final String valueOne = "valueOne";
        final String valueTwo = "valueTwo";
        final String three = "valueThree";

        final String copySwitchPractice = "SWITCH_PRACTICE";

        boolean found = switch (switchPractice) {
            case valueOne -> {
                System.out.println("At first this won't compile.. valueOne var is not final");
                yield false;
            }

            case "donald trump", valueTwo -> false;

            case three -> {
                System.out.println("At first this one wouldn't compile either. I may have already changed it tho.. hehe");
                yield false;
            }

            case copySwitchPractice -> {
                System.out.println("I am curious as to whether this is going to work on a string because it is an object.. I don't think you can even do it like this");
                yield true;
            }

            default -> {
                System.out.println("The value was not foun...");
                yield false;
            }
        };

        System.out.println("Found: " + found);

    }
}