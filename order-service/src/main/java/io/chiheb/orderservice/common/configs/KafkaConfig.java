package io.chiheb.orderservice.common.configs;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.TopicConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {
  public static final String WAREHOUSE_STOCK_RESERVE_TOPIC = "warehouse.stock.reserve";
  public static final String WAREHOUSE_STOCK_RELEASE_TOPIC = "warehouse.stock.release";
  public static final String WAREHOUSE_SHIPMENT_DISPATCH_TOPIC = "warehouse.shipment.dispatch";
  public static final String ORDER_STOCK_CONFIRM_TOPIC = "order.stock.confirm";
  public static final String ORDER_STOCK_REJECT_TOPIC = "order.stock.reject";

  public static final String FINANCE_PAYMENT_PROCESS_TOPIC = "finance.payment.process";
  public static final String ORDER_PAYMENT_CONFIRM_TOPIC = "order.payment.confirm";
  public static final String ORDER_PAYMENT_REJECT_TOPIC = "order.payment.reject";

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Bean
  public KafkaAdmin admin() {
    return new KafkaAdmin(Map.of(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers));
  }

  @Bean
  public NewTopic stockConfirmTopic() {
    return TopicBuilder.name(ORDER_STOCK_CONFIRM_TOPIC)
        .partitions(10)
        .replicas(3)
        .config(TopicConfig.COMPRESSION_TYPE_CONFIG, "zstd")
        .build();
  }

  @Bean
  public NewTopic stockRejectTopic() {
    return TopicBuilder.name(ORDER_STOCK_REJECT_TOPIC)
        .partitions(10)
        .replicas(3)
        .config(TopicConfig.COMPRESSION_TYPE_CONFIG, "zstd")
        .build();
  }

  @Bean
  public NewTopic paymentConfirmTopic() {
    return TopicBuilder.name(ORDER_PAYMENT_CONFIRM_TOPIC)
        .partitions(10)
        .replicas(3)
        .config(TopicConfig.COMPRESSION_TYPE_CONFIG, "zstd")
        .build();
  }

  @Bean
  public NewTopic paymentRejectTopic() {
    return TopicBuilder.name(ORDER_PAYMENT_REJECT_TOPIC)
        .partitions(10)
        .replicas(3)
        .config(TopicConfig.COMPRESSION_TYPE_CONFIG, "zstd")
        .build();
  }

  @Bean
  public ConsumerFactory<String, Object> consumerFactory() {
    JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>();
    jsonDeserializer.addTrustedPackages("*");
    return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(), jsonDeserializer);
  }

  @Bean
  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    factory.setConcurrency(3);
    factory.getContainerProperties().setPollTimeout(3000);
    return factory;
  }

  @Bean
  public Map<String, Object> consumerConfigs() {
    return Map.of(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class,
        ConsumerConfig.GROUP_ID_CONFIG, "json",
        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"
    );
  }

  @Bean
  public ProducerFactory<String, Object> producerFactory() {
    return new DefaultKafkaProducerFactory<>(producerConfigs());
  }

  @Bean
  public Map<String, Object> producerConfigs() {
    return Map.of(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class
    );
  }

  @Bean
  public KafkaTemplate<String, Object> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }
}