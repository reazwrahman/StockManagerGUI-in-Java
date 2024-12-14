package org.example;

import org.example.GUIComponents.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GUIApp {

    // custom GUI components
    GUIMenu menuComponent;
    Map<String, GUIComponentIF> componentMapper = new HashMap<>();
    private JFrame frame;
    private Container contentPane;

    public GUIApp() {
        componentMapper.put(BorderLayout.CENTER, new StockEntryPanel(this));
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
            contentPane.add(panel, region);
            contentPane.add(panel, BorderLayout.PAGE_START);
        }
        contentPane.revalidate();
        contentPane.repaint();
    }
}
