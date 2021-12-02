package yr2016.exercises;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Ex10 {

    private static final String OUTPUT = "output ";

    public static void solution() throws IOException {
        try {
            partOne();
            partTwo();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2016/ex10.txt")));
    }

    private static void partOne() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n\n---------------------------- 2016: Exercise 10 - 1 ----------------------------\n");
        String[] instructions = getInput().replace("\r", "").split("\n");

        final Map<String, Bot> botMap = getBotMap(instructions);

        final String result = botMap.entrySet().stream()
                .filter(entry -> !entry.getValue().id.startsWith(OUTPUT) && entry.getValue().values.containsAll(List.of(61, 17)))
                .findFirst().orElseThrow(NullPointerException::new).getKey();
        System.out.println("Solution: " + result);
    }

    private static void partTwo() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n---------------------------- 2016: Exercise 10 - 2 ----------------------------\n");
        String[] instructions = getInput().replace("\r", "").split("\n");

        Map<String, Bot> botMap = getBotMap(instructions);
        final Integer result = botMap.entrySet().stream().filter(entry ->
                        entry.getKey().equals(OUTPUT + "0")
                                || entry.getKey().equals(OUTPUT + "1")
                                || entry.getKey().equals(OUTPUT + "2"))
                .map(entry -> entry.getValue().values.stream().reduce(Integer::sum).orElse(0))
                .reduce((integer, integer2) -> integer * integer2).orElse(0);

        System.out.println("Solution: " + result);
    }

    private static Map<String, Bot> getBotMap(String[] instructions) {
        Map<String, Bot> botMap = new HashMap<>();

        for (String instruction : instructions) {
            if (instruction.startsWith("value")) {
                valueInstruction(instruction, botMap);
            } else {
                botInstruction(instruction, botMap);
            }
        }

        return botMap;
    }

    private static void valueInstruction(String instruction, Map<String, Bot> botMap) {
        int valueIndex = instruction.indexOf("value ") + "value ".length();
        int value = Integer.parseInt(instruction.substring(valueIndex, instruction.indexOf(' ', valueIndex)));
        int botIndex = instruction.indexOf("bot ") + "bot ".length();
        String botId = instruction.substring(botIndex);
        botMap.putIfAbsent(botId, new Bot(botId));
        Bot bot = botMap.get(botId);
        bot.addValue(value);
    }

    private static void botInstruction(String instruction, Map<String, Bot> botMap) {
        int botIndex = instruction.indexOf("bot ") + "bot ".length();
        String botId = instruction.substring(botIndex, instruction.indexOf(' ', botIndex));
        botMap.putIfAbsent(botId, new Bot(botId));
        Bot bot = botMap.get(botId);

        int lowIndex = instruction.indexOf("low to ");
        if (lowIndex != -1) {
            lowIndex += "low to ".length();
            if (instruction.substring(lowIndex, instruction.indexOf(' ', lowIndex)).equals("bot")) {
                lowIndex += "bot ".length();
                String lowBotId = instruction.substring(lowIndex, instruction.indexOf(' ', lowIndex));
                botMap.putIfAbsent(lowBotId, new Bot(lowBotId));
                bot.setLow(botMap.get(lowBotId));
            } else {
                lowIndex += OUTPUT.length();
                String outputId = OUTPUT + instruction.substring(lowIndex, instruction.indexOf(' ', lowIndex));
                botMap.putIfAbsent(outputId, new Bot(outputId));
                bot.setLow(botMap.get(outputId));
            }
        }

        int highIndex = instruction.indexOf("high to ");
        if (highIndex != -1) {
            highIndex += "high to ".length();
            if (instruction.substring(highIndex, instruction.indexOf(' ', highIndex)).equals("bot")) {
                highIndex += "bot ".length();
                String highBotId = instruction.substring(highIndex);
                botMap.putIfAbsent(highBotId, new Bot(highBotId));
                bot.setHigh(botMap.get(highBotId));
            } else {
                highIndex += OUTPUT.length();
                String outputId = OUTPUT + instruction.substring(highIndex);
                botMap.putIfAbsent(outputId, new Bot(outputId));
                bot.setHigh(botMap.get(outputId));
            }
        }
    }

    private static class Bot {
        private final String id;
        private Bot low;
        private Bot high;
        private final Set<Integer> values = new HashSet<>();

        public Bot(String id) {
            this.id = id;
        }

        public void addValue(Integer val) {
            values.add(val);
            checkValues();
        }

        public void setLow(Bot low) {
            this.low = low;
            checkValues();
        }

        public void setHigh(Bot high) {
            this.high = high;
            checkValues();
        }

        private void checkValues() {
            if (values.size() >= 2) {
                if (low != null) {
                    Integer min = values.stream().min(Integer::compareTo).orElse(0);
                    low.addValue(min);
                    low = null;
                }
                if (high != null) {
                    Integer max = values.stream().max(Integer::compareTo).orElse(0);
                    high.addValue(max);
                    high = null;
                }
            }
        }
    }

}
