package com.weihan1394.SpringStatemachine.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {
  private Long id;
  private String item;            // Item to order
  private Integer number;         // Approval number
  private String note;            // Note
  private BigDecimal budget;      // Total paid for the order
  private Date approvalDate;
  private Date cancelDate;

  public Order(Long id, String item, BigDecimal budget) {
    this.id = id;
    this.item = item;
    this.budget = budget;
  }

  @Override
  public String toString() {
    return "Order [id=" + id + ", item=" + item + ", number=" + number +
            ", note=" + note + ", budget=" + budget + ", approvalDate=" +
            approvalDate + ", cancelDate=" + cancelDate + "]";
  }
}

