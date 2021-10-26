package yr2016.exercices;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Ex12 {

    public static void solution() throws IOException {
        try {
            partOne();
            partTwo();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2016/ex12.txt")));
    }

    private static void partOne() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n\n---------------------------- 2016: Exercise 12 - 1 ----------------------------\n");
        getResult(0);
    }

    private static void partTwo() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n---------------------------- 2016: Exercise 12 - 2 ----------------------------\n");
        getResult(1);
    }

    private static void getResult(int cInitValue) throws IOException {
        String[] input = getInput().replace("\r", "").split("\n");

        final List<Instruction> instructions = Arrays.stream(input).map(Ex12::transform).collect(Collectors.toList());

        Map<String, Integer> registerMap = new HashMap<>();
        registerMap.put("a", 0);
        registerMap.put("b", 0);
        registerMap.put("c", cInitValue);
        registerMap.put("d", 0);

        int i = 0;
        while (i < instructions.size()) {
            i += operate(instructions.get(i), registerMap);
        }

        System.out.println("Solution: " + registerMap.get("a"));
    }

    private static Instruction transform(String instruction) {
        Instruction res = new Instruction();
        final String[] split = instruction.split(" ");
        res.type = split[0];
        res.x = split[1];
        if (split.length == 3)
            res.y = split[2];
        return res;
    }

    private static int operate(Instruction instruction, Map<String, Integer> registerMap) {
        int increment = 1;
        switch (instruction.type) {
            case "cpy" -> {
                if (registerMap.containsKey(instruction.x)) {
                    registerMap.put(instruction.y, registerMap.get(instruction.x));
                } else {
                    registerMap.put(instruction.y, Integer.parseInt(instruction.x));
                }
            }
            case "inc" -> registerMap.put(instruction.x, registerMap.get(instruction.x) + 1);
            case "dec" -> registerMap.put(instruction.x, registerMap.get(instruction.x) - 1);
            case "jnz" -> {
                int control;
                if (registerMap.containsKey(instruction.x)) {
                    control = registerMap.get(instruction.x);
                } else {
                    control = Integer.parseInt(instruction.x);
                }
                if (control != 0) {
                    increment = Integer.parseInt(instruction.y);
                }
            }
        }

        return increment;
    }

    private static class Instruction {
        public String type;
        public String x;
        public String y;
    }
}
