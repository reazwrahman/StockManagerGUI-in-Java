package stock.manager.ui.components_factory.stock_entry;

import javax.swing.*;
import java.awt.*;

class PanelRow {

    public JLabel counter;
    public JTextField ticker;
    public JTextField qty;
    public JTextField cost;

    public PanelRow(String count) {
        counter = new JLabel(count);

        ticker = new JTextField();
        qty = new JTextField();
        cost = new JTextField();
        Dimension textFieldSize = new Dimension(100, 25);
        ticker.setPreferredSize(textFieldSize);
        qty.setPreferredSize(textFieldSize);
        cost.setPreferredSize(textFieldSize);

        ticker.setMaximumSize(textFieldSize); // Restrict maximum size
        qty.setMaximumSize(textFieldSize);
        cost.setMaximumSize(textFieldSize);
    }
}