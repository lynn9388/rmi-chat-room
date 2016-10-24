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
import com.lynn9388.rmichatroom.rmi.Conversation;
import com.lynn9388.rmichatroom.rmi.Server;
import com.lynn9388.rmichatroom.rmi.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ServerImpl extends UnicastRemoteObject implements Server {
    private Map<String, User> users;
    private Map<String, Date> lastHeartbeatTimes;
    private Map<String, Client> onlineClients;
    private Map<String, Conversation> conversations;

    public ServerImpl() throws RemoteException {
        users = new ConcurrentHashMap<>();
        lastHeartbeatTimes = new ConcurrentHashMap<>();
        onlineClients = new ConcurrentHashMap<>();
        conversations = new ConcurrentHashMap<>();
    }

    @Override
    public boolean register(User user) throws RemoteException {
        if (users.put(user.getUsername(), user) == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean sendHeartbeat(String username) throws RemoteException {
        boolean result = lastHeartbeatTimes.containsKey(username);
        lastHeartbeatTimes.put(username, new Date());
        return result;
    }

    @Override
    public List<User> getUsers() throws RemoteException {
        return new ArrayList<>(users.values());
    }

    @Override
    public void recordMessage(String from, String to, Date date, String message) throws RemoteException {
        String key = getConversationKey(from, to);
        if (!conversations.containsKey(key)) {
            conversations.put(key, new Conversation());
        }
        Conversation conversation = conversations.get(key);
        conversation.addMessage(from, to, date, message);
    }

    private String getConversationKey(String from, String to) {
        return from + " & " + to;
    }

    public void checkClientsStatus() {
        Date now = new Date();
        Set<Map.Entry<String, Date>> set = lastHeartbeatTimes.entrySet();
        for (Map.Entry entry : set) {
            if (now.getTime() - ((Date) entry.getValue()).getTime() > Server.HEARTBEAT_RATE * 10) {
                set.remove(entry);
                onlineClients.remove(entry.getKey());
            }
        }
    }
}
