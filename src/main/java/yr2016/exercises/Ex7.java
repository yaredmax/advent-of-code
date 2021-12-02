package yr2016.exercises;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Ex7 {
    public static void solution() throws IOException {
        try {
            partOne();
            partTwo();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2016/ex7.txt")));
    }

    public static void partOne() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n\n---------------------------- 2016: Exercise 7 - 1 ----------------------------\n");
        String[] ips = getInput().replace("\r", "").split("\n");

        int result = 0;
        for (String ip : ips) {

            List<String> hypernetSequences = new ArrayList<>();
            List<String> supernetSequences = new ArrayList<>();

            int openBracket = ip.indexOf('[');
            int closeBracket = -1;
            while (openBracket != -1) {
                String supernet = ip.substring(closeBracket + 1, openBracket);
                closeBracket = ip.indexOf(']', closeBracket + 1);
                String hypernet = ip.substring(openBracket + 1, closeBracket);
                hypernetSequences.add(hypernet);
                supernetSequences.add(supernet);
                openBracket = ip.indexOf('[', openBracket + 1);
            }
            String supernetcheck = ip.substring(closeBracket + 1);
            supernetSequences.add(supernetcheck);


            if (hypernetSequences.stream().noneMatch(Ex7::hasABBA) && supernetSequences.stream().anyMatch(Ex7::hasABBA))
                result++;
        }

        System.out.println("Solution: " + result);
    }

    private static void partTwo() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n---------------------------- 2016: Exercise 7 - 2 ----------------------------\n");
        String[] ips = getInput().replace("\r", "").split("\n");

        int result = 0;
        for (String ip : ips) {

            List<String> hypernetSequences = new ArrayList<>();
            List<String> supernetSequences = new ArrayList<>();

            int openBracket = ip.indexOf('[');
            int closeBracket = -1;
            while (openBracket != -1) {
                String supernet = ip.substring(closeBracket + 1, openBracket);
                closeBracket = ip.indexOf(']', closeBracket + 1);
                String hypernet = ip.substring(openBracket + 1, closeBracket);
                hypernetSequences.add(hypernet);
                supernetSequences.add(supernet);
                openBracket = ip.indexOf('[', openBracket + 1);
            }
            String supernet = ip.substring(closeBracket + 1);
            supernetSequences.add(supernet);


            if (supportsSSL(hypernetSequences, supernetSequences))
                result++;
        }

        System.out.println("Solution: " + result);
    }

    private static boolean hasABBA(String text) {
        if (text.length() < 4)
            return false;
        for (int i = 0; i < text.length() - 3; i++) {
            final String sub = text.substring(i, i + 4);
            if (isABBA(sub))
                return true;
        }
        return false;
    }

    private static boolean isABBA(String s) {
        return s.replace(s.charAt(0), 'A').replace(s.charAt(1), 'B').equals("ABBA");
    }


    private static boolean supportsSSL(List<String> hypernetSequences, List<String> supernetSequences) {
        return hypernetSequences.stream().anyMatch(hypernet -> supernetSequences.stream().map(Ex7::getBABList).flatMap(Collection::stream).anyMatch(hypernet::contains));
    }

    private static List<String> getBABList(String text) {
        List<String> result = new ArrayList<>();
        if (text.length() < 3)
            return result;
        for (int i = 0; i < text.length() - 2; i++) {
            final String sub = text.substring(i, i + 3);
            if (isABA(sub)) {
                result.add("" + sub.charAt(1) + sub.charAt(0) + sub.charAt(1));
            }
        }
        return result;
    }

    private static boolean isABA(String s) {
        return s.replace(s.charAt(0), 'A').replace(s.charAt(1), 'B').equals("ABA");
    }

}
