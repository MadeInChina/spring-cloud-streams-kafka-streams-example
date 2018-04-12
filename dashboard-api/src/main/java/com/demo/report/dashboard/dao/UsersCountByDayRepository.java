package com.demo.report.dashboard.dao;

import com.demo.report.dashboard.dao.entity.UsersCountByDay;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersCountByDayRepository extends CrudRepository<UsersCountByDay, String> {}
