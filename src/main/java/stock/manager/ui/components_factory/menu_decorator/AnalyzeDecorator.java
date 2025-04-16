package stock.manager.ui.components_factory.menu_decorator;

import stock.manager.ui.app_builder.AppBuilderIF;
import stock.manager.ui.components_factory.AnalysisDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class AnalyzeDecorator extends AbstractMenuDecorator {

    public AnalyzeDecorator(AppBuilderIF appBuilder, AbstractMenuDecorator parent, String title) {
        super(appBuilder, parent, title);
    }

    protected void addMenuItems() {
        JMenuItem menuItem;
        menuItem = new JMenuItem("Sort by Return Rate");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, Event.CTRL_MASK));
        m_menu.add(menuItem);
        menuItem.addActionListener(new AnalyzeDecorator.SortByReturnRateListener());

        menuItem = new JMenuItem("Sort by Total Gain");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, Event.CTRL_MASK));
        m_menu.add(menuItem);
        menuItem.addActionListener(new AnalyzeDecorator.SortByTotalGainListener());

        menuItem = new JMenuItem("Sort by Total Cost");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK));
        m_menu.add(menuItem);
        menuItem.addActionListener(new AnalyzeDecorator.SortByTotalCostListener());

    }

    protected void setAccelerator() {
        m_menu.setMnemonic(KeyEvent.VK_A);
    }

    private class SortByReturnRateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AnalysisDisplay analysisDisplay = (AnalysisDisplay) m_appBuilder.getAnalysisPanel();
            analysisDisplay.sortByReturnRate();
        }
    }

    private class SortByTotalGainListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AnalysisDisplay analysisDisplay = (AnalysisDisplay) m_appBuilder.getAnalysisPanel();
            analysisDisplay.sortByTotalGain();
        }
    }

    private class SortByTotalCostListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AnalysisDisplay analysisDisplay = (AnalysisDisplay) m_appBuilder.getAnalysisPanel();
            analysisDisplay.sortByTotalCost();
        }
    }
}
