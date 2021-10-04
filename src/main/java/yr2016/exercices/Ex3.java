package yr2016.exercices;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Ex3 {
    public static void solution() throws IOException {
        partOne();
        partTwo();
    }


    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2016/ex3.txt")));
    }


    public static void partOne() throws IOException {
        System.out.println("\n\n---------------------------- 2016: Exercise 3 - 1 ----------------------------\n");

        String[] triangles = getInput().replace("\r", "").split("\n");

        int result = 0;
        for (String triangle : triangles) {
            final List<Integer> sides = Arrays.stream(triangle.trim().split("\\s+")).map(Integer::parseInt).collect(Collectors.toList());
            int max = sides.stream().max(Integer::compareTo).orElse(0);
            int sum = sides.stream().reduce(Integer::sum).orElse(0);
            if (sum - max > max) {
                result++;
            }
        }

        System.out.println("Solution: " + result);
    }

    public static void partTwo() throws IOException {
        System.out.println("\n\n---------------------------- 2016: Exercise 3 - 2 ----------------------------\n");

        String[][] input = Arrays.stream(getInput().replace("\r", "").split("\n")).map(line -> line.trim().split("\\s+")).collect(Collectors.toList()).toArray(new String[][]{});

        Integer[][] triangles = new Integer[input.length][3];

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < 3; j++) {
                int pos = (i / 3) + input.length / 3 * j;
                triangles[pos][i % 3] = Integer.parseInt(input[i][j]);
            }
        }

        int result = 0;
        for (Integer[] triangle : triangles) {
            final List<Integer> sides = Arrays.stream(triangle).collect(Collectors.toList());
            int max = sides.stream().max(Integer::compareTo).orElse(0);
            int sum = sides.stream().reduce(Integer::sum).orElse(0);
            if (sum - max > max) {
                result++;
            }
        }

        System.out.println("Solution: " + result);
    }
}
