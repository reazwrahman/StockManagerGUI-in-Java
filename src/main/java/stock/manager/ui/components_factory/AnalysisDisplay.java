package stock.manager.ui.components_factory;

import stock.manager.ui.stock_manager.StockSorter;

import javax.swing.*;
import java.awt.*;

public class AnalysisDisplay extends AbstractGUIComponent implements GUIComponentIF {

    JTextArea m_textArea;
    StockSorter m_stockSorter;

    public AnalysisDisplay() {
        m_stockSorter = new StockSorter();
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

    public void updatePanel() {
        m_stockSorter.updateData();
        m_textArea.setText(m_stockSorter.toString());
        JScrollPane scrollPane = new JScrollPane(m_textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setAlignmentY(Component.TOP_ALIGNMENT);

        m_panel.removeAll(); // Remove any existing components
        m_panel.add(scrollPane, BorderLayout.CENTER);
        m_panel.revalidate(); // Ensures the layout is recalculated
        m_panel.repaint();
    }

}
