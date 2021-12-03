import console.ConsoleUI;

import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) {
        try {
            String className = ConsoleUI.getProblemPath();
            long startAt = System.currentTimeMillis();
            Method method = Class.forName(className).getDeclaredMethod("solution");
            method.invoke(Class.forName(className).getDeclaredConstructor().newInstance());
            long endAt = System.currentTimeMillis();
            System.out.printf("\nTime taken: %d milis%n\n", endAt - startAt);
        } catch (Exception e) {
            System.out.println("Error llamando a la clase o al método");
            e.printStackTrace();
        }
    }
}
