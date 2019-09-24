package org.mvnsearch.boot.gossip;


import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;

import java.util.HashMap;
import java.util.Map;

/**
 * gossip endpoint
 *
 * @author linux_china
 */
@Endpoint(id = "gossip")
public class GossipEndpoint {

    @ReadOperation
    public Map<String, Object> gossipCluster() {
        Map<String, Object> info = new HashMap<>();
        return info;
    }

    @WriteOperation
    public void updateStatus(@Selector String ops) {
        // ops, such as shutdown
    }

    @WriteOperation
    public void gossipEvent(@Selector String name, GossipEvent event) {

    }
}
