package com.treasury.treasury.balance.dto;

import java.time.Instant;

import com.treasury.treasury.balance.constants.BalanceIncomingTypes;
import com.treasury.treasury.balance.constants.BalanceTypes;
import com.treasury.treasury.balance.constants.PaymentMethods;

public class BalanceDto {
  private BalanceTypes type;
  private Float value;
  private String responsible;
  private Instant balanceDate;
  private String description;
  private BalanceIncomingTypes incomingType;
  private PaymentMethods paymentMethod;
  private String category;

  public BalanceDto(
      BalanceTypes type,
      Float value,
      String responsible,
      Instant balanceDate,
      String description,
      BalanceIncomingTypes incomingTypes,
      String category,
      PaymentMethods paymentMethod) {
    this.type = type;
    this.value = value;
    this.responsible = responsible;
    this.balanceDate = balanceDate;
    this.description = description;
    this.incomingType = incomingTypes;
    this.paymentMethod = paymentMethod;
    this.category = category;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BalanceTypes getType() {
    return type;
  }

  public void setType(BalanceTypes type) {
    this.type = type;
  }

  public Float getValue() {
    return value;
  }

  public void setValue(Float value) {
    this.value = value;
  }

  public String getResponsible() {
    return responsible;
  }

  public void setResponsible(String responsible) {
    this.responsible = responsible;
  }

  public Instant getBalanceDate() {
    return balanceDate;
  }

  public void setBalanceDate(Instant balanceDate) {
    this.balanceDate = balanceDate;
  }

  public BalanceIncomingTypes getIncomingType() {
    return incomingType;
  }

  public void setIncomingType(BalanceIncomingTypes incomingTypes) {
    this.incomingType = incomingTypes;
  }

  public PaymentMethods getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(PaymentMethods paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }
}
