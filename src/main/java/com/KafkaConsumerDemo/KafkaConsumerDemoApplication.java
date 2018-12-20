package com.KafkaConsumerDemo;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Properties;

@SpringBootApplication
	public class KafkaConsumerDemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KafkaConsumerDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Properties props = new Properties();
		String topicName = "my-event-stream";

		props.put("bootstrap.servers", "localhost:9092");
		props.put("group.id", "test");
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);

		consumer.subscribe(Arrays.asList(topicName));

		System.out.println("Subscribed to topic " + topicName);


		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(100);
			for (ConsumerRecord<String, String> record : records) {
				System.out.println(record.value());
			}
		}
	}
}

