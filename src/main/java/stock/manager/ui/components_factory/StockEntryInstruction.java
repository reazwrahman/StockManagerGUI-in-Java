package stock.manager.ui.components_factory;

import javax.swing.*;
import java.awt.*;

public class StockEntryInstruction extends AbstractGUIComponent
        implements GUIComponentIF {

    @Override
    public void render() {
        m_panel = new JPanel();
        String instructions =
                "<html>" +
                        "1) In the 'Enter Stock' panel, click 'Add' to enter a new stock, or 'Delete' to remove the bottom-most stock from the list.<br>" +
                        "<br>" +
                        "2) Make as many edits as you'd like, but be cautious before clicking 'Save'. Saving is irreversible.<br>" +
                        "<br>" +
                        "3) From the 'View' menu, select 'Reset' to clear all stocks from your panel <br>" +
                        "<br>" +
                        "&nbsp;&nbsp;&nbsp;&nbsp;3.1) select 'Reload' to repopulate the panel with saved data. <br>" +
                        "<br>" +
                        "4) To delete a particular stock, set its quantity and cost to 0 and hit 'Save' <br>" +
                        "<br>" +
                        "5) Use the 'Analyze' menu from top to analyze the data based on return rate, gain, cost etc. <br>" +
                        "<br>" +
                        "&nbsp;&nbsp;&nbsp;&nbsp; 5.1) View the results in the 'Analysis' panel. <br>" +

                        "</html>";
        JLabel label = new JLabel(instructions, JLabel.CENTER);
        m_panel.setPreferredSize(new Dimension(100, 90));
        m_panel.add(label);
    }

    @Override
    public Boolean enableVerticalScroll() {
        return true;
    }

    @Override
    public Boolean enableHorizontalScroll() {
        return true;
    }

    @Override
    public String getDescription() {
        String description = "StockEntryInstruction: Displays the instructions for entering/updating/deleting " +
                "stock entries for first time users";
        System.out.println(description);
        return description;
    }
}
