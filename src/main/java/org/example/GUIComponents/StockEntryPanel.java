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
    GUIApp m_app;
    Map<String, Map<String, Float>> m_stockEntries;
    FileHandler m_fileHandler;
    Configs m_configs;
    private JPanel m_buttonsPanel;
    private JButton m_addButton;
    private JButton m_deleteButton;
    private JButton m_submitButton;
    private JPanel m_stocksHolder;

    public StockEntryPanel(GUIApp app) {
        m_app = app;
        m_stockEntries = new HashMap<>();
        m_fileHandler = new FileHandler();
        m_configs = new Configs();
    }

    @Override
    public void render() {
        // setup main panel
        m_panel = new JPanel();
        m_panel.setLayout(new BoxLayout(m_panel, BoxLayout.Y_AXIS));
        m_panel.setBorder(BorderFactory.createEmptyBorder(3, 40, 3, 20));

        // setup button panel
        m_buttonsPanel = new JPanel();
        m_addButton = new JButton("Add");
        m_addButton.setActionCommand("add");
        m_addButton.addActionListener(this);

        m_deleteButton = new JButton("Delete");
        m_deleteButton.setActionCommand("delete");
        m_deleteButton.addActionListener(this);

        m_submitButton = new JButton("Submit");
        m_submitButton.setActionCommand("submit");
        m_submitButton.addActionListener(this);

        m_buttonsPanel.add(m_addButton);
        m_buttonsPanel.add(m_deleteButton);
        m_buttonsPanel.add(m_submitButton);
        m_panel.add(m_buttonsPanel);

        addEntryLabels();
    }

    private void addEntryLabels() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel label1 = new JLabel("Ticker");
        JLabel label2 = new JLabel("Quantity");
        JLabel label3 = new JLabel("Avg. Cost");
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

    private void addStockEntry() {
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
    }

    private void deleteStockEntry() {
        java.awt.Component[] components = m_panel.getComponents();
        int length = components.length;
        if (length > 2) { // 0: buttons, 1: labels
            m_panel.remove(length - 1); // gap filler (Box$Filler)
            m_panel.remove(length - 2); // stock entry panel
        }
    }

    private void readEntries() {
        String validationResult = validateInput();
        if (!validationResult.isEmpty()) {
            JOptionPane.showMessageDialog(m_app.frame,
                    validationResult,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);

            return;
        }
        java.awt.Component[] components = m_panel.getComponents();
        if (components.length > 2) {
            for (int i = 2; i < components.length; i += 2) {
                saveEntries(components[i]);
            }
        }
    }

    @Override
    public String validateInput() {
        java.awt.Component[] components = m_panel.getComponents();
        if (components.length > 2) {
            for (int i = 2; i < components.length; i += 2) {
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
                    return "Stock quantity and average cost must be valid numbers";
                }

            }
        }
        return "";
    }

    private void saveEntries(java.awt.Component component) {
        JPanel parentPanel = (JPanel) component;
        JPanel panel = (JPanel) parentPanel.getComponent(0);

        JTextField tickerField = (JTextField) panel.getComponent(0);
        JTextField qtyField = (JTextField) panel.getComponent(2);
        JTextField costField = (JTextField) panel.getComponent(4);

        if (!tickerField.getText().isBlank()) {
            String ticker = tickerField.getText();
            Float quantity = Float.parseFloat(qtyField.getText());
            Float cost = Float.parseFloat(costField.getText());

            Map<String, Float> innerMap = new HashMap<>();
            innerMap.put("quantity", quantity);
            innerMap.put("avgCost", cost);
            m_stockEntries.put(ticker, innerMap);
            writeToJson();
        }
    }

    private void writeToJson() {
        Gson gson = new Gson();
        String json = gson.toJson(m_stockEntries);
        m_fileHandler.writeToFile(m_configs.STOCK_ENTRY_FILE_NAME, json);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add")) {
            this.addStockEntry();
            m_app.renderRegions();
        } else if (e.getActionCommand().equals("delete")) {
            deleteStockEntry();
            m_app.renderRegions();
        } else if (e.getActionCommand().equals("submit")) {
            readEntries();
        }
    }
}
