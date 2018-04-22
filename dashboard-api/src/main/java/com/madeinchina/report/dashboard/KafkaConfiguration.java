package com.madeinchina.report.dashboard;

import com.madeinchina.report.dashboard.vo.PeriodGameTransaction;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@EnableKafka
public class KafkaConfiguration {

  @Value(value = "${kafka.bootstrap-servers}")
  private String bootstrapAddress;

  @Value(value = "${kafka.consumers.user.group-id}")
  private String userGroupId;

  @Value(value = "${kafka.consumers.game-transaction-10-mins.group-id}")
  private String gtGroupId;

  @Bean
  public ConsumerFactory<String, Long> userConsumerLongFactory() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, userGroupId);
    return new DefaultKafkaConsumerFactory(props, new StringDeserializer(), new LongDeserializer());
  }

  @Bean
  public ConsumerFactory<Long, PeriodGameTransaction> gameTransactionConsumerDoubleFactory() {
    Map<String, Object> props = new HashMap<>(2);
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, gtGroupId);
    return new DefaultKafkaConsumerFactory(
        props, new LongDeserializer(), new PeriodGameTransactionJsonDeserializer());
  }

  @Bean
  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Long>>
      userKafkaListenerContainerFactory() {

    ConcurrentKafkaListenerContainerFactory<String, Long> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(userConsumerLongFactory());
    return factory;
  }

  @Bean
  public KafkaListenerContainerFactory<
          ConcurrentMessageListenerContainer<Long, PeriodGameTransaction>>
      gameTransactionKafkaListenerContainerFactory() {

    ConcurrentKafkaListenerContainerFactory<Long, PeriodGameTransaction> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(gameTransactionConsumerDoubleFactory());
    return factory;
  }

  public class PeriodGameTransactionJsonDeserializer
      extends JsonDeserializer<PeriodGameTransaction> {}
}
