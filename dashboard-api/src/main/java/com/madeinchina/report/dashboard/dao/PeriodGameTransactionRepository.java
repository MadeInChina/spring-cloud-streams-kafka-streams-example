package com.madeinchina.report.dashboard.dao;

import com.madeinchina.report.dashboard.dao.entity.GameTransactionByTime;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodGameTransactionRepository
    extends JpaRepository<GameTransactionByTime, Long> {
  Stream<GameTransactionByTime> findByStartInMinsOrderByStartInSecondsDesc(Long startInMins);
}
