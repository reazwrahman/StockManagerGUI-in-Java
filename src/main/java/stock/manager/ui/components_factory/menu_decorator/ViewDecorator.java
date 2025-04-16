package stock.manager.ui.components_factory.menu_decorator;

import stock.manager.ui.app_builder.AppBuilderIF;
import stock.manager.ui.components_factory.GUIComponentIF;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Map;

public class ViewDecorator extends AbstractMenuDecorator {

    public ViewDecorator(AppBuilderIF appBuilder, AbstractMenuDecorator parent, String title) {
        super(appBuilder, parent, title);
    }

    @Override
    protected void setAccelerator() {
        m_menu.setMnemonic(KeyEvent.VK_V);
    }

    @Override
    protected void addMenuItems() {
        JMenuItem menuItem;
        menuItem = new JMenuItem("Reset");
        m_menu.add(menuItem);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, Event.CTRL_MASK));
        menuItem.addActionListener(new ViewDecorator.newListener());

        menuItem = new JMenuItem("Reload Data");
        m_menu.add(menuItem);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, Event.CTRL_MASK));
        menuItem.addActionListener(new ViewDecorator.reloadListener());


        menuItem = new JMenuItem("Exit");
        m_menu.add(menuItem);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK));
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public void reset() {
        Map<String, Map<String, GUIComponentIF>> componentMapper = m_appBuilder.getComponentMapper();
        for (String region : componentMapper.keySet()) {
            for (String subRegion : componentMapper.get(region).keySet()) {
                var component = componentMapper.get(region).get(subRegion);
                component.reset();
            }
        }
    }

    private class newListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            reset();
        }
    }

    private class reloadListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            m_appBuilder.reloadData();
        }
    }
}
