package yr2016.exercices;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Ex11 {

    public static void solution() throws IOException {
        try {
            partOne();
            partTwo();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2016/ex11.txt")));
    }

    private static void partOne() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n\n---------------------------- 2016: Exercise 11 - 1 ----------------------------\n");
        String[] instructions = getInput().replace("\r", "").split("\n");
        int floorNumber = 1;
        Map<Integer, List<Element>> building = new HashMap<>();
        for (String instruction : instructions) {
            List<Element> floor = new ArrayList<>();
            int indexOfA = instruction.indexOf("a ");
            while (indexOfA != -1) {
                indexOfA += 2;
                int indexOfGenerator = instruction.indexOf(" generator", indexOfA);
                int indexOfCompatible = instruction.indexOf("-compatible", indexOfA);
                if (indexOfGenerator != -1 && (indexOfCompatible == -1 || indexOfGenerator < indexOfCompatible)) {
                    floor.add(new Element(instruction.substring(indexOfA, indexOfGenerator), ElementType.GENERATOR));
                } else {
                    floor.add(new Element(instruction.substring(indexOfA, indexOfCompatible), ElementType.MICROCHIP));
                }
                indexOfA = instruction.indexOf("a ", indexOfA);
            }
            building.put(floorNumber, floor);
            floorNumber++;
        }

        List<Move> nextCases = new ArrayList<>();
        nextCases.add(new Move(1, building, null));
        int count = 0;
        while (nextCases.stream().noneMatch(m -> m.building.get(1).isEmpty() && m.building.get(2).isEmpty() && m.building.get(3).isEmpty())) {
            List<Move> aux = new ArrayList<>();
            for (Move move : nextCases) {
                int currentFloor = move.currentFloor;
                final List<Element> elements = move.building.get(currentFloor);
                for (int i = 0; i < elements.size(); i++) {
                    Element element = elements.get(i);
                    if (currentFloor > 1) {
                        addMove(move, currentFloor, currentFloor - 1, element, null, aux);
                    }
                    if (currentFloor < 4) {
                        addMove(move, currentFloor, currentFloor + 1, element, null, aux);
                    }
                    for (int j = i + 1; j < elements.size(); j++) {
                        Element pair = elements.get(j);
//                        if (currentFloor > 1) {
//                            addMove(move, currentFloor, currentFloor - 1, element, pair, aux);
//                        }
                        if (currentFloor < 4) {
                            addMove(move, currentFloor, currentFloor + 1, element, pair, aux);
                        }
                    }
                }
            }
            nextCases = aux;
            count++;
        }


        System.out.println("Solution: " + count);
    }

    private static void partTwo() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n---------------------------- 2016: Exercise 11 - 2 ----------------------------\n");
        String[] instructions = getInput().replace("\r", "").split("\n");

        System.out.println("Solution: " + "");
    }

    private static void addMove(Move move, int currentFloor, int targetFloor, Element element, Element pair, List<Move> aux) {
        if (!repeated(move, targetFloor, element, pair)) {
            Map<Integer, List<Element>> clone = new HashMap<>();
            move.building.forEach((integer, floor) -> {
                clone.put(integer, new ArrayList<>(floor));
            });
            clone.get(targetFloor).add(element);
            clone.get(currentFloor).remove(element);
            if (pair != null) {
                clone.get(currentFloor).remove(pair);
                clone.get(targetFloor).add(pair);
            }
            if (isValidFloor(clone.get(targetFloor)) && isValidFloor(clone.get(currentFloor)))
                aux.add(new Move(targetFloor, clone, move));
        } else {
//            System.out.println();
        }
    }

    private static boolean repeated(Move move, int targetFloor, Element element, Element pair) {
        boolean repeated = false;
        if (move.previous != null && move.previous.currentFloor == targetFloor) {
            if (pair == null) {
                repeated = move.previous.building.get(targetFloor).contains(element);
            } else {
                repeated = move.previous.building.get(targetFloor).contains(element) && move.previous.building.get(targetFloor).contains(pair);
            }
        }
        return repeated;
    }

    private static boolean isValidFloor(List<Element> elements) {
        boolean valid = true;
        List<Element> generators = elements.stream().filter(element -> element.type == ElementType.GENERATOR).collect(Collectors.toList());
        List<Element> microchips = elements.stream().filter(element -> element.type == ElementType.MICROCHIP).collect(Collectors.toList());

//        List<Element> auxGen = new ArrayList<>(generators);

//        generators.removeIf(gen -> microchips.stream().anyMatch(mic -> mic.name.equals(gen.name)));
        microchips.removeIf(mic -> generators.stream().anyMatch(gen -> gen.name.equals(mic.name)));

        if (!microchips.isEmpty() && !generators.isEmpty()) {
            valid = false;
        }

        return valid;
    }

    private static record Move(int currentFloor, Map<Integer, List<Element>> building, Move previous) {
    }

    private enum ElementType {
        GENERATOR, MICROCHIP
    }

    private static record Element(String name, ElementType type) {
    }
}
