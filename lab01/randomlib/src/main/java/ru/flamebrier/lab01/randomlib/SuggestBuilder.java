package ru.flamebrier.lab01.randomlib;

import java.io.*;
import java.util.Scanner;
import java.util.Properties;
import java.util.InputMismatchException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Locale;

public class SuggestBuilder {

  private static final String BUNDLE_BASE_NAME = "suggest";

  public static final String PATH_TO_PROPERTIES_RU = "src/main/resources/suggest_ru.properties";
  public static final String PATH_TO_PROPERTIES_EN = "src/main/resources/suggest_en.properties";

  private static boolean isRu(Scanner in) {
    String ans;
    System.out.println("Is russian your language? (y/n)");
    try {
      ans = in.next("[yn]");
    } catch (InputMismatchException e) {
      ans = "n";
    }
    if (ans.compareTo("y") == 0) {
      return true;
    } else {
      return false;
    }
  }

  private static void askUser(Scanner in, ResourceBundle prop) {
    String numbersQuantity = prop.getString("numbersQuantity");
    String changeNumbersQuantity = prop.getString("changeNumbersQuantity");
    String cont = prop.getString("continue");

    System.out.println(numbersQuantity);
    Shuffle shuff = new Shuffle(in.nextInt());

    boolean change = false;
    String ans = "y";

    while (ans.compareTo("y") == 0) {
      if (change) {
        System.out.println(changeNumbersQuantity);
        try {
          ans = in.next("[yn]");
        } catch (InputMismatchException e) {
          ans = "n";
        }
        if (ans.compareTo("y") == 0) {
          System.out.println(numbersQuantity);
          shuff.setQuantity(in.nextInt());
        }
      } else {
        change = true;
      }
      System.out.println(shuff.shuffleNumbers());
      System.out.println(cont);
      try {
        ans = in.next("[yn]");
      } catch (InputMismatchException e) {
        ans = "n";
      }
    }
  }

  public static void build() {
    Locale locale;
    ResourceBundle prop;
    FileInputStream fileInputStream;
    Scanner in = new Scanner(System.in);

    if (isRu(in)) {
      locale = new Locale("ru", "RU");
      prop = PropertyResourceBundle.getBundle(BUNDLE_BASE_NAME, locale);
    } else {
      locale = new Locale("en", "US");
      prop = PropertyResourceBundle.getBundle(BUNDLE_BASE_NAME, locale);
    }
    askUser(in, prop);
  }
}
