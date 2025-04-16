package stock.manager.ui.components_factory.menu_decorator;

import stock.manager.ui.app_builder.AppBuilderIF;
import stock.manager.ui.components_factory.GUIMenuOld;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MenuForHelp extends AbstractMenu{

    public MenuForHelp(AppBuilderIF appBuilder, AbstractMenu parent){
        m_menu = new JMenu("Help");
        addMenuItems();
        setMenuBar(parent);
    }

    @Override
    protected void setAccelerator() {
        m_menu.setMnemonic(KeyEvent.VK_H);
    }

    @Override
    protected void addMenuItems() {
        JMenuItem menuItem;
        menuItem = new JMenuItem("About");
        m_menu.add(menuItem);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK));
        menuItem.addActionListener(new MenuForHelp.aboutListener());
    }

    private class aboutListener implements ActionListener {
        String message = "Stock Portfolio Management System\n" +
                "Easily manage and analyze your stock performance\n" +
                "Author: Reaz W. Rahman \n" +
                "All Rights Reserved © 2025.";

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(m_appBuilder.getFrame(),
                    message,
                    "About",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
