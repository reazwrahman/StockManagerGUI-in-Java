package stock.manager.ui.components_factory;

import stock.manager.ui.app_builder.AppBuilderIF;

import java.util.HashMap;
import java.util.Map;

public class SimpleFactory {
    private static SimpleFactory factoryInstance;
    final Map<ComponentEnums, GUIComponentIF> m_componentMapper;

    public SimpleFactory(AppBuilderIF app) {
        m_componentMapper = new HashMap<>();
        m_componentMapper.put(ComponentEnums.ANALYSIS_DISPLAY, new AnalysisDisplay());
        m_componentMapper.put(ComponentEnums.IMAGE_PANEL, new ImagePanel());
        m_componentMapper.put(ComponentEnums.INSTRUCTION_PANEL, new StockEntryInstruction());
        m_componentMapper.put(ComponentEnums.STOCK_ENTRY_PANEL, new StockEntryPanel(app));

        m_componentMapper.put(ComponentEnums.MAIN_MENU, new GUIMenu(app));
    }

    // Singleton Pattern: only one factory instance is needed
    public static synchronized SimpleFactory getFactoryInstance(AppBuilderIF app) {
        if (factoryInstance == null) {
            factoryInstance = new SimpleFactory(app);
        }
        return factoryInstance;
    }

    public GUIComponentIF getComponent(ComponentEnums type) {
        return m_componentMapper.get(type);
    }
}
