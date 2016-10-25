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
    private JPanel contentPane;
    private JTextArea showMessage;//信息显示区域
    private JTextArea sendMessage;//信息输入区域
    private Button send;
    private ButtonGroup chooseUser;
    private JPanel chooseUserPanel;
    private JScrollPane chooseUserScroll;
    private String username;
    private List<User> allRegisteredUsers;

    public MainGui(String username) {
        this.username = username;
    }

    public void createAndShow() {
        setTitle("ChatRoom");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 619, 650);
        setResizable(false);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        //标签
        Label labelForShow = new Label("Choose User:");
        labelForShow.setBounds(10, 5, 100, 17);
        contentPane.add(labelForShow);

        //消息显示区域,自动添加滚动条
        showMessage = new JTextArea();
        showMessage.setColumns(45);
        showMessage.setEditable(false); //不可编辑
        JScrollPane scroll = new JScrollPane(showMessage);
        //分别设置水平和垂直滚动条自动出现
        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(10, 25, 595, 390);
        scroll.setViewportView(showMessage);
        contentPane.add(scroll);

        //标签
        Label labelForCombo = new Label("Choose User:");
        labelForCombo.setBounds(10, 425, 100, 20);
        contentPane.add(labelForCombo);

        //选择用户
        chooseUserPanel = new JPanel();
        chooseUserScroll = new JScrollPane(chooseUserPanel);
        //设置垂直滚动条自动出现
        chooseUserScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        chooseUser = new ButtonGroup();
        chooseUserScroll.setBounds(10, 450, 100, 150);
        chooseUserScroll.setViewportView(chooseUserPanel);
        contentPane.add(chooseUserScroll);

        //标签
        Label labelForSend = new Label("text message:");
        labelForSend.setBounds(130, 425, 100, 20);
        contentPane.add(labelForSend);

        //消息输入区
        sendMessage = new JTextArea();
        sendMessage.setColumns(25);
        JScrollPane scroll2 = new JScrollPane(sendMessage);
        //分别设置水平和垂直滚动条自动出现
        scroll2.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll2.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll2.setBounds(130, 445, 370, 170);
        scroll2.setViewportView(sendMessage);
        contentPane.add(scroll2);

        //发送按钮，默认按enter键也会发送
        send = new Button("Send");
        send.addActionListener(new sendActionListener());
        send.setBounds(520, 510, 75, 35);
        contentPane.add(send);

        this.setVisible(true);

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
        } else {                      //没有注册，直接添加一个在线用户
            JRadioButton readd = new JRadioButton(onlineUser.getUsername() + "(online)");
            chooseUser.add(readd);
            chooseUserPanel.add(readd);
            readd.addActionListener(this);
            chooseUserScroll.updateUI();
            System.out.println("直接添加上线用户" + onlineUser.getUsername());
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
    }

    /**
     * Update all users' information of the client
     *
     * @param users           all registered users
     * @param onlineUsernames all usernames of online users
     */
    public void updateUsers(List<User> users, List<String> onlineUsernames) {
        allRegisteredUsers = users;
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
        showMessage.append(username + "(" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date) + ")：\n" + "      " + message + "\n");
    }

    /**
     * @param username  name of user receving message
     * @param date      time
     * @param message
     */
    public void sendMessage(String username, Date date, String message) {

    }

    /**
     * send按钮，点击事件监听
     */
    class sendActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String message = sendMessage.getText().toString();  //用户输入的信息

            showMessage.append(" 我(" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ")：\n" + "      " + message + "\n");
            sendMessage.setText("");//发送后清空输入框
            System.out.println(message);
        }
    }

}
