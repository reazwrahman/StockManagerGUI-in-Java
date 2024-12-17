package org.example.GUIComponents;

import javax.swing.*;
import java.awt.*;

public class StockEntryInstruction extends AbstractGUIComponent
        implements GUIComponentIF{

    @Override
    public void render() {
        m_panel = new JPanel();
        String instructions =
                "<html>" +
                        "1) Click 'Add' to enter a new stock, or 'Delete' to remove the bottom-most stock from the list.<br>" +
                        "2) Make as many edits as you'd like, but be cautious before clicking 'Save'. Saving is irreversible.<br>" +
                        "3) From the 'View' menu, select 'Reset' to clear all stocks from your panel.<br>" +
                        "4) From the 'View' menu, select 'Reload' to repopulate the panel with saved data." +
                        "</html>";
        JLabel label = new JLabel(instructions, JLabel.CENTER);
        m_panel.setPreferredSize(new Dimension(100, 80));
        m_panel.add(label);
    }
}
