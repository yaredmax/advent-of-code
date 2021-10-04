package utils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class StringUtils {
    public static Character mostCommonChar(String s) {
        char maxchar = ' ';
        int maxcnt = 0;
        int[] charCount = new int[128];
        for (int i = s.length() - 1; i >= 0; i--) {
            char ch = s.charAt(i);
            if (++charCount[ch] >= maxcnt) {
                maxcnt = charCount[ch];
                maxchar = ch;
            }
        }
        return maxchar;
    }

    public static Character mostCommonChar(char[] chars) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : chars) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        return map.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(' ');
    }

    public static Character lessCommonChar(char[] chars) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : chars) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        return map.entrySet().stream().min(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(' ');
    }
}
