package com.madeinchina.streams.user;

import com.madeinchina.streams.data.UserEvent;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
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
        .map(
            ((key, value) -> {
              System.out.println("value :" + value);
              return KeyValue.pair(LocalDate.now().toString(), 0L);
            }))
        .filter(
            (key, msg) -> {
              System.out.println("Got a message! key = {}, value = {}" + key + "  " + msg);
              return true;
            })
        .groupByKey()
        .count(Materialized.as(UserRegisterAnalyticsBinding.USER_REGISTER_COUNT_MV));
  }
}
