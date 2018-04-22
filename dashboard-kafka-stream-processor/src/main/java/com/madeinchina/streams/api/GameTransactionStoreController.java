package com.madeinchina.streams.api;

import com.madeinchina.streams.game.GameTransactionAnalyticsBinding;
import com.madeinchina.streams.game.PeriodGameTransaction;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.WindowStoreIterator;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.QueryableStoreRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/game/transaction/store/")
public class GameTransactionStoreController {
  @Autowired private QueryableStoreRegistry queryableStoreRegistry;
  DateTimeFormatter formatter = DateTimeFormat.forPattern("MM-dd-yyyy-HH:mm");

  @GetMapping(path = "/{key}", produces = "application/json")
  public List games(@PathVariable String key) {
    List<PeriodGameTransaction> list = new ArrayList<>();
    WindowStoreIterator<PeriodGameTransaction> iterator =
        queryableStoreRegistry
            .getQueryableStoreType(
                GameTransactionAnalyticsBinding.GAME_TRANSACTION_1_MINS_WINDOW_MV,
                QueryableStoreTypes.<String, PeriodGameTransaction>windowStore())
            .fetch(key, 0, System.currentTimeMillis());
    while (iterator.hasNext()) {
      KeyValue<Long, PeriodGameTransaction> next = iterator.next();
      long windowTimestamp = next.key;
      PeriodGameTransaction periodGameTransaction = next.value;
      periodGameTransaction.setKey(new Date(windowTimestamp).getTime());
      log.info(
          "'game transaction' @ time "
              + LocalDateTime.fromDateFields(new Date(windowTimestamp)).toString(formatter)
              + " CashBet is "
              + periodGameTransaction);
      list.add(periodGameTransaction);
    }
    return list;
  }
}
