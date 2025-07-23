package packageb;

import packagea.ClassA;
import packagea.Swan;
import java.util.Random;


public class ClassB {

	public static void main(String... argumentos) {
		System.out.println("Hello, let's find the future wife");
		ClassA a = new ClassA();
		System.out.println(a.getFutureWife());

		System.out.println("If you made it this far then you found your future wife");
		Random random = new Random();
		int num = random.nextInt(100);
		for (int i = 0; i < num; i++) {
			System.out.println("i equals " + i);
		}

		Swan swan = new Swan();
		System.out.println("Number of eggs: " + swan.numberEggs);




	}
}

