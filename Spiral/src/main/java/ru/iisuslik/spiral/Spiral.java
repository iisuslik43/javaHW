package ru.iisuslik.spiral;

/**
 * Class working with matrix that has two methods:
 * 1)print odd square matrix
 * 2)sort matrix by cols
 */
public class Spiral {

    /**
     * Prints your odd square matrix
     *
     * @param matrix matrix you want to print
     */
    public static String printLikeSpiral(int[][] matrix) {
        StringBuilder buf = new StringBuilder();
        if (matrix.length % 2 != 1 && matrix[0].length % 2 != 1) {
            System.out.println("Even array");
            return null;
        }
        if (matrix.length != matrix[0].length) {
            System.out.println("n != m");
            return null;
        }
        int n = matrix.length;
        int middleIndex = n / 2;
        for (int i = 0; i <= n / 2; i++) {
            if (i == 0) {
                buf.append(matrix[middleIndex][middleIndex]);
                buf.append(" ");
                continue;
            }
            int beginIndex = middleIndex - i;
            int iNow, jNow;
            iNow = jNow = beginIndex;

            do {
                buf.append(matrix[iNow][jNow + 1]);
                buf.append(" ");
                jNow++;
            }
            while (jNow < middleIndex + i);

            do {
                buf.append(matrix[iNow + 1][jNow]);
                buf.append(" ");
                iNow++;
            }
            while (iNow < middleIndex + i);

            do {
                buf.append(matrix[iNow][jNow - 1]);
                buf.append(" ");
                jNow--;
            }
            while (jNow > middleIndex - i);

            do {
                buf.append(matrix[iNow - 1][jNow]);
                buf.append(" ");
                iNow--;
            }
            while (iNow > middleIndex - i);

        }
        return buf.toString();
    }

    private static int[][] transpose(int[][] matrix) {
        int[][] trMatrix = new int[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                trMatrix[j][i] = matrix[i][j];
            }
        }
        return trMatrix;
    }

    private static void swapRows(int index1, int index2, int[][] matrix) {
        int temp[] = matrix[index1];
        matrix[index1] = matrix[index2];
        matrix[index2] = temp;
    }

    /**
     * Sorts matrix's cols, compares elements of matrix[0]
     *
     * @param matrix matrix you want to sort
     */
    public static void sortMatrixCols(int[][] matrix) {
        int[][] trMatrix = transpose(matrix);
        for (int i = 0; i < trMatrix.length; i++) {
            int min = trMatrix[i][0];
            int indexMin = i;
            for (int j = i; j < trMatrix.length; j++) {
                if (trMatrix[j][0] < min) {
                    min = trMatrix[j][0];
                    indexMin = j;
                }
            }
            swapRows(i, indexMin, trMatrix);
        }
        for (int i = 0; i < trMatrix.length; i++) {
            for (int j = 0; j < trMatrix[0].length; j++) {
                matrix[j][i] = trMatrix[i][j];
            }
        }
    }
}
