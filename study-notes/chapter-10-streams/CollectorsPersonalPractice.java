import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Function;

public class CollectorsPersonalPractice {

    public static void main(String[] args) {

        List<String> ohMy = List.of("Lions", "Tigers", "Bears", "Cheetah", "Hippo", "Stewart Little");

        Map<String, Integer> mapOne = ohMy.stream()
            .collect(
                Collectors.toMap(
                    Function.identity(), 
                    String::length
                )
            );
        
        System.out.println("Map of string to length of the string. Collectors.toMap(Function k, Function v)");

        System.out.println(mapOne);

         Map<String, Integer> mapTwo = ohMy.stream()
            .collect(
                Collectors.toMap(
                    val -> val, 
                    String::length
                )
            );

        System.out.println(mapTwo);

        Map<Integer, String> countToName = ohMy.stream()
            .collect(
                Collectors.toMap(
                    String::length,
                    Function.identity(),
                    (a, b) -> a + "," + b,
                    TreeMap::new
                )
            );
        System.out.println(countToName);

        List<String> ohMyMy = new ArrayList<>(
            List.of("Sally", "Walker", "Tommy", "Ian", "Lucy", "Tim", "Walker", "Ricky")
        );

        Map<Integer, List<String>> countToListOfNames = 
            ohMyMy.stream()
                .collect(
                    Collectors.groupingBy(String::length)
                );

        System.out.println();

        System.out.println("Grouping by");
        System.out.println(countToListOfNames);


        Map<Integer, Set<String>> countToSet = 
            ohMyMy.stream()
                .collect(
                    Collectors.groupingBy(
                        String::length,
                        TreeMap::new,
                        Collectors.toSet()
                    )
                );
        System.out.println(countToSet);



        
    }
}