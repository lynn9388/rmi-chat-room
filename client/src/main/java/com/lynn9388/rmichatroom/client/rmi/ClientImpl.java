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

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;

public class ClientImpl extends UnicastRemoteObject implements Client {
    protected ClientImpl() throws RemoteException {
    }

    @Override
    public void updateUsernames(List<String> usernames) throws RemoteException {

    }

    @Override
    public void receiveMessage(Date date, String message) throws RemoteException {

    }
}
