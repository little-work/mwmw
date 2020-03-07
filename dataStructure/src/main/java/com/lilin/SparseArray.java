package com.lilin.mwmw.dataStructure;

public class SparseArray {


    public static void main(String[] args) {
        //array2ToSparse();
        sparseArrayToArray2();

    }

    public static void array2ToSparse() {
        //创建一个二维数组当做棋盘
        int[][] checkerboard = new int[11][11];
        //保存别人下过棋子后的棋盘  1 表示黑子  2  表示白子
        checkerboard[2][3] = 1;
        checkerboard[4][3] = 1;
        checkerboard[5][7] = 2;
        checkerboard[7][10] = 1;
        checkerboard[9][5] = 2;
        checkerboard[10][7] = 2;


        int temp = 0;
        int temp2 = 0;

        //遍历棋盘  或者其中的有效值
        System.out.println("原数组");
        for (int[] arrs : checkerboard) {
            for (int arr : arrs) {
                if (arr != 0) {
                    temp++;
                }
                System.out.printf("%d\t", arr);
            }
            System.out.println();
        }

        //创建一个稀疏数组
        int[][] sparseArray = new int[temp + 1][3];
        sparseArray[temp2][0] = 11;
        sparseArray[temp2][1] = 11;
        sparseArray[temp2][2] = temp;

        //遍历棋盘  获取有效值的位置  存进稀疏数组中
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (checkerboard[i][j] != 0) {
                    temp2++;
                    sparseArray[temp2][0] = i;
                    sparseArray[temp2][1] = j;
                    sparseArray[temp2][2] = checkerboard[i][j];
                }
            }
        }
        System.out.println("稀疏数组");
        for (int[] arrs : sparseArray) {
            for (int arr : arrs) {
                System.out.printf("%d\t", arr);
            }
            System.out.println();
        }
    }

    public static void sparseArrayToArray2() {
        int[][] sparseArray = {{11, 11, 6}, {2, 3, 1}, {4, 3, 1}, {5, 7, 2}, {7, 10, 1}, {9, 5, 2}, {10, 7, 2}};

        //遍历稀疏数组
        System.out.println("转换前的稀疏数组");
        for(int[] arrs:sparseArray){
            for (int arr:arrs){
                System.out.printf("%d\t", arr);
            }
            System.out.println();
        }
        int[][] array2 = new int[sparseArray[0][0]][sparseArray[0][1]];
        for (int i = 1; i < sparseArray.length; i++) {
            array2[sparseArray[i][0]][sparseArray[i][1]] = sparseArray[i][2];
        }
        //转换后的二维数组
        System.out.println("转换后的二维数组");
        for(int[] arrs:array2){
            for (int arr:arrs){
                System.out.printf("%d\t", arr);
            }
            System.out.println();
        }

    }
}
