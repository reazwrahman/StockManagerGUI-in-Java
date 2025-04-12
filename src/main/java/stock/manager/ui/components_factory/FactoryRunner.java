package stock.manager.ui.components_factory;

import stock.manager.ui.app_builder.StockAppBuilder;

public class FactoryRunner {
    public static void main(String[] args) {
        System.out.println("------- TESTING FACTORY RUNNER ---------");
        SimpleFactory factory = SimpleFactory.getFactoryInstance(new StockAppBuilder());
        for (ComponentEnums key : factory.m_componentMapper.keySet()) {
            factory.getComponent(key).getDescription();
            System.out.println();
        }
    }
}
