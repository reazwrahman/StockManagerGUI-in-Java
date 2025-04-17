package stock.manager.ui.app_builder;

import stock.manager.ui.components_factory.ComponentEnums;
import stock.manager.ui.components_factory.GUIComponentIF;
import stock.manager.ui.components_factory.SimpleFactory;
import stock.manager.ui.components_factory.stock_entry.StockEntryPanel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StockAppBuilder implements AppBuilderIF {

    public static final String REGIONAL_KEY = "Regional";
    public static final String TABBED_KEY = "Tabbed";
    public static final String ENTER_STOCK_TAB = "Enter Stock";
    public static final String ANALYSIS_TAB = "Analysis";
    public static final String INSTRUCTIONS_TAB = "Instructions";
    // maps all components on the GUI to the right region and tab
    public Map<String, Map<String, GUIComponentIF>> m_componentMapper = new HashMap<>();
    public JFrame m_frame;
    public JTabbedPane m_mainTab;
    SimpleFactory m_componentFactory; // to get GUI component from factory
    // custom GUI components
    GUIComponentIF m_menuComponent;
    private Set<String> m_regionRendered;
    private Container m_contentPane;

    @Override
    public void setupTabsAndPanels() {
        m_componentFactory = SimpleFactory.getFactoryInstance(this);

        Map<String, GUIComponentIF> regionalMap = new HashMap<>();
        regionalMap.put(BorderLayout.NORTH, m_componentFactory.getComponent(ComponentEnums.IMAGE_PANEL));
        m_componentMapper.put(REGIONAL_KEY, regionalMap);

        Map<String, GUIComponentIF> tabbedMap = new HashMap<>();
        tabbedMap.put(ENTER_STOCK_TAB, m_componentFactory.getComponent(ComponentEnums.STOCK_ENTRY_PANEL));
        tabbedMap.put(ANALYSIS_TAB, m_componentFactory.getComponent(ComponentEnums.ANALYSIS_DISPLAY));
        tabbedMap.put(INSTRUCTIONS_TAB, m_componentFactory.getComponent(ComponentEnums.INSTRUCTION_PANEL));
        m_componentMapper.put(TABBED_KEY, tabbedMap);

        m_regionRendered = new HashSet<>();
        m_mainTab = new JTabbedPane();
    }

    @Override
    public void startFrame() {
        m_frame = new JFrame("Portfolio Analyzer");
        m_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m_contentPane = m_frame.getContentPane();
        m_mainTab = new JTabbedPane();
    }

    @Override
    public void renderMenu() {
        m_menuComponent = m_componentFactory.getComponent(ComponentEnums.MAIN_MENU);
        m_menuComponent.render();
    }

    @Override
    public void renderTabbedPanes() {
        String[] orderedTabs = {INSTRUCTIONS_TAB, ENTER_STOCK_TAB, ANALYSIS_TAB};
        for (String tabTitle : orderedTabs) {
            GUIComponentIF component = m_componentMapper.get(TABBED_KEY).get(tabTitle);
            if (component.enableVerticalScroll() || component.enableHorizontalScroll()) {
                m_mainTab.add(tabTitle, addScrollPane(component.getPanel()));
            } else {
                m_mainTab.addTab(tabTitle, component.getPanel());
            }
        }
        m_contentPane.add(m_mainTab);
    }

    @Override
    public void renderRegions() {
        for (String region : m_componentMapper.get(REGIONAL_KEY).keySet()) {
            var component = m_componentMapper.get(REGIONAL_KEY).get(region);
            JPanel panel = component.getPanel();
            if (component.enableVerticalScroll() || component.enableHorizontalScroll()) {
                m_contentPane.add(addScrollPane(panel), region);
            } else {
                m_contentPane.add(panel, region);
            }
        }
    }

    @Override
    public void refresh() {
        for (String region : m_componentMapper.keySet()) {
            for (String subRegion : m_componentMapper.get(region).keySet()) {
                var component = m_componentMapper.get(region).get(subRegion);
                component.updatePanel();
            }
        }
        m_contentPane.revalidate();
        m_contentPane.repaint();
    }

    @Override
    public void startUI() {
        m_frame.setSize(900, 700);
        m_frame.setVisible(true);
    }

    @Override
    public JFrame getFrame() {
        return m_frame;
    }

    @Override
    public Map<String, Map<String, GUIComponentIF>> getComponentMapper() {
        return m_componentMapper;
    }

    @Override
    public void reloadData() {
        StockEntryPanel stockPanel = (StockEntryPanel) m_componentMapper.get(StockAppBuilder.TABBED_KEY).get(StockAppBuilder.ENTER_STOCK_TAB);
        stockPanel.reset();
        stockPanel.fillStockEntry();
    }

    @Override
    public GUIComponentIF getPanel(String panelIdentifier) {
        return m_componentMapper.get(TABBED_KEY).get(panelIdentifier);
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
