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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Conversation {
    private List<Message> messages;

    public Conversation() {
        this.messages = new ArrayList<>();
    }

    public void addMessage(String from, String to, Date date, String message) {
        messages.add(new Message(from, to, date, message));
    }

    public List<Message> getMessages() {
        return messages;
    }
}
