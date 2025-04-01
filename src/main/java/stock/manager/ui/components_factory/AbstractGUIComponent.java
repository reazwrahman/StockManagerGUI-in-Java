package stock.manager.ui.components_factory;

import javax.swing.*;

public abstract class AbstractGUIComponent implements GUIComponentIF {
    protected JPanel m_panel = null;

    @Override
    public JPanel getPanel() {
        if (m_panel == null) {
            render();
        }
        return m_panel;
    }

    @Override
    public void reset() {
    }

    @Override
    public String validateInput() {
        return "";
    }

    @Override
    public Boolean enableVerticalScroll() {
        return false;
    }

    @Override
    public Boolean enableHorizontalScroll() {
        return false;
    }

    @Override
    public void updatePanel() {
    }

}
