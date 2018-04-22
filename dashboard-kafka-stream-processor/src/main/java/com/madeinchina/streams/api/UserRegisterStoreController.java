package com.madeinchina.streams.api;

import com.madeinchina.streams.data.User;
import com.madeinchina.streams.user.UserRegisterAnalyticsBinding;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.QueryableStoreRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Log4j2
@RestController
@RequestMapping("/user/count/")
public class UserRegisterStoreController {
  @Autowired private QueryableStoreRegistry queryableStoreRegistry;
  private static final List<User> tests =
      Arrays.asList(
          new User[] { //
            new User("1", "user1@example.com"), //
            new User("2", "user1@example.com"), //
            new User("3", "user1@example.com")
          });

  @GetMapping("/{date}")
  public Long count(@PathVariable String date) {
    return (Long)
        queryableStoreRegistry
            .getQueryableStoreType(
                UserRegisterAnalyticsBinding.USER_REGISTER_COUNT_MV,
                QueryableStoreTypes.keyValueStore())
            .get(date);
  }

  @GetMapping(path = "/sync")
  public List<User> sync() {
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
    }
    return tests;
  }

  @GetMapping(path = "/async", produces = "application/json")
  public Flux<User> async() {
    return Flux.fromStream(tests.stream()).delaySubscription(Duration.ofMillis(10));
  }
}
