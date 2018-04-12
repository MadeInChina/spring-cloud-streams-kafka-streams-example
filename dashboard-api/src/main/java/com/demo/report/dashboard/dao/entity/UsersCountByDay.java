package com.demo.report.dashboard.dao.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UsersCountByDay implements Serializable {
  @Id
  private String id;
  private Long count;
}
