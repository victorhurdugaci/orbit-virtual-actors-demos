package demo.shared;

import java.util.List;

public interface Mailbox {
    void addMessage(String text);

    List<Message> getAllMessages();

    void deleteAllMessages();
}
