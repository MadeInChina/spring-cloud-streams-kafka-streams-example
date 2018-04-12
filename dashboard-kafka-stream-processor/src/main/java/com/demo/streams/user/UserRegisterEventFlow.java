package com.demo.streams.user;

import com.demo.streams.data.UserEvent;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueStore;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class UserRegisterEventFlow {

  public KTable<String, Long> process(KStream<String, UserEvent> input) {
    //    return input
    //        .map(((key, value) -> KeyValue.pair(LocalDate.now().toString(), 1L)))
    //        .filter(
    //            (key, msg) -> {
    //              System.out.println("Got a message! key = {}, value = {}" + key + "  " + msg);
    //              return true;
    //            })
    //        .groupByKey(Serialized.with(Serdes.String(), Serdes.Long()))
    //        .reduce((e1, e2) -> e1 + e2);

    return input
        .map(((key, value) -> KeyValue.pair(LocalDate.now().toString(), 0L)))
        .filter(
            (key, msg) -> {
              System.out.println("Got a message! key = {}, value = {}" + key + "  " + msg);
              return true;
            })
        .groupByKey()
        .count(
            Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as(
                    UserRegisterAnalyticsBinding.USER_REGISTER_COUNT_MV)
                .withKeySerde(Serdes.String())
                .withValueSerde(Serdes.Long()));
  }
}
