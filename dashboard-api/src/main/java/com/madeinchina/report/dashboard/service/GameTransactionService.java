package com.madeinchina.report.dashboard.service;

import com.madeinchina.report.dashboard.dao.PeriodGameTransactionRepository;
import com.madeinchina.report.dashboard.dao.entity.GameTransactionByTime;
import com.madeinchina.report.dashboard.vo.GameTransactionByTimeResponse;
import com.madeinchina.report.dashboard.vo.PeriodGameTransaction;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

/**
 * Service class to send traffic data messages to dashboard ui at fixed interval using web-socket.
 *
 * @author abaghel
 */
@Service
@Slf4j
@Transactional
public class GameTransactionService {
  private static String DESTINATION = "/topic/game/transaction";

  @Autowired private SimpMessagingTemplate template;

  @Autowired private PeriodGameTransactionRepository periodGameTransactionRepository;
  private long instantEpochSecond;

  public Mono<GameTransactionByTime> findByToday() {
    Instant instant = Instant.now().truncatedTo(ChronoUnit.MINUTES);
    instantEpochSecond = instant.getEpochSecond() * 1000;
    log.info("date:{}", instantEpochSecond);
    return Mono.just(
        periodGameTransactionRepository
            .findByStartInMinsOrderByStartInSecondsDesc(instantEpochSecond)
            .findFirst()
            .orElse(
                new GameTransactionByTime(
                    instantEpochSecond, instantEpochSecond, 0.0, 0.0, 0.0, 0.0)));
  }

  @Scheduled(fixedRate = 5000)
  public void trigger() {
    log.info("Scheduled...start");
    // prepare response
    findByToday()
        .subscribe(
            gameTransactionByTime ->
                Mono.fromFuture(
                    CompletableFuture.runAsync(
                        () -> {
                          GameTransactionByTimeResponse response =
                              new GameTransactionByTimeResponse();
                          response.setGameTransactionByTime(gameTransactionByTime);
                          log.info("Sending to UI:{} ", response);
                          // send to ui
                          this.template.convertAndSend(DESTINATION, response);
                        })));
  }

  @KafkaListener(
    topics = "${kafka.topics.game-transaction-10-mins}",
    containerFactory = "gameTransactionKafkaListenerContainerFactory"
  )
  public void receive(ConsumerRecord<Long, PeriodGameTransaction> record) {
    Mono.fromFuture(
        CompletableFuture.runAsync(
            () -> {
              GameTransactionByTime gtbt = new GameTransactionByTime();
              PeriodGameTransaction periodGameTransaction = record.value();
              log.info("key {}, value {}", record.key(), periodGameTransaction);
              gtbt.setStartInMins(record.key());
              gtbt.setStartInSeconds(periodGameTransaction.getStartInSeconds());
              gtbt.setCashBet(periodGameTransaction.getCashBet());
              gtbt.setCashGgr(periodGameTransaction.getCashGgr());
              gtbt.setPromoBet(periodGameTransaction.getPromoBet());
              gtbt.setPromoGGR(periodGameTransaction.getPromoGGR());
              periodGameTransactionRepository.save(gtbt);
            }));
  }
}
