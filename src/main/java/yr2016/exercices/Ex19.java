package yr2016.exercices;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.stream.IntStream;

public class Ex19 {

    public static void solution() throws IOException {
        try {
            partOne();
            partTwo();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2016/ex19.txt")));
    }

    private static void partOne() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n\n---------------------------- 2016: Exercise 19 - 1 ----------------------------\n");
        System.out.println("Solution: " + getResult());
    }

    private static void partTwo() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n\n---------------------------- 2016: Exercise 19 - 2 ----------------------------\n");
        int nElves = Integer.parseInt(getInput().replace("\r", "").replace("\n", ""));
        int[] elves = IntStream.range(1, nElves + 1).toArray();
        boolean haciaAbajo = false;
        while (elves.length != 1) {
            int start = haciaAbajo ? 1 : 0;
            int newLength = elves.length / 2;
            if (elves.length % 2 != 0) {
                newLength += haciaAbajo ? 0 : 1;
                haciaAbajo = !haciaAbajo;
            }
            int[] aux = new int[newLength];
            for (int i = start; i < elves.length; i += 2) {
                aux[i / 2] = elves[i];
            }
            elves = aux;
        }

        System.out.println("Solution: " + elves[0]);
    }

    private static int getResult() throws IOException {
        int nElves = Integer.parseInt(getInput().replace("\r", "").replace("\n", ""));
        int[] elves = IntStream.range(1, nElves + 1).toArray();
        boolean haciaAbajo = false;
        while (elves.length != 1) {
            int start = haciaAbajo ? 1 : 0;
            int newLength = elves.length / 2;
            if (elves.length % 2 != 0) {
                newLength += haciaAbajo ? 0 : 1;
                haciaAbajo = !haciaAbajo;
            }
            int[] aux = new int[newLength];
            for (int i = start; i < elves.length; i += 2) {
                aux[i / 2] = elves[i];
            }
            elves = aux;
        }
        return elves[0];
    }
}
