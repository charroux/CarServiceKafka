# CarServiceKafka

## Installation

Download Kafka 2.13.* from: https://kafka.apache.org/

tar -xzf kafka_2.13.*.tgz

Change directory to kafka

## Start Zookeeper

ZooKeeper allows distributed processes to coordinate with each other through a shared hierarchical name space of data registers (we call these registers znodes), much like a file system. Unlike normal file systems ZooKeeper provides its clients with high throughput, low latency, highly available, strictly ordered access to the znodes. The performance aspects of ZooKeeper allow it to be used in large distributed systems. The reliability aspects prevent it from becoming the single point of failure in big systems. Its strict ordering allows sophisticated synchronization primitives to be implemented at the client.

bin/zookeeper-server-start.sh config/zookeeper.properties

Ctr-c to stop

## Start Kafka broker

bin/kafka-server-start.sh config/server.properties

Ctr-c to stop

If the broker hasn't been stoped properly stop it with: bin/kafka-server-stop.sh Then restart it.

## Create a topic for producing messages with 2 partitions

bin/kafka-topics.sh --create --topic car-service --partitions 2 --bootstrap-server localhost:9092

## Create a topic for consuming messages with 2 partitions

bin/kafka-topics.sh --create --topic car-service-listener --partitions 2 --bootstrap-server localhost:9092

## The producer https://github.com/charroux/CarServiceKafka/tree/main/producer

### Event definition

https://github.com/charroux/CarServiceKafka/blob/main/producer/src/main/java/com/example/CarEvent.java

### Event sedializer / deserialiser

https://github.com/charroux/CarServiceKafka/tree/main/producer/src/main/java/com/example/serdes

### Partitioner according to the office name

https://github.com/charroux/CarServiceKafka/blob/main/producer/src/main/java/com/example/partitioner/OfficePartitioner.java

### Sending event

https://github.com/charroux/CarServiceKafka/blob/main/producer/src/main/java/com/example/producer/ProducerApplication.java

## Streaming events https://github.com/charroux/CarServiceKafka/tree/main/stream

### Event serializer / deserializer

https://github.com/charroux/CarServiceKafka/tree/main/stream/src/main/java/com/example/serdes

### Partitioner according to the office name

https://github.com/charroux/CarServiceKafka/blob/main/stream/src/main/java/com/example/partitioner/OfficePartitioner.java

### Streaming 

https://github.com/charroux/CarServiceKafka/blob/main/stream/src/main/java/com/example/stream/StreamApplication.java

## Consumer: https://github.com/charroux/CarServiceKafka/tree/main/consumer

### Event serializer / deserialiser

https://github.com/charroux/CarServiceKafka/tree/main/consumer/src/main/java/com/example/serdes

### Consumer

https://github.com/charroux/CarServiceKafka/blob/main/consumer/src/main/java/com/example/consumer/ConsumerApplication.java




