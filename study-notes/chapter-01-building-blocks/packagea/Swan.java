package packagea;

public class Swan {
    public int numberEggs; 
    int numberHens;
    public Swan() {
        System.out.println("Inside the swan constructor");
    }
    public static void main(String[] argumentos) {

        System.out.println("Creating a swan");
        Swan swan = new Swan();
        System.out.println("Number of eggs: " + swan.numberEggs);
        swan.numberEggs = 600;
        System.out.println("Number of eggs later: " + swan.numberEggs);


    }
}