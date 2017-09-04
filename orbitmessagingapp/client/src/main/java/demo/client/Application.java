package demo.client;

import cloud.orbit.actors.Actor;
import cloud.orbit.actors.Stage;
import cloud.orbit.actors.cluster.RedisClusterBuilder;
import cloud.orbit.actors.cluster.RedisClusterPeer;
import cloud.orbit.spring.EnableOrbit;
import cloud.orbit.spring.OrbitSpringConfigurationAddon;
import demo.shared.AccountActor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.stereotype.Component;

@EnableOrbit
@SpringBootApplication(scanBasePackages = {"demo.client"})
public class Application {
    public static void main(String args[]) {
        new SpringApplicationBuilder(Application.class).run(args);

        while (true) {
            System.out.println("\n1. Get messages\n2. Send message\n3. Delete all messages");
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

    private static void showMessages() {
        System.out.println("Account: ");
        final String account = System.console().readLine();

        AccountActor accountActor = Actor.getReference(AccountActor.class, account);
        System.out.println("=== Messages: ===");
        accountActor.getAllMessages().join()
                .forEach(System.out::println);
        System.out.println("=== === ===\n");
    }

    private static void sendMessage() {
        System.out.println("Account: ");
        final String account = System.console().readLine();
        System.out.println("Message: ");
        final String message = System.console().readLine();

        AccountActor accountActor = Actor.getReference(AccountActor.class, account);
        accountActor.addMessage(message).join();
    }

    private static void deleteAllMessages() {
        System.out.println("Account: ");
        final String account = System.console().readLine();

        AccountActor accountActor = Actor.getReference(AccountActor.class, account);
        accountActor.deleteAllMessages();
    }
}

@Component
class OrbitConfiguration implements OrbitSpringConfigurationAddon {
    public void configure(Stage stage) {
        stage.setMode(Stage.StageMode.CLIENT);

        final String redisUri = "redis://localhost:6379";

        RedisClusterPeer redisPeer = new RedisClusterBuilder()
                .actorDirectoryUri(redisUri)
                .nodeDirectoryUri(redisUri)
                .messagingUri(redisUri)
                .build();

        stage.setClusterPeer(redisPeer);
    }
}

