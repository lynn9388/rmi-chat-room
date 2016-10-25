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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by ZhangJing on 16/10/24.
 * <p>
 * 客户端界面
 */
public class MainGUI extends JFrame implements ActionListener {
    private JPanel contentPane;
    private JTextArea showMessage;//信息显示区域
    private JTextArea sendMessage;//信息输入区域
    private Button send;
    private ButtonGroup chooseUser;
    private JPanel chooseUserPanel;
    private JScrollPane chooseUserScroll;

    public MainGUI() {
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

/* 测试
       chooseUserPanel .setLayout(new GridLayout(0,1));
        chooseUser = new ButtonGroup();
        JRadioButton b1 = new JRadioButton("ceshi",false);
        JRadioButton b2 = new JRadioButton("ceshi2",false);
        JRadioButton b3 = new JRadioButton("ceshi3",false);
        JRadioButton b4 = new JRadioButton("ceshi3",false);
        JRadioButton b5 = new JRadioButton("ceshi4",false);
        b1.addActionListener(this);
        chooseUser.add(b5);
        chooseUser.add(b4);
        chooseUser.add(b3);
        chooseUser.add(b1);
        chooseUser.add(b2);

        chooseUserPanel.add(b1);
        chooseUserPanel.add(b2);
        chooseUserPanel.add(b3);
        chooseUserPanel.add(b4);
        chooseUserPanel.add(b5);
*/

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

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            MainGUI frame = new MainGUI();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新用户列表，重绘
     * @param usernames
     */
    public void updateUsernames(List<String> usernames) {
        int num = usernames.size();
        List<JRadioButton> radioList = new ArrayList<>();
        chooseUserScroll.removeAll();
        chooseUserPanel = new JPanel();
        chooseUserScroll = new JScrollPane(chooseUserPanel);
        chooseUserScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        chooseUserPanel.setLayout(new GridLayout(0, 1));
        for (int i = 0; i < num; i++) {
            radioList.add(i, new JRadioButton(usernames.get(i), false));
            radioList.get(i).addActionListener(this);
            chooseUserPanel.add(radioList.get(i));
        }
        chooseUserScroll.setBounds(10, 450, 100, 150);
        chooseUserScroll.setViewportView(chooseUserPanel);
        contentPane.add(chooseUserScroll);
        chooseUserScroll.repaint();
    }

    /**
     * 接收用户在某时刻发送到的信息
     * @param username 用户名
     * @param date     日期
     * @param message  接收到的信息
     */
    public void appendMessage(String username, Date date, String message) {

        showMessage.append(username + "(" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date) + ")：\n" + "      " + message + "\n");

    }

    /**
     * @param username
     * @param date
     * @param message
     */
    public void sendMessage(String username, Date date, String message) {

    }

    /**
     * 监听radio按钮
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String username = e.getActionCommand();
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
