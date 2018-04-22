package com.madeinchina.streams.game;

import com.madeinchina.streams.data.GameTransaction;
import com.madeinchina.streams.data.GameTransactionEvent;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Serialized;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.state.WindowStore;
import org.joda.time.LocalDate;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class GameTransactionEventFlow {

  public KTable<Windowed<String>, PeriodGameTransaction> process(
      KStream<String, GameTransactionEvent> input) {
    return input
        .map(
            ((key, value) -> {
              String newKey = LocalDate.now().toString();
              log.info("key {},new key {} ,value {}", key, newKey, value);
              GameTransaction gameTransaction = value.getGameTransaction();
              PeriodGameTransaction periodGameTransaction = transform(gameTransaction);
              return KeyValue.pair(newKey, periodGameTransaction);
            }))
        .filter(
            (key, msg) -> {
              System.out.println("Got a message! key = {}, value = {}" + key + "  " + msg);
              return true;
            })
        .groupByKey(Serialized.with(Serdes.String(), new JsonSerde<>(PeriodGameTransaction.class)))
        .windowedBy(TimeWindows.of(TimeUnit.MINUTES.toMillis(1)))
        .reduce(
            (v1, v2) -> {
              v1.setCashBet(v1.getCashBet() + v2.getCashBet());
              v1.setCashGgr(v1.getCashGgr() + v2.getCashGgr());
              v1.setPromoBet(v1.getPromoBet() + v2.getPromoBet());
              v1.setPromoGGR(v1.getPromoGGR() + v2.getPromoGGR());
              v1.setStartInSeconds(v2.getStartInSeconds());
              return v1;
            },
            Materialized.<String, PeriodGameTransaction, WindowStore<Bytes, byte[]>>as(
                    GameTransactionAnalyticsBinding.GAME_TRANSACTION_1_MINS_WINDOW_MV)
                .withKeySerde(Serdes.String())
                .withValueSerde(new JsonSerde<>(PeriodGameTransaction.class)));
  }

  private PeriodGameTransaction transform(GameTransaction gameTransaction) {
    Instant instant = Instant.now().truncatedTo(ChronoUnit.SECONDS);
    Long startInSeconds = instant.getEpochSecond() * 1000;
    PeriodGameTransaction periodGameTransaction = new PeriodGameTransaction();
    periodGameTransaction.setStartInSeconds(startInSeconds);
    periodGameTransaction.setPromoBet(gameTransaction.getPromoBet());
    periodGameTransaction.setPromoGGR(gameTransaction.getPromoGGR());
    periodGameTransaction.setCashBet(gameTransaction.getCashBet());
    periodGameTransaction.setCashGgr(gameTransaction.getCashGgr());
    return periodGameTransaction;
  }
}
