package stock.manager.ui.components_factory;

import javax.swing.*;

public interface GUIComponentIF {
    void render();

    JPanel getPanel();

    void reset();

    String validateInput();

    Boolean enableVerticalScroll();

    Boolean enableHorizontalScroll();

    void updatePanel();
}
