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

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 8116975872176331171L;
    private String username;
    private String ip;
    private int port;
    private String remoteName;

    public User(String username, String ip, int port, String remoteName) {
        this.username = username;
        this.ip = ip;
        this.port = port;
        this.remoteName = remoteName;
    }

    public String getUsername() {
        return username;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getRemoteName() {
        return remoteName;
    }
}
