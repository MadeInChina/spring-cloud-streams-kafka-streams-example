package com.madeinchina.streams.user;

import com.madeinchina.streams.data.UserEvent;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;

@EnableBinding(UserRegisterAnalyticsBinding.class)
public class UserRegisterEventSink {
  @Autowired private UserRegisterEventFlow userEventFlow;

  @StreamListener(UserRegisterAnalyticsBinding.USER_REGISTER_IN)
  @SendTo(UserRegisterAnalyticsBinding.USER_REGISTER_OUT)
  public KStream<String, Long> process(KStream<String, UserEvent> input) {
    return userEventFlow.process(input).toStream();
  }
}
