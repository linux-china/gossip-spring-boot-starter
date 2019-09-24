package org.mvnsearch.boot.gossip;

import io.scalecube.cluster.ClusterMessageHandler;
import io.scalecube.cluster.membership.MembershipEvent;
import io.scalecube.cluster.transport.api.Message;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * seed server 2
 *
 * @author linux_china
 */
@SpringBootApplication
public class SeedServer2 {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(SeedServer2.class, args);
    }

    @Bean
    public ClusterMessageHandler clusterMessageHandler() {
        return new ClusterMessageHandler() {
            @Override
            public void onMessage(Message message) {

            }

            @Override
            public void onGossip(Message gossip) {

            }

            @Override
            public void onMembershipEvent(MembershipEvent event) {

            }
        };
    }

}
