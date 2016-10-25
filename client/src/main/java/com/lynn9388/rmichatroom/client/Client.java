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

package com.lynn9388.rmichatroom.client;

import com.lynn9388.rmichatroom.client.gui.LoginGui;

import javax.swing.SwingUtilities;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

public class Client {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginGui().createAndShow());
    }

    /**
     * Get the ip address of client that could connect to server
     *
     * @param serverIp   the ip address of server
     * @param serverPort the port of server
     * @return the ip address that could connected to server, or null if can't find out the ip address
     */
    public static String getConnectedIp(String serverIp, int serverPort) {
        String ip = null;
        try {
            Socket socket = new Socket(serverIp, serverPort);
            if (socket.isConnected()) {
                if (InetAddress.getByName(serverIp).isSiteLocalAddress()) {
                    ip = socket.getLocalAddress().getHostAddress();
                } else {
                    ip = getExternalIp();
                }
                socket.close();
            }
        } catch (Exception e) {

        }
        return ip;
    }

    /**
     * Get the external ip of the client or the connected router
     *
     * @return the external ip of the client, or null if failed
     */
    private static String getExternalIp() {
        String ip = null;
        try {
            URL url = new URL("http://checkip.amazonaws.com/");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            ip = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ip;
    }
}
