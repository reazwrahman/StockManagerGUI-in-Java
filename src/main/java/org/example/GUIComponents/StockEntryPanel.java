package org.example.GUIComponents;

import com.google.gson.Gson;
import org.example.Configs;
import org.example.GUIApp;
import org.example.Uitility.FileHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;


public class StockEntryPanel extends AbstractGUIComponent
        implements GUIComponentIF, ActionListener {

    private static final int COLUMNS = 10;
    private static final int WIDTH = 70;
    private static final int HEIGHT = 26;
    private static final int STOCK_PANEL_STARTS_AT = 2;
    private static final int DELTA_BETWEEN_STOCK_PANEL = 2;

    private GUIApp m_app;
    private Map<String, Map<String, Double>> m_stockMap;
    private FileHandler m_fileHandler;
    private JPanel m_buttonsPanel;
    private JButton m_addButton;
    private JButton m_deleteButton;
    private JButton m_submitButton;

    public StockEntryPanel(GUIApp app) {
        m_app = app;
        m_stockMap = new HashMap<>();
        m_fileHandler = new FileHandler();

        m_stockMap = m_fileHandler.readStockEntries();
    }

    @Override
    public void render() {
        // setup main panel
        m_panel = new JPanel();
        m_panel.setLayout(new BoxLayout(m_panel, BoxLayout.Y_AXIS));
        m_panel.setBorder(BorderFactory.createTitledBorder("View/Edit Current Stocks:"));

        // setup button panel
        m_buttonsPanel = new JPanel();
        m_addButton = new JButton("Add");
        m_addButton.setActionCommand("add");
        m_addButton.addActionListener(this);

        m_deleteButton = new JButton("Delete");
        m_deleteButton.setActionCommand("delete");
        m_deleteButton.addActionListener(this);

        m_submitButton = new JButton("Save");
        m_submitButton.setActionCommand("submit");
        m_submitButton.addActionListener(this);

        m_buttonsPanel.add(m_addButton);
        m_buttonsPanel.add(m_deleteButton);
        m_buttonsPanel.add(m_submitButton);
        m_panel.add(m_buttonsPanel);

        addEntryLabels(); // add a panel for the columns
        fillStockEntry();
    }

    private void addEntryLabels() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel label1 = new JLabel("Ticker");
        JLabel label2 = new JLabel("Quantity");
        JLabel label3 = new JLabel("Total Cost");
        Dimension textFieldSize = new Dimension(100, 25);
        label1.setPreferredSize(textFieldSize);
        label2.setPreferredSize(textFieldSize);
        label3.setPreferredSize(textFieldSize);

        panel.add(label1);
        panel.add(Box.createHorizontalStrut(80)); // Gap between fields
        panel.add(label2);
        panel.add(Box.createHorizontalStrut(55));
        panel.add(label3);

        // Wrap the panel in a top-aligned container
        JPanel topAlignedPanel = new JPanel();
        topAlignedPanel.setLayout(new BorderLayout());
        topAlignedPanel.add(panel, BorderLayout.NORTH);

        m_panel.add(topAlignedPanel);

    }

    private void fillStockEntry(){
        for (String ticker: m_stockMap.keySet()) {
            JPanel stockPanel = addStockEntry();
            JTextField tickerField = (JTextField) stockPanel.getComponent(0);
            JTextField qtyField = (JTextField) stockPanel.getComponent(2);
            JTextField costField = (JTextField) stockPanel.getComponent(4);

            tickerField.setText(ticker);
            tickerField.setEditable(true);
            qtyField.setText(m_stockMap.get(ticker).get("quantity").toString());
            costField.setText(m_stockMap.get(ticker).get("totalCost").toString());
        }
    }

    private JPanel addStockEntry() {
        JPanel stockPanel = new JPanel();
        stockPanel.setLayout(new BoxLayout(stockPanel, BoxLayout.X_AXIS));
        stockPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        stockPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        JTextField textField1 = new JTextField();
        JTextField textField2 = new JTextField();
        JTextField textField3 = new JTextField();
        Dimension textFieldSize = new Dimension(100, 25);
        textField1.setPreferredSize(textFieldSize);
        textField2.setPreferredSize(textFieldSize);
        textField3.setPreferredSize(textFieldSize);

        textField1.setMaximumSize(textFieldSize); // Restrict maximum size
        textField2.setMaximumSize(textFieldSize);
        textField3.setMaximumSize(textFieldSize);

        // Add text fields to panel with gaps
        stockPanel.add(textField1);
        stockPanel.add(Box.createHorizontalStrut(10)); // Gap between fields
        stockPanel.add(textField2);
        stockPanel.add(Box.createHorizontalStrut(10));
        stockPanel.add(textField3);
        stockPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        // Wrap the panel in a top-aligned container
        JPanel topAlignedPanel = new JPanel();
        topAlignedPanel.setLayout(new BorderLayout());
        topAlignedPanel.add(stockPanel, BorderLayout.NORTH);

        m_panel.add(topAlignedPanel);
        m_panel.add(Box.createVerticalStrut(5));

        return stockPanel;
    }

    private boolean deleteStockEntry() {
        java.awt.Component[] components = m_panel.getComponents();
        int length = components.length;
        if (length > STOCK_PANEL_STARTS_AT) { // 0: buttons, 1: labels
            m_panel.remove(length - 1); // gap filler (Box$Filler)
            m_panel.remove(length - 2); // stock entry panel
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        boolean panelLeft = deleteStockEntry();
        while (panelLeft) {
            panelLeft = deleteStockEntry();
        }
        m_app.refresh();
    }

    private void readEntries() {
        String validationResult = validateInput();
        if (!validationResult.isEmpty()) {
            JOptionPane.showMessageDialog(m_app.frame,
                    validationResult,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            saveEntries();
        }
    }

    @Override
    public String validateInput() {
        java.awt.Component[] components = m_panel.getComponents();
        if (components.length > STOCK_PANEL_STARTS_AT) {
            for (int i = STOCK_PANEL_STARTS_AT; i < components.length; i += DELTA_BETWEEN_STOCK_PANEL) {
                JPanel parentPanel = (JPanel) components[i];
                JPanel panel = (JPanel) parentPanel.getComponent(0);

                JTextField tickerField = (JTextField) panel.getComponent(0);
                JTextField qtyField = (JTextField) panel.getComponent(2);
                JTextField costField = (JTextField) panel.getComponent(4);

                if (tickerField.getText().isBlank() || qtyField.getText().isBlank() || costField.getText().isBlank()) {
                    return "Stock entries can not be empty";
                }

                try {
                    Float quantity = Float.parseFloat(qtyField.getText());
                    Float cost = Float.parseFloat(costField.getText());
                } catch (Exception e) {
                    return "Stock quantity and total cost must be valid numbers";
                }

            }
        }
        return "";
    }

    @Override
    public Boolean enableVerticalScroll() {
        return true;
    }

    private void saveEntries() {
        java.awt.Component[] components = m_panel.getComponents();

        if (components.length > STOCK_PANEL_STARTS_AT) { // 0: buttons, 1: labels
            for (int i = STOCK_PANEL_STARTS_AT; i < components.length; i += DELTA_BETWEEN_STOCK_PANEL) {

                JPanel parentPanel = (JPanel) components[i];
                JPanel panel = (JPanel) parentPanel.getComponent(0);

                JTextField tickerField = (JTextField) panel.getComponent(0);
                JTextField qtyField = (JTextField) panel.getComponent(2);
                JTextField costField = (JTextField) panel.getComponent(4);

                String ticker = tickerField.getText();
                Double quantity = Double.parseDouble(qtyField.getText());
                Double cost = Double.parseDouble(costField.getText());

                insertToMap(ticker.toUpperCase(), quantity, cost);
            } // end of for loop

            writeToJson();
            JOptionPane.showMessageDialog(m_app.frame,
                    "Data Submitted",
                    "Submitted",
                    JOptionPane.INFORMATION_MESSAGE);
        } // end of if statement
    }

    private void insertToMap(String ticker, double quantity, double cost) {
        Map<String, Double> innerMap = new HashMap<>();
        if (quantity == 0 && cost == 0) { // remove this stock if applicable, or don't enter at all
            if (m_stockMap.get(ticker) != null) {
                m_stockMap.remove(ticker);
            }
            return;
        }
        if (m_stockMap.get(ticker) == null) {
            innerMap.put("quantity", quantity);
            innerMap.put("totalCost", cost);
            m_stockMap.put(ticker, innerMap);
        } else {
            double existingQuantity = m_stockMap.get(ticker).get("quantity");
            double existingCost = m_stockMap.get(ticker).get("totalCost");
            innerMap.put("quantity", quantity + existingQuantity);
            innerMap.put("totalCost", cost + existingCost);
            m_stockMap.put(ticker, innerMap);
        }
    }

    private void writeToJson() {
        Gson gson = new Gson();
        String json = gson.toJson(m_stockMap);
        m_fileHandler.writeToFile(Configs.STOCK_ENTRY_FILE_NAME, json);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add")) {
            this.addStockEntry();
            m_app.refresh();
        } else if (e.getActionCommand().equals("delete")) {
            deleteStockEntry();
            m_app.refresh();
        } else if (e.getActionCommand().equals("submit")) {
            readEntries();
        }
    }
}
