package com.madeinchina.streams.game;

import com.madeinchina.streams.data.GameTransactionEvent;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;

public interface GameTransactionAnalyticsBinding {
  String GAME_TRANSACTION_1_MINS_WINDOW_MV = "gtmv";
  String GAME_TRANSACTION_IN = "gtin";
  String GAME_TRANSACTION_OUT = "gtot";

  @Input(GAME_TRANSACTION_IN)
  KStream<String, GameTransactionEvent> gameTransactionIn();

  @Output(GAME_TRANSACTION_OUT)
  KStream<String, Double> gameTransactionOut();
}
