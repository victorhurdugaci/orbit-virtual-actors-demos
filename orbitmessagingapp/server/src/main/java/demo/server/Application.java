package demo.server;

import cloud.orbit.actors.Stage;
import cloud.orbit.actors.cluster.RedisClusterBuilder;
import cloud.orbit.actors.cluster.RedisClusterPeer;
import cloud.orbit.spring.EnableOrbit;
import cloud.orbit.spring.OrbitSpringConfigurationAddon;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.stereotype.Component;

@EnableOrbit
@SpringBootApplication(scanBasePackages = {"demo.server"})
public class Application {
    public static void main(String args[]) {
        new SpringApplicationBuilder(Application.class)
                .run(args);
    }
}

@Component
class OrbitConfiguration implements OrbitSpringConfigurationAddon {
    public void configure(Stage stage) {
        stage.setMode(Stage.StageMode.HOST);
        stage.setDefaultActorTTL(20 * 1000);

        final String redisUri = "redis://localhost:6379";

        RedisClusterPeer redisPeer = new RedisClusterBuilder()
                .actorDirectoryUri(redisUri)
                .nodeDirectoryUri(redisUri)
                .messagingUri(redisUri)
                .build();

        stage.setClusterPeer(redisPeer);
    }
}

