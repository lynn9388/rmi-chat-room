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
import java.util.Date;
import java.util.List;


/**
 * Created by ZhangJing on 16/10/24.
 * <p>
 * 客户端界面
 */
public class MainGUI extends JFrame {
    private JPanel contentPane;
    private JComboBox comboBox;
    private JTextArea showMessage;//信息显示区域
    private JTextArea sendMessage;//信息输入区域
    private Button send;

    public MainGUI() {
        setTitle("聊天室");
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

        //下拉框标签
        Label labelForCombo = new Label("选择聊天对象：");
        labelForCombo.setBounds(10, 10, 100, 20);
        contentPane.add(labelForCombo);

        //下拉框
        comboBox = new JComboBox();
        comboBox.addItem("¥¥¥");
        comboBox.addItem("@@@");
        comboBox.setBounds(110, 10, 150, 23);
        contentPane.add(comboBox);

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
        scroll.setBounds(10, 40, 595, 400);
        scroll.setViewportView(showMessage);
        contentPane.add(scroll);

        //消息输入区
        sendMessage = new JTextArea();
        sendMessage.setColumns(45);
        JScrollPane scroll2 = new JScrollPane(sendMessage);
        //分别设置水平和垂直滚动条自动出现
        scroll2.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll2.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll2.setBounds(10, 445, 595, 150);
        scroll2.setViewportView(sendMessage);
        contentPane.add(scroll2);

        //发送按钮，默认按enter键也会发送
        send = new Button("发送");
        send.addActionListener(new sendActionListener());
        send.setBounds(520, 600, 65, 25);
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
     * @param usernames
     */
    public void updateUsernames(List<String> usernames) {


    }

    /**
     * @param username 用户名
     * @param date     日期
     * @param message
     */
    public void appendMessage(String username, Date date, String message) {

    }

    /**
     * @param username
     * @param date
     * @param message
     */
    public void sendMessage(String username, Date date, String message) {

    }

    //send按钮，点击事件监听
    class sendActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String message = sendMessage.getText().toString();  //用户输入的信息

            showMessage.append(" 我(" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ")：\n" + "      " + message + "\n");
            sendMessage.setText("");
            System.out.println(message);
        }
    }

}
