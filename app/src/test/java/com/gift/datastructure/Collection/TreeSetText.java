package com.gift.datastructure.Collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

/**
 * 在聊天软件中，发送方发送消息时，遇到网络超时后就会自动重发，因此，接收方可能会收到重复的消息，在显示给用户看的时候，需要首先去重。请练习使用Set去除重复的消息：
 */
public class TreeSetText {
    @Test
    public void test() {
        List<Message> received = Arrays.asList(
                new Message(1, "Hello!"),
                new Message(2, "发工资了吗？"),
                new Message(2, "发工资了吗？"),
                new Message(3, "去哪吃饭？"),
                new Message(3, "去哪吃饭？"),
                new Message(4, "Bye")
        );
        List<Message> displayMessages = process(received);
        for (Message message : displayMessages) {
            System.out.println(message.text);
        }
    }

    static List<Message> process(List<Message> received) {
        Set messageSet = new TreeSet<>(new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                return Integer.compare(o1.sequence, o2.sequence);
            }
        });

        messageSet.addAll(received);
        return new ArrayList<>(messageSet);
    }

    static class Message {
        public final int sequence;
        public final String text;

        public Message(int sequence, String text) {
            this.sequence = sequence;
            this.text = text;
        }
    }
}
