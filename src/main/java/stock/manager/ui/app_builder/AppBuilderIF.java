package stock.manager.ui.app_builder;

import stock.manager.ui.components_factory.GUIComponentIF;

import javax.swing.*;
import java.util.Map;

public interface AppBuilderIF {
    void initialize();

    void startFrame();

    void renderMenu();

    void renderTabbedPanes();

    void renderRegions();

    void startUI();

    void refresh();

    JFrame getFrame();

    Map<String, Map<String, GUIComponentIF>> getComponentMapper();

    void reloadData();
}
