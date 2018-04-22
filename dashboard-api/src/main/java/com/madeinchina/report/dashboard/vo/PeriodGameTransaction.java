package com.madeinchina.report.dashboard.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeriodGameTransaction {

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
