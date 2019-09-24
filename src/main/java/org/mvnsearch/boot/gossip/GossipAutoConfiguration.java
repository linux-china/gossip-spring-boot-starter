package org.mvnsearch.boot.gossip;

import io.scalecube.cluster.Cluster;
import io.scalecube.cluster.ClusterImpl;
import io.scalecube.cluster.ClusterMessageHandler;
import io.scalecube.net.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Gossip auto configuration
 *
 * @author linux_china
 */
@Configuration
@EnableConfigurationProperties(GossipProperties.class)
public class GossipAutoConfiguration {
    @Autowired
    private GossipProperties properties;

    @Bean
    public Cluster gossipCluster(@Autowired ClusterMessageHandler handler) {
        int listenPort = properties.getListen();
        final String host;
        if (properties.getHost() == null || properties.getHost().isEmpty()) {
            host = getLocalIP();
        } else {
            host = properties.getHost();
        }
        return new ClusterImpl()
                .config(clusterConfig -> clusterConfig.memberHost(host).memberPort(listenPort))
                .membership(membershipConfig -> membershipConfig.seedMembers(seedMembers()).syncInterval(properties.getSyncInterval()))
                .transport(transportConfig -> transportConfig.host(host).port(listenPort))
                .handler(cluster1 -> handler)
                .startAwait();
    }

    public List<Address> seedMembers() {
        return properties.getSeeds().stream().map(address -> {
            String[] parts = address.split(":");
            return Address.create(parts[0], Integer.parseInt(parts[1]));
        }).collect(Collectors.toList());
    }


    public static String getLocalIP() {
        String ip = null;
        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                Enumeration ee = n.getInetAddresses();
                while (ee.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress) ee.nextElement();
                    String hostAddress = inetAddress.getHostAddress();
                    if (hostAddress.contains(".") && !inetAddress.isLoopbackAddress()) {
                        ip = hostAddress;
                        break;
                    }
                }
            }
            if (ip == null) {
                ip = InetAddress.getLocalHost().getHostAddress();
            }
        } catch (Exception ignore) {

        }
        return ip;
    }
}
