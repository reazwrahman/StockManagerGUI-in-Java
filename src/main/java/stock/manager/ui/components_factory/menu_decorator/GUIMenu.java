package stock.manager.ui.components_factory.menu_decorator;

import stock.manager.ui.app_builder.AppBuilderIF;
import stock.manager.ui.app_builder.StockAppBuilder;
import stock.manager.ui.components_factory.AbstractGUIComponent;
import stock.manager.ui.components_factory.GUIComponentIF;

import javax.swing.*;

public class GUIMenu extends AbstractGUIComponent implements GUIComponentIF {

    private final JFrame frame;
    AppBuilderIF m_appBuilder;

    public GUIMenu(AppBuilderIF appBuilder) {
        m_appBuilder = appBuilder;
        frame = appBuilder.getFrame();
    }

    @Override
    public void render() {
        AbstractMenu mainMenu = new MenuForView(m_appBuilder, null);
        mainMenu = new MenuForAnalyze(m_appBuilder, mainMenu);
        mainMenu = new MenuForHelp(m_appBuilder, mainMenu);
        frame.setJMenuBar(mainMenu.m_menuBar);
    }
}
