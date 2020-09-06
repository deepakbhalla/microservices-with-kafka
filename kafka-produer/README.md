# Kafka Producer

A producer is an entity/application that publishes data to a Kafka cluster, which is made up of brokers. A broker is responsible for receiving and storing the data when a producer publishes. A consumer then consumes data from a broker at a specified offset, i.e. position.

This application demonstrates how a 'Kafka Producer' can be implemented with spring boot. You can refer to the screenshots to follow each step.

## Steps to be followed

* Start Zookeeper (default port is 2181)

	.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties


* Kafka broker/server (default port is 9092)

	.\bin\windows\kafka-server-start.bat .\config\server.properties


* Create Topic with 1 Partition and 1 Replica

	.\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic HelloWorldTopic


* Start Consumer

	.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --from-beginning --topic HelloWorldTopic –partition 0

* Run Spring Boot application

	Run 'com.example.kafka.KafkaProducerApplication.java' as Java Application

## Few Results

- Rest API sent a message to Kafka Consumer

!(screenshots/rest_api_sending_messages_using_kafka_producer.png)

- Kafka consumer received message sent by Kafka producer

!(screenshots/kafka_consumer_cli_received_message_sent_by_producer.png)

