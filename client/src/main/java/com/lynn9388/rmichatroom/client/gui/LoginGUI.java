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

/**
 * Created by ZhangJing on 16/10/24.
 */

public class LoginGUI extends JFrame implements ActionListener {
    String Usersname; //定义记录用户名
    String Userspassword;//定义记录密码
    private JPanel contentpane;
    private JLabel labelName, labelPassword;
    private JButton buttonLogin, buttonRegister;
    private JTextField userName;
    private JPasswordField password;


    public LoginGUI() {
        setTitle("登陆界面");
        setResizable(false);
        setBounds(400, 200, 276, 190);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        contentpane = new JPanel();//创建面板对象
        contentpane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentpane);
        contentpane.setLayout(null);

        //标签，用户名
        labelName = new JLabel("用户名:");
        labelName.setBounds(20, 45, 60, 20);
        contentpane.add(labelName);

        //用户名 输入框
        userName = new JTextField(16);
        userName.setBounds(100, 45, 140, 24);
        contentpane.add(userName);

/*        //标签，密码
        labelPassword = new JLabel("密   码:");
        labelPassword.setBounds(20,85,60,20);
        contentpane.add(labelPassword);

        //密码，输入框
        password=new JPasswordField(16);
        password.setBounds(100,85,140,24);
        contentpane.add(password);


        //注册按钮
        buttonRegister=new JButton("注册");
        buttonRegister.setBounds(135,130,60,30);
        contentpane.add(buttonRegister);
*/

        //登陆按钮
        buttonLogin = new JButton("登陆");
        buttonLogin.setBounds(180, 85, 60, 30);
        buttonLogin.addActionListener(this);
        contentpane.add(buttonLogin);

/*        JLabel label=new JLabel("<html>Tips:已存在的用户直接输入用户名密码登陆;</br>    不存在的用户输入用户名密码注册</html>");
        label.setBounds(13, 130, 240, 60);
        contentpane.add(label);
*/

        getLayeredPane().setLayout(null);
        setVisible(true);
        validate();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());


            LoginGUI frame = new LoginGUI();
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

    //登陆按钮，监听事件
    @Override
    public void actionPerformed(ActionEvent e) {
        String username = userName.getText();

        System.out.println(username);
        dispose();//登录界面 消失

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
