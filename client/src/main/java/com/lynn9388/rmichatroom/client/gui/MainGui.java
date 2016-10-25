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

import com.lynn9388.rmichatroom.rmi.User;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

public class MainGui extends JFrame implements ActionListener {
    private JTextArea historyArea;
    private ButtonGroup chooseUser;
    private JPanel chooseUserPanel;
    private JScrollPane chooseUserScroll;
    private JTextArea messageEditArea;
    private Button sendButton;

    private String username;
    private List<User> registeredUsers;
    private List<String> onlineUsers;

    public MainGui(String username) {
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
        chooseUserScroll = new JScrollPane(chooseUserPanel);
        chooseUserScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        chooseUser = new ButtonGroup();
        chooseUserScroll.setBounds(10, 450, 100, 150);
        contentPanel.add(chooseUserScroll);

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

    /**
     * 监听radio按钮
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String username = e.getActionCommand();

        System.out.println("点击了radio" + username);
    }

    /**
     * Add a new online user
     *
     * @param onlineUser the user just online, and the user may have registered before
     */
    public void addOnlineUser(User onlineUser) {
        boolean isRegistered = false;
        AbstractButton ab = new JRadioButton();
        for (Enumeration<AbstractButton> e = chooseUser.getElements(); e.hasMoreElements(); ) {
            ab = e.nextElement();
            if (ab.getActionCommand().equals(onlineUser.getUsername())) { //已经注册了，但是不在线，只需要加上在线标志
                isRegistered = true;
                break;
            }
        }
        if (isRegistered) { //已经注册了，但是不在线，只需要加上在线标志
            chooseUser.remove(ab);
            chooseUserPanel.remove(ab);
            JRadioButton readd = new JRadioButton(onlineUser.getUsername() + "(online)");
            chooseUser.add(readd);
            chooseUserPanel.add(readd);
            readd.addActionListener(this);
            chooseUserScroll.updateUI();
            System.out.println(onlineUser.getUsername() + "已经存在,上线了");
            addToOnlineUserList(onlineUser.getUsername()); //添加到在线用户列表
        } else {                      //没有注册，直接添加一个在线用户
            JRadioButton readd = new JRadioButton(onlineUser.getUsername() + "(online)");
            chooseUser.add(readd);
            chooseUserPanel.add(readd);
            readd.addActionListener(this);
            chooseUserScroll.updateUI();
            System.out.println("直接添加上线用户" + onlineUser.getUsername());
            addToRegisterList(onlineUser); //之前没有注册，加入到注册list
        }

    }

    /**
     * Set a user to offline
     *
     * @param offlineUsername the username of the user just leave
     */
    public void setUserOffline(String offlineUsername) {

        for (Enumeration<AbstractButton> e = chooseUser.getElements(); e.hasMoreElements(); ) {
            AbstractButton ab = e.nextElement();
            if (ab.getActionCommand().equals(offlineUsername + "(online)")) {
                //               ab.setActionCommand(offlineUsername);
                chooseUser.remove(ab);
                chooseUserPanel.remove(ab);
                JRadioButton readd = new JRadioButton(offlineUsername);
                chooseUser.add(readd);
                chooseUserPanel.add(readd);
                readd.addActionListener(this);
                chooseUserScroll.updateUI();
                System.out.println(offlineUsername + "下线了");
            }
        }
        //在onlineuser列表中删除下线的用户
        deleteFromOnlineUserList(offlineUsername);
    }

    /**
     * Update all users' information of the client
     *
     * @param users           all registered users
     * @param onlineUsernames all usernames of online users
     */
    public void updateUsers(List<User> users, List<String> onlineUsernames) {
        registeredUsers = users;
        onlineUsers = onlineUsernames;
        int num = users.size();
        List<JRadioButton> radioList = new ArrayList<>();
        chooseUserPanel.removeAll();
        chooseUserPanel.setLayout(new GridLayout(0, 1));
        for (int i = 0; i < num; i++) {
            if (!users.get(i).getUsername().equals(username)) {   //用户列表不用显示用户自己
                if (onlineUsernames.contains(users.get(i).getUsername())) {  //在线
                    radioList.add(i, new JRadioButton(users.get(i).getUsername() + "(online)", false));
                } else {
                    radioList.add(i, new JRadioButton(users.get(i).getUsername(), false));
                }
                radioList.get(i).addActionListener(this);
                chooseUser.add(radioList.get(i));
                chooseUserPanel.add(radioList.get(i));
            }
        }
        chooseUserPanel.repaint();
    }

    /**
     * 接收某用户在某时刻发送到的信息
     * @param username 发送message的用户的name
     * @param date     日期
     * @param message  接收到的信息
     */
    public void appendMessage(String username, Date date, String message) {
        historyArea.append(username + "(" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date) + ")：\n" + "      " + message + "\n\n");
    }

    /**
     * @param username  name of user receving message
     * @param date      time
     * @param message
     */
    public void sendMessage(String username, Date date, String message) {

    }

    /**
     *
     * @param name 下线的用户，从在线用户列表中删除
     */
    public void deleteFromOnlineUserList(String name) {
        for (int i = 0; i < onlineUsers.size(); i++) {
            if (onlineUsers.get(i).equals(name)) {
                onlineUsers.remove(i);
            }
            break;
        }
    }

    /**
     * @param name 上线的用户，添加到在线用户列表
     */
    public void addToOnlineUserList(String name) {
        onlineUsers.add(name);
    }

    /**
     * @param user 新注册的用户，添加到register列表
     */
    public void addToRegisterList(User user) {
        registeredUsers.add(user);
    }

    /**
     * send按钮，点击事件监听
     */
    class sendActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String message = messageEditArea.getText().toString();  //用户输入的信息

            historyArea.append(" 我(" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ")：\n" + "      " + message + "\n");
            messageEditArea.setText("");//发送后清空输入框
            sendMessage(username, new Date(), message);
            System.out.println(message);
        }
    }

}
