package org.example.GUIComponents;

import javax.swing.*;

public interface GUIComponentIF {
    void render();

    JPanel getPanel();

    void reset();

    String validateInput();

    String getOrder();
}
