/*
 * Copyright (C) 2016 Lynn
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.lynn9388.rmichatroom.client.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame implements ActionListener {
    private JTextField userName;

    public void createAndShow() {
        setTitle("Login");
        setResizable(false);
        setBounds(400, 200, 276, 190);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Create panel object
        JPanel loginPanel = new JPanel();
        loginPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(loginPanel);
        loginPanel.setLayout(null);

        // Username label
        JLabel labelName = new JLabel("Username:");
        labelName.setBounds(20, 45, 80, 20);
        loginPanel.add(labelName);

        // Text field for username
        userName = new JTextField(16);
        userName.setBounds(100, 45, 140, 24);
        loginPanel.add(userName);

        // Login button
        JButton buttonLogin = new JButton("Login");
        buttonLogin.setBounds(170, 85, 70, 30);
        buttonLogin.addActionListener(this);
        loginPanel.add(buttonLogin);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = userName.getText();

        System.out.println(username);
        dispose(); // Close login window

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            MainGUI frame = new MainGUI();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);


        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }

}
