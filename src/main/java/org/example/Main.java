package org.example;

import org.example.StockManager.StockSorter;

public class Main {
    public static void main(String[] args) {

//        GUIApp app = new GUIApp();
//        app.start();
//        System.setProperty("apple.laf.useScreenMenuBar", "true");

        // test stock sorter
        StockSorter ss = new StockSorter();
        ss.updateData();
        System.out.println(ss.toString());
    }
}