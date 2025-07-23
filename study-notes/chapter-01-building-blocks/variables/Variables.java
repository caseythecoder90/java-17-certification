

public class Variables {

    // class variable
    public static final int TOTAL = 10;

    // instance variables
    private String name;
    private int age;


    public Variables(String name, int age) {
        this.name = name;
        this.age = age;

        String favoriteColor = "blue";
        double height = 6.5;
    }

    public String getName() { return name; }
    public int getAge() { return age; }

    public static void main(String... argumentos) {
        Variables variable = new Variables("Casey Quinn", 35);
        System.out.println(variable.getAge());
        System.out.println(variable.getName());
    }



}