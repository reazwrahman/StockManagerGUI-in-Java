package stock.manager.ui.app_builder;

public class AppDirector {
    private final AppBuilderIF m_builder;

    public AppDirector(AppBuilderIF builder) {
        m_builder = builder;
    }

    public void constructUI() {
        m_builder.startFrame();
        m_builder.setupTabsAndPanels();
        m_builder.renderMenu();
        m_builder.renderTabbedPanes();
        m_builder.renderRegions();
        m_builder.startUI();
    }

}
