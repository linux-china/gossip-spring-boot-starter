package org.mvnsearch.boot.gossip;

import io.scalecube.cluster.Cluster;
import io.scalecube.cluster.ClusterImpl;
import io.scalecube.cluster.ClusterMessageHandler;
import io.scalecube.cluster.Member;
import io.scalecube.net.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
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
    private Logger log = LoggerFactory.getLogger(GossipAutoConfiguration.class);
    @Autowired
    private GossipProperties properties;

    @Bean(destroyMethod = "shutdown")
    public Cluster gossipCluster(@Autowired(required = false) ObjectProvider<ClusterMessageHandler> handlers,
                                 @Autowired(required = false) ObjectProvider<GossipClusterCustomizer> customizers) {
        int listenPort = properties.getListen();
        final String host;
        if (properties.getHost() == null || properties.getHost().isEmpty()) {
            host = getLocalIP();
        } else {
            host = properties.getHost();
        }
        ClusterImpl clusterImpl = new ClusterImpl()
                .config(clusterConfig -> clusterConfig.containerHost(host).containerPort(listenPort))
                .membership(membershipConfig -> membershipConfig.seedMembers(seedMembers()).syncInterval(properties.getSyncInterval()))
                .transport(transportConfig -> transportConfig.host(host).port(listenPort));
        if (handlers != null) {
            ClusterMessageHandlerChain chain = new ClusterMessageHandlerChain(handlers.orderedStream().collect(Collectors.toList()));
            if (!chain.isEmpty()) {
                clusterImpl = clusterImpl.handler(cluster1 -> chain);
            }
        }
        for (GossipClusterCustomizer customizer : customizers.orderedStream().collect(Collectors.toList())) {
            clusterImpl = customizer.customize(clusterImpl);
        }
        Cluster cluster = clusterImpl.startAwait();
        Member member = cluster.member();
        log.info("Gossip cluster started with id: " + member.id() + ", host: " + member.address().host() + " and port: " + member.address().port());
        return cluster;
    }

    @Bean
    public GossipEndpoint gossipEndpoint(Cluster cluster) {
        return new GossipEndpoint(cluster);
    }

    private List<Address> seedMembers() {
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
