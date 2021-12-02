package yr2016.exercises;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Ex21 {

    private static final String SWAP = "swap";
    private static final String ROTATE = "rotate";
    private static final String REVERSE = "reverse";
    private static final String MOVE = "move";

    private static final String POSITION = "position";
    private static final String LETTER = "letter";

    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private static final String BASED = "based";

    public static void solution() throws IOException {
        try {
            partOne();
            partTwo();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2016/ex21.txt")));
    }

    private static void partOne() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n\n---------------------------- 2016: Exercise 21 - 1 ----------------------------\n");
        String[] instructions = getInput().replace("\r", "").split("\n");
        Map<Integer, Character> password = new HashMap<>();
        password.put(0, 'a');
        password.put(1, 'b');
        password.put(2, 'c');
        password.put(3, 'd');
        password.put(4, 'e');
        password.put(5, 'f');
        password.put(6, 'g');
        password.put(7, 'h');

        for (String instruction : instructions) {
            int firstSpaceIndex = instruction.indexOf(" ");
            String type = instruction.substring(0, firstSpaceIndex);

            switch (type) {
                case SWAP -> swap(password, instruction, firstSpaceIndex);
                case ROTATE -> {
                    int secondSpaceIndex = instruction.indexOf(" ", firstSpaceIndex + 1);
                    String rotateType = instruction.substring(firstSpaceIndex + 1, secondSpaceIndex);
                    int rotate = 0;
                    switch (rotateType) {
                        case LEFT -> {
                            final int count = Character.getNumericValue(instruction.charAt(12));
                            final int rest = count % password.size();
                            rotate = password.size() - rest;
                        }
                        case RIGHT -> {
                            final int count = Character.getNumericValue(instruction.charAt(13));
                            rotate = count % password.size();
                        }
                        case BASED -> {
                            final char letter = instruction.charAt(35);
                            final Optional<Map.Entry<Integer, Character>> first = password.entrySet().stream().filter(entry -> entry.getValue() == letter).findFirst();
                            if (first.isPresent()) {
                                int count = first.get().getKey();
                                if (count >= 4)
                                    count++;
                                count++;
                                rotate = count % password.size();
                            }
                        }
                    }

                    Map<Integer, Character> aux = new HashMap<>();
                    for (int i = 0; i < password.size(); i++) {
                        aux.put((i + rotate) % password.size(), password.get(i));
                    }
                    password = aux;
                }
                case REVERSE -> reverse(password, instruction);
                case MOVE -> {
                    final int from = Character.getNumericValue(instruction.charAt(14));
                    final int to = Character.getNumericValue(instruction.charAt(28));
                    move(password, from, to);
                }
            }
        }

        StringBuilder p = new StringBuilder();
        for (int i = 0; i < password.size(); i++) {
            p.append(password.get(i));
        }
        System.out.println("Solution: " + p);
    }

    private static void reverse(Map<Integer, Character> password, String instruction) {
        final int from = Character.getNumericValue(instruction.charAt(18));
        final int to = Character.getNumericValue(instruction.charAt(28));

        int dif = (to - from) / 2;
        for (int i = 0; i <= dif; i++) {
            Character aux = password.get(from + i);
            password.put(from + i, password.get(to - i));
            password.put(to - i, aux);
        }
    }

    private static void swap(Map<Integer, Character> password, String instruction, int firstSpaceIndex) {
        int secondSpaceIndex = instruction.indexOf(" ", firstSpaceIndex + 1);
        String swapType = instruction.substring(firstSpaceIndex + 1, secondSpaceIndex);

        switch (swapType) {
            case POSITION -> {
                final int x = Character.getNumericValue(instruction.charAt(14));
                final int y = Character.getNumericValue(instruction.charAt(30));

                Character cX = password.get(x);
                Character cY = password.get(y);

                password.put(x, cY);
                password.put(y, cX);
            }
            case LETTER -> {
                Character cX = instruction.charAt(12);
                Character cY = instruction.charAt(26);
                final Optional<Map.Entry<Integer, Character>> firstX = password.entrySet().stream().filter(entry -> entry.getValue() == cX).findFirst();
                final Optional<Map.Entry<Integer, Character>> firstY = password.entrySet().stream().filter(entry -> entry.getValue() == cY).findFirst();
                if (firstX.isPresent() && firstY.isPresent()) {
                    final int x = firstX.get().getKey();
                    final int y = firstY.get().getKey();
                    password.put(x, cY);
                    password.put(y, cX);
                }

            }
        }
    }

    private static void move(Map<Integer, Character> password, int from, int to) {
        Character aux = password.get(from);
        if (from < to) {
            for (int i = from; i < to; i++) {
                password.put(i, password.get(i + 1));
            }
        } else {
            for (int i = from; i > to; i--) {
                password.put(i, password.get(i - 1));
            }
        }
        password.put(to, aux);
    }

    private static void partTwo() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n---------------------------- 2016: Exercise 21 - 2 ----------------------------\n");
        String[] instructions = getInput().replace("\r", "").split("\n");

        // fbgdceah
        Map<Integer, Character> password = new HashMap<>();
        password.put(0, 'f');
        password.put(1, 'b');
        password.put(2, 'g');
        password.put(3, 'd');
        password.put(4, 'c');
        password.put(5, 'e');
        password.put(6, 'a');
        password.put(7, 'h');

        Map<Integer, Integer> basedMap = new HashMap<>();
        basedMap.put(0, 1);
        basedMap.put(1, 1);
        basedMap.put(2, 6);
        basedMap.put(3, 2);
        basedMap.put(4, 7);
        basedMap.put(5, 3);
        basedMap.put(6, 0);
        basedMap.put(7, 4);

        List<String> list = Arrays.asList(instructions);
        Collections.reverse(list);

        for (String instruction : list) {
            int firstSpaceIndex = instruction.indexOf(" ");
            String type = instruction.substring(0, firstSpaceIndex);

            switch (type) {
                case SWAP -> swap(password, instruction, firstSpaceIndex);
                case ROTATE -> {
                    int secondSpaceIndex = instruction.indexOf(" ", firstSpaceIndex + 1);
                    String rotateType = instruction.substring(firstSpaceIndex + 1, secondSpaceIndex);
                    int rotate = 0;
                    switch (rotateType) {
                        case LEFT -> {
                            final int count = Character.getNumericValue(instruction.charAt(12));
                            rotate = count % password.size();
                        }
                        case RIGHT -> {
                            final int count = Character.getNumericValue(instruction.charAt(13));
                            final int rest = count % password.size();
                            rotate = password.size() - rest;
                        }
                        case BASED -> {
                            final char letter = instruction.charAt(35);
                            final Optional<Map.Entry<Integer, Character>> first = password.entrySet().stream().filter(entry -> entry.getValue() == letter).findFirst();
                            if (first.isPresent()) {
                                int index = first.get().getKey();
                                int count = basedMap.get(index);
                                final int rest = count % password.size();
                                rotate = password.size() - rest;
                            }
                        }
                    }

                    Map<Integer, Character> aux = new HashMap<>();
                    for (int i = 0; i < password.size(); i++) {
                        aux.put((i + rotate) % password.size(), password.get(i));
                    }
                    password = aux;
                }
                case REVERSE -> reverse(password, instruction);
                case MOVE -> {
                    final int to = Character.getNumericValue(instruction.charAt(14));
                    final int from = Character.getNumericValue(instruction.charAt(28));
                    move(password, from, to);
                }
            }
        }

        StringBuilder p = new StringBuilder();
        for (int i = 0; i < password.size(); i++) {
            p.append(password.get(i));
        }
        System.out.println("Solution: " + p);
    }
}
