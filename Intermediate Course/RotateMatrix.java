package com.javatest;

public class RotateMatrix {
    /**
     * 给定一个正方形矩阵，只用有限几个变量，实现矩阵中每个位置的数顺时针转动
     * 90度，比如如下的矩阵
     * 0 1 2 3
     * 4 5 6 7
     * 8 9 10 11
     * 12 13 14 15
     * 矩阵应该被调整为：
     * 12 8 4 0
     * 13 9 5 1
     * 14 10 6 2
     * 15 11 7 3
     */
    public static void rotate(int[][] m) {
        int tR = 0;
        int tC = 0;
        int dR = m.length - 1;
        int dC = m[0].length - 1;
        while (tC < dC) {
            rotateEdge(m, tR++, tC++, dR--, dC--);
        }
    }

    public static void rotateEdge(int[][] m, int tR, int tC, int dR, int dC) {
        int times = dC - tC;
        int tmp = 0;
        //逆时针旋转
//        for (int i = 0; i < times; i++) {
//            tmp = m[tR][tC + i];
//            m[tR][tC + i] = m[tR + i][dC];
//            m[tR + i][dC] = m[dR][dC - i];
//            m[dR][dC - i] = m[dR - i][tC];
//            m[dR - i][tC] = tmp;
//        }
        // 顺时针旋转
        for (int i = 0; i != times; i++) {
            tmp = m[tR][tC + i];
            m[tR][tC + i] = m[dR - i][tC];
            m[dR - i][tC] = m[dR][dC - i];
            m[dR][dC - i] = m[tR + i][dC];
            m[tR + i][dC] = tmp;
        }
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i != matrix.length; i++) {
            for (int j = 0; j != matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 },
                { 13, 14, 15, 16 } };
        printMatrix(matrix);
        rotate(matrix);
        System.out.println("=========");
        printMatrix(matrix);
    }
}
