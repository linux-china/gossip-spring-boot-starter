package org.mvnsearch.boot.gossip;

import org.junit.jupiter.api.Test;

import static org.mvnsearch.boot.gossip.GossipAutoConfiguration.getLocalIP;

/**
 * Network test
 *
 * @author linux_china
 */
public class NetworkTest {

    @Test
    public void testPrintIp() {
        System.out.println(getLocalIP());
    }
}
