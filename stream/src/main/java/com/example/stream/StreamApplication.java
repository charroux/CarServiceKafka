package com.example.stream;

import com.example.CarEvent;
import com.example.serdes.CarEventDeserializer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication
public class StreamApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(StreamApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-pipe");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, new CarEventDeserializer().getClass());

		System.out.println(props);

		final StreamsBuilder builder = new StreamsBuilder();

		KStream<String, CarEvent> source = builder.stream("car-service");
		source
				.filter((key, value) -> value.getState() == CarEvent.State.RENTED)
						.to("car-service-listener");

		final Topology topology = builder.build();

		System.out.println(topology.describe());

		final KafkaStreams streams = new KafkaStreams(topology, props);
		final CountDownLatch latch = new CountDownLatch(1);

		// attach shutdown handler to catch control-c
		Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
			@Override
			public void run() {
				streams.close();
				latch.countDown();
			}
		});

		try {
			streams.start();
			latch.await();
		} catch (Throwable e) {
			System.exit(1);
		}
		System.exit(0);

	}

}