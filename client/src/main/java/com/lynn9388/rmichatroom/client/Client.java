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

import com.lynn9388.rmichatroom.rmi.Server;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    private static final String SERVER_IP = "";
    private static final int SERVER_PORT = 1099;

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(SERVER_IP, SERVER_PORT);
            Server server = (Server) registry.lookup(Server.NAME);
            System.out.println("Bound success!");
        } catch (NotBoundException e) {
            System.err.println("Bound failed!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}