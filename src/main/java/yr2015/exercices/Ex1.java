package yr2015.exercices;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Ex1 {
    public static void solution() throws IOException {
        partOne();
        partTwo();
    }

    private static void partOne() throws IOException {
        System.out.println("\n\n---------------------------- 2015: Exercise 1 - 1----------------------------\n");

        String instructions = new String(Files.readAllBytes(Paths.get("inputs/yr2015/ex1.txt"))).replace("\n", "").replace("\r", "");

        int up = instructions.replace(")", "").length();
        int down = instructions.length() - up;

        System.out.println("Solution: " + (up - down));
    }

    private static void partTwo() throws IOException {
        System.out.println("\n---------------------------- 2015: Exercise 1 - 2 ----------------------------\n");
        char[] instructions = new String(Files.readAllBytes(Paths.get("inputs/yr2015/ex1.txt"))).replace("\n", "").replace("\r", "").toCharArray();

        int i = 0;
        int position = 0;
        int floor = 0;
        while (position == 0 && i < instructions.length) {
            if (instructions[i] == '(') {
                floor++;
            } else {
                floor--;
            }
            if (floor < 0) {
                position = i + 1;
            }
            i++;
        }

        System.out.println("Solution: " + position);
    }
}
