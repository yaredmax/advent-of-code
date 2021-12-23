package yr2021.exercises;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Ex20 {
    public static void solution() throws IOException {
        partOne();
        partTwo();
    }

    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2021/ex20.txt")));
    }

    private static void partOne() throws IOException {
        System.out.println("\n\n---------------------------- 2021: Exercise 20 - 1 ----------------------------\n");
        getResult(2);
    }

    private static void partTwo() throws IOException {
        System.out.println("\n---------------------------- 2021: Exercise 20 - 2 ----------------------------\n");
        getResult(50);
    }

    private static void getResult(int times) throws IOException {
        String[] input = getInput().replace("\r", "").split("\n\n");
        char[] inputAlgorithm = input[0].toCharArray();
        int[] algorithm = new int[inputAlgorithm.length];
        for (int i = 0; i < inputAlgorithm.length; i++) {
            algorithm[i] = inputAlgorithm[i] == '#' ? 1 : 0;
        }

        String[] imageString = input[1].split("\n");

        int[][] map = new int[imageString.length + (times * 2)][imageString[0].length() + (times * 2)];

        for (int i = 0; i < imageString.length; i++) {
            final char[] chars = imageString[i].toCharArray();
            for (int j = 0; j < chars.length; j++) {
                char pixel = chars[j];
                if (pixel == '#') map[i + times][j + times] = 1;
            }
        }

        int infinity = 0;

        while (times != 0) {
            map = enhance(map, algorithm, infinity);
            if (infinity == 0) {
                infinity = algorithm[0];
            } else {
                infinity = algorithm[algorithm.length - 1];
            }
            times--;
        }
        System.out.println("Solution: " + Arrays.stream(map).flatMapToInt(Arrays::stream).reduce(Integer::sum).orElse(0));
    }

    private static int[][] enhance(int[][] map, int[] algorithm, int infinite) {
        int[][] aux = new int[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                StringBuilder binary = new StringBuilder();
                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        int x = i + k;
                        int y = j + l;
                        if (x < 0 || y < 0 || x >= map.length || y >= map[i].length)
                            binary.append(infinite);
                        else
                            binary.append(map[x][y]);
                    }
                }
                int decimal = Integer.parseInt(binary.toString(), 2);
                aux[i][j] = algorithm[decimal];
            }
        }
        return aux;
    }
}
