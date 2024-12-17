package org.example;

import org.example.GUIComponents.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GUIApp {

    public JFrame frame;
    // custom GUI components
    GUIMenu menuComponent;
    Map<String, GUIComponentIF> componentMapper = new HashMap<>();
    private Container contentPane;

    public GUIApp() {
        componentMapper.put(BorderLayout.NORTH, new ImagePanel());
        componentMapper.put(BorderLayout.CENTER, new StockEntryPanel(this));
        componentMapper.put(BorderLayout.SOUTH, new StockEntryInstruction());
    }

    public void start() {
        frame = new JFrame("Portfolio Analyzer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = frame.getContentPane();

        // set ui components
        renderMenu();
        renderRegions();

        frame.setSize(800, 800);
        frame.setVisible(true);
    }

    private void renderMenu() {
        menuComponent = new GUIMenu(frame, componentMapper);
        menuComponent.render();
    }

    public void renderRegions() {
        for (String region : componentMapper.keySet()) {
            var component = componentMapper.get(region);
            JPanel panel = component.getPanel();
            if (component.enableVerticalScroll()) {
                JScrollPane scrollPane = new JScrollPane(panel);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                contentPane.add(scrollPane, region);
            }
            else {
                contentPane.add(panel, region);
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
