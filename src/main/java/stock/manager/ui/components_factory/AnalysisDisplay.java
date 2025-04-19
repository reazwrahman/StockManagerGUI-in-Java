package stock.manager.ui.components_factory;

import stock.manager.ui.stock_manager.StockSorter;
import stock.manager.ui.stock_manager.backend.DisplayHelper;

import javax.swing.*;
import java.awt.*;

public class AnalysisDisplay extends AbstractGUIComponent implements GUIComponentIF {

    private final String API_CALL_MESSAGE = "Requesting Data from Backend API ...";
    JTextArea m_textArea;
    StockSorter m_stockSorter;
    DisplayHelper displayHelper;

    public AnalysisDisplay() {
        m_stockSorter = new StockSorter();
        displayHelper = new DisplayHelper();
    }

    @Override
    public String getDescription() {
        String description = "AnalysisDisplay: Responsible for displaying stock data post analysis";
        System.out.println(description);
        return description;
    }

    @Override
    public void render() {
        m_panel = new JPanel();
        m_panel.setLayout(new BorderLayout());
        m_textArea = new JTextArea(10, 30);
        m_textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        m_stockSorter.updateData();
        m_textArea.setText(m_stockSorter.toString());
        m_textArea.setLineWrap(true);
        m_textArea.setWrapStyleWord(true);
        m_textArea.setEditable(false);

        m_panel.removeAll(); // Remove any existing components
        m_panel.add(m_textArea, BorderLayout.CENTER);
        m_panel.revalidate(); // Ensures the layout is recalculated
        m_panel.repaint();
    }

    @Override
    public Boolean enableVerticalScroll() {
        return true;
    }

    @Override
    public Boolean enableHorizontalScroll() {
        return true;
    }

    public void updatePanel(String displayableText) {
        m_textArea.setText(displayableText);
        JScrollPane scrollPane = new JScrollPane(m_textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setAlignmentY(Component.TOP_ALIGNMENT);

        m_panel.removeAll(); // Remove any existing components
        m_panel.add(scrollPane, BorderLayout.CENTER);
        m_panel.revalidate(); // Ensures the layout is recalculated
        m_panel.repaint();
    }

    public void sortByTotalCost() {
        m_stockSorter.updateData();
        updatePanel(m_stockSorter.toString());
    }

    public void sortByReturnRate() {
        updatePanel(API_CALL_MESSAGE);
        updatePanel(displayHelper.getReturnRateString());
    }

    public void sortByTotalGain() {
        updatePanel(API_CALL_MESSAGE);
        updatePanel(displayHelper.getTotalGainString());
    }

}
