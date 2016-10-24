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

import org.junit.Test;

import javax.swing.*;

/**
 * Created by ZhangJing on 16/10/24.
 */
public class MainGUITest extends JFrame {

    @Test
    public void maingui() {
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


}