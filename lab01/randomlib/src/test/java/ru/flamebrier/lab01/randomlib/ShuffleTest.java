package ru.flamebrier.lab01.randomlib;

import org.junit.*;

/**
* Unit test for simple App.
*/
public class ShuffleTest  {  
  @Test
  public void testShuffle1() {
    System.out.println("@Test - run testShuffle1");

    Shuffle shuf = new Shuffle();
    String value;
    String expectedResult;

    for (int number = 0; number < 1000; number += 100) {
      expectedResult = "";
      shuf.setQuantity(number);
      value = shuf.shuffleNumbers();

      for (int i = 0; i < number; i++) {
        expectedResult += Integer.toString(i + 1) + "\t";
      }

      Assert.assertEquals("Shuffle numbers check", expectedResult.length(), value.length());
    }
  }

  @Test
  public void testShuffle2() {
    System.out.println("@Test - run testShuffle2");

    int expectedResult = 0;
    int counterDontFind = 0;
    Shuffle shuf = new Shuffle();
    String value;

    for (int number = 0; number < 1000; number += 100) {
      shuf.setQuantity(number);
      value = shuf.shuffleNumbers();

      for (int i = 0; i < number; i++) {
        if (value.indexOf(Integer.toString(i + 1)) < 0) {
          counterDontFind++;
        }
      }
    }

    Assert.assertEquals("Shuffle numbers check", expectedResult, counterDontFind);
  }
}
