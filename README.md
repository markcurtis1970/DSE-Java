# DSE Java

This repository is purely for holding any examples, tests and other bits of java code related to working with the Datastax java driver and Cassandra

## Building a very basic example

Theres a lot of examples for building with Maven but I'll just give a basic example here using command line and all local jar files:

Here's the files I used (I probably didn't need all the netty files), Also please use the **src/DSEtest.java** from the above src directory

```
cassandra-driver-core-2.1.6.jar
guava-14.0.1.jar
log4j-1.2.17.jar
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
slf4j-api-1.7.9.jar
slf4j-log4j12-1.7.9.jar
```

### Compiling

```
javac -cp ./*:. DSEtest.java
```

### Running

```
$ java -cp ./*:. DSEtest 192.168.56.21
 INFO 17:25:01,490 Did not find Netty's native epoll transport in the classpath, defaulting to NIO.
 INFO 17:25:02,051 Using data-center name 'Cassandra' for DCAwareRoundRobinPolicy (if this is incorrect, please provide the correct datacenter name with DCAwareRoundRobinPolicy constructor)
 INFO 17:25:02,054 New Cassandra host /192.168.56.22:9042 added
 INFO 17:25:02,054 New Cassandra host /192.168.56.20:9042 added
 INFO 17:25:02,055 New Cassandra host /192.168.56.23:9042 added
 INFO 17:25:02,055 New Cassandra host /192.168.56.21:9042 added
Connected to cluster: MarkieCluster
Datacenter: Cassandra; Host: /192.168.56.22; Rack: rack1
Datacenter: Cassandra; Host: /192.168.56.23; Rack: rack1
Datacenter: Cassandra; Host: /192.168.56.20; Rack: rack1
Datacenter: Cassandra; Host: /192.168.56.21; Rack: rack1
Schema agrement: true
Keyspaces: [CREATE KEYSPACE system_traces WITH REPLICATION = { 'class' : 'org.apache.cassandra.locator.SimpleStrategy', 'replication_factor': '2' } AND DURABLE_WRITES = true;, CREATE KEYSPACE system WITH REPLICATION = { 'class' : 'org.apache.cassandra.locator.LocalStrategy' } AND DURABLE_WRITES = true;, CREATE KEYSPACE test1 WITH REPLICATION = { 'class' : 'org.apache.cassandra.locator.SimpleStrategy', 'replication_factor': '1' } AND DURABLE_WRITES = true;, CREATE KEYSPACE "OpsCenter" WITH REPLICATION = { 'class' : 'org.apache.cassandra.locator.SimpleStrategy', 'replication_factor': '2' } AND DURABLE_WRITES = true;, CREATE KEYSPACE dse_perf WITH REPLICATION = { 'class' : 'org.apache.cassandra.locator.SimpleStrategy', 'replication_factor': '1' } AND DURABLE_WRITES = true;, CREATE KEYSPACE markc WITH REPLICATION = { 'class' : 'org.apache.cassandra.locator.SimpleStrategy', 'replication_factor': '3' } AND DURABLE_WRITES = true;, CREATE KEYSPACE dse_system WITH REPLICATION = { 'class' : 'org.apache.cassandra.locator.EverywhereStrategy' } AND DURABLE_WRITES = true;]
Homer:t19792 mark$ vi log4j.properties
Homer:t19792 mark$ java -cp ./*:. DSEtest 192.168.56.21
DEBUG 17:25:23,930 Using SLF4J as the default logging framework
DEBUG 17:25:23,939 java.nio.Buffer.address: available
DEBUG 17:25:23,941 sun.misc.Unsafe.theUnsafe: available
DEBUG 17:25:23,941 sun.misc.Unsafe.copyMemory: available
DEBUG 17:25:23,942 java.nio.Bits.unaligned: true
DEBUG 17:25:23,943 Java version: 7
DEBUG 17:25:23,943 -Dio.netty.noUnsafe: false
DEBUG 17:25:23,943 sun.misc.Unsafe: available
DEBUG 17:25:23,943 -Dio.netty.noJavassist: false
DEBUG 17:25:23,944 Javassist: unavailable
DEBUG 17:25:23,944 You don't have Javassist in your class path or you don't have enough permission to load dynamically generated classes.  Please check the configuration for better performance.
DEBUG 17:25:23,945 -Dio.netty.tmpdir: /var/folders/j7/18sd3dzn21j8jmp8fq1bvm5r0000gn/T (java.io.tmpdir)
DEBUG 17:25:23,945 -Dio.netty.bitMode: 64 (sun.arch.data.model)
DEBUG 17:25:23,945 -Dio.netty.noPreferDirect: false
 INFO 17:25:23,959 Did not find Netty's native epoll transport in the classpath, defaulting to NIO.
DEBUG 17:25:23,961 -Dio.netty.eventLoopThreads: 16
DEBUG 17:25:23,968 -Dio.netty.noKeySetOptimization: false
DEBUG 17:25:23,968 -Dio.netty.selectorAutoRebuildThreshold: 512
DEBUG 17:25:23,981 -Dio.netty.leakDetectionLevel: simple
DEBUG 17:25:24,081 -Dio.netty.allocator.numHeapArenas: 8
DEBUG 17:25:24,081 -Dio.netty.allocator.numDirectArenas: 8
DEBUG 17:25:24,081 -Dio.netty.allocator.pageSize: 8192
DEBUG 17:25:24,082 -Dio.netty.allocator.maxOrder: 11
DEBUG 17:25:24,082 -Dio.netty.allocator.chunkSize: 16777216
DEBUG 17:25:24,082 -Dio.netty.allocator.tinyCacheSize: 512
DEBUG 17:25:24,082 -Dio.netty.allocator.smallCacheSize: 256
DEBUG 17:25:24,082 -Dio.netty.allocator.normalCacheSize: 64
DEBUG 17:25:24,082 -Dio.netty.allocator.maxCachedBufferCapacity: 32768
DEBUG 17:25:24,083 -Dio.netty.allocator.cacheTrimInterval: 8192
DEBUG 17:25:24,107 -Dio.netty.initialSeedUniquifier: 0xd45ed5094a477a66 (took 6 ms)
DEBUG 17:25:24,121 -Dio.netty.allocator.type: unpooled
DEBUG 17:25:24,121 -Dio.netty.threadLocalDirectBufferSize: 65536
DEBUG 17:25:24,166 -Dio.netty.recycler.maxCapacity.default: 262144
 INFO 17:25:24,390 Using data-center name 'Cassandra' for DCAwareRoundRobinPolicy (if this is incorrect, please provide the correct datacenter name with DCAwareRoundRobinPolicy constructor)
 INFO 17:25:24,390 New Cassandra host /192.168.56.22:9042 added
 INFO 17:25:24,390 New Cassandra host /192.168.56.20:9042 added
 INFO 17:25:24,390 New Cassandra host /192.168.56.23:9042 added
 INFO 17:25:24,390 New Cassandra host /192.168.56.21:9042 added
Connected to cluster: MarkieCluster
Datacenter: Cassandra; Host: /192.168.56.22; Rack: rack1
Datacenter: Cassandra; Host: /192.168.56.23; Rack: rack1
Datacenter: Cassandra; Host: /192.168.56.20; Rack: rack1
Datacenter: Cassandra; Host: /192.168.56.21; Rack: rack1
Schema agrement: true
Keyspaces: [CREATE KEYSPACE system_traces WITH REPLICATION = { 'class' : 'org.apache.cassandra.locator.SimpleStrategy', 'replication_factor': '2' } AND DURABLE_WRITES = true;, CREATE KEYSPACE system WITH REPLICATION = { 'class' : 'org.apache.cassandra.locator.LocalStrategy' } AND DURABLE_WRITES = true;, CREATE KEYSPACE test1 WITH REPLICATION = { 'class' : 'org.apache.cassandra.locator.SimpleStrategy', 'replication_factor': '1' } AND DURABLE_WRITES = true;, CREATE KEYSPACE "OpsCenter" WITH REPLICATION = { 'class' : 'org.apache.cassandra.locator.SimpleStrategy', 'replication_factor': '2' } AND DURABLE_WRITES = true;, CREATE KEYSPACE dse_perf WITH REPLICATION = { 'class' : 'org.apache.cassandra.locator.SimpleStrategy', 'replication_factor': '1' } AND DURABLE_WRITES = true;, CREATE KEYSPACE markc WITH REPLICATION = { 'class' : 'org.apache.cassandra.locator.SimpleStrategy', 'replication_factor': '3' } AND DURABLE_WRITES = true;, CREATE KEYSPACE dse_system WITH REPLICATION = { 'class' : 'org.apache.cassandra.locator.EverywhereStrategy' } AND DURABLE_WRITES = true;]
```


