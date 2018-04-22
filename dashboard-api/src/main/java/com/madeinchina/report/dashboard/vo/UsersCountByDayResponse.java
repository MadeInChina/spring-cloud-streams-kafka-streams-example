package com.madeinchina.report.dashboard.vo;

import com.madeinchina.report.dashboard.dao.entity.UsersCountByDay;
import lombok.Data;

import java.io.Serializable;

@Data
public class UsersCountByDayResponse implements Serializable {
    private UsersCountByDay usersCountByDay;
}
