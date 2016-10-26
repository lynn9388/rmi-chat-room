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

import com.lynn9388.rmichatroom.rmi.Client;
import com.lynn9388.rmichatroom.rmi.Message;
import com.lynn9388.rmichatroom.rmi.Server;
import com.lynn9388.rmichatroom.rmi.User;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

public class MainGui extends JFrame implements ActionListener {
    private JTextArea historyArea;
    private ButtonGroup userButtonGroup;
    private JPanel chooseUserPanel;
    private JScrollPane chooseUserScrollPane;
    private JTextArea messageEditArea;
    private Button sendButton;

    private Server server;
    private String username;
    private String sendToUsername;
    private List<User> registeredUsers;
    private List<String> onlineUsernames;

    public MainGui(Server server, String username) {
        this.server = server;
        this.username = username;
    }

    public void createAndShow() {
        setTitle("Chat Room");
        setResizable(false);
        setBounds(100, 100, 619, 650);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPanel);

        // Label for history area
        Label historyLabel = new Label("History:");
        historyLabel.setBounds(10, 5, 100, 17);
        contentPanel.add(historyLabel);

        // Message display area, add scroll bar
        historyArea = new JTextArea();
        historyArea.setEditable(false);
        JScrollPane historyScrollPane = new JScrollPane(historyArea);
        historyScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        historyScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        historyScrollPane.setBounds(10, 25, 595, 390);
        contentPanel.add(historyScrollPane);

        // Label for user select area
        Label userSelectLabel = new Label("Send to:");
        userSelectLabel.setBounds(10, 425, 100, 20);
        contentPanel.add(userSelectLabel);

        // Area to choose user
        chooseUserPanel = new JPanel();
        chooseUserScrollPane = new JScrollPane(chooseUserPanel);
        chooseUserScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        userButtonGroup = new ButtonGroup();
        chooseUserScrollPane.setBounds(10, 450, 100, 150);
        contentPanel.add(chooseUserScrollPane);

        // Label for message edit area
        Label messageLabel = new Label("Message:");
        messageLabel.setBounds(130, 425, 100, 20);
        contentPanel.add(messageLabel);

        // Message edit area
        messageEditArea = new JTextArea();
        messageEditArea.setLineWrap(true);
        JScrollPane scrollPane1 = new JScrollPane(messageEditArea);
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane1.setBounds(130, 445, 370, 170);
        contentPanel.add(scrollPane1);

        // Button to send message,
        sendButton = new Button("Send");
        sendButton.setBounds(520, 510, 75, 35);
        sendButton.addActionListener(new sendActionListener());
        contentPanel.add(sendButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        sendToUsername = e.getActionCommand();
    }

    /**
     * Add a new online user
     *
     * @param onlineUser the user just online, and the user may have registered before
     */
    public void addOnlineUser(User onlineUser) {
        boolean isRegistered = false;
        AbstractButton abstractButton = null;
        for (Enumeration<AbstractButton> e = userButtonGroup.getElements(); e.hasMoreElements(); ) {
            abstractButton = e.nextElement();
            if (abstractButton.getActionCommand().equals(onlineUser.getUsername())) {
                isRegistered = true;
                break;
            }
        }

        if (isRegistered) {
            abstractButton.setForeground(Color.GREEN);
            chooseUserPanel.updateUI();
        } else {
            JRadioButton radioButton = new JRadioButton(onlineUser.getUsername(), false);
            radioButton.setForeground(Color.GREEN);
            radioButton.addActionListener(this);
            userButtonGroup.add(radioButton);
            chooseUserPanel.add(radioButton);
            chooseUserScrollPane.updateUI();
            registeredUsers.add(onlineUser);
        }
        onlineUsernames.add(onlineUser.getUsername());
    }

    /**
     * Set a user to offline
     *
     * @param offlineUsername the username of the user just leave
     */
    public void setUserOffline(String offlineUsername) {
        for (Enumeration<AbstractButton> e = userButtonGroup.getElements(); e.hasMoreElements(); ) {
            AbstractButton abstractButton = e.nextElement();
            if (abstractButton.getActionCommand().equals(offlineUsername)) {
                abstractButton.setForeground(Color.BLACK);
                chooseUserPanel.updateUI();
                break;
            }
        }

        for (String onlineUsername : onlineUsernames) {
            if (offlineUsername.equals(onlineUsername)) {
                onlineUsernames.remove(onlineUsername);
                break;
            }
        }
    }

    /**
     * Update all users' information of the client
     *
     * @param users           all registered users
     * @param onlineUsernames all usernames of online users
     */
    public void updateUsers(List<User> users, List<String> onlineUsernames) {
        registeredUsers = users;
        this.onlineUsernames = onlineUsernames;
        chooseUserPanel.removeAll();
        chooseUserPanel.setLayout(new GridLayout(0, 1));
        for (User user : users) {
            if (!user.getUsername().equals(username)) {
                String name = user.getUsername();
                JRadioButton radioButton = new JRadioButton(name, false);
                for (String onlineUsername : onlineUsernames) {
                    if (name.equals(onlineUsername)) {
                        radioButton.setForeground(Color.GREEN);
                    }
                }
                radioButton.addActionListener(this);
                userButtonGroup.add(radioButton);
                chooseUserPanel.add(radioButton);
            }
        }
        chooseUserPanel.repaint();
    }

    /**
     * Show message in history area
     * @param message the message need to be displayed
     */
    public void appendMessage(Message message) {
        StringBuilder builder = new StringBuilder();
        String formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(message.getDate());
        if (message.getFrom().equals(username)) {
            builder.append("To " + message.getTo());
        } else {
            builder.append("From " + message.getFrom());
        }
        builder.append("      (" + formatDate + ")\n" + "      " + message.getContent() + "\n\n");
        historyArea.append(builder.toString());
    }

    /**
     * Show message in history area
     *
     * @param username the username who send the message
     * @param date     the date of the message
     * @param message  the content of the message
     */
    public void appendMessage(String username, Date date, String message) {
        appendMessage(new Message(username, this.username, date, message));
    }

    /**
     * Send message to another user, if the user is offline or send failed,
     * the message will send to server
     *
     * @param date     the date of the message
     * @param message  the content of the message
     */
    public void sendMessage(Date date, String message) {
        boolean isReceiverOnline = false;
        for (String onlineUsername : onlineUsernames) {
            if (onlineUsername.equals(sendToUsername)) {
                isReceiverOnline = true;
                for (User user : registeredUsers) {
                    if (sendToUsername.equals(user.getUsername())) {
                        try {
                            Registry registry = LocateRegistry.getRegistry(user.getIp(), user.getPort());
                            Client client = (Client) registry.lookup(user.getRemoteName());
                            client.receiveMessage(username, date, message);
                        } catch (NotBoundException | RemoteException e) {
                            try {
                                server.recordMessage(username, sendToUsername, date, message);
                            } catch (RemoteException e1) {
                                e1.printStackTrace();
                            }
                            e.printStackTrace();
                        }
                        break;
                    }
                }
                break;
            }
        }

        if (!isReceiverOnline) {
            try {
                server.recordMessage(username, sendToUsername, date, message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    class sendActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String message = messageEditArea.getText().toString();
            if (!message.isEmpty()) {
                if (sendToUsername != null && !sendToUsername.isEmpty()) {
                    appendMessage(new Message(username, sendToUsername, new Date(), message));
                    messageEditArea.setText("");
                    sendMessage(new Date(), message);
                } else {
                    JOptionPane.showMessageDialog(MainGui.this, "Please choose a user to send the message.");
                }
            }
        }
    }
}
