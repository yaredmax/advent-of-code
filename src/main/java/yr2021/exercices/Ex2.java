package yr2021.exercices;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Ex2 {

    private static final String FORWARD = "forward";
    private static final String DOWN = "down";
    private static final String UP = "up";

    public static void solution() throws IOException {
        partOne();
        partTwo();
    }

    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2021/ex2.txt")));
    }

    private static void partOne() throws IOException {
        System.out.println("\n\n---------------------------- 2021: Exercise 2 - 1 ----------------------------\n");

        String[] course = getInput().replace("\r", "").split("\n");

        int horizontal = 0;
        int depth = 0;

        for (String instruction : course) {
            final String[] ins = instruction.split(" ");

            switch (ins[0]) {
                case UP -> depth -= Integer.parseInt(ins[1]);
                case DOWN -> depth += Integer.parseInt(ins[1]);
                case FORWARD -> horizontal += Integer.parseInt(ins[1]);
            }
        }

        int result = horizontal * depth;


        System.out.println("Solution: " + result);
    }

    private static void partTwo() throws IOException {
        System.out.println("\n---------------------------- 2021: Exercise 2 - 2 ----------------------------\n");

        String[] course = getInput().replace("\r", "").split("\n");

        int horizontal = 0;
        int depth = 0;
        int aim = 0;

        for (String instruction : course) {
            final String[] ins = instruction.split(" ");

            switch (ins[0]) {
                case UP -> aim -= Integer.parseInt(ins[1]);
                case DOWN -> aim += Integer.parseInt(ins[1]);
                case FORWARD -> {
                    int x = Integer.parseInt(ins[1]);
                    horizontal += x;
                    depth += aim * x;
                }
            }
        }

        int result = horizontal * depth;

        System.out.println("Solution: " + result);
    }
}
