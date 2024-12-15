package org.example.GUIComponents;

import org.example.Uitility.FileHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Map;

public class GUIMenu extends AbstractGUIComponent implements GUIComponentIF {
    private final JFrame frame;
    private final FileHandler fileHandler;
    // menu related
    JMenuBar menuBar;
    JMenu viewMenu;
    JMenu helpMenu;
    JMenuItem menuItem;
    Map<String, GUIComponentIF> componentMapper;

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
    public void render() {
        menuBar = new JMenuBar(); // first thing we need to add: the bar to hold "menu" objects
        frame.setJMenuBar(menuBar); // add the bar to the frame

        viewMenu = new JMenu("View"); // this is the first "menu" type object
        viewMenu.setMnemonic(KeyEvent.VK_V);

        helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);

        menuBar.add(viewMenu); // add the menu object to the bar
        menuBar.add(helpMenu);
        addMenuItems();
    }

    private void addMenuItems() {
        menuItem = new JMenuItem("Reset");
        viewMenu.add(menuItem);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, Event.CTRL_MASK));
        menuItem.addActionListener(new newListener());

//        menuItem = new JMenuItem("Save Order");
//        viewMenu.add(menuItem);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
//        menuItem.addActionListener(new saveListener());


        menuItem = new JMenuItem("Exit");
        viewMenu.add(menuItem);
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
        String message = "Stock Portfolio Management System\n" +
                "Easily manage and analyze your stock performance\n" +
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
        }
    }

    private class saveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String errorMessage = validateInput();
            if (errorMessage.isEmpty()) { // valid inputs
                String order = "";
                JOptionPane.showMessageDialog(frame,
                        order,
                        "Order Saved",
                        JOptionPane.INFORMATION_MESSAGE);

                fileHandler.writeToFile("PizzaOrder.txt", order);
            }
        }
    }
}