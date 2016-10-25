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

package com.lynn9388.rmichatroom.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

public interface Server extends Remote {
    String IP = "";
    int PORT = 1099;
    String NAME = "Server";

    /**
     * Time interval of milliseconds during each heartbeat
     */
    int HEARTBEAT_RATE = 3000;

    boolean register(User user) throws RemoteException;

    /**
     * Send heartbeat signal to keep user online
     *
     * @param username the name of the user
     * @return true if the user keep online, or false when the user has been set to offline,
     * and should register the user again
     * @throws RemoteException
     */
    boolean sendHeartbeat(String username) throws RemoteException;

    List<User> getRegisteredUsers() throws RemoteException;

    List<String> getOnlineUsernames() throws RemoteException;

    void recordMessage(String from, String to, Date date, String message) throws RemoteException;

    /**
     * Return messages that others send when the user is not online
     *
     * @param username the username of these messages sent to
     * @return the message sent to user, or null if no messages belongs to the user
     * @throws RemoteException
     */
    List<Message> getMissedMessages(String username) throws RemoteException;

    /**
     * Remove all messages sent to the user
     *
     * @param username the username of these messages sent to
     * @throws RemoteException
     */
    void removeMissedMessages(String username) throws RemoteException;
}
