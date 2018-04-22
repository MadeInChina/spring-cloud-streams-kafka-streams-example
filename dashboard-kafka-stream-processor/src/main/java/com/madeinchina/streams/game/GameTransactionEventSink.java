package com.madeinchina.streams.game;

import com.madeinchina.streams.data.GameTransactionEvent;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;

@EnableBinding(GameTransactionAnalyticsBinding.class)
public class GameTransactionEventSink {
  @Autowired private GameTransactionEventFlow gameTransactionEventFlow;

  //  WindowedSerializer<String> windowedSerializer = new
  // WindowedSerializer<>(Serdes.String().serializer());
  //  WindowedDeserializer<String> windowedDeserializer = new
  // WindowedDeserializer<>(Serdes.String().deserializer(), TimeUnit.MINUTES.toMinutes(10));
  //  //            WindowedDeserializer<String> windowedDeserializer = new
  // WindowedDeserializer<>(Serdes.String().deserializer());
  //  Serde<Windowed<String>> windowedSerde = Serdes.serdeFrom(windowedSerializer,
  // windowedDeserializer);

  @StreamListener(GameTransactionAnalyticsBinding.GAME_TRANSACTION_IN)
  @SendTo(GameTransactionAnalyticsBinding.GAME_TRANSACTION_OUT)
  public KStream<Long, PeriodGameTransaction> process(KStream<String, GameTransactionEvent> input) {
    return gameTransactionEventFlow
        .process(input)
        .toStream()
        .map((windowed, value) -> new KeyValue<>(windowed.window().start(), value));
  }
}
