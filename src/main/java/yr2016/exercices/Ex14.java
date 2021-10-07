package yr2016.exercices;

import jakarta.xml.bind.DatatypeConverter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

public class Ex14 {
    public static void solution() throws IOException {
        try {
            partOne();
            partTwo();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2016/ex14.txt")));
    }


    public static void partOne() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n\n---------------------------- 2016: Exercise 14 - 1 ----------------------------\n");
        String hash = getInput().replace("\r", "").replace("\n", "");

        byte[] bytes;

        MessageDigest md = MessageDigest.getInstance("MD5");

        int index = 0;
        Set<Integer> keysIndex = new HashSet<>();
        Map<String, List<Integer>> indexMap = new HashMap<>();
        while (keysIndex.size() < 64) {
            bytes = (hash + index).getBytes(StandardCharsets.UTF_8);
            md.update(bytes);
            byte[] md5 = md.digest();
            String hex = DatatypeConverter.printHexBinary(md5).toLowerCase();

            if (index == 110) {
                System.out.println();
            }

            int i = 0;
            while (i < hex.length() - 5) {
                final String pentaString = hex.substring(i, i + 5);
                if (allSame(pentaString)) {
                    int finalIndex = index;
                    keysIndex.addAll(indexMap.get(pentaString.substring(2)).stream().filter(triIndex -> triIndex > finalIndex - 999).collect(Collectors.toList()));
                }
                i++;
            }

            i = 0;
            String triplet = "";
            while (i < hex.length() - 3 && triplet.isEmpty()) {
                final String triString = hex.substring(i, i + 3);
                if (allSame(triString))
                    triplet = triString;
                i++;
            }

            if (!triplet.isEmpty()) {
                final List<Integer> list = indexMap.getOrDefault(triplet, new ArrayList<>());
                list.add(index);
                indexMap.put(triplet, list);
            }

            index++;
        }

        System.out.println("Solution: " + keysIndex.stream().sorted().limit(64).max(Integer::compareTo).get());
    }

    private static boolean allSame(String s) {
        int n = s.length();
        for (int i = 1; i < n; i++)
            if (s.charAt(i) != s.charAt(0))
                return false;

        return true;
    }


    public static void partTwo() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n\n---------------------------- 2016: Exercise 14 - 2 ----------------------------\n");
        System.out.println("Solution: ");
    }

}
