import java.util.Random;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class Matrix {

    public static void main(String[] args) {
        int[][] matrix = generateMatrix(10000);

        ToIntFunction<int[][]> summator = Matrix::sumByCol;
        if (Boolean.getBoolean("sumByRow")) {
            summator = Matrix::sumByRow;
        }
        int sum = summator.applyAsInt(matrix);

        System.out.printf("Matrix sum: %d\n", sum);
    }

    private static int[][] generateMatrix(int size) {
        Random rnd = new Random(1234);
        int[][] m = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                m[i][j] = rnd.nextInt();
            }
        }
        return m;
    }

    private static int sumByRow(int[][] m) {
        int sum = 0;
        for (int row = 0; row < m.length; row++) {
            for (int col = 0; col < m[row].length; col++) {
                sum += m[row][col];
            }
        }
        return sum;
    }

    private static int sumByCol(int[][] m) {
        int sum = 0;
        for (int col = 0; col < m.length; col++) {
            for (int row = 0; row < m[col].length; row++) {
                sum += m[row][col];
            }
        }
        return sum;
    }
}
