package org.mvnsearch.boot.gossip;


import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * gossip endpoint
 *
 * @author linux_china
 */
@Component
@Endpoint(id = "gossip")
public class GossipEndpoint {

    @ReadOperation
    public Map<String, Object> gossipCluster() {
        Map<String, Object> info = new HashMap<>();
        return info;
    }
}
