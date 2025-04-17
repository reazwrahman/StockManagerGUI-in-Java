package stock.manager.ui.components_factory.stock_entry;

import javax.swing.*;

class RowValues {
    public String counter;
    public String ticker;
    public String qty;
    public String cost;

    public RowValues(JPanel panel) {
        try {
            counter = ((JLabel) panel.getComponent(0)).getText();
            ticker = ((JTextField) panel.getComponent(2)).getText();
            qty = ((JTextField) panel.getComponent(4)).getText();
            cost = ((JTextField) panel.getComponent(6)).getText();
        } catch (Exception ex) {
            throw new IllegalArgumentException("Inputted panel can not be converted to row values");
        }
    }
}
