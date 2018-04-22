package com.madeinchina.report.dashboard.vo;

import com.madeinchina.report.dashboard.dao.entity.GameTransactionByTime;
import java.io.Serializable;
import lombok.Data;

@Data
public class GameTransactionByTimeResponse implements Serializable {
  private GameTransactionByTime gameTransactionByTime;
}
