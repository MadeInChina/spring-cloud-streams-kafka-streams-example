package com.demo.streams.user;

import com.demo.streams.data.UserEvent;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;

public interface UserRegisterAnalyticsBinding {
  String USER_REGISTER_COUNT_MV = "urcmv";
  String USER_REGISTER_IN = "urin";
  String USER_REGISTER_OUT = "urot";

  @Input(USER_REGISTER_IN)
  KStream<String, UserEvent> userRegisterIn();

  @Output(USER_REGISTER_OUT)
  KStream<String, Long> userRegisterOut();
}
