package yr2016.exercices;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Ex2 {
    public static void solution() throws IOException {
        partOne();
        partTwo();
    }


    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2016/ex2.txt")));
    }


    public static void partOne() throws IOException {
        System.out.println("\n\n---------------------------- 2016: Exercise 2 - 1 ----------------------------\n");

        String[] instructions = getInput().replace("\r", "").split("\n");

        String[][] buttons = new String[][]{
                {"1", "2", "3"},
                {"4", "5", "6"},
                {"7", "8", "9"},
        };

        StringBuilder result = new StringBuilder();

        for (String instruction : instructions) {
            int x = 1;
            int y = 1;

            for (char c : instruction.toCharArray()) {
                if (c == 'U' && y > 0) {
                    y--;
                }
                if (c == 'D' && y < 2) {
                    y++;
                }
                if (c == 'R' && x < 2) {
                    x++;
                }
                if (c == 'L' && x > 0) {
                    x--;
                }
            }
            result.append(buttons[y][x]);
        }

        System.out.println("Solution: " + result);
    }

    public static void partTwo() throws IOException {
        System.out.println("\n\n---------------------------- 2016: Exercise 2 - 2 ----------------------------\n");

        String[] instructions = getInput().replace("\r", "").split("\n");

        Map<String, String[]> map = new HashMap<>();

        map.put("1", new String[]{null, null, "3", null});
        map.put("2", new String[]{null, "3", "6", null});
        map.put("3", new String[]{"1", "4", "7", "2"});
        map.put("4", new String[]{null, null, "8", "3"});
        map.put("5", new String[]{null, "6", null, null});
        map.put("6", new String[]{"2", "7", "A", "5"});
        map.put("7", new String[]{"3", "8", "B", "6"});
        map.put("8", new String[]{"4", "9", "C", "7"});
        map.put("9", new String[]{null, null, null, "8"});
        map.put("A", new String[]{"6", "B", null, null});
        map.put("B", new String[]{"7", "C", "D", "A"});
        map.put("C", new String[]{"8", null, null, "B"});
        map.put("D", new String[]{"B", null, null, null});

        StringBuilder result = new StringBuilder();
        String actual = "5";

        for (String instruction : instructions) {
            for (char c : instruction.toCharArray()) {
                int pos = -1;
                if (c == 'U') {
                    pos = 0;
                }
                if (c == 'D') {
                    pos = 2;
                }
                if (c == 'L') {
                    pos = 3;
                }
                if (c == 'R') {
                    pos = 1;
                }

                if (map.get(actual)[pos] != null) {
                    actual = map.get(actual)[pos];
                }
            }
            result.append(actual);
        }

        System.out.println("Solution: " + result);
    }
}
