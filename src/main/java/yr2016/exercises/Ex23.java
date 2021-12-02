package yr2016.exercises;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Ex23 {

    public static void solution() throws IOException {
        try {
            partOne();
            partTwo();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2016/ex23.txt")));
    }

    private static void partOne() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n\n---------------------------- 2016: Exercise 23 - 1 ----------------------------\n");
        getResult(7);
    }

    private static void partTwo() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n---------------------------- 2016: Exercise 23 - 2 ----------------------------\n");
        getResult(12);
    }

    private static void getResult(int aInitValue) throws IOException {
        String[] input = getInput().replace("\r", "").split("\n");

        final List<Instruction> instructions = Arrays.stream(input).map(Ex23::transform).collect(Collectors.toList());

        Map<String, Integer> registerMap = new HashMap<>();
        registerMap.put("a", aInitValue);
        registerMap.put("b", 0);
        registerMap.put("c", 0);
        registerMap.put("d", 0);

        int count = 0;
        int i = 0;
        while (i < instructions.size()) {
            count++;
            Instruction instruction = instructions.get(i);
            if (instruction.type.equals("dec") && instruction.x.equals("d"))
                System.out.println(registerMap.get("a"));
            if (instruction.type.equals("tgl")) {
                int target;
                if (registerMap.containsKey(instruction.x)) {
                    target = registerMap.get(instruction.x);
                } else {
                    target = Integer.parseInt(instruction.x);
                }
                target = i + target;
                if (target < instructions.size() && target > 0) {
                    Instruction tgl = instructions.get(target);

                    if (tgl.y == null) {
                        if (tgl.type.equals("inc"))
                            tgl.type = "dec";
                        else
                            tgl.type = "inc";
                    } else {
                        if (tgl.type.equals("jnz"))
                            tgl.type = "cpy";
                        else
                            tgl.type = "jnz";
                    }
                }
                i++;
            } else {
                i += operate(instructions.get(i), registerMap);
            }

            // LOOP
            if (instruction.type.equals("jnz")) {
                int backward = Integer.parseInt(instruction.y);
                if (backward < 0) {
                    boolean isLoop = false;
                    while (backward <= 0 && !isLoop) {
                        Instruction ins = instructions.get(i - backward);
                        if (ins.type.equals("dec") && ins.x.equals(instruction.x))
                            isLoop = true;
                        backward++;
                    }

                    if (isLoop) {
                        backward = Integer.parseInt(instruction.y);

                        String loopMaker = instruction.x;
                        for (int j = backward; j <= 0; j++) {
                            Instruction ins = instructions.get(i - j);
                            if (ins.type.equals("inc")) {
                                ins.type = "cpy";
                                ins.y = ins.x;
                                ins.x = String.valueOf(registerMap.get(ins.x) + registerMap.get(loopMaker));
                            }
                            if (ins.type.equals("dec")) {
                                ins.type = "cpy";
                                ins.y = ins.x;
                                ins.x = String.valueOf(registerMap.get(ins.x) - registerMap.get(loopMaker));
                            }
                        }
                    }
                }
            }
//            System.out.println(instruction.type + " " + instruction.x + (instruction.y != null ? instruction.y : "") + " -> " + registerMap.get("a"));
        }

        System.out.println("Solution: " + registerMap.get("a"));
    }

    public static Instruction transform(String instruction) {
        Instruction res = new Instruction();
        final String[] split = instruction.split(" ");
        res.type = split[0];
        res.x = split[1];
        if (split.length == 3)
            res.y = split[2];
        return res;
    }

    public static int operate(Instruction instruction, Map<String, Integer> registerMap) {
        int increment = 1;
        switch (instruction.type) {
            case "cpy" -> {
                if (registerMap.containsKey(instruction.y))
                    if (registerMap.containsKey(instruction.x)) {
                        registerMap.put(instruction.y, registerMap.get(instruction.x));
                    } else {
                        registerMap.put(instruction.y, Integer.parseInt(instruction.x));
                    }
            }
            case "inc" -> {
                if (registerMap.containsKey(instruction.x))
                    registerMap.put(instruction.x, registerMap.get(instruction.x) + 1);
            }
            case "dec" -> {
                if (registerMap.containsKey(instruction.x))
                    registerMap.put(instruction.x, registerMap.get(instruction.x) - 1);
            }
            case "jnz" -> {
                int control;
                if (registerMap.containsKey(instruction.x))
                    control = registerMap.get(instruction.x);
                else
                    control = Integer.parseInt(instruction.x);

                if (control != 0) {
                    int jump;
                    if (registerMap.containsKey(instruction.y))
                        jump = registerMap.get(instruction.y);
                    else
                        jump = Integer.parseInt(instruction.y);
                    increment = jump;
                }
            }
        }

        return increment;
    }

    private static class Instruction {
        private String type;
        private String x;
        private String y;
    }
}
