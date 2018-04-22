package com.madeinchina.report.dashboard.dao;

import com.madeinchina.report.dashboard.dao.entity.UsersCountByDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersCountByDayRepository extends JpaRepository<UsersCountByDay, String> {}
