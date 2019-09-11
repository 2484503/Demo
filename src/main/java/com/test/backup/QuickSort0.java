package com.test.backup;

public class QuickSort0 {

    public static void main(String[] args) {
        int[] a = {1, -1, 2, -3, 0, 9, 0, -4, 10, 1};

        quickSort(a, 0, a.length - 1);

        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }

    public static void quickSort(int[] arr, int low, int high) {
        int i, j, temp, t;
        if (low > high) {
            return;
        }
        i = low;
        j = high;

        while (i < j) {
            //先看右边，依次往左递减
            while (0 == arr[j] && i < j) {
                j--;
            }
            //再看左边，依次往右递增
            while (0 != arr[i] && i < j) {
                i++;
            }
            //如果满足条件则交换
            if (i < j) {
                t = arr[j];
                arr[j] = arr[i];
                arr[i] = t;
            }

        }
    }
}
