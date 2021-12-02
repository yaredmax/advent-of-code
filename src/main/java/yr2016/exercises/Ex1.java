package yr2016.exercises;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Ex1 {
    public static void solution() throws IOException {
        partOne();
        partTwo();
    }

    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2016/ex1.txt")));
    }

    private static void partOne() throws IOException {
        System.out.println("\n\n---------------------------- 2016: Exercise 1 - 1 ----------------------------\n");

        String[] instructions = getInput().replace("\n", "").replace("\r", "").split(", ");

        CardinalPoint north = new CardinalPoint();
        CardinalPoint east = new CardinalPoint();
        CardinalPoint south = new CardinalPoint();
        CardinalPoint west = new CardinalPoint();

        north.next = east;
        north.previous = west;
        north.facing = Facing.NORTH;

        east.next = south;
        east.previous = north;
        east.facing = Facing.EAST;

        south.next = west;
        south.previous = east;
        south.facing = Facing.SOUTH;

        west.next = north;
        west.previous = south;
        west.facing = Facing.WEST;

        int n = 0;
        int s = 0;
        int e = 0;
        int w = 0;

        CardinalPoint actual = north;

        for (String instruction : instructions) {
            if (instruction.charAt(0) == 'R') {
                actual = actual.next;
            } else {
                actual = actual.previous;
            }

            int move = Integer.parseInt(instruction.substring(1));

            switch (actual.facing) {
                case NORTH -> n += move;
                case EAST -> e += move;
                case SOUTH -> s += move;
                case WEST -> w += move;
            }
        }

        int result = Math.abs(n - s) + Math.abs(e - w);

        System.out.println("Solution: " + result);
    }

    private static void partTwo() throws IOException {
        System.out.println("\n---------------------------- 2016: Exercise 1 - 2 ----------------------------\n");

        String[] instructions = getInput().replace("\n", "").replace("\r", "").split(", ");

        int max = 1024;

        boolean[][] map = new boolean[max][max];

        CardinalPoint north = new CardinalPoint();
        CardinalPoint east = new CardinalPoint();
        CardinalPoint south = new CardinalPoint();
        CardinalPoint west = new CardinalPoint();

        north.next = east;
        north.previous = west;
        north.facing = Facing.NORTH;

        east.next = south;
        east.previous = north;
        east.facing = Facing.EAST;

        south.next = west;
        south.previous = east;
        south.facing = Facing.SOUTH;

        west.next = north;
        west.previous = south;
        west.facing = Facing.WEST;

        CardinalPoint actual = north;

        int x = 1024 / 2;
        int y = 1024 / 2;

        map[x][y] = true;

        int j = 0;

        boolean stop = false;
        while (j < instructions.length && !stop) {
            String instruction = instructions[j];
            if (instruction.charAt(0) == 'R') {
                actual = actual.next;
            } else {
                actual = actual.previous;
            }

            int move = Integer.parseInt(instruction.substring(1));

            int i = 0;
            while (i < move && !stop) {
                switch (actual.facing) {
                    case NORTH -> {
                        if (!map[x - 1][y]) {
                            x--;
                            map[x][y] = true;
                        } else
                            stop = true;
                    }
                    case EAST -> {
                        if (!map[x][y + 1]) {
                            y++;
                            map[x][y] = true;
                        } else
                            stop = true;

                    }
                    case SOUTH -> {
                        if (!map[x + 1][y]) {
                            x++;
                            map[x][y] = true;
                        } else
                            stop = true;
                    }
                    case WEST -> {
                        if (!map[x][y - 1]) {
                            y--;
                            map[x][y] = true;
                        } else
                            stop = true;

                    }
                }
                i++;
            }
            j++;
        }

        int result = Math.abs(x - 1024 / 2) + Math.abs(y - 1024 / 2) - 1;

        System.out.println("Solution: " + result);
    }

    private enum Facing {
        NORTH, EAST, SOUTH, WEST
    }

    private static class CardinalPoint {
        private Facing facing;
        private CardinalPoint next;
        private CardinalPoint previous;
    }

}
