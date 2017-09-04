package demo.shared;

import cloud.orbit.actors.Actor;
import cloud.orbit.concurrent.Task;

import java.util.List;

public interface AccountActor extends Actor {
    Task<Void> addMessage(String text);

    Task<List<Message>> getAllMessages();

    Task<Void> deleteAllMessages();
}
