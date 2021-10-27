package yr2016.exercices;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

public class Ex11 {

    private static final Set<Integer> dictionary = new HashSet<>();

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
        printResult("");
    }

    private static void partTwo() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n---------------------------- 2016: Exercise 11 - 2 ----------------------------\n");
        printResult("a elerium generator, a elerium-compatible microchip, a dilithium generator, a dilithium-compatible microchip");
    }


    private static void printResult(String extra) throws IOException {
        String[] instructions = getInput().replace("\r", "").split("\n");
        dictionary.clear();
        int floorNumber = 1;
        Map<Integer, List<Element>> building = new HashMap<>();
        readInstruction(extra, floorNumber, building);
        for (String instruction : instructions) {
            readInstruction(instruction, floorNumber, building);
            floorNumber++;
        }
        Set<Move> nextCases = new HashSet<>();
        nextCases.add(new Move(1, building));
        int count = 0;
        Set<Move> aux = new HashSet<>();
        while (nextCases.stream().noneMatch(m -> m.building.get(1).isEmpty() && m.building.get(2).isEmpty() && m.building.get(3).isEmpty())) {
            aux.clear();
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
                        if (currentFloor < 4) {
                            addMove(move, currentFloor, currentFloor + 1, element, pair, aux);
                        }
                    }
                }
            }
            nextCases.clear();
            nextCases.addAll(aux);
            System.out.println(count + " -> " + nextCases.size() + "\t\tD: " + dictionary.size());
            count++;
        }

        System.out.println("Solution: " + count);
    }

    private static void readInstruction(String instruction, int floorNumber, Map<Integer, List<Element>> building) {
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
        if (building.containsKey(floorNumber)) {
            building.get(floorNumber).addAll(floor);
        } else {
            building.put(floorNumber, floor);
        }
    }

    private static void addMove(Move move, int currentFloor, int targetFloor, Element element, Element pair, Set<Move> aux) {
        Map<Integer, List<Element>> clone = new HashMap<>();
        move.building.forEach((integer, floor) -> clone.put(integer, new ArrayList<>(floor)));
        clone.get(targetFloor).add(element);
        clone.get(currentFloor).remove(element);
        if (pair != null) {
            clone.get(currentFloor).remove(pair);
            clone.get(targetFloor).add(pair);
        }
        if (isValidFloor(clone.get(targetFloor)) && isValidFloor(clone.get(currentFloor))) {
            Move newMove = new Move(targetFloor, clone);
            if (dictionary.add(newMove.buildingId)) {
                aux.add(newMove);
            }
        }
    }

    private static boolean isValidFloor(List<Element> elements) {
        boolean valid = true;
        List<Element> generators = elements.stream().filter(element -> element.type == ElementType.GENERATOR).collect(Collectors.toList());
        List<Element> microchips = elements.stream().filter(element -> element.type == ElementType.MICROCHIP).collect(Collectors.toList());

        microchips.removeIf(mic -> generators.stream().anyMatch(gen -> gen.name.equals(mic.name)));

        if (!microchips.isEmpty() && !generators.isEmpty()) {
            valid = false;
        }

        return valid;
    }

    public static class Move {
        private final int currentFloor;
        private final Map<Integer, List<Element>> building;
        private final int buildingId;

        public Move(int currentFloor, Map<Integer, List<Element>> building) {
            this.currentFloor = currentFloor;
            this.building = building;
            int buildingId = 0;
            for (Integer floor : building.keySet()) {
                final List<Element> elements = building.get(floor);
                buildingId += (int) Math.pow(10, floor - 1) * elements.stream().map(Element::getId).reduce(Integer::sum).orElse(0);
            }
            buildingId *= (int) Math.pow(100, currentFloor - 1);
            this.buildingId = buildingId;
        }
    }

    private enum ElementType {
        GENERATOR, MICROCHIP
    }

    private static class Element {
        private final int id;
        private final String name;
        private final ElementType type;

        public Element(String name, ElementType type) {
            this.name = name;
            this.type = type;
            this.id = Character.getNumericValue(name.charAt(0)) * (type == ElementType.GENERATOR ? 1 : 2);
        }

        public int getId() {
            return id;
        }
    }
}
