package org.mvnsearch.boot.gossip;

import io.scalecube.cluster.ClusterMessageHandler;
import io.scalecube.cluster.membership.MembershipEvent;
import io.scalecube.cluster.transport.api.Message;

import java.util.List;

/**
 * Cluster Message Handler Chain
 *
 * @author linux_china
 */
public class ClusterMessageHandlerChain implements ClusterMessageHandler {
    private List<ClusterMessageHandler> handlers;

    public ClusterMessageHandlerChain(List<ClusterMessageHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void onMessage(Message message) {
        handlers.forEach(handler -> {
            handler.onMessage(message);
        });
    }

    @Override
    public void onGossip(Message gossip) {
        handlers.forEach(handler -> {
            handler.onGossip(gossip);
        });
    }

    @Override
    public void onMembershipEvent(MembershipEvent event) {
        handlers.forEach(handler -> {
            handler.onMembershipEvent(event);
        });
    }

    public boolean isEmpty() {
        return this.handlers == null || handlers.isEmpty();
    }
}
