package ru.flamebrier.lab01.randomapp;

import ru.flamebrier.lab01.randomlib.*;

public class Application {

  public static void main(String[] args) {
    SuggestBuilder dialog = new SuggestBuilder();
    dialog.build();
    System.out.println("Привет, мир!");
  }
}
