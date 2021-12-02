package yr2016.exercises;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Ex20 {

    public static void solution() throws IOException {
        try {
            partOne();
            partTwo();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2016/ex20.txt")));
    }

    private static void partOne() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n\n---------------------------- 2016: Exercise 20 - 1 ----------------------------\n");
        Set<Limit> blockedIps = getBlockedIPs();
        long res = -1;
        long i = 0;
        while (res == -1) {
            long finalI = i;
            final Optional<Limit> optionalLimit = blockedIps.stream().filter(limits -> limits.isBlocked(finalI)).findFirst();
            if (optionalLimit.isPresent()) {
                i = optionalLimit.get().max;
            } else {
                res = i;
            }
            i++;
        }

        System.out.println("Solution: " + res);
    }

    private static void partTwo() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n---------------------------- 2016: Exercise 20 - 2 ----------------------------\n");
        Set<Limit> blockedIps = getBlockedIPs();
        long max = 4294967295L;
        long res = 0;
        long i = 0;
        while (i <= max) {
            long finalI = i;
            final Optional<Limit> optionalLimit = blockedIps.stream().filter(limits -> limits.isBlocked(finalI)).findFirst();
            if (optionalLimit.isPresent()) {
                i = optionalLimit.get().max;
            } else {
                res++;
            }
            i++;
        }

        System.out.println("Solution: " + res);
    }

    private static Set<Limit> getBlockedIPs() throws IOException {
        final String[] input = getInput().replace("\r", "").split("\n");
        Set<Limit> blocked = new HashSet<>();
        for (String rule : input) {
            String[] r = rule.split("-");
            long from = Long.parseLong(r[0]);
            long to = Long.parseLong(r[1]);
            blocked.add(new Limit(from, to));
        }
        return blocked;
    }

    private static class Limit {
        private final long min;
        private final long max;

        public Limit(long min, long max) {
            this.min = min;
            this.max = max;
        }


        private boolean isBlocked(long val) {
            return val >= min && val <= max;
        }
    }

}
