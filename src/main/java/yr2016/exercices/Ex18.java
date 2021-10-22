package yr2016.exercices;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

public class Ex18 {

    public static void solution() throws IOException {
        try {
            partOne();
            partTwo();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2016/ex18.txt")));
    }

    private static void partOne() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n\n---------------------------- 2016: Exercise 18 - 1 ----------------------------\n");
        System.out.println("Solution: " + getResult(40));
    }

    private static void partTwo() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n---------------------------- 2016: Exercise 18 - 2 ----------------------------\n");
        System.out.println("Solution: " + getResult(400000));
    }

    private static int getResult(int rows) throws IOException {
        char[] input = getInput().replace("\r", "").replace("\n", "").toCharArray();
        boolean[][] map = new boolean[rows][input.length];
        int count = 0;
        for (int i = 0; i < input.length; i++) {
            char c = input[i];
            map[0][i] = c == '^';
            if (!map[0][i])
                count++;
        }

        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < map[i].length; j++) {
                boolean center = map[i - 1][j];
                boolean left = j > 0 && map[i - 1][j - 1];
                boolean right = j < map[i].length - 1 && map[i - 1][j + 1];

                map[i][j] =
                        (center && left && !right) ||
                                (center && !left && right) ||
                                (!center && left && !right) ||
                                (!center && !left && right);
                if (!map[i][j])
                    count++;
            }
        }

        return count;
    }
}
