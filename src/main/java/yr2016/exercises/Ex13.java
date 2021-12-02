package yr2016.exercises;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

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

    private static void partOne() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n\n---------------------------- 2016: Exercise 13 - 1 ----------------------------\n");
        final int designerNumber = Integer.parseInt(getInput().replace("\r", "").replace("\n", ""));

        int targetX = 31, targetY = 39;

        List<Move> moves = new ArrayList<>();
        moves.add(new Move(new Cell(1, 1), new ArrayList<>()));

        int result = 0;
        while (moves.stream().noneMatch(m -> m.cell.x == targetX && m.cell.y == targetY)) {
            List<Move> aux = new ArrayList<>();
            for (Move move : moves) {
                move.visited.add(move.cell);
                final int x = move.cell.x;
                final int y = move.cell.y;
                add(x + 1, y, designerNumber, move, aux);
                if (move.cell.x > 0) {
                    add(x - 1, y, designerNumber, move, aux);
                }
                add(x, y + 1, designerNumber, move, aux);
                if (move.cell.y > 0) {
                    add(x, y - 1, designerNumber, move, aux);
                }
            }
            moves = aux;
            result++;
        }


        System.out.println("Solution: " + result);
    }

    private static void partTwo() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n---------------------------- 2016: Exercise 13 - 2 ----------------------------\n");
        final int designerNumber = Integer.parseInt(getInput().replace("\r", "").replace("\n", ""));
        List<Move> moves = new ArrayList<>();
        moves.add(new Move(new Cell(1, 1), new ArrayList<>()));

        Set<Cell> cells = new HashSet<>();
        int result = 0;
        while (result <= 50) {
            List<Move> aux = new ArrayList<>();
            for (Move move : moves) {
                move.visited.add(move.cell);
                final int x = move.cell.x;
                final int y = move.cell.y;
                add(x + 1, y, designerNumber, move, aux);
                if (move.cell.x > 0) {
                    add(x - 1, y, designerNumber, move, aux);
                }
                add(x, y + 1, designerNumber, move, aux);
                if (move.cell.y > 0) {
                    add(x, y - 1, designerNumber, move, aux);
                }
            }
            moves = aux;
            cells.addAll(moves.stream().map(move -> move.cell).collect(Collectors.toList()));
            result++;
        }

        System.out.println("Solution: " + cells.size());
    }

    private static void add(int x, int y, int designerNumber, Move move, List<Move> aux) {
        if (!isWall(x, y, designerNumber)) {
            if (move.visited.stream().noneMatch(c -> c.x == x && c.y == y))
                aux.add(new Move(new Cell(x, y), new ArrayList<>(move.visited)));
        }
    }

    private static boolean isWall(int x, int y, int designerNumber) {
        int formula = (x * x + 3 * x + 2 * x * y + y + y * y) + designerNumber;
        final String binary = Integer.toBinaryString(formula);
        return binary.chars().filter(value -> Character.getNumericValue(value) == 1).count() % 2 != 0;
    }

    private enum CellType {
        WALL, TARGET, PATH
    }


    private static class Move {
        private Cell cell;
        private List<Cell> visited;

        public Move(Cell cell, List<Cell> visited) {
            this.cell = cell;
            this.visited = visited;
        }
    }

    private static class Cell {
        private final int x;
        private final int y;

        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cell cell = (Cell) o;
            return x == cell.x && y == cell.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

}
