
public class StreamsPractice {

    public static Optional<Double> average(int... scores) {

        if (scores.length == 0) return Optional.empty();

        int sum = 0;
        for (var score : scores) sum += score;

        return Optional.of(sum);

    }

    public static Optional<Double> averageStreams(int... scores) {
        return Optional.of(() -> Arrays.stream(scores).average().getAsDouble());
    }
}