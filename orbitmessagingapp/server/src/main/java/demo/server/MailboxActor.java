package demo.server;

import cloud.orbit.actors.runtime.AbstractActor;
import cloud.orbit.concurrent.Task;
import demo.shared.Mailbox;
import demo.shared.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MailboxActor extends AbstractActor implements Mailbox {
    private List<Message> messages = new ArrayList<>();

    @Override
    public Task<?> activateAsync() {
        printMsg("activateAsync called");

        return Task.done();
    }

    @Override
    public Task<?> deactivateAsync() {
        printMsg("deactivateAsync called");

        return super.deactivateAsync();
    }

    @Override
    public Task<Void> addMessage(String text) {
        printMsg("addMessage called");

        Message newMessage = new Message();

        newMessage.setDateTime(new Date());
        newMessage.setText(text);

        messages.add(newMessage);

        return Task.done();
    }

    @Override
    public Task<List<Message>> getAllMessages() {
        printMsg("getAllMessages called");

        return Task.fromValue(messages);
    }

    @Override
    public Task<Void> deleteAllMessages() {
        printMsg("deleteAllMessages called");

        messages.clear();

        return Task.done();
    }

    private void printMsg(String pattern, Object[]... args) {
        final String prefix = String.format("[%s %s] ",
                new SimpleDateFormat("HH:mm:ss").format(new Date()),
                getIdentity());
        pattern = prefix + pattern;
        System.out.println(String.format(pattern, (Object[]) args));
    }
}
