package com.zizou.csvparser.gui;

import com.zizou.csvparser.gui.panel.RootPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

/**
 * Created by zizou on 2017-09-04.
 */
@Component
public class MainFrame extends JFrame{
    private static final String VERSION = "v0.0.1";
    private static final Logger log = LoggerFactory.getLogger(MainFrame.class);

    @Autowired
    private RootPanel rootPanel;

    public void showWindow(){
        this.setDefaultCloseOperation(3);
        this.setTitle("Csv parser by zizou - " + VERSION);
        this.setSize(800, 700);
        this.initLayout();
        this.setVisible(true);
    }

    public void initLayout(){
        this.setLayout(new BorderLayout());
        this.add(this.rootPanel, "Center");
    }
}
