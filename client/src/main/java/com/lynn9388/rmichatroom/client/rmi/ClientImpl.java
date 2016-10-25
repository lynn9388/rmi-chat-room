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

package com.lynn9388.rmichatroom.client.rmi;

import com.lynn9388.rmichatroom.rmi.Client;
import com.lynn9388.rmichatroom.rmi.Server;
import com.lynn9388.rmichatroom.rmi.User;

import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;

public class ClientImpl extends UnicastRemoteObject implements Client {
    private static final String SERVER_IP = "";
    private static final int SERVER_PORT = 1099;

    private Server server;

    public ClientImpl(String username) throws RemoteException {
        try {
            Registry registry = LocateRegistry.getRegistry(SERVER_IP, SERVER_PORT);
            server = (Server) registry.lookup(Server.NAME);
            System.out.println("Bound server success!");


        } catch (NotBoundException | IOException e) {
            System.err.println("Bound server failed!");
            e.printStackTrace();
        }
    }

    @Override
    public void addOnlineUser(User onlineUser) throws RemoteException {

    }

    @Override
    public void setUserOffline(String offlineUsername) throws RemoteException {

    }

    @Override
    public void updateUsers(List<User> users, List<String> onlineUsernames) throws RemoteException {

    }

    @Override
    public void receiveMessage(Date date, String message) throws RemoteException {

    }

    public boolean isConnectedToServer() {
        return server != null;
    }

    /**
     * Get the local ip address of client that could connect to server
     *
     * @param serverIp   the ip address of server
     * @param serverPort the port of server
     * @return the ip address that could connected to server
     */
    private String getConnectedIp(String serverIp, int serverPort) {
        String ip = null;
        try {
            Socket socket = new Socket(serverIp, serverPort);
            if (socket.isConnected()) {
                ip = socket.getLocalAddress().getHostAddress();
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ip;
    }
}
