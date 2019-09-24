gossip-spring-boot-starter
==========================
Gossip & SWIM starter for Spring Boot App withe ScaleCube Cluster support.

# How to use?

* Edit application pom.xml and add following dependency:

```xml
<dependency>
    <groupId>org.mvnsearch.boot</groupId>
    <artifactId>gossip-spring-boot-starter</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>    
```

* Open application.properties and add gossip.seeds configuration.

```
gossip.seeds=seed_1_ip:7946,seed_2_ip:7946
```

### Gossip Spring Boot Actuator

* /actuator/gossip: display Gossip Cluster info
* /actuator/gossip/shutdown: leave from cluster
* /actuator/gossip/event: gossip an event to cluster.

```HTTP REQUEST
POST http://localhost:8080/actuator/gossip/event
Content-Type: application/vnd.spring-boot.actuator.v2+json

{
  "type": "com.foobar.user.LoginEvent",
  "source": "source-1",
  "datacontenttype": "text/plain",
  "data": "uid:1234"
}
```

# References

* SWIM: http://www.cs.cornell.edu/projects/Quicksilver/public_pdfs/SWIM.pdf
* ScaleCube Cluster: https://github.com/scalecube/scalecube-cluster
* Serf: https://www.serf.io/
