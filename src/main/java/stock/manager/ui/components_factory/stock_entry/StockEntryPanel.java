package stock.manager.ui.components_factory.stock_entry;

import com.google.gson.Gson;
import stock.manager.ui.Configs;
import stock.manager.ui.app_builder.AppBuilderIF;
import stock.manager.ui.components_factory.AbstractGUIComponent;
import stock.manager.ui.components_factory.GUIComponentIF;
import stock.manager.ui.stock_manager.StockSorter;
import stock.manager.ui.utility.FileHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StockEntryPanel extends AbstractGUIComponent
        implements GUIComponentIF, ActionListener {

    private static final int COLUMNS = 10;
    private static final int WIDTH = 70;
    private static final int HEIGHT = 26;
    private static final int STOCK_PANEL_STARTS_AT = 2;
    private static final int DELTA_BETWEEN_STOCK_PANEL = 2;
    private final AppBuilderIF m_app;
    private final FileHandler m_fileHandler;
    private final StockSorter m_stockSorter;
    private Integer stockEntryCounter = 0;
    private Map<String, Map<String, String>> m_stockMap;
    private JPanel m_buttonsPanel;
    private JButton m_addButton;
    private JButton m_deleteButton;
    private JButton m_submitButton;

    public StockEntryPanel(AppBuilderIF app) {
        m_app = app;
        m_stockMap = new HashMap<>();
        m_fileHandler = new FileHandler();

        m_stockMap = m_fileHandler.readStockEntries();
        m_stockSorter = new StockSorter();
    }

    @Override
    public String getDescription() {
        String description = "StockEntryPanel: This is where stock entries added, removed or updated";
        System.out.println(description);
        return description;
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
        m_submitButton.setMnemonic(KeyEvent.VK_S);
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

        JLabel label0 = new JLabel("No.");
        JLabel label1 = new JLabel("Ticker");
        JLabel label2 = new JLabel("Quantity");
        JLabel label3 = new JLabel("Total Cost");
        Dimension textFieldSize = new Dimension(100, 25);
        label0.setPreferredSize(textFieldSize);
        label1.setPreferredSize(textFieldSize);
        label2.setPreferredSize(textFieldSize);
        label3.setPreferredSize(textFieldSize);

        panel.add(label0);
        panel.add(Box.createHorizontalStrut(20)); // Gap between fields
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

    public void fillStockEntry() {
        for (String ticker : m_stockMap.keySet()) {
            PanelRow row = addStockEntry();
            row.ticker.setText(ticker);
            row.ticker.setEditable(true);
            row.qty.setText(m_stockMap.get(ticker).get("quantity"));
            row.cost.setText(m_stockMap.get(ticker).get("totalCost"));
        }
    }

    public PanelRow addStockEntry() {
        stockEntryCounter++;
        JPanel stockPanel = new JPanel();
        stockPanel.setLayout(new BoxLayout(stockPanel, BoxLayout.X_AXIS));
        stockPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        stockPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        PanelRow row = new PanelRow(stockEntryCounter.toString());

        // Add text fields to panel with gaps
        stockPanel.add(row.counter);
        stockPanel.add(Box.createHorizontalStrut(10)); // Gap between fields
        stockPanel.add(row.ticker);
        stockPanel.add(Box.createHorizontalStrut(10)); // Gap between fields
        stockPanel.add(row.qty);
        stockPanel.add(Box.createHorizontalStrut(10));
        stockPanel.add(row.cost);
        stockPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        // Wrap the panel in a top-aligned container
        JPanel topAlignedPanel = new JPanel();
        topAlignedPanel.setLayout(new BorderLayout());
        topAlignedPanel.add(stockPanel, BorderLayout.NORTH);

        m_panel.add(topAlignedPanel);
        m_panel.add(Box.createVerticalStrut(5));

        return row;
    }

    public boolean deleteStockEntry() {
        java.awt.Component[] components = m_panel.getComponents();
        int length = components.length;
        if (length > STOCK_PANEL_STARTS_AT) { // 0: buttons, 1: labels
            m_panel.remove(length - 1); // gap filler (Box$Filler)
            m_panel.remove(length - 2); // stock entry panel
            stockEntryCounter--;
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

    public void readEntries() {
        String validationResult = validateInput();
        if (!validationResult.isEmpty()) {
            JOptionPane.showMessageDialog(m_app.getFrame(),
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
        Set<String> stockSeen = new HashSet<>();

        if (components.length > STOCK_PANEL_STARTS_AT) {
            for (int i = STOCK_PANEL_STARTS_AT; i < components.length; i += DELTA_BETWEEN_STOCK_PANEL) {
                JPanel parentPanel = (JPanel) components[i];
                JPanel panel = (JPanel) parentPanel.getComponent(0);
                RowValues rowValues = new RowValues(panel);

                if (rowValues.ticker.isBlank() || rowValues.qty.isBlank() || rowValues.cost.isBlank()) {
                    return "Stock entries can not be empty";
                }

                String ticker = rowValues.ticker.strip().toUpperCase();

                if (stockSeen.contains(ticker)) {
                    return "Duplicate Stock not allowed: " + ticker;
                }
                stockSeen.add(ticker);

                try {
                    Float quantity = Float.parseFloat(rowValues.qty);
                    Float cost = Float.parseFloat(rowValues.cost);
                } catch (Exception e) {
                    return "Stock quantity and total cost must be valid numbers";
                }

            }
        }
        if (stockSeen.isEmpty()) {
            return "Nothing to save, add a stock by clicking the Add button";
        }
        return "";
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
    public void updatePanel() {
        m_panel.revalidate();
        m_panel.repaint();
    }

    private void saveEntries() {
        java.awt.Component[] components = m_panel.getComponents();
        m_stockMap = new HashMap<>();

        if (components.length > STOCK_PANEL_STARTS_AT) {
            for (int i = STOCK_PANEL_STARTS_AT; i < components.length; i += DELTA_BETWEEN_STOCK_PANEL) {

                JPanel parentPanel = (JPanel) components[i];
                JPanel panel = (JPanel) parentPanel.getComponent(0);
                RowValues rowValues = new RowValues(panel);
                insertToMap(rowValues.ticker.toUpperCase(), rowValues.qty, rowValues.cost);
            }
            writeToJson();
            reset();
            fillStockEntry();
            JOptionPane.showMessageDialog(m_app.getFrame(),
                    "Data Submitted",
                    "Submitted",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void insertToMap(String ticker, String quantity, String cost) {
        Map<String, String> innerMap = new HashMap<>();
        if (Float.parseFloat(quantity) == 0 && Float.parseFloat(cost) == 0) { // remove this stock if applicable, or don't enter at all
            if (m_stockMap.get(ticker) != null) {
                m_stockMap.remove(ticker);
            }
            return;
        }
        innerMap.put("quantity", quantity);
        innerMap.put("totalCost", cost);
        m_stockMap.put(ticker, innerMap);
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
            m_app.refresh();
        }
    }
}
