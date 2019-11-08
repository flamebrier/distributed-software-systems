package ru.flamebrier.lab01.randomlib;

import java.util.Random;
import java.io.*;

public class Shuffle {
  private int quantity;

  public Shuffle() {
  }

  public Shuffle(int n) {
    quantity = n;
  }

  public void setQuantity(int value) {
    this.quantity = value;
  }

  public int getQuantity() {
    return quantity;
  }

  /**
  * перемешать массив чисел
  */
  public String shuffleNumbers() {
    int[] shuffledArray = new int[quantity];
    Random randomize = new Random(); //создаём генератор случайных чисел

    for (int i = 0; i < quantity; i++) {
      //сначала заполняем ключ последовательностью чисел, чтобы не было повторений
      shuffledArray[i] = i + 1;
    }
    for (int i = 0; i < quantity; i++) {
      //затем перемешиваем числа  в случайном порядке
      Utils.arraySwap(shuffledArray, randomize.nextInt(quantity), randomize.nextInt(quantity));
    }

    return Utils.resultToString(shuffledArray);
  }
}
