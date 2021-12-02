import console.ConsoleUI;

import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) {
        long startAt = System.currentTimeMillis();
        try {
            String className = ConsoleUI.getProblemPath();
            Method method = Class.forName(className).getDeclaredMethod("solution");
            method.invoke(Class.forName(className).getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            System.out.println("Error llamando a la clase o al método");
            e.printStackTrace();
        }
        long endAt = System.currentTimeMillis();
        System.out.printf("\nTime taken: %d milis%n\n", endAt - startAt);
    }
}
