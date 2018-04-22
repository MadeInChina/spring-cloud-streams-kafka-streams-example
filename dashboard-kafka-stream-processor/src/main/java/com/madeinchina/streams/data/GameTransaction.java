package com.madeinchina.streams.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameTransaction {

  @JsonProperty("PK_USER_ID")
  private String id;

  @JsonProperty("PROMO_BET")
  private Double promoBet;

  @JsonProperty("PROMO_GGR")
  private Double promoGGR;

  @JsonProperty("CASH_BET")
  private Double cashBet;

  @JsonProperty("CASH_GGR")
  private Double cashGgr;
}
