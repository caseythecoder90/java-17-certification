import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.Set;
import java.util.List;
import java.util.Optional;
import java.util.Objects;
import java.util.stream.*;


/*

    public <U> U reduce(U identity, 
        BiFunction<U,? super T, U> accumulator, 
        BinaryOperator<U> combiner);

*/


public class StreamsPractice {


    public static void main(String... args) {
        String[] array = {"w", "o", "l", "f"};
        var copy = Arrays.stream(array)
            .reduce("", (s, t) -> s + t);
        System.out.println(copy);
        var copy2 = Arrays.stream(array)
            .reduce("", String::concat);
        System.out.println(copy2);

        Stream<String> wolf = Stream.of("w", "o", "l", "f!");
        int length = wolf.reduce(0, (i, s) -> i + s.length(), (a, b) -> a + b);
        System.out.println(length);


        /*

            public <R> R collect(Supplier<R> supplier, 
                BiConsumer<R,? super T> accumulator, 
                BiConsumer<R, R> combiner);

        */
        Stream<String> secondWolf = Stream.of("w", "o", "l", "f!");
        StringBuilder collectedWord = secondWolf.collect(
            StringBuilder::new,
            StringBuilder::append,
            StringBuilder::append
        );

        System.out.println(collectedWord);


        Stream<String> collectorsStream = Stream.of("w", "o", "l", "f");
        Set<String> set = collectorsStream.collect(Collectors.toSet());
        System.out.println(set);

        Stream<String> howIFeel = Stream.of("I", "am", "depressed", "and", "want", "to", "use", "some", "good", "weed", "I", "a", "depressed", "and", "want", "to");
        howIFeel
            .map(String::toUpperCase)
            .map(s -> s.concat("hello"))
            .distinct()
            .map(s -> s.replace("hello", ""))
            .forEach(System.out::print);

        System.out.println();


        Stream<Integer> integerStream = Stream.iterate(1, n -> n + 1);
        integerStream
            .skip(5)
            .limit(2)
            .forEach(System.out::print);

        System.out.println();

        List<String> friends = List.of("Michael", "Peter", "Tim", "Ryan", "Everett", "Jose", "Linda", "Anna", "Iany", "Cobin", "Steve", "Sean");
        String singleFriend = 
            friends.stream()
                .filter(name -> name.length() == 5)
                .limit(4)
                .sorted()
                .filter(Objects::nonNull)
                .map(String::toUpperCase)
                .reduce("", (a, b) -> a + " " + b);

        Set<String> secondFriend = 
            friends.stream()
                .filter(name -> name.length() == 5)
                .limit(4)
                .sorted()
                .filter(Objects::nonNull)
                .map(String::toUpperCase)
                .collect(Collectors.toSet());


        System.out.println(singleFriend);
        System.out.println(secondFriend);


        Stream<Integer> numsNums = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);

        Integer sumOne = numsNums.reduce(0, (a, b) -> a + b);
        System.out.println("sumOne: " + sumOne);

        Stream<Integer> numsNums2 = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);


        Integer countOne = numsNums2.mapToInt(x -> x).sum();
        System.out.println("primitive int stream : " + countOne);


        Stream<Integer> averageStream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);

        double average = averageStream
            .mapToInt(x -> x)
            .peek(num -> System.out.println(num))
            .peek(System.out::println)
            .average()
            .orElseThrow(
               () -> {
                System.out.println("There was an exception");
                return new RuntimeException("ouch");
               }
            );
        System.out.println(average);
        
        StringBuilder builder = new StringBuilder("Hello World");
        Optional.ofNullable(builder)
            .ifPresent(b -> {
                System.out.println("optional has data");
                String s = b.toString();
                IntStream.iterate(1, n -> n + 1) // <-- IntStream.generate() with no supplier was invalid
                    .map(n -> n * 2)
                    .limit(5)
                    .forEach(System.out::println);
                System.out.println(b);
            });

        



    }
}