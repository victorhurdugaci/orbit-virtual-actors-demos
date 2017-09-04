package demo.server;

import cloud.orbit.actors.runtime.AbstractActor;
import cloud.orbit.concurrent.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.shared.AccountActor;
import demo.shared.Message;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MailboxActor extends AbstractActor<MailboxState> implements AccountActor {
    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Override
    public Task<?> activateAsync() {
        printMsg("activateAsync called");

        return readState();
    }

    @Override
    public Task<?> deactivateAsync() {
        printMsg("deactivateAsync called");

        return super.deactivateAsync();
    }

    @Override
    protected Task<Boolean> readState() {
        return Task.supplyAsync(() -> {
            File inputFile = new File(getIdentity() + "_state.json");

            if (!inputFile.exists()) {
                printMsg("No previous state found. Creating new");

                this.state = new MailboxState();
            } else {
                printMsg("Loading previous state");

                try {
                    this.state = jsonMapper.readValue(inputFile, MailboxState.class);
                } catch (Exception ex) {

                }
            }

            return true;
        });
    }

    @Override
    protected Task<Void> writeState() {
        return Task.runAsync(() -> {
            printMsg("Saving state");

            File outputFile = new File(getIdentity() + "_state.json");
            try {
                jsonMapper.writeValue(outputFile, state());
            } catch (Exception ex) {

            }
        });
    }

    @Override
    protected Task<Void> clearState() {
        return Task.supplyAsync(() -> {
            printMsg("Clearning state");

            this.state = new MailboxState();

            return writeState();
        });
    }

    @Override
    public Task<Void> addMessage(String text) {
        printMsg("addMessage called");

        Message newMessage = new Message();

        newMessage.setDateTime(new Date());
        newMessage.setText(text);

        state().getMessages().add(newMessage);

        return writeState();
    }

    @Override
    public Task<List<Message>> getAllMessages() {
        printMsg("getAllMessages called");

        return Task.fromValue(state().getMessages());
    }

    @Override
    public Task<Void> deleteAllMessages() {
        printMsg("deleteAllMessages called");

        return clearState();
    }

    private void printMsg(String pattern, Object[]... args) {
        final String prefix = String.format("[%s %s] ",
                new SimpleDateFormat("HH:mm:ss").format(new Date()),
                getIdentity());
        pattern = prefix + pattern;
        System.out.println(String.format(pattern, (Object[]) args));
    }
}
