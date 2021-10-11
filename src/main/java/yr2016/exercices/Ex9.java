package yr2016.exercices;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

public class Ex9 {

    public static void solution() throws IOException {
        try {
            partOne();
            partTwo();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2016/ex9.txt")));
    }


    private static void partOne() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n\n---------------------------- 2016: Exercise 9 - 1 ----------------------------\n");
        String file = getInput().replace("\r", "").replace("\n", "").replace(" ", "");
        System.out.println("Solution: " + decompress(file, false));
    }

    private static void partTwo() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n\n---------------------------- 2016: Exercise 9 - 2 ----------------------------\n");
        String file = getInput().replace("\r", "").replace("\n", "").replace(" ", "");
        System.out.println("Solution: " + decompress(file, true));
    }

    private static long decompress(String compressed, boolean recursive) {
        long result = 0;
        int startIndex = -1;
        int endIndex = compressed.indexOf('(');
        while (endIndex != -1) {
            String normal = compressed.substring(startIndex + 1, endIndex);
            result += normal.length();
            startIndex = compressed.indexOf(')', startIndex + 1);

            String compression = compressed.substring(endIndex + 1, startIndex);
            int xIndex = compression.indexOf('x');

            int length = Integer.parseInt(compression.substring(0, xIndex));
            int times = Integer.parseInt(compression.substring(xIndex + 1));

            if (recursive) {
                String sub = compressed.substring(startIndex + 1, startIndex + 1 + length);
                result += (decompress(sub, true) * times);
            } else {
                result += ((long) length * times);
            }

            startIndex += length;
            endIndex = compressed.indexOf('(', startIndex + 1);
        }
        String rest = compressed.substring(startIndex + 1);
        result += rest.length();

        return result;
    }
}
