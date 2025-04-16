package stock.manager.ui.components_factory.menu_decorator;

import stock.manager.ui.app_builder.AppBuilderIF;
import stock.manager.ui.components_factory.AnalysisDisplay;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MenuForAnalyze extends AbstractMenu{

    public MenuForAnalyze(AppBuilderIF appBuilder, AbstractMenu parent){
        m_menu = new JMenu("Analyze");
        addMenuItems();
        setMenuBar(parent);
    }

    protected void addMenuItems(){
        JMenuItem menuItem;
        menuItem = new JMenuItem("Sort by Return Rate");
        m_menu.add(menuItem);
        menuItem.addActionListener(new MenuForAnalyze.SortByReturnRateListener());

        menuItem = new JMenuItem("Sort by Total Gain");
        m_menu.add(menuItem);
        menuItem.addActionListener(new MenuForAnalyze.SortByTotalGainListener());

        menuItem = new JMenuItem("Sort by Total Cost");
        m_menu.add(menuItem);
        menuItem.addActionListener(new MenuForAnalyze.SortByTotalCostListener());

    }

    protected void setAccelerator(){
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
