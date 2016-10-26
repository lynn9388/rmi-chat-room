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

import com.lynn9388.rmichatroom.client.Client;
import com.lynn9388.rmichatroom.client.rmi.ClientImpl;
import com.lynn9388.rmichatroom.rmi.Message;
import com.lynn9388.rmichatroom.rmi.Server;
import com.lynn9388.rmichatroom.rmi.User;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LoginGui extends JFrame implements java.awt.event.ActionListener {
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
    public void actionPerformed(java.awt.event.ActionEvent e) {
        String username = userName.getText();

        if (username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username can't be empty.");
        } else {
            try {
                ClientImpl client = new ClientImpl();
                Server server = client.getServer();

                String ip = Client.getConnectedIp(Server.IP, Server.PORT);
                int port = com.lynn9388.rmichatroom.rmi.Client.PORT;
                if (server != null && ip != null) {
                    Registry registry;
                    try {
                        registry = LocateRegistry.createRegistry(port);
                    } catch (RemoteException e1) {
                        registry = LocateRegistry.getRegistry(port);
                        e1.printStackTrace();
                    }
                    registry.rebind(username, client);

                    dispose(); // Close login window

                    server.register(new User(username, ip, port, username));
                    MainGui mainGui = new MainGui(server, username);
                    client.setMainGui(mainGui);
                    mainGui.createAndShow();

                    mainGui.updateUsers(server.getRegisteredUsers(), server.getOnlineUsernames());

                    List<Message> messages = server.getMissedMessages(username);
                    server.removeMissedMessages(username);
                    if (messages != null) {
                        for (Message message : messages) {
                            mainGui.appendMessage(message);
                        }
                    }

                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            try {
                                server.sendHeartbeat(username);
                            } catch (RemoteException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }, 0, Server.HEARTBEAT_RATE);
                } else {
                    JOptionPane.showMessageDialog(this, "Can't connect to server.");
                }
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        }
    }
}
