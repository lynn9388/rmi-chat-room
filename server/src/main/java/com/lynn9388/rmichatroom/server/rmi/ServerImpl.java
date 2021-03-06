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

package com.lynn9388.rmichatroom.server.rmi;

import com.lynn9388.rmichatroom.rmi.Client;
import com.lynn9388.rmichatroom.rmi.Message;
import com.lynn9388.rmichatroom.rmi.Server;
import com.lynn9388.rmichatroom.rmi.User;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerImpl extends UnicastRemoteObject implements Server {
    private Map<String, User> registeredUsers;
    private Map<String, Date> lastHeartbeatTimes;
    private Map<String, Client> onlineClients;
    private Map<String, List<Message>> missedMessages;

    public ServerImpl() throws RemoteException {
        registeredUsers = new ConcurrentHashMap<>();
        lastHeartbeatTimes = new ConcurrentHashMap<>();
        onlineClients = new ConcurrentHashMap<>();
        missedMessages = new ConcurrentHashMap<>();
    }

    @Override
    public boolean register(User user) throws RemoteException {
        boolean result = false;
        registeredUsers.put(user.getUsername(), user);
        lastHeartbeatTimes.put(user.getUsername(), new Date());

        try {
            Registry registry = LocateRegistry.getRegistry(user.getIp(), user.getPort());
            Client client = (Client) registry.lookup(user.getRemoteName());
            onlineClients.put(user.getUsername(), client);
            System.out.println("User registered:" + user.getUsername());
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

        for (Map.Entry entry : onlineClients.entrySet()) {
            Client client = (Client) entry.getValue();
            if (!entry.getKey().equals(user.getUsername())) {
                try {
                    client.addOnlineUser(user);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    @Override
    public boolean sendHeartbeat(String username) throws RemoteException {
        boolean result = lastHeartbeatTimes.containsKey(username);
        lastHeartbeatTimes.put(username, new Date());
        System.out.println("User send heartbeat signal:" + username);
        return result;
    }

    public List<User> getRegisteredUsers() throws RemoteException {
        return new ArrayList<>(registeredUsers.values());
    }

    @Override
    public List<String> getOnlineUsernames() throws RemoteException {
        return new ArrayList<>(onlineClients.keySet());
    }

    @Override
    public void recordMessage(String from, String to, Date date, String message) throws RemoteException {
        if (!missedMessages.containsKey(to)) {
            missedMessages.put(to, new ArrayList<>());
        }
        missedMessages.get(to).add(new Message(from, to, date, message));
        System.out.println("Record message from " + from + " to " + to + ":" + message);
    }

    @Override
    public List<Message> getMissedMessages(String username) throws RemoteException {
        return missedMessages.get(username);
    }

    @Override
    public void removeMissedMessages(String username) throws RemoteException {
        missedMessages.remove(username);
    }

    /**
     * Check clients' status and update users' information for online users
     */
    public void checkClientsStatus() {
        Date now = new Date();
        List<String> offlineUsernames = new ArrayList<>();
        for (Map.Entry entry : lastHeartbeatTimes.entrySet()) {
            if (now.getTime() - ((Date) entry.getValue()).getTime() > Server.HEARTBEAT_RATE * 10) {
                onlineClients.remove(entry.getKey());
                offlineUsernames.add((String) entry.getKey());
                System.out.println("User offline:" + entry.getKey());
            }
        }

        for (String username : offlineUsernames) {
            lastHeartbeatTimes.remove(username);
        }

        for (Client client : onlineClients.values()) {
            for (String username : offlineUsernames) {
                try {
                    client.setUserOffline(username);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
