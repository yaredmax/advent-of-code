package yr2021.exercises;

import yr2021.utils.NumberUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Ex3 {
    public static void solution() throws IOException {
        partOne();
        partTwo();
    }

    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2021/ex3.txt")));
    }

    private static void partOne() throws IOException {
        System.out.println("\n\n---------------------------- 2021: Exercise 3 - 1 ----------------------------\n");

        String[] diagnostic = getInput().replace("\r", "").split("\n");

        int length = diagnostic[0].length();

        int[] oneCount = new int[length];
        int[] zeroCount = new int[length];

        for (String binary : diagnostic) {
            final char[] chars = binary.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == '1') {
                    oneCount[i]++;
                } else {
                    zeroCount[i]++;
                }
            }
        }

        StringBuilder mostCommon = new StringBuilder();
        for (int i = 0; i < oneCount.length; i++) {
            if (oneCount[i] > zeroCount[i]) {
                mostCommon.append('1');
            } else {
                mostCommon.append('0');
            }
        }

        int gamma = NumberUtils.toDecimal(mostCommon.toString());

        int epsilon = (int) ((Math.pow(2, length) - 1) - gamma);

        int result = gamma * epsilon;

        System.out.println("Solution: " + result);
    }

    private static void partTwo() throws IOException {
        System.out.println("\n---------------------------- 2021: Exercise 3 - 2 ----------------------------\n");

        List<String> mostCommonList = Arrays.stream(getInput().replace("\r", "").split("\n")).collect(Collectors.toList());
        List<String> leastCommonList = new ArrayList<>(mostCommonList);

        int length = mostCommonList.get(0).length();


        int i = 0;
        while (i < length) {
            char mc = mostCommon(mostCommonList, i);
            char lc = leastCommon(leastCommonList, i);
            int finalI = i;
            if (mostCommonList.size() > 1)
                mostCommonList = mostCommonList.stream().filter(s -> s.charAt(finalI) == mc).collect(Collectors.toList());
            if (leastCommonList.size() > 1)
                leastCommonList = leastCommonList.stream().filter(s -> s.charAt(finalI) == lc).collect(Collectors.toList());
            i++;
        }

        int oxygen = NumberUtils.toDecimal(mostCommonList.get(0));
        int co2 = NumberUtils.toDecimal(leastCommonList.get(0));

        System.out.println("Solution: " + oxygen * co2);
    }

    private static char mostCommon(List<String> diag, int position) {
        int ones = 0;
        int zeros = 0;

        for (String s : diag) {
            if (s.charAt(position) == '1') {
                ones++;
            } else {
                zeros++;
            }
        }

        char res;
        if (zeros > ones) {
            res = '0';
        } else if (zeros < ones) {
            res = '1';
        } else {
            res = '1';
        }

        return res;
    }

    private static char leastCommon(List<String> diag, int position) {
        int ones = 0;
        int zeros = 0;

        for (String s : diag) {
            if (s.charAt(position) == '1') {
                ones++;
            } else {
                zeros++;
            }
        }

        char res;
        if (zeros < ones) {
            res = '0';
        } else if (zeros > ones) {
            res = '1';
        } else {
            res = '0';
        }

        return res;
    }
}
