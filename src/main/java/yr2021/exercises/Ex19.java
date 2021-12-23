package yr2021.exercises;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("SuspiciousNameCombination")
public class Ex19 {

    private static List<Scanner> scanners = new ArrayList<>();

    public static void solution() throws IOException {
        partOne();
        partTwo();
    }

    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2021/ex19.txt")));
    }

    private static void partOne() throws IOException {
        System.out.println("\n\n---------------------------- 2021: Exercise 19 - 1 ----------------------------\n");
        if (scanners.isEmpty()) {
            scanners = findScanners();
        }
        Set<Coordinate> coordinates = scanners.stream().flatMap(scanner -> scanner.coordinates.stream()).collect(Collectors.toSet());

        System.out.println("Solution: " + coordinates.size());
    }

    private static void partTwo() throws IOException {
        System.out.println("\n---------------------------- 2021: Exercise 19 - 2 ----------------------------\n");
        if (scanners.isEmpty()) {
            scanners = findScanners();
        }

        int maxDistance  = 0;
        for (int i = 0; i < scanners.size(); i++) {
            Scanner a = scanners.get(i);
            for (int j = i; j < scanners.size(); j++) {
                Scanner b = scanners.get(j);
                final int aux = Math.abs(a.position.x - b.position.x) + Math.abs(a.position.y - b.position.y) + Math.abs(a.position.z - b.position.z);
                if (aux > maxDistance) {
                    maxDistance = aux;
                }
            }
        }
        System.out.println("Solution: " + maxDistance);
    }

    private static List<Scanner> findScanners() throws IOException {
        String[] input = getInput().replace("\r", "").split("\n\n");
        List<Scanner> scanners = new ArrayList<>();
        for (String in : input) {
            final String[] split = in.split("\n");
            int idIndex = split[0].indexOf("--- scanner ") + "--- scanner ".length();
            int id = Integer.parseInt(split[0].substring(idIndex, split[0].indexOf(" ", idIndex)));
            Scanner sc = new Scanner();
            sc.id = id;
            if (id == 0) {
                sc.position = new Coordinate(0, 0, 0);
            }
            for (int i = 1; i < split.length; i++) {
                final Coordinate coordinate = toCoordinate(split[i]);
                sc.coordinates.add(coordinate);
            }
            scanners.add(sc);
        }

        List<Scanner> notRotated = scanners.stream().filter(scanner -> scanner.id != 0).collect(Collectors.toList());
        List<Scanner> rotated = scanners.stream().filter(scanner -> scanner.id == 0).collect(Collectors.toList());

        final Map<Integer, Function<List<Coordinate>, List<Coordinate>>> rotations = getRotations();

        while (!notRotated.isEmpty()) {
            List<Scanner> rotatedAux = new ArrayList<>();
            for (final Scanner a : rotated) {
                List<Scanner> notRotatedAux = new ArrayList<>();
                for (final Scanner b : notRotated) {
                    Coordinate bPosition = check(a.coordinates, b.coordinates);
                    int rotation = 1;
                    List<Coordinate> aux = new ArrayList<>();
                    while (bPosition == null && rotation <= 24) {
                        aux = rotations.get(rotation).apply(b.coordinates);
                        bPosition = check(a.coordinates, aux);
                        rotation++;
                    }
                    if (bPosition != null) {
                        b.coordinates = aux;
                        b.position = bPosition;
                        b.coordinates = b.coordinates.stream().map(c -> new Coordinate(c.x + b.position.x, c.y + b.position.y, c.z + b.position.z)).collect(Collectors.toList());
                        rotatedAux.add(b);
                    } else {
                        notRotatedAux.add(b);
                    }
                }
                notRotated = notRotatedAux;
            }
            rotated = rotatedAux;
        }

        return scanners;
    }


    private static Map<Integer, Function<List<Coordinate>, List<Coordinate>>> getRotations() {
        Map<Integer, Function<List<Coordinate>, List<Coordinate>>> rotations = new HashMap<>();
        rotations.put(1, coordinates -> coordinates.stream().map(c -> new Coordinate(c.x, c.y, c.z)).collect(Collectors.toList()));
        rotations.put(2, coordinates -> coordinates.stream().map(c -> new Coordinate(c.x, c.z, -c.y)).collect(Collectors.toList()));
        rotations.put(3, coordinates -> coordinates.stream().map(c -> new Coordinate(c.x, -c.y, -c.z)).collect(Collectors.toList()));
        rotations.put(4, coordinates -> coordinates.stream().map(c -> new Coordinate(c.x, -c.z, c.y)).collect(Collectors.toList()));
        rotations.put(5, coordinates -> coordinates.stream().map(c -> new Coordinate(-c.x, c.y, -c.z)).collect(Collectors.toList()));
        rotations.put(6, coordinates -> coordinates.stream().map(c -> new Coordinate(-c.x, -c.z, -c.y)).collect(Collectors.toList()));
        rotations.put(7, coordinates -> coordinates.stream().map(c -> new Coordinate(-c.x, -c.y, c.z)).collect(Collectors.toList()));
        rotations.put(8, coordinates -> coordinates.stream().map(c -> new Coordinate(-c.x, c.z, c.y)).collect(Collectors.toList()));
        rotations.put(9, coordinates -> coordinates.stream().map(c -> new Coordinate(c.y, c.z, c.x)).collect(Collectors.toList()));
        rotations.put(10, coordinates -> coordinates.stream().map(c -> new Coordinate(c.y, c.x, -c.z)).collect(Collectors.toList()));
        rotations.put(11, coordinates -> coordinates.stream().map(c -> new Coordinate(c.y, -c.z, -c.x)).collect(Collectors.toList()));
        rotations.put(12, coordinates -> coordinates.stream().map(c -> new Coordinate(c.y, -c.x, c.z)).collect(Collectors.toList()));
        rotations.put(13, coordinates -> coordinates.stream().map(c -> new Coordinate(-c.y, c.z, -c.x)).collect(Collectors.toList()));
        rotations.put(14, coordinates -> coordinates.stream().map(c -> new Coordinate(-c.y, -c.x, -c.z)).collect(Collectors.toList()));
        rotations.put(15, coordinates -> coordinates.stream().map(c -> new Coordinate(-c.y, -c.z, c.x)).collect(Collectors.toList()));
        rotations.put(16, coordinates -> coordinates.stream().map(c -> new Coordinate(-c.y, c.x, c.z)).collect(Collectors.toList()));
        rotations.put(17, coordinates -> coordinates.stream().map(c -> new Coordinate(c.z, c.x, c.y)).collect(Collectors.toList()));
        rotations.put(18, coordinates -> coordinates.stream().map(c -> new Coordinate(c.z, c.y, -c.x)).collect(Collectors.toList()));
        rotations.put(19, coordinates -> coordinates.stream().map(c -> new Coordinate(c.z, -c.x, -c.y)).collect(Collectors.toList()));
        rotations.put(20, coordinates -> coordinates.stream().map(c -> new Coordinate(c.z, -c.y, c.x)).collect(Collectors.toList()));
        rotations.put(21, coordinates -> coordinates.stream().map(c -> new Coordinate(-c.z, c.x, -c.y)).collect(Collectors.toList()));
        rotations.put(22, coordinates -> coordinates.stream().map(c -> new Coordinate(-c.z, -c.y, -c.x)).collect(Collectors.toList()));
        rotations.put(23, coordinates -> coordinates.stream().map(c -> new Coordinate(-c.z, -c.x, c.y)).collect(Collectors.toList()));
        rotations.put(24, coordinates -> coordinates.stream().map(c -> new Coordinate(-c.z, c.y, c.x)).collect(Collectors.toList()));

        return rotations;
    }

    private static Coordinate check(List<Coordinate> coordinates1, List<Coordinate> coordinates2) {
        Map<Coordinate, Integer> coordinateMap = new HashMap<>();

        int i = 0;
        while (i < coordinates1.size() && coordinateMap.values().stream().noneMatch(matches -> matches >= 12)) {
            Coordinate c1 = coordinates1.get(i);
            for (Coordinate c2 : coordinates2) {
                Coordinate coordinate = new Coordinate(c1.x - c2.x, c1.y - c2.y, c1.z - c2.z);
                coordinateMap.put(coordinate, coordinateMap.getOrDefault(coordinate, 0) + 1);
            }
            i++;
        }
        return coordinateMap.entrySet().stream().filter(entry -> entry.getValue() >= 12).map(Map.Entry::getKey).findFirst().orElse(null);
    }

    private static Coordinate toCoordinate(String in) {
        final String[] split = in.split(",");
        return new Coordinate(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
    }

    private static class Scanner {
        private int id;
        //        private boolean rotated;
        private Coordinate position;
        private List<Coordinate> coordinates = new ArrayList<>();
    }

    private record Coordinate(int x, int y, int z) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return x == that.x && y == that.y && z == that.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }
    }
}
