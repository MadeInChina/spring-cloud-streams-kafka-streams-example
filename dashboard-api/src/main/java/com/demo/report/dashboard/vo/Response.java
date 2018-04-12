package com.demo.report.dashboard.vo;

import com.demo.report.dashboard.dao.entity.UsersCountByDay;
import lombok.Data;

import java.io.Serializable;

@Data
public class Response implements Serializable {
    private UsersCountByDay usersCountByDay;
}
