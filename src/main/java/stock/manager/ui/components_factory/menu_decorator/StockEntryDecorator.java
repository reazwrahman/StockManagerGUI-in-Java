package stock.manager.ui.components_factory.menu_decorator;

import stock.manager.ui.app_builder.AppBuilderIF;
import stock.manager.ui.app_builder.StockAppBuilder;
import stock.manager.ui.components_factory.StockEntryPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class StockEntryDecorator extends AbstractMenuDecorator {

    public StockEntryDecorator(AppBuilderIF appBuilder, AbstractMenuDecorator parent, String title) {
        super(appBuilder, parent, title);
    }

    @Override
    protected void setAccelerator() {
        m_menu.setMnemonic(KeyEvent.VK_S);
    }

    @Override
    protected void addMenuItems() {
        JMenuItem menuItem;
        menuItem = new JMenuItem("Add Stock");
        m_menu.add(menuItem);
        menuItem.addActionListener(new StockEntryDecorator.AddStockListener());

        menuItem = new JMenuItem("Delete Stock");
        m_menu.add(menuItem);
        menuItem.addActionListener(new StockEntryDecorator.DeleteStockListener());

        menuItem = new JMenuItem("Save");
        m_menu.add(menuItem);
        menuItem.addActionListener(new StockEntryDecorator.SaveListener());

    }

    private class AddStockListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StockEntryPanel stockEntryPanel = (StockEntryPanel) m_appBuilder.getPanel(StockAppBuilder.ENTER_STOCK_TAB);
            stockEntryPanel.addStockEntry();
            m_appBuilder.refresh();
        }
    }

    private class DeleteStockListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StockEntryPanel stockEntryPanel = (StockEntryPanel) m_appBuilder.getPanel(StockAppBuilder.ENTER_STOCK_TAB);
            stockEntryPanel.deleteStockEntry();
            m_appBuilder.refresh();
        }
    }

    private class SaveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StockEntryPanel stockEntryPanel = (StockEntryPanel) m_appBuilder.getPanel(StockAppBuilder.ENTER_STOCK_TAB);
            stockEntryPanel.readEntries();
            m_appBuilder.refresh();
        }
    }
}
