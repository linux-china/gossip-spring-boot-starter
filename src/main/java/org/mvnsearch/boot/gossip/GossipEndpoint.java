package org.mvnsearch.boot.gossip;


import io.scalecube.cluster.Cluster;
import io.scalecube.cluster.transport.api.Message;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * gossip endpoint
 *
 * @author linux_china
 */
@Endpoint(id = "gossip")
public class GossipEndpoint {
    private Cluster cluster;

    public GossipEndpoint(Cluster cluster) {
        this.cluster = cluster;
    }

    @ReadOperation
    public Map<String, Object> gossipCluster() {
        Map<String, Object> info = new HashMap<>();
        info.put("status", cluster.isShutdown() ? "shutdown" : "active");
        info.put("members", cluster.members().stream().map(member -> {
                    Map<String, Object> temp = new HashMap<>();
                    temp.put("host", member.address().host());
                    temp.put("port", member.address().port());
                    if (member.id() != null && !member.id().isEmpty()) {
                        temp.put("id", member.id());
                    }
                    return temp;
                }).collect(Collectors.toList())
        );
        return info;
    }

    @WriteOperation
    public String showdown(@Selector String ops) {
        if (ops.equals("shutdown")) {
            cluster.shutdown();
        }
        return "shutdown";
    }

    @WriteOperation
    public Mono<String> gossipEvent(@Selector String arg0,
                                    String type, String source, String datacontenttype, String data) {
        Message msg = Message.builder()
                .data(data)
                .header("id", UUID.randomUUID().toString())
                .header("type", type)
                .header("source", source)
                .header("datacontenttype", source)
                .header("time", ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .sender(cluster.member().address())
                .build();
        return cluster.spreadGossip(msg);
    }
}
