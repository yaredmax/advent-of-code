package yr2016.exercices;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Ex4 {
    public static void solution() throws IOException {
        partOne();
        partTwo();
    }


    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2016/ex4.txt")));
    }


    private static void partOne() throws IOException {
        System.out.println("\n\n---------------------------- 2016: Exercise 4 - 1 ----------------------------\n");
        String[] encryptionArray = getInput().replace("\r", "").split("\n");

        int result = 0;

        for (String encryptionText : encryptionArray) {
            final Decryption decryption = getDecryption(encryptionText);

            if (decryption.isValid())
                result += decryption.getId();
        }

        System.out.println("Solution: " + result);
    }


    private static void partTwo() throws IOException {
        System.out.println("\n---------------------------- 2016: Exercise 4 - 2 ----------------------------\n");

        String[] encryptionArray = getInput().replace("\r", "").split("\n");

        int i = 0, encryptionLength = encryptionArray.length;
        int resultId = -1;
        while (i < encryptionLength && resultId == -1) {
            final Decryption decryption = getDecryption(encryptionArray[i]);

            if (decryption.isValid()) {
                StringBuilder finalName = new StringBuilder();
                for (char c : decryption.getName().toCharArray()) {
                    int move = decryption.getId() % 26;
                    int newChar = c + move;
                    if (newChar > 122) {
                        newChar = 96 + (newChar - 122);
                    }
                    finalName.append((char) newChar);

                    //noinspection SpellCheckingInspection
                    if (finalName.toString().equals("northpoleobjectstorage"))
                        resultId = decryption.getId();
                }
            }
            i++;
        }


        System.out.println("Solution: " + resultId);
    }

    private static Decryption getDecryption(String encryption) {
        int bracketIndex = encryption.indexOf('[');
        int lastDashIndex = encryption.lastIndexOf('-');
        String checksum = encryption.substring(bracketIndex + 1, encryption.length() - 1);
        int id = Integer.parseInt(encryption.substring(lastDashIndex + 1, bracketIndex));
        String name = encryption.substring(0, lastDashIndex).replace("-", "");
        char[] nameArray = name.toCharArray();
        Arrays.sort(nameArray);

        Map<Character, Integer> letters = new HashMap<>();

        for (char letter : nameArray) {
            int count = letters.getOrDefault(letter, 0) + 1;
            letters.put(letter, count);
        }

        //noinspection ComparatorMethodParameterNotUsed
        final String validChecksum = letters.entrySet()
                .stream()
                .sorted((o1, o2) -> o2.getValue() > o1.getValue() || (o2.getValue().equals(o1.getValue()) && o2.getKey() < o1.getKey()) ? 1 : -1)
                .map(Map.Entry::getKey)
                .limit(5)
                .map(String::valueOf)
                .reduce((s, s2) -> s + s2).orElse("");

        return new Decryption(id, name, validChecksum.equals(checksum));
    }

    @Getter
    @AllArgsConstructor
    private static class Decryption {
        private int id;
        private String name;
        private boolean valid;
    }
}
