package stock.manager.ui;

import stock.manager.ui.app_builder.AppDirector;
import stock.manager.ui.app_builder.StockAppBuilder;

public class Main {
    public static void main(String[] args) {

        AppDirector director = new AppDirector(new StockAppBuilder());
        director.constructUI();
        System.setProperty("apple.laf.useScreenMenuBar", "true");

    }
}