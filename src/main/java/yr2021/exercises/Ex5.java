package yr2021.exercises;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Ex5 {
    public static void solution() throws IOException {
        partOne();
        partTwo();
    }

    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2021/ex5.txt")));
    }

    private static void partOne() throws IOException {
        System.out.println("\n\n---------------------------- 2021: Exercise 5 - 1 ----------------------------\n");
        printResult(false);
    }

    private static void partTwo() throws IOException {
        System.out.println("\n---------------------------- 2021: Exercise 5 - 2 ----------------------------\n");
        printResult(true);
    }

    private static void printResult(boolean checkDiagonals) throws IOException {
        String[] coordinates = getInput().replace("\r", "").split("\n");

        int[][] map = new int[1000][1000];

        for (String coordinate : coordinates) {
            final String[] coordinateSplit = coordinate.split(" -> ");
            final String[] c1 = coordinateSplit[0].split(",");
            final String[] c2 = coordinateSplit[1].split(",");

            int x1 = Integer.parseInt(c1[0]);
            int y1 = Integer.parseInt(c1[1]);
            int x2 = Integer.parseInt(c2[0]);
            int y2 = Integer.parseInt(c2[1]);

            if (checkDiagonals || (x1 == x2 || y1 == y2)) {
                map[x1][y1]++;
                while (x1 != x2 || y1 != y2) {
                    if (x1 > x2)
                        x1--;
                    else if (x1 < x2)
                        x1++;
                    if (y1 > y2)
                        y1--;
                    else if (y1 < y2)
                        y1++;
                    map[x1][y1]++;
                }
            }
        }

       int result = Arrays.stream(map).flatMapToInt(Arrays::stream).reduce((a, b) -> b > 1 ? a = a + 1 : a).orElse(0);

        System.out.println("Solution: " + result);
    }
}
