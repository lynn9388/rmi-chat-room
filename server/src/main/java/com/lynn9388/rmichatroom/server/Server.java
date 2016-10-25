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

package com.lynn9388.rmichatroom.server;

import com.lynn9388.rmichatroom.server.rmi.ServerImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;
import java.util.TimerTask;

public class Server {
    public static final int PORT = 1099;

    public static void main(String[] args) {
        try {
            com.lynn9388.rmichatroom.rmi.Server stub = new ServerImpl();

            Registry registry = LocateRegistry.createRegistry(PORT);
            registry.rebind(com.lynn9388.rmichatroom.rmi.Server.NAME, stub);
            System.out.println("Bound success!");

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    ((ServerImpl) stub).checkClientsStatus();
                }
            }, 0, 10000);
        } catch (RemoteException e) {
            System.err.print("Bound failed!");
            e.printStackTrace();
        }
    }
}
