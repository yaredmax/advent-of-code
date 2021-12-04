package yr2021;

import yr2021.exercises.Ex4;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();

//        Ex1.solution();
//        Ex2.solution();
//        Ex3.solution();
        Ex4.solution();

        long end = System.currentTimeMillis();
        System.out.println("\n" + (end - start) + " millis.");
    }
}
