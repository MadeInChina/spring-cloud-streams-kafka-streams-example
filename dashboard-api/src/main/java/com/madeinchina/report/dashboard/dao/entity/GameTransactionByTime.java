package com.madeinchina.report.dashboard.dao.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class GameTransactionByTime implements Serializable {
  @Id
  @Column(name = "id")
  @JsonProperty("START_IN_SECONDS")
  private Long startInSeconds;

  @Column(name = "START_IN_MINS")
  @JsonProperty("START_IN_MINS")
  private Long startInMins;

  @JsonProperty("PROMO_BET")
  private Double promoBet;

  @JsonProperty("PROMO_GGR")
  private Double promoGGR;

  @JsonProperty("CASH_BET")
  private Double cashBet;

  @JsonProperty("CASH_GGR")
  private Double cashGgr;
}
