package utils;

public class ArrayUtils {
    public static <T> void rotateMatrix(T[][] matrix) {
        int row = matrix.length;
        //first find the transpose of the matrix.
        for (int i = 0; i < row; i++) {
            for (int j = i; j < row; j++) {
                T temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        //reverse each row
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row / 2; j++) {
                T temp = matrix[i][j];
                matrix[i][j] = matrix[i][row - 1 - j];
                matrix[i][row - 1 - j] = temp;
            }
        }
    }

    public static void rotateMatrix(int[][] matrix) {
        int row = matrix.length;
        //first find the transpose of the matrix.
        for (int i = 0; i < row; i++) {
            for (int j = i; j < row; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        //reverse each row
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row / 2; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][row - 1 - j];
                matrix[i][row - 1 - j] = temp;
            }
        }
    }

    public static char[][] rotateMatrix(char[][] matrix) {
        char[][] result = new char[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                result[j][i] = matrix[i][j];
            }
        }
        return result;
    }

    public static void print(boolean[][] matrix) {
        for (boolean[] row : matrix) {
            for (boolean b : row) {
                System.out.print(b + "\t");
            }
            System.out.println();
        }
    }

    public static void print(boolean[][] matrix, char t, char f) {
        for (boolean[] row : matrix) {
            for (boolean b : row) {
                System.out.print(" " + (b ? t : f));
            }
            System.out.println();
        }
    }
}
