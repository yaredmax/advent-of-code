package yr2016.exercices;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Ex22 {

    private static final String SWAP = "swap";
    private static final String ROTATE = "rotate";
    private static final String REVERSE = "reverse";
    private static final String MOVE = "move";

    private static final String POSITION = "position";
    private static final String LETTER = "letter";

    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private static final String BASED = "based";

    public static void solution() throws IOException {
        try {
            partOne();
            partTwo();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2016/ex22.txt")));
    }

    private static void partOne() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n\n---------------------------- 2016: Exercise 22 - 1 ----------------------------\n");
        List<Disk> disks = getDisks();
        final long result = disks.stream().filter(d1 -> d1.used != 0 && disks.stream().anyMatch(d2 -> !d1.equals(d2) && d1.used <= d2.avail)).count();
        System.out.println("Solution: " + result);
    }

    private static void partTwo() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n---------------------------- 2016: Exercise 22 - 2 ----------------------------\n");
        List<Disk> disks = getDisks();
        int maxX = disks.stream().map(d -> d.x).max(Integer::compareTo).orElse(0);
        int maxY = disks.stream().map(d -> d.y).max(Integer::compareTo).orElse(0);
        Disk wall = null;
        Disk empty = null;
        Disk[][] matrix = new Disk[maxX + 1][maxY + 1];
        disks.forEach(n -> matrix[n.x][n.y] = n);
        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[x].length; y++) {
                Disk n = matrix[x][y];
                if (n.used == 0) {
                    empty = n;
                } else if (n.size > 250) {
                    if (wall == null)
                        wall = matrix[x - 1][y];
                }
            }
        }
        int result = Math.abs(empty.x - wall.x) + Math.abs(empty.y - wall.y);
        result += Math.abs(wall.x - maxX) + wall.y;
        result = result + 5 * (maxX - 1);

        System.out.println("Solution: " + result);
    }

    private static List<Disk> getDisks() throws IOException {
        List<String> input = Arrays.stream(getInput().replace("\r", "").split("\n")).skip(2).collect(Collectors.toList());
        List<Disk> disks = new ArrayList<>();
        for (String in : input) {
            int xIndex = in.indexOf('x');
            int yIndex = in.indexOf('y');
            int spaceIndex = in.indexOf(' ');
            int tIndex = in.indexOf('T');
            Disk d = new Disk();
            d.x = Integer.parseInt(in.substring(xIndex + 1, yIndex - 1));
            d.y = Integer.parseInt(in.substring(yIndex + 1, spaceIndex));
            d.size = Integer.parseInt(in.substring(spaceIndex, tIndex).trim());
            spaceIndex = in.indexOf(' ', tIndex);
            tIndex = in.indexOf('T', spaceIndex);
            d.used = Integer.parseInt(in.substring(spaceIndex, tIndex).trim());
            spaceIndex = in.indexOf(' ', tIndex);
            tIndex = in.indexOf('T', spaceIndex);
            d.avail = Integer.parseInt(in.substring(spaceIndex, tIndex).trim());
            disks.add(d);
        }
        return disks;
    }

    private static class Disk {
        private int x;
        private int y;
        private int size;
        private int used;
        private int avail;

        private boolean adjacent(Disk d) {
            return (this.x == d.x && (this.y == d.y - 1 || this.y == d.y + 1)) || (this.y == d.y && (this.x == d.x - 1 || this.x == d.x + 1));
        }

        public boolean equals(Disk o) {
            if (this == o) return true;
            return x == o.x && y == o.y;
        }
    }
}
