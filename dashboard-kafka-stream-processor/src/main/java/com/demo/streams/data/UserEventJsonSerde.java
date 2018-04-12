package com.demo.streams.data;

import org.springframework.kafka.support.serializer.JsonSerde;

public class UserEventJsonSerde extends JsonSerde<UserEvent> {}
