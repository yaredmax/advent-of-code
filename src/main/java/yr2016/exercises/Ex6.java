package yr2016.exercises;

import utils.ArrayUtils;
import utils.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Ex6 {
    public static void solution() throws IOException {
        try {
            partOne();
            partTwo();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2016/ex6.txt")));
    }

    public static void partOne() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n\n---------------------------- 2016: Exercise 6 - 1 ----------------------------\n");
        char[][] matrix = Arrays.stream(getInput().replace("\r", "").split("\n")).map(String::toCharArray).collect(Collectors.toList()).toArray(new char[][]{});
        matrix = ArrayUtils.rotateMatrix(matrix);
        final String result = Arrays.stream(matrix).map(s -> String.valueOf(StringUtils.mostCommonChar(s))).reduce(String::concat).orElse("");
        System.out.println("Solution: " + result);
    }


    private static void partTwo() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n---------------------------- 2016: Exercise 6 - 2 ----------------------------\n");
        char[][] matrix = Arrays.stream(getInput().replace("\r", "").split("\n")).map(String::toCharArray).collect(Collectors.toList()).toArray(new char[][]{});
        matrix = ArrayUtils.rotateMatrix(matrix);
        final String result = Arrays.stream(matrix).map(s -> String.valueOf(StringUtils.lessCommonChar(s))).reduce(String::concat).orElse("");
        System.out.println("Solution: " + result);
    }

}
