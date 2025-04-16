package stock.manager.ui.components_factory.menu_decorator;

import stock.manager.ui.app_builder.AppBuilderIF;

import javax.swing.*;

public abstract class AbstractMenu {
    protected JMenuBar m_menuBar;
    protected JMenu m_menu;
    protected AppBuilderIF m_appBuilder;

//    public AbstractMenu(AppBuilderIF builder, AbstractMenu parent){
//        m_appBuilder = builder;
//        setMenuBar(parent.m_menuBar);
//    }

    protected void setMenuBar(AbstractMenu parent){
        if (parent == null) {
            m_menuBar = new JMenuBar();
            m_menuBar.add(m_menu);
        } else {
            parent.m_menuBar.add(m_menu);
            m_menuBar = parent.m_menuBar;
        }
    }

    protected abstract void setAccelerator();
    protected abstract void addMenuItems();

}
