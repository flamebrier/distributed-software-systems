package ru.flamebrier.lab01.randomlib;

public class Utils {
  /**
  * поменять два элемента массива местами
  */
  public static void arraySwap(int[] array, int i1, int i2) {
    int tmp = array[i1];
    array[i1] = array[i2];
    array[i2] = tmp;
  }

  /**
  * вывести массив
  */
  public static String resultToString(int[] arr) {
    String res = "";

    for (int item : arr){
      res += item + "\t";
    }
    return res;
  }
}
