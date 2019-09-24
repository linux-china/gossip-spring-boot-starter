package org.mvnsearch.boot.gossip;

/**
 * gossip event
 *
 * @author linux_china
 */
public class GossipEvent {
    private String type;
    private String data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
