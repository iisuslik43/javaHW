package ru.iisuslik.spiral;

import org.junit.Test;

import static org.junit.Assert.*;

public class SpiralTest {

    /**Try to print 1X1 matrix*/
    @Test
    public void printLikeSpiralOne() throws Exception {
        int[][] matrix = {{1}};
        String result = Spiral.printLikeSpiral(matrix);
        assertEquals("1 ", result);
    }

    /**Try to print 3X3 matrix*/
    @Test
    public void printLikeSpiralSimple() throws Exception {
        int[][] matrix = {{9, 2, 3},
                          {8, 1, 4},
                          {7, 6, 5}};
        String result = Spiral.printLikeSpiral(matrix);
        assertEquals("1 2 3 4 5 6 7 8 9 ", result);
    }

    /**Try to print 5X5 matrix*/
    @Test
    public void printLikeSpiralBigger() throws Exception {
        int[][] matrix = {{25, 10, 11, 12, 13},
                          {24, 9, 2, 3, 14},
                          {23, 8, 1, 4, 15},
                          {22, 7, 6, 5, 16},
                          {21, 20, 19, 18, 17}};
        String result = Spiral.printLikeSpiral(matrix);
        StringBuilder buf = new StringBuilder();
        for (int i = 1; i <= 25; i++) {
            buf.append(i);
            buf.append(" ");
        }
        assertEquals(buf.toString(), result);
    }

    /**Try to print 3X3 random matrix*/
    @Test
    public void printLikeSpiralRandom() throws Exception {
        int[][] matrix = {{2, 11, 4},
                          {66, 22, 9},
                          {34, 52, 43}};
        String result = Spiral.printLikeSpiral(matrix);
        assertEquals("22 11 4 9 43 52 34 66 2 ", result);
    }


    /**Try to sort sorted matrix*/
    @Test
    public void sortMatrixColsMatrixSorted() throws Exception {
        int[][] matrix = {{1, 2, 3},
                          {4, 5, 6},
                          {7, 8, 9}};
        Spiral.sortMatrixCols(matrix);
        int[][] expected = {{1, 2, 3},
                            {4, 5, 6},
                            {7, 8, 9}};
        assertArrayEquals(expected, matrix);
    }

    /**Try to print 3X3 matrix*/
    @Test
    public void sortMatrixColsSimple() throws Exception {
        int[][] matrix = {{3, 2, 1},
                          {4, 5, 6},
                          {7, 8, 9}};
        Spiral.sortMatrixCols(matrix);
        int[][] expected = {{1, 2, 3},
                            {6, 5, 4},
                            {9, 8, 7}};
        assertArrayEquals(expected, matrix);
    }

    /**Try to sort 3X4 matrix*/
    @Test
    public void sortMatrixColsNotSquare() throws Exception {
        int[][] matrix = {{3, 2, 0, 1},
                          {4, 5, 6, 43},
                          {7, 8, 9, 22}};
        Spiral.sortMatrixCols(matrix);
        int[][] expected = {{0, 1, 2, 3},
                            {6, 43, 5, 4},
                            {9, 22, 8, 7}};
        assertArrayEquals(expected, matrix);
    }
}