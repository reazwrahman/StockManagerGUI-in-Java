package org.example.GUIComponents;

import org.example.Uitility.FileHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Map;

public class GUIMenu extends AbstractGUIComponent implements GUIComponentIF {
    // menu related
    JMenuBar menuBar;
    JMenu orderMenu;
    JMenu helpMenu;
    JMenuItem menuItem;
    Map<String, GUIComponentIF> componentMapper;
    private final JFrame frame;
    private final FileHandler fileHandler;

    public GUIMenu(JFrame guiFrame, Map<String, GUIComponentIF> componentsMapped) {
        frame = guiFrame;
        componentMapper = componentsMapped;
        fileHandler = new FileHandler();
    }

    @Override
    public JPanel getPanel() {
        return null; // placeholder implementation, menu doesn't need panel
    }

    @Override
    public void reset() {
        for (String region : componentMapper.keySet()) {
            GUIComponentIF component = componentMapper.get(region);
            component.reset();
        }
    }

    @Override
    public String validateInput() {
        for (String region : componentMapper.keySet()) {
            GUIComponentIF component = componentMapper.get(region);
            String errorMessage = component.validateInput();

            if (!errorMessage.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        errorMessage,
                        "Order Error",
                        JOptionPane.ERROR_MESSAGE);
                return errorMessage;
            }
        }
        return "";
    }

    @Override
    public String getOrder() {
        String order = "Pizza Order\n" +
                "===========\n";

        order += componentMapper.get(BorderLayout.WEST).getOrder(); // crust
        order += componentMapper.get(BorderLayout.CENTER).getOrder(); // toppings
        order += componentMapper.get(BorderLayout.EAST).getOrder(); // sides
        order += componentMapper.get(BorderLayout.SOUTH).getOrder(); // delivery address

        order += "\n***END OF ORDER ***\n";
        return order;
    }

    @Override
    public void render() {
        menuBar = new JMenuBar(); // first thing we need to add: the bar to hold "menu" objects
        frame.setJMenuBar(menuBar); // add the bar to the frame

        orderMenu = new JMenu("Order"); // this is the first "menu" type object
        orderMenu.setMnemonic(KeyEvent.VK_O);

        helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);

        menuBar.add(orderMenu); // add the menu object to the bar
        menuBar.add(helpMenu);
        addMenuItems();
    }

    private void addMenuItems() {
        menuItem = new JMenuItem("New Order");
        orderMenu.add(menuItem);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
        menuItem.addActionListener(new newListener());

        menuItem = new JMenuItem("Save Order");
        orderMenu.add(menuItem);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
        menuItem.addActionListener(new saveListener());


        menuItem = new JMenuItem("Exit");
        orderMenu.add(menuItem);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK));
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // ----- help menu ------ //
        menuItem = new JMenuItem("About");
        helpMenu.add(menuItem);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK));
        menuItem.addActionListener(new aboutListener());
    }

    private class aboutListener implements ActionListener {
        String message = "Pizza Ordering System GUI - Written in Java.\n" +
                "Easily customize and order your favorite pizzas.\n" +
                "Author: Reaz W. Rahman \n" +
                "All Rights Reserved © 2024.";

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(frame,
                    message,
                    "About",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private class newListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            reset();
            newMethod("Order has been reset!");
        }

        private void newMethod(String message) {
            JOptionPane.showMessageDialog(frame,
                    message,
                    "",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private class saveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String errorMessage = validateInput();
            if (errorMessage.isEmpty()) { // valid inputs
                String order = getOrder();
                JOptionPane.showMessageDialog(frame,
                        order,
                        "Order Saved",
                        JOptionPane.INFORMATION_MESSAGE);

                fileHandler.writeToFile("PizzaOrder.txt", order);
            }
        }
    }
}