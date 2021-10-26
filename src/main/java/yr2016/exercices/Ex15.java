package yr2016.exercices;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

public class Ex15 {
    public static void solution() throws IOException {
        try {
            partOne();
            partTwo();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2016/ex15.txt")));
    }

    public static void partOne() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n\n---------------------------- 2016: Exercise 15 - 1 ----------------------------\n");
        String[] discs = getInput().replace("\r", "").split("\n");
        final Set<Disc> sculpture = getSculpture(discs);
        System.out.println("Solution: " + getResult(sculpture));
    }


    public static void partTwo() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n\n---------------------------- 2016: Exercise 15 - 2 ----------------------------\n");
        String[] discs = getInput().replace("\r", "").split("\n");
        final Set<Disc> sculpture = getSculpture(discs);

        Disc newDisc = new Disc();
        newDisc.id = sculpture.size() + 1;
        newDisc.max = 11;
        newDisc.position = newDisc.id % newDisc.max;
        sculpture.add(newDisc);

        System.out.println("Solution: " + getResult(sculpture));
    }

    private static Set<Disc> getSculpture(String[] discs) {
        Set<Disc> sculpture = new HashSet<>();
        for (String disc : discs) {
            Disc d = new Disc();
            d.id = Character.getNumericValue(disc.charAt(6));
            int hasIndex = disc.indexOf("has ") + "has ".length();
            d.max = Integer.parseInt(disc.substring(hasIndex, disc.indexOf(" ", hasIndex)));
            d.position = (Integer.parseInt(disc.substring(disc.lastIndexOf(' ') + 1, disc.length() - 1)) + d.id) % d.max;
            sculpture.add(d);
        }
        return sculpture;
    }

    private static int getResult(Set<Disc> sculpture) {
        int time = 0;
        while (!sculpture.stream().allMatch(disc -> disc.position == 0)) {
            sculpture.forEach(Disc::tick);
            time++;
        }

        return time;
    }

    private static class Disc {
        private int id;
        private int max;
        private int position;

        private void tick() {
            position++;
            if (position > max - 1) {
                position = 0;
            }
        }
    }
}
