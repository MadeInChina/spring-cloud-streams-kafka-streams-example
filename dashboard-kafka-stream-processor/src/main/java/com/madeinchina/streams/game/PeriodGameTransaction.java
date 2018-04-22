package com.madeinchina.streams.game;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class PeriodGameTransaction {
  private Long key;
  @JsonProperty("START_IN_SECONDS")
  private Long startInSeconds;

  @JsonProperty("PROMO_BET")
  private Double promoBet;

  @JsonProperty("PROMO_GGR")
  private Double promoGGR;

  @JsonProperty("CASH_BET")
  private Double cashBet;

  @JsonProperty("CASH_GGR")
  private Double cashGgr;
}
