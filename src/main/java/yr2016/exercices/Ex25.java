package yr2016.exercices;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

public class Ex25 {

    public static void solution() throws IOException {
        try {
            partOne();
            partTwo();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2016/ex25.txt")));
    }

    private static void partOne() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n\n---------------------------- 2016: Exercise 25 - 1 ----------------------------\n");
        getResult();
    }

    private static void partTwo() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n---------------------------- 2016: Exercise 25 - 2 ----------------------------\n");
//        getResult(12);
    }

    private static void getResult() throws IOException {
        String[] input = getInput().replace("\r", "").split("\n");
        int aux = 1;
        for (int i = 1; i <= 2; i++) {
            int cpyIndex = input[i].indexOf("cpy ") + 4;
            int spaceIndex = input[i].indexOf(" ", cpyIndex);
            aux *= Integer.parseInt(input[i].substring(cpyIndex, spaceIndex));
        }

        int a = 0;
        String res;
        do {
            a++;
            res = Integer.toBinaryString(a + aux);
        } while (!complete(res));

        System.out.println("Solution: " + a);
    }

    private static boolean complete(String binary) {
        int i = 0;
        boolean complete = true;
        char expected = '1';
        while (complete && i < binary.length()) {
            if (binary.charAt(i) != expected)
                complete = false;
            expected = expected == '1' ? '0' : '1';
            i++;
        }

        return complete;
    }
}
