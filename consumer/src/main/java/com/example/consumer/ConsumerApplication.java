package com.example.consumer;

import com.example.CarEvent;
import com.example.serdes.CarEventDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

@SpringBootApplication
public class ConsumerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String bootstrapServers = "localhost:9092";
		String grp_id="third_app";

		Properties properties = new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CarEventDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG,grp_id);
		properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		//creating consumer
		KafkaConsumer<String, CarEvent> consumer = new KafkaConsumer<String, CarEvent>(properties);
		//Subscribing
		String topic = "car-service-listener";
		consumer.subscribe(Arrays.asList(topic));

		while (true) {
			ConsumerRecords<String, CarEvent> records = consumer.poll(Duration.ofMillis(100));
			for (ConsumerRecord<String, CarEvent> record : records) {
				System.out.println("Key: " + record.key() + ", Value:" + record.value());
				System.out.println("Partition:" + record.partition() + ",Offset:" + record.offset());
			}
		}
	}
}
