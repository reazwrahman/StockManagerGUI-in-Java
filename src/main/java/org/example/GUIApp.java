package org.example;

import org.example.GUIComponents.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GUIApp {

    public JFrame frame;
    // custom GUI components
    GUIMenu menuComponent;
    public Map<String, GUIComponentIF> componentMapper = new HashMap<>();
    private Container contentPane;
    private Set<String> m_regionRendered;

    public GUIApp() {
        componentMapper.put(BorderLayout.NORTH, new ImagePanel());
        componentMapper.put(BorderLayout.CENTER, new StockEntryPanel(this));
        componentMapper.put(BorderLayout.SOUTH, new StockEntryInstruction());
        componentMapper.put(BorderLayout.EAST, new AnalysisDisplay());

        m_regionRendered = new HashSet<>();
    }

    public void start() {
        frame = new JFrame("Portfolio Analyzer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = frame.getContentPane();

        // set ui components
        renderMenu();
        renderTab();
        renderRegions();

        frame.setSize(800, 800);
        frame.setVisible(true);
    }

    private void renderMenu() {
        menuComponent = new GUIMenu(frame, componentMapper);
        menuComponent.render();
    }

    private void renderTab(){
        JTabbedPane tabby = new JTabbedPane();

        JScrollPane scrollPane = new JScrollPane(componentMapper.get(BorderLayout.CENTER).getPanel());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setAlignmentY(Component.TOP_ALIGNMENT);
        tabby.addTab("Enter Stock", scrollPane);


        tabby.addTab("Analysis", componentMapper.get(BorderLayout.EAST).getPanel());
        tabby.addTab("Instructions", componentMapper.get(BorderLayout.SOUTH).getPanel());

        tabby.setMnemonicAt(0, KeyEvent.VK_E);
        contentPane.add(tabby);

        m_regionRendered.add(BorderLayout.CENTER);
        m_regionRendered.add(BorderLayout.SOUTH);
        m_regionRendered.add(BorderLayout.EAST);
    }

    public void renderRegions() {

        for (String region : componentMapper.keySet()) {
            if (!m_regionRendered.contains(region)) {
                var component = componentMapper.get(region);
                JPanel panel = component.getPanel();
                if (component.enableVerticalScroll()) {
                    JScrollPane scrollPane = new JScrollPane(panel);
                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                    scrollPane.setAlignmentY(Component.TOP_ALIGNMENT);
                    contentPane.add(scrollPane, region);
                } else {
                    contentPane.add(panel, region);
                }
            }

        }
    }

    public void refresh(){
        for (String region : componentMapper.keySet()) {
            var component = componentMapper.get(region);
            component.getPanel().revalidate();
            component.getPanel().repaint();
        }
        contentPane.revalidate();
        contentPane.repaint();
    }

}
