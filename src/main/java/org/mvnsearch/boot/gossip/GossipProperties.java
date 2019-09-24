package org.mvnsearch.boot.gossip;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * gossip properties
 *
 * @author linux_china
 */
@ConfigurationProperties(prefix = "gossip")
public class GossipProperties {
    /**
     * listen port
     */
    private Integer listen = 7946;
    /**
     * listen host, default is  local ip
     */
    private String host;
    /**
     * cluster seeds
     */
    private List<String> seeds;
    /**
     * membership sync interval(ms)
     */
    private int syncInterval = 5_000;

    public Integer getListen() {
        return listen;
    }

    public void setListen(Integer listen) {
        this.listen = listen;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public List<String> getSeeds() {
        return seeds;
    }

    public void setSeeds(List<String> seeds) {
        this.seeds = seeds;
    }

    public int getSyncInterval() {
        return syncInterval;
    }

    public void setSyncInterval(int syncInterval) {
        this.syncInterval = syncInterval;
    }
}
