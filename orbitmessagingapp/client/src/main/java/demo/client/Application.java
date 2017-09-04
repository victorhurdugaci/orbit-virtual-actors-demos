package demo.client;

import demo.shared.Mailbox;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.HashMap;

@SpringBootApplication(scanBasePackages = {"demo.client"})
public class Application {
    private static HashMap<String, Mailbox> mailboxes = new HashMap<>();

    public static void main(String args[]) {
        new SpringApplicationBuilder(Application.class).run(args);

        while (true) {
            System.out.println();
            System.out.println("========================================");
            System.out.println("======== 1. Get messages        ========");
            System.out.println("======== 2. Send message        ========");
            System.out.println("======== 3. Delete all messages ========");
            System.out.println("========================================");

            final String option = System.console().readLine();
            try {
                switch (option) {
                    case "1":
                        showMessages();
                        break;
                    case "2":
                        sendMessage();
                        break;
                    case "3":
                        deleteAllMessages();
                        break;
                    default:
                        break;
                }
            } catch (Exception ex) {
                System.out.println("Exception: " + ex.getMessage());
            }
        }
    }

    private static Mailbox getMailbox(String id) {
        if (!mailboxes.containsKey(id)) {
            mailboxes.put(id, new MailboxLocal(id));
        }

        return mailboxes.get(id);
    }

    private static void showMessages() {
        System.out.println("Account: ");
        final String account = System.console().readLine();

        Mailbox mail = getMailbox(account);
        System.out.println("=== Messages: ===");
        mail.getAllMessages().join()
                .forEach(System.out::println);
        System.out.println("=== === ===\n");
    }

    private static void sendMessage() {
        System.out.println("Account: ");
        final String account = System.console().readLine();
        System.out.println("Message: ");
        final String message = System.console().readLine();

        Mailbox mailbox = getMailbox(account);
        mailbox.addMessage(message).join();
    }

    private static void deleteAllMessages() {
        System.out.println("Account: ");
        final String account = System.console().readLine();

        Mailbox accountActor = getMailbox(account);
        accountActor.deleteAllMessages();
    }
}

