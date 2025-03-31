package stock.manager.ui.components_factory;

import java.util.HashMap;
import java.util.Map;

public class SimpleFactory {
    private static SimpleFactory factoryInstance;
    private Map<ComponentEnums, GUIComponentIF> m_componentMapper;

    public SimpleFactory(){
        m_componentMapper = new HashMap<>();
        m_componentMapper.put(ComponentEnums.ANALYSIS_DISPLAY, new AnalysisDisplay());
        m_componentMapper.put(ComponentEnums.IMAGE_PANEL, new ImagePanel());
        m_componentMapper.put(ComponentEnums.INSTRUCTION_PANEL, new StockEntryInstruction());
    }

    // Singleton Pattern: only one factory instance is needed
    public static synchronized SimpleFactory getFactoryInstance() {
        if (factoryInstance == null) {
            factoryInstance = new SimpleFactory();
        }
        return factoryInstance;
    }

    public GUIComponentIF getComponent(ComponentEnums type){
        return m_componentMapper.get(type);
    }
}
