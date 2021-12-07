package yr2021.exercises;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Ex7 {
    public static void solution() throws IOException {
        partOne();
        partTwo();
    }

    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2021/ex7.txt")));
    }

    private static void partOne() throws IOException {
        System.out.println("\n\n---------------------------- 2021: Exercise 7 - 1 ----------------------------\n");
        List<Integer> crabs = Arrays.stream(getInput().replace("\r", "").replace("\n", "").split(",")).map(Integer::parseInt).collect(Collectors.toList());

        final int max = crabs.stream().max(Integer::compareTo).orElse(0);

        int result = Integer.MAX_VALUE;

        int i = 0;
        while (i <= max) {
            int current = 0;
            int j = 0;
            while (current < result && j < crabs.size()) {
                current += Math.abs(i - crabs.get(j));
                j++;
            }
            if (j == crabs.size() && current < result) {
                result = current;
            }
            i++;
        }

        System.out.println("Solution: " + result);
    }

    private static void partTwo() throws IOException {
        System.out.println("\n---------------------------- 2021: Exercise 7 - 2 ----------------------------\n");
        List<Integer> crabs = Arrays.stream(getInput().replace("\r", "").replace("\n", "").split(",")).map(Integer::parseInt).collect(Collectors.toList());

        final int max = crabs.stream().max(Integer::compareTo).orElse(0);

        int result = Integer.MAX_VALUE;

        int i = 0;
        while (i <= max) {
            int current = 0;
            int j = 0;
            while (current < result && j < crabs.size()) {
                int n = Math.abs(crabs.get(j) - i);
                current += (n * (n + 1)) / 2;
                j++;
            }
            if (j == crabs.size() && current < result) {
                result = current;
            }
            i++;
        }

        System.out.println("Solution: " + result);
    }
}
