package org.example;

import org.example.GUIComponents.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GUIApp {
    public static final String REGIONAL_KEY = "Regional";
    public static final String TABBED_KEY = "Tabbed";
    public static final String ENTER_STOCK_TAB = "Enter Stock";
    public static final String ANALYSIS_TAB = "Analysis";
    public static final String INSTRUCTIONS_TAB = "Instructions";
    private final Set<String> m_regionRendered;
    public JFrame frame;
    public JTabbedPane mainTab = new JTabbedPane();
    public Map<String, Map<String, GUIComponentIF>> componentMapper = new HashMap<>();
    // custom GUI components
    GUIMenu menuComponent;
    private Container contentPane;

    public GUIApp() {
        Map<String, GUIComponentIF> regionalMap = new HashMap<>();
        regionalMap.put(BorderLayout.NORTH, new ImagePanel());
        componentMapper.put(REGIONAL_KEY, regionalMap);

        Map<String, GUIComponentIF> tabbedMap = new HashMap<>();
        tabbedMap.put(ENTER_STOCK_TAB, new StockEntryPanel(this));
        tabbedMap.put(ANALYSIS_TAB, new AnalysisDisplay());
        tabbedMap.put(INSTRUCTIONS_TAB, new StockEntryInstruction());
        componentMapper.put(TABBED_KEY, tabbedMap);

        m_regionRendered = new HashSet<>();
    }

    public void start() {
        frame = new JFrame("Portfolio Analyzer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = frame.getContentPane();
        mainTab = new JTabbedPane();

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

    private void renderTab() {
        String[] orderedTabs = {ENTER_STOCK_TAB, ANALYSIS_TAB, INSTRUCTIONS_TAB};
        for (String tabTitle : orderedTabs) {
            GUIComponentIF component = componentMapper.get(TABBED_KEY).get(tabTitle);
            if (component.enableVerticalScroll() || component.enableHorizontalScroll()) {
                mainTab.add(tabTitle, addScrollPane(component.getPanel()));
            } else {
                mainTab.addTab(tabTitle, component.getPanel());
            }
        }
        contentPane.add(mainTab);
    }

    public void renderRegions() {
        for (String region : componentMapper.get(REGIONAL_KEY).keySet()) {
            var component = componentMapper.get(REGIONAL_KEY).get(region);
            JPanel panel = component.getPanel();
            if (component.enableVerticalScroll() || component.enableHorizontalScroll()) {
                contentPane.add(addScrollPane(panel), region);
            } else {
                contentPane.add(panel, region);
            }
        }
    }

    public void refresh() {
        for (String region : componentMapper.keySet()) {
            for (String subRegion : componentMapper.get(region).keySet()) {
                var component = componentMapper.get(region).get(subRegion);
                component.updatePanel();
//                component.getPanel().revalidate();
//                component.getPanel().repaint();
            }
        }
        contentPane.revalidate();
        contentPane.repaint();
    }

    // -------------------------------------------------------------------//
    // -------------------- helper methods  ------------------------------//
    // -------------------------------------------------------------------//
    private JScrollPane addScrollPane(JPanel panel) {
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setAlignmentY(Component.TOP_ALIGNMENT);
        return scrollPane;
    }


}
