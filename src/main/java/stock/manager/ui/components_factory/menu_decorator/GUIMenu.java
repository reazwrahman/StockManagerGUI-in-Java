package stock.manager.ui.components_factory.menu_decorator;

import stock.manager.ui.app_builder.AppBuilderIF;
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
        AbstractMenuDecorator mainMenu = null;
        mainMenu = new ViewDecorator(m_appBuilder, mainMenu, "View"); // View behavior is added
        mainMenu = new StockEntryDecorator(m_appBuilder, mainMenu, "Stock Entry"); // Stock Entry is wrapped on top of view
        mainMenu = new AnalyzeDecorator(m_appBuilder, mainMenu, "Analyze");
        mainMenu = new HelpDecorator(m_appBuilder, mainMenu, "Help");
        frame.setJMenuBar(mainMenu.m_menuBar);
    }
}
