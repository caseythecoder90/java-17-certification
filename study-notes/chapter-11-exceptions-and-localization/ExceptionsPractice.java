import java.io.*;

public class ExceptionsPractice implements AutoCloseable {

    private int num = 3;

    public ExceptionsPractice(int num) {
        this.num = num;
    }

    @Override public void close() {
        System.out.println("Closing: " + num);
    }


    int goHome() {
        
        try {
            System.out.print("1");
            return -1;
        } catch (Exception e) {
            System.out.print("2");
            return -2;
        } finally {
            System.out.print("3");
            return -3;
        }
    }


    public static void main(String... args) {
        ExceptionsPractice practice = new ExceptionsPractice(400);
        System.out.println(practice.goHome());


        try (ExceptionsPractice autoclosed = new ExceptionsPractice(80); ) {
            System.out.println("Inside the try with resources code block and find it cosy in here");
        }


        System.out.println("Outside of the try with resources code block now");
    }
}