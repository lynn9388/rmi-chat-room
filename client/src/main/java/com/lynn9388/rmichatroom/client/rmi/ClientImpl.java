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

import com.lynn9388.rmichatroom.client.gui.MainGui;
import com.lynn9388.rmichatroom.rmi.Client;
import com.lynn9388.rmichatroom.rmi.Server;
import com.lynn9388.rmichatroom.rmi.User;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

public class ClientImpl extends UnicastRemoteObject implements Client {
    private Server server;
    private MainGui mainGui;

    public ClientImpl() throws RemoteException {
        try {
            Registry registry = LocateRegistry.getRegistry(Server.IP, Server.PORT);
            server = (Server) registry.lookup(Server.NAME);
            System.out.println("Bound server success!");
        } catch (NotBoundException | IOException e) {
            System.err.println("Bound server failed!");
            e.printStackTrace();
        }
    }

    @Override
    public void addOnlineUser(User onlineUser) throws RemoteException {
        mainGui.addOnlineUser(onlineUser);
    }

    @Override
    public void setUserOffline(String offlineUsername) throws RemoteException {
        mainGui.setUserOffline(offlineUsername);
    }

    @Override
    public void receiveMessage(String username, Date date, String message) throws RemoteException {
        mainGui.appendMessage(username, date, message);
    }

    public Server getServer() {
        return server;
    }

    public void setMainGui(MainGui mainGui) {
        this.mainGui = mainGui;
    }
}
