package yr2015;

import yr2015.exercices.Ex1;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();

        Ex1.solution();

        long end = System.currentTimeMillis();
        System.out.println("\n" + (end - start) + " millis.");
    }
}
