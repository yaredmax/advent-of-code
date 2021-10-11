package yr2016.exercices;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

public class Ex16 {
    public static void solution() throws IOException {
        try {
            partOne();
            partTwo();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2016/ex16.txt")));
    }

    private static void partOne() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n\n---------------------------- 2016: Exercise 16 - 1 ----------------------------\n");
        StringBuilder initialState = new StringBuilder(getInput().replace("\r", "").replace("\n", ""));
        int length = 272;
        fillDisk(initialState, length);

        System.out.println("Solution: " + calculateChecksum(initialState));
    }

    private static void partTwo() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n\n---------------------------- 2016: Exercise 16 - 2 ----------------------------\n");
        StringBuilder initialState = new StringBuilder(getInput().replace("\r", "").replace("\n", ""));
        int length = 35651584;
        fillDisk(initialState, length);

        System.out.println("Solution: " + calculateChecksum(initialState));
    }

    private static void fillDisk(StringBuilder initialState, int length) {
        while (initialState.length() < length) {
            StringBuilder b = new StringBuilder();
            for (int i = 0; i < initialState.length(); i++) {
                if (initialState.charAt(i) == '0')
                    b.append('1');
                else
                    b.append('0');
            }
            b.reverse();

            initialState.append("0").append(b);
        }

        initialState.delete(length, initialState.length());
    }

    private static StringBuilder calculateChecksum(StringBuilder initialState) {
        StringBuilder result = new StringBuilder(initialState);
        while (result.length() % 2 == 0) {
            StringBuilder aux = new StringBuilder();
            for (int i = 0; i < result.length() - 1; i += 2) {
                String pair = result.substring(i, i + 2);
                if (pair.equals("00") || pair.equals("11"))
                    aux.append("1");
                else
                    aux.append("0");
            }
            result = aux;
        }

        return result;
    }
}
