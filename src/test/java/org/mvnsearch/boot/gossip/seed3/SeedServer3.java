package org.mvnsearch.boot.gossip.seed3;

import io.scalecube.cluster.ClusterMessageHandler;
import io.scalecube.cluster.membership.MembershipEvent;
import io.scalecube.cluster.transport.api.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * seed server 3
 *
 * @author linux_china
 */
@SpringBootApplication
public class SeedServer3 {
    private Logger log = LoggerFactory.getLogger(SeedServer3.class);

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(SeedServer3.class, args);
    }

    @Bean
    public ClusterMessageHandler clusterMessageHandler() {
        return new ClusterMessageHandler() {
            @Override
            public void onMessage(Message message) {

            }

            @Override
            public void onGossip(Message gossip) {
                System.out.println(gossip);
            }

            @Override
            public void onMembershipEvent(MembershipEvent event) {
                if (event.isAdded()) {
                    log.info("Member joined: " + event.member().address());
                } else if (event.isRemoved()) {
                    log.info("Member left: " + event.member().address());
                }
            }
        };
    }

}
