package yr2016.exercices;

import utils.ArrayUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

public class Ex8 {

    private static final String RECT = "rect";
    private static final String ROTATE = "rotate";
    private static final String ROW = "row";
    private static final String COLUMN = "column";

    public static void solution() throws IOException {
        try {
            partOne();
            partTwo();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2016/ex8.txt")));
    }


    private static void partOne() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n\n---------------------------- 2016: Exercise 8 - 1 ----------------------------\n");
        String[] instructions = getInput().replace("\r", "").split("\n");

        int wide = 50;
        int tall = 6;

        boolean[][] screen = new boolean[tall][wide];

        for (String instruction : instructions) {
            int actionSpaceIndex = instruction.indexOf(' ');
            final String action = instruction.substring(0, actionSpaceIndex);
            switch (action) {
                case RECT -> {
                    int xIndex = instruction.indexOf('x');
                    int w = Integer.parseInt(instruction.substring(actionSpaceIndex + 1, xIndex));
                    int t = Integer.parseInt(instruction.substring(xIndex + 1));
                    rectAction(screen, w, t);
                }
                case ROTATE -> {
                    int directionSpaceIndex = instruction.indexOf(' ', actionSpaceIndex + 1);
                    final String direction = instruction.substring(actionSpaceIndex + 1, directionSpaceIndex);
                    int startIndex = instruction.indexOf('=');
                    int byIndex = instruction.indexOf(" by ");
                    final int start = Integer.parseInt(instruction.substring(startIndex + 1, byIndex));
                    final int count = Integer.parseInt(instruction.substring(byIndex + 4));
                    rotateAction(screen, direction, start, count);
                }
            }
        }

        int result = 0;
        for (boolean[] row : screen) {
            for (boolean pixel : row) {
                if (pixel)
                    result++;
            }
        }

        System.out.println("Solution: " + result);
    }

    private static void partTwo() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n\n---------------------------- 2016: Exercise 8 - 2 ----------------------------\n");
        String[] instructions = getInput().replace("\r", "").split("\n");

        int wide = 50;
        int tall = 6;

        boolean[][] screen = new boolean[tall][wide];

        for (String instruction : instructions) {
            int actionSpaceIndex = instruction.indexOf(' ');
            final String action = instruction.substring(0, actionSpaceIndex);
            switch (action) {
                case RECT -> {
                    int xIndex = instruction.indexOf('x');
                    int w = Integer.parseInt(instruction.substring(actionSpaceIndex + 1, xIndex));
                    int t = Integer.parseInt(instruction.substring(xIndex + 1));
                    rectAction(screen, w, t);
                }
                case ROTATE -> {
                    int directionSpaceIndex = instruction.indexOf(' ', actionSpaceIndex + 1);
                    final String direction = instruction.substring(actionSpaceIndex + 1, directionSpaceIndex);
                    int startIndex = instruction.indexOf('=');
                    int byIndex = instruction.indexOf(" by ");
                    final int start = Integer.parseInt(instruction.substring(startIndex + 1, byIndex));
                    final int count = Integer.parseInt(instruction.substring(byIndex + 4));
                    rotateAction(screen, direction, start, count);
                }
            }
        }

        ArrayUtils.print(screen, '♥', ' ');
    }

    private static void rectAction(boolean[][] screen, int w, int t) {
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < w; j++) {
                screen[i][j] = true;
            }
        }
    }

    private static void rotateAction(boolean[][] screen, String direction, int start, int count) {
        switch (direction) {
            case ROW -> {
                for (int i = 0; i < count; i++) {
                    final boolean aux = screen[start][screen[start].length - 1];
                    System.arraycopy(screen[start], 0, screen[start], 1, screen[start].length - 1);
                    screen[start][0] = aux;
                }
            }
            case COLUMN -> {
                for (int i = 0; i < count; i++) {
                    final boolean aux = screen[screen.length - 1][start];
                    for (int j = screen.length - 1; j > 0; j--) {
                        screen[j][start] = screen[j - 1][start];
                    }
                    screen[0][start] = aux;
                }
            }
        }
    }
}
