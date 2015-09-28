# DSE Java

This repository is purely for holding any examples, tests and other bits of java code related to working with the Datastax java driver and Cassandra

## Building a very basic example

Theres a lot of examples for building with Maven but I'll just give a basic example here using command line and all local jar files:

Here's the files I used (I probably didn't need all the netty files)

```
cassandra-driver-core-2.1.6.jar
guava-14.0.1.jar
metrics-core-3.0.2.jar
netty-buffer-4.0.27.Final.jar
netty-codec-4.0.27.Final.jar
netty-codec-http-4.0.27.Final.jar
netty-codec-socks-4.0.27.Final.jar
netty-common-4.0.27.Final.jar
netty-example-4.0.27.Final.jar
netty-handler-4.0.27.Final.jar
netty-transport-4.0.27.Final.jar
netty-transport-rxtx-4.0.27.Final.jar
netty-transport-sctp-4.0.27.Final.jar
netty-transport-udt-4.0.27.Final.jar
slf4j-api-1.7.12.jar
```

### Compiling

```
javac -cp ./*:. DSEtest.java
```

### Running

```
java -cp ./*:. DSEtest 192.168.56.23
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
Connected to cluster: MarkieCluster
Datacenter: Cassandra; Host: /192.168.56.22; Rack: rack1
Datacenter: Cassandra; Host: /192.168.56.23; Rack: rack1
Datacenter: Cassandra; Host: /192.168.56.20; Rack: rack1
Datacenter: Cassandra; Host: /192.168.56.21; Rack: rack1
Schema agrement: true
Keyspaces: [CREATE KEYSPACE system_traces WITH REPLICATION = { 'class' : 'org.apache.cassandra.locator.SimpleStrategy', 'replication_factor': '2' } AND DURABLE_WRITES = true;, CREATE KEYSPACE system WITH REPLICATION = { 'class' : 'org.apache.cassandra.locator.LocalStrategy' } AND DURABLE_WRITES = true;, CREATE KEYSPACE test1 WITH REPLICATION = { 'class' : 'org.apache.cassandra.locator.SimpleStrategy', 'replication_factor': '1' } AND DURABLE_WRITES = true;, CREATE KEYSPACE "OpsCenter" WITH REPLICATION = { 'class' : 'org.apache.cassandra.locator.SimpleStrategy', 'replication_factor': '2' } AND DURABLE_WRITES = true;, CREATE KEYSPACE dse_perf WITH REPLICATION = { 'class' : 'org.apache.cassandra.locator.SimpleStrategy', 'replication_factor': '1' } AND DURABLE_WRITES = true;, CREATE KEYSPACE markc WITH REPLICATION = { 'class' : 'org.apache.cassandra.locator.SimpleStrategy', 'replication_factor': '3' } AND DURABLE_WRITES = true;, CREATE KEYSPACE dse_system WITH REPLICATION = { 'class' : 'org.apache.cassandra.locator.EverywhereStrategy' } AND DURABLE_WRITES = true;]
```


