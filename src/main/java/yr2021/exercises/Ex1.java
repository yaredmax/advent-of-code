package yr2021.exercises;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Ex1 {
    public static void solution() throws IOException {
        partOne();
        partTwo();
    }

    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2021/ex1.txt")));
    }

    private static void partOne() throws IOException {
        System.out.println("\n\n---------------------------- 2021: Exercise 1 - 1 ----------------------------\n");

        List<Integer> sonar = Arrays.stream(getInput().replace("\r", "").split("\n")).map(Integer::parseInt).collect(Collectors.toList());

        int result = 0;

        for (int i = 1; i < sonar.size(); i++) {
            int curr = sonar.get(i);
            int prev = sonar.get(i - 1);
            if (curr > prev)
                result++;
        }

        System.out.println("Solution: " + result);
    }

    private static void partTwo() throws IOException {
        System.out.println("\n---------------------------- 2021: Exercise 1 - 2 ----------------------------\n");

        List<Integer> sonar = Arrays.stream(getInput().replace("\r", "").split("\n")).map(Integer::parseInt).collect(Collectors.toList());

        int result = 0;
        int prev = sonar.get(0) + sonar.get(1) + sonar.get(2);
        for (int i = 0; i < sonar.size() - 2; i++) {
            int a = sonar.get(i);
            int b = sonar.get(i + 1);
            int c = sonar.get(i + 2);
            int curr = a + b + c;
            if (curr > prev)
                result++;
            prev = curr;
        }

        System.out.println("Solution: " + result);
    }
}
