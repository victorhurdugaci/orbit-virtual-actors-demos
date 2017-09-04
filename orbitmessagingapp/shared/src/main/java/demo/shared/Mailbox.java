package demo.shared;

import cloud.orbit.concurrent.Task;

import java.util.List;

public interface Mailbox {
    Task<Void> addMessage(String text);

    Task<List<Message>> getAllMessages();

    Task<Void> deleteAllMessages();
}
