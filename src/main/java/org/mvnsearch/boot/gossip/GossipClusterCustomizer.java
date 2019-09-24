package org.mvnsearch.boot.gossip;

import io.scalecube.cluster.ClusterImpl;

/**
 * Gossip Cluster customizer
 *
 * @author linux_china
 */
@FunctionalInterface
public interface GossipClusterCustomizer {
    
    ClusterImpl customize(ClusterImpl cluster);

}
