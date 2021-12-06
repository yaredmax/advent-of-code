package yr2021.exercises;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Ex6 {
    public static void solution() throws IOException {
        partOne();
        partTwo();
    }

    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2021/ex6.txt")));
    }

    private static void partOne() throws IOException {
        System.out.println("\n\n---------------------------- 2021: Exercise 6 - 1 ----------------------------\n");
        getResult(80);
    }

    private static void partTwo() throws IOException {
        System.out.println("\n---------------------------- 2021: Exercise 6 - 2 ----------------------------\n");
        getResult(256);
    }

    private static void getResult(int maxDay) throws IOException {

        String[] initialState = getInput().replace("\r", "").replace("\n", "").split(",");

        Map<Integer, Long> lanternfishMap = new HashMap<>();

        for (String state : initialState) {
            final int lanternfish = Integer.parseInt(state);
            lanternfishMap.put(lanternfish, lanternfishMap.getOrDefault(lanternfish, 0L) + 1);
        }

        int day = 0;

        while (day < maxDay) {
            for (int i = 0; i < lanternfishMap.keySet().size(); i++) {
                lanternfishMap.put(i - 1, lanternfishMap.getOrDefault(i, 0L));
            }
            lanternfishMap.put(8, lanternfishMap.getOrDefault(-1, 0L));
            lanternfishMap.put(6, lanternfishMap.getOrDefault(6, 0L) + lanternfishMap.getOrDefault(-1, 0L));
            lanternfishMap.remove(-1);
            day++;
        }

        System.out.println("Solution: " + lanternfishMap.values().stream().reduce(Long::sum).orElse(0L));
    }
}
