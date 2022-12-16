package com.example.producer;

import com.example.CarEvent;
import com.example.serdes.CarEventSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class ProducerApplication  implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		String topicName = "car-service-listener";

		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CarEventSerializer.class);

		Producer<String, CarEvent> producer = new KafkaProducer<String, CarEvent>(props);

		CarEvent carEvent = new CarEvent("11AA22", CarEvent.State.RENTED);
		producer.send(new ProducerRecord<String, CarEvent>(topicName, 0, "Office Paris", carEvent));
		System.out.println("CarEvent sent to Office Paris: " + carEvent);

		carEvent = new CarEvent("33BB44", CarEvent.State.RENTED);
		producer.send(new ProducerRecord<String, CarEvent>(topicName, 1, "Office Nice", carEvent));
		System.out.println("CarEvent sent to Office Nice: " + carEvent);

		carEvent = new CarEvent("55CC66", CarEvent.State.RENTED);
		producer.send(new ProducerRecord<String, CarEvent>(topicName, 0, "Office Toulouse", carEvent));
		System.out.println("CarEvent sent to Office Paris: " + carEvent);

		carEvent = new CarEvent("77DD88", CarEvent.State.RENTED);
		producer.send(new ProducerRecord<String, CarEvent>(topicName, 1, "Office Marseille", carEvent));
		System.out.println("CarEvent sent to Office Nice: " + carEvent);

		producer.close();
	}
}
