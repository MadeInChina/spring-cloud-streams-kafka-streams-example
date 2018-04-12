package com.demo.report.dashboard;

import com.demo.report.dashboard.dao.UsersCountByDayRepository;
import com.demo.report.dashboard.dao.entity.UsersCountByDay;
import com.demo.report.dashboard.vo.Response;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Service class to send traffic data messages to dashboard ui at fixed interval using web-socket.
 *
 * @author abaghel
 */
@Service
public class UsersCountByDayService {
  private static final Logger logger = Logger.getLogger(UsersCountByDayService.class);

  @Autowired private SimpMessagingTemplate template;

  @Autowired private UsersCountByDayRepository usersCountByDayRepository;

  // Method sends traffic data message in every 5 seconds.
  @Scheduled(fixedRate = 5000)
  public void trigger() {
    usersCountByDayRepository.findById(LocalDate.now().toString());
    // prepare response
    Response response = new Response();
    response.setUsersCountByDay(
        usersCountByDayRepository
            .findById(LocalDate.now().toString())
            .orElseGet(() -> new UsersCountByDay()));
    logger.info("Sending to UI " + response);
    // send to ui
    this.template.convertAndSend("/topic/newusers", response);
  }

  @KafkaListener(topics = "${spring.kafka.topic}")
  public void receive(ConsumerRecord<String, Long> record) {
    System.out.println("key:" + record.key() + " value:" + record.value());
    logger.info("Sending to UI " + record.value());
    usersCountByDayRepository.save(new UsersCountByDay(record.key(), record.value()));
  }
}
