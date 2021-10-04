package yr2016;

import yr2016.exercices.Ex8;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();

//        Ex1.solution();
//        Ex2.solution();
//        Ex3.solution();
//        Ex4.solution();
//        Ex5.solution();
//        Ex6.solution();
//        Ex7.solution();
        Ex8.solution();

        long end = System.currentTimeMillis();
        System.out.println("\n" + (end - start) + " millis.");
    }
}
