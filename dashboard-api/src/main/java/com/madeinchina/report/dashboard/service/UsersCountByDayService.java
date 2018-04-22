package com.madeinchina.report.dashboard.service;

import com.madeinchina.report.dashboard.dao.UsersCountByDayRepository;
import com.madeinchina.report.dashboard.dao.entity.UsersCountByDay;
import com.madeinchina.report.dashboard.vo.UsersCountByDayResponse;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Service class to send traffic data messages to dashboard ui at fixed interval using web-socket.
 *
 * @author abaghel
 */
@Service
@Slf4j
public class UsersCountByDayService {
  @Autowired private SimpMessagingTemplate template;

  @Autowired private UsersCountByDayRepository usersCountByDayRepository;

  public Mono<UsersCountByDay> findByToday() {
    return Mono.justOrEmpty(usersCountByDayRepository.findById(LocalDate.now().toString()));
  }

  // Method sends traffic data message in every 5 seconds.
  @Scheduled(fixedRate = 5000)
  public void trigger() {
    // prepare response
    findByToday()
        .subscribe(
            usersCountByDay ->
                Mono.fromFuture(
                    CompletableFuture.runAsync(
                        () -> {
                          UsersCountByDayResponse response = new UsersCountByDayResponse();
                          response.setUsersCountByDay(usersCountByDay);
                          log.info("Sending to UI:{} ", response);
                          // send to ui
                          this.template.convertAndSend("/topic/newusers", response);
                        })));
  }

  @KafkaListener(
    topics = "${kafka.topics.user}",
    containerFactory = "userKafkaListenerContainerFactory"
  )
  public void userReceive(ConsumerRecord<String, Long> record) {
    Mono.fromFuture(
        CompletableFuture.runAsync(
            () -> {
              log.info("key {}, value {}", record.key(), record.value());
              usersCountByDayRepository.save(new UsersCountByDay(record.key(), record.value()));
            }));
  }
}
