package yr2016.exercices;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Ex24 {

    public static void solution() throws IOException {
        try {
            partOne();
            partTwo();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2016/ex24.txt")));
    }

    private static void partOne() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n\n---------------------------- 2016: Exercise 24 - 1 ----------------------------\n");
        String[] input = getInput().replace("\r", "").split("\n");
        Cell[][] map = new Cell[input.length][input[0].length()];

        LinkedList<Cell> targets = new LinkedList<>();

        for (int i = 0; i < input.length; i++) {
            String row = input[i];
            for (int j = 0; j < row.length(); j++) {
                final char c = row.charAt(j);
                Cell cell = new Cell(c, i, j);
                map[i][j] = cell;
                if (cell.type == CellType.TARGET)
                    targets.add(cell);
            }
        }

        int coun = 0;
        for (Cell[] cells : map) {
            for (Cell cell : cells) {
                if (cell.type == CellType.WALL)
                    coun++;
            }
        }
        System.out.println(coun);
        reduceMap(map);

        coun = 0;
        for (Cell[] cells : map) {
            for (Cell cell : cells) {
                if (cell.type == CellType.WALL)
                    coun++;
                System.out.print(cell.value + " ");
            }
            System.out.println();
        }
        System.out.println(coun);

        Map<Character, Set<Shortest>> shortestMap = new HashMap<>();

        for (Cell target : targets) {
            shortestMap.put(target.value, new HashSet<>());
        }
        Cell start;
        while (targets.size() > 0) {
            start = targets.poll();
            List<Shortest> shortest = shortestPaths(start, targets, map);
            shortestMap.get(start.value).addAll(shortest);
            for (Shortest s : shortest) {
                shortestMap.get(s.cell.value).add(new Shortest(start, s.distance));
            }
        }

        int result = Integer.MAX_VALUE;
//        shortestMap.values().stream().flatMap(Collection::stream).map()
//        final Collection<Set<Shortest>> values = shortestMap.values();
//        for (Set<Shortest> value : values) {
//            for (Shortest shortest : value) {
//
//            }
//            for (Set<Shortest> value2 : values) {
//                value
//            }
//        }
        System.out.println();
        for (Character key : shortestMap.keySet()) {
            System.out.println("\n EMPEZANDO POR " + key);
            Set<Character> used = new HashSet<>();
            int aux = 0;
            Set<Shortest> currentList = shortestMap.get(key);
            used.add(key);
            Character lastValue = key;
            while (used.size() != shortestMap.size()) {
                final Shortest shortest = currentList.stream().filter(s -> !used.contains(s.cell.value)).min(Comparator.comparingInt(o -> o.distance)).get();
                used.add(shortest.cell.value);
                aux += shortest.distance;
                currentList = shortestMap.get(shortest.cell.value);
                System.out.println(lastValue + " ---- " + shortest.distance + " ----> " + shortest.cell.value + ". Total: " + aux);
                lastValue = shortest.cell.value;
            }
            System.out.println(aux);
        }


        System.out.println("Solution: " + result);
    }

    private static void partTwo() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n---------------------------- 2016: Exercise 24 - 2 ----------------------------\n");
    }

    private static void reduceMap(Cell[][] map) {
        boolean anyChange = true;

        while (anyChange) {
            anyChange = false;
            for (int x = 1; x < map.length - 1; x++) {
                for (int y = 1; y < map[x].length - 1; y++) {
                    final Cell cell = map[x][y];
                    if (cell.type == CellType.PATH) {
                        int wallCount = countWall(map, x, y);
                        if (wallCount >= 3) {
                            cell.value = '#';
                            cell.type = CellType.WALL;
                            anyChange = true;
                        }
                    }
                    if (cell.type == CellType.WALL) {
                        int wallCount = countWall(map, x, y);
                        if (map[x + 1][y + 1].type == CellType.WALL)
                            wallCount++;
                        if (map[x - 1][y + 1].type == CellType.WALL)
                            wallCount++;
                        if (map[x + 1][y - 1].type == CellType.WALL)
                            wallCount++;
                        if (map[x - 1][y - 1].type == CellType.WALL)
                            wallCount++;

                        if (wallCount == 0) {
                            int pathCount = 0;
                            int targetCount = 0;
                            for (int i = x - 2; i <= x + 2; i++) {
                                for (int j = y - 2; j <= y + 2; j++) {
                                    if (map[i][j].type == CellType.PATH) {
                                        pathCount++;
                                    }
                                    if (map[i][j].type == CellType.TARGET) {
                                        targetCount++;
                                    }
                                }
                            }
                            if (targetCount == 0 && pathCount == 9) {
                                anyChange = true;
                                for (int i = x - 1; i <= x + 1; i++) {
                                    for (int j = y - 1; j <= y + 1; j++) {
                                        makeWall(map, i, j);
                                    }
                                }
                            }
                            if (targetCount == 0 && pathCount >= 10) {
                                boolean closed = false;
                                for (int i = x - 1; i <= x + 1; i += 2) {
                                    for (int j = y - 1; j <= y + 1; j += 2) {
                                        int count = countWall(map, i, j);
                                        if (count == 2 && !closed) {
                                            closed = true;
                                            makeWall(map, i, j);
                                            makeWall(map, i, j + 1);
                                            makeWall(map, i, j - 1);
                                            makeWall(map, i - 1, j);
                                            makeWall(map, i + 1, j);
                                            anyChange = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static void makeWall(Cell[][] map, int x, int y) {
        map[x][y].value = '#';
        map[x][y].type = CellType.WALL;
    }

    private static int countWall(Cell[][] map, int x, int y) {
        int wallCount = 0;
        if (map[x - 1][y].type == CellType.WALL)
            wallCount++;
        if (map[x + 1][y].type == CellType.WALL)
            wallCount++;
        if (map[x][y - 1].type == CellType.WALL)
            wallCount++;
        if (map[x][y + 1].type == CellType.WALL)
            wallCount++;
        return wallCount;
    }


    private static List<Shortest> shortestPaths(Cell init, LinkedList<Cell> targets, Cell[][] map) {
        List<Shortest> shortlists = new ArrayList<>();
        List<Move> nextMoves = new ArrayList<>();
        nextMoves.add(new Move(init, new HashSet<>()));
        int count = 0;
//        Cell found = null;
        Set<Cell> foundList = new HashSet<>();
        while (shortlists.size() != targets.size()) {
            List<Move> aux = new ArrayList<>();
            for (int i = 0; i < nextMoves.size(); i++) {
                Move move = nextMoves.get(i);
                int x = move.from.x;
                int y = move.from.y;

                move.visited.add(move.from);

                final Cell down = map[x - 1][y];
                if (down.type != CellType.WALL && !move.visited.contains(down)) {
                    if (targets.contains(down)) {
                        foundList.add(down);
                    } else {
                        Move next = new Move(down, move.visited);
                        aux.add(next);
                    }
                }

                final Cell up = map[x + 1][y];
                if (up.type != CellType.WALL && !move.visited.contains(up)) {
                    if (targets.contains(up)) {
                        foundList.add(up);
                    } else {
                        Move next = new Move(up, move.visited);
                        aux.add(next);
                    }
                }

                final Cell left = map[x][y - 1];
                if (left.type != CellType.WALL && !move.visited.contains(left)) {
                    if (targets.contains(left)) {
                        foundList.add(left);
                    } else {
                        Move next = new Move(left, move.visited);
                        aux.add(next);
                    }
                }

                final Cell right = map[x][y + 1];
                if (right.type != CellType.WALL && !move.visited.contains(right)) {
                    if (targets.contains(right)) {
                        foundList.add(right);
                    } else {
                        Move next = new Move(right, move.visited);
                        aux.add(next);
                    }
                }
                if (aux.size() == 0 && i == nextMoves.size() - 1) {
                    System.out.print("");
                }
            }
            nextMoves = aux;
            count++;
            for (Cell found : foundList) {
                System.out.println("Desde el " + init.value + " al " + found.value + " hay " + count);
                shortlists.add(new Shortest(found, count));
            }
            foundList.clear();
        }
        return shortlists;
    }

    private record Shortest(Cell cell, int distance) {
    }

    private record Move(Cell from, Set<Cell> visited) {
    }

    private enum CellType {
        WALL, TARGET, PATH
    }

    private static class Cell {
        private CellType type;
        private Character value;
        private final int x;
        private final int y;

        public Cell(char value, int x, int y) {
            this.value = value;
            this.x = x;
            this.y = y;
            switch (value) {
                case '#' -> type = CellType.WALL;
                case '.' -> type = CellType.PATH;
                default -> type = CellType.TARGET;
            }
        }


    }
}
