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

public interface Client extends Remote {
    /**
     * Add a new online user
     *
     * @param onlineUser the user just online, and the user may have registered before
     * @throws RemoteException
     */
    void addOnlineUser(User onlineUser) throws RemoteException;

    /**
     * Set a user to offline
     *
     * @param offlineUsername the username of the user just leave
     * @throws RemoteException
     */
    void setUserOffline(String offlineUsername) throws RemoteException;

    /**
     * Update all users' information of the client
     *
     * @param users           all registered users
     * @param onlineUsernames all usernames of online users
     * @throws RemoteException
     */
    void updateUsers(List<User> users, List<String> onlineUsernames) throws RemoteException;

    void receiveMessage(Date date, String message) throws RemoteException;
}
