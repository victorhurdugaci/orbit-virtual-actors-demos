package demo.server;

import demo.shared.Message;

import java.util.ArrayList;
import java.util.List;

public class MailboxState {
    private List<Message> messages = new ArrayList<>();

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
