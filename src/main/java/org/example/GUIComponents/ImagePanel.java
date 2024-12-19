package org.example.GUIComponents;

import org.example.Configs;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends AbstractGUIComponent implements GUIComponentIF {

    private static final String IMAGE_REF = Configs.COVER_PHOTO;

    @Override
    public void render() {
        m_panel = new JPanel();
        ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource(IMAGE_REF));
        JLabel imgLabel = new JLabel(imageIcon, JLabel.CENTER);
        m_panel.setPreferredSize(new Dimension(400, 250));
        m_panel.add(imgLabel);
    }
}
