import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Function;

public class CollectorsPractice {
    
    public static void main(String[] args) {
        System.out.println("=== Java Collectors Practice ===\n");
        
        // Sample data for examples
        List<String> names = List.of("Alice", "Bob", "Charlie", "David", "Eve", "Alice");
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Person> people = List.of(
            new Person("Alice", 25, "Engineer"),
            new Person("Bob", 30, "Manager"), 
            new Person("Charlie", 35, "Engineer"),
            new Person("David", 40, "Manager"),
            new Person("Eve", 28, "Designer")
        );
        
        demonstrateToList(names);
        demonstrateToSet(names);
        demonstrateAllToMapVariations(people);
        demonstrateGroupingBy(people);
        demonstratePartitioningBy(numbers, people);
        demonstrateAllCounting();
        demonstrateAllAveraging(numbers, people);
        demonstrateAllSumming(numbers, people);
        demonstrateAllSummarizing(numbers, people);
        demonstrateJoining(names);
        demonstrateReducing(numbers);
        demonstrateMapping(people);
        demonstrateAllFiltering(people);
        demonstrateFlatMapping(people);
        demonstrateCollectingAndThen(names, numbers);
        demonstrateAllTeeing(numbers);
    }
    
    /**
     * toList() - Collects stream elements into a List
     * Returns an ArrayList by default
     */
    private static void demonstrateToList(List<String> names) {
        System.out.println("=== Collectors.toList() ===");
        
        // Basic usage - collect all elements
        List<String> namesList = names.stream()
            .collect(Collectors.toList());
        System.out.println("All names: " + namesList);
        
        // With filtering
        List<String> longNames = names.stream()
            .filter(name -> name.length() > 4)
            .collect(Collectors.toList());
        System.out.println("Names longer than 4 chars: " + longNames);
        
        // With transformation
        List<String> upperCaseNames = names.stream()
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        System.out.println("Uppercase names: " + upperCaseNames);
        System.out.println();
    }
    
    /**
     * toSet() - Collects stream elements into a Set
     * Automatically removes duplicates, returns HashSet by default
     */
    private static void demonstrateToSet(List<String> names) {
        System.out.println("=== Collectors.toSet() ===");
        
        // Basic usage - removes duplicates
        Set<String> uniqueNames = names.stream()
            .collect(Collectors.toSet());
        System.out.println("Unique names: " + uniqueNames);
        
        // With transformation
        Set<Integer> nameLengths = names.stream()
            .map(String::length)
            .collect(Collectors.toSet());
        System.out.println("Unique name lengths: " + nameLengths);
        System.out.println();
    }
    
    /**
     * ALL toMap() VARIATIONS - Collects stream elements into a Map
     * 
     * SIGNATURES:
     * 1. toMap(Function<T,K> keyMapper, Function<T,V> valueMapper)
     * 2. toMap(Function<T,K> keyMapper, Function<T,V> valueMapper, BinaryOperator<V> mergeFunction)
     * 3. toMap(Function<T,K> keyMapper, Function<T,V> valueMapper, BinaryOperator<V> mergeFunction, Supplier<M> mapSupplier)
     * 4. toConcurrentMap(...) - same signatures but returns ConcurrentMap
     * 
     * PARAMETERS:
     * - keyMapper: Function to extract key from stream element
     * - valueMapper: Function to extract value from stream element
     * - mergeFunction: BinaryOperator to resolve duplicate keys (optional)
     * - mapSupplier: Supplier to create specific Map type (optional)
     */
    private static void demonstrateAllToMapVariations(List<Person> people) {
        System.out.println("=== ALL Collectors.toMap() VARIATIONS ===");
        
        // Basic usage - name as key, age as value
        Map<String, Integer> nameToAge = people.stream()
            .collect(Collectors.toMap(
                Person::getName,    // key mapper
                Person::getAge      // value mapper
            ));
        System.out.println("Name to age map: " + nameToAge);
        
        // With duplicate key handling - keep older person
        List<Person> peopleWithDuplicates = List.of(
            new Person("John", 25, "Engineer"),
            new Person("John", 30, "Manager")  // duplicate name
        );
        
        Map<String, Integer> nameToMaxAge = peopleWithDuplicates.stream()
            .collect(Collectors.toMap(
                Person::getName,
                Person::getAge,
                Integer::max        // merge function for duplicates
            ));
        System.out.println("Name to max age: " + nameToMaxAge);
        
        // Specify map type (TreeMap for sorted keys)
        Map<String, String> sortedNameToJob = people.stream()
            .collect(Collectors.toMap(
                Person::getName,
                Person::getJob,
                (existing, replacement) -> existing,  // keep first
                TreeMap::new                          // map supplier
            ));
        System.out.println("Sorted name to job: " + sortedNameToJob);
        
        // toConcurrentMap - thread-safe version
        Map<String, String> concurrentNameToJob = people.stream()
            .collect(Collectors.toConcurrentMap(
                Person::getName,
                Person::getJob
            ));
        System.out.println("Concurrent name to job: " + concurrentNameToJob);
        System.out.println();
    }
    
    /**
     * groupingBy() - Groups elements by a classifier function
     * Returns Map<K, List<V>> by default
     */
    private static void demonstrateGroupingBy(List<Person> people) {
        System.out.println("=== Collectors.groupingBy() ===");
        
        // Basic grouping by job
        Map<String, List<Person>> peopleByJob = people.stream()
            .collect(Collectors.groupingBy(Person::getJob));
        System.out.println("People grouped by job:");
        peopleByJob.forEach((job, persons) -> 
            System.out.println("  " + job + ": " + persons.stream().map(Person::getName).toList()));
        
        // Grouping with downstream collector - count people per job
        Map<String, Long> countByJob = people.stream()
            .collect(Collectors.groupingBy(
                Person::getJob,
                Collectors.counting()
            ));
        System.out.println("Count by job: " + countByJob);
        
        // Grouping with custom map type and downstream collector
        TreeMap<String, Set<String>> jobToNames = people.stream()
            .collect(Collectors.groupingBy(
                Person::getJob,
                TreeMap::new,                           // map supplier
                Collectors.mapping(Person::getName, Collectors.toSet())  // downstream collector
            ));
        System.out.println("Job to unique names (sorted): " + jobToNames);
        
        // Multi-level grouping - by job, then by age range
        Map<String, Map<String, List<Person>>> nestedGrouping = people.stream()
            .collect(Collectors.groupingBy(
                Person::getJob,
                Collectors.groupingBy(person -> 
                    person.getAge() < 30 ? "Young" : "Experienced"
                )
            ));
        System.out.println("Nested grouping (job -> age range):");
        nestedGrouping.forEach((job, ageGroups) -> {
            System.out.println("  " + job + ":");
            ageGroups.forEach((ageRange, persons) ->
                System.out.println("    " + ageRange + ": " + 
                    persons.stream().map(Person::getName).toList()));
        });
        System.out.println();
    }
    
    /**
     * partitioningBy() - Partitions elements into two groups based on a predicate
     * Returns Map<Boolean, List<T>>
     */
    private static void demonstratePartitioningBy(List<Integer> numbers, List<Person> people) {
        System.out.println("=== Collectors.partitioningBy() ===");
        
        // Partition numbers into even/odd
        Map<Boolean, List<Integer>> evenOddNumbers = numbers.stream()
            .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        System.out.println("Even numbers: " + evenOddNumbers.get(true));
        System.out.println("Odd numbers: " + evenOddNumbers.get(false));
        
        // Partition people by age (young vs experienced)
        Map<Boolean, List<Person>> youngVsExperienced = people.stream()
            .collect(Collectors.partitioningBy(p -> p.getAge() < 30));
        System.out.println("Young people: " + 
            youngVsExperienced.get(true).stream().map(Person::getName).toList());
        System.out.println("Experienced people: " + 
            youngVsExperienced.get(false).stream().map(Person::getName).toList());
        
        // Partition with downstream collector - count in each partition
        Map<Boolean, Long> countByAgeGroup = people.stream()
            .collect(Collectors.partitioningBy(
                p -> p.getAge() < 30,
                Collectors.counting()
            ));
        System.out.println("Count by age group: " + countByAgeGroup);
        System.out.println();
    }
    
    /**
     * counting() - Counts the number of elements
     * 
     * SIGNATURE:
     * counting() -> Collector<T, ?, Long>
     * 
     * PARAMETERS: None
     * RETURNS: Long count of elements
     * 
     * Often used as downstream collector with groupingBy/partitioningBy
     */
    private static void demonstrateAllCounting() {
        System.out.println("=== Collectors.counting() - SIGNATURE: counting() ===");
        
        List<String> words = List.of("apple", "banana", "cherry", "date", "elderberry");
        
        // Simple counting
        long totalCount = words.stream()
            .collect(Collectors.counting());
        System.out.println("Total word count: " + totalCount);
        
        // Count with filtering
        long longWordCount = words.stream()
            .filter(word -> word.length() > 5)
            .collect(Collectors.counting());
        System.out.println("Long word count: " + longWordCount);
        
        // Counting as downstream collector (group by first letter, count each group)
        Map<Character, Long> countByFirstLetter = words.stream()
            .collect(Collectors.groupingBy(
                word -> word.charAt(0),
                Collectors.counting()
            ));
        System.out.println("Count by first letter: " + countByFirstLetter);
        System.out.println();
    }
    
    /**
     * ALL AVERAGING COLLECTORS - Calculate average of numeric values
     * 
     * SIGNATURES:
     * averagingInt(ToIntFunction<T> mapper) -> Collector<T, ?, Double>
     * averagingLong(ToLongFunction<T> mapper) -> Collector<T, ?, Double>
     * averagingDouble(ToDoubleFunction<T> mapper) -> Collector<T, ?, Double>
     * 
     * PARAMETERS:
     * - mapper: Function to extract numeric value from stream element
     * RETURNS: Double average of extracted values
     */
    private static void demonstrateAllAveraging(List<Integer> numbers, List<Person> people) {
        System.out.println("=== ALL Collectors.averaging*() VARIATIONS ===");
        
        // averagingInt
        Double avgNumber = numbers.stream()
            .collect(Collectors.averagingInt(Integer::intValue));
        System.out.println("Average of numbers (averagingInt): " + avgNumber);
        
        // averagingLong
        Double avgNumberLong = numbers.stream()
            .collect(Collectors.averagingLong(Integer::longValue));
        System.out.println("Average of numbers (averagingLong): " + avgNumberLong);
        
        // averagingDouble
        Double avgNumberDouble = numbers.stream()
            .collect(Collectors.averagingDouble(Integer::doubleValue));
        System.out.println("Average of numbers (averagingDouble): " + avgNumberDouble);
        
        // averagingInt with object extraction
        Double avgAge = people.stream()
            .collect(Collectors.averagingInt(Person::getAge));
        System.out.println("Average age (averagingInt): " + avgAge);
        
        // averagingDouble as downstream collector
        Map<String, Double> avgAgeByJob = people.stream()
            .collect(Collectors.groupingBy(
                Person::getJob,
                Collectors.averagingDouble(Person::getAge)
            ));
        System.out.println("Average age by job: " + avgAgeByJob);
        System.out.println();
    }
    
    /**
     * ALL SUMMING COLLECTORS - Calculate sum of numeric values
     * 
     * SIGNATURES:
     * summingInt(ToIntFunction<T> mapper) -> Collector<T, ?, Integer>
     * summingLong(ToLongFunction<T> mapper) -> Collector<T, ?, Long>
     * summingDouble(ToDoubleFunction<T> mapper) -> Collector<T, ?, Double>
     * 
     * PARAMETERS:
     * - mapper: Function to extract numeric value from stream element
     * RETURNS: Sum of extracted values (Integer, Long, or Double)
     */
    private static void demonstrateAllSumming(List<Integer> numbers, List<Person> people) {
        System.out.println("=== ALL Collectors.summing*() VARIATIONS ===");
        
        // summingInt
        Integer sumNumbers = numbers.stream()
            .collect(Collectors.summingInt(Integer::intValue));
        System.out.println("Sum of numbers (summingInt): " + sumNumbers);
        
        // summingLong
        Long sumNumbersLong = numbers.stream()
            .collect(Collectors.summingLong(Integer::longValue));
        System.out.println("Sum of numbers (summingLong): " + sumNumbersLong);
        
        // summingDouble
        Double sumNumbersDouble = numbers.stream()
            .collect(Collectors.summingDouble(Integer::doubleValue));
        System.out.println("Sum of numbers (summingDouble): " + sumNumbersDouble);
        
        // summingInt with object extraction
        Integer totalAge = people.stream()
            .collect(Collectors.summingInt(Person::getAge));
        System.out.println("Total age (summingInt): " + totalAge);
        
        // summingInt as downstream collector
        Map<String, Integer> totalAgeByJob = people.stream()
            .collect(Collectors.groupingBy(
                Person::getJob,
                Collectors.summingInt(Person::getAge)
            ));
        System.out.println("Total age by job: " + totalAgeByJob);
        System.out.println();
    }
    
    /**
     * ALL SUMMARIZING COLLECTORS - Provide statistical summary of numeric values
     * 
     * SIGNATURES:
     * summarizingInt(ToIntFunction<T> mapper) -> Collector<T, ?, IntSummaryStatistics>
     * summarizingLong(ToLongFunction<T> mapper) -> Collector<T, ?, LongSummaryStatistics>
     * summarizingDouble(ToDoubleFunction<T> mapper) -> Collector<T, ?, DoubleSummaryStatistics>
     * 
     * PARAMETERS:
     * - mapper: Function to extract numeric value from stream element
     * RETURNS: SummaryStatistics object with count, sum, min, max, average
     */
    private static void demonstrateAllSummarizing(List<Integer> numbers, List<Person> people) {
        System.out.println("=== ALL Collectors.summarizing*() VARIATIONS ===");
        
        // summarizingInt
        IntSummaryStatistics intStats = numbers.stream()
            .collect(Collectors.summarizingInt(Integer::intValue));
        System.out.println("Int Summary Statistics:");
        System.out.println("  Count: " + intStats.getCount());
        System.out.println("  Sum: " + intStats.getSum());
        System.out.println("  Min: " + intStats.getMin());
        System.out.println("  Max: " + intStats.getMax());
        System.out.println("  Average: " + intStats.getAverage());
        
        // summarizingLong
        LongSummaryStatistics longStats = numbers.stream()
            .collect(Collectors.summarizingLong(Integer::longValue));
        System.out.println("Long Summary Statistics:");
        System.out.println("  Count: " + longStats.getCount());
        System.out.println("  Sum: " + longStats.getSum());
        System.out.println("  Min: " + longStats.getMin());
        System.out.println("  Max: " + longStats.getMax());
        System.out.println("  Average: " + longStats.getAverage());
        
        // summarizingDouble
        DoubleSummaryStatistics doubleStats = numbers.stream()
            .collect(Collectors.summarizingDouble(Integer::doubleValue));
        System.out.println("Double Summary Statistics:");
        System.out.println("  Count: " + doubleStats.getCount());
        System.out.println("  Sum: " + doubleStats.getSum());
        System.out.println("  Min: " + doubleStats.getMin());
        System.out.println("  Max: " + doubleStats.getMax());
        System.out.println("  Average: " + doubleStats.getAverage());
        
        // Summarizing ages of people
        IntSummaryStatistics ageStats = people.stream()
            .collect(Collectors.summarizingInt(Person::getAge));
        System.out.println("Age statistics:");
        System.out.println("  Count: " + ageStats.getCount());
        System.out.println("  Sum: " + ageStats.getSum());
        System.out.println("  Min age: " + ageStats.getMin());
        System.out.println("  Max age: " + ageStats.getMax());
        System.out.println("  Average age: " + ageStats.getAverage());
        
        // Summarizing with grouping
        Map<String, IntSummaryStatistics> ageStatsByJob = people.stream()
            .collect(Collectors.groupingBy(
                Person::getJob,
                Collectors.summarizingInt(Person::getAge)
            ));
        System.out.println("Age statistics by job:");
        ageStatsByJob.forEach((job, stats) ->
            System.out.println("  " + job + " - Avg age: " + stats.getAverage() + 
                             ", Min: " + stats.getMin() + ", Max: " + stats.getMax()));
        System.out.println();
    }
    
    /**
     * joining() - Joins stream elements into a single String
     * Can specify delimiter, prefix, and suffix
     */
    private static void demonstrateJoining(List<String> names) {
        System.out.println("=== Collectors.joining() ===");
        
        // Simple joining with comma
        String commaSeparated = names.stream()
            .collect(Collectors.joining(", "));
        System.out.println("Comma separated: " + commaSeparated);
        
        // Joining with delimiter, prefix, and suffix
        String formattedList = names.stream()
            .distinct()  // remove duplicates
            .collect(Collectors.joining(", ", "[", "]"));
        System.out.println("Formatted list: " + formattedList);
        
        // Joining transformed elements
        String uppercaseNames = names.stream()
            .map(String::toUpperCase)
            .collect(Collectors.joining(" | "));
        System.out.println("Uppercase with pipe: " + uppercaseNames);
        
        // Joining with filtering
        String longNamesOnly = names.stream()
            .filter(name -> name.length() > 3)
            .collect(Collectors.joining(" - ", "Long names: ", "."));
        System.out.println(longNamesOnly);
        System.out.println();
    }
    
    /**
     * reducing() - Performs a reduction on stream elements
     * Similar to Stream.reduce() but as a collector
     */
    private static void demonstrateReducing(List<Integer> numbers) {
        System.out.println("=== Collectors.reducing() ===");
        
        // Sum with initial value
        Optional<Integer> sum = numbers.stream()
            .collect(Collectors.reducing(Integer::sum));
        System.out.println("Sum (no identity): " + sum.orElse(0));
        
        // Sum with identity value
        Integer sumWithIdentity = numbers.stream()
            .collect(Collectors.reducing(0, Integer::sum));
        System.out.println("Sum (with identity): " + sumWithIdentity);
        
        // Find maximum with mapper function
        Optional<Integer> maxSquare = numbers.stream()
            .collect(Collectors.reducing(
                (a, b) -> (a * a) > (b * b) ? a : b  // compare squares
            ));
        System.out.println("Number with max square: " + maxSquare.orElse(0));
        
        // Complex reduction with mapper - sum of squares
        Integer sumOfSquares = numbers.stream()
            .collect(Collectors.reducing(
                0,                          // identity
                n -> n * n,                 // mapper (square each number)
                Integer::sum                // combiner
            ));
        System.out.println("Sum of squares: " + sumOfSquares);
        System.out.println();
    }
    
    /**
     * mapping() - Applies a mapping function before collecting
     * Often used as downstream collector
     */
    private static void demonstrateMapping(List<Person> people) {
        System.out.println("=== Collectors.mapping() ===");
        
        // Group people by job, but collect only their names
        Map<String, List<String>> namesByJob = people.stream()
            .collect(Collectors.groupingBy(
                Person::getJob,
                Collectors.mapping(Person::getName, Collectors.toList())
            ));
        System.out.println("Names by job: " + namesByJob);
        
        // Group by job, collect ages as set
        Map<String, Set<Integer>> agesByJob = people.stream()
            .collect(Collectors.groupingBy(
                Person::getJob,
                Collectors.mapping(Person::getAge, Collectors.toSet())
            ));
        System.out.println("Ages by job: " + agesByJob);
        
        // Partition by age, collect job titles
        Map<Boolean, Set<String>> jobsByAgeGroup = people.stream()
            .collect(Collectors.partitioningBy(
                p -> p.getAge() < 30,
                Collectors.mapping(Person::getJob, Collectors.toSet())
            ));
        System.out.println("Jobs by age group: " + jobsByAgeGroup);
        System.out.println();
    }
    
    /**
     * filtering() - Filters elements before collecting (Java 9+)
     * 
     * SIGNATURE:
     * filtering(Predicate<T> predicate, Collector<T, A, R> downstream) -> Collector<T, ?, R>
     * 
     * PARAMETERS:
     * - predicate: Predicate to test elements
     * - downstream: Collector to apply to filtered elements
     * RETURNS: Result of downstream collector applied to filtered elements
     * 
     * Used as downstream collector to filter within groups
     */
    private static void demonstrateAllFiltering(List<Person> people) {
        System.out.println("=== Collectors.filtering() - SIGNATURE: filtering(Predicate<T>, Collector<T,A,R>) ===");
        
        // Group by job, but only include people over 30
        Map<String, List<Person>> experiencedByJob = people.stream()
            .collect(Collectors.groupingBy(
                Person::getJob,
                Collectors.filtering(p -> p.getAge() > 30, Collectors.toList())
            ));
        System.out.println("Experienced people by job:");
        experiencedByJob.forEach((job, persons) ->
            System.out.println("  " + job + ": " + persons.stream().map(Person::getName).toList()));
        
        // Combine filtering and mapping
        Map<String, List<String>> experiencedNamesByJob = people.stream()
            .collect(Collectors.groupingBy(
                Person::getJob,
                Collectors.filtering(
                    p -> p.getAge() > 30,
                    Collectors.mapping(Person::getName, Collectors.toList())
                )
            ));
        System.out.println("Names of experienced people by job: " + experiencedNamesByJob);
        System.out.println();
    }
    
    /**
     * flatMapping() - Flattens and maps elements before collecting (Java 9+)
     * Similar to flatMap() but as a downstream collector
     */
    private static void demonstrateFlatMapping(List<Person> people) {
        System.out.println("=== Collectors.flatMapping() ===");
        
        // Add skills to our Person data
        List<PersonWithSkills> skillfulPeople = List.of(
            new PersonWithSkills("Alice", 25, "Engineer", List.of("Java", "Python", "SQL")),
            new PersonWithSkills("Bob", 30, "Manager", List.of("Leadership", "Planning")),
            new PersonWithSkills("Charlie", 35, "Engineer", List.of("Java", "JavaScript", "React"))
        );
        
        // Group by job, collect all skills as a flat set
        Map<String, Set<String>> skillsByJob = skillfulPeople.stream()
            .collect(Collectors.groupingBy(
                PersonWithSkills::getJob,
                Collectors.flatMapping(
                    p -> p.getSkills().stream(),
                    Collectors.toSet()
                )
            ));
        System.out.println("Skills by job: " + skillsByJob);
        
        // Count total skills per job
        Map<String, Long> skillCountByJob = skillfulPeople.stream()
            .collect(Collectors.groupingBy(
                PersonWithSkills::getJob,
                Collectors.flatMapping(
                    p -> p.getSkills().stream(),
                    Collectors.counting()
                )
            ));
        System.out.println("Total skill count by job: " + skillCountByJob);
        System.out.println();
    }
    
    /**
     * collectingAndThen() - Applies a finishing transformation to the collected result
     * Useful for post-processing the collector result
     */
    private static void demonstrateCollectingAndThen(List<String> names, List<Integer> numbers) {
        System.out.println("=== Collectors.collectingAndThen() ===");
        
        // Collect to list and make it unmodifiable
        List<String> unmodifiableNames = names.stream()
            .collect(Collectors.collectingAndThen(
                Collectors.toList(),
                Collections::unmodifiableList
            ));
        System.out.println("Unmodifiable names: " + unmodifiableNames);
        
        // Find max and convert Optional to string message
        String maxNumberMessage = numbers.stream()
            .collect(Collectors.collectingAndThen(
                Collectors.maxBy(Integer::compareTo),
                opt -> opt.map(n -> "Max number is: " + n)
                         .orElse("No numbers found")
            ));
        System.out.println(maxNumberMessage);
        
        // Group by length and get the size of the largest group
        Integer maxGroupSize = names.stream()
            .collect(Collectors.collectingAndThen(
                Collectors.groupingBy(
                    String::length,
                    Collectors.counting()
                ),
                map -> map.values().stream()
                         .mapToInt(Long::intValue)
                         .max()
                         .orElse(0)
            ));
        System.out.println("Size of largest group by name length: " + maxGroupSize);
        System.out.println();
    }
    
    /**
     * teeing() - Combines results of two collectors (Java 12+)
     * 
     * SIGNATURE:
     * teeing(Collector<T, ?, R1> downstream1, Collector<T, ?, R2> downstream2, BiFunction<R1, R2, R> merger) -> Collector<T, ?, R>
     * 
     * PARAMETERS:
     * - downstream1: First collector to apply to the stream
     * - downstream2: Second collector to apply to the stream
     * - merger: BiFunction to combine results from both collectors
     * RETURNS: Combined result from both collectors
     * 
     * Applies both collectors to the same stream and combines results
     */
    private static void demonstrateAllTeeing(List<Integer> numbers) {
        System.out.println("=== Collectors.teeing() - SIGNATURE: teeing(Collector<T,?,R1>, Collector<T,?,R2>, BiFunction<R1,R2,R>) ===");
        
        // Get both min and max in one pass
        MinMax minMax = numbers.stream()
            .collect(Collectors.teeing(
                Collectors.minBy(Integer::compareTo),    // first collector
                Collectors.maxBy(Integer::compareTo),    // second collector
                (min, max) -> new MinMax(               // combiner function
                    min.orElse(0), 
                    max.orElse(0)
                )
            ));
        System.out.println("Min: " + minMax.min() + ", Max: " + minMax.max());
        
        // Count and average in one pass
        CountAndAverage countAndAvg = numbers.stream()
            .collect(Collectors.teeing(
                Collectors.counting(),
                Collectors.averagingDouble(Integer::doubleValue),
                CountAndAverage::new
            ));
        System.out.println("Count: " + countAndAvg.count() + ", Average: " + countAndAvg.average());
        
        // Partition into even/odd and get their sums
        EvenOddSums sums = numbers.stream()
            .collect(Collectors.teeing(
                Collectors.filtering(n -> n % 2 == 0, Collectors.summingInt(Integer::intValue)),
                Collectors.filtering(n -> n % 2 == 1, Collectors.summingInt(Integer::intValue)),
                EvenOddSums::new
            ));
        System.out.println("Even sum: " + sums.evenSum() + ", Odd sum: " + sums.oddSum());
        System.out.println();
    }
    
    // Helper classes
    static class Person {
        private final String name;
        private final int age;
        private final String job;
        
        public Person(String name, int age, String job) {
            this.name = name;
            this.age = age;
            this.job = job;
        }
        
        public String getName() { return name; }
        public int getAge() { return age; }
        public String getJob() { return job; }
        
        @Override
        public String toString() {
            return name + "(" + age + ")";
        }
    }
    
    static class PersonWithSkills extends Person {
        private final List<String> skills;
        
        public PersonWithSkills(String name, int age, String job, List<String> skills) {
            super(name, age, job);
            this.skills = skills;
        }
        
        public List<String> getSkills() { return skills; }
    }
    
    record MinMax(int min, int max) {}
    record CountAndAverage(long count, double average) {}
    record EvenOddSums(int evenSum, int oddSum) {}
}