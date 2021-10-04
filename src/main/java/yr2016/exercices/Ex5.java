package yr2016.exercices;

import jakarta.xml.bind.DatatypeConverter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Ex5 {
    public static void solution() throws IOException {
        try {
            partOne();
            partTwo();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2016/ex5.txt")));
    }


    public static void partOne() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n\n---------------------------- 2016: Exercise 5 - 1 ----------------------------\n");
        String hash = getInput().replace("\r", "").replace("\n", "");

        byte[] bytes;

        MessageDigest md = MessageDigest.getInstance("MD5");

        StringBuilder password = new StringBuilder();
        int index = 0;
        while (password.length() < 8) {
            bytes = (hash + index).getBytes(StandardCharsets.UTF_8);
            md.update(bytes);
            byte[] md5 = md.digest();
            String hex = DatatypeConverter.printHexBinary(md5).toUpperCase();
            if (hex.startsWith("00000")) {
                password.append(hex.charAt(5));
            }
            index++;
        }

        System.out.println("Solution: " + password);
    }


    public static void partTwo() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n\n---------------------------- 2016: Exercise 5 - 2 ----------------------------\n");
        String hash = getInput().replace("\r", "").replace("\n", "");

        byte[] bytes;

        Map<Integer, String> passwordMap = new HashMap<>();

        MessageDigest md = MessageDigest.getInstance("MD5");

        long index = 0;
        while (passwordMap.size() < 8) {
            bytes = (hash + index).getBytes(StandardCharsets.UTF_8);
            md.update(bytes);
            byte[] md5 = md.digest();
            String hex = DatatypeConverter.printHexBinary(md5).toUpperCase();
            if (hex.startsWith("00000")) {
                int pos = Character.getNumericValue(hex.charAt(5));
                if (pos >= 0 && pos <= 7 && !passwordMap.containsKey(pos))
                    passwordMap.put(pos, String.valueOf(hex.charAt(6)));
            }
            index++;
        }

        String password = passwordMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue).reduce(String::concat).orElse("");

        System.out.println("Solution: " + password);
    }

}
