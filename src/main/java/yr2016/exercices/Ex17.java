package yr2016.exercices;

import jakarta.xml.bind.DatatypeConverter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Ex17 {

    static String passcode;
    static List<Character> OPEN_DOOR = new ArrayList<>();
    static MessageDigest md;
    static int targetX = 3, targetY = 3;

    public static void solution() throws IOException {
        try {
            OPEN_DOOR.add('b');
            OPEN_DOOR.add('c');
            OPEN_DOOR.add('d');
            OPEN_DOOR.add('e');
            OPEN_DOOR.add('f');

            md = MessageDigest.getInstance("MD5");
            partOne();
            partTwo();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String getInput() throws IOException {
        return new String(Files.readAllBytes(Paths.get("inputs/yr2016/ex17.txt")));
    }

    private static void partOne() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n\n---------------------------- 2016: Exercise 17 - 1 ----------------------------\n");
        passcode = getInput().replace("\r", "").replace("\n", "");
        String startHash = hash(passcode);
        Room start = createRoom(0, 0, "", startHash);
        List<Room> path = List.of(start);
        Room last = null;

        while (last == null) {
            path = step(passcode, path);
            last = path.stream().filter(room -> room.x == targetX && room.y == targetY).findFirst().orElse(null);
        }

        System.out.println("Solution: " + last.path);
    }

    private static void partTwo() throws IOException, NoSuchAlgorithmException {
        System.out.println("\n\n---------------------------- 2016: Exercise 17 - 2 ----------------------------\n");
        passcode = getInput().replace("\r", "").replace("\n", "");
        String startHash = hash(passcode);
        Room start = createRoom(0, 0, "", startHash);
        List<Room> path = List.of(start);

        int count = 0;
        int result = 0;
        while (path.size() > 0) {
            List<Room> next = step(passcode, path);
            path = next.stream().filter(room -> room.x != targetX || room.y != targetY).collect(Collectors.toList());
            count++;
            if (next.size() != path.size()) {
                result = count;
            }
        }

        System.out.println("Solution: " + result);
    }

    private static List<Room> step(String passcode, List<Room> path) {
        List<Room> next = new ArrayList<>();
        for (Room room : path) {
            if (room.up) {
                String newPath = room.path + 'U';
                next.add(createRoom(room.x, room.y - 1, newPath, hash(passcode + newPath)));
            }
            if (room.down) {
                String newPath = room.path + 'D';
                next.add(createRoom(room.x, room.y + 1, newPath, hash(passcode + newPath)));
            }
            if (room.left) {
                String newPath = room.path + 'L';
                next.add(createRoom(room.x - 1, room.y, newPath, hash(passcode + newPath)));
            }
            if (room.right) {
                String newPath = room.path + 'R';
                next.add(createRoom(room.x + 1, room.y, newPath, hash(passcode + newPath)));
            }
        }

        return next;
    }


    private static String hash(String path) {
        byte[] bytes = path.getBytes(StandardCharsets.UTF_8);
        md.update(bytes);
        byte[] md5 = md.digest();
        return DatatypeConverter.printHexBinary(md5).toLowerCase();
    }

    private static Room createRoom(int x, int y, String path, String hash) {
        return new Room(x, y, path,
                x > 0 && OPEN_DOOR.contains(hash.charAt(2)),
                x < 3 && OPEN_DOOR.contains(hash.charAt(3)),
                y > 0 && OPEN_DOOR.contains(hash.charAt(0)),
                y < 3 && OPEN_DOOR.contains(hash.charAt(1)));
    }

    private record Room(int x, int y, String path, boolean left, boolean right, boolean up, boolean down) {
    }

//    private static void
}
