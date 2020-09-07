package com.homeadvisor.kafdrop.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;


public class AvroMessageDeserializer implements MessageDeserializer {

   private String topicName;
   private String schemaRegistryUrl;

   public AvroMessageDeserializer(String topicName, String schemaRegistryUrl) {
      this.topicName = topicName;
      this.schemaRegistryUrl = schemaRegistryUrl;
   }

   @Override
   public String deserializeMessage(ByteBuffer buffer) {
      KafkaAvroDeserializer deserializer = getDeserializer();

      // Convert byte buffer to byte array
      byte[] bytes = ByteUtils.convertToByteArray(buffer);

      return formatJsonMessage(deserializer.deserialize(topicName, bytes).toString());
   }

   private String formatJsonMessage(String jsonMessage) {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      JsonParser parser = new JsonParser();
      JsonElement element = parser.parse(jsonMessage);
      String formattedJsonMessage = gson.toJson(element);
      return formattedJsonMessage;
   }

   private KafkaAvroDeserializer getDeserializer() {
      Map<String, Object> config = new HashMap<>();
      config.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);
      KafkaAvroDeserializer kafkaAvroDeserializer = new KafkaAvroDeserializer();
      kafkaAvroDeserializer.configure(config, false);
      return kafkaAvroDeserializer;
   }

}
