package yr2016.exercices;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

public class Ex13 {

    public static void solution() throws IOException {
        try {
            partOne();
            partTwo();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2016/ex13.txt")));
    }

    public static void partOne() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n\n---------------------------- 2016: Exercise 13 - 1 ----------------------------\n");
        final int designerNumber = Integer.parseInt(getInput().replace("\r", "").replace("\n", ""));

        int x = 1, y = 1, targetX = 31, targetY = 39;

        boolean[][] labyrinth = new boolean[190][190];

        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth[i].length; j++) {
                labyrinth[i][j] = isWall(i, j, designerNumber);
            }
        }

        labyrinth[x][y] = true;

        int result = path(labyrinth, x, y, targetX, targetY, 0) - 1;

        System.out.println("Solution: " + result);
    }

    public static void partTwo() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n\n---------------------------- 2016: Exercise 13 - 2 ----------------------------\n");
        final int designerNumber = Integer.parseInt(getInput().replace("\r", "").replace("\n", ""));
        int x = 1, y = 1;
        boolean[][] labyrinth = new boolean[99][99];
        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth[i].length; j++) {
                labyrinth[i][j] = isWall(i, j, designerNumber);
            }
        }

        labyrinth[x][y] = true;

        int result = howMany(labyrinth, x, y, 0);


        System.out.println("Solution: " + result);
    }

    private static int path(boolean[][] labyrinth, int x, int y, int targetX, int targetY, int depth) {
        int minCount = Integer.MAX_VALUE - 1;

        boolean[][] copy = labyrinth.clone();

        if (x == targetX && y == targetY) {
            minCount = 0;
        } else {
            for (int i = -1; i <= 1; i += 2) {
                if (x + i >= 0 && x + 1 < labyrinth.length && !labyrinth[x + i][y]) {
                    copy[x + i][y] = true;
                    int count = path(copy, x + i, y, targetX, targetY, depth + 1);
                    if (count < minCount) {
                        minCount = count;
                    }
                }

                if (y + i >= 0 && y + 1 < labyrinth[x].length && !labyrinth[x][y + i]) {
                    copy[x][y + i] = true;
                    int count = path(copy, x, y + i, targetX, targetY, depth + 1);
                    if (count < minCount) {
                        minCount = count;
                    }
                }
            }
        }

        return minCount + 1;
    }

    private static int howMany(boolean[][] labyrinth, int x, int y, int depth) {
        int count = 0;

        System.out.println(depth);
        boolean[][] copy = labyrinth.clone();

        if (depth == 50) {
            count = 1;
        } else {
            for (int i = -1; i <= 1; i += 2) {
                if (x + i >= 0 && x + 1 < labyrinth.length && !labyrinth[x + i][y]) {
                    copy[x + i][y] = true;
                    count += howMany(copy, x + i, y, depth + 1);
                }

                if (y + i >= 0 && y + 1 < labyrinth[x].length && !labyrinth[x][y + i]) {
                    copy[x][y + i] = true;
                    count += howMany(copy, x, y + i, depth + 1);
                }
            }
        }

        return count;
    }


    public static void printWay(boolean[][] lab, char t, char f) {
        for (int i = 0; i < lab.length; i++) {
            boolean[] row = lab[i];
            for (int j = 0; j < row.length; j++) {
                boolean b = row[j];
                if (false) {
                    System.out.print(" O");
                } else {
                    System.out.print(" " + (b ? t : f));
                }
            }
            System.out.println();
        }
    }

    private static boolean isWall(int x, int y, int designerNumber) {
        int formula = (x * x + 3 * x + 2 * x * y + y + y * y) + designerNumber;
        final String binary = Integer.toBinaryString(formula);
        return binary.chars().filter(value -> Character.getNumericValue(value) == 1).count() % 2 != 0;
    }


}
