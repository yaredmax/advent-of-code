package yr2021.exercises;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Ex4 {
    public static void solution() throws IOException {
        partOne();
        partTwo();
    }

    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2021/ex4.txt")));
    }

    private static void partOne() throws IOException {
        System.out.println("\n\n---------------------------- 2021: Exercise 4 - 1 ----------------------------\n");

        String[] bingo = getInput().replace("\r", "").replace("  ", " ").replace("\n ", "\n").split("\n\n");

        List<Integer> numbers = Arrays.stream(bingo[0].split(",")).map(Integer::parseInt).collect(Collectors.toList());

        List<List<List<Integer>>> boardList = getBoard(bingo);

        List<Integer> current = new ArrayList<>(numbers.subList(0, 5));

        List<List<Integer>> winner = null;
        int i = 5;
        while (winner == null) {
            final Optional<List<List<Integer>>> optional = boardList.stream().filter(board -> board.stream().anyMatch(current::containsAll)).findFirst();
            if (optional.isPresent()) {
                winner = optional.get();
            } else {
                current.add(numbers.get(i));
            }
            i++;
        }

        final Integer lastNumber = current.get(current.size() - 1);
        final Set<Integer> notMarked = winner.stream().flatMap(Collection::stream).filter(n -> !current.contains(n)).collect(Collectors.toSet());
        final Integer notMarkedSum = notMarked.stream().reduce(Integer::sum).orElse(0);

        System.out.println("Solution: " + lastNumber * notMarkedSum);
    }

    private static void partTwo() throws IOException {
        System.out.println("\n---------------------------- 2021: Exercise 4 - 2 ----------------------------\n");

        String[] bingo = getInput().replace("\r", "").replace("  ", " ").replace("\n ", "\n").split("\n\n");

        List<Integer> numbers = Arrays.stream(bingo[0].split(",")).map(Integer::parseInt).collect(Collectors.toList());

        List<List<List<Integer>>> boardList = getBoard(bingo);

        List<Integer> current = new ArrayList<>(numbers.subList(0, 4));

        List<List<Integer>> lastWinner = new ArrayList<>();
        int i = 4;
        while (boardList.size() > 0) {
            i++;
            current.add(numbers.get(i));
            final List<List<List<Integer>>> winners = boardList.stream().filter(board -> board.stream().anyMatch(current::containsAll)).collect(Collectors.toList());
            if (winners.size() > 0) {
                lastWinner = winners.get(0);
            }
            boardList.removeAll(winners);
        }

        final Integer lastNumber = current.get(current.size() - 1);
        final Set<Integer> notMarked = lastWinner.stream().flatMap(Collection::stream).filter(n -> !current.contains(n)).collect(Collectors.toSet());
        final Integer notMarkedSum = notMarked.stream().reduce(Integer::sum).orElse(0);

        System.out.println("Solution: " + lastNumber * notMarkedSum);
    }

    private static List<List<List<Integer>>> getBoard(String[] bingo){
        List<List<List<Integer>>> boardList = new ArrayList<>();
        for (int j = 1, bingoLength = bingo.length; j < bingoLength; j++) {
            String boardString = bingo[j];
            List<List<Integer>> board = new ArrayList<>();
            List<List<Integer>> boardMatrix = Arrays.stream(boardString.split("\n")).map(s ->
                    Arrays.stream(s.split(" "))
                            .map(Integer::parseInt).collect(Collectors.toList())).collect(Collectors.toList());
            for (int i = 0; i < boardMatrix.size(); i++) {
                List<Integer> row = boardMatrix.get(i);
                List<Integer> column = new ArrayList<>();
                for (List<Integer> rows : boardMatrix) {
                    column.add(rows.get(i));
                }
                board.add(row);
                board.add(column);
            }
            boardList.add(board);
        }

        return boardList;
    }
}
