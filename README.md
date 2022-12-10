# CarServiceKafka

# Installation

Download Kafka 2.13.* from: https://kafka.apache.org/

tar -xzf kafka_2.13.*.tgz

Change deirectory to kafka

## Create a topic

bin/kafka-topics.sh --create --topic car-service --bootstrap-server localhost:9092
