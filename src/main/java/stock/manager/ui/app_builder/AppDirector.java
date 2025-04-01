package stock.manager.ui.app_builder;

public class AppDirector {
    private final AppBuilderIF m_builder;

    public AppDirector(AppBuilderIF builder) {
        m_builder = builder;
    }

    public void constructUI() {
        m_builder.startFrame();
        m_builder.initialize();
        m_builder.startUI();
    }

}
