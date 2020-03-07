package com.lilin;

/**
 * java中的经典排序算法（数组排序，二叉树排序）
 */
public class ClassicSortAlgorithm {


    public static void main(String[] args) {
        int[] arr = {7, 3, 2, 9, 34, 5, 3, 67, 2, 1};
        printArr(arr);
        //directInsert(arr);
        //bubblingSort(arr);
        //chooseSort(arr);
        //System.out.println();
        System.out.println(midFind(arr, 34));
        printArr(arr);
    }

    /**
     * 直接插入算法
     *
     * @param data
     */
    public static void directInsert(int[] data) {

        int temp;

        for (int i = 1; i < data.length; i++) {
            //temp相当于插入的那个比较对象
            temp = data[i];
            for (int j = i - 1; j >= 0; j--) {
                if (temp < data[j]) {
                    data[j + 1] = data[j];
                    data[j] = temp;
                }
            }
        }
    }

    /**
     * 冒泡排序
     *
     * @param data
     */
    public static void bubblingSort(int[] data) {
        for (int i = 0; i < data.length - 1; i++) {
            for (int j = 1; j < data.length - i; j++) {
                if (data[j] < data[j - 1]) {
                    int temp = data[j - 1];
                    data[j - 1] = data[j];
                    data[j] = temp;
                }
            }
        }
    }

    /**
     * 选择排序
     *
     * @param data
     */
    public static void chooseSort(int[] data) {
        for (int i = 0; i < data.length - 1; i++) {
            for (int j = i + 1; j < data.length; j++) {
                if (data[i] > data[j]) {
                    int temp = data[i];
                    data[i] = data[j];
                    data[j] = temp;
                }
            }
        }
    }

    /**
     * 二分查找法
     * 返回要查找的数在数组中的角标
     * @param data
     * @param num
     * @return
     */
    public static int midFind(int[] data,int num){
        //首先对数组进行排序
        chooseSort(data);
        int low=0;
        int up=data.length-1;
        while (low <=up){
            int mid=(low+up)/2;
            if(num<data[mid]){
                up=mid-1;
            }else if(num>data[mid]){
                low=mid+1;
            }else{
                System.out.println(mid);
                return mid;
            }
        }
        return -1;
    }

    /**
     * 打印数组
     *
     * @param objs
     */
    public static void printArr(int[] objs) {
        for (Object obj : objs) {
            System.out.print(obj + "-");
        }
    }
}
